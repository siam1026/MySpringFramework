package com.myspring.framework.helper;

import java.util.Properties;

import com.myspring.framework.ConfigConstant;
import com.myspring.framework.util.PropsUtil;

/**
 * �����ļ������� �������ڻ�ȡ�����ļ��е�����, ����JSP�;�̬��Դ������Ĭ��·��
 */
public final class ConfigHelper {

  /**
   * ���������ļ�������
   */
  private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

  /**
   * ��ȡ JDBC ����
   */
  public static String getJdbcDriver() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
  }

  /**
   * ��ȡ JDBC URL
   */
  public static String getJdbcUrl() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
  }

  /**
   * ��ȡ JDBC �û���
   */
  public static String getJdbcUsername() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
  }

  /**
   * ��ȡ JDBC ����
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
   * ��ȡӦ�� JSP ·��
   */
  public static String getAppJspPath() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
  }

  /**
   * ��ȡӦ�þ�̬��Դ·��
   */
  public static String getAppAssetPath() {
    return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
  }

  /**
   * ������������ȡ String ���͵�����ֵ
   */
  public static String getString(String key) {
    return PropsUtil.getString(CONFIG_PROPS, key);
  }

  /**
   * ������������ȡ int ���͵�����ֵ
   */
  public static int getInt(String key) {
    return PropsUtil.getInt(CONFIG_PROPS, key);
  }

  /**
   * ������������ȡ boolean ���͵�����ֵ
   */
  public static boolean getBoolean(String key) {
    return PropsUtil.getBoolean(CONFIG_PROPS, key);
  }
}
