package org.extendng;

import org.testng.ITestNGListener;
import org.testng.annotations.Listeners;

import static java.util.Arrays.asList;

public class ReflectionUtils {

    @SuppressWarnings("unchecked")
    static boolean shouldBeInvoked(Class testClass, Class<? extends ITestNGListener> listener){
        if(!testClass.isAnnotationPresent(Listeners.class))
            return false;
        if(asList(((Listeners) testClass.getAnnotation(Listeners.class)).value()).contains(listener))
            return true;

        return shouldBeInvoked(testClass.getSuperclass(), listener);
    }
}
