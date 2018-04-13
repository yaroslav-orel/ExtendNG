import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import testclasses.fastfail.StopIfFailedTest;
import testclasses.methodingroups.*;
import testclasses.orderbydeclaration.ChildTest;
import testclasses.orderbydeclaration.DoNotOrderWithoutListener;
import testclasses.orderbydeclaration.OrderByDeclarationTest;
import testclasses.orderbygroups.OrderByGroupsSortedTest;
import testclasses.orderbygroups.OrderByGroupsSpecialCasesTest;
import testclasses.orderbygroups.OrderByGroupsTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSuite {

    @Test
    public void fastFailPositive(){
        val orderListener = TestUtils.run(new InvokedMethodNameListener(), StopIfFailedTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(orderListener.getSucceedMethodNames()).containsExactly("doesntThrowException()");
        softly.assertThat(orderListener.getFailedMethodNames()).containsExactly("throwsException()");
        softly.assertThat(orderListener.getSkippedMethodNames()).containsExactly("doesNotRunAfterException1()", "doesNotRunAfterException2()");
        softly.assertAll();
    }

    @Test
    public void orderByDeclaration(){
        val invokedMethodNameListener = TestUtils.run(new InvokedMethodNameListener(), OrderByDeclarationTest.class);

        assertThat(invokedMethodNameListener.getInvokedMethodNames()).containsExactly(
                "nameThis()",
                "withoutDependency()",
                "aVeryCoolTest()",
                "veryImportant()",
                "needThisToBeLast()"
        );
    }

    @Test
    public void orderByDeclarationDoesNotChangeOrderOfClassesWithoutListener(){
        val invokedMethodNameListener = TestUtils.run(
                new InvokedMethodNameListener(),
                OrderByDeclarationTest.class, DoNotOrderWithoutListener.class);

        assertThat(invokedMethodNameListener.getInvokedMethodNames()).containsSequence(
                "aNiceOne()",
                "boyOhBoy()",
                "test1()"
        );
    }

    @Test
    public void orderByDeclarationSuperclassMethodsAlsoOrdered(){
        val invokedMethodNameListener = TestUtils.run(new InvokedMethodNameListener(), ChildTest.class);

        assertThat(invokedMethodNameListener.getInvokedMethodNames()).containsExactly(
                "theTestThatShouldBeFirst()",
                "aTestThatShouldBeSecond()",
                "betterThisToBeLastInParent()",
                "firstInChildCLass()",
                "aLastOne()"
        );
    }

    @Test
    public void methodInGroups(){
        TestUtils.run(InvocationTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(InvocationTest.beforeCalledCount).isEqualTo(2);
        softly.assertThat(InvocationTest.afterCalledCount).isEqualTo(2);
        softly.assertAll();
    }

    @Test
    public void methodInGroupsGroupsDontMatch(){
        TestUtils.run(DoNotInvokeTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(DoNotInvokeTest.beforeCalledCount).isEqualTo(0);
        softly.assertThat(DoNotInvokeTest.afterCalledCount).isEqualTo(0);
        softly.assertAll();
    }

    @Test
    public void methodInGroupsNoListener(){
        TestUtils.run(ListenerAbsentTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ListenerAbsentTest.beforeCalledCount).isEqualTo(0);
        softly.assertThat(ListenerAbsentTest.afterCalledCount).isEqualTo(0);
        softly.assertAll();
    }

    @Test
    public void methodInGroupsMultipleGroups(){
        TestUtils.run(MultipleGroupsTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(MultipleGroupsTest.beforeCalledCount).isEqualTo(2);
        softly.assertThat(MultipleGroupsTest.afterCalledCount).isEqualTo(2);
        softly.assertAll();
    }

    @Test
    public void methodInGroupsMultipleMethods(){
        TestUtils.run(MultipleMethodsTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(MultipleMethodsTest.isBefore1Called).isTrue();
        softly.assertThat(MultipleMethodsTest.isBefore2Called).isTrue();
        softly.assertThat(MultipleMethodsTest.isAfter1Called).isTrue();
        softly.assertThat(MultipleMethodsTest.isAfter2Called).isTrue();

        softly.assertThat(MultipleMethodsTest.beforeCalledFirst).isEqualTo("before1");
        softly.assertThat(MultipleMethodsTest.afterCalledFirst).isEqualTo("after1");
        softly.assertAll();
    }

    @Test
    public void methodInGroupsWithSuperclass(){
        TestUtils.run(SubclassTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(BaseClass.isBeforeCalled).isTrue();
        softly.assertThat(BaseClass.isAfterCalled).isTrue();
        softly.assertAll();
    }

    @Test
    public void methodInGroupsInjection(){
        val invokedMethodNameListener = TestUtils.run(new InvokedMethodNameListener(), InjectionTest.class);

        assertThat(invokedMethodNameListener.getSucceedMethodNames()).containsExactly("injectionPositive()");
    }

    @Test
    public void orderByGroups(){
        val listener = TestUtils.run(new InvokedMethodNameListener(), OrderByGroupsTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("test1()", "wayToName()");
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("coveredByTests()", "thisOne()");
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("originalName()", "treatThisRight()");
        softly.assertAll();
    }

    @Test
    public void orderByGroupsSortedGroups(){
        val listener = TestUtils.run(new InvokedMethodNameListener(), OrderByGroupsSortedTest.class);

        assertThat(listener.getInvokedMethodNames()).containsExactly(
                "test1()", "wayToName()",
                "coveredByTests()", "treatThisRight()",
                "originalName()", "thisOne()"
        );
    }

    @Test
    public void orderByGroupsSpecialCases(){
        val listener = TestUtils.run(new InvokedMethodNameListener(), OrderByGroupsSpecialCasesTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("withMultipleGroups()", "actuallyWithOneGroup()");
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("anotherTestWithoutGroups()", "zeroGroups()");
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("betThisWorks()", "orderMeMan()");
        softly.assertAll();
    }
}
