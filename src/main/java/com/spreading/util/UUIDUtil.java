/**
 * 
 */
package com.spreading.util;

import java.util.UUID;

/**
 * UUID工具类
 * @author mac
 *
 */
public class UUIDUtil {
	
	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		String uuidString = UUID.randomUUID().toString().replaceAll("-","");
		return uuidString;
	}
}
