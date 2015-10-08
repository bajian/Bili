package com.yanbober.support_library_demo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.yanbober.support_library_demo.utils.ImageLoaderUtil;

import java.lang.ref.WeakReference;


/**
 * Created by hgx on 2015/1/19.
 */
public class BaseApplication extends Application {

    static Context instance;

    @Override
    public void onCreate() {
        Log.d("BaseApplication", "onCreate");
//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());
            ImageLoaderUtil.initImageLoader(this);


/*        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new FadeInBitmapDisplayer(0))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY) //default
                .cacheInMemory(true)
//                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);*/
        instance = getApplicationContext();
//        RequestManager.init(this);

        super.onCreate();
    }
//
//    private static DaoMaster daoMaster;
//    private static DaoSession daoSession;

    /**
     * ȡDaoMaster
     *
     * @param context
     * @return
     */
/*    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, "cache-db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }*/

    /**
     * ȡDaoSession
     *
     * @param context
     * @return
     */
/*    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }*/

    public static WeakReference<Activity> instanceRef;

    public static synchronized Context getInstance() {
        if (instanceRef == null || instanceRef.get() == null) {
            return BaseApplication.getContext();
        } else {
            return instanceRef.get();
        }
    }

    public static synchronized Activity getActivity() {
        Activity result = null;
        if (instanceRef != null && instanceRef.get() != null) {
            result = instanceRef.get();
        }
        return result;
    }

    public static synchronized Context getContext() {
        return instance;
    }

    public static SparseArray<WeakReference<Activity>> taskStack = new SparseArray<WeakReference<Activity>>();

    public static synchronized SparseArray<WeakReference<Activity>> getTaskStack() {
        return taskStack;
    }


}
