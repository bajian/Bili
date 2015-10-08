package com.yanbober.support_library_demo.utils;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hgx on 2015/6/16.
 */
public class LogUtil {

    public static void logThreadId(String prefix) {

    }



    public static void log(RectF rect) {
        // if (APIV1.isDebug()) {
        Log.v("rect", Misc.concat(rect.left, "-", rect.right, ",", rect.top, "-", rect.bottom));
        // }
    }


    public static void debug(Object... objects) {
        // if (APIV1.isDebug()) {
        Log.v("System.err", dumpObject(objects));
        // (new Exception()).printStackTrace();
        // }
    }

    public static String dumpObject(Object... objects) {
        String result = new String();

        ArrayList<String> resultArr = new ArrayList<String>();

        if (objects == null) {
            result = "null";
        } else {
            for (Object object : objects) {
                resultArr.add(dumpSingleObject(object));
            }
            result = Misc.quoteString(StringUtil.join(resultArr.toArray()), "[", "]");
        }

        return result;
    }

    /**
     * @return
     */
    private static String dumpSingleObject(Object object) {
        String result = new String();
        if (object == null) {
            result = "null";
        } else {
            String className = object.getClass().getName();
            String value = object.toString();
            result = className + ":" + value;
        }
        return result;
    }

}
