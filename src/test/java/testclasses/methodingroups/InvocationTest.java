package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({MethodInGroupsListener.class})
public class InvocationTest {

    public static int beforeCalledCount = 0;
    public static int afterCalledCount = 0;

    @BeforeMethodInGroups(groups = "target")
    public void before(){ beforeCalledCount++; }

    @AfterMethodInGroups(groups = "target")
    public void after(){ afterCalledCount++; }

    @BeforeMethod
    public void setUp(){ }

    @Test(groups = "target")
    public void tesWithTargetGroup1(){ }

    @Test(groups = "target")
    public void tesWithTargetGroup2(){ }

    @Test
    public void hasNoGroup(){ }

    @Test(groups = "notTarget")
    public void hasOtherGroup(){ }
}
