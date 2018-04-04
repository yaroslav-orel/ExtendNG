package testclasses.orderbygroups;

import org.extendng.GroupOrder;
import org.extendng.OrderByGroupsListener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(OrderByGroupsListener.class)
public class OrderByGroupsSortedTest {

    String orderOfExecution = "";

    @GroupOrder
    public String[] groupOrder(){
        return new String[]{"first", "second", "third"};
    }

    @AfterClass
    public void finalAssert(){
        Assert.assertEquals(orderOfExecution, "test1 wayToName coveredByTests treatThisRight originalName thisOne ");
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
