package com.hsbc.fw.portal.scheduler.schedulerframework;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTaskPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Do nothing before initialization
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ScheduledTask) {
            // You can log or track the initialization if necessary
            System.out.println("Initialized ScheduledTask: " + beanName);
        }
        return bean;
    }
}
