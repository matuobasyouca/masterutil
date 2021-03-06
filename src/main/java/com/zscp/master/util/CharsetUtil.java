package com.zscp.master.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Description:
 * 编码相关的封装类
 */
public final class CharsetUtil {
    /**
     * 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块
     */
    public static final String US_ASCII = "US-ASCII";

    /**
     * ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * 8 位 UCS 转换格式
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
     */
    public static final String UTF_16BE = "UTF-16BE";

    /**
     * 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
     */
    public static final String UTF_16LE = "UTF-16LE";

    /**
     * 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
     */
    public static final String UTF_16 = "UTF-16";

    /**
     * 中文超大字符集
     */
    public static final String GBK = "GBK";

    /**
     * 将字符编码转换成US-ASCII码
     *
     * @param str 要转换的字符串
     * @return 字符串转换后的US-ASCII码字符串
     * @throws UnsupportedEncodingException 转换异常
     */
    public final static String toASCII(String str) throws UnsupportedEncodingException {
        return changeCharset(str, US_ASCII);
    }

    /**
     * 将字符编码转换成ISO_8859_1码
     *
     * @param str 要转换的字符串
     * @return 字符串转换后的ISO_8859_1码字符串
     * @throws UnsupportedEncodingException 转换异常
     */
    public final static String toISO_8859_1(String str) throws UnsupportedEncodingException {
        return changeCharset(str, ISO_8859_1);
    }

    /**
     * 将字符编码转换成UTF_8码
     *
     * @param str 要转换的字符串
     * @return 字符串转换后的UTF_8码字符串
     * @throws UnsupportedEncodingException 转换异常
     */
    public static String toUTF_8(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_8);
    }


    /**
     * 将字符编码转换成GBK码
     *
     * @param str 要转换的字符串
     * @return 字符串转换后的GBK码字符串
     * @throws UnsupportedEncodingException 转换异常
     */
    public final static String toGBK(String str) throws UnsupportedEncodingException {
        return changeCharset(str, GBK);
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @return 字符串转换编码后的字符串
     * @throws UnsupportedEncodingException 转换异常
     */
    public final static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return 转换后的字符串
     * @throws UnsupportedEncodingException 转换异常
     */
    public final static String changeCharset(String str, String oldCharset,
                                             String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * Unicode转换成GBK字符集
     *
     * @param input 待转换字符串
     * @return 转换完成字符串
     * @throws UnsupportedEncodingException 转换异常
     */
    public final static String toGBKWithUTF8(String input) throws UnsupportedEncodingException {
        if (ValidUtil.isEmpty(input)) {
            return "";
        } else {
            String s1;
            s1 = new String(input.getBytes("ISO8859_1"), "GBK");
            return s1;
        }
    }

    /**
     * GBK转换成Unicode字符集
     *
     * @param input 待转换字符串
     * @return 转换完成字符串
     * @throws UnsupportedEncodingException 转换异常
     */
    public final static String toUnicodeWithGBK(String input) throws UnsupportedEncodingException {
        if (ValidUtil.isEmpty(input)) {
            return "";
        } else {
            String s1;
            s1 = new String(input.getBytes("GBK"), "ISO8859_1");
            return s1;
        }
    }

    /**
     * 转换为Charset对象
     *
     * @param charset 字符集，为空则返回默认字符集
     * @return Charset
     */
    public static Charset charset(String charset) {
        return ValidUtil.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
    }
}