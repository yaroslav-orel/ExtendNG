package testclasses.fastfail;

import org.extendng.FastFailListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(FastFailListener.class)
public class StopIfFailedTest {

    @Test(priority = 0)
    public void doesntThrowException(){}

    @Test(priority = 1)
    public void throwsException(){ Assert.fail(); }

    @Test(priority = 2)
    public void doesNotRunAfterException1(){}

    @Test(priority = 3)
    public void doesNotRunAfterException2(){}
}
