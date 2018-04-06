package testclasses.orderbygroups;

import org.extendng.GroupOrder;
import org.extendng.OrderByGroupsListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;

@Listeners(OrderByGroupsListener.class)
public class OrderByGroupsSpecialCasesTest {

    @GroupOrder
    public List<String> groupOrder(){ return asList("one", "three");}

    @Test
    public void zeroGroups(){ }

    @Test
    public void anotherTestWithoutGroups(){ }

    @Test(groups = {"two", "four"})
    public void betThisWorks(){ }

    @Test(groups = {"one", "two"})
    public void withMultipleGroups(){ }

    @Test(groups = {"two", "four"})
    public void orderMeMan(){ }

    @Test(groups = "three")
    public void actuallyWithOneGroup(){ }
}
