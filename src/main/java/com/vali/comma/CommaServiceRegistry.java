package com.vali.comma;

import com.period.server.PeriodServerUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.remoting.caucho.HessianServiceExporter;

import java.util.Map;

/**
 * Created by vali on 15-8-27.
 */
public class CommaServiceRegistry implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private Map<String, String> services;

    @Override public void afterPropertiesSet() throws Exception {

        if (services == null || services.size() == 0) {
            return;
        }

        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) configurableContext.getBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(
                HessianServiceExporter.class);

        for (String key : services.keySet()) {
            //com.dianping.daren.api.service.DarenIdentityTaskService/darenIdentityTaskService_1.0 darenIdentityTaskService
            if (register2SpringContext(beanDefinitionBuilder, listableBeanFactory, key)) {
                //register2Zk(key);
            }
        }
    }

    private boolean register2SpringContext(BeanDefinitionBuilder beanDefinitionBuilder,
                                           DefaultListableBeanFactory defaultListableBeanFactory, String key) {
        String beanRef = services.get(key);
        String serviceInterface = key.split("/")[0];
        //String beanName = applicationContext.getBean(beanRef).getClass().getName();
        beanDefinitionBuilder.addPropertyReference("service", beanRef);
        beanDefinitionBuilder.addPropertyValue("serviceInterface", serviceInterface);
        defaultListableBeanFactory.registerBeanDefinition("printHelloWorldFromCommaServiceRegistry", beanDefinitionBuilder.getRawBeanDefinition());
        Object o = applicationContext.getBean("printHelloWorldFromCommaServiceRegistry");
        return (o == null) ? false : true;
    }

    private boolean register2Zk(String key) {
        return PeriodServerUtil.createEphemeralNode(key, Helper.getServerIP(), "", "alpha");
    }

    public void setServices(Map<String, String> services) {
        this.services = services;
    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
