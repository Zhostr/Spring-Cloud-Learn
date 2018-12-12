package com.zst;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description: 事件监听模式
 * @author: Zhoust
 * @date: 2018/12/12 下午1:44
 * @version: V1.0
 */
public class EventListenerMode {

    /** 自定义事件 **/
    public static class CustomEvent extends ApplicationEvent {
        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public CustomEvent(Object source) {
            super(source);
        }
    }

    /** 自定义事件监听器 **/
    public static class CustomEventListener implements ApplicationListener<CustomEvent> {
        @Override
        public void onApplicationEvent(CustomEvent event) {
            Object source = event.getSource();
            System.out.println(source);
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.addApplicationListener(new CustomEventListener());
        configApplicationContext.refresh();

        //保证 publishEvent/refresh 和 addApplicationListener 的顺序
        configApplicationContext.publishEvent(new CustomEvent("我我我哦我问问"));
    }

}