/*
 * GsonTools.java
 * classes : com.cloud.app.http.GsonTools
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��5�� ����2:02:32
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.entity.StringEntity;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;

/**
 * gson ������������ ���ݲ�ͬ�ķ�װ���ö�Ӧ�ķ��� com.cloud.app.http.GsonTools
 * 
 * @author Andrew Lee <br/>
 *         create at 2014��6��5�� ����2:02:32
 */
public class GsonTools {

	private static Gson gson;

	public GsonTools() {

	}

	/*
	 * �鿴Gson api���е�fromJson(String json, Class<T> classOfT)���� public <T> T
	 * fromJson(String json, Class<T> classOfT)
	 * ��������һ�������Json����,ʹ�÷�����
	 * ƿ��Խ�JSON�ַ����а��������ݸ�ֵ��ָ�����ࡣ��ߵ�T
	 * ��ʾ����ͨ������,Ҳ���ǿ��Ա�ʾ��������͡� ����˵���� json :
	 * ָ�������������JSON�ַ���Դ,����ת����Java���� classOfT �� ���� T ��Class����
	 */
	public static <T> T getPerson(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
		}
		return t;
	}

	/*
	 * List ����ʹ�õ�Gson�е� public <T> T fromJson(String json, Type typeOfT)
	 * ����
	 * �����Ҫȡ��List<T>�в�ͬ�Ķ�����ͨ��ʵ�ַ�ʽ������һ����org.Json��������JSON�ķ�ʽһ
	 * ��(�����Ķ���һ������)�� ��������ͨ�� Gson�е�
	 * TypeToken���Ǽ����������typeOfT�
	 * ��÷���ͨ��������ư�T����Ķ�������Ե�ֵӳ�������Ȼ�
	 * �ͨ����json�ַ���ȡ�õ�ֵ��ֵ��ȥ�Ϳ����ˡ�
	 * getType()����˼���Ǳ�ʾ��jsonString���ַ���
	 * ��������֮���װ��List�����У�Ȼ��ֱ��T����ȡ�����ͽ��临�ơ�
	 */

	public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	public static List<String> getList(String jsonString) {
		List<String> list = new ArrayList<String>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
			}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	public static Map<String, Object> getMaps(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			map = gson.fromJson(jsonString,
					new TypeToken<Map<String, Object>>() {
					}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	public static List<Map<String, String>> getListMaps(String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString,
					new TypeToken<List<Map<String, String>>>() {
					}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	public static RequestParams GetParams(Map maps) {
		RequestParams params = new RequestParams();
		gson = new Gson();
		String json = gson.toJson(maps);
		StringEntity s = null;
		try {
			s = new StringEntity(json, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params.setContentType("application/json");
		params.setBodyEntity(s);
	
		return params;
	}
}
