package fastfail;

import org.extendng.FastFailListener;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(FastFailListener.class)
public class StopIfFailedTest {

    @Test(expectedExceptions = AssertionError.class, priority = 0)
    public void throwsExcpetion(){
        Assert.fail();
    }

    @Test(expectedExceptions = SkipException.class, priority = 1)
    public void DoesNotRunAfterExcpetion1(){ }

    @Test(expectedExceptions = SkipException.class, priority = 2)
    public void doesNotRunAfterException2(){}
}
