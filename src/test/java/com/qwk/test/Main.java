package com.qwk.test;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public void testArguments(String test, Integer myInteger, boolean booleanTest) {
    }

    public void test() {
    }

    public static void main(String[] args) {
        Method[] methods = Main.class.getDeclaredMethods();
        for (Method method : methods) {
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            System.out.println("方法：" + method.getName() + " 参数为：" + Arrays.asList(parameterNames));
        }
    }

}
