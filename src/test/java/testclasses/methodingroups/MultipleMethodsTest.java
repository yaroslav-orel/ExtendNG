package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupsListener.class)
public class MultipleMethodsTest {

    public static boolean isBefore1Called = false;
    public static boolean isAfter1Called = false;
    public static boolean isBefore2Called = false;
    public static boolean isAfter2Called = false;

    public static String beforeCalledFirst = null;
    public static String afterCalledFirst = null;

    @BeforeMethodInGroups(groups = "target", priority = 1)
    public void before1(){ isBefore1Called = true; beforeCalledFirst("before1"); }

    @BeforeMethodInGroups(groups = "target", priority = 2)
    public void before2(){ isBefore2Called = true; beforeCalledFirst("before2"); }

    @AfterMethodInGroups(groups = "target", priority = 0)
    public void after1(){ isAfter1Called = true; afterCalledFirst("after1");}

    @AfterMethodInGroups(groups = "target", priority = 3)
    public void after2(){ isAfter2Called = true; afterCalledFirst("after2");}

    @Test(groups = "target")
    public void beforesExecuteInPriorityOrder(){ }

    private static void beforeCalledFirst(String methodName){
        if(beforeCalledFirst != null)
            return;

        beforeCalledFirst = methodName;
    }

    private static void afterCalledFirst(String methodName){
        if(afterCalledFirst != null)
            return;

        afterCalledFirst = methodName;
    }

}
