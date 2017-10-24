import org.apache.commons.lang3.ArrayUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class MethodInGroupListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(iInvokedMethod.isTestMethod()){
            Stream.of(iInvokedMethod.getTestMethod().getTestClass().getRealClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(BeforeMethodInGroup.class))
                    .filter(method -> ArrayUtils.contains(iInvokedMethod.getTestMethod().getGroups(), method.getAnnotation(BeforeMethodInGroup.class).value()))
                    .findFirst()
                    .ifPresent(method -> {
                        method.setAccessible(true);
                        invokeMethod(method, iTestResult);
                    });
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(iInvokedMethod.isTestMethod()){
            Stream.of(iInvokedMethod.getTestMethod().getTestClass().getRealClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AfterMethodInGroup.class))
                    .filter(method -> ArrayUtils.contains(iInvokedMethod.getTestMethod().getGroups(), method.getAnnotation(AfterMethodInGroup.class).value()))
                    .findFirst()
                    .ifPresent(method -> {
                        method.setAccessible(true);
                        invokeMethod(method, iTestResult);
                    });
        }
    }

    private void invokeMethod(Method method, ITestResult result){
        try {
            method.invoke(result.getInstance());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
