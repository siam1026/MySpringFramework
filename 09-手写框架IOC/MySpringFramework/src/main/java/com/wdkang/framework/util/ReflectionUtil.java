package com.wdkang.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);
  
  /**
   * ����ʵ��
   */
  @SuppressWarnings("deprecation")
  public static Object newInstance(Class<?> cls) {
    Object instance;
    try {
      instance = cls.newInstance();
    } catch (Exception e) {
      LOGGER.error("new instance failure", e);
      throw new RuntimeException(e);
    }
    return instance;
  }
  
  /**
   * ����ʵ�� ������������
   */
  public static Object newInstance(String className) {
    Class<?> cls = ClassUtil.loadClass(className);
    return newInstance(cls);
  }
  
  /**
   * ���÷���
   */
  public static Object invokeMethod(Object obj, Method method, Object... args) {
    Object result;
    try {
      method.setAccessible(true);
      result = method.invoke(obj, args);
    } catch(Exception e) {
      LOGGER.error("invoke method failure", e);
      throw new RuntimeException(e);
    }
    return result;
  }
  
  /**
   * ���ó�Ա������ֵ
   */
  public static void setField(Object obj, Field field, Object value) {
    try {
      field.setAccessible(true);
      field.set(obj, value);
    } catch (Exception e) {
      LOGGER.error("set field failure", e);
      throw new RuntimeException(e);
    }
  }
}
