package com.qwk.test;

import com.qwk.HttpInterfaceRegistrar;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.List;

public class TestHttpInterfaceInSpring {
    AnnotationConfigApplicationContext context;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext();
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(PropertyPlaceholderConfigurer.class)
                .addPropertyValue("locations", "classpath:test.properties").getBeanDefinition();

        context.registerBeanDefinition("PropertyPlaceholderConfigurer",beanDefinition);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(HttpInterfaceRegistrar.class);
        beanDefinitionBuilder.addConstructorArgValue(new String[]{"com.shengpay.pos.operating.http"});
        context.registerBeanDefinition("HttpInterfaceRegistrar", beanDefinitionBuilder.getBeanDefinition());
        context.refresh();
    }

    @Test
    public void simpleTest() {
        GithubApi githubApi = context.getBean(GithubApi.class);
        List<Contributor> contributors = githubApi.contributors("spring", "spring","test","hjh","");
        Assert.notEmpty(contributors);
    }

}
