package com.zscp.master.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * 封装一些正则相关的操作
 */
public final class RegUtil {

    /**
     * 获取符合reg正则表达式的字符串在String中出现的次数
     *
     * @param str 需要处理的字符串
     * @param reg 正则
     * @return 出现的次数
     */
    public final static int countSubStrReg(String str, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
            i++;
        }
        return i;
    }

    /**
     * 获得匹配的字符串
     *
     * @param regex      匹配的正则
     * @param content    被匹配的内容
     * @param groupIndex 匹配正则的分组序号
     * @return 匹配后得到的字符串，未匹配返回null
     */
    public static String getMatchValue(String regex, String content, int groupIndex) {
        if (null == content || null == regex) {
            return null;
        }

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return getMatchValue(pattern, content, groupIndex);
    }

    /**
     * 获得匹配的字符串
     *
     * @param pattern    编译后的正则模式
     * @param content    被匹配的内容
     * @param groupIndex 匹配正则的分组序号
     * @return 匹配后得到的字符串，未匹配返回null
     */
    public static String getMatchValue(Pattern pattern, String content, int groupIndex) {
        if (null == content || null == pattern) {
            return null;
        }

        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(groupIndex);
        }
        return null;
    }

    /**
     * 取得内容中匹配的所有结果
     *
     * @param pattern 编译后的正则模式
     * @param content 被查找的内容
     * @param group 正则的分组
     * @param collection 返回的集合类型
     * @return 结果集
     */
    public static <T extends Collection<String>> T getAllMatchValues(Pattern pattern, String content, int group, T collection) {
        if(null == pattern || null == content){
            return null;
        }

        if(null == collection){
            throw new NullPointerException("Null collection param provided!");
        }

        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            collection.add(matcher.group(group));
        }
        return collection;
    }

    /**
     * 取得内容中匹配的所有结果
     *
     * @param regex  匹配的正则
     * @param content 被查找的内容
     * @param group 正则的分组
     * @param collection 返回的集合类型
     * @return 结果集
     */
    public static <T extends Collection<String>> T getAllMatchValues(String regex, String content, int group, T collection) {
        if (null == content || null == regex) {
            return null;
        }

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return getAllMatchValues(pattern,content,group,collection);
    }
}
