package org.extendng;

import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static org.extendng.MethodInterceptorUtils.filterAndSortTests;
import static org.extendng.ReflectionUtils.shouldBeInvoked;

public class OrderByGroupsListener implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        return filterAndSortTests(methods,
                OrderByGroupsListener::shouldListen,
                OrderByGroupsListener::sortByGroupsOrByGroupOrder);
    }

    public static void sortByGroupsOrByGroupOrder(Map.Entry<Object, List<IMethodInstance>> entry){
        entry.getValue().sort(byGroupsOrByGroupOrder(entry.getKey()));
    }

    public static Comparator<IMethodInstance> byGroupsOrByGroupOrder(Object testClassInstance){
        val groupOrder = getGroupOrder(testClassInstance);

        return groupOrder.isPresent() ?
                comparingInt(method -> getGroupIndex(method, groupOrder.get())):
                comparingInt(method -> Arrays.hashCode(method.getMethod().getGroups()));
    }

    private static Optional<List<String>> getGroupOrder(Object testClassInstance){
        return Stream.of(testClassInstance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(GroupOrder.class))
                .findFirst()
                .map(method -> (List<String>) ReflectionUtils.invokeMethod(method, testClassInstance));
    }

    private static int getGroupIndex(IMethodInstance method, List<String> order) {
        return order.stream()
                .filter(item -> ArrayUtils.contains(method.getMethod().getGroups(), item))
                .findFirst()
                .map(item -> order.indexOf(item))
                .get();
    }

    private static boolean shouldListen(Map.Entry<Object, List<IMethodInstance>> testClass){
        return shouldBeInvoked(testClass.getKey().getClass(), OrderByGroupsListener.class);
    }

}
