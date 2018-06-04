package end.to.end;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import testclasses.fastfail.StopIfFailedTest;
import util.InvokedMethodNameListener;
import util.TestUtils;

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
}
