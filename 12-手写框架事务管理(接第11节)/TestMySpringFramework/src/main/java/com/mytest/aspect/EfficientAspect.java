package com.mytest.aspect;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myspring.framework.annotation.Aspect;
import com.myspring.framework.proxy.AspectProxy;

/**
 * ��������
 * ��UserServiceImpl�м�����Thread.sleep(1000L); ģ������ʱ��
 * ������潫�ڿ���̨��ӡ��Ϣ
 * ===========before begin=========== 
 * time: 1022ms 
 * ===========after end=========== 
 * 
 */
@Aspect(pkg = "com.mytest.controller", cls = "UserController")
public class EfficientAspect extends AspectProxy {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(EfficientAspect.class);
  
  private long begin;
  
  /**
   * ������ж�
   */
  @Override
  public boolean intercept(Method method, Object[] params) throws Throwable {
    return method.getName().equals("getUserList");
  }
  
  @Override
  public void before(Method method, Object[] params) throws Throwable {
    LOGGER.debug("===========before begin===========");
    begin = System.currentTimeMillis();
  }
  
  @Override
  public void after(Method method, Object[] params) throws Throwable {
    LOGGER.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
    LOGGER.debug("===========after end===========");
  }
  
}
