import org.apache.commons.lang3.ArrayUtils;
import org.testng.*;
import org.testng.annotations.Listeners;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class MethodInGroupListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(iInvokedMethod.isTestMethod() && shouldBeInvoked(iInvokedMethod.getTestMethod().getRealClass())){
            Stream.of(getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new Method[]{}))
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
        if(iInvokedMethod.isTestMethod() && shouldBeInvoked(iInvokedMethod.getTestMethod().getRealClass())){
            Stream.of(getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new Method[]{}))
                    .filter(method -> method.isAnnotationPresent(AfterMethodInGroup.class))
                    .filter(method -> ArrayUtils.contains(iInvokedMethod.getTestMethod().getGroups(), method.getAnnotation(AfterMethodInGroup.class).value()))
                    .findFirst()
                    .ifPresent(method -> {
                        method.setAccessible(true);
                        invokeMethod(method, iTestResult);
                    });
        }
    }

    private boolean shouldBeInvoked(Class testClass){
        if(!testClass.isAnnotationPresent(Listeners.class))
            return false;
        if(asList(((Listeners) testClass.getAnnotation(Listeners.class)).value()).contains(MethodInGroupListener.class))
            return true;

        return shouldBeInvoked(testClass.getSuperclass());
    }

    private Method[] getAllMethods(Class testClass, Method[] methods){
        if(testClass.equals(Object.class))
            return methods;
        else
            return getAllMethods(testClass.getSuperclass(), ArrayUtils.addAll(methods, testClass.getDeclaredMethods()));
    }

    private void invokeMethod(Method method, ITestResult result){
        try {
            method.invoke(result.getInstance());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
