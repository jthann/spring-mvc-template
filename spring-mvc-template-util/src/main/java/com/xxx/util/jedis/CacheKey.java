package com.xxx.util.jedis;

public class CacheKey {

	private static String PREFIX = "xxx_____";
	
	
	/**
	 * @param moduleId
	 * @param pageId
	 * @param dateStr
	 * @return
	 */
	public static String getCacheKey(String moduleId, Long pageId, String dateStr){
		return PREFIX  + moduleId + "_" + pageId+"_"+dateStr;
	}
	
}
