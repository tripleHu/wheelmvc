package cn.edu.cqu.wheel.framework;

import cn.edu.cqu.wheel.framework.helper.AopHelper;
import cn.edu.cqu.wheel.framework.helper.BeanHelper;
import cn.edu.cqu.wheel.framework.helper.ClassHelper;
import cn.edu.cqu.wheel.framework.helper.ControllerHelper;
import cn.edu.cqu.wheel.framework.helper.IocHelper;
import cn.edu.cqu.wheel.framework.util.ClassUtil;

/**
 * 加载相应的 Helper 类
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
            ClassHelper.class,
            BeanHelper.class,
            AopHelper.class,
            IocHelper.class,
            ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}