import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({MethodInGroupListener.class})
public class InvokeTest {

    private int beforeMethodInGroupInvokedCount = 0;
    private int afterMethodInGroupInvokedCount = 0;

    @BeforeMethodInGroup(groups = "target")
    private void before(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroup(groups = "target")
    private void after(){
        afterMethodInGroupInvokedCount += 1;
    }

    @Test(groups = "target")
    public void beforeIsInvokedBeforeTest1(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 1);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 0);
    }

    @Test(groups = "target", dependsOnMethods = "beforeIsInvokedBeforeTest1")
    public void beforeIsInvokedBeforeTest2(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 2);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 1);
    }

    @Test(dependsOnMethods = "beforeIsInvokedBeforeTest2")
    public void afterIsInvokedAfterEachTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 2);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 2);
    }
}
