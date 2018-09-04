package org.extendng;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.nonNull;
import static org.extendng.ReflectionUtils.*;

public class MethodInGroupsListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(isTestEligibleForWrapping(iInvokedMethod)){
            getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new ArrayList<>()).stream()
                    .filter(MethodInGroupsListener::hasBeforeMethodAnnotation)
                    .filter(method -> hasTheSameBeforeMethodGroupAsTest(method, iInvokedMethod))
                    .sorted(byBeforeMethodPriority())
                    .forEach(method -> invokeMethodWithInjection(method, iTestResult));
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(isTestEligibleForWrapping(iInvokedMethod)){
            getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new ArrayList<>()).stream()
                    .filter(MethodInGroupsListener::hasAfterMethodAnnotation)
                    .filter(method -> hasTheSameAfterMethodGroupAsTest(method, iInvokedMethod))
                    .sorted(byAfterMethodPriority())
                    .forEach(method -> invokeMethodWithInjection(method, iTestResult));
        }
    }

    private static boolean isTestEligibleForWrapping(IInvokedMethod invokedMethod){
        return invokedMethod.isTestMethod() &&
                shouldListen(invokedMethod) &&
                nonNull(invokedMethod.getTestResult().getInstance());
    }

    private static boolean shouldListen(IInvokedMethod method){
        return shouldBeInvoked(method.getTestMethod().getRealClass(), MethodInGroupsListener.class);
    }

    private static boolean hasBeforeMethodAnnotation(Method method){
        return method.isAnnotationPresent(BeforeMethodInGroups.class);
    }

    private static boolean hasAfterMethodAnnotation(Method method){
        return method.isAnnotationPresent(AfterMethodInGroups.class);
    }

    private static boolean hasTheSameBeforeMethodGroupAsTest(Method method, IInvokedMethod test){
        return intersects(test.getTestMethod().getGroups(), method.getAnnotation(BeforeMethodInGroups.class).groups());
    }

    private static boolean hasTheSameAfterMethodGroupAsTest(Method method, IInvokedMethod test){
        return intersects(test.getTestMethod().getGroups(), method.getAnnotation(AfterMethodInGroups.class).groups());
    }

    private static Comparator<Method> byBeforeMethodPriority(){
        return comparingInt(method -> method.getAnnotation(BeforeMethodInGroups.class).priority());
    }

    private static Comparator<Method> byAfterMethodPriority(){
        return comparingInt(method -> method.getAnnotation(AfterMethodInGroups.class).priority());
    }

    private static boolean intersects(String[] testMethodGroups, String[] methodInGropGroups) {
        return !intersection(newHashSet(testMethodGroups), newHashSet(methodInGropGroups)).isEmpty();
    }



}
