package com.myspring.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myspring.framework.annotation.Autowired;
import com.myspring.framework.util.ReflectionUtil;

/**
 * ����ע��������
 */
public final class IocHelper {

  /**
   * ����bean��������bean������, Ϊ���д�@Autowiredע�������ע��ʵ��
   */
  static {
    // ����bean�����������bean
    Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);
    Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
    LOGGER.debug("BeanMap => " + beanMap.toString());
    if (MapUtils.isNotEmpty(beanMap)) {
      for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
        // bean��class��
        Class<?> beanClass = beanEntry.getKey();
        // bean��ʵ��
        Object beanInstance = beanEntry.getValue();
        // ���������ȡ����
        Field[] beanFields = beanClass.getDeclaredFields();
        // ����bean������
        if (ArrayUtils.isNotEmpty(beanFields)) {
          for (Field beanField : beanFields) {
            // �ж������Ƿ��Autowiredע��
            if (beanField.isAnnotationPresent(Autowired.class)) {
              LOGGER.debug("Autowired.class => " + beanField.toString());
              // ��������
              Class<?> beanFieldClass = beanField.getType();
              LOGGER.debug("beanField.getType => " + beanField.getType().toString());
              // ���beanFieldClass�ǽӿ�, �ͻ�ȡ�ӿڶ�Ӧ��ʵ����
              beanFieldClass = findImplementClass(beanFieldClass);
              // ��ȡClass���Ӧ��ʵ��
              Object beanFieldInstance = beanMap.get(beanFieldClass);
              LOGGER.debug("beanFieldInstance => " + beanMap.get(beanFieldClass));
              if (beanFieldInstance != null) {
                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                LOGGER.debug("ReflectionUtil => " + beanFieldInstance.toString());
              }
            }
          }
        }
      }
    }
  }

  /**
   * ��ȡ�ӿڶ�Ӧ��ʵ����
   */
  public static Class<?> findImplementClass(Class<?> interfaceClass) {
    Class<?> implementClass = interfaceClass;
    // �ӿڶ�Ӧ������ʵ����
    Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(interfaceClass);
    if (CollectionUtils.isNotEmpty(classSetBySuper)) {
      // ��ȡ��һ��ʵ����
      implementClass = classSetBySuper.iterator().next();
    }
    return implementClass;
  }
}
