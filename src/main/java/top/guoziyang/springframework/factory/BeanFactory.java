package top.guoziyang.springframework.factory;

import top.guoziyang.springframework.BeanDefinition;

/**
 * Bean工厂接口
 *
 * @author ziyang
 */
public interface BeanFactory {

    /**
     * 从容器中获取bean
     * @param name bean的名字
     * @return bean实例对象
     */
    Object getBean(String name);

    /**
     * 向工厂中注册bean定义
     * @param name bean的名字
     * @param beanDefinition bean的定义对象
     * @throws Exception 可能出现的异常
     */
    void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception;

}
