package cn.icnt.dinners.dinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import m.framework.utils.UIHandler;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.beans.UserLoginBean;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Constants;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.PreferencesUtils;
import cn.icnt.dinners.utils.ToastUtil;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

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

public class LoginActivity extends Activity implements PlatformActionListener {
	@ViewInject(R.id.title_left_btn)
	private RelativeLayout back; // 返回按钮
	// @ViewInject(R.id.edit_phone_bg)
	// private RelativeLayout edit_phone_bg; // 用户名输入框背景
	// @ViewInject(R.id.edit_password_bg)
	// private RelativeLayout edit_password_bg; // 密码输入框背景
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
	@ViewInject(R.id.login_phone_editing)
	private FrameLayout login_phone_editing; // 用户输入状态
	@ViewInject(R.id.login_pwd_editing)
	private FrameLayout login_pwd_editing; // 密码输入状态
	@ViewInject(R.id.sina)
	private LinearLayout sina; // 新浪登陆
	@ViewInject(R.id.tencent)
	private LinearLayout tencent; // qq登录
	// private List<Map<String, String>> list;
	private String userName;
	private String userPassword;
	private Intent intent;
	private InputMethodManager manager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		ShareSDK.initSDK(this);
		initview();
	}

	private void initview() {
		// weiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// animation = AnimationUtils.loadAnimation(this,R.anim.user_login_v);
		// AnimationUtils.loadAnimation(this,R.anim.user_login_inv);
		ViewUtils.inject(this);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		changeBackGround();
	}

	private void changeBackGround() {
		user_name.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setViewVisible(hasFocus || user_name.getText().length() > 0,
						login_phone_editing, user_name,
						R.string.login_user_name);
			}
		});
		edit_password.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setViewVisible(
						hasFocus || edit_password.getText().length() > 0,
						login_pwd_editing, edit_password,
						R.string.login_user_password);
			}
		});
	}

	@OnClick({ R.id.title_left_btn, R.id.user_regiest, R.id.forget_password,
			R.id.login, R.id.sina, R.id.tencent })
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
			} else {
				intent = new Intent();
				intent.setClass(LoginActivity.this, FindPassWordActivity.class);
				intent.putExtra("user_name", username);
				startActivity(intent);
			}
			break;
		case R.id.login:
			userLogin();
			break;
		case R.id.sina:
			Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
			weibo.removeAccount();// 清除以前缓存在本地的账户信息，如果注释掉此行，将不用授权，直接分享
			authorize(weibo);
			// authorize(new SinaWeibo(this));
			// ssoHandler = new SsoHandler(LoginActivity.this, weiboAuth);
			// ssoHandler.authorize(new AuthListener());
			break;
		case R.id.tencent:
			// authorize(new QZone(this));
			// userLogin();

			break;
		}
	}

	/********************************** 第三方登陆 ***********************************************************************************/
	// case R.id.sina:
	// Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
	// weibo.removeAccount();//清除以前缓存在本地的账户信息，如果注释掉此行，将不用授权，直接分享
	// authorize(weibo);
	// break;

	private void authorize(Platform plat) {
		if (plat.isValid()) {
			String userId = plat.getDb().getUserId();
			if (StringUtils.isEmpty(userId)) {
				Toast.makeText(LoginActivity.this, "nUserId=" + userId, 0)
						.show();
				// ToastUtil.show(
				// LoginActivity.this,
				// "nUserId="+ userId);
			}
		}
		plat.setPlatformActionListener(this);
		plat.SSOSetting(false);
		plat.showUser(null);
	}

	@Override
	protected void onDestroy() {
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}

	@Override
	public void onCancel(Platform arg0, int arg1) {

	}

	@Override
	public void onComplete(Platform palm, int arg1, HashMap<String, Object> arg2) {
//		PlatformDb db = palm.getDb();
//		Toast.makeText(
//				LoginActivity.this,
//				"nUserId=" + "userName=" + db.getUserName() + "\r\nUserId="
//						+ db.getUserId() + "\r\nuserToken" + db.getToken()
//						+ "\r\nUserIcon" + db.getUserIcon(), 0).show();
		Log.e("D", palm.getDb().getToken() + "##getName::" + palm.getName()
				+ "##getUserId::" + palm.getDb().getUserId());
		Log.e("D", "ICON##::" + palm.getDb().getUserIcon() + "##getExpiresIn::"
				+ palm.getDb().getExpiresIn() + "##getUserName::"
				+ palm.getDb().getUserName());
		PreferencesUtils.putValueToSPMap(this,
				PreferencesUtils.Keys.USER_PORTRAIT, palm.getDb().getUserIcon()
						.toString());
		// if (db != null) {
		// ToastUtil.show(
		// LoginActivity.this,
		// "userName=" + db.getUserName() + "\r\nUserId="
		// + db.getUserId() + "\r\nuserToken" + db.getToken()
		// + "\r\nUserIcon" + db.getUserIcon());
		// }
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
//		ToastUtil.show(this, "请求登陆失败，请稍后重试。。。");

	}

	/*********************************************************************************************************************/
	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	// class AuthListener implements WeiboAuthListener {
	//
	// @Override
	// public void onComplete(Bundle values) {
	// // 从 Bundle 中解析 Token
	// Oauth2AccessToken mAccessToken = Oauth2AccessToken
	// .parseAccessToken(values);
	// if (mAccessToken.isSessionValid()) {
	// // 显示 Token
	// // updateTokenView(false);
	// // 保存 Token 到 SharedPreferences
	// LogUtil.d("login", "Uid::::" + mAccessToken.getToken());
	//
	// // AccessTokenKeeper.writeAccessToken(LoginActivity.this,
	// // mAccessToken);
	// Toast.makeText(LoginActivity.this,
	// "授权成功" + mAccessToken.getToken(), Toast.LENGTH_SHORT)
	// .show();
	// thirdRegiest(mAccessToken.getUid(), mAccessToken.getToken());
	// } else {
	// // 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
	// String code = values.getString("code");
	// String message = "授权失败";
	// if (!TextUtils.isEmpty(code)) {
	// message = message + "\nObtained the code: " + code;
	// }
	// Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG)
	// .show();
	// }
	// }
	//
	// @Override
	// public void onCancel() {
	// Toast.makeText(LoginActivity.this, "取消", Toast.LENGTH_LONG).show();
	// }
	//
	// @Override
	// public void onWeiboException(WeiboException arg0) {
	//
	// }
	// }

	/**
	 * 1、用户触发第三方登录事件 2、showUser(null)请求授权用户的资料（这个过程中可能涉及授权操作）
	 * 3、如果onComplete()方法被回调，将其参数Hashmap代入你应用的Login流程
	 * 4、否则提示错误，调用removeAccount()方法，删除可能的授权缓存数据 5、Login时客户端发送用户资料中的用户ID给服务端
	 * 6、服务端判定用户是已注册用户，则引导用户进入系统，否则返回特定错误码
	 * 7、客户端收到“未注册用户”错误码以后，代入用户资料到你应用的Register流程
	 * 8、Register时在用户资料中挑选你应用的注册所需字段，并提交服务端注册 9、服务端完成用户注册，成功则反馈客户端引导用户进入系统
	 * 10、否则提示错误，调用removeAccount()方法，删除可能的授权缓存数据
	 * 
	 */
	// private WeiboAuth weiboAuth;
	// private SsoHandler ssoHandler;

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
			// Toast.makeText(this, userName + "##" + userPassword, 0).show();
			sendLogin1(userName, userPassword);
		}
	}

	private void sendLogin1(String name, String password) {
		MapPackage mp = new MapPackage();
		mp.setHead(this);
		mp.setPara("account", name);
		mp.setPara("pwd", password);
		mp.setPara("type", "0");
		Map<String, Object> maps = mp.getMap();
		RequestParams params = GsonTools.GetParams(maps);
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, Container.LOGIN_URL, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						ToastUtil.showProgressDialog(LoginActivity.this);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("LoginActivity", responseInfo.result);
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

	private void setViewVisible(boolean need_visible, FrameLayout tv,
			EditText et, int id) {

		if (need_visible) {
			tv.setVisibility(View.VISIBLE);
			et.setHint("");
		} else {
			tv.setVisibility(View.INVISIBLE);
			et.setHint(getResources().getString(id));
		}
	}

	private void action(UserLoginBean userInfo) {
		if (userInfo == null) {
		} else if ("10000".equals(userInfo.head.code)) {
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.UID, userInfo.para.user_id);
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.EMAIL, userInfo.para.email);
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.PHONE, userInfo.para.phone);
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.NICKNAME, userInfo.para.nickname);
			// 账户订单数
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.ORDERNO_NO, userInfo.para.order_num);
			// 账户收藏
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.COLLECT_NO,
					userInfo.para.favorite_num);
			// 账户优惠券
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.COUPON_NO, userInfo.para.ticket_num);
			// 积分
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.CREDITS_NO, userInfo.para.point_num);
			// 账户余额************************************************
			PreferencesUtils.putValueToSPMap(LoginActivity.this,
					PreferencesUtils.Keys.ACCOUNT_NO, userInfo.para.balance);
			PreferencesUtils.putValueToSPMap(LoginActivity.this,

					PreferencesUtils.Keys.USER_PORTRAIT,
					Container.URL+userInfo.para.picture_url);

			ToastUtil.closeProgressDialog();
			intent = new Intent();
			intent.setClass(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			this.finish();

		} else {
			Toast.makeText(this, userInfo.head.msg, 0).show();
			edit_password.setText("");
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	public void onClick(View v) {

	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // TODO Auto-generated method stub
	// if (ssoHandler != null) {
	// ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	// super.onActivityResult(requestCode, resultCode, data);
	// }
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
			Intent i = new Intent();
			i.setClass(this, MainActivity.class);
			startActivity(i);
			return false;
		}
		return true;
	}

	private void thirdRegiest(String uid, String token) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("source", Constants.APP_KEY);
		params.addQueryStringParameter("access_token", token);
		params.addQueryStringParameter("uid", uid);
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
				"https://api.weibo.com/2/users/show.json", params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> info) {
						Log.e("login", info.result);
					}
				});
	}

}
