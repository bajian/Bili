package com.yanbober.support_library_demo.utils;

import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

/**
 * 废弃的工具类。。。感觉资源管理很不方便，还是不用了。。但还是可以参考流程的
 * Created by hgx on 2015/6/11.
 */
public class MediaPlayerUtil {
    private static volatile MediaPlayer mp;
    private static boolean createState =false;


    public static MediaPlayer getInstance(){
        if(mp == null) {
            synchronized(MediaPlayer.class) {
                if(mp == null) {
                    mp = new MediaPlayer();
                    if (!createState){
                        try {
                            mp.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return mp;
    }


    /**
     * 播放网络mp3 未测试
     * @param url
     */
    public static void playNetMp3(String url){
        try {
            MediaPlayerUtil.getInstance().setDataSource(url);
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e) {
        } catch (IOException e) {
        }
        mp.start();
    }


    /**
     * 播放本地MP3
     * @param path 绝对路径
     * @return
     */
    public static void playLocalMp3(String path){
        /**
         * 创建音频文件的方法：
         * 1、播放资源目录的文件：MediaPlayer.create(MainActivity.this,R.raw.beatit);//播放res/raw 资源目录下的MP3文件
         * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer();
         *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件
         */
//        MediaPlayer mp=MediaPlayer.create(this, R.raw.beatit);

        try {
            File file = new File(path);
            if (file.exists()) {
                MediaPlayerUtil.getInstance().setDataSource(path);
                mp.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
