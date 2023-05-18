package com.qwk;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author qiuwenke
 */
public class HttpInterfaceFactory implements FactoryBean {


    public HttpInterfaceFactory(Target<?> target) {
        this.target = target;
    }

    private Target<?> target;

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{target.type()}, new HttpInterfaceProxy(target));
    }

    @Override
    public Class<?> getObjectType() {
        return target.type();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
