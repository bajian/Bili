package com.yanbober.support_library_demo.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 测绘工具类
 * 
 * @author hgx
 * @since 2015-6-9
 */
public final class MeasureUtil {
	/**
	 * 获取屏幕尺寸,px
	 * @param activity Activity
	 * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
	 */
	public static int[] getScreenSize(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return new int[] { metrics.widthPixels, metrics.heightPixels };
	}


	/**
	 * get WindowManager
	 */
	public static WindowManager getWindowManger(Context context) {
		if (context == null)
			return null;
		WindowManager windowManager = null;
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return windowManager;
	}

	public static int dp2px(int dp,Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}


}
