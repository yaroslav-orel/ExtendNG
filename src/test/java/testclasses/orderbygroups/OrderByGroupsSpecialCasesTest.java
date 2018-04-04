package testclasses.orderbygroups;

import org.extendng.OrderByGroupsListener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.stream.Stream;

@Listeners(OrderByGroupsListener.class)
public class OrderByGroupsSpecialCasesTest {

    @AfterClass
    public void actualTest(ITestContext result){
        long testsRunInClass = Stream.of(result.getAllTestMethods())
                .filter(iTestNGMethod -> iTestNGMethod.getTestClass().getRealClass().equals(OrderByGroupsSpecialCasesTest.class))
                .count();
        Assert.assertEquals(testsRunInClass, 2L, "intended tests were'nt run");
    }

    @Test
    public void withoutGroup(){ }

    @Test(groups = {"one", "two"})
    public void withMultipleGroups(){ }
}
