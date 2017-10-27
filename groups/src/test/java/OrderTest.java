import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupListener.class)
public class OrderTest {

    private String beforeMethodInGroupInvokedCount = "";
    private String afterMethodInGroupInvokedCount = "";

    @BeforeMethodInGroup("target")
    public void before1(){ beforeMethodInGroupInvokedCount += "before1 "; }

    @BeforeMethodInGroup("target")
    public void before2(){
        beforeMethodInGroupInvokedCount += "before2 ";
    }

    @AfterMethodInGroup("target")
    public void after1(){
        afterMethodInGroupInvokedCount += "after1 ";
    }

    @AfterMethodInGroup("target")
    public void after2(){
        afterMethodInGroupInvokedCount += "after2 ";
    }

    @Test
    public void startingTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, "");
        Assert.assertEquals(afterMethodInGroupInvokedCount, "");
    }

    @Test(groups = "target", dependsOnMethods = "startingTest")
    public void actualTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, "before1 before2 ");
        Assert.assertEquals(afterMethodInGroupInvokedCount, "");
    }

    @Test(dependsOnMethods = "actualTest")
    public void closingTest(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, "before1 before2 ");
        Assert.assertEquals(afterMethodInGroupInvokedCount, "after1 after2 ");
    }
}
