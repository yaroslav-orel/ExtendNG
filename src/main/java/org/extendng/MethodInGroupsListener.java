package org.extendng;

import lombok.val;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Comparator.comparingInt;
import static org.extendng.ReflectionUtils.*;

public class MethodInGroupsListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(shouldListen(iInvokedMethod)){
            getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new ArrayList<>()).stream()
                    .filter(MethodInGroupsListener::hasBeforeMethodAnnotation)
                    .filter(method -> hasTheSameBeforeMethodGroupAsTest(method, iInvokedMethod))
                    .sorted(byBeforeMethodPriority())
                    .forEach(method -> invokeMethodWithInjection(method, iTestResult));
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if(shouldListen(iInvokedMethod)){
            getAllMethods(iInvokedMethod.getTestMethod().getRealClass(), new ArrayList<>()).stream()
                    .filter(MethodInGroupsListener::hasAfterMethodAnnotation)
                    .filter(method -> hasTheSameAfterMethodGroupAsTest(method, iInvokedMethod))
                    .sorted(byAfterMethodPriority())
                    .forEach(method -> invokeMethodWithInjection(method, iTestResult));
        }
    }

    private static boolean shouldListen(IInvokedMethod method){
        return method.isTestMethod() && shouldBeInvoked(method.getTestMethod().getRealClass(), MethodInGroupsListener.class);
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

    private static Object invokeMethodWithInjection(Method method, ITestResult result){
        val params = method.getParameters();
        if(params.length == 0){
            return invokeMethod(method, result.getInstance());
        }
        val injectables = Stream.of(params).map(param -> toInjectable(param, result)).toArray();
        return invokeMethod(method, result.getInstance(), injectables);
    }

    private static Object toInjectable(Parameter param, ITestResult result){
        val type = param.getType();

        if(type.equals(ITestResult.class))
            return result;
        else if(type.equals(ITestContext.class))
            return result.getTestContext();
        else if(type.equals(XmlTest.class))
            return result.getMethod().getXmlTest();
        else if(type.equals(Method.class))
            return result.getMethod().getConstructorOrMethod().getMethod();
        else
            throw new IllegalArgumentException("Parameter of type " + type + " is not supported");
    }

}
