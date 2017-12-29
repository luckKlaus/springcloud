package com.luck.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 发送http 请求并返回
 *
 * @author
 */
public class HttpSender {

    private static HttpSender instance = new HttpSender();

    private HttpSender() {
    }

    public static HttpSender getInstance() {
        return instance;
    }

    /**
     * 利用URL发起POST请求，并接收返回信息
     *
     * @param url 请求URL
     * @return 响应内容
     * @throws Exception
     * 根据Content-Type 的不同，请传入参数 ：application/x-www-form-urlencoded，application/json
     */
    public String post(String url, String str,String ContentType) {
//        if (!url.toLowerCase().startsWith("https://")) {
//            return "非 http请求";
//        }
        StringBuffer sb = new StringBuffer("");
        BufferedReader reader = null;
        try {
            URL u = new URL(url);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setDoOutput(true);
//			System.setProperty("sun.net.client.defaultConnectTimeout", "15000");  
//			System.setProperty("sun.net.client.defaultReadTimeout", "15000");  
            connection.setConnectTimeout(15000);//设置超时
            connection.setReadTimeout(15000);//设置读取超时
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", ContentType); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(str);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 利用URL发起get请求，并接收返回信息
     *
     * @param url 请求URL
     * @return 响应内容
     * @throws Exception
     */
    public String get(String url) {
//        if (!url.toLowerCase().startsWith("https://")) {
//            return "非 http请求";
//        }
        StringBuffer sb = new StringBuffer("");
        BufferedReader reader = null;
        try {
            URL u = new URL(url);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setConnectTimeout(15000);//设置超时
            connection.setReadTimeout(15000);//设置读取超时
            connection.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * url字符串拼接post
     */
    public String formatPostParameter(String postman, Map<String, Object> paramsMap) {


        if (paramsMap != null && !paramsMap.isEmpty()) {
            if (postman != null) {
                if (!postman.endsWith("?")) {
                    postman = postman + "";

                }
                Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
                Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    if (entry != null) {
                        String key = entry.getKey();
                        Object value =  entry.getValue();
                        postman = postman + key + "=" + value;
                        if (iterator.hasNext()) {
                            postman = postman + "&";

                        }

                    }

                }

            }


        }

        return postman;

    }


    /**
     * url字符串拼接get
     */
    public String formatGetParameter(String postman, Map<String, Object> paramsMap) {


        if (paramsMap != null && !paramsMap.isEmpty()) {
            if (postman != null) {
                if (!postman.endsWith("?")) {
                    postman = postman + "?";

                }
                Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
                Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    if (entry != null) {
                        String key = entry.getKey();
                        String value = (String) entry.getValue();
                        postman = postman + key + "=" + value;
                        if (iterator.hasNext()) {
                            postman = postman + "&";

                        }

                    }

                }

            }


        }

        return postman;

    }


    public static void main(String[] args) {
        HttpSender httpSender = new HttpSender();
        Map<String, Object> map = new HashMap<>();
        map.put("partner_code", "uf7fintech");
        map.put("partner_key", "214eaae762fa4d3d95192ff99988d973");
        map.put("app_name", "uf7fintech_web");
        map.put("report_id", "ER2017071814573718646058");
        String url = "https://apitest.tongdun.cn/preloan/report/v6";
        String str = httpSender.formatGetParameter(url, map);
        String result = httpSender.get(str);
        System.out.println(str);
        System.out.println(result);
    }
}
