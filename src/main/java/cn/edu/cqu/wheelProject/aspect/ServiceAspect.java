package cn.edu.cqu.wheelProject.aspect;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.cqu.wheel.framework.anotation.Aspect;
import cn.edu.cqu.wheel.framework.anotation.Service;
import cn.edu.cqu.wheel.framework.proxy.AspectProxy;


/**
 * 拦截 service 所有方法
 * @author hxc
 */
@Aspect(Service.class)
public class ServiceAspect extends AspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.debug("---------- begin ----------");
        LOGGER.debug(String.format("class: %s", cls.getName()));
        LOGGER.debug(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
        LOGGER.debug("----------- end -----------");
    }
}
