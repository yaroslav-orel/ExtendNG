package methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupsListener.class)
public class DoNotInvokeTest {

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

    @Test(groups = "notTarget")
    public void beforeIsNotInvokedWithoutGroup(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 0);
    }

    @Test(dependsOnMethods = "beforeIsNotInvokedWithoutGroup")
    public void afterIsNotInvokedWithoutGroup(){
        Assert.assertEquals(afterMethodInGroupInvokedCount, 0);
    }

}
