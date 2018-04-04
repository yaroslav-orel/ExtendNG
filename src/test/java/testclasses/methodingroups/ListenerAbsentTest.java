package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ListenerAbsentTest {
    private int beforeMethodInGroupInvokedCount = 0;
    private int afterMethodInGroupInvokedCount = 0;

    @BeforeMethodInGroups(groups = "target")
    public void before(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroups(groups = "target")
    public void after(){
        afterMethodInGroupInvokedCount += 1;
    }

    @Test(groups = "target")
    public void beforeIsNotExecutedWithoutListener(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 0);
    }

    @Test(dependsOnMethods = "beforeIsNotExecutedWithoutListener")
    public void afterIsNotExecutedWithoutListener(){
        Assert.assertEquals(afterMethodInGroupInvokedCount, 0);
    }
}
