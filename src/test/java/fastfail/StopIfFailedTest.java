package fastfail;

import org.extendng.FastFailListener;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(FastFailListener.class)
public class StopIfFailedTest {

    @Test(priority = 0)
    public void doesntThrowException(){ }

    @Test(expectedExceptions = AssertionError.class, priority = 1)
    public void throwsException(){
        Assert.fail();
    }

    @Test(expectedExceptions = SkipException.class, expectedExceptionsMessageRegExp = ".* 'throwsException'", priority = 2)
    public void DoesNotRunAfterException1(){ }

    @Test(expectedExceptions = SkipException.class, expectedExceptionsMessageRegExp = ".* .* 'throwsException'", priority = 3)
    public void doesNotRunAfterException2(){}
}
