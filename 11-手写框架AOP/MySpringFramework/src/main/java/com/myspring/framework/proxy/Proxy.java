package com.myspring.framework.proxy;

public interface Proxy {
  /**
   * ִ����ʽ����
   * ��ν��ʽ���� �ɽ��������ͨ��һ�����Ӵ����� һ������ȥִ�� 
   * ִ��˳��ȡ���ڼ��뵽���ϵ��Ⱥ�˳��
   */
  Object doProxy(ProxyChain proxyChain) throws Throwable; 
}
