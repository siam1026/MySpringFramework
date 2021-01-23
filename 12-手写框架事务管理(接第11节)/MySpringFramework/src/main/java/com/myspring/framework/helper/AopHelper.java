package com.myspring.framework.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myspring.framework.annotation.Aspect;
import com.myspring.framework.proxy.AspectProxy;
import com.myspring.framework.proxy.Proxy;
import com.myspring.framework.proxy.ProxyFactory;
import com.myspring.framework.util.ClassUtil;

public final class AopHelper {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
  
  static {
    try {
      // ������ Ŀ���༯�ϵ�ӳ��
      Map<Class<?>, Set<Class<?>>> aspectMap = createAspectMap();
      // Ŀ���� ��������б��ӳ��
      Map<Class<?>, List<Proxy>> targetMap = createTargetMap(aspectMap);
      // ���������֯�뵽Ŀ������ �����������
      for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
        Class<?> targetClass = targetEntry.getKey();
        List<Proxy> proxyList = targetEntry.getValue();
        Object proxy = ProxyFactory.createProxy(targetClass, proxyList);
        // ����Bean����Ŀ�����Ӧ��ʵ�� �´δ�Bean������ȡ�ľ��Ǵ��������
        BeanHelper.setBean(targetClass, proxy);
      }
    } catch (Exception e) {
      LOGGER.error("aop failure", e);
    }
  }
  
  
  /**
   * ��ȡ������ Ŀ���༯�ϵ�ӳ��
   */
  private static Map<Class<?>, Set<Class<?>>> createAspectMap() throws Exception {
    Map<Class<?>, Set<Class<?>>> aspectMap = new HashMap<>();
    addAspectProxy(aspectMap);
    return aspectMap;
  }
  
  /**
   * ��ȡ��ͨ�������� Ŀ���༯�ϵ�ӳ��
   */
  private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception {
    // ʵ�������е�AspectProxy�����������
    Set<Class<?>> aspectClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
    for (Class<?> aspectClass : aspectClassSet) {
      if (aspectClass.isAnnotationPresent(Aspect.class)) {
        Aspect aspect = aspectClass.getAnnotation(Aspect.class);
        // ��������Ӧ��Ŀ���༯��
        Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
        aspectMap.put(aspectClass, targetClassSet);
      }
    }
  }
  
  /**
   * ���� @Aspect ����İ���������ȥ��ȡ��Ӧ��Ŀ�꼯���� 
   */
  private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
    Set<Class<?>> targetClassSet = new HashSet<>();
    // ����
    String pkg = aspect.pkg();
    // ����
    String cls = aspect.cls();
    // �����������������Ϊ�� �����ָ����
    if (!pkg.equals("") && !cls.equals("")) {
      targetClassSet.add(Class.forName(pkg + "." + cls));
    } else if (!pkg.equals("")) {
      // ���������Ϊ�� ����Ϊ�� ����Ӹð����µ�������
      targetClassSet.addAll(ClassUtil.getClassSet(pkg));
    }
    return targetClassSet;
  }
  
  /**
   * ������ Ŀ���༯�ϵ�ӳ�伯�� ת��Ϊ Ŀ���� �������б��ӳ���ϵ
   */
  private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception {
    Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
    for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : aspectMap.entrySet()) {
      // ������
      Class<?> aspectClass = proxyEntry.getKey();
      // Ŀ���༯��
      Set<Class<?>> targetClassSet = proxyEntry.getValue();
      // ����Ŀ���� ��������б� ��ӳ�����
      for (Class<?> targetClass : targetClassSet) {
        // �������
        @SuppressWarnings("deprecation")
        Proxy aspect = (Proxy) aspectClass.newInstance();
        if (targetMap.containsKey(targetClass)) {
          targetMap.get(targetClass).add(aspect);
        } else {
          // ��������б�
          List<Proxy> aspectList = new ArrayList<>();
          aspectList.add(aspect);
          targetMap.put(targetClass, aspectList);
        }
      }
    }
    return targetMap;
  }
}
