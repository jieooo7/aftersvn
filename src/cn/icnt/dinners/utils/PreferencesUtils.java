package cn.icnt.dinners.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtils
{
	/**
	 * 公共key类
	 * @author Administrator
	 *
	 */
	
	/**
	 * cn.icnt.dinners.utils.Keys
	 * @author Andrew Lee <br/>
	 * create at 2014年7月17日 下午7:39:32
	 */
	public static class Keys
	{
		/**
		 * 版本号
		 */
		public static final String VERSION = "version";
		/**
		 * 手机号码
		 */
		public static final String PHONE = "phone";
		/**
		 * 手机唯一编号
		 */
		public static final String NO = "no";
		/**
		 * 用户ID
		 */
		public static final String UID = "uid";
		/**
		 * 用户名
		 */
		public static final String ACCOUNT = "account";
		/**
		 * 用户密码
		 */
		public static final String PWD = "pwd";
		
		/**
		 * sharedPreference 文件名
		 */
		public  static final String USERINFO="userInfo";
		/**
		 * 昵称
		 */
		public  static final String NICKNAME="nickName";
		


	}

/**
 * 存储布尔值
 * @param mContext
 * @param key
 * @param value
 */
	public static void putBooleanToSPMap(Context mContext, String key, boolean value)
	{
		SharedPreferences preferences = mContext.getSharedPreferences(Keys.USERINFO, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

/**
 * 获取布尔值
 * @param mContext
 * @param key
 * @return
 */
	public static Boolean getBooleanFromSPMap(Context mContext, String key)
	{
		SharedPreferences ferences = mContext.getSharedPreferences(Keys.USERINFO,  Context.MODE_PRIVATE);
		boolean value = ferences.getBoolean(key, false);
		return value;
	}

/**
 * 存储String
 * @param mContext
 * @param key  
 * @param value  
 */
	public static void putValueToSPMap(Context mContext, String key, String value)
	{
		SharedPreferences preferences = mContext.getSharedPreferences(Keys.USERINFO, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * 获取String
	 * @param mContext
	 * @param key  
	 * @return value
	 */
	public static String getValueFromSPMap(Context mContext, String key)
	{
		if (null != mContext)
		{
			SharedPreferences ferences = mContext.getSharedPreferences(Keys.USERINFO,  Context.MODE_PRIVATE);
			String value = ferences.getString(key, "");
			return value;
		} else
		{
			return null;
		}
	}

/**
 * 获取String
 * @param mContext
 * @param key
 * @param defaults 无值时取defaults
 * @return
 */
	
	public static String getValueFromSPMap(Context mContext, String key,String defaults )
	{
		if (null != mContext)
		{
			SharedPreferences ferences = mContext.getSharedPreferences(Keys.USERINFO,  Context.MODE_PRIVATE);
			String value = ferences.getString(key, defaults);
			return value;
		} else
		{
			return null;
		}
	}
	/**
	 * 清除全部
	 * @param mContext
	 */
	public static void clearSPMap(Context mContext)
	{
		SharedPreferences preferences = mContext.getSharedPreferences(Keys.USERINFO, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.clear();
		edit.commit();
	}

	

	/**
	 * 指定key清除
	 * 
	 * @param mContext
	 * @param key
	 */
	public static void clearSpMap(Context mContext, String key)
	{
		putValueToSPMap(mContext, key, "");
	}

}
