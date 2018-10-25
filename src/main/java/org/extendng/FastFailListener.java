package org.extendng;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.extendng.ReflectionUtils.shouldBeInvoked;

public class FastFailListener implements IInvokedMethodListener {

    private Map<Object, IInvokedMethod> failedClasses = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(shouldListen(testResult) && previouslyClassFailed(testResult))
                skipTest(testResult);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(shouldListen(testResult) && currentTestFailed(testResult))
                markTestClassAsFailed(testResult, method);
    }


    private boolean shouldListen(ITestResult testResult){
        return shouldBeInvoked(testResult.getInstance().getClass(), FastFailListener.class);
    }

    private boolean currentTestFailed(ITestResult testResult){
        return testResult.getThrowable() != null;
    }

    private boolean previouslyClassFailed(ITestResult testResult){
        return failedClasses.containsKey(testResult.getInstance());
    }

    private String getFailedMethodName(ITestResult testResult){
        return failedClasses.get(testResult.getInstance()).getTestMethod().getMethodName();
    }

    private void skipTest(ITestResult testResult){
        testResult.setStatus(ITestResult.SKIP);
        throw new SkipException(format("Skipped because of the failed test '%s'", getFailedMethodName(testResult)));
    }

    private void markTestClassAsFailed(ITestResult testResult, IInvokedMethod method){
        failedClasses.put(testResult.getInstance(), method);
    }
}
