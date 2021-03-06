package cn.icnt.dinners.dinner;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.beans.UserLoginBean;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.DensityUtil;
import cn.icnt.dinners.utils.MD5;
import cn.icnt.dinners.utils.PreferencesUtils;
import cn.icnt.dinners.utils.ToastUtil;

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

public class RegisterActivity extends Activity {
	@ViewInject(R.id.title_left_btn)
	private RelativeLayout title_left_btn;

	@ViewInject(R.id.title_left_btn_img)
	private ImageView title_left_btn_img;

	@ViewInject(R.id.title_center_text)
	private TextView title_center_text;
	@ViewInject(R.id.regiest_alert)
	private TextView regiest_alert;

	@ViewInject(R.id.user_regiest)
	private TextView user_regiest;

	@ViewInject(R.id.nickname_alert)
	private TextView nickname_alert;
	@ViewInject(R.id.nickname_line)
	private ImageView nickname_line;
	@ViewInject(R.id.edit_nickname)
	private EditText edit_nickname;

	@ViewInject(R.id.username_alert)
	private TextView username_alert;
	@ViewInject(R.id.username_line)
	private ImageView username_line;
	@ViewInject(R.id.edit_username)
	private EditText edit_username;

	@ViewInject(R.id.password_alert)
	private TextView password_alert;
	@ViewInject(R.id.password_line)
	private ImageView password_line;
	@ViewInject(R.id.edit_password)
	private EditText edit_password;

	@ViewInject(R.id.password2_alert)
	private TextView password2_alert;
	@ViewInject(R.id.password2_line)
	private ImageView password2_line;
	@ViewInject(R.id.edit_password2)
	private EditText edit_password2;

	private InputMethodManager manager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_regiest);
		initView();
	}

	private void initView() {
		ViewUtils.inject(this);
		title_center_text.setText("注册");
		// setViewVisible( );
		setEditBackground();
		// Spanned text = Html.fromHtml("点击确认代表你已阅读并同意美食聚惠的: "
		// + "<a href=\"http://www.baidu.com\" >服务协议</a> " + "");
		// regiest_alert.setText(text);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	@OnClick({ R.id.title_left_btn, R.id.user_regiest })
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			Intent i = new Intent();
			i.setClass(RegisterActivity.this, LoginActivity.class);
			startActivity(i);
			break;
		case R.id.user_regiest:
			userRegiest();
			break;
		}
	}

	private void userRegiest() {
		String nickname = edit_nickname.getText().toString();
		String username = edit_username.getText().toString();
		String password = edit_password.getText().toString();
		String password2 = edit_password2.getText().toString();
		// String email = "";
		// String phone = "";
		if (StringUtils.isEmpty(nickname)) {
			Toast.makeText(this, "请输入昵称", 0).show();
		} else {
			if (StringUtils.isEmpty(username)) {
				Toast.makeText(this, "请输入昵称", 0).show();
			} else {
				if (StringUtils.isEmpty(password)) {
					Toast.makeText(this, "请输入密码", 0).show();
				} else {
					if (StringUtils.isEmpty(password2)) {
						Toast.makeText(this, "请确认密码", 0).show();
					} else {
						if (!password.equals(password2)) {
							Toast.makeText(this, "两次密码输入不一致，请重新输入", 0).show();
						} else {
							if (DensityUtil.isMobileNO(username)) {
								// phone = username;
								regiestSend(nickname, username, MD5.getMD5(password));
							} else {
								Toast.makeText(this, "请正确输入手机号", 0).show();
							}
						}
					}
				}
			}
		}

	}

	private void regiestSend(String nickname, String phone, String password) {
		MapPackage mp = new MapPackage();
		mp.setHead(this);
		mp.setPara("nickname", nickname);
		mp.setPara("phone", phone);
		mp.setPara("pwd", password);
		mp.setPara("type", "0");
		Map<String, Object> maps = mp.getssMap();
		RequestParams params = GsonTools.GetParams(maps);
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, Container.REGIEST, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						ToastUtil.showProgressDialog(RegisterActivity.this);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("RegisterActivity", responseInfo.result);
						UserLoginBean userInfo =GsonTools.getPerson(responseInfo.result, UserLoginBean.class);
						if ("10000".equals(userInfo.head.code)) {
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.UID,
									userInfo.para.user_id);
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.EMAIL,
									userInfo.para.email);
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.PHONE,
									userInfo.para.phone);
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.NICKNAME,
									userInfo.para.nickname);
							// 账户订单数
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.ORDERNO_NO,
									userInfo.para.order_num);
							// 账户收藏
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.COLLECT_NO,
									userInfo.para.favorite_num);
							// 账户优惠券
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.COUPON_NO,
									userInfo.para.ticket_num);
							// 积分
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.CREDITS_NO,
									userInfo.para.point_num);
							// 头像
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.USER_PORTRAIT,
									Container.URL + userInfo.para.picture_url);
							// 账户余额************************************************
							PreferencesUtils.putValueToSPMap(
									RegisterActivity.this,
									PreferencesUtils.Keys.ACCOUNT_NO,
									userInfo.para.balance);
							// ToastUtil.show(RegisterActivity.this,
							// userInfo.head.msg);
							ToastUtil.closeProgressDialog();
//							Intent intent = new Intent();
//							intent.setClass(RegisterActivity.this,
//									MainActivity.class);
//							startActivity(intent);
							RegisterActivity.this.finish();
						} else {
							ToastUtil.show(RegisterActivity.this,
									userInfo.head.msg);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					    Log.i("RegisterActivity", msg);
					}
				});
	}

	/**
	 * 设置textview 隐藏 和显示
	 * 
	 * @param tv
	 * @param im
	 */
	private void setViewVisible(boolean need_visible, EditText et, TextView tv,
			ImageView im, int id) {
		if (need_visible) {
			tv.setVisibility(View.VISIBLE);
			im.setBackgroundResource(R.color.tab_font);
			et.setHint("");
		} else {
			tv.setVisibility(View.GONE);
			im.setBackgroundResource(R.color.login_gray);
			et.setHint(getResources().getString(id));
		}

	}

	/**
	 * 设置Edittext 监听控制背景
	 */
	private void setEditBackground() {
		edit_nickname.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setViewVisible(
						hasFocus
								|| !StringUtils.isEmpty(edit_nickname.getText()
										.toString()), edit_nickname,
						nickname_alert, nickname_line,
						R.string.regiest_nickname);
			}
		});
		edit_username.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setViewVisible(
						hasFocus
								|| !StringUtils.isEmpty(edit_username.getText()
										.toString()), edit_username,
						username_alert, username_line,
						R.string.regiest_username);
			}
		});
		edit_password.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setViewVisible(
						hasFocus
								|| !StringUtils.isEmpty(edit_password.getText()
										.toString()), edit_password,
						password_alert, password_line,
						R.string.regiest_password);
			}
		});
		edit_password2.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setViewVisible(
						hasFocus
								|| !StringUtils.isEmpty(edit_password2
										.getText().toString()), edit_password2,
						password2_alert, password2_line,
						R.string.regiest_passwords);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
			Intent i = new Intent();
			i.setClass(this, LoginActivity.class);
			startActivity(i);
			return false;
		}
		return true;
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
