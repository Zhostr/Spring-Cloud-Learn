package com.zst.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.SocketException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @description: JDK 动态代理以及可能会出现的异常情况
 * @author: Zhoust
 * @date: 2019/10/28 下午7:48
 * @version: V1.0
 */
@Slf4j
public class JDKDynamicProxyTest {

    interface CustomInterface {

        void say();

        /**
         * 这里的返回值，如果是包装类则不会有空指针异常
         * 如果是基本数据类型，可能会产生 NPE（InvocationHandler#invoke 方法返回 null 的情况下）
         * @param num
         * @return
         */
        Integer getPow(Integer[] num);
    }

    @Slf4j
    static class RealSubject implements CustomInterface {

        @Override
        public void say() {
            log.info("I'm real subject，这是我的 say() 方法.");
        }

        @Override
        public Integer getPow(Integer[] num) {
            log.info("I'm real subject，这是我 getPow() 方法.");
            Optional<Integer> reduce = Stream.of(num).map(i -> i * i).reduce(Integer::sum);
            log.info("reduce.get()= {}", reduce.get());
            say();
            return reduce.get();
        }
    }

    @Slf4j
    static class DynamicProxy implements InvocationHandler {

        /** 真正的对象 **/
        private Object instance;

        DynamicProxy(Object o) {
            instance = o;
        }

        /**
         * 空指针异常：如果这个方法的返回值是 null，而接口的返回类型是基本数据类型，就会产生 NPE
         * ClassCastException：
         * UndeclaredThrowableException：如果该方法抛出了可检查性异常，就会抛出 UndeclaredThrowableException 包着这个可检查性异常
         * @param proxy     最终生成的代理对象（就是 Proxy#newProxyInstance 方法生成的对象）
         * @param method    被代理对象的某个具体方法
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("proxy 类名为：{}", proxy.getClass().getName());
            log.info("----------->进入代理类的 invoke 方法<--------------");
            Object invoke = method.invoke(instance, args);
            log.info("----------->method.invoke()方法结束<--------------");
            if (true) {
                //这里直接抛出检查性异常，会被包装成 UndeclaredThrowableException
                throw new SocketException("dsadsa");
            }
            return null;//这里返回 null，null 在转化为 int 类型时，会报空指针异常
        }
    }

    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Integer [] intList = new Integer[]{1,2,3,4,5,6,7,8};
        InvocationHandler handler = new DynamicProxy(realSubject);

        /**
         * Returns an instance of a proxy class for the specified interfaces
         * that dispatches method invocations to the specified invocation
         * handler.
         */
        CustomInterface proxyInstance  = (CustomInterface) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(),//代理对象实现的接口列表
                handler);
        try {
            Integer pow = proxyInstance.getPow(intList);
            proxyInstance.say();
        }catch (Exception e) {
            if (e instanceof UndeclaredThrowableException) {
                log.error("未声明的可检查性异常", ((UndeclaredThrowableException) e).getUndeclaredThrowable());
            }
            else {
                log.error("some ", e);
            }
        }

        log.info("proxyInstance.getClass().getName() = {}", proxyInstance.getClass().getName());
    }

}