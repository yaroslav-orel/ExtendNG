package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.annotations.Listeners;

@Listeners(MethodInGroupsListener.class)
public class BaseClass {

    public static boolean isBeforeCalled = false;
    public static boolean isAfterCalled = false;

    @BeforeMethodInGroups(groups = "methodInBase")
    public void baseBefore(){
        isBeforeCalled = true;
    }

    @AfterMethodInGroups(groups = "methodInBase")
    public void baseAfter(){
        isAfterCalled = true;
    }
}
