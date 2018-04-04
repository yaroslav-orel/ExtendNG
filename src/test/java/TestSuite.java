import org.testng.annotations.Test;
import testclasses.fastfail.StopIfFailedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSuite {

    @Test
    public void fastFailPositive(){
        InvokedMethodNameListener orderListener = TestUtils.run(new InvokedMethodNameListener(), StopIfFailedTest.class);

        assertThat(orderListener.getSucceedMethodNames()).containsExactly("doesntThrowException()");
        assertThat(orderListener.getFailedMethodNames()).containsExactly("throwsException()");
        assertThat(orderListener.getSkippedMethodNames()).containsExactly("doesNotRunAfterException1()", "doesNotRunAfterException2()");
    }
}
