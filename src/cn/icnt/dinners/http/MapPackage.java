/*
 * MapPackage.java
 * classes : com.cloud.app.http.MapPackage
 * author Andrew Lee
 * V 1.0.0
 * create at  2014年7月1日 下午3:34:09
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.http;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.utils.MD5;

import com.google.gson.Gson;


/**
 * com.cloud.app.http.MapPackage
 * @author Andrew Lee <br/>
 * create at 2014年7月1日 下午3:34:09
 */
public class MapPackage {
	
//	加密key
	private final String CODEKEY = "1234";
//	os类别
	private final String OS = "1";
//	app版本号
	private static final String VERSION="1.1.2";
//	域名地址
	public static final String PATH = "http://115.29.13.164/";
	private Map<String, Object> headmap = new HashMap<String, Object>();
	private Map<String, Object> paramap = new HashMap<String, Object>();
	private Map<String, Object> resmap = new HashMap<String, Object>();
//	返回结果
	private Map<String, Object> backResult=new HashMap<String, Object>();
	private String path = "";
	private String uid="-1";
	
	
	
	public Map<String, Object> getResult(){
		return this.backResult;
	}
	
	public String getUid(){
		return this.uid;
	}
//	uid设置，默认为-1（游客）
	public void setUid(Context context){
//		shered文件中获取
		SharedPreferences sp = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
		this.uid=sp.getString("uid", "-1");
		DebugUtil.i("shared","999999"+sp.getString("uid", "-1"));
	}
	public String getpath() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA);
		return PATH + this.path+format.format(date);
	}

	/**
	 *@param path 用户设置的路径
	 */
	public void setPath(String path) {

		this.path = path;
	}

	public String getHead(String key) {
		return (String) this.headmap.get(key);

	}

	/**
	 *@param context 头信息拼装
	 */
	public void setHead(Context context) {
//		手机唯一标示信息
		TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//		用户id
		setUid(context);
		this.headmap.put("uid", this.getUid());
		this.headmap.put("no", telephonyManager.getDeviceId());
		this.headmap.put("os", OS);
		this.headmap.put("version",VERSION);
		this.headmap.put(
				"key",
				MD5.getMD5(getHead("uid") + CODEKEY + getHead("no")
						+ getHead("os") + getHead("version")));

	}

	public String getPara(String key) {
		return (String) this.paramap.get(key);

	}

	/**
	 * para信息设置
	 *@param key 
	 *@param val
	 */
	public void setPara(String key, String val) {

		this.paramap.put(key, val);
		

	}

	public String getRes(String key) {
		return (String) this.resmap.get(key);

	}

	/**
	 * result信息拼装
	 *@param key
	 *@param val
	 */
	public void setRes(String key, String val) {

		this.resmap.put(key, val);

	}



	/**
	 * 发送请求并返回信息
	 * @return 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public void send() throws InterruptedException, ExecutionException {
		Map<String, Object> map = new HashMap<String, Object>();
//		加入初始化设置
		this.headmap.put("", "");
		this.paramap.put("","");
		this.resmap.put("","");
		map.put("head", this.headmap);
		map.put("para", this.paramap);
		map.put("result", this.resmap);
		Gson gson = new Gson();
//		发送请求
		HttpSendRecv task=new HttpSendRecv(this.getpath(),gson.toJson(map));
//		返回信息
		String backResult =task.execute().get();
		DebugUtil.i("all", backResult);
		this.backResult=GsonTools.getMaps(backResult.substring(backResult.indexOf("{")));

	}

	/**
	 *@return  返回头信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getBackHead() {
		try{
		return (Map<String, String>) this.backResult.get("head");
		}catch(Exception e){
			return null;
		}
	}

	/**
	 *@return  返回para信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getBackPara() {
		try{
		return (Map<String, String>) this.backResult.get("para");
		}catch(Exception e){
		return null;
		}
	}

	/**
	 *@return 返回result信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getBackResult() {
		try{
//			list map形式的
		return (List<Map<String, String>>) this.backResult.get("result");
		}catch(Exception e){
			return null;
		}
	}

}
