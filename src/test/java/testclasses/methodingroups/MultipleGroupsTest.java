package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupsListener.class)
public class MultipleGroupsTest {

    public static int beforeCalledCount = 0;
    public static int afterCalledCount = 0;

    @BeforeMethodInGroups(groups = {"one", "two", "three"})
    private void before(){
        beforeCalledCount++;
    }

    @AfterMethodInGroups(groups = {"one", "two", "three"})
    private void after(){
        afterCalledCount++;
    }

    @Test(groups = "one")
    public void multipleGroupsOneMatch(){ }

    @Test(groups = {"two","four"})
    public void multipleGroupsOneMatchOneMiss(){ }

    @Test(groups = {"five", "six", "seven"})
    public void multipleGroupsNoMatch(){ }

    @Test
    public void multipleGroupsAfterNoMatch(){ }
}
