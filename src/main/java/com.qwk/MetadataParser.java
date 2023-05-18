package com.qwk;

import java.lang.reflect.Method;

public interface MetadataParser {

    MethodMetaData parse(Method method, Object[] args);

}
