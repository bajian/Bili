package com.yanbober.support_library_demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yanbober.support_library_demo.R;
import com.yanbober.support_library_demo.app.BaseApplication;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



public class Misc {






    /**
     * get fit width for different resolution,input param is width for 720
     *
     * @param context
     * @param inwidth
     */
    public static int getFitWidth(Context context, int inwidth) {
        if (context == null)
            return inwidth;
        int screenwidth = getResolution(context)[0];
        return (inwidth * screenwidth) / 720;
    }


    /**
     * get fit width for different resolution,input param is width for 1280
     *
     * @param context
     * @param inheight
     */
    public static int getFitHeight(Context context, int inheight) {
        if (context == null)
            return inheight;
        int screenheight = getResolution(context)[1];
        return (inheight * screenheight) / 1280;
    }

    /**
     * get resolution
     *
     * @param context
     * @return
     */
    public static int[] getResolution(Context context) {
        int resolution[] = new int[2];
        // DisplayMetrics dm = new DisplayMetrics();
        // getWindowManger(context).getDefaultDisplay().getMetrics(dm);
        Display display = getWindowManger(context).getDefaultDisplay();
        resolution[0] = display.getWidth();
        resolution[1] = display.getHeight();
        return resolution;
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



    private static Resources resources;

    /**
     * @return 导出数据流
     */
    public static BufferedInputStream exportData(String dbName) {
        try {
            // 当前程序路径
            String path = BaseApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();
            path = path + "/../databases/" + dbName;
            File file = new File(path);
            FileInputStream inStream = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(inStream);
            return in;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * @param in 导入数据流
     */
    public static void importData(BufferedInputStream in, String dbName) {
        try {
            // 当前程序路径
            String path = BaseApplication.getInstance().getFilesDir().getAbsolutePath();
            path = path + "/../databases/";
            File file = new File(path);
            file.mkdirs();
            path += dbName;
            file = new File(path);
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file);
            BufferedOutputStream out = new BufferedOutputStream(outStream);
            int c;
            while ((c = in.read()) >= 0) {
                out.write(c);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 求一个数的模数,该函数通常用来循环获得0-length的索引.
     * <p/>
     * <code>for example:</br>
     * <pre>
     * final int divisor = 4;
     * for(int dividend = -9; dividend < 10; dividend ++)
     * {
     *     System.out.println(computeRemainder(dividend, divisor));
     * }
     * </pre></code>
     *
     * 你将只能获得0-3的数。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 模数
     */
    public static final int computeRemainder(int dividend, int divisor) {
        final int modulus = dividend % divisor;
        if (modulus < 0)
            return modulus + divisor;
        else
            return modulus;
    }



    public static String quoteString(Object object, String left, String right) {
        return left.concat(object.toString()).concat(right);
    }

    public static String concat(Object... objects) {

        String str = new String();
        if (objects != null) {
            for (Object object : objects) {
                if (object != null) {
                    try {
                        str = str.concat(object.toString());
                    } catch (Exception e) {
                    }
                }
            }
        }
        return str;
    }





    public static void setUnderLine(TextView textView, String text1, String text2) {
        textView.setText(Html.fromHtml(text1 + "<u>" + text2 + "</u>"));
    }

    public static String toString(Object object) {
        String result = "错误：null";
        if (object != null) {
            result = object.toString();
        }
        return result;
    }


    public static boolean isInt(Field field) {
        return field.getType().getSimpleName().equals("int");
    }

    public static int getInt(Object object, int defaultValue) {

        String str = "";
        if (object != null) {
            str = object.toString();

        }
        return parseInt(str, defaultValue);
    }

    public static long getLong(Object object, long defaultValue) {
        String str = "";
        if (object != null) {
            str = object.toString();

        }
        return parseLong(str, defaultValue);
    }

    public static long getLong(JSONObject jsonObject, String name, long defaultValue) {
        long result = defaultValue;
        try {
            if (jsonObject != null && jsonObject.has(name)) {
                result = jsonObject.getLong(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int parseInt(String object, int defalult) {
        return (int) parseDouble(object, defalult);
    }

    public static float parseFloat(String object, float defalult) {
        return (float) parseDouble(object, defalult);
    }

    public static long parseLong(String object, long defalult) {
        return (long) parseDouble(object, defalult);
    }

    public static double parseDouble(String object, double defalult) {
        double result = defalult;
        if (object != null && object.length() > 0) {
            try {
                result = Double.parseDouble(object);
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static int getInt(JSONObject jsonObject, String name, int defaultValue) {
        int result = defaultValue;
        try {
            if (jsonObject != null && jsonObject.has(name)) {
                result = jsonObject.getInt(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * @param string string以逗号分隔返回string的hashset
     * @return
     */
    public static HashSet<String> parseKeys(String string) {
        HashSet<String> keywords = new HashSet<String>();
        if (string != null) {
            for (String key : string.split(",")) {
                keywords.add(key);
            }
        }
        return keywords;
    }





    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> getRands(ArrayList<T> arrayList, int num) {
        ArrayList<T> result = new ArrayList<T>();
        if (arrayList != null && arrayList.size() > 0) {
            num = Math.min(num, arrayList.size());
            arrayList = (ArrayList<T>) arrayList.clone();

            for (int i = 0; i < num; i++) {
                int randIndex = (int) (Math.random() * (arrayList.size() - 1));
                result.add(arrayList.get(randIndex));
                arrayList.remove(randIndex);
            }
        }
        return result;
    }

    public static float getDenisty() {
        DisplayMetrics screenDpi = new DisplayMetrics();
        ((Activity) BaseApplication.getInstance()).getWindowManager().getDefaultDisplay().getMetrics(screenDpi);
        return screenDpi.density;
    }

    /**
     * @param objects
     * @param glue
     * @param left
     * @param right
     * @return
     */
    public static String joinWithQuote(Object[] objects, String glue, String left, String right) {
        int k = objects.length;
        if (k == 0)
            return null;
        StringBuilder out = new StringBuilder();
        out.append(quoteString(objects[0], left, right));
        for (int x = 1; x < k; ++x)
            out.append(glue).append(quoteString(objects[x], left, right));
        return out.toString();
    }

    /**
     * @param object 判断对象是否为空，字符从为“”，long跟int是否为0
     * @return
     */
    public static boolean isEmptyObject(Object object) {
        boolean isEmpty = false;
        if (object == null) {
            isEmpty = true;
        } else {
            if (object.getClass().equals(String.class)) {
                if (object.toString().length() == 0) {
                    isEmpty = true;
                }
            } else if (object.getClass().equals(Integer.class) || object.getClass().equals(Long.class)) {
                if (object.equals(0)) {
                    isEmpty = true;
                }
            }
        }
        return isEmpty;
    }

    public static boolean isTrue(Object object) {
        return isTrue(object, true);
    }

    public static boolean isTrue(Object object, Object compareTo) {
        if (object == null) {
            return false;
        } else if (object.equals(compareTo)) {
            return true;
        } else {
            return false;
        }
    }

    public static <K, V extends Comparable<V>> List<Entry<K, V>> sortedByValue(Map<K, V> map, Comparator<Entry<K, V>> comparator) {
        List<Entry<K, V>> list = new ArrayList<Entry<K, V>>();
        list.addAll(map.entrySet());
        Collections.sort(list, comparator);

        return list;
    }

    /**
     * @param v
     * @param width
     * @param height
     * @return 获取view固定宽高的图像
     */
    public static Bitmap getViewCache(View v, int width, int height) {
        Bitmap b = null;
        if (v != null) {
            try {

                v.setDrawingCacheEnabled(true);
                v.measure(width == 0 ? MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED) : width, height == 0 ? MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED) : height);
                v.layout(0, 0, width == 0 ? v.getMeasuredWidth() : width, height == 0 ? v.getMeasuredHeight() : height);
                v.buildDrawingCache(true);
                if (v.getDrawingCache() != null) {
                    b = Bitmap.createBitmap(v.getDrawingCache());
                }
                v.setDrawingCacheEnabled(false); // clear drawing cache
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    /**
     * @return 精简版缓存路径
     */
    private static String getCacheDir() {
        File cacheDir = new File(concat(getSDcardRootPath(), "/", ".cache/dayima/concept/"));
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getAbsolutePath();
    }

    /**
     * @return 精简版crash路径
     */
    public static String getCrashReportDir() {
        File cacheDir = new File(concat(getSDcardRootPath(), "/", ".cache/dayima/concept/crash/"));
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getAbsolutePath();
    }

    public static String getSDcardRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }



    /**
     * 临时保存图片
     *
     * @param bitmap
     * @return 图片路径
     */
    public static String saveImageToCache(Bitmap bitmap) {
        String result = null;
        try {
            if (checkSDCard()) {
                String cacheDir = getCacheDir();
                if (cacheDir != null) {
                    File cacheFile = new File(getCacheDir(), "com.guangzhi.weijianzhi.imageShare.png");
                    cacheFile.deleteOnExit();
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(cacheFile));
                    bitmap.compress(CompressFormat.PNG, 90, out);
                    result = cacheFile.getPath();
                    // 文件关闭
                    out.flush();
                    out.close();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 检测是否有sd卡
     *
     * @return
     */
    public static boolean checkSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    public static int getPackageVerionCode() throws NameNotFoundException {
        int infoVersion;
        PackageInfo info = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
        infoVersion = info.versionCode;
        return infoVersion;
    }

    /**
     * 检查新版本
     *
     *
     */

    /**
     * @param color
     * @param relative
     * @param str
     * @return
     */
    public static CharSequence getSpan(int color, float relative, CharSequence str) {
        SpannableStringBuilder sb = new SpannableStringBuilder(str);
        sb.setSpan(new ForegroundColorSpan(color), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (relative != 1f) {
            sb.setSpan(new RelativeSizeSpan(relative), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sb;
    }

    /**
     * @param color
     * @param relative
     * @param typeFace
     * @param str
     * @return
     */
    public static CharSequence getSpan(int color, float relative, int typeFace, CharSequence str) {
        SpannableStringBuilder sb = new SpannableStringBuilder(str);
        sb.setSpan(new ForegroundColorSpan(color), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (relative != 1f) {
            sb.setSpan(new RelativeSizeSpan(relative), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        sb.setSpan(new StyleSpan(typeFace), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    /**
     * @return
     */
    public static int getVersion() {
        int result = 0;
        try {
            PackageInfo info = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            result = info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @return
     */
    public static String getVersionName() {
        String result = "";
        try {
            PackageInfo info = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            result = info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得可视高度
     *
     * @param parent
     * @param son
     * @return
     */
    public static int getVisiableHeight(ViewGroup parent, View son) {
        // parent 的可视区域
		/*
		 * parent.invalidate(); son.invalidate();
		 */
        Rect rect = new Rect();
        parent.getHitRect(rect);
        int offset = parent.getScrollY();
        int result = rect.height() - (son.getTop() - offset);
        return result;
    }

    public static String getSign(int num) {
        String result = "";
        if (num > 0) {
            result = "+";
        } else if (num == 0) {
            result = "";
        } else {
            result = "";
        }
        return result;
    }


    public static Display display = null;

    public static Display getDisplay() {
        if (display == null) {
            WindowManager wm = ((Activity) BaseApplication.getInstance()).getWindowManager();
            display = wm.getDefaultDisplay();
        }
        return display;
    }

    public static int getScreenWidth() {
        return getDisplay().getWidth();
    }

    public static int getScreenHeight() {
        return getDisplay().getHeight();
    }

    /**
     * 返回下一个元素，如果是最后一个，返回最后一个
     *
     * @param <T>
     * @param lists
     * @param cur
     * @return
     */
    public static <T> T getNextCycle(List<T> lists, int cur) {
        int count = lists.size();
        T result;

        int next = cur + 1;

        if (next == count) {
            next = 0;
        }

        result = lists.get(next);

        return result;

    }




    public static <T> ArrayList<T> toArrayList(T[] objects) {
        ArrayList<T> result = new ArrayList<T>();
        if (objects != null) {
            for (T object : objects) {
                result.add(object);
            }
        }
        return result;
    }

    public static String toString(InputStream inputStream) {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        try {
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }

    // enum 类型

    /**
     * enum to int
     *
     * @param <E>
     * @param type
     * @return
     */
    public static <E extends Enum<E>> int enumToInt(E type) {
        return type.ordinal();
    }

    /**
     * int to enum
     *
     * @param <E>
     * @param clazz
     * @param ordinal
     * @return
     */
    public static <E extends Enum<E>> E intToEnum(Class<E> clazz, int ordinal) {
        return clazz.getEnumConstants()[ordinal];

    }

    // 基于enum的 intentType设置
    public static <E extends Enum<E>> void setIntentType(Intent intent, E type) {
        intent.putExtra("INTENT_TYPE", enumToInt(type));
    }

    // 基于enum的 intentType获得
    public static <E extends Enum<E>> E getIntentType(Intent intent, Class<E> clazz) {
        int index = intent.getIntExtra("INTENT_TYPE", 0);
        E type = intToEnum(clazz, index);
        return type;
    }

    public static TextView getTextView(int id, View view) {
        return (TextView) view.findViewById(id);
    }

    public static <T extends Object> ArrayList<T> toArrayList(HashSet<T> hashSet) {
        ArrayList<T> result = new ArrayList<T>();
        if (hashSet != null) {

            result.addAll(hashSet);
        }
        return result;
    }

    public static int makeCrash() {
        int[] a = new int[4];
        return a[4];
    }

    public static String getStr(JSONObject jsonObject, String name) {
        String result = "";
        try {
            if (jsonObject != null && jsonObject.has(name)) {
                result = jsonObject.getString(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static void startActivity(Intent intent) {
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.getInstance().startActivity(intent);
        }
    }

    public static View getSpaceViewForListView(int width, int height) {

        View view = new TextView(BaseApplication.getInstance());
        view.setLayoutParams(new ListView.LayoutParams(width, height));
        return view;
    }

    /**
     * 递归的回收view下的所有bitmap 建议运行在 Activity.onDestory()
     *
     * @param view
     */
    public static void recyleBitmapRecursively(final View view) {

        if (view != null) {
            view.setBackgroundDrawable(null);
        }
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(null);
        } else if (view instanceof ViewGroup) {
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                recyleBitmapRecursively(((ViewGroup) view).getChildAt(i));
            }
        }

    }

    /**
     * SharedPreferences 文件名
     */
    private static final String SETTINGS = "settings";

    /**
     * @param key
     * @param value
     * @return 是否保存成功
     */
    public static boolean setSharedPreferences(String key, String value) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return sp.edit().putString(key, value).commit();
    }

    /**
     * @param key
     * @param defaultValue
     * @return value
     */
    public static String getSharedPreferences(String key, String defaultValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * @param str md5加密
     * @return
     */
    public static String md5s(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        StringBuffer hexString = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            int length = hash.length;
            for (int i = 0; i < length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexString.toString();
    }

    /**
     * 获取string
     *
     * @param id
     * @return
     */
    public static String getStrValue(int id) {
        try {
            return BaseApplication.getInstance().getString(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 高清图与普通图相差大小
     *
     * @param path
     * @return
     */
    public static long getSaveSize(String path) {
        File file = new File(path);
        long size = file.length();
        if (size == 0) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                byte buffer[] = new byte[1024];
                size = 0;
                int num = 0;
                while ((num = in.read(buffer)) > 0) {
                    size += num;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        InputStream temp = null;
        try {
            temp = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeStream(temp, null, options);
        int with = options.outWidth;
        int heigth = options.outHeight;
        int max = with > heigth ? with : heigth;
        float savePercent = 0;
        if (max > 1080 && max < 1600) {
            float px_percent_1 = ((float) (max * 1.0)) / with;
            float px_percent_2 = ((float) (1024 * 1.0)) / with;
            savePercent = (float) (px_percent_1 * px_percent_1 * 0.8 * 0.8 - px_percent_2 * px_percent_2 * 0.4);
        } else if (max >= 1600) {
            float px_percent_1 = ((float) (1600 * 1.0)) / with;
            float px_percent_2 = ((float) (1024 * 1.0)) / with;
            savePercent = (float) (px_percent_1 * px_percent_1 * 0.8 * 0.8 - px_percent_2 * px_percent_2 * 0.4);
        } else {
            savePercent = (float) 0.4;
        }
        return (long) (size * savePercent);
    }

    /**
     * 指定大小的压缩
     *
     * @param path
     * @param limtMax
     * @return
     * @throws IOException
     */
    private static Bitmap revitionImageSize(String path, int limtMax) throws IOException {
        // 取得图片
        File file = new File(path);
        InputStream temp = new FileInputStream(file);
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeStream(temp, null, options);
        // 关闭流
        temp.close();
        // 生成压缩的图片
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            // 这一步是根据要设置的大小，使宽和高都能满足
            if ((options.outWidth >> i <= limtMax) && (options.outHeight >> i <= limtMax)) {
                // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                temp = new FileInputStream(file);
                // 这个参数表示 新生成的图片为原始图片的几分之一。
                options.inSampleSize = (int) Math.pow(2.0D, i);
                // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                options.inJustDecodeBounds = false;
                options.inTargetDensity = 240;
                bitmap = BitmapFactory.decodeStream(temp, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * @param path
     * @param limitMax 限制最大边
     * @param quality  质量
     * @return
     */
    public static byte[] getAddBitmapByte(String path, int limitMax, int quality) {
        // 用于存储bitmap的字节数组
        int LIMIT_MAX_MARGIN = limitMax;
        byte[] data = null;
        Bitmap localBitmap = null;
        try {
            localBitmap = revitionImageSize(path, 2 * LIMIT_MAX_MARGIN);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (localBitmap != null) {
            int width = localBitmap.getWidth();
            int height = localBitmap.getHeight();
            if (width > LIMIT_MAX_MARGIN || height > LIMIT_MAX_MARGIN) {
                // 创建操作图片用的matrix对象
                Matrix matrix = new Matrix();
                // 计算宽高缩放率.
                float scaleWidth = 0;
                float scaleHeight = 0;
                int newWidth = 0;
                int newHeight = 0;
                if (width > height) {
                    newWidth = LIMIT_MAX_MARGIN;
                    newHeight = (LIMIT_MAX_MARGIN * height) / width;
                    scaleWidth = ((float) newWidth) / width;
                    scaleHeight = ((float) newHeight) / height;
                } else {
                    newHeight = LIMIT_MAX_MARGIN;
                    newWidth = (LIMIT_MAX_MARGIN * width) / height;
                    scaleWidth = ((float) newWidth) / width;
                    scaleHeight = ((float) newHeight) / height;
                }
                // 缩放图片动作
                matrix.postScale(scaleWidth, scaleHeight);
                // 创建缩放后的图片
                try {
                    localBitmap = Bitmap.createBitmap(localBitmap, 0, 0, (int) width, (int) height, matrix, true);
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 压缩图片质量
            localBitmap.compress(CompressFormat.JPEG, quality, bos);
            data = bos.toByteArray();
            try {
                localBitmap.recycle();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }



    public static View inflate(int id) {
        return LayoutInflater.from(BaseApplication.getInstance()).inflate(id, null);
    }



    public static void importTempleData(BufferedInputStream in) {
        try {
            String path = BaseApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();

            path += "/" + "task.db";
            File file = new File(path);
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file);
            BufferedOutputStream out = new BufferedOutputStream(outStream);
            int c;
            while ((c = in.read()) >= 0) {
                out.write(c);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startActivityForResult(Intent intent, int requestCode) {
        if (intent != null) {
            ((Activity) BaseApplication.getInstance()).startActivityForResult(intent, requestCode);
            setActivityAnimation((Activity) BaseApplication.getInstance());
        }
    }

    /**
     * activity间切换动画
     *
     * @param activity
     */
    public static void setActivityAnimation(Activity activity) {
        if (getIsSetAnimation()) {
            activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    public static boolean getIsSetAnimation() {
        return true;
    }

    /**
     * 回收view的bitmap
     *
     * @param view
     */
    public static void recycleViewBitmap(View view) {
        if (view != null) {
            Drawable drawable = view.getBackground();
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                view.setBackgroundDrawable(null);
                drawable.setCallback(null);
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null) {
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    bitmap = null;
                }
            }
        }

    }

    public static void DelData() {
        try {
            String path = BaseApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();

            path += "/" + "task.db";
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    public static String encrypt(String input) throws Exception {
        return base64Encode(desEncrypt(input.getBytes()));
    }*/

/*    private static String base64Encode(byte[] s) {
        if (s == null)
            return null;

        return com.ihgoo.cocount.util.Base64.encodeBytes(s);
    }*/

    /**
     * DES密钥
     */
    private static final String private_key = "i*_uka*ld(_l);jsl;#k*&a";

    private static byte[] desEncrypt(byte[] plainText) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = private_key.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte data[] = plainText;
        byte encryptedData[] = cipher.doFinal(data);
        return encryptedData;
    }

    /**
     * 临时保存图片
     *
     * @param bitmap
     * @return 图片路径
     */
    public static String saveImageToAPP(Bitmap bitmap) {
        String result = "";
        OutputStream out = null;
        try {
            if (bitmap != null) {
                String path = BaseApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();
                path = path + "/../.cache/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                File cacheFile = new File(file, "imageShare.jpeg");
                cacheFile.deleteOnExit();
                out = new BufferedOutputStream(new FileOutputStream(cacheFile));
                bitmap.compress(CompressFormat.JPEG, 90, out);
                result = cacheFile.getPath();
                // 文件关闭
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 手动回收imageView图片资源
     *
     * @param imageView
     */
    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null)
            return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }





}
