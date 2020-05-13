package top.guoziyang.springframework.factory;

import top.guoziyang.springframework.BeanDefinition;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory {

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        Object bean = doCreateBean(beanDefinition);
        beanDefinition.setBean(bean);
        beanDefinitionMap.put(name, beanDefinition);
    }

    /**
     * 创建Bean实例
     * @param beanDefinition Bean定义对象
     * @return Bean实例对象
     * @throws Exception 可能出现的异常
     */
    abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;
}
