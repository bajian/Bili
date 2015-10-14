package com.yanbober.support_library_demo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * 数据库封装类。
 * <p>
 * 1.首次调用时，会先导入asset中的内置同名数据库，导入不成功则创建。每次发版前，注意内置库保证最新。
 * 2.数据库若打开不成功，会循环等待，直到成功打开，或者超时为止(当前为3s)。
 * <p>
 * 数据库使用时需注意每次调用结束之后必须关闭，必须使用try-catch-finally结构。 同时保证在数据开关期间，只进行必要的数据库操作。
 * <p>
 * 修改数据库时，注意DB_NAME,DB_VERSION,以及数据库升级
 * <p>
 * 必须以静态方法重载 DB_NAME、DB_VERSION、DB_MIN_VERSION
 *
 * @author Leo yin
 */
public abstract class BaseDB {
	/**
	 * 是否为可写入状态。true for rw；false for r
	 */
	public static boolean WRITABLE = true;
	// 操作表
	private String table;
	// 保存返回的数据库对象
	private SQLiteDatabase mSqLiteDatabase = null;
	private DatabaseHelper mDatabaseHelper = null;

	/**
	 * 数据库初始化
	 */
	public static boolean DATA_INITED_PERIOD = false;
	/**
	 * 数据库初始化
	 */
	public static boolean DATA_INITED_MALE = false;

	/**
	 * 请求超时，s
	 */
	public static int TIMEOUT = 3 * 60;

	/**
	 * @return 最小数据库升级版本
	 */
	public abstract int getDBMinVersion();

	/**
	 * @return 当前数据库版本
	 */
	public abstract int getDBVersion();

	/**
	 * @return String
	 */
	public abstract String getDBName();

	/**
	 * @return context
	 */
	protected abstract Context getContext();

	protected abstract Byte[] getDBLock();

	/**
	 * 创建数据库
	 *
	 * @param db
	 */
	protected abstract void onDBCreate(SQLiteDatabase db);

	/**
	 * 数据库升级
	 *
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	protected abstract void onDBUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

	/**
	 * 多线程互斥锁
	 */
	// public static Byte[] muti_thread_lock = new Byte[0];
	/**
	 * 默认打开只读数据库,必须调用close()
	 *
	 * @param tableName
	 */
	public BaseDB(String tableName) {
		this(tableName, false);
	}

	/**
	 * 打开读写数据库，必须调用close()
	 *
	 * @param tableName
	 * @param isWritable
	 */
	public BaseDB(String tableName, boolean isWritable) {
		synchronized (getDBLock()) {
//			importDatabase(getContext(), false);
			table = tableName + "_" + getDBVersion();
		}
		if (isWritable) {
			openWritableDatabase();
		} else {
			openReadableDatabase();
		}
	}

	/**
	 * 打开只读数据库, 必须调用close()
	 */
	private void openReadableDatabase() {
		mDatabaseHelper = new DatabaseHelper();
		mSqLiteDatabase = mDatabaseHelper.getReadableDatabase();
	}

	/**
	 * 打开读写数据库, 必须调用close()
	 */
	private void openWritableDatabase() {
		mDatabaseHelper = new DatabaseHelper();
		mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
	}

	public void close() {
		if (mSqLiteDatabase != null && mSqLiteDatabase.isOpen()) {
			try {
				mSqLiteDatabase.close();
			} catch (Exception e) {
				//Ihgoo.warning(WARNING.DB_CLOSE_EXCEPTION, e);
			}
		}
	}

	/**
	 * 开启事务
	 */
	public void beginTransaction() {
		if (!mSqLiteDatabase.inTransaction()) {
			try {
				mSqLiteDatabase.beginTransaction();
			} catch (Exception e) {
				//Ihgoo.warning("DB_BEGIN_TRANS", e);
			}
		}
	}

	/**
	 * 事务完成
	 */
	public void setTransactionSuccessful() {
		if (mSqLiteDatabase != null && mSqLiteDatabase.inTransaction()) {
			try {
				mSqLiteDatabase.setTransactionSuccessful();
			} catch (Exception e) {
				//Ihgoo.warning("DB_SET_TRANS_SUCCESS", e);
			}
		}
	}

