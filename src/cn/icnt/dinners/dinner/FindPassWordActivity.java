package cn.icnt.dinners.dinner;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.beans.FindPwdBean;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.MD5;
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

public class FindPassWordActivity extends Activity {
	@ViewInject(R.id.title_left_btn)
	private RelativeLayout back; // 返回按钮
	@ViewInject(R.id.title_center_text)
	private TextView title_center_text;

	@ViewInject(R.id.text_question)
	private TextView text_question;
	@ViewInject(R.id.find_msg)
	private TextView find_msg;
	@ViewInject(R.id.edit_answer)
	private EditText edit_answer;

	@ViewInject(R.id.answer)
	private RelativeLayout answer;
	@ViewInject(R.id.text_answer)
	private TextView text_answer;

	@ViewInject(R.id.edit_newpwd)
	private EditText edit_newpwd;
	@ViewInject(R.id.edit_confirmpwd)
	private EditText edit_confirmpwd;

	@ViewInject(R.id.change_password)
	private RelativeLayout change_password;
	@ViewInject(R.id.text_change_password)
	private TextView text_change_password;
/*******验证码**********/
	@ViewInject(R.id.server_send_captcha)
	private TextView server_send_captcha;
	@ViewInject(R.id.findpwd_captcha)
	private EditText findpwd_captcha;
	
	private InputMethodManager manager;
	private boolean canChange = false;
	private HttpUtils http;
	private FindPwdBean findPwd;
	private FindPwdBean answer_res;
	private FindPwdBean change_pwd;
	private String user_name;
	private String state;
	private String answer2;
	private Map<String, Object> headmap;
	private String captcha;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_password);
		http = new HttpUtils();
		headmap = new HashMap<String, Object>();
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		headmap.put("uid", "-1");
		headmap.put("no", telephonyManager.getDeviceId());
		headmap.put("os", "1");
		headmap.put("version", "1.1.2");
		headmap.put("key", MD5.getMD5("-11234" + telephonyManager.getDeviceId()
				+ "11.1.2"));

		initview();
		initData();
	}

	private void initData() {
		Intent intent = getIntent();
		user_name = intent.getStringExtra("user_name");
		// MapPackage mp = new MapPackage();
		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<String, Object>();
		paramap.put("user_name", user_name);
		paramap.put("answer", "");

		maps.put("head", headmap);
		maps.put("para", paramap);

		// mp.setHead(this);
		// mp.setPara("user_name", user_name);
		// mp.setPara("answer","");

		// Map<String, Object> maps = mp.getssMap();
		RequestParams params = GsonTools.GetParams(maps);
		http.send(HttpRequest.HttpMethod.POST, Container.FINDPASSWOED, params,
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
						Log.i("order", responseInfo.result);
						Gson gson = new Gson();
						findPwd = gson.fromJson(responseInfo.result,
								FindPwdBean.class);
						if (findPwd != null) {
							if ("10000".equals(findPwd.head.code)) {
								text_question.setText(findPwd.para.toString());
								state = findPwd.para;
							}
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
	}

	private void initview() {
		ViewUtils.inject(this);
		title_center_text.setText("修改账户密码");
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

	@OnClick({ R.id.answer, R.id.change_password, R.id.title_left_btn ,R.id.server_send_captcha})
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			break;
		case R.id.answer:
			answer2 = edit_answer.getText().toString().trim();
			captcha = findpwd_captcha.getText().toString().trim(); //验证码
			if (!canChange) {
				if (StringUtils.isEmpty(answer2)) {
					ToastUtil.show(this, "请输入密保");
				} else if ( StringUtils.isEmpty(captcha)) {
					ToastUtil.show(this, "请输入验证码");
				} else {
					answering(answer2);
				}
			}
			break;
		case R.id.change_password:
			String newpwd = edit_newpwd.getText().toString().trim();
			String confirmpwd = edit_confirmpwd.getText().toString().trim();
			if (canChange) {
				if (!StringUtils.isEmpty(newpwd)
						&& !StringUtils.isEmpty(confirmpwd)
						&& confirmpwd.equals(newpwd)) {
					changePassword(newpwd);
					// ToastUtil.show(this, "更改密码成功");
				} else if (StringUtils.isEmpty(newpwd)
						|| StringUtils.isEmpty(confirmpwd)) {
					ToastUtil.show(this, "请输入密码");
				} else if (!confirmpwd.equals(newpwd)) {
					ToastUtil.show(this, "请检查确认密码与新密码是否一致");
				}
			} else {
				ToastUtil.show(this, "请先验证密保问题");
			}
			break;
		case R.id.server_send_captcha:
			
			//想服务器发送 索要验证码 请求
			break;
		}
	}

	private void answering(String answer2) {

		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<String, Object>();
		paramap.put("user_name", user_name);
		paramap.put("new_password", "");
		paramap.put("answer", answer2);
		paramap.put("type", state);

		maps.put("head", headmap);
		maps.put("para", paramap);
		// MapPackage mp = new MapPackage();
		// mp.setHead(this);
		// mp.setPara("user_name", user_name);
		// mp.setPara("new_password", "");
		// mp.setPara("answer",answer2);
		// mp.setPara("type",state);
		// Map<String, Object> maps = mp.getssMap();
		RequestParams params = GsonTools.GetParams(maps);
		http.send(HttpRequest.HttpMethod.POST,
				Container.FINDPASSWOED, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("order", responseInfo.result);
						Gson gson = new Gson();
						answer_res = gson.fromJson(responseInfo.result,
								FindPwdBean.class);
						if (answer_res != null) {
							if ("10000".equals(answer_res.head.code)) {
								text_answer.setTextColor(getResources()
										.getColor(R.color.white_text));
								answer.setBackgroundColor(getResources()
										.getColor(R.color.change_btn_bg));

								text_change_password
										.setTextColor(getResources().getColor(
												R.color.tab_font));
								change_password
										.setBackgroundColor(getResources()
												.getColor(R.color.login_btn_bg));
								canChange = true;
								state = answer_res.para;
								find_msg.setText(answer_res.head.msg);
							} else {
								find_msg.setText(answer_res.head.msg);
							}
						}
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
					}
				});
	}

	private void changePassword(String newpwd) {
		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<String, Object>();
		paramap.put("user_name", user_name);
		paramap.put("new_password", newpwd);
		paramap.put("answer", answer2);
		paramap.put("type", state);

		maps.put("head", headmap);
		maps.put("para", paramap);

		// MapPackage mp = new MapPackage();
		// mp.setHead(this);
		// mp.setPara("user_name", user_name);
		// mp.setPara("new_password", newpwd);
		// mp.setPara("answer", answer2);
		// mp.setPara("type", state);
		// Map<String, Object> maps = mp.getssMap();
		RequestParams params = GsonTools.GetParams(maps);
		http.send(HttpRequest.HttpMethod.POST,
				Container.FINDPASSWOED, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("order", responseInfo.result);
						Log.i("order", "state" + state);
						Gson gson = new Gson();
						change_pwd = gson.fromJson(responseInfo.result,
								FindPwdBean.class);
						if ("10000".equals(change_pwd.head.code)) {
							ToastUtil.show(FindPassWordActivity.this, "###"
									+ change_pwd.head.msg + "###");
							state = change_pwd.para;
							finish();
						}
					}
				});
	}

}
