import lombok.val;
import org.testng.annotations.Test;
import testclasses.fastfail.StopIfFailedTest;
import testclasses.methodingroups.*;
import testclasses.orderbydeclaration.ChildTest;
import testclasses.orderbydeclaration.DoNotOrderWithoutListener;
import testclasses.orderbydeclaration.OrderByDeclarationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSuite {

    @Test
    public void fastFailPositive(){
        InvokedMethodNameListener orderListener = TestUtils.run(new InvokedMethodNameListener(), StopIfFailedTest.class);

        assertThat(orderListener.getSucceedMethodNames()).containsExactly("doesntThrowException()");
        assertThat(orderListener.getFailedMethodNames()).containsExactly("throwsException()");
        assertThat(orderListener.getSkippedMethodNames()).containsExactly("doesNotRunAfterException1()", "doesNotRunAfterException2()");
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

        assertThat(InvocationTest.beforeCalledCount).isEqualTo(2);
        assertThat(InvocationTest.afterCalledCount).isEqualTo(2);
    }

    @Test
    public void methodInGroupsGroupsDontMatch(){
        TestUtils.run(DoNotInvokeTest.class);

        assertThat(DoNotInvokeTest.beforeCalledCount).isEqualTo(0);
        assertThat(DoNotInvokeTest.afterCalledCount).isEqualTo(0);
    }

    @Test
    public void methodInGroupsNoListener(){
        TestUtils.run(ListenerAbsentTest.class);

        assertThat(ListenerAbsentTest.beforeCalledCount).isEqualTo(0);
        assertThat(ListenerAbsentTest.afterCalledCount).isEqualTo(0);
    }

    @Test
    public void methodInGroupsMultipleGroups(){
        TestUtils.run(MultipleGroupsTest.class);

        assertThat(MultipleGroupsTest.beforeCalledCount).isEqualTo(2);
        assertThat(MultipleGroupsTest.afterCalledCount).isEqualTo(2);
    }

    @Test
    public void methodInGroupsMultipleMethods(){
        TestUtils.run(MultipleMethodsTest.class);

        assertThat(MultipleMethodsTest.isBefore1Called).isTrue();
        assertThat(MultipleMethodsTest.isBefore2Called).isTrue();
        assertThat(MultipleMethodsTest.isAfter1Called).isTrue();
        assertThat(MultipleMethodsTest.isAfter2Called).isTrue();

        assertThat(MultipleMethodsTest.beforeCalledFirst).isEqualTo("before1");
        assertThat(MultipleMethodsTest.afterCalledFirst).isEqualTo("after1");
    }

    @Test
    public void methodInGroupsWithSuperclass(){
        TestUtils.run(SubclassTest.class);

        assertThat(BaseClass.isBeforeCalled).isTrue();
        assertThat(BaseClass.isAfterCalled).isTrue();
    }
}
