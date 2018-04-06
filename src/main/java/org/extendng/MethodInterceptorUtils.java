package org.extendng;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.testng.IMethodInstance;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class MethodInterceptorUtils {

    public static List<IMethodInstance> filterAndSortTests(
            List<IMethodInstance> methods,
            Predicate<Map.Entry<Object, List<IMethodInstance>>> predicate,
            Consumer<Map.Entry<Object, List<IMethodInstance>>> action) {
        val testClassesMap = MethodInterceptorUtils.groupTestsByTestClasses(methods);

        testClassesMap.entrySet().stream()
                .filter(predicate)
                .forEach(action);

        return flattenTestClasses(testClassesMap);
    }

    public static Map<Object, List<IMethodInstance>> groupTestsByTestClasses(List<IMethodInstance> methods){
        return methods.stream().collect(groupingBy(IMethodInstance::getInstance, LinkedHashMap::new, toList()));
    }

    public static List<IMethodInstance> flattenTestClasses(Map<Object, List<IMethodInstance>> testClassesMap) {
        return testClassesMap.entrySet().stream().flatMap(e -> e.getValue().stream()).collect(toList());
    }
}
