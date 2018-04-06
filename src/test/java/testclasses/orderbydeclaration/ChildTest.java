package testclasses.orderbydeclaration;

import org.extendng.OrderByDeclarationListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(OrderByDeclarationListener.class)
public class ChildTest extends ParentTest {

    @Test
    public void firstInChildCLass(){ }

    @Test
    public void aLastOne() { }
}
