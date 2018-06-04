package org.extendng;

import lombok.val;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static org.extendng.MethodInterceptorUtils.filterAndSortTests;
import static org.extendng.ReflectionUtils.invokeMethod;
import static org.extendng.ReflectionUtils.shouldBeInvoked;

public class OrderByGroupsListener implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        return filterAndSortTests(methods,
                OrderByGroupsListener::shouldListen,
                OrderByGroupsListener::sortByGroupsOrByGroupOrder);
    }

    public static void sortByGroupsOrByGroupOrder(Map.Entry<Object, List<IMethodInstance>> entry){
        entry.getValue().sort(byByGroupOrderOrByGroupHash(entry.getKey()));
    }

    public static Comparator<IMethodInstance> byByGroupOrderOrByGroupHash(Object testClassInstance){
        val groupOrder = getGroupOrder(testClassInstance);

        return groupOrder.isPresent() ?
                comparingByGroupOrder(groupOrder.get()).thenComparingInt(OrderByGroupsListener::byGroupHash):
                comparingInt(OrderByGroupsListener::byGroupHash);
    }

    public static Comparator<IMethodInstance> comparingByGroupOrder(List<String> groupOrder){
        return comparingInt(method -> getGroupIndex(method, groupOrder));
    }

    public static int byGroupHash(IMethodInstance method){
        return Arrays.hashCode(method.getMethod().getGroups());
    }

    @SuppressWarnings("unchecked")
    private static Optional<List<String>> getGroupOrder(Object testClassInstance){
        if(testClassInstance.getClass().isAnnotationPresent(GroupOrder.class))
            return Optional.of(StreamEx.of(testClassInstance.getClass().getAnnotation(GroupOrder.class).groups()).toList());
        else
            return Stream.of(testClassInstance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(GroupOrder.class))
                .findFirst()
                .map(method -> (List<String>) invokeMethod(method, testClassInstance));
    }

    private static int getGroupIndex(IMethodInstance method, List<String> order) {
        return order.stream()
                .filter(item -> ArrayUtils.contains(method.getMethod().getGroups(), item))
                .findFirst()
                .map(order::indexOf)
                .orElse(-1);
    }

    private static boolean shouldListen(Map.Entry<Object, List<IMethodInstance>> testClass){
        return shouldBeInvoked(testClass.getKey().getClass(), OrderByGroupsListener.class);
    }

}
