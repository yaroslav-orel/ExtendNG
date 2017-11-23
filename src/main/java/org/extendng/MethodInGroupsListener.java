package org.extendng;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.stream.Stream;

import static com.google.common.collect.ObjectArrays.concat;
import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;

public class MethodInGroupsListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(iInvokedMethod.isTestMethod() && ReflectionUtils.shouldBeInvoked(iInvokedMethod.getTestMethod().getRealClass(), MethodInGroupsListener.class)){
            Stream.of(getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new Method[]{}))
                    .filter(method -> method.isAnnotationPresent(BeforeMethodInGroups.class))
                    .filter(method -> intersects(iInvokedMethod.getTestMethod().getGroups(), method.getAnnotation(BeforeMethodInGroups.class).groups()))
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(BeforeMethodInGroups.class).priority()))
                    .forEach(method -> {
                        method.setAccessible(true);
                        ReflectionUtils.invokeMethod(method, iTestResult.getInstance());
                    });
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(iInvokedMethod.isTestMethod() && ReflectionUtils.shouldBeInvoked(iInvokedMethod.getTestMethod().getRealClass(), MethodInGroupsListener.class)){
            Stream.of(getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new Method[]{}))
                    .filter(method -> method.isAnnotationPresent(AfterMethodInGroups.class))
                    .filter(method -> intersects(iInvokedMethod.getTestMethod().getGroups(), method.getAnnotation(AfterMethodInGroups.class).groups()))
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(AfterMethodInGroups.class).priority()))
                    .forEach(method -> {
                        method.setAccessible(true);
                        ReflectionUtils.invokeMethod(method, iTestResult.getInstance());
                    });
        }
    }

    private boolean intersects(String[] testMethodGroups, String[] methodInGropGroups) {
        return !intersection(newHashSet(testMethodGroups), newHashSet(methodInGropGroups)).isEmpty();
    }

    private Method[] getAllMethods(Class testClass, Method[] methods){
        if(testClass.equals(Object.class))
            return methods;
        else
            return getAllMethods(testClass.getSuperclass(), concat(methods, testClass.getDeclaredMethods(), Method.class));
    }

}
