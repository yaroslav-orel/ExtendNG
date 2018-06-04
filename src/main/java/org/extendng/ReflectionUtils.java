package org.extendng;

import kiss.util.Reflect;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@UtilityClass
public class ReflectionUtils {

    @SuppressWarnings("unchecked")
    public static boolean shouldBeInvoked(Class testClass, Class<? extends ITestNGListener> listener){
        if(!testClass.isAnnotationPresent(Listeners.class))
            return false;
        if(asList(((Listeners) testClass.getAnnotation(Listeners.class)).value()).contains(listener))
            return true;

        return shouldBeInvoked(testClass.getSuperclass(), listener);
    }

    public static List<Method> getClassMethodsInOrder(Class clazz, List<Method> methods){
        if(clazz.equals(Object.class))
            return methods;

        val methodsInOrder = asList(Reflect.getDeclaredMethodsInOrder(clazz));
        methods.addAll(0, methodsInOrder);
        return getClassMethodsInOrder(clazz.getSuperclass(), methods);
    }

    public static Object invokeMethodWithInjection(Method method, ITestResult result){
        val params = method.getParameters();
        if(params.length == 0){
            return invokeMethod(method, result.getInstance());
        }
        val injectables = Stream.of(params).map(param -> toInjectable(param, result)).toArray();
        return invokeMethod(method, result.getInstance(), injectables);
    }

    public static Object toInjectable(Parameter param, ITestResult result){
        val type = param.getType();

        if(type == ITestResult.class)
            return result;
        else if(type == ITestContext.class)
            return result.getTestContext();
        else if(type == XmlTest.class)
            return result.getMethod().getXmlTest();
        else if(type == Method.class)
            return result.getMethod().getConstructorOrMethod().getMethod();
        else
            throw new IllegalArgumentException("Parameter of type " + type + " is not supported");
    }

    @SneakyThrows
    public static Object invokeMethod(Method method, Object testInstance, Object... args){
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
