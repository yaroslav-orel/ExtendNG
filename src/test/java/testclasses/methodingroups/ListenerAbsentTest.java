package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.testng.annotations.Test;

public class ListenerAbsentTest {

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

    @Test(groups = "target")
    public void beforeIsNotExecutedWithoutListener(){ }
}
