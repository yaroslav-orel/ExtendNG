package testclasses.methodingroups;

import org.extendng.MethodInGroupsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupsListener.class)
public class SubclassTest extends BaseClass {

    @Test(groups = "methodInBase")
    public void superclassBeforesArePickedUp(){ }
}
