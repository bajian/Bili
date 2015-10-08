package com.yanbober.support_library_demo.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.UUID;

/**
 * 获取设备唯一标识
 */

/**
 * @author Administrator
 *
 */
public class SerialUtil {

	/**
	 * 获取CPU序列号
	 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 * @return string CPU序列号(16位)
	 * 读取失败为"0000000000000000"
	 */
	public static String getCPUSerial() {
		String str = "", strCPU = "", cpuAddress = "0000000000000000";
		try {
			//读取CPU信息
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			//查找CPU序列号
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					//查找到序列号所在行
					if (str.contains("Serial")) {
						//提取序列号
						strCPU = str.substring(str.indexOf(":") + 1,
								str.length());
						//去空格
						cpuAddress = strCPU.trim();
						break;
					}
				}else{
					//文件结尾
					break;
				}
			}
		} catch (IOException ex) {
			//赋予默认值
			ex.printStackTrace();
		}
		return cpuAddress;
	}




	/**
	 * 获取设备IMEI
	 * @param context
	 * 在manifest.xml文件中要添加 <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 * @return string
	 *
	 */
	public static String getIMEI(Context context){
		return ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE))
				.getDeviceId();

	}


	/**
	 * 获取国际移动用户识别码（IMSI）
	 *
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();
		if (TextUtils.isEmpty(imsi)) {
			return "0";
		} else {
			return imsi;
		}
	}


	/**
	 * 获取每个应用的唯一码UUID
	 * @param context
	 * @return uniqueId
	 */
	public static String getMyUUID(Context context){
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		System.out.println("uuid="+uniqueId);
		return uniqueId;
	}

}
