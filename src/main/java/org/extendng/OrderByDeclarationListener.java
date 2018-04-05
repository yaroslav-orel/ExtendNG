package org.extendng;

import lombok.val;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.extendng.ReflectionUtils.getClassMethodsInOrder;
import static org.extendng.ReflectionUtils.shouldBeInvoked;

public class OrderByDeclarationListener implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        val testClassesMap = groupTestsByTestClasses(methods);
        orderTestsByDeclarationInsideTestClassesWithListener(testClassesMap);

        return flattenTestClasses(testClassesMap);
    }

    private Map<Object, List<IMethodInstance>> groupTestsByTestClasses(List<IMethodInstance> methods){
        return methods.stream().collect(groupingBy(method -> method.getInstance(), LinkedHashMap::new, toList()));
    }

    private List<IMethodInstance> flattenTestClasses(Map<Object, List<IMethodInstance>> testClassesMap) {
        return testClassesMap.entrySet().stream().flatMap(e -> e.getValue().stream()).collect(toList());
    }

    private void orderTestsByDeclarationInsideTestClassesWithListener(Map<Object,List<IMethodInstance>> testClassesMap) {
        testClassesMap.entrySet().stream()
                .filter(e -> shouldBeInvoked(e.getKey().getClass(), OrderByDeclarationListener.class))
                .forEach(entry -> entry.getValue().sort(byDeclaration(entry.getKey().getClass())));
    }

    private Comparator<IMethodInstance> byDeclaration(Class testClass){
        return byDeclaration(getClassMethodsInOrder(testClass, new ArrayList<>()));
    }

    private Comparator<IMethodInstance> byDeclaration(List<Method> classMethodsInOrder){
        return comparingInt(o -> classMethodsInOrder.indexOf(o.getMethod().getConstructorOrMethod().getMethod()));
    }
}
