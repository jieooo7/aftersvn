package cn.icnt.dinners.utils;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


public class NetUtils {

	private HttpUtils http = new HttpUtils();

	public String getResult() {
		RequestParams params = new RequestParams();
		params.addHeader("content-type", "application/json");
		params.addBodyParameter("uid", "-1");
		params.addBodyParameter("no", "123444444");
		params.addBodyParameter("os", "1");
		params.addBodyParameter("version", "1.0");
		params.addBodyParameter("key", "1.0");
		
		http.send(HttpRequest.HttpMethod.POST,
		    "http://115.29.13.164/login.do?t=309343089034",
		    params,
		    new RequestCallBack<String>() {

		        @Override
		        public void onStart() {
		        }

		        @Override
		        public void onLoading(long total, long current, boolean isUploading) {
		            if (isUploading) {
		            } else {
		            }
		        }

		        @Override
		        public void onSuccess(ResponseInfo<String> responseInfo) {
		        	Log.i("LoginActivity", responseInfo.result);
		        }

		        @Override
		        public void onFailure(HttpException error, String msg) {
		        }
		});
		
		return null;
	}


	
	
}
