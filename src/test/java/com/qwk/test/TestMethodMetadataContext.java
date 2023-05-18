package com.qwk.test;

import com.qwk.request.AnnotationMetadata;
import com.qwk.request.MethodMetadataContext;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;

public class TestMethodMetadataContext {

    private interface TestApi {
        void test(@RequestParam(defaultValue = "行乎和") String fasdf);
    }

    @Test
    public void test() throws NoSuchMethodException {
        Object[] objects = {"test"};
        Method test = TestApi.class.getMethod("test", String.class);
        MethodMetadataContext methodMetadataContext = new MethodMetadataContext(objects,test);
        System.out.println(methodMetadataContext);
        AnnotationMetadata annotation = methodMetadataContext.findAnnotation(RequestParam.class);
        System.out.println(annotation);
    }


}
