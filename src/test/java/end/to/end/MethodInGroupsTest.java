package end.to.end;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import testclasses.methodingroups.*;
import util.InvokedMethodNameListener;
import util.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodInGroupsTest {

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
    public void methodInGroupsSkippedByBeforeClass(){
        val listener = TestUtils.run(new InvokedMethodNameListener(), IgnoreSkippedTest.class);

        assertThat(listener.getInvokedMethodNames()).contains("skipped()");
    }
}
