package org.extendng;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.extendng.ReflectionUtils.shouldBeInvoked;

public class OrderByGroups implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        return methods.stream()
                .collect(groupingBy(IMethodInstance::getInstance))
                    .entrySet().stream()
                        .flatMap(OrderByGroups::orderByGroupsIfListenerIsApplied)
                            .collect(toList());
    }

    private static Stream<? extends IMethodInstance> orderByGroupsIfListenerIsApplied(Entry<Object, List<IMethodInstance>> entry) {
        if (shouldBeInvoked(entry.getKey().getClass(), OrderByGroups.class)) {
            return orderByGroups(entry);
        }
        else return entry.getValue().stream();
    }

    private static Stream<IMethodInstance> orderByGroups(Entry<Object, List<IMethodInstance>> entry) {
        return entry.getValue().stream()
                .collect(groupingBy(method -> asList(method.getMethod().getGroups())))
                .entrySet().stream()
                .sorted(byGroupOrder())
                .flatMap(e -> e.getValue().stream());
    }

    public static Comparator<Entry<List<String>, List<IMethodInstance>>> byGroupOrder(){
        return (o1, o2) -> getGroupOrder(o1)
                .map(order -> Integer.compare(getGroupIndex(o1, order), getGroupIndex(o2, order)))
                .orElse(0);
    }

    private static int getGroupIndex(Entry<List<String>, List<IMethodInstance>> o1, String[] order) {
        return Stream.of(order)
                .filter(item -> o1.getKey().contains(item))
                .findFirst()
                .map(item -> asList(order).indexOf(item))
                .orElse(-1);
    }

    private static Optional<String[]> getGroupOrder(Entry<List<String>, List<IMethodInstance>> comparisonInstance){
        Object testClassInstance = comparisonInstance.getValue().get(0).getInstance();

        return Stream.of(testClassInstance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(GroupOrder.class))
                .findFirst()
                .map(method -> (String[]) ReflectionUtils.invokeMethod(method, testClassInstance));
    }
    
}
