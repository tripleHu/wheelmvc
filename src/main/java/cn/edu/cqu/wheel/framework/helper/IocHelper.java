package cn.edu.cqu.wheel.framework.helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;

import cn.edu.cqu.wheel.framework.anotation.Inject;
import cn.edu.cqu.wheel.framework.util.ArrayUtil;
import cn.edu.cqu.wheel.framework.util.CollectionUtil;
import cn.edu.cqu.wheel.framework.util.ReflectionUtil;


/**
 * 依赖注入助手类
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();//获取容器里的所有Bean
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {//遍历容器里的所有Bean
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();//获取Bean所有声明的字段
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {//
                        if (beanField.isAnnotationPresent(Inject.class)) {//是@Inject注解则注入对象
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
