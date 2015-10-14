package com.yanbober.support_library_demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yanbober.support_library_demo.app.BaseApplication;


/**
 * 用于控制settings.db数据库，存取一些设置类型的键值对
 */
public class SettingsDB extends BaseDB {
	static final String DB_NAME = "settings.db";
	private static Byte[] dblock = new Byte[0];

	public SettingsDB(String tableName) {
		super(tableName);
	}

	public SettingsDB(String tableName, boolean iswrite) {
		super(tableName, iswrite);
	}

	@Override
	protected Context getContext() {
		return BaseApplication.getInstance();
	}

	@Override
	protected void onDBCreate(SQLiteDatabase db) {
		try {
			db.beginTransaction();
			String sql;
			/* 全局设置 */
			sql = "create table settings_" + getDBVersion() + " (";
			sql += "key varchar(40) not null primary key default '', ";
			sql += "value varchar(4000) not null default ''";
			sql += ")";
			db.execSQL(sql);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();

		}
	}

	@Override
	protected void onDBUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public String getDBName() {
		return DB_NAME;
	}

	@Override
	public int getDBMinVersion() {
		return 0;
	}

	@Override
	public int getDBVersion() {
		return 1;
	}

	@Override
	protected Byte[] getDBLock() {
		return dblock;
	}
}
