package com.yanbober.support_library_demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yanbober.support_library_demo.persistence.Constant;

import java.io.File;



/**
 * Created by hgx on 2015/6/11.
 */
public class ImageLoaderUtil {

    /**
     * ImageLoader框架 自定义初始化配置
     * 自定义缓存路径、线程数、缓存类型和大小
     * @param context
     */
    public static void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);//缓存大小
        MemoryCacheAware<String, Bitmap> memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {//缓存类型
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }

        DisplayImageOptions mNormalImageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisc(true)
                .resetViewBeforeLoading(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(mNormalImageOptions)
                .denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiscCache(new File(Constant.IMAGES_FOLDER)))//自定义缓存路径
                        // .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(memoryCache)
                        // .memoryCacheSize(memoryCacheSize)
                .tasksProcessingOrder(QueueProcessingType.LIFO).threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(5).build();//自定义线程数

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

//相信大部分人都是使用GridView，ListView来显示大量的图片，而当我们快速滑动GridView，ListView，
// 我们希望能停止图片的加载，而在GridView，ListView停止滑动的时候加载当前界面的图片，这个框架当然也提供这个功能
// ，使用起来也很简单，它提供了PauseOnScrollListener这个类来控制ListView,GridView滑动过程中停止去加载图片
// ，该类使用的是代理模式

//listView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
//eg:mListview.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, false));
//gridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
//第一个参数就是我们的图片加载对象ImageLoader, 第二个是控制是否在滑动过程中暂停加载图片，
// 如果需要暂停传true就行了，第三个参数控制猛的滑动界面的时候图片是否加载




}
