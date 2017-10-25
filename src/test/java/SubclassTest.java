import org.testng.Assert;
import org.testng.annotations.Test;

public class SubclassTest extends BaseTest{

    @Test
    public void startingTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 0);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 0);
    }

    @Test(groups = "methodInBase", dependsOnMethods = "startingTest")
    public void actualTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 1);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 0);
    }

    @Test(dependsOnMethods = "actualTest2")
    public void closingTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 2);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 2);
    }
}
