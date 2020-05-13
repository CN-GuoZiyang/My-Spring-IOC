package top.guoziyang.springframework.context;

import top.guoziyang.springframework.factory.BeanFactory;

public abstract class AbstractApplicationContext implements ApplicationContext {

    BeanFactory beanFactory;

    @Override
    public Object getBean(Class clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
