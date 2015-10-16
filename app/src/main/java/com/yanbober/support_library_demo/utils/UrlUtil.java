package com.yanbober.support_library_demo.utils;

import com.yanbober.support_library_demo.persistence.Secret;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2015/10/16.
 */
public class UrlUtil {

    /**
     * 从 URL 获取参数,并按照顺序排序
     *
     * @param url URL
     * @return 参数MAP
     */
    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new TreeMap<>();
        String query = url.getQuery();
        if (query!=null && query.contains("&")){
            String[] pairs = query.split("&");//key=value
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                // query_pairs.put(key,value)
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }

        return query_pairs;
    }


    /**
     * b站加密url
     */
    public static String getParaUriNoSigned(Map<String, String> para) {

        para.put("ts", String.valueOf(System.currentTimeMillis() / 1000L));
        para.put("appkey", Secret.App_Key);
        String paraUri = "";
        for (Map.Entry<String, String> entry : para.entrySet()) {
            paraUri += entry.getKey() + "=" + entry.getValue() + "&";
        }
        paraUri = paraUri.substring(0, paraUri.length() - 1);
        return paraUri;
    }
}
