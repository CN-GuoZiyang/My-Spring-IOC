package top.guoziyang.main.service;

import top.guoziyang.springframework.annotation.Autowired;
import top.guoziyang.springframework.annotation.Component;
import top.guoziyang.springframework.annotation.Qualifier;

@Component(name = "wrapService")
public class WrapService {

    @Qualifier("helloWorldService")
    @Autowired
    private HelloWorldService helloWorldService;

    public void say() {
        helloWorldService.saySomething();
    }

}
