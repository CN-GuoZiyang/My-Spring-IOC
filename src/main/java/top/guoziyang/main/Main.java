package top.guoziyang.main;

import top.guoziyang.main.service.HelloWorldService;
import top.guoziyang.springframework.context.ApplicationContext;
import top.guoziyang.springframework.context.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean(HelloWorldService.class);
        helloWorldService.saySomething();
    }

}
