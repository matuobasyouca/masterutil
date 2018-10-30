package com.zscp.master.util;

import com.zscp.master.util.bean.StreamProgress;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/1/16.
 */
public class HttpUtil {
    private final static int CONNECT_TIMEOUT = 60000; // in milliseconds
    private final static String DEFAULT_ENCODING = "UTF-8";
    public static final Pattern CHARSET_PATTERN = Pattern.compile("charset=(.*?)\"");

    /**
     * 编码字符为 application/x-www-form-urlencoded
     *
     * @param content 被编码内容
     * @param charset 编码
     * @return 编码后的字符
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String encode(String content, Charset charset) throws UnsupportedEncodingException {
        return encode(content, charset.name());
    }

    /**
     * 编码字符为 application/x-www-form-urlencoded
     *
     * @param content    被编码内容
     * @param charsetStr 编码
     * @return 编码后的字符
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String encode(String content, String charsetStr) throws UnsupportedEncodingException {
        if (ValidUtil.isEmpty(content)) return content;

        String encodeContent = null;
        try {
            encodeContent = URLEncoder.encode(content, charsetStr);
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
        return encodeContent;
    }

    /**
     * 解码application/x-www-form-urlencoded字符
     *
     * @param content 被解码内容
     * @param charset 编码
     * @return 编码后的字符
     * @throws UnsupportedEncodingException 解码异常
     */
    public static String decode(String content, Charset charset) throws UnsupportedEncodingException {
        return decode(content, charset.name());
    }

