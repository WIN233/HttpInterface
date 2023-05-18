package com.qwk.test;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestBody;

public class TestJsonRequestProcessor {


    private interface TestApi {
        void test(@RequestBody String test);
    }

    @Test
    public void test() throws NoSuchMethodException {
//        JsonRequestProcessor processor = new JsonRequestProcessor();
//        Method test = TestApi.class.getMethod("test", String.class);
//        MethodMetaData methodMetaData = new MethodMetaData();
//        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("test","hhhh");
//        processor.process(test,new Object[]{objectObjectHashMap},methodMetaData);
//        System.out.println(methodMetaData);


    }

}
