import org.extendng.AfterMethodInGroup;
import org.extendng.BeforeMethodInGroup;
import org.extendng.MethodInGroupListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupListener.class)
public class MultipleMethodsTest {

    private int beforeMethodInGroupInvokedCount = 0;
    private int afterMethodInGroupInvokedCount = 0;

    @BeforeMethodInGroup(groups = "target")
    public void before1(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @BeforeMethodInGroup(groups = "target")
    public void before2(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroup(groups = "target")
    public void after1(){
        afterMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroup(groups = "target")
    public void after2(){
        afterMethodInGroupInvokedCount += 1;
    }

    @Test(groups = "target")
    public void multipleBeforesAreExecuted(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 2);
    }

    @Test(dependsOnMethods = "multipleBeforesAreExecuted")
    public void multipleAftersAreExecuted(){
        Assert.assertEquals(afterMethodInGroupInvokedCount, 2);
    }
}
