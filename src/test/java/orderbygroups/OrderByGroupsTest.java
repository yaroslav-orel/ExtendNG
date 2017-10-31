package orderbygroups;

import org.extendng.OrderByGroups;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Listeners(OrderByGroups.class)
public class OrderByGroupsTest {

    String orderOfExecution = "";

    @AfterClass
    public void finalAssert(){
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(orderOfExecution.contains("test1 wayToName"), "group 'first' was not grouped");
        softAssert.assertTrue(orderOfExecution.contains("originalName thisOne"), "group 'third' was not grouped");
        softAssert.assertTrue(orderOfExecution.contains("coveredByTests treatThisRight"), "group 'second' was not grouped");

        softAssert.assertAll();
    }

    @Test(groups = "first")
    public void test1(){
        orderOfExecution += "test1 ";
    }

    @Test(groups = "second")
    public void coveredByTests(){
        orderOfExecution += "coveredByTests ";
    }

    @Test(groups = "third")
    public void thisOne(){
        orderOfExecution += "thisOne ";
    }

    @Test(groups = "first")
    public void wayToName(){
        orderOfExecution += "wayToName ";
    }

    @Test(groups = "second")
    public void treatThisRight(){
        orderOfExecution += "treatThisRight ";
    }

    @Test(groups = "third")
    public void originalName(){
        orderOfExecution += "originalName ";
    }

}
