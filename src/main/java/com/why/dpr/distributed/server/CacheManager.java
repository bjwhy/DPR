package com.why.dpr.distributed.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.why.dpr.run.EachDistribution;

public class CacheManager {
	private List<ICache> cacheList = new CopyOnWriteArrayList<ICache>();
	public static int cacheSize;

	private CacheManager() {
	}

	public static CacheManager getInstance() {
		return CacheManagerHolder.instance;
	}

	private static class CacheManagerHolder {
		static CacheManager instance = new CacheManager();
	}

	public void addCache(ICache cache) {
		cacheList.add(cache);
	}

	public void notifyObservers() {
		if (cacheList.size() == cacheSize) {
			for (ICache cache : cacheList) {
				cache.update();
			}
			EachDistribution test = new EachDistribution();
			new Thread(test).start();
		}
	}
}