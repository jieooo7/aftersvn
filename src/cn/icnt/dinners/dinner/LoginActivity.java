package cn.icnt.dinners.dinner;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
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
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.MD5;
import cn.icnt.dinners.utils.PreferencesUtils;
import cn.icnt.dinners.utils.ToastUtil;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

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
import com.umeng.analytics.MobclickAgent;

public class LoginActivity extends Activity implements PlatformActionListener ,Callback {
    @ViewInject(R.id.title_left_btn)
    private RelativeLayout back; // 返回按钮
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
    private String userName;
    private String userPassword;
    private Intent intent;
    private InputMethodManager manager;
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.user_login);
	ShareSDK.initSDK(this);
	intent = new Intent();
	initview();
    }

    private Handler handler = new Handler() {
	@Override
	public void handleMessage(Message msg) {
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 3:
		break;
	    case 4:
		break;
	    }
	}
    };

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
			login_phone_editing, user_name, R.string.login_user_name);
	    }
	});
	edit_password.setOnFocusChangeListener(new OnFocusChangeListener() {
	    @Override
	    public void onFocusChange(View v, boolean hasFocus) {
		setViewVisible(hasFocus || edit_password.getText().length() > 0,
			login_pwd_editing, edit_password, R.string.login_user_password);
	    }
	});
    }

    @OnClick({ R.id.title_left_btn, R.id.user_regiest, R.id.forget_password, R.id.login,
	    R.id.sina, R.id.tencent })
    public void clickMethod(View v) {
	switch (v.getId()) {
	case R.id.title_left_btn:
	    finish();
	    break;
	case R.id.user_regiest:
	    intent.setClass(LoginActivity.this, RegisterActivity.class);
	    startActivity(intent);
	    finish();
	    break;
	case R.id.forget_password:
	    String username = user_name.getText().toString().trim();
	    if (StringUtils.isEmpty(username)) {
		ToastUtil.show(this, "请输入用户名");
	    } else {
		finish();
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
	    break;
	case R.id.tencent:
	    authorize(ShareSDK.getPlatform(this, QQ.NAME));
	    // userLogin();
	    break;
	}
    }

    /********************************** 第三方登陆 ***********************************************************************************/

    private void authorize(Platform plat) {
	if (plat.isValid()) {
	    String userId = plat.getDb().getUserId();
	    if (!StringUtils.isEmpty(userId)) {
		Toast.makeText(LoginActivity.this, "nUserId=" + userId, 0).show();
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

    public void onError(Platform platform, int action, Throwable t) {
	if (action == Platform.ACTION_USER_INFOR) {
		UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
	}
	t.printStackTrace();
}

public void onCancel(Platform platform, int action) {
	if (action == Platform.ACTION_USER_INFOR) {
		UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
	}
}

    @Override
    public void onComplete(Platform palm, int arg1, HashMap<String, Object> arg2) {
	UIHandler.sendEmptyMessage(MSG_USERID_FOUND, LoginActivity.this);
	Log.e("D", palm.getDb().getToken() + "##getName::" + palm.getName()
		+ "##getUserId::" + palm.getDb().getUserId());
	Log.e("D", "ICON##::" + palm.getDb().getUserIcon() + "##getExpiresIn::"
		+ palm.getDb().getExpiresIn() + "##getUserName::"
		+ palm.getDb().getUserName());
	PreferencesUtils.putValueToSPMap(this, PreferencesUtils.Keys.USER_PORTRAIT, palm
		.getDb().getUserIcon().toString());
    }

    /*********************************************************************************************************************/

    private void userLogin() {
	userName = user_name.getText().toString().trim();
	userPassword = MD5.getMD5(edit_password.getText().toString().trim());
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
		    public void onLoading(long total, long current, boolean isUploading) {
		    }
		    @Override
		    public void onSuccess(ResponseInfo<String> responseInfo) {
			Log.i("LoginActivity", responseInfo.result);
			Gson gson = new Gson();
			UserLoginBean userInfo = gson.fromJson(responseInfo.result,
				UserLoginBean.class);
			action(userInfo);
		    }
		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});
    }

    private void setViewVisible(boolean need_visible, FrameLayout tv, EditText et, int id) {
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
		    PreferencesUtils.Keys.COLLECT_NO, userInfo.para.favorite_num);
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

	    PreferencesUtils.Keys.USER_PORTRAIT, Container.URL
		    + userInfo.para.picture_url);

	    ToastUtil.closeProgressDialog();
//	    intent = new Intent();
//	    intent.setClass(LoginActivity.this, MainActivity.class);
//	    startActivity(intent);
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
	    if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
		manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
			InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}
	return super.onTouchEvent(event);
    }

    public void onClick(View v) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    finish();
	    return false;
	}
	return true;
    }

    public boolean handleMessage(Message msg) {
	switch (msg.what) {
	case MSG_USERID_FOUND: {
	    Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
	}
	    break;
	case MSG_LOGIN: {
	    String text = getString(R.string.logining, msg.obj);
	    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	    break;
	case MSG_AUTH_CANCEL: {
	    Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
	}
	    break;
	case MSG_AUTH_ERROR: {
	    Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
	}
	    break;
	case MSG_AUTH_COMPLETE: {
	    Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
	}
	    break;
	}
	return false;
    }
    public void onResume() {
	super.onResume();
	MobclickAgent.onResume(this);
	}
	public void onPause() {
	super.onPause();
	MobclickAgent.onPause(this);
	}
}
