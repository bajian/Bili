package com.yanbober.support_library_demo.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by hgx on 2015/6/26.
 */
public class MathUtil {


    /**
     * 计算方差
     *
     * @param nums
     * @return
     */
    public static double calD(ArrayList<Integer> nums) {
        double sum = 0;
        double n = nums.size();
        double d = 0;
        if (n > 0) {
            // 求和
            for (double num : nums) {
                sum += num;
            }
            double u = (double) sum / (double) nums.size();

            double temp = 0;
            for (double num : nums) {
                temp += (num - u) * (num - u);
            }

            d = Math.sqrt((double) temp / (double) n) / u;

        }

        return d;
    }


    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }






}
