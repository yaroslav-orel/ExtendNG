package org.extendng;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;
import static org.extendng.MethodInterceptorUtils.filterAndSortTests;
import static org.extendng.ReflectionUtils.getClassMethodsInOrder;
import static org.extendng.ReflectionUtils.shouldBeInvoked;

public class OrderByDeclarationListener implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        return filterAndSortTests(methods,
                OrderByDeclarationListener::shouldListen,
                OrderByDeclarationListener::sortByDeclaration);
    }

    public static boolean shouldListen(Map.Entry<Object, List<IMethodInstance>> entry){
        return shouldBeInvoked(entry.getKey().getClass(), OrderByDeclarationListener.class);
    }

    public static void sortByDeclaration(Map.Entry<Object, List<IMethodInstance>> entry){
        entry.getValue().sort(byDeclaration(entry.getKey()));
    }

    public static Comparator<IMethodInstance> byDeclaration(Object testClassInstance){
        return byDeclaration(getClassMethodsInOrder(testClassInstance.getClass(), new ArrayList<>()));
    }

    public static Comparator<IMethodInstance> byDeclaration(List<Method> classMethodsInOrder){
        return comparingInt(o -> classMethodsInOrder.indexOf(o.getMethod().getConstructorOrMethod().getMethod()));
    }
}
