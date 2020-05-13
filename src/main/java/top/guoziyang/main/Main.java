package top.guoziyang.main;

import top.guoziyang.main.service.HelloWorldService;
import top.guoziyang.springframework.BeanDefinition;
import top.guoziyang.springframework.factory.AbstractBeanFactory;
import top.guoziyang.springframework.factory.AutowiredCapableBeanFactory;
import top.guoziyang.springframework.io.ResourceLoader;
import top.guoziyang.springframework.reader.XmlBeanDefinitionReader;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        beanDefinitionReader.loadBeanDefinitions("application.xml");
        AbstractBeanFactory beanFactory = new AutowiredCapableBeanFactory();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.saySomething();
    }

}
