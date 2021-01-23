package com.myspring.framework.helper;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.myspring.framework.bean.Param;

public final class RequestHelper {
  /**
   * ��ȡ�������
   */
  public static Param createParam(HttpServletRequest request) throws IOException {
    Map<String, Object> paramMap = new HashMap<>();
    Enumeration<String> paramNames = request.getParameterNames();
    // û�в���
    if (!paramNames.hasMoreElements()) {
      return null;
    }
    // get �� post �������ܻ�ȡ��
    while (paramNames.hasMoreElements()) {
      String fieldName = paramNames.nextElement();
      String fieldValue = request.getParameter(fieldName);
      paramMap.put(fieldName, fieldValue);
    }
    return new Param(paramMap);
  }
}
