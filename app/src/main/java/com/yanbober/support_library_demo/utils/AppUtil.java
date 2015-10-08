package com.yanbober.support_library_demo.utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * Created by hgx on 2015/6/8.
 */
public class AppUtil {
    /**
     * 启动一个app
     *   private List<ApplicationInfo> mAppList;
     *   mAppList = getPackageManager().getInstalledApplications(0);
     *   ApplicationInfo item = mAppList.get(position);
     * @param item 其实就是要个包名，就可以启动应用程序
     * @param context
     */

    private void open(ApplicationInfo item,Context context) {
        // open app
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(item.packageName);
        List<ResolveInfo> resolveInfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(
                    activityPackageName, className);

            intent.setComponent(componentName);
            context.startActivity(intent);
        }
    }



    /**
     * APP在后台或者锁屏了返回TRUE,如果在前台返回false
     * @param context
     * @return
     */
    public static  boolean isBackgroundRunning(Context context) {
        String processName = "com.bqs.wetime.fruits";

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        if (activityManager == null)
            return false;
        // get running application processes
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (process.processName.startsWith(processName)) {
                boolean isBackground = process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                ;
                boolean isLockedState = keyguardManager
                        .inKeyguardRestrictedInputMode();
                if (isBackground || isLockedState)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }


    /**
     * 浏览器打开指定网址
     * @param url
     * @param context
     */
    public static void gotoUrl(String url,Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // 此处打开默认浏览器，若无默认浏览器则报错
            // intent.putExtra(Browser.EXTRA_APPLICATION_ID,
            // Base.getInstance().getPackageName());
            context.startActivity(intent);
        } catch (Exception e) {
            // e.printStackTrace();
            e.printStackTrace();
        }
    }

    /**
     * APP 版本
     * @param context
     * @return
     */
    public static int getVersion(Context context) {
        int result = 0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            result = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String result = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            result = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean isAppInstalled(final Context context, final String packageName) {
        try {
            final PackageManager pm = context.getPackageManager();
            final PackageInfo info = pm.getPackageInfo(packageName, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isMainProcess(final Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processes) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isServiceRunning(Context context, Class<?> cls) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Integer.MAX_VALUE);
        final String className = cls.getName();
        for (ActivityManager.RunningServiceInfo service : services) {
            if (className.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> apps = am.getRunningAppProcesses();
        if (apps == null || apps.isEmpty()) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo app : apps) {
            if (packageName.equals(app.processName)) {
                return true;
            }
        }
        return false;
    }

    public static PackageInfo getCurrentPackageInfo(final Context context) {
        final PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static PackageInfo getPackageInfo(final Context context, final String packageName) {
        final PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean isDisabled(Context context, Class<?> clazz) {
        ComponentName componentName = new ComponentName(context, clazz);
        PackageManager pm = context.getPackageManager();
        return pm.getComponentEnabledSetting(componentName)
                == PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

    public static boolean isEnabled(Context context, Class<?> clazz) {
        ComponentName componentName = new ComponentName(context, clazz);
        PackageManager pm = context.getPackageManager();
        return pm.getComponentEnabledSetting(componentName)
                != PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

    public static void enableComponent(Context context, Class<?> clazz) {
        setComponentState(context, clazz, true);
    }

    public static void disableComponent(Context context, Class<?> clazz) {
        setComponentState(context, clazz, false);
    }

    public static void setComponentState(Context context, Class<?> clazz, boolean enable) {
        ComponentName componentName = new ComponentName(context, clazz);
        PackageManager pm = context.getPackageManager();
        final int oldState = pm.getComponentEnabledSetting(componentName);
        final int newState = enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        if (newState != oldState) {
            final int flags = PackageManager.DONT_KILL_APP;
            pm.setComponentEnabledSetting(componentName, newState, flags);
        }
    }


}
