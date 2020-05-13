package top.guoziyang.main.service;

public class HelloWorldServiceImpl implements HelloWorldService {

    private String text;

    @Override
    public void saySomething() {
        System.out.println(text);
    }
}
