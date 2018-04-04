import lombok.val;
import org.testng.annotations.Test;
import testclasses.fastfail.StopIfFailedTest;
import testclasses.orderbydeclaration.DoNotOrderWithoutListener;
import testclasses.orderbydeclaration.OrderByDeclarationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSuite {

    @Test
    public void fastFailPositive(){
        InvokedMethodNameListener orderListener = TestUtils.run(new InvokedMethodNameListener(), StopIfFailedTest.class);

        assertThat(orderListener.getSucceedMethodNames()).containsExactly("doesntThrowException()");
        assertThat(orderListener.getFailedMethodNames()).containsExactly("throwsException()");
        assertThat(orderListener.getSkippedMethodNames()).containsExactly("doesNotRunAfterException1()", "doesNotRunAfterException2()");
    }

    @Test
    public void orderByDeclaration(){
        val invokedMethodNameListener = TestUtils.run(new InvokedMethodNameListener(), OrderByDeclarationTest.class);

        assertThat(invokedMethodNameListener.getInvokedMethodNames()).containsExactly(
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
                OrderByDeclarationTest.class, DoNotOrderWithoutListener.class);

        assertThat(invokedMethodNameListener.getInvokedMethodNames()).containsSequence(
                "aNiceOne()",
                "boyOhBoy()",
                "test1()"
        );
    }
}
