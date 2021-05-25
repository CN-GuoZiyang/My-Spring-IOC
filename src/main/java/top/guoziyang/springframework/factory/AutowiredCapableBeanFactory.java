package top.guoziyang.springframework.factory;

import top.guoziyang.springframework.entity.BeanDefinition;
import top.guoziyang.springframework.entity.BeanReference;
import top.guoziyang.springframework.entity.PropertyValue;

import java.lang.reflect.Field;
import java.util.HashMap;

public class AutowiredCapableBeanFactory extends AbstractBeanFactory {

    @Override
    Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        if(beanDefinition.isSingleton() && beanDefinition.getBean() != null) {
            return beanDefinition.getBean();
        }
        Object bean = beanDefinition.getBeanClass().newInstance();
        if(beanDefinition.isSingleton()) {
            //如果是单例，就算没有完成属性赋值，也可以存起来
            //这样可以直接避免出现循环依赖导致的死循环问题
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
                // 优先按照自定义名称匹配
                BeanDefinition refDefinition = beanDefinitionMap.get(beanReference.getName());
                if(refDefinition != null) {
                    value = createBeanFromBeanDefinition(bean, beanDefinition, beanReference, refDefinition);
                } else {
                    // 按照类型匹配，返回第一个匹配的
                    Class clazz = Class.forName(beanReference.getName());
                    for(BeanDefinition definition : beanDefinitionMap.values()) {
                        if(clazz.isAssignableFrom(definition.getBeanClass())) {
                            value = createBeanFromBeanDefinition(bean, beanDefinition, beanReference, refDefinition);
                        }
                    }
                }
            }
            if(value == null) {
                throw new RuntimeException("无法注入");
            }
            field.setAccessible(true);
            field.set(bean, value);
        }
        //如果自己在earlyBean里，就删除
        if(earylyBean.get()!=null && earylyBean.get().containsKey(beanDefinition.getBeanClassName())){
            earylyBean.get().remove(beanDefinition.getBeanClassName());
        }
    }

    private Object createBeanFromBeanDefinition(Object bean, BeanDefinition beanDefinition, BeanReference beanReference, BeanDefinition refDefinition) throws Exception {
        if(refDefinition.isSingleton()){
            //单例就直接拿
            if(refDefinition.getBean()!=null){
                return refDefinition.getBean();
            }else{
                return doCreateBean(refDefinition);
            }
        }else{
            //先把自己放入earlyBean
            if(earylyBean.get() == null){
                earylyBean.set(new HashMap<>());
            }
            if(!earylyBean.get().containsKey(beanDefinition.getBeanClassName())){
                earylyBean.get().put(beanDefinition.getBeanClassName(), bean);
            }
            //再尝试获取所需的Bean
            return getBean(beanReference.getName());
        }
    }


}
