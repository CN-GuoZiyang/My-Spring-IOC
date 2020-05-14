package top.guoziyang.main.service;

import top.guoziyang.springframework.annotation.Autowired;
import top.guoziyang.springframework.annotation.Component;

@Component(name = "wrapService")
public class WrapService {

    @Autowired
    private HelloWorldService helloWorldService;

    public void say() {
        helloWorldService.saySomething();
    }

}
