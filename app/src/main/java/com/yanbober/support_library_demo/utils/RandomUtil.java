package com.yanbober.support_library_demo.utils;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 随机工具类
 * modify form <a href="http://www.trinea.cn" target="_blank">Trinea</a>
 * @author trinea
 * @date 2014-12-10
 */
public class RandomUtil {
    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS             = "0123456789";
    public static final String LETTERS             = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";

    private RandomUtil() {
        throw new AssertionError();
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase, lowercase letters and numbers
     *
     * @param length
     * @return
     * @see RandomUtil#getRandom(String source, int length)
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of numbers
     *
     * @param length
     * @return
     * @see RandomUtil#getRandom(String source, int length)
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase and lowercase letters
     *
     * @param length
     * @return
     * @see RandomUtil#getRandom(String source, int length)
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase letters
     *
     * @param length
     * @return
     * @see RandomUtil#getRandom(String source, int length)
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of lowercase letters
     *
     * @param length
     * @return
     * @see RandomUtil#getRandom(String source, int length)
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of chars in source
     *
     * @param source
     * @param length
     * @return <ul>
     * <li>if source is null or empty, return null</li>
     * <li>else see {@link RandomUtil#getRandom(char[] sourceChar, int length)}</li>
     * </ul>
     */
    public static String getRandom(String source, int length) {
        return source == null ? null : getRandom(source.toCharArray(), length);
    }

    /**
     * get a fixed-length random string, its a mixture of chars in sourceChar
     *
     * @param sourceChar
     * @param length
     * @return <ul>
     * <li>if sourceChar is null or empty, return null</li>
     * <li>if length less than 0, return null</li>
     * </ul>
     */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * get random int between 0 and max
     *
     * @param max
     * @return <ul>
     * <li>if max <= 0, return 0</li>
     * <li>else return random int between 0 and max</li>
     * </ul>
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * get random int between min and max
     *
     * @param min
     * @param max
     * @return <ul>
     * <li>if min > max, return 0</li>
     * <li>if min == max, return min</li>
     * <li>else return random int between min and max</li>
     * </ul>
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    /**
     * Shuffling algorithm, Randomly permutes the specified array using a default source of randomness
     */
    public static boolean shuffle(Object[] objArray) {
        if (objArray == null) {
            return false;
        }
        return shuffle(objArray, getRandom(objArray.length));
    }

    /**
     * Shuffling algorithm, Randomly permutes the specified array
     */
    public static boolean shuffle(Object[] objArray, int shuffleCount) {
        int length;
        if (objArray == null || shuffleCount < 0 || (length = objArray.length) < shuffleCount) {
            return false;
        }

        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            Object temp = objArray[length - i];
            objArray[length - i] = objArray[random];
            objArray[random] = temp;
        }
        return true;
    }

    /**
     * Shuffling algorithm, Randomly permutes the specified int array using a default source of randomness
     */
    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }

        return shuffle(intArray, getRandom(intArray.length));
    }

    /**
     * Shuffling algorithm, Randomly permutes the specified int array
     */
    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0 || (length = intArray.length) < shuffleCount) {
            return null;
        }

        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }


    /**
     * 从数组里随机的获取一个
     *
     * @param <T>
     * @param list
     * @return
     */
    public static <T> T getRandOneFromArr(T[] list) {
        T result = null;
        if (list != null && list.length > 0) {
            int count = list.length;
            // int index = count - 1;
            int index = (int) (Math.random() * count);
            result = list[index];
        }
        return result;
    }

    /**
     * 从数组里随机的获取一个
     *
     * @param List
     * @param arrayList
     * @return
     */
    public static <T> T getRandOneFromArr(List<T> arrayList) {
        T result = null;
        if (arrayList != null && arrayList.size() > 0) {
            int count = arrayList.size();
            int index = count - 1;
            index = (int) (Math.random() * index);
            result = arrayList.get(index);
        }
        return result;
    }


    public static <T> T getRandOneFromArr(Set<T> sets) {
        T result = null;
        if (sets != null) {
            int size = sets.size();
            int random = new Random().nextInt(size);
            int i = 0;
            for (T item : sets) {
                if (i == random) {
                    result = item;
                    break;
                }
                i = i + 1;
            }
        }
        return result;
    }


}
