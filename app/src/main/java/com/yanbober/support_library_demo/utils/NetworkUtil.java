package com.yanbober.support_library_demo.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.yanbober.support_library_demo.app.BaseApplication;


/**
 * <p>Title:判断当前网络状态 </p>
 * ConnectivityManager主要管理和网络连接相关的操作 
 * 相关的TelephonyManager则管理和手机、运营商等的相关信息；WifiManager则管理和wifi相关的信息。 
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * @author: hgx
 * @version 1.0
 */
public class NetworkUtil {

	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_NULL = 0x00;
	public static final int NETTYPE_2G = 0x02;
	public static final int NETTYPE_3G = 0x03;
	public static final int NETTYPE_4G = 0x04;


	/**
	 * 检测网络是否可用
	 * @param context eg:Activity.this
	 * @return boolean
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}


	/**
	 * 得到当前的手机网络类型
	 * 
	 * @param context
	 * @return int 0=null、1=wifi、2=2g、3=3g、4=4g
	 */ 
	public static int getCurrentNetType(Context context) { 
		int type = 0; 
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo info = cm.getActiveNetworkInfo(); 
		if (info == null) { 
			type = NETTYPE_NULL; 
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) { 
			type = NETTYPE_WIFI; 
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) { 
			int subType = info.getSubtype(); 
			if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS 
					|| subType == TelephonyManager.NETWORK_TYPE_EDGE) { 
				type = NETTYPE_2G; 
			} else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA 
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0 
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_B) { 
				type = NETTYPE_3G; 
			} else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准 
				type = NETTYPE_4G; 
			} 
		} 
		return type; 
	}

	/**
	 * 是否连接WIFI
	 *<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo.isConnected()) {
			return true;
		}
		return false;
	}


	public boolean isNetworkOnline() {
		boolean status = false;
		try {
			// Check all NetworkInfo state which has been connected.
			// If not find the one is connected, always return false.
			ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
			for (int index = 0; index < networkInfos.length; index++) {
				NetworkInfo netInfo = networkInfos[index];
				if ((netInfo != null) && netInfo.isConnected()) {
					status = true;
					;
				}
			}
		} catch (Exception e) {
			// Only print the exception message and stack trace,
			// because this method must return a value to caller.
			e.printStackTrace();
		}
		return status;
	}


	/**
	 * 快捷判断网络是wifi还是mobile
	 * @param context
	 * @return ""、wifi、mobile
	 */
	public static String getAPN(Context context) {
		String apn = "";
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info != null) {
			if (ConnectivityManager.TYPE_WIFI == info.getType()) {
				apn = info.getTypeName();
				if (apn == null) {
					apn = "wifi";
				}
			} else {
				apn = info.getExtraInfo().toLowerCase();
				if (apn == null) {
					apn = "mobile";
				}
			}
		}
		return apn;
	}

}
