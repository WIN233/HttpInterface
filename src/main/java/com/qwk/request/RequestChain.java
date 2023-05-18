package com.qwk.request;


import com.qwk.MethodMetaData;

import java.lang.reflect.Method;
import java.util.List;

public class RequestChain {
    private static List<RequestProcessor> requestProcessorList;
    static {
        FormRequestProcessor formRequestProcessor = new FormRequestProcessor();
        JsonRequestProcessor processor = new JsonRequestProcessor();
        requestProcessorList.add(formRequestProcessor);
        requestProcessorList.add(processor);
    }

    void process(Method method, Object[] args, MethodMetaData methodMetaData) {
        MethodMetadataContext methodMetadataContext = new MethodMetadataContext(args,method);
        for (RequestProcessor requestProcessor : requestProcessorList) {

        }
    }



}
