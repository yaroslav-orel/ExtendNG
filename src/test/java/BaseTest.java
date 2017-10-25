public class BaseTest {

    protected int beforeMethodInGroupInvokedCount = 0;
    protected int afterMethodInGroupInvokedCount = 0;

    @BeforeMethodInGroup("methodInBase")
    public void baseBefore(){
        beforeMethodInGroupInvokedCount += 1;
    }

    @AfterMethodInGroup("methodInBase")
    public void baseAfter(){
        afterMethodInGroupInvokedCount += 1;
    }
}
