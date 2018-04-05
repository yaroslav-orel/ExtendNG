package org.extendng;

import kiss.util.Reflect;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.testng.ITestNGListener;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
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

        List<Method> methodsInOrder = asList(Reflect.getDeclaredMethodsInOrder(clazz));
        methods.addAll(0, methodsInOrder);
        return getClassMethodsInOrder(clazz.getSuperclass(), methods);
    }

    @SneakyThrows
    static Object invokeMethod(Method method, Object testInstance){
        return method.invoke(testInstance);
    }
}
