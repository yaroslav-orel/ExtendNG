package flow;

import org.extendng.FlowListener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(FlowListener.class)
public class SmokeTest {

    String orderOfExecution = "";

    @Test
    public void nameThis(){
        orderOfExecution += "nameThis ";
        Assert.assertEquals(orderOfExecution, "nameThis ");
    }

    @Test
    public void withoutDependency(){
        orderOfExecution += "withoutDependency ";
        Assert.assertEquals(orderOfExecution, "nameThis withoutDependency ");
    }

    @Test
    public void aVeryCoolTest(){
        orderOfExecution += "aVeryCoolTest ";
        Assert.assertEquals(orderOfExecution, "nameThis withoutDependency aVeryCoolTest ");
    }

    @Test
    public void veryImportant(){
        orderOfExecution += "veryImportant ";
        Assert.assertEquals(orderOfExecution, "nameThis withoutDependency aVeryCoolTest veryImportant ");
    }

    @Test
    public void needThisToBeLast(){
        orderOfExecution += "needThisToBeLast";
        Assert.assertEquals(orderOfExecution, "nameThis withoutDependency aVeryCoolTest veryImportant needThisToBeLast");
    }

}
