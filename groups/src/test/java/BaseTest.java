import org.testng.annotations.Listeners;

@Listeners(MethodInGroupListener.class)
public class BaseTest {

    protected int beforeMethodInGroupInvokedCount = 0;
    protected int afterMethodInGroupInvokedCount = 0;

    @BeforeMethodInGroup(groups = "methodInBase")
    public void baseBefore(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroup(groups = "methodInBase")
    public void baseAfter(){
        afterMethodInGroupInvokedCount += 1;
    }
}
