package cn.icnt.dinners.beans;

import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.MD5;
import cn.icnt.dinners.utils.PreferencesUtils;
import android.content.Context;


public class HttpApi {

	private String uid;
	private String no;
	private String os = "1";
	private String version;
	private String key;
	private String account;
	private String pwd;
	private String result;
	private static HttpApi instance;
	
	private HttpApi(){}
	/**
	 * 单一实例
	 */
	public static HttpApi getLoginAPI(){
		if(instance==null){
			instance=new HttpApi();
		}
		return instance;
	}
//	uid	
//	no	
//	os	
//	version	
//	key	
//	account	
//	pwd	
//	""	

	public String getHead(Context context ){
		uid=PreferencesUtils.getValueFromSPMap(context, PreferencesUtils.Keys.UID, "-1");
		no=PreferencesUtils.getValueFromSPMap(context, PreferencesUtils.Keys.NO);
		version="1.1.2";
		key=MD5.getMD5(uid+no+Container.CODEKEY+version);
		this.account = account;
		this.pwd=pwd;
		return "{uid:"+uid+",no:"+no+",os:"+os+",version:"+ version +"key:"+key+"}";
		
	}
	public String getLoginParas(String account ,String pwd){
		return "para:{account: "+account+ ",pwd:"+pwd+"}";
	}
	public String getLoginResult(){
		return "";
	}
	
	/**
	 * 注册
	 */
	public String getRegiestParas(String email ,String phone,String pwd){
		return "para:{email: "+email+ ",phone:"+phone+",pwd:"+pwd+"}";
	}
	public String getRegiestResult(){
		return "";
	}
}
