package com.yanbober.support_library_demo.persistence;

import android.os.Environment;

import java.io.File;

/**
 * Created by hgx on 2015/6/10.
 */
public class Constant {

    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    public static final String APP_ROOT_FOLDER = SDCARD_PATH + File.separator + "hgxBilidemo" + File.separator ;
    public static final String IMAGES_FOLDER = APP_ROOT_FOLDER + "images" + File.separator;
    public static final String VOICE_FOLDER = APP_ROOT_FOLDER + "voice" + File.separator;

    public static final String SP_BILI_FANJU = "bili_fanju";
    public static final String SP_BILI_VIEWFLOW = "bili_viewflow";

    public static final String STATUS_SUCC = "000000";


}
