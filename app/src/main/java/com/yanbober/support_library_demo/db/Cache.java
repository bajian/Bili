package com.yanbober.support_library_demo.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存缓存器
 *
 *
 * @param <K>
 * @param <V>
 */
public class Cache<K, V> {
	private int MAX_SIZE = 10;
	private float DELETE_RATE = 0.5f;

	Map<K, V> cache = new ConcurrentHashMap<K, V>();

	// 记录命中率
	// HashMap<K, Integer> hit = new HashMap<K, Integer>();
	ArrayList<K> hit = new ArrayList<K>();

	public interface CallBack<K, V> {
		V onMiss(K key);

		HashMap<K, V> onMisses(K key);

		void onRemove(K key, V value);

		// 命中后需要执行的函数
		void afterHit(K key, V value);
	}

	public int getSize() {
		int result = 0;
		synchronized (dataLock) {
			result = cache.size();
		}
		return result;
	}

	CallBack<K, V> callback;

	public Cache(CallBack<K, V> callBack, int size) {
		this.callback = callBack;
		this.MAX_SIZE = size;
	}

	boolean isset(K key) {
		return cache.containsKey(key);
	}

	public V getRaw(K key) {
		V result = null;
		synchronized (dataLock) {
			if (isset(key))
				result = cache.get(key);
		}
		return result;
	}

	Byte[] dataLock = new Byte[0];

	public V get(K key) {
		V result = null;
		synchronized (dataLock) {
			if (isset(key))
				result = cache.get(key);

			if (result != null) {
				// 命中
				// hit.put(key, hit.get(key) + 1);
				addHit(key);
				callback.afterHit(key, result);

			} else {
				result = callback.onMiss(key);
				if (result != null) {
					put(key, result);
				}
				// misses
				HashMap<K, V> results = callback.onMisses(key);
				if (results != null) {
					cache.putAll(results);
				}
				// 返回result
				if (isset(key))
					result = cache.get(key);
			}
		}
		return result;
	}

	public int put(K key, V v) {
		synchronized (dataLock) {
			if (cache.size() > MAX_SIZE) {
				kick2();
			}
			cache.put(key, v);
			addHit(key);
		}
		return 0;
	}

	void remove(ArrayList<K> keys) {
		for (K key : keys) {
			remove(key);
		}
	}

	void clear() {
		hit.clear();
		cache.clear();
		// datas.clear();
	}

	public void remove(K key) {
		synchronized (dataLock) {

			if (isset(key)) {
				callback.onRemove(key, cache.get(key));
				hit.remove(key);
				cache.remove(key);
				// datas.remove(key);
			}
		}
	}

	public void removeAll() {
		synchronized (dataLock) {
			cache.clear();
		}
	}

	void addHit(K key) {
		if (hit.contains(key)) {
			hit.remove(key);
		}
		hit.add(key);
	}

	void kick2() {
		int count = (int) (hit.size() - MAX_SIZE * DELETE_RATE);
		while (count > 0) {
			remove(hit.get(0));
			count--;
		}
	}

	/**
	 * 指定数量 kick
	 */
	/*
	 * void kick() { Comparator<Entry<K, Integer>> comparator = new
	 * Comparator<Map.Entry<K, Integer>>() { public int compare(Entry<K,
	 * Integer> object1, Entry<K, Integer> object2) { return object1.getValue()
	 * - object2.getValue(); } }; List<Entry<K, Integer>> list = new
	 * ArrayList<Entry<K, Integer>>(); list.addAll(hit.entrySet());
	 * Collections.sort(list, comparator); int deleteNum = (int) (list.size() *
	 * DELETE_RATE); for (int i = 0; i < deleteNum; i++) { if (list.get(i) !=
	 * null) { remove(list.get(i).getKey()); } } }
	 */
}