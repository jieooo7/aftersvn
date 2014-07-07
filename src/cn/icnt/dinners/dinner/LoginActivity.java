package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		initview();
	}

	private TelephonyManager telephonyManager;

	private void initview() {
		ViewUtils.inject(this);
		// back = (RelativeLayout) findViewById(R.id.title_left_btn);
		// edit_phone_bg = (RelativeLayout) findViewById(R.id.edit_phone_bg);
		// edit_password_bg = (RelativeLayout)
		// findViewById(R.id.edit_password_bg);
		//
		// user_name = (EditText) findViewById(R.id.user_name);
		// edit_password = (EditText) findViewById(R.id.edit_password);
		//
		// user_regiest = (TextView) findViewById(R.id.user_regiest);
		// forget_password = (TextView) findViewById(R.id.forget_password);
		// login = (TextView) findViewById(R.id.login);

		// back.setOnClickListener(this);
		// user_regiest.setOnClickListener(this);
		// forget_password.setOnClickListener(this);
		// login.setOnClickListener(this);

		telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		phoneNumber = telephonyManager.getLine1Number();

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
			break;
		case R.id.user_regiest:

			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.forget_password:
			Toast.makeText(this, "忘记密码", 0).show();
			break;
		case R.id.login:

			userLogin();
			break;
		}
	}

	private void userLogin() {
		userName = user_name.getText().toString().trim();
		userPassword = edit_password.getText().toString().trim();
		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userPassword)) {
			Toast.makeText(this, "请正确输入！", 0).show();
		} else if (StringUtils.isEmpty(userName)) {

			Toast.makeText(this, "请输入用户名哦！亲", 0).show();
		} else if (StringUtils.isEmpty(userPassword)) {
			Toast.makeText(this, "请输入密码哦！亲", 0).show();
		} else {
			sendLogin(userName, userPassword);

		}
	}

	// 加密key
	private final String CODEKEY = "1234";
	// os类别
	private final String OS = "1";
	// app版本号
	private static final String VERSION = "1.1.2";

	private String phoneNumber;

	private void sendLogin(String name, String password) {
		RequestParams params = new RequestParams();
		// params.addHeader("name", "value");
		// params.addQueryStringParameter("name", "value");
		params.addBodyParameter("uid", "-1");
		params.addBodyParameter("no", "123444444");
		params.addBodyParameter("os", "1");
		params.addBodyParameter("version", "1.0");
		params.addBodyParameter("key", "1.0");
		long currentTimeMillis = System.currentTimeMillis();
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				"http://115.29.13.164/login.do?t=" + currentTimeMillis, params,
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
						Log.i("LoginActivity", responseInfo.result);
						Gson gson = new Gson();
						// Head fromJson = gson.fromJson(responseInfo.result,
						// Head.class);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
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

}
