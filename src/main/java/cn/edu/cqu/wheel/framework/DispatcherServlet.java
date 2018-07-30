package cn.edu.cqu.wheel.framework;

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

import cn.edu.cqu.wheel.framework.bean.ActionHandler;
import cn.edu.cqu.wheel.framework.bean.Data;
import cn.edu.cqu.wheel.framework.bean.View;
import cn.edu.cqu.wheel.framework.helper.BeanHelper;
import cn.edu.cqu.wheel.framework.helper.ConfigHelper;
import cn.edu.cqu.wheel.framework.helper.ControllerHelper;
import cn.edu.cqu.wheel.framework.util.JsonUtil;
import cn.edu.cqu.wheel.framework.util.ReflectionUtil;
import cn.edu.cqu.wheel.framework.util.StringUtil;


/**
 * 请求转发器

 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void init(ServletConfig servletConfig) throws ServletException {
    	//初始化IOC容器等
        HelperLoader.init();
        //获取servletContext对象
        ServletContext servletContext = servletConfig.getServletContext();

        registerServlet(servletContext);

    }

    private void registerServlet(ServletContext servletContext) {
    	//注册jsp处理的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
      //注册静态资源处理的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
            String requestMethod = request.getMethod().toLowerCase();
            String requestPath = request.getPathInfo();
            //获取Action处理器
            ActionHandler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                
                Method actionMethod = handler.getActionMethod();
               
                Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, request);
                

                if (result instanceof View) {
                    handleViewResult((View) result, request, response);
                } else if (result instanceof Data) {
                    handleDataResult((Data) result, response);
                }
            }
        
    }

    private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
