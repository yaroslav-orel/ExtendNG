package testclasses.orderbygroups;

import org.extendng.GroupOrder;
import org.extendng.OrderByGroupsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;

@Listeners(OrderByGroupsListener.class)
public class OrderByGroupsSortedTest {

    @GroupOrder
    public List<String> groupOrder(){
        return asList("first", "second", "third");
    }

    @Test(groups = "first")
    public void test1(){ }

    @Test(groups = "second")
    public void coveredByTests(){ }

    @Test(groups = "third")
    public void thisOne(){ }

    @Test(groups = "first")
    public void wayToName(){ }

    @Test(groups = "second")
    public void treatThisRight(){ }

    @Test(groups = "third")
    public void originalName(){ }
}
