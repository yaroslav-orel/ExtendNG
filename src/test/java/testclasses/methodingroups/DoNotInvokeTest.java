package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupsListener.class)
public class DoNotInvokeTest {

    public static int beforeCalledCount = 0;
    public static int afterCalledCount = 0;

    @BeforeMethodInGroups(groups = "target")
    public void before(){
        beforeCalledCount++;
    }

    @AfterMethodInGroups(groups = "target")
    public void after(){
        afterCalledCount++;
    }

    @Test(groups = "notTarget")
    public void beforeIsNotInvokedWithoutGroup(){ }

    @Test(groups = "target1")
    public void afterIsNotInvokedWithoutGroup(){ }

}
