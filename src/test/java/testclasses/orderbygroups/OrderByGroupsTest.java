package testclasses.orderbygroups;

import org.extendng.OrderByGroupsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(OrderByGroupsListener.class)
public class OrderByGroupsTest {

    @Test(groups = "a")
    public void test1(){ }

    @Test(groups = "b")
    public void coveredByTests(){ }

    @Test(groups = "b")
    public void thisOne(){ }

    @Test(groups = "a")
    public void wayToName(){ }

    @Test(groups = "c")
    public void treatThisRight(){ }

    @Test(groups = "c")
    public void originalName(){ }

}
