package com.c88.common.core.desensitization.annotation;

import com.c88.common.core.desensitization.enums.DesensitizedType;
import com.c88.common.core.desensitization.json.DesensitizedJsonSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizedJsonSerializer.class)
public @interface Sensitive {

    DesensitizedType strategy() default  DesensitizedType.NONE;

    /**
     * 是否使用dfa算法
     * @return
     */
    boolean useDFA() default false;

    /**
     * dfa敏感字符替换，默认替换成 "*"
     * @return
     */
    String dfaReplaceChar() default "*";


    /**
     * dfa敏感字符替换次数
     * @return
     */
    int dfaReplaceCharRepeatCount() default 1;

}