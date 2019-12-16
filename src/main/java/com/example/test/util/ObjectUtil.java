package com.example.test.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * @author: xy
 * @create: 2019-05-22
 */
public class ObjectUtil {

    public static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * 判断对象是否为null
     *
     * @param object
     * @return
     * @author ff
     */
    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断对象是否不为null
     *
     * @param object
     * @return
     * @author ff
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 判断对象任意一个是否为null
     *
     * @param objects
     * @return
     * @author ff
     */
    public static boolean anyIsNull(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断集合是否为null或者空
     *
     * @param colls
     * @return
     * @author ff
     */
    @Deprecated
    public static boolean isNullOrEmpty(Collection... colls) {
        boolean flag = false;
        for (Collection coll : colls) {
            if (coll == null || coll.isEmpty()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断集合是否不为null且不为空
     *
     * @param colls
     * @return
     * @author ff
     */
    public static boolean isNotNullAndNotEmpty(Collection... colls) {
        boolean flag = true;
        for (Collection coll : colls) {
            if (coll != null && !coll.isEmpty()) {
                continue;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }
    /**
     * Created by LogicArk on 2019/6/9
     *
     * @param
     * @return
     * @description 为某个类型的多个对象批量设置值
     */
    public static void setValueByBatch(List<Object> objectList, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        if (objectList == null || fieldName == null || fieldValue == null) {
            return;
        }
        for (Object object : objectList) {
            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, fieldValue);
        }
    }

    /**
     * 深克隆(后期优化)
     *
     * @param orig 源对象
     * @param dest 目标对象
     * @author ff
     */
    public static void copyProperties(final Object orig, final Object dest) {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param origs           源list对象
     * @param origsElementTpe 源list元素类型对象
     * @param <T>             源list元素类型
     * @Description：深拷贝list元素对象
     */
    public static <T> List<T> copyProperties(final List<T> origs, Class<T> origsElementTpe) {
        List<T> resultList = new ArrayList<>();
        if (origs == null) {
            return resultList;
        }
        try {
            for (T orig : origs) {
                T t = origsElementTpe.newInstance();
                copyProperties(orig, t);

                resultList.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return resultList;
    }

    /**
     * 通过Map的键值对拷贝到对象
     *
     * @param updateProperties
     * @param bean
     * @return
     * @author xiaoyin
     * @date 2019/11/3 14:45
     */
    public static <T> void copyPropertiesInclude(Map<String, Object> updateProperties, T bean) {
        Set<Map.Entry<String, Object>> abilityFiledSet = updateProperties.entrySet();
        for (Map.Entry<String, Object> entry : abilityFiledSet) {
            Object value = entry.getValue();
            if (value != null) {
                try {
                    BeanUtils.setProperty(bean, entry.getKey(), value);
                } catch (Exception ignore) {

                }
            }
        }
    }

    //---------------------------------------------------------------------
    // 对象类型判断
    //---------------------------------------------------------------------

    public static boolean isCollection(Object obj) {
        return obj instanceof Collection;
    }

    public static boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    public static boolean isNumber(Object obj) {
        return obj instanceof Number;
    }

    public static boolean isBoolean(Object obj) {
        return obj instanceof Boolean;
    }

    public static boolean isEnum(Object obj) {
        return obj instanceof Enum;
    }

    public static boolean isDate(Object obj) {
        return obj instanceof Date || obj instanceof TemporalAccessor;
    }

    public static boolean isCharSequence(Object obj) {
        return obj instanceof CharSequence;
    }

    /**
     * 判断对象是否为八大基本类型包装类除外即(boolean, byte, char, short, int, long, float, and double)<br/>
     *
     * @param obj
     * @return
     */
    public static boolean isPrimitive(Object obj) {
        return obj != null && obj.getClass().isPrimitive();
    }


    /**
     * 判断对象是否为包装类或者非包装类的基本类型
     *
     * @param obj
     * @return
     */
    public static boolean isWrapperOrPrimitive(Object obj) {
        return isPrimitive(obj) || isNumber(obj) || isCharSequence(obj) || isBoolean(obj);
    }
}