	/**
	 * 结束事务
	 */
	public void endTransaction() {
		if (mSqLiteDatabase != null && mSqLiteDatabase.inTransaction()) {
			try {
				mSqLiteDatabase.endTransaction();
			} catch (Exception e) {
				//Ihgoo.warning("DB_END_TRANS", e);
			}
		}
	}

	/**
	 * 返回指定表中记录总数
	 *
	 * @return
	 */
	public int getCount() {
		return getCount(null);
	}

	/**
	 * 根据条件记录符合的记录数
	 *
	 * @param where
	 *            条件
	 * @return
	 */
	public int getCount(Pair<String, ArrayList<String>> where) {
		int count = 0;
		synchronized (getDBLock()) {
			Cursor cr = null;
			try {
				cr = mSqLiteDatabase.query(table, new String[] { "count(1)" }, where != null ? where.first : null, where != null
						&& where.second != null ? where.second.toArray(new String[] {}) : null, null, null, null);
				if (cr.getCount() > 0) {
					cr.moveToFirst();
					count = cr.getInt(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (cr != null) {
						cr.close();
					}
				} catch (Exception e2) {
					//Ihgoo.warning(WARNING.DB_CURSOR_EXCEPTION, e2);
				}
			}

		}
		return count;
	}

	/**
	 * 根据条件删除记录
	 *
	 * @param where
	 *            条件
	 * @return
	 */
	public int delete(Pair<String, ArrayList<String>> where) {
		int affected = 0;
		synchronized (getDBLock()) {
			affected = mSqLiteDatabase.delete(table, (where != null && where.first != null ? where.first : null), (where != null
					&& where.first != null && where.second != null ? where.second.toArray(new String[] {}) : null));
			// //Ihgoo.warning(WARNING.DB_CLOSE_EXCEPTION, e2);
		}
		return affected;
	}

	/**
	 * 插入数据
	 *
	 * @param row
	 *            键值对
	 * @return
	 */
	public long insert(HashMap<String, String> row) {
		return insert(row, false);
	}

	/**
	 * 插入数据
	 *
	 * @param row
	 *            键值对
	 * @param replace
	 *            是否替换
	 * @return
	 */
	public long insert(HashMap<String, String> row, boolean replace) {
		return insert(table, row, replace);
	}

	/**
	 * 在指定表插入数据，用于操作其他表
	 *
	 * @param table_name
	 * @param row
	 * @param replace
	 * @return
	 */
	public long insert(String table_name, HashMap<String, String> row, boolean replace) {
		long ret = 0;
		if (!table_name.endsWith("_" + getDBVersion())) {
			table_name += "_" + getDBVersion();
		}
		synchronized (getDBLock()) {
			try {
				if (replace) {
					ret = mSqLiteDatabase.replace(table_name, null, parseRowdata(row));
				} else {
					ret = mSqLiteDatabase.insert(table_name, null, parseRowdata(row));
				}
			} catch (Exception e) {
				e.printStackTrace();
				ret = -1;
			}

		}
		return ret;
	}

	/**
	 * 更新数据
	 *
	 * @param row
	 *            要更新的键值对
	 * @param where
	 *            条件
	 * @return
	 */
	public int update(HashMap<String, String> row, Pair<String, ArrayList<String>> where) {
		int ret = 0;
		synchronized (getDBLock()) {
			try {
				ret = mSqLiteDatabase.update(table, parseRowdata(row), where != null ? where.first : null, where != null
						&& where.second != null ? where.second.toArray(new String[] {}) : null);
			} catch (Exception e) {
				e.printStackTrace();
				ret = -1;
			}
		}
		return ret;
	}

	/**
	 * 查找并返回单条记录
	 *
	 * @param where
	 *            条件
	 * @return 找不到数据时返回null
	 */
	public HashMap<String, String> findOne(Pair<String, ArrayList<String>> where) {
		return findOne(where, null);
	}

	/**
	 * 查找并返回单条记录
	 *
	 * @param where
	 *            条件
	 * @param order
	 *            排序规则
	 * @return 找不到数据时返回null
	 */
	public HashMap<String, String> findOne(Pair<String, ArrayList<String>> where, String order) {
		ArrayList<HashMap<String, String>> list = findAll(where, order, 1);
		if (list != null) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 返回前N条记录
	 *
	 * @param where
	 *            条件
	 * @return 无符合记录时返回null，最多返回10000条
	 */
	public ArrayList<HashMap<String, String>> findAll(Pair<String, ArrayList<String>> where) {
		return findAll(where, null, 10000);
	}

	/**
	 * 返回前N条记录
	 *
	 * @param where
	 *            条件
	 * @param limit
	 *            限制返回数目
	 * @return 无符合记录时返回null
	 */
	public ArrayList<HashMap<String, String>> findAll(Pair<String, ArrayList<String>> where, int limit) {
		return findAll(where, null, limit);
	}

	/**
	 * 返回前N条记录
	 *
	 * @param where
	 *            条件
	 * @param order
	 *            排序条件
	 * @param limit
	 *            限制返回数目
	 * @return 无符合记录时返回null
	 */
	public ArrayList<HashMap<String, String>> findAll(Pair<String, ArrayList<String>> where, String order, int limit) {
		return findAll(where, order, 0, limit);
	}

	public ArrayList<HashMap<String, String>> findAll(Pair<String, ArrayList<String>> where, String order, int start, int offset) {
		return findAll(table, where, order, start, offset);
	}

	/**
	 * 返回指定条件的记录
	 *
	 * @param where
	 *            条件
	 * @param order
	 *            排序
	 * @param start
	 *            起始位置
	 * @param offset
	 *            偏移量
	 * @return 无符合记录时返回null
	 */
	public ArrayList<HashMap<String, String>> findAll(String table, Pair<String, ArrayList<String>> where, String order,
													  int start, int offset) {
		ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
		synchronized (getDBLock()) {
			Cursor cur = null;
			try {
				String sql = "select * from " + table + " " + (where != null ? "where " + where.first : "") + " "
						+ (order != null && order.length() > 0 ? "order by " + order : "") + " limit " + start + "," + offset;
				cur = mSqLiteDatabase.rawQuery(sql, where != null && where.second != null ? where.second.toArray(new String[] {})
						: null);
				if (cur.getCount() > 0) {
					cur.moveToFirst();
					do {
						String[] cols = cur.getColumnNames();
						HashMap<String, String> row = new HashMap<String, String>();
						int length = cols.length;
						for (int j = 0; j < length; j++) {
							row.put(cols[j], cur.getString(j));
						}
						rows.add(row);
					} while (cur.moveToNext());
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (cur != null) {
						cur.close();
					}
				} catch (Exception e2) {
					//Ihgoo.warning(WARNING.DB_CURSOR_EXCEPTION, e2);
				}
			}
		}

		if (rows.size() > 0) {
			return rows;
		} else {
			return null;
		}
	}

	/**
	 * 返回指定条件的记录
	 *
	 * @param where
	 *            条件
	 * @param order
	 *            排序
	 * @param start
	 *            起始位置
	 * @param offset
	 *            偏移量
	 * @return 无符合记录时返回null
	 */
	public ArrayList<HashMap<String, String>> findAll2(String table, Pair<String, ArrayList<String>> where, String order,
													   int limit, int offset) {
		ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
		synchronized (getDBLock()) {
			Cursor cur = null;
			try {
				String sql = "select * from " + table + " " + (where != null ? "where " + where.first : "") + " "
						+ (order != null && order.length() > 0 ? "order by " + order : "") + " limit " + limit;
				if (offset > 0)
					sql += " offset " + offset;
				cur = mSqLiteDatabase.rawQuery(sql, where != null && where.second != null ? where.second.toArray(new String[] {})
						: null);
				if (cur.getCount() > 0) {
					cur.moveToFirst();
					do {
						String[] cols = cur.getColumnNames();
						HashMap<String, String> row = new HashMap<String, String>();
						int length = cols.length;
						for (int j = 0; j < length; j++) {
							row.put(cols[j], cur.getString(j));
						}
						rows.add(row);
					} while (cur.moveToNext());
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (cur != null) {
						cur.close();
					}
				} catch (Exception e2) {
					//Ihgoo.warning(WARNING.DB_CURSOR_EXCEPTION, e2);
				}
			}
		}

		if (rows.size() > 0) {
			return rows;
		} else {
			return null;
		}
	}

	/**
	 * 检测是否包含某个字符串
	 *
	 * @param coloum
	 * @param key
	 * @return
	 */
	public boolean isContainsValue(String coloum, String key) {
		synchronized (getDBLock()) {
			Cursor cur = null;
			try {
				String sql = "select * from " + table;
				cur = mSqLiteDatabase.rawQuery(sql, null);
				if (cur != null && cur.getCount() > 0) {
					cur.moveToFirst();
					do {
						String coloumstr = cur.getString(cur.getColumnIndex(coloum));
						if (coloumstr.contains(key)) {
							return true;
						}
					} while (cur.moveToNext());
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (cur != null) {
						cur.close();
					}
				} catch (Exception e2) {
					//Ihgoo.warning(WARNING.DB_CURSOR_EXCEPTION, e2);
				}
			}
		}
		return false;
	}

	ContentValues parseRowdata(HashMap<String, String> row) {
		ContentValues values = new ContentValues();
		Iterator<Entry<String, String>> iterator = row.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			values.put(entry.getKey(), entry.getValue());
		}
		return values;
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper() {
			super(getContext(), getDBName(), null, getDBVersion());
		}

		/*
		 * 调用前必须加锁 db_write_lock
		 *
		 * @see android.database.sqlite.SQLiteOpenHelper#getReadableDatabase()
		 */
		@Override
		public SQLiteDatabase getReadableDatabase() {
			synchronized (getDBLock()) {
				SQLiteDatabase db = null;
				boolean retry = false;
				long time = System.currentTimeMillis();
				Exception ex = null;
				do {
					retry = false;
					try {
						db = super.getReadableDatabase();
					} catch (Exception e) {
						e.printStackTrace();
						ex = e;
						retry = true;
						try {
							// //Ihgoo.warning(WARNING.DB_LOCK);
							Thread.sleep(5 + (int) (Math.random() * 5)
									/**
									 * 随机数防止可能的线程并发
									 **/
							);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				} while (retry && System.currentTimeMillis() - time < TIMEOUT * 1000l);

				if (retry) {
					//Ihgoo.warning(WARNING.DB_LOCK, ex);
				}
				return db;
			}

		}

		/*
		 * 调用前必须加锁 db_write_lock
		 *
		 * @see android.database.sqlite.SQLiteOpenHelper#getWritableDatabase()
		 */
		@Override
		public SQLiteDatabase getWritableDatabase() {
			synchronized (getDBLock()) {
				SQLiteDatabase db = null;

				boolean retry = false;
				long time = System.currentTimeMillis();
				Exception ex = null;
				do {
					retry = false;
					try {
						db = super.getWritableDatabase();
					} catch (Exception e) {
						e.printStackTrace();
						ex = e;
						retry = true;
						try {
							// //Ihgoo.warning(WARNING.DB_LOCK);
							Thread.sleep(5 + (int) (Math.random() * 5)
									/**
									 *
									 * 随机数防止可能的线程并发
									 **/
							);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}

				} while (retry && System.currentTimeMillis() - time < TIMEOUT * 1000l);

				if (retry) {
					//Ihgoo.warning(WARNING.DB_LOCK, ex);
				}

				return db;
			}
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			onDBCreate(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (oldVersion < newVersion) {
				if (oldVersion < getDBMinVersion()) {
					oldVersion = getDBMinVersion();
				}
				makeSureNewTables(db, oldVersion + 1);
				update2NewTables(db, oldVersion, oldVersion + 1);
				// 数据库升级
				onDBUpgrade(db, oldVersion, newVersion);
				oldVersion++;
				onUpgrade(db, oldVersion, newVersion);
				if (oldVersion == newVersion) {
					clearOldTables(db);
				}
			}
		}

		ArrayList<String> getTables(SQLiteDatabase db) {
			Cursor cur = db.rawQuery(" select * from sqlite_master where type = 'table' limit 1000", null);
			ArrayList<String> arr = new ArrayList<String>();
			if (cur.getCount() > 0) {
				cur.moveToFirst();
				do {
					String[] cols = cur.getColumnNames();
					HashMap<String, String> row = new HashMap<String, String>();
					for (int j = 0; j < cols.length; j++) {
						row.put(cols[j], cur.getString(j));
					}
					String name = row.get("tbl_name");
					if (name.length() < 7 || (!name.substring(0, 7).equals("sqlite_"))) {
						arr.add(name);
					}
				} while (cur.moveToNext());
			}
			cur.close();
			if (arr.size() > 0) {
				return arr;
			} else {
				return null;
			}
		}

		void clearOldTables(SQLiteDatabase sqlite) {
			ArrayList<String> tables = getTables(sqlite);
			if (tables != null) {
				int size = tables.size();
				for (int i = 0; i < size; i++) {
					String name = tables.get(i);
					String suffix = "_" + getDBVersion();
					if (!name.substring(name.length() - suffix.length()).equals(suffix)) {
						sqlite.execSQL("drop table " + tables.get(i));
					}
				}
			}
		}

		void makeSureNewTables(SQLiteDatabase sqlite, int ver) {
			ArrayList<String> tables = getTables(sqlite);
			String suffix = "_" + ver;
			int size = tables.size();
			for (int i = 0; i < size; i++) {
				String name = tables.get(i);
				if (name.substring(name.length() - suffix.length()).equals(suffix)) {
					sqlite.execSQL("drop table if exists " + tables.get(i));
				}
			}
		}

		void update2NewTables(SQLiteDatabase sqlite, int oldVersion, int newVersion) {
			ArrayList<String> tables = getTables(sqlite);
			String suffix = "_" + oldVersion;
			String suffix1 = "_" + newVersion;
			int size = tables.size();
			for (int i = 0; i < size; i++) {
				String name = tables.get(i);
				if (name.substring(name.length() - suffix.length()).equals(suffix)) {
					sqlite.execSQL("alter table " + tables.get(i) + " rename to " + name.replace(suffix, suffix1));
				}
			}
		}

	}

	/**
	 * 导入数据库 , 使用前必须加锁
	 *
	 * @param isForce
	 *            true 不论当前flag是什么,都从assets里导入
	 */
//	void importDatabase(Context context, boolean isForce) {
//		// Ihgoo.log("importing database1");
//		if (!BaseDB.DATA_INITED_PERIOD || !BaseDB.DATA_INITED_MALE || isForce) {
//			if (getDBName().equals("period.db")) {
//				BaseDB.DATA_INITED_PERIOD = true;
//			} else if (getDBName().equals("male.db")) {
//				BaseDB.DATA_INITED_MALE = true;
//			} else {
//				return;
//			}
//			String path = WeijianzhiApplication.getInstance().getFilesDir().getAbsolutePath();
//			path = path + "/../databases/";
//			File file = new File(path + getDBName());
//			if (!file.exists() && !chkDataExists(context)) {
//				try {
//					BufferedInputStream in;
//					in = new BufferedInputStream(context.getAssets().open(getDBName()), 8192);
//					Ihgoo.importData(in, getDBName());
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}

	/**
	 * 检测databases路径下数据库是否存在
	 *
	 * @param context
	 * @return
	 */
	boolean chkDataExists(Context context) {
		// Ihgoo.log(context, context.getFilesDir());
		String path = context.getFilesDir().getAbsolutePath();
		path = path + "/../databases/" + getDBName();
		File file = new File(path);
		return file.exists();
	}

}
