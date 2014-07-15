package cn.icnt.dinners.dinner;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.icnt.dinners.utils.ToastUtil;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class FindPassWordActivity extends Activity {
	@ViewInject(R.id.title_left_btn)
	private RelativeLayout back; // 返回按钮
	@ViewInject(R.id.title_center_text)
	private TextView title_center_text;
	
	@ViewInject(R.id.edit_question)
	private EditText edit_question;
	@ViewInject(R.id.edit_answer)
	private EditText edit_answer;
	
	@ViewInject(R.id.answer)
	private RelativeLayout answer;
	
	@ViewInject(R.id.edit_oldpwd)
	private EditText edit_oldpwd;
	@ViewInject(R.id.edit_newpwd)
	private EditText edit_newpwd;
	@ViewInject(R.id.edit_confirmpwd)
	private EditText edit_confirmpwd;
	
	@ViewInject(R.id.change_password)
	private RelativeLayout change_password;
	
	private InputMethodManager manager;
	private boolean canChange= false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_password);
		initview();
	}

	private void initview() {
		ViewUtils.inject(this);
		title_center_text.setText("忘记密码");
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

	@OnClick({ R.id.answer, R.id.change_password, R.id.title_left_btn })
	public void clickMethod(View v) {
		String question = edit_question.getText().toString().trim();
		String answer = edit_answer.getText().toString().trim();
		
		String oldpwd = edit_oldpwd.getText().toString().trim();
		String newpwd = edit_newpwd.getText().toString().trim();
		String confirmpwd = edit_confirmpwd.getText().toString().trim();
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			break;
		case R.id.answer:
			if (StringUtils.isEmpty(question)) {
				ToastUtil.show(this, "请输入密保问题");
			}else if (StringUtils.isEmpty(answer)) {
				ToastUtil.show(this, "请输入密保");
			}else {
				ToastUtil.show(this, "验证操作");
			}
			
			break;
		case R.id.change_password:
			if (!StringUtils.isEmpty(oldpwd) && !StringUtils.isEmpty(newpwd) && !StringUtils.isEmpty(confirmpwd) && confirmpwd.equals(newpwd)) {
				ToastUtil.show(this, "更改密码成功");
			}else if (StringUtils.isEmpty(oldpwd) || StringUtils.isEmpty(newpwd) || StringUtils.isEmpty(confirmpwd)) {
				ToastUtil.show(this, "请输入密码");
			}else if (!confirmpwd.equals(newpwd)) {
				ToastUtil.show(this, "请检查确认密码与新密码是否一致");
			}
			break;
		}
	}
}
