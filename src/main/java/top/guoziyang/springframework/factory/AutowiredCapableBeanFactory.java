package top.guoziyang.springframework.factory;

import top.guoziyang.springframework.entity.BeanDefinition;
import top.guoziyang.springframework.entity.BeanReference;
import top.guoziyang.springframework.entity.PropertyValue;

import java.lang.reflect.Field;

public class AutowiredCapableBeanFactory extends AbstractBeanFactory {

    @Override
    Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        if(beanDefinition.isSingleton() && beanDefinition.getBean() != null) {
            return beanDefinition.getBean();
        }
        Object bean = beanDefinition.getBeanClass().newInstance();
        if(beanDefinition.isSingleton()) {
            beanDefinition.setBean(bean);
        }
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
            Object value = propertyValue.getValue();
            if(value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) propertyValue.getValue();
                BeanDefinition refDefinition = beanDefinitionMap.get(beanReference.getName());
                if(refDefinition.getBean() == null) {
                    value = doCreateBean(refDefinition);
                }
            }
            field.setAccessible(true);
            field.set(bean, value);
        }
    }


}
