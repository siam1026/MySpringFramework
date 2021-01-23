package com.myspring.framework.annotation;

import java.lang.annotation.*;

/**
 * Controller ����ע��
 * RequestMapping
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * ����·��
     * @return
     */
    String value() default "";

    /**
     * ���󷽷�
     * @return
     */
    RequestMethod method() default RequestMethod.GET;
}
