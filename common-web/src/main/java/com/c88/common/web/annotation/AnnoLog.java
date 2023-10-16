package com.c88.common.web.annotation;


import com.c88.common.web.log.OperationEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author zry
 * date 2021-12-20 16:01
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  //注解作用的目标
@Retention(RetentionPolicy.RUNTIME)  //注解的保留位置,这种类型的Annotations将被JVM保留,所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用
@Documented  //该注解将被包含在javadoc中
@Repeatable(AnnoLogs.class) //表明该注解可以进行重复标注
public @interface AnnoLog {

    //业务唯一id
    String uuId();

    /**
     * i18n key 對應的文字內容中對應的參數陣列
     *
     * @return [{0},{1},{2},{3}]
     */
    String content() default "";

    /**
     * 記錄的樣版描述，方便開發閱讀並了解對應參數而已
     *
     * @return
     */
    String desc() default "operation";

    OperationEnum operationEnum() default OperationEnum.DEFAULT;

    String menu() default "";

    String menuPage() default "";

    String i18nKey() default "";

    /**
     * 當 OperationEnum.UPDATE 時，若在addition指定「欄位名稱」，則content帶入的下一個參數為此指定欄位修改後的值，
     * 若沒有指定「欄位名稱」，則content會帶入{Josn格式紀錄所有有異動欄位名及欄位值(異動前的值)}, {Josn格式紀錄所有有異動欄位名及欄位值(異動後的值)}。
     * EX:sort
     *
     * @return
     */
    String addition() default "";

    String ip() default "";
}