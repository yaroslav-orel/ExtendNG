package org.extendng;

import kiss.util.Reflect;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.testng.ITestNGListener;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@UtilityClass
public class ReflectionUtils {

    @SuppressWarnings("unchecked")
    static boolean shouldBeInvoked(Class testClass, Class<? extends ITestNGListener> listener){
        if(!testClass.isAnnotationPresent(Listeners.class))
            return false;
        if(asList(((Listeners) testClass.getAnnotation(Listeners.class)).value()).contains(listener))
            return true;

        return shouldBeInvoked(testClass.getSuperclass(), listener);
    }

    static List<Method> getClassMethodsInOrder(Class clazz, List<Method> methods){
        if(clazz.equals(Object.class))
            return methods;

        val methodsInOrder = asList(Reflect.getDeclaredMethodsInOrder(clazz));
        methods.addAll(0, methodsInOrder);
        return getClassMethodsInOrder(clazz.getSuperclass(), methods);
    }

    @SneakyThrows
    static Object invokeMethod(Method method, Object testInstance, Object... args){
        method.setAccessible(true);
        return method.invoke(testInstance, args);
    }

    public static List<Method> getAllMethods(Class testClass, List<Method> methods){
        if(testClass.equals(Object.class))
            return methods;

        val declaredMethods = Arrays.asList(testClass.getDeclaredMethods());
        methods.addAll(declaredMethods);
        return getAllMethods(testClass.getSuperclass(), methods);
    }
}
