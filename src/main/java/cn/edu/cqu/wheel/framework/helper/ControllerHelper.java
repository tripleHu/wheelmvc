package cn.edu.cqu.wheel.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.edu.cqu.wheel.framework.anotation.Action;
import cn.edu.cqu.wheel.framework.bean.ActionHandler;
import cn.edu.cqu.wheel.framework.bean.Request;
import cn.edu.cqu.wheel.framework.util.ArrayUtil;
import cn.edu.cqu.wheel.framework.util.CollectionUtil;


/**
 * 控制器助手类

 */
public final class ControllerHelper {
	
	/**
	 * 用于存放请求与Controller的对应关系
	 */
    private static final Map<Request, ActionHandler> ACTION_MAP = new HashMap<Request, ActionHandler>();

    static {
    	//获取所有Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
        	//遍历所有Controller类
            for (Class<?> controllerClass : controllerClassSet) {
            	//获取Controller里面的所有方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                	//遍历Controller里面的所有方法
                    for (Method method : methods) {
                    	//判断是否有@Action注解
                        if (method.isAnnotationPresent(Action.class)) {
                        	//获取@Action填写的url映射
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证映射规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                	//获取请求方法和请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    ActionHandler handler = new ActionHandler(controllerClass, method);
                                    //保存对应关系
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 Handler
     */
    public static ActionHandler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
