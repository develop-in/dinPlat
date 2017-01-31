/*
 *
 */
package com.Xnote.memcached;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * The Class MemcachedUtil.
 *
 * @author skplanet
 */
public class MemcachedUtil {

	/** The config. */
	@Autowired
//	private CachedConfig config;

	/** The m. */
	private MemCachedClient[] m = null;
	// private static MemcachedUtilNew instance = null;
	/** The cur index. */
	private int curIndex = 0;

	/** The min index. */
	private final int minIndex = 0;

	/** The max index. */
	private final int maxIndex = 20;

	/** The pool. */
	private SockIOPool pool;

	/**
	 * @return the config
	 */
//	public CachedConfig getConfig() {
//		return config;
//	}

	/**
	 * @param config
	 *            the config to set
	 */
//	public void setConfig(CachedConfig config) {
//		this.config = config;
//	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
//		String[] serverlist = config.getConfigValueString("MEMCACHED_SERVERLIST").split(Config.DEFAULT_SPLIT_STRING, -1);
		String[] serverlist = {"127.0.0.1:11211"};
		pool = SockIOPool.getInstance();
		pool.setServers(serverlist);

//		pool.setFailover(config.getConfigValueBoolean("MEMCACHED_FAILOVER"));
//		pool.setNagle(config.getConfigValueBoolean("MEMCACHED_NAGLE"));
//		pool.setAliveCheck(config.getConfigValueBoolean("MEMCACHED_ALIVE_CHECK"));
//
//		pool.setInitConn(config.getConfigValueInteger("MEMCACHED_INIT_CONN"));
//		pool.setMinConn(config.getConfigValueInteger("MEMCACHED_MIN_CONN"));
//		pool.setMaxConn(config.getConfigValueInteger("MEMCACHED_MAX_CONN"));
//		pool.setMaintSleep(config.getConfigValueInteger("MEMCACHED_MAINT_SLEEP"));
//		pool.setSocketTO(config.getConfigValueInteger("MEMCACHED_SOCKET_TO"));
		
		pool.setFailover(true);
		pool.setNagle(false);
		pool.setAliveCheck(true);

		pool.setInitConn(5);
		pool.setMinConn(2);
		pool.setMaxConn(250);
		pool.setMaintSleep(30);
		pool.setSocketTO(500);
		
		/* pool.setSocketConnectTO(config.getConfigValueInteger("MEMCACHED_SOCKET_TO")); */
		pool.initialize();

		m = new MemCachedClient[maxIndex];
		for (int i = 0; i < maxIndex; i++) {
			MemCachedClient c = new MemCachedClient();
			// c.setCompressEnable( false );
			// c.setCompressThreshold(0);
			m[i] = c;
		}
	}

	/**
	 * Clean up.
	 */
	@PreDestroy
	public void cleanUp() {
		if (pool == null) {
			return;
		}
		pool.shutDown();
	}

	/*
	 * public static MemcachedUtilNew getInstance() { if (instance == null) { synchronized (MemcachedUtilNew.class) { if
	 * (instance == null) { instance = new MemcachedUtilNew(); } } } return instance; }
	 */

	/**
	 * Sets the.
	 *
	 * @param key
	 *            the key
	 * @param o
	 *            the o
	 */
	public void set(String key, final Object o) {
		getCache().set(key, o);
	}

	/**
	 * Sets the.
	 *
	 * @param key
	 *            the key
	 * @param o
	 *            the o
	 * @param expirySec
	 *            the expiry sec
	 */
	public void set(String key, final Object o, int expirySec) {
		getCache().set(key, o, DateUtils.addSeconds(new Date(), expirySec));
	}

	/**
	 * Gets the.
	 *
	 * @param key
	 *            the key
	 * @return the object
	 */
	public Object get(String key) {
		Object o = getCache().get(key);
		return o;
	}

	/**
	 * Gets the multi array.
	 *
	 * @param keys
	 *            the keys
	 * @return the multi array
	 */
	public Object[] getMultiArray(String[] keys) {
		Object[] o = getCache().getMultiArray(keys);
		return o;
	}

	/**
	 * Gets the multi.
	 *
	 * @param keys
	 *            the keys
	 * @return the multi
	 */
	public Map<String, Object> getMulti(String[] keys) {
		Map<String, Object> o = getCache().getMulti(keys);
		return o;
	}

	/**
	 * Delete.
	 *
	 * @param key
	 *            the key
	 * @return the object
	 */
	public Object delete(String key) {
		return getCache().delete(key);
	}

	/**
	 * Gets the cache.
	 *
	 * @return the cache
	 */
	public MemCachedClient getCache() {
		return m[getNextIndex()];
	}

	/**
	 * Gets the next index.
	 *
	 * @return the next index
	 */
	private synchronized int getNextIndex() {
		curIndex++;
		if (curIndex >= maxIndex) {
			curIndex = minIndex;
		}

		return curIndex;
	}
}
