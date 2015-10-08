package com.yanbober.support_library_demo.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Create a activity stack.<br></br>
 * in order to manager exit the app.
 */
public class AppManager extends Application {

    private List<Activity> activityList = new LinkedList<>();


    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (null == instance) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit(Context context) {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
