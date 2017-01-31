/*
 *
 */
package com.dinPlat.svc.cache;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.dinPlat.svc.code.Config.P;
import com.dinPlat.svc.log.DropLog;
import com.dinPlat.svc.property.PropertyE;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * The Class MemcachedUtil.
 *
 * @author skplanet
 */
@Repository
public class MemcachedE {

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
		String[] serverlist = PropertyE.getStringValue(P.MEMCASHED, "MEMCACHED_SERVERLIST").split(";");
		pool = SockIOPool.getInstance();
		pool.setServers(serverlist);

		pool.setFailover(PropertyE.getbooleanValue(P.MEMCASHED, "MEMCACHED_FAILOVER"));
		pool.setNagle(PropertyE.getbooleanValue(P.MEMCASHED, "MEMCACHED_NAGLE"));
		pool.setAliveCheck(PropertyE.getbooleanValue(P.MEMCASHED, "MEMCACHED_ALIVE_CHECK"));

		pool.setInitConn(PropertyE.getintValue(P.MEMCASHED, "MEMCACHED_INIT_CONN"));
		pool.setMinConn(PropertyE.getintValue(P.MEMCASHED, "MEMCACHED_MIN_CONN"));
		pool.setMaxConn(PropertyE.getintValue(P.MEMCASHED, "MEMCACHED_MAX_CONN"));
		pool.setMaintSleep(PropertyE.getlongValue(P.MEMCASHED, "MEMCACHED_MAINT_SLEEP"));
		pool.setSocketTO(PropertyE.getintValue(P.MEMCASHED, "MEMCACHED_SOCKET_TO"));
		
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
