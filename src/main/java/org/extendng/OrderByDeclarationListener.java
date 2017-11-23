package org.extendng;

import kiss.util.Reflect;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.extendng.ReflectionUtils.shouldBeInvoked;

public class OrderByDeclarationListener implements IMethodInterceptor {
    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        return methods.stream()
                .collect(groupingBy(IMethodInstance::getInstance))
                .entrySet().stream()
                    .flatMap(e ->
                        shouldBeInvoked(e.getKey().getClass(), OrderByDeclarationListener.class) ?
                                e.getValue().stream().sorted(byDeclaration()) :
                                e.getValue().stream())
                .collect(toList());
    }

    public Comparator<IMethodInstance> byDeclaration(){
        return new Comparator<IMethodInstance>() {

            List<Method> classMethodsInOrder;

            @Override
            public int compare(IMethodInstance o1, IMethodInstance o2) {
               if(classMethodsInOrder == null){
                   classMethodsInOrder = Stream.of(Reflect.getDeclaredMethodsInOrder(o1.getInstance().getClass())).collect(toList());
               }
                return Integer.compare(
                        classMethodsInOrder.indexOf(o1.getMethod().getConstructorOrMethod().getMethod()),
                        classMethodsInOrder.indexOf((o2.getMethod().getConstructorOrMethod().getMethod()))
                );
            }
        };
    }
}
