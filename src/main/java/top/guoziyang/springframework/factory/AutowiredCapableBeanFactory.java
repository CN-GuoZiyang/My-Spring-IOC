package top.guoziyang.springframework.factory;

import top.guoziyang.springframework.entity.BeanDefinition;
import top.guoziyang.springframework.entity.PropertyValue;

import java.lang.reflect.Field;

public class AutowiredCapableBeanFactory extends AbstractBeanFactory {

    @Override
    Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = beanDefinition.getBeanClass().newInstance();
        beanDefinition.setBean(bean);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    /**
     * 为新创建了bean注入属性
     * @param bean 待注入属性的bean
     * @param beanDefinition bean的定义
     * @throws Exception 反射异常
     */
    void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        for(PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
            Field field = bean.getClass().getDeclaredField(propertyValue.getName());
            field.setAccessible(true);
            field.set(bean, propertyValue.getValue());
        }
    }


}
