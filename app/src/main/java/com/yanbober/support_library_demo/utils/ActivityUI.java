package com.yanbober.support_library_demo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.yanbober.support_library_demo.R;


/**
 * Created by hgx on 2015/6/7.
 */
public class ActivityUI {

    /**
     * 设置透明状态栏和导航栏和强制竖屏
     * 必须添加在Activity最前面
     * API>=19
     * @param context
     * */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setNoTitle(Context context) {
        //强制竖屏
        ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((Activity)context).requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            ((Activity)context).getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            ((Activity)context).getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    /**
     * activity间切换动画
     *
     * @param activity
     */
    public static void setActivityAnimation(Activity activity) {
            activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
