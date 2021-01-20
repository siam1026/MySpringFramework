package com.wdkang.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wdkang.framework.util.ReflectionUtil;

public final class BeanHelper {
  /**
   * BEAN_MAP �൱��һ��Spring����
   * ӵ��Ӧ�����е�Bean��ʵ��
   */
  private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
  
  static {
    // ��ȡӦ���е�����Bean
    Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
    // ��Beanʵ����
    for (Class<?> beanClass : beanClassSet) {
      Object obj = ReflectionUtil.newInstance(beanClass);
      BEAN_MAP.put(beanClass, obj);
    }
  }
  
  /**
   * ��ȡbean����
   */
  public static Map<Class<?>, Object> getBeanMap() {
    return BEAN_MAP;
  }
  
  /**
   * ��ȡbeanʵ��
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(Class<T> cls) {
    if (BEAN_MAP.containsKey(cls)) {
       throw new RuntimeException("can not get bean by class : " + cls);
    }
    return (T) BEAN_MAP.get(cls);
  }
  
  /**
   * ����beanʵ��
   */
  public static void setBean(Class<?> cls, Object obj) {
    BEAN_MAP.put(cls, obj);
  }
  
}
