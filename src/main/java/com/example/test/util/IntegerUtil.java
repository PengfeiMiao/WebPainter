package com.example.test.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: xy
 * @create: 2019-05-13
 */
public class IntegerUtil {

    public static Integer strToInt(String value) {
        if (StringUtils.isBlank(value)) {
            return 0;
        }
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isInteger(String value) {
        if (value == null) {
            return false;
        }
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Integer strToInteger(String str, Integer defaultVal) {
        Integer res = defaultVal;
        try {
            res = Integer.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Long objToLong(Object str, Long defaultVal) {
        if (str == null) {
            return defaultVal;
        }
        Long res = defaultVal;
        try {
            res = Long.valueOf(str.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Created by LogicArk on 2019/5/31
     *
     * @param
     * @return
     * @description 计算二维数组的列的总和
     */
    public static List<Integer> calculateColumnSum(List<List<Integer>> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        int columnMaxLength = 0;
        for (List<Integer> per : list) {
            if (per.size() > columnMaxLength) {
                columnMaxLength = per.size();
            }
        }
        if (columnMaxLength == 0) {
            return new ArrayList<>();
        }
        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < columnMaxLength; i++) {
            Integer columnSum = 0;
            //遍历每一行
            for (List<Integer> row : list) {
                if (row.size() - 1 >= i) {
                    columnSum += row.get(i);
                }
            }
            resultList.add(columnSum);
        }
        return resultList;
    }

    /**
     * Created by LogicArk on 2019/6/10
     * @description 获取奇数集合
     * @param
     * @return
     */
    public static Set<Integer> getOddNumberSet(Set<Integer> integerSet){
        if (integerSet == null) {
            return new HashSet<>();
        }
        if (integerSet.isEmpty()){
            return integerSet;
        }
        Set<Integer> oddNumberSet = new HashSet<>();
        for (Integer per : integerSet){
            if (per != null && per.intValue() % 2 == 1 ){
                oddNumberSet.add(per);
            }
        }
        return oddNumberSet;
    }

    /**
     * Created by LogicArk on 2019/6/10
     * @description 获取偶数集合
     * @param
     * @return
     */
    public static Set<Integer> getEvenNumberSet(Set<Integer> integerSet){
        if (integerSet == null) {
            return new HashSet<>();
        }
        if (integerSet.isEmpty()){
            return integerSet;
        }
        Set<Integer> evenNumberSet = new HashSet<>();
        for (Integer per : integerSet){
            if (per != null && per.intValue() % 2 == 0 ){
                evenNumberSet.add(per);
            }
        }
        return evenNumberSet;
    }

}