    /**
     * 解码application/x-www-form-urlencoded字符
     *
     * @param content    被解码内容
     * @param charsetStr 编码
     * @return 编码后的字符
     * @throws UnsupportedEncodingException 解码异常
     */
    public static String decode(String content, String charsetStr) throws UnsupportedEncodingException {
        if (ValidUtil.isEmpty(content)) return content;
        String encodeContnt = null;
        try {
            encodeContnt = URLDecoder.decode(content, charsetStr);
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
        return encodeContnt;
    }

    /**
     * post数据
     *
     * @param urlStr url
     * @param data   数据
     * @return 返回数据
     */
    public static String postData(String urlStr, String data) {
        return postData(urlStr, data, "");
    }

    /**
     * post数据
     *
     * @param urlStr      url
     * @param data        数据
     * @param contentType content-type
     * @return 返回数据
     */
    public static String postData(String urlStr, String data, String contentType) {
        try {
            return postData(urlStr, data, new HashMap<String, String>() {{
                put("content-type", contentType);
            }});
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * post数据
     *
     * @param urlStr  url
     * @param data    数据
     * @param headers http头请求
     * @return 返回数据
     */
    public static String postData(String urlStr, String data, Map<String, String> headers) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            for (String headKey : headers.keySet()) {
                conn.setRequestProperty(headKey, headers.get(headKey));
            }
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            if (data == null)
                data = "";
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr url
     * @return 返回数据
     */
    public static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CONNECT_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 下载远程文件
     *
     * @param url      请求的url
     * @param destFile 目标文件或目录，当为目录时，取URL中的文件名，取不到使用编码后的URL做为文件名
     * @return 文件大小
     * @throws IOException io异常
     */
    public static long downloadFile(String url, File destFile) throws IOException {
        return downloadFile(url, destFile, null);
    }


    /**
     * 下载远程文件
     *
     * @param url            请求的url
     * @param destFile       目标文件或目录，当为目录时，取URL中的文件名，取不到使用编码后的URL做为文件名
     * @param streamProgress 进度条
     * @return 文件大小
     * @throws IOException io异常
     */
    public static long downloadFile(String url, File destFile, StreamProgress streamProgress) throws IOException {
        if (ValidUtil.isEmpty(url)) {
            throw new NullPointerException("[url] is null!");
        }
        if (null == destFile) {
            throw new NullPointerException("[destFile] is null!");
        }
        if (destFile.isDirectory()) {
            String fileName = StringUtil.subSuf(url, url.lastIndexOf('/') + 1);
            if (ValidUtil.isEmpty(fileName)) {
                fileName = HttpUtil.encode(url, StandardCharsets.UTF_8);
            }
            destFile = FileUtil.file(destFile, fileName);
        }

        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(destFile));
            return download(url, out, true, streamProgress);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 下载远程文件
     *
     * @param url            请求的url
     * @param out            将下载内容写到输出流中 {@link OutputStream}
     * @param isCloseOut     是否关闭输出流
     * @param streamProgress 进度条
     * @return 文件大小
     * @throws IOException io异常
     */
    public static long download(String url, OutputStream out, boolean isCloseOut, StreamProgress streamProgress) throws IOException {
        if (ValidUtil.isEmpty(url)) {
            throw new NullPointerException("[url] is null!");
        }
        if (null == out) {
            throw new NullPointerException("[out] is null!");
        }

        InputStream in = null;
        try {
            in = new URL(url).openStream();
            return IoUtil.copyByNIO(in, out, IoUtil.DEFAULT_BUFFER_SIZE, streamProgress);
        } catch (IOException e) {
            throw e;
        } finally {
            IoUtil.close(in);
            if (isCloseOut) {
                IoUtil.close(out);
            }
        }
    }

    /**
     * 将Map形式的Form表单数据转换为Url参数形式，不做编码
     *
     * @param paramMap 表单数据
     * @return url参数
     * @throws UnsupportedEncodingException 转换异常
     */
    public static String toParams(Map<String, Object> paramMap) throws UnsupportedEncodingException {
        return toParams(paramMap, StandardCharsets.UTF_8);
    }

    /**
     * 将Map形式的Form表单数据转换为Url参数形式<br>
     * 编码键和值对
     *
     * @param paramMap    表单数据
     * @param charsetName 编码
     * @return url参数
     * @throws UnsupportedEncodingException 转换异常
     */
    public static String toParams(Map<String, Object> paramMap, String charsetName) throws UnsupportedEncodingException {
        return toParams(paramMap, CharsetUtil.charset(charsetName));
    }

    /**
     * 将Map形式的Form表单数据转换为Url参数形式<br>
     * 编码键和值对
     *
     * @param paramMap 表单数据
     * @param charset  编码
     * @return url参数
     * @throws UnsupportedEncodingException 转换异常
     */
    public static String toParams(Map<String, Object> paramMap, Charset charset) throws UnsupportedEncodingException {
        if (paramMap == null || paramMap.size() == 0) {
            return StringUtil.EMPTY;
        }
        if (null == charset) {//默认编码为系统编码
            charset = StandardCharsets.UTF_8;
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<String, Object> item : paramMap.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }
            sb.append(encode(item.getKey(), charset)).append("=").append(encode(item.getValue() == null ? "" : item.getValue().toString(), charset));
        }
        return sb.toString();
    }

    /**
     * 将URL参数解析为Map（也可以解析Post中的键值对参数）
     *
     * @param paramsStr 参数字符串（或者带参数的Path）
     * @param charset   字符集
     * @return 参数Map
     * @throws UnsupportedEncodingException 转换异常
     */
    public static Map<String, List<String>> decodeParams(String paramsStr, String charset) throws UnsupportedEncodingException {
        if (ValidUtil.isEmpty(paramsStr)) {
            return Collections.emptyMap();
        }

        // 去掉Path部分
        int pathEndPos = paramsStr.indexOf('?');
        if (pathEndPos > 0) {
            paramsStr = StringUtil.subSuf(paramsStr, pathEndPos + 1);
        }
        paramsStr = decode(paramsStr, charset);

        final Map<String, List<String>> params = new LinkedHashMap<String, List<String>>();
        String name = null;
        int pos = 0; // 未处理字符开始位置
        int i; // 未处理字符结束位置
        char c; // 当前字符
        for (i = 0; i < paramsStr.length(); i++) {
            c = paramsStr.charAt(i);
            if (c == '=' && name == null) { // 键值对的分界点
                if (pos != i) {
                    name = paramsStr.substring(pos, i);
                }
                pos = i + 1;
            } else if (c == '&' || c == ';') { // 参数对的分界点
                if (name == null && pos != i) {
                    // 对于像&a&这类无参数值的字符串，我们将name为a的值设为""
                    addParam(params, paramsStr.substring(pos, i), StringUtil.EMPTY);
                } else if (name != null) {
                    addParam(params, name, paramsStr.substring(pos, i));
                    name = null;
                }
                pos = i + 1;
            }
        }

        if (pos != i) {
            if (name == null) {
                addParam(params, paramsStr.substring(pos, i), StringUtil.EMPTY);
            } else {
                addParam(params, name, paramsStr.substring(pos, i));
            }
        } else if (name != null) {
            addParam(params, name, StringUtil.EMPTY);
        }

        return params;
    }

    /**
     * 将表单数据加到URL中（用于GET表单提交）
     *
     * @param url  URL
     * @param form 表单数据
     * @return 合成后的URL
     * @throws UnsupportedEncodingException 转换异常
     */
    public static String urlWithForm(String url, Map<String, Object> form) throws UnsupportedEncodingException {
        final String queryString = toParams(form, CharsetUtil.UTF_8);
        return urlWithForm(url, queryString);
    }

    /**
     * 将表单数据字符串加到URL中（用于GET表单提交）
     *
     * @param url         URL
     * @param queryString 表单数据字符串
     * @return 拼接后的字符串
     */
    public static String urlWithForm(String url, String queryString) {
        if (!ValidUtil.isEmpty(queryString)) {
            if (url.contains("?")) {
                //原URL已经带参数
                url += "&" + queryString;
            }
            url += url.endsWith("?") ? queryString : "?" + queryString;
        }

        return url;
    }

    /**
     * 从Http连接的头信息中获得字符集<br>
     * 从ContentType中获取
     *
     * @param conn HTTP连接对象
     * @return 字符集
     */
    public static String getCharset(HttpURLConnection conn) {
        if (conn == null) {
            return null;
        }

        String charset = RegUtil.getMatchValue(CHARSET_PATTERN, conn.getContentType(), 1);
        return charset;
    }

    /**
     * 将键值对加入到值为List类型的Map中
     *
     * @param params 参数
     * @param name   key
     * @param value  value
     * @return 是否成功
     */
    private static boolean addParam(Map<String, List<String>> params, String name, String value) {
        List<String> values = params.get(name);
        if (values == null) {
            values = new ArrayList<String>(1); // 一般是一个参数
            params.put(name, values);
        }
        values.add(value);
        return true;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 参考文章： http://developer.51cto.com/art/201111/305181.htm
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
