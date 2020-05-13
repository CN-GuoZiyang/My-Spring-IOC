package top.guoziyang.springframework.context;

/**
 * 应用程序上下文接口
 *
 * @author ziyang
 */
public interface ApplicationContext {

    Object getBean(Class clazz);

    Object getBean(String beanName);

}
