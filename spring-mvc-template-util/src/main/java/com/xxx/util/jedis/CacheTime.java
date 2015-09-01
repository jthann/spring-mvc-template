/**
 * 
 */
package com.xxx.util.jedis;

/**
 * 缓存时间定义
 *
 */
public interface CacheTime {

	/** 缓存时间1个月 */
	int ONE_MONTH = 2592000;
	
	/** 缓存时间1天 */
	int ONE_DAY = 86400;
	
	/** 缓存时间3天 */
	int THREE_DAYS = 3 * ONE_DAY;
	
	/** 缓存时间5天 */
	int FIVE_DAYS = 5 * ONE_DAY;
	
	/** 缓存时间5分钟 */
	int FIVE_MINUTES = 300;
	
	/** 缓存时间1小时 */
	int ONE_HOUR = 3600;
	
	/** 缓存时间30分钟 */
	int HALF_AN_HOUR = 1800;
}
