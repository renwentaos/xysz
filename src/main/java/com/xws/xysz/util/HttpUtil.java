package com.xws.xysz.util;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;


/**
 * Http请求工具类
 * <p/>
 * Created by 光 on 2015/3/25.
 */
@Slf4j
public final class HttpUtil {
    //  private static Logger logger = Logger.getLogger(HttpUtil.class.getName());

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public static String doGet(String url) throws IOException {
        String result = "";
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
        String getURL = url;
        URL getUrl = new URL(getURL);
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
        // 服务器
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "utf8"));// 设置编码,否则中文乱码
        String lines;
        while ((lines = reader.readLine()) != null) {
            result += lines;
        }
        reader.close();
// 断开连接
        connection.disconnect();
        return result;
    }

    public static String doPost(String url, Map<String, String> querys, Map<String, String> bodys) throws UnsupportedEncodingException {
        if (querys.size() > 0) {
            StringBuilder urlSB = new StringBuilder(url);
            if (url.indexOf("?") > -1) {
                urlSB.append("&");
            } else {
                urlSB.append("?");
            }
            for (String key : querys.keySet()) {
                urlSB.append(key).append("=")
                        .append(java.net.URLEncoder.encode(querys.get(key), "UTF-8"))
                        .append("&");
            }
            url = urlSB.toString();
            url = url.substring(0, url.length() - 1);
        }
        StringBuilder paramSB = new StringBuilder();
        for (String key : bodys.keySet()) {
            paramSB.append(key).append("=")
                    .append(java.net.URLEncoder.encode(bodys.get(key), "UTF-8"))
                    .append("&");
        }
        String param = paramSB.toString();
        if (!StringUtil.isEmpty(param)) {
            param = param.substring(0, param.length() - 1);
        }
        return doPost(url, param);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");


            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送POST请求出现异常！请求地址：" + url + ",请求参数为:" + param);
            //logger.error("发送POST请求出现异常！请求地址：" + url + ",请求参数为:" + param, e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                //  logger.error("", ex);
                log.error("关闭流异常");
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 检查URL是否存在
     *
     * @param url
     * @return
     */
    public static InputStream checkUrl(String url) {
        try {
            URL uu = new URL(
                    url);
            // 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
            URLConnection uc = uu.openConnection();
            // 打开的连接读取的输入流。
            InputStream in = uc.getInputStream();
            return in;
        } catch (Exception e) {
            return null;
        }

    }

}
