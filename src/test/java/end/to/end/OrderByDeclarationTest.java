package end.to.end;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import testclasses.orderbydeclaration.ChildTest;
import testclasses.orderbydeclaration.DoNotOrderWithoutListener;
import util.InvokedMethodNameListener;
import util.TestUtils;

public class OrderByDeclarationTest {

    @Test
    public void orderByDeclaration(){
        val invokedMethodNameListener = TestUtils.run(new InvokedMethodNameListener(), testclasses.orderbydeclaration.OrderByDeclarationTest.class);

        Assertions.assertThat(invokedMethodNameListener.getInvokedMethodNames()).containsExactly(
                "nameThis()",
                "withoutDependency()",
                "aVeryCoolTest()",
                "veryImportant()",
                "needThisToBeLast()"
        );
    }

    @Test
    public void orderByDeclarationDoesNotChangeOrderOfClassesWithoutListener(){
        val invokedMethodNameListener = TestUtils.run(
                new InvokedMethodNameListener(),
                testclasses.orderbydeclaration.OrderByDeclarationTest.class, DoNotOrderWithoutListener.class);

        Assertions.assertThat(invokedMethodNameListener.getInvokedMethodNames()).containsSequence(
                "aNiceOne()",
                "boyOhBoy()",
                "test1()"
        );
    }

    @Test
    public void orderByDeclarationSuperclassMethodsAlsoOrdered(){
        val invokedMethodNameListener = TestUtils.run(new InvokedMethodNameListener(), ChildTest.class);

        Assertions.assertThat(invokedMethodNameListener.getInvokedMethodNames()).containsExactly(
                "theTestThatShouldBeFirst()",
                "aTestThatShouldBeSecond()",
                "betterThisToBeLastInParent()",
                "firstInChildCLass()",
                "aLastOne()"
        );
    }
}
