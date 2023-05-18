package com.qwk.request;

import com.qwk.MethodMetaData;

/**
 * @author qiuwenke
 */
public interface RequestProcessor {


    void process(MethodMetadataContext context, MethodMetaData methodMetaData);
}
