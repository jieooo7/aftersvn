package cn.icnt.dinners.dinner;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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
		Spanned text = Html.fromHtml("点击确认代表你已阅读并同意美食聚惠的: "
				+ "<a href=\"http://www.baidu.com\">服务协议</a> " + "");
		regiest_alert.setText(text);
	}

	@OnClick({ R.id.title_left_btn, R.id.user_regiest })
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
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
							
							
							regiestSend(nickname, username, password);
						}
					}
				}
			}
		}

	}

	private void regiestSend(String nickname, String username, String password) {
	HttpUtils httpUtils = new HttpUtils();
	RequestParams params = new RequestParams();
	params.addHeader("Header", "headervalue");
	params.addQueryStringParameter("namesds", "addQueryStringParameter");

	// 只包含字符串参数时默认使用BodyParamsEntity，
	// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
	params.addBodyParameter("body", "bodyvalue");
Toast.makeText(this, params.toString(), 1).show();
	// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
	// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
	// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
	// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
	// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
		
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
}
