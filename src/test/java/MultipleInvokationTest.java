import org.testng.Assert;
import org.testng.annotations.Test;

public class MultipleInvokationTest {

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

    @Test(groups = "target", dependsOnMethods = "startingTest", invocationCount = 3)
    public void actualTest(){
    }

    @Test(dependsOnMethods = "actualTest")
    public void closingTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 3);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 3);
    }
}
