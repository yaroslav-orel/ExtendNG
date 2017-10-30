package org.extendng;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.extendng.ReflectionUtils.shouldBeInvoked;

public class FastFailListener implements IInvokedMethodListener {

    private Map<Object, IInvokedMethod> failedClasses = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(shouldBeInvoked(testResult.getInstance().getClass(), FastFailListener.class) &&
           failedClasses.containsKey(testResult.getInstance()))
                throw new SkipException(String.format("Skipepd because of the failed test '%s'",
                        failedClasses.get(testResult.getInstance()).getTestMethod().getMethodName()));
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(shouldBeInvoked(testResult.getInstance().getClass(), FastFailListener.class) &&
           testResult.getThrowable() != null && !failedClasses.containsKey(testResult.getInstance()))
                failedClasses.put(testResult.getInstance(), method);
    }
}
