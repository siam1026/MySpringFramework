package com.myspring.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.myspring.framework.util.ReflectionUtil;

/**
 * Bean ������
 */
public final class BeanHelper {

  /**
   * BEAN_MAP�൱��һ��bean����, ӵ����Ŀ����bean��ʵ��
   */
  private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

  static {
    // ��ȡ����bean
    Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
    // ��beanʵ����, ������bean������
    for (Class<?> beanClass : beanClassSet) {
      Object obj = ReflectionUtil.newInstance(beanClass);
      BEAN_MAP.put(beanClass, obj);
    }
  }

  /**
   * ��ȡ Bean ����
   */
  public static Map<Class<?>, Object> getBeanMap() {
    return BEAN_MAP;
  }

  /**
   * ��ȡ Bean ʵ��
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(Class<T> cls) {
    if (!BEAN_MAP.containsKey(cls)) {
      throw new RuntimeException("can not get bean by class: " + cls);
    }
    return (T) BEAN_MAP.get(cls);
  }

  /**
   * ���� Bean ʵ��
   */
  public static void setBean(Class<?> cls, Object obj) {
    BEAN_MAP.put(cls, obj);
  }
}
