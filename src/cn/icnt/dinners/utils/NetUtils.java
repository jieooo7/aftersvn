package cn.icnt.dinners.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import cn.icnt.dinners.beans.HttpApi;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class NetUtils {
	private static NetUtils instance;
	private HttpUtils http = new HttpUtils();
	private Context context;
	private Map<String, Object> headmap = new HashMap<String, Object>();
	private Map<String, Object> paramap = new HashMap<String, Object>();
	private Map<String, Object> resmap = new HashMap<String, Object>();

	private NetUtils() {
	}

	public NetUtils getNetUtils() {
		if (instance == null) {
			instance = new NetUtils();
		}
		return instance;
	}

	public String getResult(Context context, String path, String head) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		RequestParams params = new RequestParams();
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("head", this.headmap);
		 map.put("para", this.paramap);
		 map.put("result", this.resmap);
		params.addQueryStringParameter("head", HttpApi.getLoginAPI().getHead(context));
		
		// Gson gson = new Gson();
		// // 发送请求"http://115.29.13.164/login.do?t=309343089034"
		// HttpSendRecv task = new HttpSendRecv(this.getpath(),
		// gson.toJson(map));
		http.send(HttpRequest.HttpMethod.POST, path, params,
				new RequestCallBack<String>() {


					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						if (isUploading) {
						} else {
						}
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});

		return null;
	}

//	public void setHead(Context context) {
//		this.headmap.put("uid", PreferencesUtils.getValueFromSPMap(context,
//				PreferencesUtils.Keys.UID, "-1"));
//		this.headmap.put("no", PreferencesUtils.getValueFromSPMap(context,
//				PreferencesUtils.Keys.NO));
//		this.headmap.put("os", "1");
//		this.headmap.put("version", PreferencesUtils.getValueFromSPMap(context,
//				PreferencesUtils.Keys.VERSION));
//		this.headmap.put(
//				"key",
//				MD5.getMD5(getHead("uid") + Container.CODEKEY + getHead("no")
//						+ getHead("os") + getHead("version")));
//
//	}
}
