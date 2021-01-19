package com.wdkang.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
  /**
   * ����·��
   */
  String value() default "";
  
  /**
   * ���󷽷�
   */
  RequestMethod method() default RequestMethod.GET;
}
