package com.myspring.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.myspring.framework.annotation.RequestMapping;
import com.myspring.framework.bean.Handler;
import com.myspring.framework.bean.Request;

public final class ControllerHelper {
  
  /**
   * REQUEST_MAP Ϊ ����-������ ӳ��
   */
  private static final Map<Request, Handler> REQUEST_MAP = new HashMap<>();
  
  static {
    // ��������Controller��
    Set<Class<?>> controllerClassSet = ClassHelper.getBeanClassSet();
    if (CollectionUtils.isNotEmpty(controllerClassSet)) {
      for (Class<?> controllerClass : controllerClassSet) {
        // ���������ȡ���Է���
        Method[] methods = controllerClass.getDeclaredMethods();
        // ��������
        if (ArrayUtils.isNotEmpty(methods)) {
          for (Method method : methods) {
            // �ж��Ƿ����RequestMappingע��
            if (method.isAnnotationPresent(RequestMapping.class)) {
              RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
              // ����·��
              String requestPath = requestMapping.value();
              // ���󷽷�
              String requestMethod = requestMapping.method().name();
              // ��װ����ʹ�����
              Request request = new Request(requestMethod, requestPath);
              Handler handler = new Handler(controllerClass, method);
              REQUEST_MAP.put(request, handler);
            }
          }
        }
      }
    }
  }
  
  /**
   * ��ȡHandler
   */
  public static Handler getHandler(String requestMethod, String requestPath) {
    Request request = new Request(requestMethod, requestPath);
    return REQUEST_MAP.get(request);
  }
}
