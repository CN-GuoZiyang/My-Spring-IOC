package top.guoziyang.main;

import top.guoziyang.main.service.HelloWorldService;
import top.guoziyang.main.service.WrapService;
import top.guoziyang.springframework.context.ApplicationContext;
import top.guoziyang.springframework.context.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        WrapService wrapService = (WrapService) applicationContext.getBean("wrapService");
        wrapService.say();
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        HelloWorldService helloWorldService2 = (HelloWorldService) applicationContext.getBean("helloWorldService");
        System.out.println(helloWorldService == wrapService.helloWorldService);
        System.out.println(helloWorldService2 == wrapService.helloWorldService);
        System.out.println(helloWorldService == helloWorldService2);
    }

}
