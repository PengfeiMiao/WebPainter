package com.example.test.util.compare;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

/**
 * @author: xy
 * @create: 2019-06-14
 */
public class ComparatorUtil {

    public static String ASC = "asc";
    public static String DESC = "desc";

    /**
     * @param list     需要排序的集合
     * @param property 属性值  即获取pojo的属性值
     * @param sort     指定desc为降序否则按升序排列
     * @return
     * @author xiaoyin
     * @date 2018/8/30 14:14
     */
    public static <T> List<T> sort(List<T> list, final String property, final String sort) {
        String[] properties = {property};
        String[] sorts = {sort};
        return sort(list, properties, sorts);
    }

    /**
     * 按两个字段值进行排序
     *
     * @param list      需要排序的集合
     * @param property1 属性值 即获取pojo的属性值
     * @param property2 属性值 即获取pojo的属性值
     * @param sort      指定desc为降序否则按升序排列
     * @return
     * @author xiaoyin
     * @date 2018/8/30 14:52
     */
    public static <T> List<T> sort(List<T> list, final String property1, final String property2, final String sort) {
        String[] properties = {property1, property2};
        String[] sorts = {sort};
        return sort(list, properties, sorts);
    }

    private static String toUpperFirstChar(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }


    private static String[] getMethodName(String[] properties) {
        String[] methods = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            methods[i] = "get" + toUpperFirstChar(properties[i]);
        }
        return methods;
    }

    /**
     * 自定义排序
     *
     * @param list
     * @param properties 多个需要排序的属性
     * @param sorts      排序方式 升序或者降序
     * @return
     * @author xiaoyin
     * @date 2018/9/2 17:47
     */
    public static <T> List<T> sort(List<T> list, final String[] properties, final String[] sorts) {
        String[] methods = getMethodName(properties);
        if (methods != null && methods.length > 0) {
            for (int i = properties.length - 1; i >= 0; i--) {
                final String method = methods[i];
                String tmpSort = ASC;
                if (sorts != null && sorts.length > i && sorts[i] != null) {
                    tmpSort = sorts[i];
                }
                final String sort = tmpSort;

                list.sort(new Comparator<T>() {
                    @Override
                    public int compare(Object a, Object b) {
                        int ret = 0;
                        try {
                            Method m1a = a.getClass().getMethod(method);
                            Method m1b = b.getClass().getMethod(method);
//                            /**支持多层嵌套值排序**/
//                            Object obj1a = PropertyUtils.getNestedProperty(a, property);
//                            Object obj1b = PropertyUtils.getNestedProperty(b, property);

                            Object obj1a = m1a.invoke(a);
                            Object obj1b = m1b.invoke(b);

                            if (obj1a == null && obj1b == null) {
                                return 0;
                            }

                            if (obj1a == null || obj1b == null) {
                                if (sort != null && DESC.equals(sort)) {
                                    if (obj1a == null) {
                                        ret = -1;
                                    }
                                    if (obj1b == null) {
                                        ret = 1;
                                    }
                                } else {
                                    if (obj1a == null) {
                                        ret = 1;
                                    }
                                    if (obj1b == null) {
                                        ret = -1;
                                    }
                                }
                                return ret;
                            }
                            if (obj1a instanceof Integer && obj1b instanceof Integer) {
                                ret = ((Integer) obj1a).compareTo((Integer) obj1b);
                            } else if (obj1a instanceof Double && obj1b instanceof Double) {
                                ret = ((Double) obj1a).compareTo((Double) obj1b);
                            } else if (obj1a instanceof Float && obj1b instanceof Float) {
                                ret = ((Float) obj1a).compareTo((Float) obj1b);
                            } else if (obj1a instanceof Long && obj1b instanceof Long) {
                                ret = ((Long) obj1a).compareTo((Long) obj1b);
                            } else {
                                ret = obj1a.toString().compareTo(obj1b.toString());
                            }

                            //倒序
                            if (sort != null && DESC.equals(sort)) {
                                return -ret;
                                //正序
                            } else {
                                return ret;
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return ret;
                    }
                });
            }
        }
        return list;
    }

}
