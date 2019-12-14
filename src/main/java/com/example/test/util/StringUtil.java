package com.example.test.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: xy
 * @create: 2019-05-13
 */
public class StringUtil {

    public static final String COMMA = ",";

    public static String safeToString(Object obj, String defaultStr) {
        try {
            return obj.toString();
        } catch (Exception e) {

        }

        return defaultStr;
    }

    /**
     * @param
     * @return
     * @author LogicArk
     * @date 2018/8/5
     * @description 判断某个字符串是否是 数字( 整数或者小数 )
     */
    public static boolean isNumeric(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        int sz = str.length();
        int numOfPoint = 0;
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                //如果字符串中，小数点的个数超过1，则不是数字
                if ('.' == str.charAt(i)) {
                    ++numOfPoint;
                    if (numOfPoint <= 1) {
                        continue;
                    }
                }
                return false;
            }
        }
        return true;
    }

    /**
     * @param
     * @return
     * @author LogicArk
     * @date 2018/8/5
     * @description 判断字符串是否是整数
     * StringUtils.isIntNumeric(null)   = false
     * StringUtils.isIntNumeric("")     = false
     * StringUtils.isIntNumeric("  ")   = false
     * StringUtils.isIntNumeric("123")  = true
     * StringUtils.isIntNumeric("12 3") = false
     * StringUtils.isIntNumeric("ab2c") = false
     * StringUtils.isIntNumeric("12-3") = false
     * StringUtils.isIntNumeric("12.3") = false
     * StringUtils.isIntNumeric("012") = true
     */
    public static boolean isIntNumeric(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (str.charAt(i) == '.') {
                return false;
            }
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Created by LogicArk on 2019/3/6
     *
     * @param
     * @return
     * @description 判断是否整数（有前缀0的也通过，例如：012）
     */
    public static boolean isIntNumericWithPrefixZero(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {

            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * created by LogicArk on 2018/8/28
     *
     * @param strList String类型的List
     * @return List<Integer> Integer类型的List
     * @description 将List<String> 转为 List<Integer>
     * 传入的List中的字符串需要本身只包含 0 到 9 ，否则无法转为整数
     */
    public static List<Integer> convertToIntegerList(List<String> strList) {
        if (strList == null) {
            return new ArrayList<>(0);
        }
        List<Integer> resultList = new ArrayList<>(0);
        for (String per : strList) {
            if (isIntNumericWithPrefixZero(per)) {
                resultList.add(Integer.parseInt(per));
            }
        }
        return resultList;
    }

    public static String getStrNotNull(String str, String defaultValue) {
        return str == null ? defaultValue : str;
    }


    /**
     * 大写的字符串转数字 A -0  ... Z-25  BA 26
     *
     * @param upperChar
     * @return
     */
    public static Long upperCharToNum(String upperChar) {
        if (upperChar == null) {
            return null;
        }
        if (upperChar.matches("^[A-Z]+$")) {
            char[] ch = upperChar.toCharArray();
            Long multiplyNum = 1L;
            Long result = 0L;
            for (int i = ch.length - 1; i >= 0; i--) {
                result += (ch[i] - 'A') * multiplyNum;
                multiplyNum *= 26;
            }
            return result;
        }

        return null;
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     * <p>
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is
     * not empty and not null and not whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {
        return !StringUtil.isBlank(str);
    }


    /**
     * 集合转数组
     *
     * @param list
     * @return
     * @author xiaoyin
     * @date 2018/11/6 17:16
     */
    public static String[] listToArray(List<String> list) {
        if (list == null) {
            return null;
        }
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * 防sql注入替换掉单引号(')，分号(;) 和 注释符号(--)
     *
     * @param str
     * @return
     * @author xiaoyin
     * @date 2019/1/14 14:21
     */
    public static String sqlInjection(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("([';])+|(--)+", "");

    }

    /**
     * 分隔指定的字符串为List
     *
     * @param str
     * @param regex
     * @return
     * @author xiaoyin
     * @date 2018/12/12 16:26
     */
    public static List<String> separatorStringWithList(String str, String regex) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(str) || StringUtils.isBlank(regex)) {
            return list;
        }
        if (str.contains(regex)) {
            list = Arrays.asList(str.split(regex));
        } else {
            list.add(str);
        }
        List<String> resultList = new ArrayList<>();
        for (String per : list) {
            if (StringUtils.isNotBlank(per)) {
                resultList.add(per);
            }
        }
        return resultList;
    }

    /**
     * 拼接字符串 如 '1001','1002'形式
     *
     * @param list
     * @return
     * @author xiaoyin
     * @date 2018/12/12 18:40
     */
    public static String joinListWithCommaInSql(List<String> list) {
        String str = StringUtils.join(list, "','");
        str = "'" + str + "'";
        return str;
    }

    public static String joinListWithCommaInSql(Set<String> list) {
        String str = StringUtils.join(list, "','");
        str = "'" + str + "'";
        return str;
    }

    /**
     * 加list的值的总和
     *
     * @param list
     * @return
     * @author xiaoyin
     * @date 2019/5/23 11:46
     */
    public static String countTotalSum(List<String> list) {

        BigDecimal bigDecimal = new BigDecimal(0);
        for (String per : list) {
            if (StringUtils.isBlank(per) && !StringUtils.isNumeric(per)) {
                continue;
            }
            BigDecimal decimal = new BigDecimal(per);
            bigDecimal = bigDecimal.add(decimal);
        }
        return bigDecimal.toString();
    }

    /**
     * 返回二进制数的 按位与结果
     * 可用于比较是否有交集 返回结果包含1 则代表有交集
     *
     * @param binary1
     * @param binary2
     * @return
     * @author xiaoyin
     * @date 2019/5/30 11:42
     */
    public static String getBitwiseAndString(String binary1, String binary2) {
        if (StringUtils.isBlank(binary1) || StringUtils.isBlank(binary2)) {
            return null;
        }
        StringBuilder a = new StringBuilder(binary1);
        StringBuilder b = new StringBuilder(binary2);
        int aLength = a.length();
        int bLength = b.length();

        /**将二进制格式的数不足位补0**/
        if (aLength != bLength) {
            if (aLength > bLength) {
                for (int i = 0; i < aLength - bLength; i++) {
                    b.append("0");
                }
            } else {
                for (int i = 0; i < bLength - aLength; i++) {
                    a.append("0");
                }
            }
        }

        /**将二进制格式的数进行与运算**/
        StringBuilder builder = new StringBuilder();
        if (a.length() == b.length()) {
            for (int i = 0; i < a.length(); i++) {
                String c = String.valueOf(a.charAt(i));
                String d = String.valueOf(b.charAt(i));
                builder.append(Integer.valueOf(c) * Integer.valueOf(d));
            }
        }
        return builder.toString();
    }

    /**
     * Created by LogicArk on 2019/5/31
     *
     * @param
     * @return
     * @description 从十六进制字符串，获取十进制的List
     */
    public static List<Integer> getDecimalListFromHexStr(String hexStr) {
        if (StringUtils.isBlank(hexStr)) {
            return new ArrayList<>(0);
        }
        int[] array = getDecimalArrayFromHexStr(hexStr);
        return Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    /**
     * Created by LogicArk on 2019/5/31
     *
     * @param
     * @return
     * @description 从十六进制字符串，获取十进制的数组
     */
    public static int[] getDecimalArrayFromHexStr(String hexStr) {
        if (StringUtils.isBlank(hexStr)) {
            return new int[0];
        }
        int[] array = new int[hexStr.length()];
        for (int i = 0; i < hexStr.length(); i++) {
            Integer num;
            try {
                num = Integer.valueOf("" + hexStr.charAt(i), 16);
            } catch (NumberFormatException e) {
                num = 0;
            }
            if (num == null) {
                num = 0;
            }
            array[i] = num.intValue();
        }
        return array;
    }


    /**
     * Created by LogicArk on 2019/6/6
     *
     * @param
     * @return
     * @description 获取二进制格式字符串（例如：1,2,3,5,7,8 表示为 11101011 ）
     */
    public static String getBinaryStr(String intNumbersStr, String separator) {
        if (intNumbersStr == null || separator == null) {
            return null;
        }
        Set<Integer> integerSet = convertIntNumbersStrToIntegerSet(intNumbersStr, separator);
        return getBinaryStr(integerSet);
    }

    /**
     * Created by LogicArk on 2019/6/6
     *
     * @param intNumbersStr int类型的数字字符串
     * @param separator     字符串中多个数字的分隔符号
     * @return Set<Integer>
     * @description 将int类型的数字字符串，转为Set<Integer>
     */
    public static Set<Integer> convertIntNumbersStrToIntegerSet(String intNumbersStr, String separator) {
        if (intNumbersStr == null || separator == null) {
            return new HashSet<>();
        }
        String[] intNumberStrArray = intNumbersStr.split(separator);
        if (intNumberStrArray.length == 0) {
            return new HashSet<>();
        }
        Set<Integer> integerSet = new TreeSet<>();
        for (String intNumberStr : intNumberStrArray) {
            if (isIntNumeric(intNumberStr)) {
                integerSet.add(Integer.parseInt(intNumberStr));
            }
        }
        return integerSet;
    }

    /**
     * Created by LogicArk on 2019/6/6
     *
     * @param
     * @return
     * @description 获取二进制格式字符串（例如：1,2,3,5,7,8 表示为 11101011 ）
     */
    public static String getBinaryStr(Set<Integer> integerSet) {
        if (integerSet == null || integerSet.isEmpty()) {
            return null;
        }
        List<Integer> integerList;
        if (integerSet instanceof TreeSet) {
            integerList = new ArrayList<>(integerSet);
        } else {
            integerList = new ArrayList<>(new TreeSet<>(integerSet));
        }
        /** 找出最大值 */
        Integer maxValue = null;
        for (int i = integerList.size() - 1; i >= 0; i--) {
            if (integerList.get(i) != null) {
                maxValue = integerList.get(i);
                break;
            }
        }
        if (maxValue == null) {
            return null;
        }
        String[] numbericMarkArray = new String[maxValue.intValue()];
        Arrays.fill(numbericMarkArray, "0");

        for (Integer per : integerList) {
            if (per != null) {
                numbericMarkArray[per - 1] = "1";
            }
        }
        String numericMarkStr = StringUtils.join(numbericMarkArray);
        return numericMarkStr;
    }


    /**
     * 对多个字符串进行判断是否有一个为空或空字符
     *
     * @param value
     * @return
     * @author xiaoyin
     * @date 2019/7/26 10:59
     */
    public static boolean hasOneBlank(String... value) {
        if (value == null) {
            return true;
        }
        for (String per : value) {
            boolean hasBlank = StringUtil.isBlank(per);
            if (hasBlank) {
                return true;
            }
        }
        return false;

    }

    public static String sqlOrInSearch(List<String> valueList, String column) {
        return sqlOrInSearch(valueList, column, null);
    }

    public static String sqlOrInSearch(Set<String> valueSet, String column) {
        return sqlOrInSearch(new ArrayList<>(valueSet), column, null);
    }

    /**
     * 拼接sql,用IN拼接
     * 超过 限制数量用转用OR凭借
     *
     * @param valueList 要搜索的值
     * @param column    数据库的字段
     * @param inNum     限制in的数量(大于1000,默认1000,为空,则默认500)
     * @return
     * @author xiaoyin
     * @date 2019/7/30 17:24
     */
    public static String sqlOrInSearch(List<String> valueList, String column, Integer inNum) {
        if (CollectionUtil.isNullOrEmpty(valueList) || StringUtil.isBlank(column)) {
            return "";
        }
        if (inNum == null) {
            inNum = 500;
        }
        if (inNum > 1000) {
            inNum = 1000;
        }
        int number = valueList.size() / inNum;
        if (valueList.size() % inNum != 0) {
            number++;
        }
        List<List<String>> averageCourseIds = CollectionUtil.averageAssign(valueList, number);
        List<String> searchInList = new ArrayList<>();
        for (List<String> courseIds : averageCourseIds) {
            String inSql = StringUtil.joinListWithCommaInSql(courseIds);
            searchInList.add("" + column + " IN (" + inSql + ")");
        }
        return StringUtils.join(searchInList, "\tOR\t");
    }

    /**
     * 改变字符串中指定位置的值
     *
     * @param source     需要改变的字符串
     * @param value      填充值
     * @param beginIndex
     * @param endIndex
     * @return
     * @author ff
     */
    public static String replace(String source, char value, int beginIndex, int endIndex) {
        if (isBlank(source)) {
            return source;
        }

        char[] sourceArray = source.toCharArray();

        for (int i = 0; i < sourceArray.length; ++i) {
            if (i >= beginIndex && i <= endIndex) {
                sourceArray[i] = value;
            }
        }

        return String.valueOf(sourceArray);
    }


    /**
     * 将字符串转换为Float型浮点数，默认保留两位小数
     *
     * @param source 原始字符串
     * @param num    保留小数点后指定位数
     * @return
     * @author mpf
     */
    public static Float convertStringToFloat(String source, int num) {
        if (!StringUtil.isNumeric(source)) {
            source = "-1.0f";
        }
        String unit = "0";
        StringBuilder pattern = new StringBuilder("###.");
        for (int i = 0; i < num; i++) {
            pattern.append(unit);
        }
        Float f = Float.parseFloat(source);
        DecimalFormat df = new DecimalFormat(pattern.toString());
        String s = df.format(f);
        return Float.parseFloat(s);
    }

    /**
     * 将字符串List转换为List<Float>，默认保留两位小数
     *
     * @param source 原始字符串
     * @param num    保留小数点后指定位数
     * @author mpf
     */
    public static List<Float> convertStringListToFloat(List<String> source, int num) {
        List<Float> floatList = new ArrayList<>();
        String unit = "0";
        StringBuilder pattern = new StringBuilder("###.");
        for (int i = 0; i < num; i++) {
            pattern.append(unit);
        }
        for (String item : source) {
            if (!StringUtil.isNumeric(item)) {
                item = "-1.0f";
            }
            Float f = Float.parseFloat(item);
            DecimalFormat df = new DecimalFormat(pattern.toString());
            String s = df.format(f);
            floatList.add(Float.parseFloat(s));
        }
        return floatList;
    }

    /**
     * String转换为xx.5或者整数格式字符串
     *
     * @param source
     * @return
     * @author mpf
     */
    public static String convertStringToPointFive(float source) {
        return String.valueOf((float)Math.round(source*2)/2).replace(".0","");
    }

    /**
     * 将List<Float>转换为List<String>
     *
     * @param source
     * @return
     * @author mpf
     */
    public static List<String> convertFloatListToString(List<Float> source) {
        List<String> strList = new ArrayList<>();
        for (Float item : source) {
            if (item == null) {
                item = -1.0f;
            }
            strList.add(String.valueOf(item));
        }
        return strList;
    }

    /**
     * 将二进制110011字符串改为十进制数字1256
     *
     * @return
     * @author: ltb
     * @date 2019/11/21
     * @params
     */
    public static List<Integer> convertBinaryStrToNum(String str) {
        List<Integer> list = new ArrayList<>();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 1 || chars[i] == '1') {
                list.add(i + 1);
            }
        }
        return list;
    }

}
