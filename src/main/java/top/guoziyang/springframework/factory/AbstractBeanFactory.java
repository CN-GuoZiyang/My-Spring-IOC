package top.guoziyang.springframework.factory;

import top.guoziyang.springframework.entity.BeanDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory {

    ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    ThreadLocal<HashMap<String,Object>> earylyBean = new ThreadLocal<HashMap<String, Object>>();

    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition == null) {
            throw new RuntimeException("Unable to find the bean of this name, please check!");
        }
        return getBeanFromBeanDefinition(beanDefinition);
    }


    @Override
    public Object getBean(Class clazz) throws Exception {
        BeanDefinition beanDefinition = null;
        for(Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class tmpClass = entry.getValue().getBeanClass();
            if(tmpClass == clazz || clazz.isAssignableFrom(tmpClass)) {
                beanDefinition = entry.getValue();
            }
        }
        if(beanDefinition == null) {
            throw new RuntimeException("Unable to find the bean of this class, please check!");
        }
        return getBeanFromBeanDefinition(beanDefinition);
    }

    private Object getBeanFromBeanDefinition(BeanDefinition beanDefinition) throws Exception {
        if(beanDefinition.isSingleton()){
            if(beanDefinition.getBean()!=null){
                return beanDefinition.getBean();
            }else{
                /**
                 * bug场景 ：当多个线程使用同一个BeanFactory，针对同一个单例的beanDefinition 调用getBean
                 * 如果没有锁，会创建多个对象
                 */
                synchronized (this){
                    if(beanDefinition.getBean()==null){
                        return doCreateBean(beanDefinition);
                    }else{
                        return beanDefinition.getBean();
                    }
                }
            }
        }else{
            //不是单例  先从earlyBean中找，如果没有就新创建
            HashMap<String, Object> earlyBeanMap = earylyBean.get();
            if(earlyBeanMap!=null && earlyBeanMap.containsKey(beanDefinition.getBeanClassName())){
                return earlyBeanMap.get(beanDefinition.getBeanClassName());
            }else{
                return doCreateBean(beanDefinition);
            }
        }

    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    /**
     * 创建Bean实例
     * @param beanDefinition Bean定义对象
     * @return Bean实例对象
     * @throws Exception 可能出现的异常
     */
    abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;

    public void populateBeans() throws Exception {
        for(Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            doCreateBean(entry.getValue());
        }
    }
}
