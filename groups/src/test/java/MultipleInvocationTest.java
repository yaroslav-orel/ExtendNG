import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodInGroupListener.class)
public class MultipleInvocationTest {

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

    @Test(groups = "target", invocationCount = 3)
    public void dummyInvocationCountTest(){
    }

    @Test(dependsOnMethods = "dummyInvocationCountTest")
    public void beforeAfterAreExecutedForEachInvokation(){
        Assert.assertEquals(beforeMethodInGroupInvokedCount, 3);
        Assert.assertEquals(afterMethodInGroupInvokedCount, 3);
    }
}
