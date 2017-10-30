package org.extendng;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastFailListener implements IInvokedMethodListener {

    private List<Object> failedClasses = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(ReflectionUtils.shouldBeInvoked(testResult.getInstance().getClass(), FastFailListener.class)){
            if(failedClasses.contains(testResult.getInstance())){
                throw new SkipException("One of the previous methods failed");
            }
        }

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(ReflectionUtils.shouldBeInvoked(testResult.getInstance().getClass(), FastFailListener.class)){
            if(testResult.getThrowable() != null)
                failedClasses.add(testResult.getInstance());
        }
    }
}
