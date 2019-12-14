package com.example.test.util;

import java.util.*;

/**
 * @author: xy
 * @create: 2019-07-18
 */
public class CollectionUtil {

    public static boolean isNull(Collection collection) {
        return collection == null;
    }

    /**
     * 判断是否为空.
     */
    public static boolean isNullOrEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotNullAndEmpty(Collection collection) {
        return !CollectionUtil.isNullOrEmpty(collection);
    }

    /**
     * 判断集合是否为null或者空
     *
     * @param colls
     * @return
     * @author ff
     */
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
     * 求 集合 ab 并集
     **/
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<T>(a);
        result.addAll(b);
        return result;
    }

    /**
     * 求多个集合的并集
     **/
    public static <T> Collection<T> union(Collection<T> coll1, Collection<T> coll2, Collection<T>... others) {
        Collection<T> union = union(coll1, coll2);

        for (Collection<T> coll : others) {
            union = union(union, coll);
        }

        return union;
    }

    /**
     * 返回集合a对集合b的补集 的新List.
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<T>(a);
        for (T element : b) {
            list.remove(element);
        }

        return list;
    }

    /**
     * 返回a与b的交集的新List.
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();

        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    /**
     * 求多个集合的交集
     **/
    public static <T> Collection<T> intersection(Collection<T> coll1, Collection<T> coll2, Collection<T>... others) {
        Collection<T> result = intersection(coll1, coll2);
        if (isNullOrEmpty(result)) {
            return result;
        } else {
            for (Collection<T> coll : others) {
                result = intersection(result, coll);
                if (isNullOrEmpty(result)) {
                    return result;
                }
            }

            return result;
        }
    }

    /**
     * 将List等分为指定的几个集合
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        /**计算余数**/
        int remainder = source.size() % n;
        /**商**/
        int number = source.size() / n;
        /***偏移量*/
        int offset = 0;
        for (int i = 0; i < n; i++) {
            List<T> value;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 构建List
     **/
    @SafeVarargs
    public static <T> List<T> makeList(T... args) {
        if (args == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(args);
    }


    /**
     * 构建Set
     **/
    @SafeVarargs
    public static <T> Set<T> makeSet(T... args) {
        if (args == null) {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(args));
    }

    /**
     * 往指定的map里面放值
     *
     * @param filledMap 被填充的map
     * @param fillKey   填充key
     * @param fillValue 填充值
     * @param <T>
     * @return
     * @author ff
     */
    public static <T, V> Map<V, List<T>> fillValueToList(Map<V, List<T>> filledMap, V fillKey, T fillValue) {
        List<T> objectList = null;
        if (filledMap.containsKey(fillKey)) {
            objectList = filledMap.get(fillKey);
        } else {
            objectList = new ArrayList<>();
        }
        if (fillValue != null) {
            objectList.add(fillValue);
        }
        filledMap.put(fillKey, objectList);

        return filledMap;
    }

    public static <T, V> Map<V, Set<T>> fillValueToSet(Map<V, Set<T>> filledMap, V fillKey, T fillValue) {
        Set<T> objectSet = null;
        if (filledMap.containsKey(fillKey)) {
            objectSet = filledMap.get(fillKey);
        } else {
            objectSet = new HashSet<>();
        }
        if (fillValue != null) {
            objectSet.add(fillValue);
        }
        filledMap.put(fillKey, objectSet);

        return filledMap;
    }

    /**
     * 计算某个容器中所有数的总值
     *
     * @param collection
     * @return
     * @author ff
     */
    public static Integer calculateTotalValue(Collection<Integer> collection) {
        Integer sum = 0;

        for (Integer value : collection) {
            sum += value;
        }

        return sum;
    }

    /**
     * 去掉容器中的null值
     *
     * @param value
     * @return
     * @author ff
     */
    public static <T> Set<T> clearNullValue(Set<T> value) {
        if (isNullOrEmpty(value)) {
            return new HashSet<>();
        }

        Iterator<T> iterator = value.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (t == null) {
                iterator.remove();
            }
        }
        return value;
    }

    public static <T> List<T> clearNullValue(List<T> value) {
        Set<T> clearValue = clearNullValue(new HashSet<>(value));
        return new ArrayList<>(clearValue);
    }

    /**
     * 判断两个集合所有的元素是否相等
     *
     * @param a 集合a
     * @param b 集合b
     * @return
     * @author xiaoyin
     * @date 2019/9/18 19:51
     */
    public static <T> boolean collectionAllEquals(Collection<T> a, Collection<T> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (T element : a) {
            if (!b.contains(element)) {
                return false;
            }
        }
        return true;
    }

}
