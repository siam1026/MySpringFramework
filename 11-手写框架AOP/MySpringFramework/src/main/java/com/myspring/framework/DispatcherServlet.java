package com.myspring.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.myspring.framework.bean.Data;
import com.myspring.framework.bean.Handler;
import com.myspring.framework.bean.Param;
import com.myspring.framework.bean.View;
import com.myspring.framework.helper.BeanHelper;
import com.myspring.framework.helper.ConfigHelper;
import com.myspring.framework.helper.ControllerHelper;
import com.myspring.framework.helper.RequestHelper;
import com.myspring.framework.util.ReflectionUtil;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  @Override
  public void init(ServletConfig servletConfig) throws ServletException {
    // ��ʼ����ص�Helper��
    HelperLoader.init();
    // ��ȡServletContext���� ����ע��Servlet
    ServletContext servletContext = servletConfig.getServletContext();
    // ע�ᴦ��JSP�;�̬��Դ��servlet
    registerServlet(servletContext);
  }
  
  /**
   * DefaultServlet �� JspServlet ������Web��������
   * org.apache.catalina.servlets.DefaultServlet
   * org.apache.catalina.servlets.JspServlet
   */
  private void registerServlet(ServletContext servletContext) {
    // ��̬ע�ᴦ��JSP��Servlet
    ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
    jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
    // ��̬ע�ᴦ��̬��Դ��Ĭ��Servlet
    ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
    defaultServlet.addMapping("/favicon.ico");
    defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
  }
  
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String requestMethod = req.getMethod().toUpperCase();
    String requestPath = req.getPathInfo();
    
    // �������Tomcat����·�����������
    // һ���� /userList
    // ��һ���� /context��ַ/userList
    String[] splits = requestPath.split("/");
    if (splits.length > 2) {
      requestPath = "/" + splits[2];
    }
    
    // ��ȡ������������������SpringMVC��ӳ�䴦������
    Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
    if (handler != null) {
      Class<?> controllerClass = handler.getControllerClass();
      Object controllerBean = BeanHelper.getBean(controllerClass);
      
      // ��ʼ������
      Param param = RequestHelper.createParam(req);
      
      // �����������Ӧ�ķ���(����������SpringMVC�Ĵ�����������)
      Object result;
      Method actionMethod  = handler.getControllerMethod();
      if (param == null || param.isEmpty()) {
        result  = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
      } else {
        result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
      }
      
      // ��תҳ��򷵻�json���� ������������SpringMVC�е���ͼ��������
      if (result instanceof View) {
        handleViewResult((View) result, req, resp);
      } else {
        handleDataResult((Data) result, resp);
      }
    }
  }
  
  /**
   * ��תҳ��
   */
  private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String path = view.getPath();
    if (StringUtils.isNotEmpty(path)) {
      if (path.startsWith("/")) {
        // �ض���
        response.sendRedirect(request.getContextPath() + path);
      } else {
        // ����ת��
        Map<String, Object> model = view.getModel();
        for (Map.Entry<String, Object> entry : model.entrySet()) {
          request.setAttribute(entry.getKey(), entry.getValue());
        }
        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
      }
    }
  }
  
  
  /**
   * ����json����
   */
  private void handleDataResult(Data data, HttpServletResponse response) throws IOException {
    Object model = data.getModel();
    if (model != null) {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      PrintWriter writer = response.getWriter();
      String json = JSON.toJSON(model).toString();
      writer.write(json);
      writer.flush();
      writer.close();
    }
  }
}
