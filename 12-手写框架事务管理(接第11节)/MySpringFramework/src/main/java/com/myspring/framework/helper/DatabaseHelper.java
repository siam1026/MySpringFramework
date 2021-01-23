package com.myspring.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DatabaseHelper {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
  
  private static final ThreadLocal<Connection> CONNECTION_HOLDER;
  
  private static final QueryRunner QUERY_RUNNER;
  
  private static final BasicDataSource DATA_SOURCE;
  
  static {
    CONNECTION_HOLDER = new ThreadLocal<Connection>();
    QUERY_RUNNER = new QueryRunner();
    DATA_SOURCE = new BasicDataSource();
    DATA_SOURCE.setDriverClassName(ConfigHelper.getJdbcDriver());
    DATA_SOURCE.setUrl(ConfigHelper.getJdbcUrl());
    DATA_SOURCE.setUsername(ConfigHelper.getJdbcUsername());
    DATA_SOURCE.setPassword(ConfigHelper.getJdbcPassword());
  }
  
  /**
   * ��ȡ����Դ
   */
  public static DataSource getDataSource() {
    return DATA_SOURCE;
  }
  
  /**
   * ��ȡ���ݿ�����
   */
  public static Connection getConnection() {
    Connection conn = CONNECTION_HOLDER.get();
    if (conn == null) {
      try {
        conn = DATA_SOURCE.getConnection();
      } catch (SQLException e) {
        LOGGER.error("get Connection failure", e);
        throw new RuntimeException(e);
      } finally {
        CONNECTION_HOLDER.set(conn);
      }
    }
    return conn;
  }
  
  /**
   * ��������
   */
  public static void beginTransaction() {
    Connection conn = getConnection();
    if (conn != null) {
      try {
        conn.setAutoCommit(false);
      } catch (SQLException e) {
        LOGGER.error("begin transaction failure", e);
        throw new RuntimeException(e);
      } finally {
        CONNECTION_HOLDER.set(conn);
      }
    }
  }
  
  
  /**
   * �ύ����
   */
  public static void commitTransaction() {
    Connection conn = getConnection();
    if (conn != null) {
      try {
        conn.commit();
        conn.close();
      } catch (SQLException e) {
        LOGGER.error("commit transaction failure", e);
        throw new RuntimeException(e);
      } finally {
        CONNECTION_HOLDER.remove();
      }
    }
  }
  
  /**
   * �ع�����
   */
  public static void rollbackTransaction() {
    Connection conn = getConnection();
    if (conn != null) {
      try {
        conn.rollback();
        conn.close();
      } catch (SQLException e) {
        LOGGER.error("rollback transaction failure", e);
        throw new RuntimeException(e);
      } finally {
        CONNECTION_HOLDER.remove();
      }
    }
  }
  
  /**
   * ��ѯʵ��
   */
  public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
    T entity;
    try {
      Connection conn = getConnection();
      entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
    } catch (SQLException e) {
      LOGGER.error("query entity failure", e);
      throw new RuntimeException(e);
    }
    return entity;
  } 
  
  /**
   * ��ѯʵ���б�
   */
  public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
    List<T> entityList;
    try {
      Connection conn = getConnection();
      entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
    } catch (SQLException e) {
      LOGGER.error("query entity list failure", e);
      throw new RuntimeException();
    }
    return entityList;
  }
  
  /**
   * ִ�и������(����update insert delete)
   */
  public static int update(String sql, Object... params) {
    int rows;
    try {
      Connection conn = getConnection();
      rows = QUERY_RUNNER.update(conn, sql, params);
    } catch (SQLException e) {
      LOGGER.error("execute update failure", e);
      throw new RuntimeException();
    }
    return rows;
  }
  
  /**
   * ����ʵ��
   */
  public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
    if (MapUtils.isEmpty(fieldMap)) {
      LOGGER.error("can not insert entity : fieldMap is empty");
      return false;
    }
    String sql = "INSERT INTO " + entityClass.getSimpleName();
    StringBuilder columns = new StringBuilder();
    StringBuilder values = new StringBuilder();
    for (String fieldName : fieldMap.keySet()) {
      columns.append(fieldName).append(", ");
      values.append("?, ");
    }
    columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
    values.replace(values.lastIndexOf(", "), values.length(), ")");
    sql += columns + " VALUES " + values;
    
    Object[] params = fieldMap.values().toArray();
    
    return update(sql, params) == 1;
  }
  
  /**
   * ����ʵ��
   */
  public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
    if (MapUtils.isEmpty(fieldMap)) {
      LOGGER.error("can not update entity : fieldMap is empty");
      return false;
    }
    String sql = "UPDATE " + entityClass.getSimpleName() + " SET ";
    StringBuilder columns = new StringBuilder();
    for (String fieldName : fieldMap.keySet()) {
      columns.append(fieldName).append(" = ?, ");
    }
    sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id = ?";
    List<Object> paramList = new ArrayList<>();
    paramList.addAll(fieldMap.values());
    paramList.add(id);
    Object[] params = paramList.toArray();
    
    return update(sql, params) == 1;
  }
  
  /**
   * ɾ��ʵ��
   */
  public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
    String sql = "DELETE FROM " + entityClass.getSimpleName() + " WHERE id = ?";
    return update(sql, id) == 1;
  }
}
