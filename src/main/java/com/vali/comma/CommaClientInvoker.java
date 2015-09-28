package com.vali.comma;

/**
 * Created by vali on 15-8-27.
 */
public class CommaClientInvoker implements  Initia{

    private String serviceName;

    private String serviceUrl;

    private String serviceInterface;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
