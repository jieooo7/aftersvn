/*
 * SettingDetailActivity.java
 * classes : cn.icnt.dinners.dinner.SettingDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月14日 上午10:22:10
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.MD5;

import com.umeng.analytics.MobclickAgent;

/**
 * cn.icnt.dinners.dinner.SettingDetailActivity
 * @author Andrew Lee <br/>
 * create at 2014年7月14日 上午10:22:10
 */
public class MysettingDetailActivity extends Activity implements OnClickListener{
	private static final String TAG = "SettingDetailActivity";
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private LinearLayout lv;
	private LinearLayout lv1;
	private ImageView iv;
	private RelativeLayout title;
	private EditText et1;
	private EditText et2;
	private EditText et3;
	private EditText et4;
	private Button bt;
	private Button bt1;
	private String question;
	private String answer;
	private String passwd1;
	private String passwd2;
	private MapPackage mp;
	private InputMethodManager manager;
	
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myseting_detail);

		// 设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.setting));
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		
		
		tv1=(TextView) findViewById(R.id.myseting_tv1);
		tv2=(TextView) findViewById(R.id.myseting_tv2);
		tv3=(TextView) findViewById(R.id.myseting_tv3);
		title = (RelativeLayout) findViewById(R.id.title_left_btn);
		title.setOnClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		
		lv=(LinearLayout) findViewById(R.id.myseting_lv);
		iv=(ImageView) findViewById(R.id.myseting_iv);
		lv1=(LinearLayout) findViewById(R.id.myseting_lv1);
		
		
		et1=(EditText) findViewById(R.id.myset_question);
		et2=(EditText) findViewById(R.id.myset_answer);
		et3=(EditText) findViewById(R.id.myset_question1);
		et4=(EditText) findViewById(R.id.myset_answer1);
		bt=(Button) findViewById(R.id.myset_button);
		bt1=(Button) findViewById(R.id.myset_button1);
		bt1.setOnClickListener(this);
		bt.setOnClickListener(this);
		
		
		
//		et1.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				CharSequence hint = et1.getHint();
//				if (hasFocus) {
//					et1.setHint("");
//				}else {
//					et1.setHint("你的居住地在哪里");
//				}
//			}
//		});
//		et2.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				CharSequence hint = et1.getHint();
//				if (hasFocus) {
//					et2.setHint("");
//				}else {
//					et2.setHint("厦门湖里区");
//				}
//			}
//		});

	}
	
	
	protected String send(String question2, String answer2){
		
		mp=new MapPackage();
		mp.setPath("reset_answer");
		mp.setHead(this);
		mp.setPara("question", question2);
		mp.setPara("answer", answer2);
		try{
			mp.send();
			
			
		}catch(Exception e){
			if (HttpSendRecv.netStat)
				Toast.makeText(getApplicationContext(), "网络错误，请重试",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "出错了>_<",
						Toast.LENGTH_LONG).show();
		}
		if(mp.getBackHead()!=null){
		return mp.getBackHead().get("code");
		}else{
			return null;
		}
		
	}
	
protected String sendpasswd(String passwd){
		
		mp=new MapPackage();
		mp.setPath("add_payment_password");
		mp.setHead(this);
		mp.setPara("payment_pwd", MD5.getMD5(passwd));
		try{
			mp.send();
			
			
		}catch(Exception e){
			if (HttpSendRecv.netStat)
				Toast.makeText(getApplicationContext(), "网络错误，请重试",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "出错了>_<",
						Toast.LENGTH_LONG).show();
		}
		if(mp.getBackHead()!=null){
		return mp.getBackHead().get("code");
		}else{
			return null;
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

	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.myseting_tv1:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			tv1.setBackgroundColor(getResources().getColor(
					R.color.mycredit_back_select));
			tv2.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			tv3.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			lv.setVisibility(View.VISIBLE);
			iv.setVisibility(View.GONE);
			lv1.setVisibility(View.GONE);
			break;
		case R.id.myseting_tv2:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			tv2.setBackgroundColor(getResources().getColor(
					R.color.mycredit_back_select));
			tv1.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			tv3.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			iv.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
			lv1.setVisibility(View.GONE);
			break;
			
		case R.id.myseting_tv3:
			tv3.setBackgroundColor(getResources().getColor(
					R.color.mycredit_back_select));
			tv1.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			tv2.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			lv1.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
			iv.setVisibility(View.GONE);
			break;
			
		case R.id.title_left_btn:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			finish();
			break;
		case R.id.myset_button:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			question=et1.getText().toString();
			answer=et2.getText().toString();
			if(StringUtils.isEmpty(question) ||StringUtils.isEmpty(answer)){
				Toast.makeText(getApplicationContext(), "问题或者答案不能为空",
						Toast.LENGTH_SHORT).show();
			}else{
				String send=send(question,answer);
				if(send!=null&&send.equals("10000")){
					Toast.makeText(getApplicationContext(), "密保设置成功",
							Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "密保设置失败，请重新设置",
							Toast.LENGTH_LONG).show();
				}
			}
			break;
			
		case R.id.myset_button1:
			passwd1=et3.getText().toString();
			passwd2=et4.getText().toString();
			if(StringUtils.isEmpty(passwd1) ||StringUtils.isEmpty(passwd2)){
				Toast.makeText(getApplicationContext(), "密码不能为空",
						Toast.LENGTH_SHORT).show();
				
			}else if(StringUtils.equals(passwd1, passwd2)){
				String sendpasswd=sendpasswd(passwd1);
				if(sendpasswd!=null&&sendpasswd.equals("10000")){
					Toast.makeText(getApplicationContext(), "支付密码设置成功",
							Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "支付密码设置失败，请重新设置",
							Toast.LENGTH_LONG).show();
				}
				
				
			}else{
				
				Toast.makeText(getApplicationContext(), "两次密码必须一致",
						Toast.LENGTH_SHORT).show();
			}
			
			break;
			
		}
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
