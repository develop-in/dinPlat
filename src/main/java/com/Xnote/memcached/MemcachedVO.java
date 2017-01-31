/*
 *
 */
package com.Xnote.memcached;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.SerializationUtils;

/**
 * The Class MemcachedVO.
 */
public class MemcachedVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7027845977271534151L;

	/** The is not empty. */
	private boolean isNotEmpty = false;

	/** The is byte array cached. */
	private boolean isByteArrayCached = false;

	/** The time stamp. */
	private long timeStamp;

	/** The cached data. */
	private Serializable cachedData;

	/** The byte cached data. */
	private byte[] byteCachedData;

	/**
	 * The Constructor.
	 *
	 * @param cachedData
	 *            the cached data
	 */
	public MemcachedVO(Serializable cachedData) {
		setCachedData(cachedData);
		setTimeStamp(System.currentTimeMillis());
	}

	/**
	 * The Constructor.
	 *
	 * @param cachedData
	 *            the cached data
	 */
	public MemcachedVO(List<?> cachedData) {
		setCachedData(SerializationUtils.serialize(cachedData));
		setTimeStamp(System.currentTimeMillis());
	}

	/**
	 * Checks if is not empty.
	 *
	 * @return true, if checks if is not empty
	 */
	public boolean isNotEmpty() {
		return isNotEmpty;
	}

	/**
	 * Sets the not empty.
	 *
	 * @param isNotEmpty
	 *            the not empty
	 */
	public void setNotEmpty(boolean isNotEmpty) {
		this.isNotEmpty = isNotEmpty;
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp
	 *            the time stamp
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * Gets the cached data.
	 *
	 * @return the cached data
	 */
	public Object getCachedData() {
		if (this.isByteArrayCached) {
			return SerializationUtils.deserialize(this.byteCachedData);
		}
		return this.cachedData;
	}

	/**
	 * Sets the cached data.
	 *
	 * @param cachedData
	 *            the cached data
	 */
	public void setCachedData(Serializable cachedData) {
		if (cachedData != null) {
			this.isNotEmpty = true;
			this.isByteArrayCached = false;
			this.cachedData = cachedData;
		}
	}

	/**
	 * Sets the cached data.
	 *
	 * @param byteCachedData
	 *            the cached data
	 */
	public void setCachedData(byte[] byteCachedData) {
		if (byteCachedData != null) {
			this.isNotEmpty = true;
			this.isByteArrayCached = true;
			this.byteCachedData = byteCachedData;
		}
	}
}
