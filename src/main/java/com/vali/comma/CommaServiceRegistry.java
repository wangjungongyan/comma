package com.vali.comma;

import com.period.server.PeriodServerUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.remoting.caucho.HessianServiceExporter;

import java.util.Map;

/**
 * Created by vali on 15-8-27.
 */
public class CommaServiceRegistry implements BeanFactoryAware,
        ApplicationListener {

    private DefaultListableBeanFactory beanFactory;

    private Map<String, String> services;

    @Override public void onApplicationEvent(ApplicationEvent event) {
        if (services == null || services.size() == 0) {
            return;
        }

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(HessianServiceExporter.class);

        for (String key : services.keySet()) {
            //com.dianping.daren.api.service.DarenIdentityTaskService/darenIdentityTaskService_1.0 darenIdentityTaskService
            if (register2SpringContext(beanDefinitionBuilder, key)) {
                //register2Zk(key);
            }
        }
    }

    private boolean register2SpringContext(BeanDefinitionBuilder beanDefinitionBuilder, String key) {
        String beanRef = services.get(key);
        String serviceInterface = key.split("/")[0];
        beanDefinitionBuilder.addPropertyReference("service", beanRef);
        beanDefinitionBuilder.addPropertyValue("serviceInterface", serviceInterface);

        String registerBeanName = "/" + key.split("/")[1];
        beanFactory.registerBeanDefinition(registerBeanName,
                                           beanDefinitionBuilder.getRawBeanDefinition());

        Object o = beanFactory.getBean(registerBeanName);
        return (o == null) ? false : true;
    }

    //TODO
    private boolean register2Zk(String key) {
        return PeriodServerUtil.createEphemeralNode(key, Helper.getServerIP(), "", "alpha");
    }

    public void setServices(Map<String, String> services) {
        this.services = services;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

}
