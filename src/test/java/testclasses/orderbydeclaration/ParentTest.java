package testclasses.orderbydeclaration;

import org.extendng.OrderByDeclarationListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(OrderByDeclarationListener.class)
public class ParentTest {

    @Test
    public void theTestThatShouldBeFirst(){ }

    @Test
    public void aTestThatShouldBeSecond(){ }

    @Test
    public void betterThisToBeLastInParent(){ }
}
