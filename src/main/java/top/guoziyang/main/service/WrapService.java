package top.guoziyang.main.service;

public class WrapService {

    private HelloWorldService helloWorldService;

    public void say() {
        helloWorldService.saySomething();
    }

}
