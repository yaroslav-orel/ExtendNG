package org.extendng;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.stream.Stream;

import static com.google.common.collect.ObjectArrays.concat;
import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

public class MethodInGroupListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(iInvokedMethod.isTestMethod() && shouldBeInvoked(iInvokedMethod.getTestMethod().getRealClass())){
            Stream.of(getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new Method[]{}))
                    .filter(method -> method.isAnnotationPresent(BeforeMethodInGroup.class))
                    .filter(method -> intersection(
                            newHashSet(iInvokedMethod.getTestMethod().getGroups()),
                            newHashSet(method.getAnnotation(BeforeMethodInGroup.class).groups())
                    ).isEmpty())
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(BeforeMethodInGroup.class).priority()))
                    .forEach(method -> {
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
                    .filter(method -> intersection(
                            newHashSet(iInvokedMethod.getTestMethod().getGroups()),
                            newHashSet(method.getAnnotation(AfterMethodInGroup.class).groups())
                    ).isEmpty())
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(AfterMethodInGroup.class).priority()))
                    .forEach(method -> {
                        method.setAccessible(true);
                        invokeMethod(method, iTestResult);
                    });
        }
    }

    @SuppressWarnings("unchecked")
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
            return getAllMethods(testClass.getSuperclass(), concat(methods, testClass.getDeclaredMethods(), Method.class));
    }

    private void invokeMethod(Method method, ITestResult result){
        try {
            method.invoke(result.getInstance());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
