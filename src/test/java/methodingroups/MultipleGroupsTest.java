package methodingroups;

import org.extendng.AfterMethodInGroup;
import org.extendng.BeforeMethodInGroup;
import org.extendng.MethodInGroupListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupListener.class)
public class MultipleGroupsTest {

    private int beforeMethodInGroupInvokedCount = 0;
    private int afterMethodInGroupInvokedCount = 0;

    @BeforeMethodInGroup(groups = {"one", "two", "three"})
    private void before(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroup(groups = {"one", "two", "three"})
    private void after(){
        afterMethodInGroupInvokedCount += 1;
    }

    @Test(groups = "one")
    public void multipleGroupsOneMatch(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 1);
    }

    @Test(groups = {"two","four"}, dependsOnMethods = "multipleGroupsOneMatch")
    public void multipleGroupsOneMatchOneMiss(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 2);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 1);
    }

    @Test(groups = {"five", "six", "seven"}, dependsOnMethods = "multipleGroupsOneMatchOneMiss")
    public void multipleGroupsNoMatch(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 2);
    }

    @Test(dependsOnMethods = "multipleGroupsNoMatch")
    public void multipleGroupsAfterNoMatch(){
        Assert.assertEquals(afterMethodInGroupInvokedCount, 2);
    }
}
