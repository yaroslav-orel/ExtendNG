package end.to.end;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import testclasses.fastfail.StopIfFailedTest;
import testclasses.fastfail.StopIfFailedWithGroupsTest;
import util.InvokedMethodNameListener;
import util.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class FastFailTest {

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
    public void fastFailCorrectMessage(){
        val orderListener = TestUtils.run(new InvokedMethodNameListener(), StopIfFailedTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(orderListener.getResult("doesNotRunAfterException1()").getThrowable().getMessage())
                .isEqualTo("Skipped because of the failed test 'throwsException'");
        softly.assertThat(orderListener.getResult("doesNotRunAfterException2()").getThrowable().getMessage())
                .isEqualTo("Skipped because of the failed test 'throwsException'");
        softly.assertAll();
    }

    //If there's group configuration method in test class NPE is thrown if not handled in listener
    @Test
    public void fastFailWithGroupsDoesntThrowException(){
        val orderListener = TestUtils.run(new InvokedMethodNameListener(), StopIfFailedWithGroupsTest.class);

        assertThat(orderListener.getInvokedMethodNames()).containsExactly("throwsException()", "doesNotRunAfterException()");
    }
}
