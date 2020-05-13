package top.guoziyang.springframework.factory;

import top.guoziyang.springframework.entity.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory {

    ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    @Override
    public Object getBean(Class clazz) {
        for(Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class tmpClass = entry.getValue().getBeanClass();
            if(tmpClass == clazz || clazz.isAssignableFrom(tmpClass)) {
                return entry.getValue().getBean();
            }
        }
        return null;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    /**
     * 创建Bean实例
     * @param beanDefinition Bean定义对象
     * @return Bean实例对象
     * @throws Exception 可能出现的异常
     */
    abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;

    public void populateBeans() throws Exception {
        for(Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            doCreateBean(entry.getValue());
        }
    }
}
