package com.yanbober.support_library_demo.db;

import android.content.Context;


/**
 * Created by ihgoo on 2015/5/6.
 */
public class CacheDBHelper {

    private static Context mContext;
    private static CacheDBHelper instance;


    private CacheDBHelper() {
    }

    public static CacheDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CacheDBHelper();
            if (mContext == null) {
                mContext = context;
            }

//            instance.cacheDao = daoSession.getCooperateUserDao();
        }
        return instance;
    }


}
