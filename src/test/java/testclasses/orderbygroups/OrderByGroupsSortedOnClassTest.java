package testclasses.orderbygroups;

import org.extendng.GroupOrder;
import org.extendng.OrderByGroupsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@GroupOrder(groups = {"first", "second", "third"})
@Listeners(OrderByGroupsListener.class)
public class OrderByGroupsSortedOnClassTest {

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
