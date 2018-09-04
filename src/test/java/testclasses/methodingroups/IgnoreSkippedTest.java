package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupsListener.class)
public class IgnoreSkippedTest {

    @BeforeClass
    public void beforeTest(){
        Assert.fail("Intentional fail");
    }

    @BeforeMethodInGroups(groups = "skipped")
    public void beforeMethodInGroup(){ }


    @AfterMethodInGroups(groups = "skipped")
    public void afterMethodInGroup(){ }


    @Test(groups = "skipped")
    public void skipped() { }

}
