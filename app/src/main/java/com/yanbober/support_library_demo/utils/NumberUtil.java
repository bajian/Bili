package com.yanbober.support_library_demo.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * @author hgx
 * @date 2014-11-21
 */
public class NumberUtil {



    /**
     * 检查一个字符串是否是整数。该函数将避免NumberFormat异常。
     *
     * @param num 需要检查的字符串
     * @return 返回true表示是一个整数;如果字符串为为空，或者不符合规则返回false.
     * @see {@link NumberFormatException}
     */
    public static final boolean checkNumberFormat(String num) {
        if (TextUtils.isEmpty(num))
            return false;
        return num.matches("-[0-9]+|[0-9]+");
    }

    /**
     * DecimalFormat("0.00")会四舍五入
     * 该方法是截取小数点后两位
     * @param number 数据
     */
    public static String dotSubTwo(double number) {
        DecimalFormat df = new DecimalFormat("0.00");
        String str = number + "";
        if(str.contains(".")) {
            if(str.substring(str.indexOf(".")).length() == 1) {
                str = str + "00";
            }else if(str.substring(str.indexOf(".")).length() == 2) {
                str = str + "0";
            }else if(str.substring(str.indexOf(".")).length() > 3) {
                str = str.substring(0,str.indexOf(".") + 3);
            }
        }else {
            str = df.format(number);
        }
        return str;
    }


    public static int convertToint(String intStr, int defValue) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static long convertTolong(String longStr, long defValue) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static float convertTofloat(String fStr, float defValue) {
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static double convertTodouble(String dStr, double defValue) {
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }


    public static Integer convertToInteger(String intStr) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Long convertToLong(String longStr) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Float convertToFloat(String fStr) {
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Double convertToDouble(String dStr) {
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Float max(Float... nums) {

        Float result = null;
        if (nums.length > 0) {
            result = nums[0];
            for (Float num : nums) {
                if (num > result) {
                    result = num;
                }
            }
        }
        return result;
    }

    public static Float min(Float... nums) {

        Float result = null;
        if (nums.length > 0) {
            result = nums[0];
            for (Float num : nums) {
                if (num < result) {
                    result = num;
                }
            }
        }
        return result;
    }

    public static Float sum(Float... nums) {

        Float result = 0f;
        for (Float num : nums) {
            result += num;
        }
        return result;
    }
}
