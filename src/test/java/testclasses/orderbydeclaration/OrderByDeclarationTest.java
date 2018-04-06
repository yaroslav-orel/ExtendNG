package testclasses.orderbydeclaration;

import org.extendng.OrderByDeclarationListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(OrderByDeclarationListener.class)
public class OrderByDeclarationTest {

    @Test
    public void nameThis(){ }

    @Test
    public void withoutDependency(){ }

    @Test
    public void aVeryCoolTest(){ }

    @Test
    public void veryImportant(){ }

    @Test
    public void needThisToBeLast(){ }

}
