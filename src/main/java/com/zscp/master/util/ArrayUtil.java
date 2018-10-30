package com.zscp.master.util;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 数组工具类
 */
public class ArrayUtil {

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @param <T>    类型
     * @return 合并后的数组
     */
    public static <T> T[] addAll(T[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }
        int length = 0;
        for (T[] array : arrays) {
            if (array == null) {
                continue;
            }
            length += array.length;
        }
        T[] result = (T[]) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), length);
        length = 0;
        for (T[] array : arrays) {
            if (array == null) {
                continue;
            }
            System.arraycopy(array, 0, result, length, array.length);
            length += array.length;
        }
        return result;
    }

    /**
     * 克隆数组
     *
     * @param array 被克隆的数组
     * @param <T>   类型
     * @return 新数组
     */
    public static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 数组转List
     * @param array 数组
     * @param <T> 类型
     * @return list
     */
    public static <T> ArrayList<T> asList(T[] array){
        ArrayList<T> list = new ArrayList<T>();
        for (T t: array) {
            list.add(t);
        }
        return list;
    }

    /**
     * 映射键值（参考Python的zip()函数）<br>
     * 例如：<br>
     * keys = [a,b,c,d]<br>
     * values = [1,2,3,4]<br>
     * 则得到的Map是 {a=1, b=2, c=3, d=4}<br>
     * 如果两个数组长度不同，则只对应最短部分
     *
     * @param keys   键列表
     * @param values 值列表
     * @param <T>    类型
     * @param <K>    类型
     * @return Map
     */
    public static <T, K> Map<T, K> zip(T[] keys, K[] values) {
        if ((keys == null || keys.length == 0) || (values == null || values.length == 0)) {
            return null;
        }
        final int size = Math.min(keys.length, values.length);
        final Map<T, K> map = new HashMap<T, K>((int) (size / 0.75));
        for (int i = 0; i < size; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @param <T>   类型
     * @return 是否包含
     */
    public static <T> boolean contains(T[] array, T value) {
        final Class<?> componetType = array.getClass().getComponentType();
        boolean isPrimitive = false;
        if (null != componetType) {
            isPrimitive = componetType.isPrimitive();
        }
        for (T t : array) {
            if (t == value) {
                return true;
            } else if (false == isPrimitive && null != value && value.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
            throw new NullPointerException("Object check for isArray is null");
        }
        return obj.getClass().isArray();
    }


    /**
     * 从数组中随机抽取元素
     * @param arr 要抽取的数组
     * @param n 抽取数量
     * @return 新的数组
     */
    public static int[] getRandomArray(int[] arr, int n) {
        Map map = new HashMap();
        int[] arrNew = new int[n];
        if(arr.length<=n){
            return arr;
        }else{
            int count = 0;//新数组下标计数
            while(map.size()<n){
                int random = (int) (Math.random() * arr.length);
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    arrNew[count++] = arr[random];
                }
            }
            return arrNew;
        }
    }
}
