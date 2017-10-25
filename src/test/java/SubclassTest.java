import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupListener.class)
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

    @Test(dependsOnMethods = "actualTest")
    public void closingTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 1);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 1);
    }
}
