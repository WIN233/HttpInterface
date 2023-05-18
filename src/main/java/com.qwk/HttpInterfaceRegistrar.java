package com.qwk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author qiuwenke
 */
public class HttpInterfaceRegistrar implements BeanFactoryAware, BeanFactoryPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(HttpInterfaceRegistrar.class);

    private DefaultListableBeanFactory beanFactory;

    private String[] basePackages;

    public HttpInterfaceRegistrar(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public void doRegister() {
        //扫描指定包，注册bean
        for (String basePackage : basePackages) {
            ClassPathScanningCandidateComponentProvider scanner = getScanner();
            scanner.addIncludeFilter(new AnnotationTypeFilter(HttpInterface.class));
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);

            for (BeanDefinition candidateComponent : candidateComponents) {
                String beanName = candidateComponent.getBeanClassName();
                Class clazz = ClassUtils.resolveClassName(beanName, this.getClass().getClassLoader());
                Map<String, Object> annotationAttributes = ((ScannedGenericBeanDefinition) candidateComponent).getMetadata().getAnnotationAttributes(HttpInterface.class.getName());
                if (annotationAttributes != null && !annotationAttributes.isEmpty()) {
                    beanName = (String) annotationAttributes.get("value");
                } else {
                    //todo 为空处理
                }

                Target target = processTarget(clazz, annotationAttributes);

                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(HttpInterfaceFactory.class);
                beanDefinitionBuilder.addConstructorArgValue(target);
                AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();


                beanFactory.registerBeanDefinition(beanName, beanDefinition);
            }
        }


    }

    public Target processTarget(Class clazz, Map<String, Object> annotationAttributes) {
        Target.DefaultTarget target = new Target.DefaultTarget<>(clazz);
        if(annotationAttributes.get("url") != null && StringUtils.hasText((String) annotationAttributes.get("url"))) {
            String url = (String) annotationAttributes.get("url");
            if(logger.isDebugEnabled()) {
                logger.debug("[{}]before process url [{}]",target.getType().getName(),url);
            }
            //TODO spring 3.1以后需要换成environment来实现
            url = beanFactory.resolveEmbeddedValue(url);
            if(logger.isDebugEnabled()) {
                logger.debug("[{}]after process url [{}]",target.getType().getName(),url);
            }
            //todo 解析失败处理
            target.setUrl(url);
        }
        return target;

    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    isCandidate = true;
                }
                return isCandidate;
            }
        };
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        doRegister();
    }

}
