package com.qwk.test;

import com.qwk.request.FormRequestProcessor;
import com.qwk.request.MethodMetadataContext;
import org.junit.Test;

import java.lang.reflect.Method;

public class TestFormRequestProcessor {
    public void test(String fsadf) {

    }

    interface TestApi {
        void test(String fsafsd);
    }

    @Test
    public void test() throws NoSuchMethodException {
        Method method = TestApi.class.getMethod("test", String.class);
        Object[] args = new Object[]{"test", 123};
        MethodMetadataContext context = new MethodMetadataContext(args, method);

        FormRequestProcessor formRequestProcessor = new FormRequestProcessor();
        formRequestProcessor.process(context,null);
    }

}
