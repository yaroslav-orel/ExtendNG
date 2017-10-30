package org.extendng;

import kiss.util.Reflect;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class OrderByDeclarationListener implements IMethodInterceptor {
    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        Map<Object, List<IMethodInstance>> groups =  methods.stream().collect(Collectors.groupingBy(IMethodInstance::getInstance));

        Map<Object, List<Method>> classesWithMethodsInOrder = groups.entrySet().stream()
                .filter(map -> ReflectionUtils.shouldBeInvoked(map.getKey().getClass(), OrderByDeclarationListener.class))
                .collect(toMap(Map.Entry::getKey, e -> Stream.of(Reflect.getDeclaredMethodsInOrder(e.getKey().getClass()))
                        .filter(method -> method.isAnnotationPresent(Test.class))
                        .collect(toList()))
                );

        Map<Object, List<IMethodInstance>> filteredTests = classesWithMethodsInOrder.entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(method -> groups.get(e.getKey()).stream()
                                        .filter(test -> test.getMethod().getConstructorOrMethod().getMethod().equals(method))
                                        .findFirst().get())
                                .collect(toList())
                        )
                );

        groups.putAll(filteredTests);

        return groups.entrySet().stream().flatMap(e-> e.getValue().stream()).collect(toList());
    }
}
