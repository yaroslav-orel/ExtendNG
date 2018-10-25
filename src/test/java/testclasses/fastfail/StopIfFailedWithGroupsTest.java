package testclasses.fastfail;

import org.extendng.FastFailListener;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(FastFailListener.class)
public class StopIfFailedWithGroupsTest {

    @BeforeGroups(groups = "target")
    public void beforeTargetGroup(){}

    @AfterGroups(groups = "target")
    public void afterTargetGroup(){}

    @Test(priority = 0)
    public void throwsException(){ Assert.fail(); }

    @Test(priority = 1, groups = "target")
    public void doesNotRunAfterException(){}

}
