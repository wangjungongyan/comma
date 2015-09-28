package com.vali.comma;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * Created by vali on 15-8-27.
 */

public class CommaClientInvoker implements InitializingBean, FactoryBean {

    private String serviceName;

    private String serviceInterface;

    private String registerBeanName;

    private String serviceUrl;

    private DefaultListableBeanFactory beanFactory;

    @Override public void afterPropertiesSet() throws Exception {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(HessianProxyFactoryBean.class);
        this.parseServiceName();

        beanDefinitionBuilder.addPropertyValue("serviceUrl", serviceUrl);
        beanDefinitionBuilder.addPropertyValue("serviceInterface", serviceInterface);
        beanDefinitionBuilder.addPropertyValue("overloadEnabled", true);

        beanFactory.registerBeanDefinition(registerBeanName, beanDefinitionBuilder.getRawBeanDefinition());
    }

    //TODO
    private void parseServiceName() {
        String[] serviceNameInfo = serviceName.split("/");
        serviceInterface = serviceNameInfo[0];
        registerBeanName = serviceNameInfo[1];
        serviceUrl = "";
    }

    @Override public Object getObject() throws Exception {
        Object object = beanFactory.getBean(registerBeanName);
        return Helper.loadClass(CommaClientInvoker.class.getClassLoader(), serviceInterface).cast(object);
    }

    @Override public Class getObjectType() {
        return null;
    }

    @Override public boolean isSingleton() {
        return false;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

}
