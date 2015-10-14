package com.yanbober.support_library_demo.db;

import android.util.Pair;

import com.yanbober.support_library_demo.utils.Misc;
import com.yanbober.support_library_demo.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * settings表的存取通用类
 * key-value 集 对应表 settings 已经添加memCache
 * <p>获取顺序：首先从缓存获取，若缓存中没有，则从数据库中读取，并存入缓存；
 * <p>若读取是发现缓存为空，则全部读取出settings表中的数据，存入缓存，以便后续使用
 * 清除、修改顺序：同时修改缓存，以及数据库
 *
 * @author ihgoo
 */
public class Settings {
	final static String TABLE_NAME = "settings";
	public final static String SET = "1";
	public final static String UNSET = "0";
	public static Byte[] settings_lock = new Byte[0];
	static Cache<String, String> memCache = new Cache<String, String>(new Cache.CallBack<String, String>() {

		public String onMiss(String key) {
			if (memCache.getSize() == 0) {
				Pair<String, ArrayList<String>> where = new Pair<String, ArrayList<String>>(" 1 = 1 ", null);
				Rows rows = null;
				synchronized (settings_lock) {
					SettingsDB db2 = null;
					try {
						db2 = new SettingsDB(TABLE_NAME);
						rows = new Rows(db2.findAll(where, 100));
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (db2 != null) {
							db2.close();
						}
					}
				}
				if (rows != null) {
					for (Row row : rows) {
						String mKey = row.get("key");
						String mValue = row.get("value");
						if (mKey != null && mValue != null) {
							memCache.put(mKey, mValue);
						}
					}
				}
				return memCache.getRaw(key);

			} else {
				Pair<String, ArrayList<String>> where = new Pair<String, ArrayList<String>>("key=?", new ArrayList<String>());
				where.second.add(key);
				HashMap<String, String> result = null;
				synchronized (settings_lock) {
					SettingsDB db2 = null;
					try {
						db2 = new SettingsDB(TABLE_NAME);
						result = db2.findOne(where);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (db2 != null) {
							db2.close();
						}
					}
				}
				if (result != null && result.size() > 0) {
					return result.get("value");
				} else {
					return null;
				}
			}
		}

		public HashMap<String, String> onMisses(String key) {
			return null;
		}

		public void onRemove(String key, String value) {

		}

		public void afterHit(String key, String value) {

		}
	}, 100);

	public static int set(final String key, ArrayList<Integer> value) {
		return set(key, StringUtil.join(value.toArray(), ","));
	}

	public static ArrayList<Integer> getArraylist(String key) {
		String string = get(key);
		String[] strings = string.split(",");
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (String string2 : strings) {

			if (string2 != null && string2.length() > 0) {
				try {
					result.add(Integer.parseInt(string2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	public static int set(ISetable setable, final Object value) {
		return set(setable.getKey(), value);
	}


	/**
	 * 设置键值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static int set(final String key, final Object value) {

		/*
		 * if (key.equals(SettingsV2.KEY_INFO_PERIOD)) { Ihgoo.debug(value); }
		 */
		if (value != null && !value.equals(get(key, null))) {
			memCache.put(key, value.toString());
			// 插入数据库队列
			HashMap<String, String> cv = new HashMap<String, String>();
			cv.put("key", key);
			cv.put("value", value.toString());
			synchronized (settings_lock) {
				SettingsDB db2 = null;
				try {
					db2 = new SettingsDB(TABLE_NAME, SettingsDB.WRITABLE);
					db2.beginTransaction();
					db2.insert(cv, true);
					db2.setTransactionSuccessful();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (db2 != null) {
						db2.endTransaction();
						db2.close();
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 直接对数据库进行操作，立即生效
	 * @param key
	 */
	public static void del(final String key) {
		memCache.remove(key);
		// 加入数据库操作队列
		Pair<String, ArrayList<String>> where = new Pair<String, ArrayList<String>>("key=?", new ArrayList<String>());
		where.second.add(key);
		synchronized (settings_lock) {
			SettingsDB db2 = null;
			try {
				db2 = new SettingsDB(TABLE_NAME, SettingsDB.WRITABLE);
				db2.delete(where);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (db2 != null) {
					db2.close();
				}
			}
		}
	}

	/**
	 * 返回键值
	 *
	 * @param key
	 * @return
	 */
	public static String get(String key, String defaultValue) {
		String result = memCache.get(key);
		if (result != null) {
			return result;
		} else {
			return defaultValue;
		}
	}

	public static String get(ISetable key, String defaultValue) {
		return get(key.getKey(), defaultValue);
	}

	/**
	 * 判断某键是否设置
	 *
	 * @param key
	 * @return
	 */
	public static boolean isNull(String key) {
		String result = memCache.get(key);
		if (result == null) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean getBoolean(String key) {
		boolean result = false;
		if (get(key, UNSET).equals(SET)) {
			result = true;
		}
		return result;
	}

	public static void setBoolean(String key, boolean value) {
		set(key, value ? SET : UNSET);
	}

	/**
	 * @param key
	 * @return return "" if not exist 
	 */
	public static String get(String key) {
		return get(key, "");
	}

	public static int getInt(ISetable iSetable) {
		return getInt(iSetable, 0);
	}

	public static int getInt(ISetable iSetable, int defaultVal) {
		return getInt(iSetable.getKey(), defaultVal);
	}

	public static int getInt(String key) {
		return Misc.parseInt(get(key), 0);
	}

	public static int getInt(String key, int defaultVal) {
		return Misc.parseInt(get(key), defaultVal);
	}

	public static long getLong(String key) {
		return Misc.parseLong(get(key), 0);
	}

	public static long getLong(String key, long defaultVal) {
		return Misc.parseLong(get(key), defaultVal);
	}


	public static void clearCache() {
		memCache.removeAll();
	}

	public static boolean isNull(ISetable iSetable) {
		return isNull(iSetable.getKey());
	}

	public static String get(ISetable iSetable) {
		return get(iSetable.getKey());
	}

	public static void del(ISetable iSetable) {
		del(iSetable.getKey());
	}

	public static HashMap<String, String> getRowMap(String key, Object value) {
		HashMap<String, String> cv = new HashMap<String, String>();
		cv.put("key", key);
		cv.put("value", value.toString());
		return cv;
	}
}
