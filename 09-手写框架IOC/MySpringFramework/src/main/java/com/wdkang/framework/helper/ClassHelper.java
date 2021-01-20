package com.wdkang.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.wdkang.framework.annotation.Controller;
import com.wdkang.framework.annotation.Service;
import com.wdkang.framework.util.ClassUtil;

public final class ClassHelper {
  /**
   * ���弯��
   */
  private static final Set<Class<?>> CLASS_SET;
  
  static {
    // ��ȡ��������
    String basePackage = ConfigHelper.getAppBasePackage();
    CLASS_SET = ClassUtil.getClassSet(basePackage);
  }
  
  /**
   * ��ȡ���������µ�������
   */
  public static Set<Class<?>> getClassSet() {
    return CLASS_SET;
  }
  
  /**
   * ��ȡ��������������Service��
   */
  public static Set<Class<?>> getServiceClassSet() {
    Set<Class<?>> classSet = new HashSet<>();
    for (Class<?> cls : CLASS_SET) {
      if (cls.isAnnotationPresent(Service.class)) {
        classSet.add(cls);
      }
    }
    return classSet;
  }
  
  /**
   * ��ȡ��������������Controller��
   */
  public static Set<Class<?>> getControllerClassSet() {
    Set<Class<?>> classSet = new HashSet<>();
    for (Class<?> cls : CLASS_SET) {
      if (cls.isAnnotationPresent(Controller.class)) {
        classSet.add(cls);
      }
    }
    return classSet;
  }
  
  /**
   * ��ȡ��������������Bean��
   */
  public static Set<Class<?>> getBeanClassSet() {
    Set<Class<?>> beanClassSet = new HashSet<>();
    beanClassSet.addAll(getServiceClassSet());
    beanClassSet.addAll(getControllerClassSet());
    return beanClassSet;
  }
  
  /**
   * ��ȡ����������ĳ������������� �� ĳ�ӿڵ�����ʵ����
   */
  public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
    Set<Class<?>> classSet = new HashSet<>();
    for (Class<?> cls : CLASS_SET) {
      // isAssignableFrom() ��ָsuperClass �� cls �Ƿ���ͬ
      // �� superClass �Ƿ�Ϊ cls �� ���ࡢ�ӿ�
      if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
        classSet.add(cls);
      }
    }
    return classSet;
  }
  
  /**
   * ��ȡ���������´���ĳע���������
   */
  public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
    Set<Class<?>> classSet = new HashSet<>();
    for (Class<?> cls : classSet) {
      if (cls.isAnnotationPresent(annotationClass)) {
        classSet.add(cls);
      }
    }
    return classSet;
  }
  
}
