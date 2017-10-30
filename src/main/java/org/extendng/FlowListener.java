package org.extendng;

import kiss.util.Reflect;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static kiss.util.Reflect.getDeclaredMethodsInOrder;

public class FlowListener implements IMethodInterceptor {
    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        System.out.println("Methods before grouping");
        methods.stream().forEach(System.out::println);



        System.out.println("Methods after grouping");
        Map<Object, List<IMethodInstance>> groups =  methods.stream().collect(Collectors.groupingBy(IMethodInstance::getInstance));
        groups.forEach((k, v) -> v.stream().forEach(System.out::println));


        System.out.println("Methods in order they should be");
        Map<Object, List<Method>> classesWithMethodsInOrder = groups.entrySet().stream()
                .filter(map -> ReflectionUtils.shouldBeInvoked(map.getKey().getClass(), FlowListener.class))
                .collect(toMap(Map.Entry::getKey, e -> Stream.of(Reflect.getDeclaredMethodsInOrder(e.getKey().getClass()))
                        .filter(method -> method.isAnnotationPresent(Test.class))
                        .collect(toList()))
                );


        System.out.println("Methods after filtering");
        Map<Object, List<IMethodInstance>> classesToApply = groups.entrySet().stream()
                .filter(map -> ReflectionUtils.shouldBeInvoked(map.getKey().getClass(), FlowListener.class))
                .collect(toMap(e -> e.getKey(), e -> e.getValue()));
        classesToApply.forEach((k, v) -> v.stream().forEach(System.out::println));


        System.out.println("Methods after matching with launched");
        Map<Object, List<IMethodInstance>> filteredTests = classesWithMethodsInOrder.entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(method -> classesToApply.get(e.getKey()).stream()
                                        .filter(test -> test.getMethod().getConstructorOrMethod().getMethod().equals(method))
                                        .findFirst().get())
                                .collect(toList())
                        )
                );
        filteredTests.forEach((k, v) -> v.stream().forEach(System.out::println));

        groups.putAll(filteredTests);

        System.out.println("Finally");
        List<IMethodInstance> finallyOrdered = groups.entrySet().stream().flatMap(e-> e.getValue().stream()).collect(toList());
        finallyOrdered.stream().forEach(System.out::println);
        return finallyOrdered;
    }
}
