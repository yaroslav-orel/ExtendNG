package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.annotations.Listeners;

@Listeners(MethodInGroupsListener.class)
public class BaseClass {

    protected int beforeMethodInGroupInvokedCount = 0;
    protected int afterMethodInGroupInvokedCount = 0;

    @BeforeMethodInGroups(groups = "methodInBase")
    public void baseBefore(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroups(groups = "methodInBase")
    public void baseAfter(){
        afterMethodInGroupInvokedCount += 1;
    }
}
