import org.testng.Assert;
import org.testng.annotations.Test;

public class InvokeTest {

    private int beforeMethodInGroupInvokedCount = 0;
    private int afterMethodInGroupInvokedCount = 0;

    @BeforeMethodInGroup("target")
    private void before(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroup("target")
    private void after(){
        afterMethodInGroupInvokedCount += 1;
    }

    @Test
    public void startingTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 0);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 0);
    }

    @Test(groups = "target", dependsOnMethods = "startingTest")
    public void actualTest1(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 1);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 0);
    }

    @Test(groups = "target", dependsOnMethods = "actualTest1")
    public void actualTest2(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 2);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 1);
    }

    @Test(dependsOnMethods = "actualTest2")
    public void closingTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 2);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 2);
    }
}
