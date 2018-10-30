package com.zscp.master.util.collection;

import com.zscp.master.util.ValidUtil;

import java.util.*;

/**
 * 封装一些集合相关度工具类
 */
public class CollectionUtil {


    private CollectionUtil() {
        // 静态类不可实例化
    }


    /**
     * 根据集合返回一个元素计数的 {@link Map}<br>
     * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
     * 例如：[a,b,c,c,c] 得到：<br>
     * a: 1<br>
     * b: 1<br>
     * c: 3<br>
     *
     * @param collection 集合
     * @param <T>        集合的对象
     * @return {@link Map}
     */
    public static <T> Map<T, Integer> countMap(Collection<T> collection) {
        HashMap<T, Integer> countMap = new HashMap<>();
        Integer count;
        for (T t : collection) {
            count = countMap.get(t);
            if (null == count) {
                countMap.put(t, 1);
            } else {
                countMap.put(t, count + 1);
            }
        }
        return countMap;
    }

    /**
     * 两个集合的并集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最多的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c, c]，此结果中只保留了三个c
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @param <T>   集合类型
     * @return 并集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> union(final Collection<T> coll1, final Collection<T> coll2) {
        final ArrayList<T> list = new ArrayList<>();
        if (ValidUtil.isEmptyCollection(coll1)) {
            list.addAll(coll2);
        } else if (ValidUtil.isEmptyCollection(coll2)) {
            list.addAll(coll1);
        } else {
            final Map<T, Integer> map1 = countMap(coll1);
            final Map<T, Integer> map2 = countMap(coll2);
            final Set<T> elts = new HashSet<T>(coll2);
            for (T t : elts) {
                for (int i = 0, m = Math.max(map1.get(t), map2.get(t)); i < m; i++) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * 多个集合的并集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最多的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c, c]，此结果中只保留了三个c
     *
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @param <T>        集合类型
     * @return 并集的集合，返回 {@link ArrayList}
     */
    @SafeVarargs
    public static <T> Collection<T> union(final Collection<T> coll1, final Collection<T> coll2, final Collection<T>... otherColls) {
        Collection<T> union = union(coll1, coll2);
        for (Collection<T> coll : otherColls) {
            union = union(union, coll);
        }
        return union;
    }

    /**
     * 两个集合的交集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最少的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c]，此结果中只保留了两个c
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @param <T>   集合类型
     * @return 交集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> intersection(final Collection<T> coll1, final Collection<T> coll2) {
        final ArrayList<T> list = new ArrayList<>();
        if ((!ValidUtil.isEmptyCollection(coll1)) && (!ValidUtil.isEmptyCollection(coll2))) {
            final Map<T, Integer> map1 = countMap(coll1);
            final Map<T, Integer> map2 = countMap(coll2);
            final Set<T> elts = new HashSet<T>(coll2);
            for (T t : elts) {
                for (int i = 0, m = Math.min(map1.get(t), map2.get(t)); i < m; i++) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * 多个集合的交集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留最少的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[a, b, c, c]，此结果中只保留了两个c
     *
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @param <T>        集合类型
     * @return 并集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> intersection(final Collection<T> coll1, final Collection<T> coll2, final Collection<T>... otherColls) {
        Collection<T> intersection = intersection(coll1, coll2);
        if (ValidUtil.isEmptyCollection(intersection)) {
            return intersection;
        }
        for (Collection<T> coll : otherColls) {
            intersection = intersection(intersection, coll);
            if (ValidUtil.isEmptyCollection(intersection)) {
                return intersection;
            }
        }
        return intersection;
    }

    /**
     * 两个集合的差集<br>
     * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留两个集合中此元素个数差的个数<br>
     * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
     * 结果：[c]，此结果中只保留了一个
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @param <T>   集合类型
     * @return 差集的集合，返回 {@link ArrayList}
     */
    public static <T> Collection<T> disjunction(final Collection<T> coll1, final Collection<T> coll2) {
        final ArrayList<T> list = new ArrayList<>();
        if ((!ValidUtil.isEmptyCollection(coll1)) && (!ValidUtil.isEmptyCollection(coll2))) {
            final Map<T, Integer> map1 = countMap(coll1);
            final Map<T, Integer> map2 = countMap(coll2);
            final Set<T> elts = new HashSet<T>(coll2);
            for (T t : elts) {
                for (int i = 0, m = Math.max(map1.get(t), map2.get(t)) - Math.min(map1.get(t), map2.get(t)); i < m; i++) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * 新建一个ArrayList
     *
     * @param values 数组
     * @param <T>    集合类型
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        ArrayList<T> arrayList = new ArrayList<T>(values.length);
        for (T t : values) {
            arrayList.add(t);
        }
        return arrayList;
    }

    /**
     * 去重集合
     *
     * @param collection 集合
     * @param <T>        集合类型
     * @return {@link ArrayList}
     */
    public static <T> ArrayList<T> distinct(Collection<T> collection) {
        if (ValidUtil.isEmptyCollection(collection)) {
            return new ArrayList<>();
        } else if (collection instanceof Set) {
            return new ArrayList<>(collection);
        } else {
            return new ArrayList<>(new LinkedHashSet<>(collection));
        }
    }

    /**
     * 截取数组的部分
     *
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @param <T>   集合类型
     * @return 截取后的数组，当开始位置超过最大时，返回null
     */
    public static <T> List<T> sub(List<T> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }
        final int size = list.size();
        if (end > size) {
            if (start >= size) {
                return null;
            }
            end = size;
        }
        return list.subList(start, end);
    }

    /**
     * 截取集合的部分
     *
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @param <T>   集合类型
     * @return 截取后的数组，当开始位置超过最大时，返回null
     */
    public static <T> List<T> sub(Collection<T> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        return sub(new ArrayList<T>(list), start, end);
    }

    /**
     * 从list中随机抽取元素
     *
     * @param list 要抽取的list
     * @param n    要抽取的数量
     * @return 新的list
     */
    public static List getRandomList(List list, int n) {
        Map map = new HashMap();
        List listNew = new ArrayList();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    listNew.add(list.get(random));
                }
            }
            return listNew;
        }
    }

    /**
     * 拆分list为固定大小的集合
     *
     * @param list     代拆分的list
     * @param pageSize 固定大小
     * @param <T>      类型
     * @return 拆分后的list
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {

        List<List<T>> listArray = new ArrayList<List<T>>(); // 创建list数组
        if (list.size() != 0) {
            int len = list.size();
            int limit = pageSize;
            int pages = 0;
            if (len % limit == 0) {
                pages = len / limit;
            } else {
                pages = len / limit + 1;
            }

            for (int page = 0; page < pages; page++) {
                int max = (page + 1) * limit;
                int start = page * limit;
                if (max > len) {
                    List<T> list1 = list.subList(start, len);
                    listArray.add(list1);
                } else {
                    List<T> list1 = list.subList(start, max);
                    listArray.add(list1);
                }
            }

        }

        return listArray;
    }

    /**
     * 比较2个字符串数组是否相等，即使顺序不一样，但是元素一样也当做相等
     *
     * @param array1 素组1
     * @param array2 素组2
     * @return 是否相等
     */
    public static boolean equalsValue(String[] array1, String[] array2) {
        if(array1 == null){
            return array2==null;
        }else{
            if(array2 == null) {
                return false;
            }
        }

        Arrays.sort(array1);
        Arrays.sort(array2);
        return Arrays.equals(array1, array2);
    }
}
