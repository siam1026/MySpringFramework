package com.wdkang.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClassUtil {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

  /**
   * ��ȡ�������
   */
  public static ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
  
  
}
