package com.yanbober.support_library_demo.utils;

import com.google.gson.Gson;

/**
 * Created by hgx on 2015/7/2.
 */
public class GsonUtil {
    private static Gson gson;

    /**
     * 单例
     * @return
     */
    public static Gson getInstace(){
        if(gson == null) {
            synchronized(GsonUtil.class) {
                if(gson == null) {
                    gson = new Gson();

                }
            }
        }

        return gson;
    }

    /**
     * 解析json
     * @param json
     * @param classOfT bean
     * @param <T>
     * @return
     */
    public  static  <T> T parseJson(String json, Class<T> classOfT){
        return getInstace().fromJson(json,classOfT);
    }
}
