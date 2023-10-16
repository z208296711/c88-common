package com.c88.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 設定物件屬性對應的 i18n key，在記錄操作日誌時，{@link com.c88.common.web.aop.AnnoLogAspect} 會進行json property name至i18n key的轉換，給前端顯示
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface I18n {
    /**
     * i18n key 值
     */
    String value() default "";

}
