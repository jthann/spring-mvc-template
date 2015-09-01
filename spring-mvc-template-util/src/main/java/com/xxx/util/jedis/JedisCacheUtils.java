package com.xxx.util.jedis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * Redis客户端，但机房读，双机房写和删除
 * @author hechangrong
 */

public class JedisCacheUtils {

	private final static Log logger = LogFactory.getLog(JedisCacheUtils.class);
	
	public static final String ENCODING="UTF-8";
	

	public void setObject(String key,int exp, Object o){
		byte[] keyByte=this.getBytes(key);
	}

	
	/**
	 * 获取对象，单机房读
	 * @param key
	 * @return
	 */
	public Object getObject(String key){
		byte[] bytes = null;


		if(bytes!=null && bytes.length!=0){
			return unserialize(bytes);
		}
		return null;
	}



	  /**
	   * 获取String的byte[]数组
	   * @param string
	   * @return
	   */
	  private byte[] getBytes(String string){
		  try {
			  if(string!=null){
				  return string.getBytes(ENCODING);
			  }
		} catch (UnsupportedEncodingException e) {}
			return null;
	  }


	  /**
	     * 序列化
	     *
	     * @param object
	     * @return
		 * @throws java.io.IOException
	     */
	    private static byte[] serialize(Object object) {
	        ObjectOutputStream oos = null;
	        ByteArrayOutputStream baos = null;
	        try {
	            // 序列化
	            baos = new ByteArrayOutputStream();
	            oos = new ObjectOutputStream(baos);
	            oos.writeObject(object);
	            oos.flush();
	            byte[] bytes = baos.toByteArray();
	            baos.flush();
	            return bytes;
	        } catch (Exception e) {
	        	logger.error("对象序列化失败！object="+object.toString(),e);
	        }finally{
	        	try {
					oos.close();
					baos.close();
				} catch (IOException e) {
					logger.error("对象序列化异常！object="+object.toString(),e);
				}
	        }
	        return null;
	    }

	    /**
	     * 反序列化
	     * 
	     * @param bytes
	     * @return
	     */
	    private static Object unserialize(byte[] bytes) {
	        ByteArrayInputStream bais = null;
	        try {
	            // 反序列化
	            bais = new ByteArrayInputStream(bytes);
	            ObjectInputStream ois = new ObjectInputStream(bais);
	            Object obj = ois.readObject();
	            ois.close();
	            bais.close();
	            return obj;
	        } catch (Exception e) {
	        	logger.error("对象反序列化失败！",e);
	        }
	        return null;
	    }
		
}
