package testclasses.methodingroups;

import org.extendng.AfterMethodInGroups;
import org.extendng.BeforeMethodInGroups;
import org.extendng.MethodInGroupsListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;

@Listeners(MethodInGroupsListener.class)
public class InjectionTest {

    @BeforeMethodInGroups(groups = "positive")
    public void beforeITestresult(ITestResult result){ }

    @BeforeMethodInGroups(groups = "positive")
    public void beforeITestContext(ITestContext context){ }

    @BeforeMethodInGroups(groups = "positive")
    public void beforeXmlTest(XmlTest xmlTest){ }

    @BeforeMethodInGroups(groups = "positive")
    public void beforeMethod(Method method){ }

    @AfterMethodInGroups(groups = "positive")
    public void afterITestresult(ITestResult result){ }

    @AfterMethodInGroups(groups = "positive")
    public void afterITestContext(ITestContext context){ }

    @AfterMethodInGroups(groups = "positive")
    public void afterXmlTest(XmlTest xmlTest){ }

    @AfterMethodInGroups(groups = "positive")
    public void afterMethod(Method method){ }


    @Test(groups = "positive")
    public void injectionPositive(){ }

}
