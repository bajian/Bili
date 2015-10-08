package com.yanbober.support_library_demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author hgx
 * 2015-6-1
 *
 */
public class StringUtil {
	
	/**
	 * 取文本中间
	 * @param str 原文本
	 * @param left 左边文本
	 * @param right 右边文本
	 * @param mode =1 返回匹配文本包含左右边，其他只返回匹配文本
	 * @return string 返回匹配文本
	 */
	public static String getStringMiddle(String str,String left,String right,int mode) {
		Matcher matcher=Pattern.compile(left+"([\\d\\D]*?)"+right).matcher(str);
		if (matcher.find()) {
			if (mode==1) {
				return matcher.group(0);
			}
			return matcher.group(1);
		}
		return "";
	}

	/**
	 * 取文本中间
	 * @param str 原文本
	 * @param left 左边文本
	 * @param right 右边文本
	 * @return string 返回匹配文本不包含左右边
	 */
	public static String getStringMiddle(String str,String left,String right) {
		return getStringMiddle(str,left,right,2);
	}
	/**
	 * get请求获得网页文本，通常用于轻量级json数据
	 * @param url 网址
	 * @return string 网页源码
	 */
	public static String urlToText(String url){
		return inputStreamToString(urlToInputStream(url));
	}

	/**
	 * 将InputStream读成string
	 * @param is
	 * @return string utf-8
	 * */
	public static String inputStreamToString(InputStream is){
		InputStreamReader isr;
		String result="" ;
		String line ="";
		try {
			isr=new InputStreamReader(is ,"utf-8");
			BufferedReader bufferedReader=new BufferedReader(isr);
			try {
				while ((line=bufferedReader.readLine())!=null){
                    result+=line;
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}


	/**
	 * 将InputStream读成string
	 * @param  url
	 * @return inputStream
	 * */
	public static InputStream urlToInputStream(String url){
		try {
			return new URL(url).openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	public static <T> String join(Set<T> list) {
		return join(list.toArray(), ",");
	}

	public static String join(Object[] objects) {
		return join(objects, ",");
	}

	public static String join(Object[] objects, String glue) {
		int k = objects.length;
		if (k == 0)
			return "";
		StringBuilder out = new StringBuilder();
		out.append(objects[0]);
		for (int x = 1; x < k; ++x)
			out.append(glue).append(objects[x]);
		return out.toString();
	}



	/**
	 * 把字符串转换成List集合
	 * @param string
	 * @return
	 */
	public static ArrayList<String> paserString2List(String string){
		String[] split = string.split(",");
		ArrayList<String> strings = new ArrayList<>();
		for (String s : split) {
			strings.add(s);
		}
		if (strings.size()==0){
			return null;
		}else {
			return strings;
		}
	}



	/**
	 * 将长字符从截取剩下的用...代替
	 *
	 * @param input
	 * @param count
	 * @return
	 */
	public static String cutString(String input, int count) {
		return cutString(input, count, null);
	}

	/**
	 * 将长字符从截取剩下的用more代替,more为空则用省略号代替
	 *
	 * @param input
	 * @param count
	 * @param more
	 * @return
	 */
	public static String cutString(String input, int count, String more) {
		String resultString = "";
		if (input != null) {
			if (more == null) {
				more = "...";
			}
			if (input.length() > count) {
				resultString = input.substring(0, count) + more;
			} else {
				resultString = input;
			}
		}
		return resultString;
	}


	/**
	 * 获得指定中文长度对应的字符串长度，用于截取显示文字，一个中文等于两个英文
	 *
	 * @param chineseLengthForDisplay
	 * @param string
	 * @return
	 */
	public static int chineseWidth2StringLenth(int chineseLengthForDisplay, String string) {
		int result = 0;
		int displayWidth = chineseLengthForDisplay * 2;
		if (string != null) {
			for (char chr : string.toCharArray()) {
				// 中文
				if (chr >= 0x4e00 && chr <= 0x9fbb) {
					displayWidth -= 2;
				} else {
					// 英文
					displayWidth -= 1;
				}
				if (displayWidth <= 0) {
					break;
				}
				result++;
			}
		}
		return result;
	}





}
