package com.yanbober.support_library_demo.db;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 数据结果集 包装函数
 *
 */
public class Rows extends ArrayList<Row>{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Rows(ArrayList<HashMap<String, String>> rows) {
		rows =  ((rows==null )? new ArrayList<HashMap<String,String>>():rows); 
		for (HashMap<String, String> row :rows) {
			add(new Row(row));
		}
	}
	
}
