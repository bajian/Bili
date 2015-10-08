package com.yanbober.support_library_demo.utils;


import com.lidroid.xutils.HttpUtils;

/**
 * 封装xutils的http
 * Created by hgx on 2015/6/11.
 */
public class HttpUtil {
    private static volatile HttpUtils http;


    public static HttpUtils getInstance(){
        if(http == null) {
            synchronized(HttpUtils.class) {
                if(http == null) {
                    http = new HttpUtils();
                }
            }
        }

        return http;
    }
}
