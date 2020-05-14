package top.guoziyang.main.service;

import top.guoziyang.springframework.annotation.Component;
import top.guoziyang.springframework.annotation.Scope;
import top.guoziyang.springframework.annotation.Value;

@Component(name = "helloWorldService")
@Scope("prototype")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Value("Hello world")
    private String text;

    @Override
    public void saySomething() {
        System.out.println(text);
    }

    @Override
    public String getString() {
        return text;
    }
}
