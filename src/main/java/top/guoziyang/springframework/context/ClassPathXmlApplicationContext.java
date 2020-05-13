package top.guoziyang.springframework.context;

import top.guoziyang.springframework.entity.BeanDefinition;
import top.guoziyang.springframework.factory.AbstractBeanFactory;
import top.guoziyang.springframework.factory.AutowiredCapableBeanFactory;
import top.guoziyang.springframework.io.ResourceLoader;
import top.guoziyang.springframework.reader.XmlBeanDefinitionReader;

import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private final Object startupShutdownMonitor = new Object();
    private String location;

    public ClassPathXmlApplicationContext(String location) throws Exception {
        super();
        this.location = location;
        refresh();
    }

    public void refresh() throws Exception {
        synchronized (startupShutdownMonitor) {
            AbstractBeanFactory beanFactory = obtainBeanFactory();
            prepareBeanFactory(beanFactory);
            this.beanFactory = beanFactory;
        }
    }

    private void prepareBeanFactory(AbstractBeanFactory beanFactory) throws Exception {
        beanFactory.populateBeans();
    }

    private AbstractBeanFactory obtainBeanFactory() throws Exception {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        beanDefinitionReader.loadBeanDefinitions(location);
        AbstractBeanFactory beanFactory = new AutowiredCapableBeanFactory();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
        return beanFactory;
    }

}
