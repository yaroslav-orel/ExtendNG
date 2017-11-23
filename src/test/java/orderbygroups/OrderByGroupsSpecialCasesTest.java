package orderbygroups;

import org.extendng.OrderByGroups;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(OrderByGroups.class)
public class OrderByGroupsSpecialCasesTest {

    @AfterClass
    public void actualTest(ITestContext context){
        Assert.assertEquals(context.getAllTestMethods().length, 2, "intended tests were'nt run");
    }

    @Test
    public void withoutGroup(){ }

    @Test(groups = {"one", "two"})
    public void withMultipleGroups(){ }
}
