package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.beans.UserLoginBean;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.PreferencesUtils;
import cn.icnt.dinners.utils.ToastUtil;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class LoginActivity extends Activity {
	@ViewInject(R.id.title_left_btn)
	private RelativeLayout back; // 返回按钮
	@ViewInject(R.id.edit_phone_bg)
	private RelativeLayout edit_phone_bg; // 用户名输入框背景

	@ViewInject(R.id.edit_password_bg)
	private RelativeLayout edit_password_bg; // 密码输入框背景
	@ViewInject(R.id.user_name)
	private EditText user_name;// 用户名输入框
	@ViewInject(R.id.edit_password)
	private EditText edit_password;// 密码输入框
	@ViewInject(R.id.user_regiest)
	private TextView user_regiest; // 注册
	@ViewInject(R.id.forget_password)
	private TextView forget_password;// 忘记密码
	@ViewInject(R.id.login)
	private TextView login; // 登陆
	private List<Map<String, String>> list;

	private String userName;

	private String userPassword;
	private Intent intent;
	private InputMethodManager manager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		initview();
	}

	private void initview() {
		ViewUtils.inject(this);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		changeBackGround();
	}

	private void changeBackGround() {
		user_name.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setViewVisible(hasFocus || user_name.getText().length() > 0,
						edit_phone_bg, user_name, R.string.login_user_name,
						R.drawable.mailbox);
			}
		});
		edit_password.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setViewVisible(
						hasFocus || edit_password.getText().length() > 0,
						edit_password_bg, edit_password,
						R.string.login_user_password, R.drawable.password);
			}
		});
	}

	@OnClick({ R.id.title_left_btn, R.id.user_regiest, R.id.forget_password,
			R.id.login })
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			intent = new Intent();
			intent.setClass(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.user_regiest:
			finish();
			intent = new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.forget_password:
			String username = user_name.getText().toString().trim();
			if (StringUtils.isEmpty(username)) {
				ToastUtil.show(this, "请输入用户名");
			}else {
				intent = new Intent();
				intent.setClass(LoginActivity.this, FindPassWordActivity.class);
				intent.putExtra("user_name", username);
				startActivity(intent);
			}
			break;
		case R.id.login:
			userLogin();

			break;
		}
	}

	private void userLogin() {
		userName = user_name.getText().toString().trim();
		userPassword = edit_password.getText().toString().trim();

//		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userPassword)) {
//			Toast.makeText(this, "请正确输入！", 0).show();
//		} else if (StringUtils.isEmpty(userName)) {
//
//			Toast.makeText(this, "请输入用户名哦！亲", 0).show();
//		} else if (StringUtils.isEmpty(userPassword)) {
//			Toast.makeText(this, "请输入密码哦！亲", 0).show();
//		} else {
//			sendLogin(userName, userPassword);
//			
//		}

		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userPassword)) {
			Toast.makeText(this, "请正确输入！", 0).show();
		} else if (StringUtils.isEmpty(userName)) {

			Toast.makeText(this, "请输入用户名哦！亲", 0).show();
		} else if (StringUtils.isEmpty(userPassword)) {
			Toast.makeText(this, "请输入密码哦！亲", 0).show();
		} else {
			// Toast.makeText(this, userName + "##" + userPassword, 0).show();
			sendLogin1(userName, userPassword);

		}

	}

	// private void sendLogin(String userName2, String userPassword2) {
	// params = new RequestParams();
	// params.addBodyParameter("userName2", userName2);
	// params.addBodyParameter("userPassword2", userPassword2);
	// params.addQueryStringParameter("qmsg", "你好");
	// params.addBodyParameter("msg", "测试");
	// HttpUtils http = new HttpUtils();
	// http.send(HttpRequest.HttpMethod.POST,
	// "http://115.29.13.164/login.do?t=", params,
	// new RequestCallBack<String>() {
	//
	// @Override
	// public void onFailure(HttpException arg0, String arg1) {
	//
	// }
	//
	// @Override
	// public void onSuccess(ResponseInfo<String> arg0) {
	//
	// }});
	// }

	private void sendLogin1(String name, String password) {
		MapPackage mp = new MapPackage();
		mp.setPath("login.do?");
		mp.setHead(this);
		mp.setPara("account", name);
		mp.setPara("pwd", password);
		Map<String, Object> maps = mp.getMap();
		RequestParams params = GsonTools.GetParams(maps);
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				"http://115.29.13.164/login.do?t=", params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Gson gson = new Gson();
						UserLoginBean userInfo = gson.fromJson(
								responseInfo.result, UserLoginBean.class);
						action(userInfo);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});

	}

	private void sendLogin(String userName2, String userPassword2) {
		MapPackage mp = new MapPackage();
		mp.setPath("login.do?");
		mp.setHead(this);
		mp.setPara("account", userName2);
		mp.setPara("pwd", userPassword2);
		Map<String, Object> maps = mp.getMap();
		// Log.i("LoginActivity", mp.toString());
		try {
			UserLoginBean userInfo = mp.send(UserLoginBean.class);
			// List<Map<String, String>> backResult = mp.getBackResult();
			// UserLoginBean userInfo =
			// GsonUtils.Json2Bean(mp.getBackResult().toString(),
			// UserLoginBean.class);

			// Log.i("LoginActivity", userInfo.toString());

			Log.i("LoginActivity", userInfo.toString());
		} catch (Exception e) {
			// TODO: handle exception
			if (HttpSendRecv.netStat)
				Toast.makeText(getApplicationContext(), "网络错误，请重试",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "出错了0_0",
						Toast.LENGTH_LONG).show();
		} finally {
		}
	}

	private void setViewVisible(boolean need_visible, RelativeLayout tv,
			EditText et, int id, int bg) {
		if (need_visible) {
			tv.setBackgroundResource(bg);
			et.setHint("");
		} else {
			tv.setBackgroundResource(R.drawable.loginedit);
			et.setHint(getResources().getString(id));
		}

	}

	private void action(UserLoginBean userInfo) {
		if (userInfo == null) {
		} else if ("10000".equals(userInfo.head.code)) {
			PreferencesUtils.putValueToSPMap(this, PreferencesUtils.Keys.UID,
					userInfo.para.user_id);
			PreferencesUtils.putValueToSPMap(this,
					PreferencesUtils.Keys.ACCOUNT, userInfo.para.email);
			PreferencesUtils.putValueToSPMap(this, PreferencesUtils.Keys.PHONE,
					userInfo.para.phone);
			this.finish();

		} else {
			Toast.makeText(this, userInfo.head.msg, 0).show();
			edit_password.setText("");
		}
	}
	@Override  
	 public boolean onTouchEvent(MotionEvent event) {  
	  // TODO Auto-generated method stub  
	  if(event.getAction() == MotionEvent.ACTION_DOWN){  
	     if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){  
	       manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
	     }  
	  }  
	  return super.onTouchEvent(event);  
	 }  
}
