package methodingroups;

import org.extendng.AfterMethodInGroup;
import org.extendng.BeforeMethodInGroup;
import org.extendng.MethodInGroupListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupListener.class)
public class OrderTest {

    private String beforeMethodInGroupInvokedCount = "";
    private String afterMethodInGroupInvokedCount = "";

    @BeforeMethodInGroup(groups = "target", priority = 1)
    public void before1(){ beforeMethodInGroupInvokedCount += "before1 "; }

    @BeforeMethodInGroup(groups = "target", priority = 2)
    public void before2(){
        beforeMethodInGroupInvokedCount += "before2 ";
    }

    @AfterMethodInGroup(groups = "target", priority = 0)
    public void after1(){
        afterMethodInGroupInvokedCount += "after1 ";
    }

    @AfterMethodInGroup(groups = "target", priority = 3)
    public void after2(){
        afterMethodInGroupInvokedCount += "after2 ";
    }

    @Test(groups = "target")
    public void beforesExecuteInPriorityOrder(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, "before1 before2 ");
    }

    @Test(dependsOnMethods = "beforesExecuteInPriorityOrder")
    public void aftersExecuteInPriorityOrder(){
        Assert.assertEquals(afterMethodInGroupInvokedCount, "after1 after2 ");
    }
}
