package unit;

import lombok.val;
import org.extendng.ReflectionUtils;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class ReflectionUtilTest {

    @Test
    public void injectMethod(){
        val parameter = mock(Parameter.class);
        val testResult = mock(ITestResult.class);
        val testNGMethod = mock(ITestNGMethod.class);
        val constOrMethod = mock(ConstructorOrMethod.class);
        val method = mock(Method.class);
        doReturn(Method.class).when(parameter).getType();
        when(testResult.getMethod()).thenReturn(testNGMethod);
        when(testNGMethod.getConstructorOrMethod()).thenReturn(constOrMethod);
        when(constOrMethod.getMethod()).thenReturn(method);
        when(method.getName()).thenReturn("Method Injected");

        val injectedMethod = ReflectionUtils.toInjectable(parameter, testResult);
        String methodName = ((Method) injectedMethod).getName();

        assertThat(methodName).isEqualTo("Method Injected");
    }

    @Test
    public void injectITestResult(){
        val parameter = mock(Parameter.class);
        val testResult = mock(ITestResult.class);
        doReturn(ITestResult.class).when(parameter).getType();
        when(testResult.getName()).thenReturn("Result Injected");

        val injectedResult = ReflectionUtils.toInjectable(parameter, testResult);
        String methodName = ((ITestResult) injectedResult).getName();

        assertThat(methodName).isEqualTo("Result Injected");
    }

    @Test
    public void injectITestContext(){
        val parameter = mock(Parameter.class);
        val testResult = mock(ITestResult.class);
        val testContext = mock(ITestContext.class);
        doReturn(ITestContext.class).when(parameter).getType();
        when(testResult.getTestContext()).thenReturn(testContext);
        when(testContext.getName()).thenReturn("TestContext Injected");

        val injectedResult = ReflectionUtils.toInjectable(parameter, testResult);
        String methodName = ((ITestContext) injectedResult).getName();

        assertThat(methodName).isEqualTo("TestContext Injected");
    }

    @Test
    public void injectXmlTest(){
        val parameter = mock(Parameter.class);
        val testResult = mock(ITestResult.class);
        val testNGMethod = mock(ITestNGMethod.class);
        val xmlTest = mock(XmlTest.class);
        doReturn(XmlTest.class).when(parameter).getType();
        when(testResult.getMethod()).thenReturn(testNGMethod);
        when(testNGMethod.getXmlTest()).thenReturn(xmlTest);
        when(xmlTest.getName()).thenReturn("XmlTest injected");

        val injectedResult = ReflectionUtils.toInjectable(parameter, testResult);
        String methodName = ((XmlTest) injectedResult).getName();

        assertThat(methodName).isEqualTo("XmlTest injected");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void injectNotSupported(){
        val parameter = mock(Parameter.class);
        val testResult = mock(ITestResult.class);
        doReturn(Object[].class).when(parameter).getType();

        ReflectionUtils.toInjectable(parameter, testResult);
    }
}
