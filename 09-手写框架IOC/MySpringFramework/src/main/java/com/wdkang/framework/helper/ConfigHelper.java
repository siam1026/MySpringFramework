package com.wdkang.framework.helper;

import java.util.Properties;

import com.wdkang.framework.ConfigConstant;
import com.wdkang.framework.util.PropsUtil;

public final class ConfigHelper {
  
  /**
   * ���������ļ�����
   */
  private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

  /**
   * ��� JDBC ����
   */
  public static String getJdbcDriver() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
  }
  
  /**
   * ��� JDBC url 
   */
  public static String getJdbcUrl() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
  }
  
  /**
   * ��� JDBC �û���
   */
  public static String getJdbcUsername() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
  }
  
  /**
   * ��� JDBC ����
   */
  public static String getJdbcPassword() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
  }
  
  /**
   * ��ȡӦ�û�������
   */
  public static String getAppBasePackage() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
  }
  
  /**
   * ��ȡ JSP ·��
   */
  public static String getAppJspPath() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH);
  }
  
  /**
   * ��ȡӦ�þ�̬��Դ·��
   */
  public static String getAppAssetPath() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH);
  }
  
  /**
   * �����������ֻ�ȡString���͵�����ֵ
   */
  public static String getString (String key) {
    return PropsUtil.getString(CONFIG_PROPS, key);
  }
  
  /**
   * �����������ֻ�ȡint���͵�����ֵ
   */
  public static int getInt(String key) {
    return PropsUtil.getInt(CONFIG_PROPS, key);
  }
  
  /**
   * �����������ֻ�ȡboolean���͵�����ֵ
   */
  public static boolean getBoolean(String key) {
    return PropsUtil.getBoolean(CONFIG_PROPS, key);
  }
  
}
