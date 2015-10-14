package com.yanbober.support_library_demo.db;


import com.yanbober.support_library_demo.utils.Misc;

import java.util.HashMap;



/**
 * 數據結果行 包裝函數
 *
 *
 */
public class Row extends HashMap<String, String> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Row(HashMap<String, String> row) {
		row = row == null ? new HashMap<String, String>() : row;
		putAll(row);
	}

	public int getInt(String fieldName) {
		return Misc.getInt(get(fieldName), 0);
	}

	public long getLong(String fieldName) {
		return Misc.getLong(get(fieldName), 0);
	}

	public String get(String fieldName) {
		return super.get(fieldName);
	}

	public Long getLong(String string, long defaultValue) {

		long result = Misc.parseLong(get(string), defaultValue);

		return result;
	}
}
