package org.extendng;

import org.testng.ITestNGListener;
import org.testng.annotations.Listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Arrays.asList;

public class ReflectionUtils {

    private ReflectionUtils(){}

    @SuppressWarnings("unchecked")
    static boolean shouldBeInvoked(Class testClass, Class<? extends ITestNGListener> listener){
        if(!testClass.isAnnotationPresent(Listeners.class))
            return false;
        if(asList(((Listeners) testClass.getAnnotation(Listeners.class)).value()).contains(listener))
            return true;

        return shouldBeInvoked(testClass.getSuperclass(), listener);
    }

    static Object invokeMethod(Method method, Object testInstance){
        try {
            return method.invoke(testInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
