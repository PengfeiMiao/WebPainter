package com.example.test.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 空值判断(字符串)
 * 支持传对象+需要验证的字段数组形式
 * 支持传对象+需要在某种行为下验证形式（配合PropsLabel注解使用）
 * 支持传对象列表+需要在某种行为下验证形式（配合PropsLabel注解使用）
 * Created by ff on 2019/5/20.
 */
public class CheckEmptyUtils {

    /**
     * PropsLabel注解中常量标签
     */
    public static final String LABEL_ADD = "add";
    public static final String LABEL_UPDATE = "update";
    public static final String LABEL_DELETE = "delete";
    public static final String LABEL_SELECT = "select";

    /**
     * 检查对象中指定字段是否为空，null，""等
     *
     * @param obj
     * @param checkAttNames 需要检查的字段
     * @param <K>
     * @return
     */
    public static <K> boolean checkObjectAttIsEmpty(K obj, String[] checkAttNames) {
        List<String> resultList = validateAttIsEmpty(obj, checkAttNames);
        if (!resultList.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 验证对象中指定字段是否为null，空，""
     * 并且返回为空字段List列表
     *
     * @param obj
     * @param checkAttNames
     * @param <K>
     * @return
     */
    public static <K> List<String> validateAttIsEmpty(K obj, String[] checkAttNames) {
        List<String> resultList = new ArrayList<>();
        /**
         * 空值判断
         */
        if (obj == null || checkAttNames.length == 0) {
            return resultList;
        }

        List<Field> fieldList = new ArrayList<Field>();

        getObjAllField(obj, fieldList);

        /**
         * 遍历对象属性
         */
        for (Field field : fieldList) {
            /**
             * 设置访问权限
             */
            field.setAccessible(true);
            String name = field.getName();
            /**
             * 循环从数组中查询该值是否存在
             */
            boolean nameExistFlag = false;
            for (String s : checkAttNames) {
                if (s.equals(name)) {
                    nameExistFlag = true;
                    break;
                }
            }
            if (!nameExistFlag) {
                continue;
            }
            /**
             * 判断某属性是否为空或长度为0或由空白符(whitespace)构成
             */
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null) {
                resultList.add(name);
                continue;
            }
            String strValue = String.valueOf(value);
            if (StringUtils.isBlank(strValue)) {
                resultList.add(name);
            }
        }
        return resultList;
    }

    public static <K> List<String> validateAttIsEmpty(List<K> objList, String[] checkAttNames) {
        List<String> resultList = new ArrayList<>();
        if (objList == null || checkAttNames == null) {
            resultList.add("null对象");
            return resultList;
        }

        /**
         * 单个循环比较
         */
        for (K k : objList) {
            List<String> validateList = validateAttIsEmpty(k, checkAttNames);
            if (validateList.size() != 0) {
                resultList = validateList;
                break;
            }
        }
        return resultList;
    }

    /**
     * 验证对象中所有字段是否为null,""等
     *
     * @param obj
     * @param <K>
     * @return list 空值属性列表
     */
    public static <K> List<String> validateAttIsEmpty(K obj) {
        List<String> resultList = new ArrayList<>();
        /**
         * 空值判断
         */
        if (obj == null) {
            return resultList;
        }

        List<Field> fieldList = new ArrayList<Field>();
        /**
         * 获取所有字段（本身和父类（N））
         */
        getObjAllField(obj, fieldList);

        /**
         * 遍历对象属性
         */
        for (Field field : fieldList) {
            /**
             * 设置访问权限
             */
            field.setAccessible(true);
            String name = field.getName();
            /**
             * 判断某属性是否为空或长度为0或由空白符(whitespace)构成
             */
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null) {
                resultList.add(name);
                continue;
            }
            String strValue = String.valueOf(value);
            if (StringUtils.isBlank(strValue)) {
                resultList.add(name);
            }
        }
        return resultList;
    }

    /**
     * 获取其自身和父类（N）的属性
     *
     * @param obj
     * @param fieldList
     */
    private static void getObjAllField(Object obj, List<Field> fieldList) {
        if (obj == null || fieldList == null) {
            return;
        }
        /**
         * 获取其本身和N级父类的属性
         */
        Class objClass = obj.getClass();
        fieldList.addAll(new ArrayList<Field>(Arrays.asList(objClass.getDeclaredFields())));

        while (objClass != null) {
            objClass = objClass.getSuperclass();
            if (objClass != null) {
                fieldList.addAll(new ArrayList<Field>(Arrays.asList(objClass.getDeclaredFields())));
            }
        }
    }

}
