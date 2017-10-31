package org.extendng;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.List;
import java.util.Map.Entry;
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
                .flatMap(e -> e.getValue().stream());
    }
    
}
