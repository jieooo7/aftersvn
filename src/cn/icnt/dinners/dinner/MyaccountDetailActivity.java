/*
 * MyaccountDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MyaccountDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月14日 上午10:21:30
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * cn.icnt.dinners.dinner.MyaccountDetailActivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年7月14日 上午10:21:30
 */
public class MyaccountDetailActivity extends Activity implements
		OnClickListener {
	private static final String TAG = "MyaccountDetailActivity";
	private LinearLayout lv1;
	private LinearLayout lv2;
	private LinearLayout lv3;
	private LinearLayout lv4;
	private TextView tv1;
	private TextView tv2;
	private boolean flag;
	private TextView itemTv1;
	private TextView itemTv2;
	private LinearLayout line0;
	private LinearLayout line1;
	private LinearLayout line2;
	private LinearLayout line3;
	private RelativeLayout title;
	private InputMethodManager manager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount_detail);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		// 设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.my_account));
		Intent intent=getIntent();
		String account="  "+intent.getStringExtra("account")+"元";
		((TextView) findViewById(R.id.myaccount_money)).setText(account);
		flag=true;
		// 充值 提现布局
		lv1 = (LinearLayout) findViewById(R.id.myaccount_lv1);
		lv2 = (LinearLayout) findViewById(R.id.myaccount_lv2);
		title = (RelativeLayout) findViewById(R.id.title_left_btn);
		title.setOnClickListener(this);
		lv1.setOnClickListener(this);
		lv2.setOnClickListener(this);
		tv1 = (TextView) findViewById(R.id.myaccount_tv1);
		tv2 = (TextView) findViewById(R.id.myaccount_tv2);

		// 银行 支付宝支付 提现
		itemTv1 = (TextView) findViewById(R.id.myaccount_alipay_recharge);
		itemTv2 = (TextView) findViewById(R.id.myaccount_bank_recharge);
//		itemTv1.setOnClickListener(this);
//		itemTv2.setOnClickListener(this);
		
//		四层布局
		line0 = (LinearLayout) findViewById(R.id.myaccount_line0);
		line1 = (LinearLayout) findViewById(R.id.myaccount_line1);
		line2 = (LinearLayout) findViewById(R.id.myaccount_line2);
		line3 = (LinearLayout) findViewById(R.id.myaccount_line3);
		
//		支付宝包裹
		lv3= (LinearLayout) findViewById(R.id.myaccount_alipay_ll);
		lv3.setOnClickListener(this);
//		银行卡包裹
		lv4= (LinearLayout) findViewById(R.id.myaccount_bank_ll);
		lv4.setOnClickListener(this);

	}
	
	
//	监听软键盘消失
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myaccount_lv1:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			// 点击充值 默认页面 第一个
			flag = true;
			tv1.setVisibility(View.VISIBLE);
			tv2.setVisibility(View.INVISIBLE);
			itemTv1.setText(getResources().getString(R.string.alipay_recharge));
			itemTv1.setTextColor(getResources().getColor(
					R.color.myaccount_text_light));
			itemTv2.setText(getResources().getString(R.string.bank_recharge));
			itemTv2.setTextColor(getResources().getColor(
					R.color.myaccount_text_dark));
			line0.setVisibility(View.VISIBLE);
			line1.setVisibility(View.GONE);
			line2.setVisibility(View.GONE);
			line3.setVisibility(View.GONE);
			break;
		case R.id.myaccount_lv2:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			// 点击提现默认页面，默认第一个
			flag = false;
			tv2.setVisibility(View.VISIBLE);
			tv1.setVisibility(View.INVISIBLE);
			itemTv1.setText(getResources().getString(R.string.alipay_get_cash));
			itemTv1.setTextColor(getResources().getColor(
					R.color.myaccount_text_light));
			itemTv2.setText(getResources().getString(R.string.bank_get_cash));
			itemTv2.setTextColor(getResources().getColor(
					R.color.myaccount_text_dark));
			line0.setVisibility(View.GONE);
			line1.setVisibility(View.GONE);
			line2.setVisibility(View.VISIBLE);
			line3.setVisibility(View.GONE);
			break;
		case R.id.myaccount_alipay_ll:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			if (flag) {
				// 支付宝充值
				itemTv1.setText(getResources().getString(
						R.string.alipay_recharge));
				itemTv1.setTextColor(getResources().getColor(
						R.color.myaccount_text_light));
				itemTv2.setText(getResources()
						.getString(R.string.bank_recharge));
				itemTv2.setTextColor(getResources().getColor(
						R.color.myaccount_text_dark));
				line0.setVisibility(View.VISIBLE);
				line1.setVisibility(View.GONE);
				line2.setVisibility(View.GONE);
				line3.setVisibility(View.GONE);

			} else {
				// 支付宝提现
				itemTv1.setText(getResources().getString(
						R.string.alipay_get_cash));
				itemTv1.setTextColor(getResources().getColor(
						R.color.myaccount_text_light));
				itemTv2.setText(getResources()
						.getString(R.string.bank_get_cash));
				itemTv2.setTextColor(getResources().getColor(
						R.color.myaccount_text_dark));
				line0.setVisibility(View.GONE);
				line1.setVisibility(View.GONE);
				line2.setVisibility(View.VISIBLE);
				line3.setVisibility(View.GONE);

			}

			break;
		case R.id.myaccount_bank_ll:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			if (!flag) {
				// 银行提现
				itemTv1.setText(getResources().getString(
						R.string.alipay_get_cash));
				itemTv1.setTextColor(getResources().getColor(
						R.color.myaccount_text_dark));
				itemTv2.setText(getResources()
						.getString(R.string.bank_get_cash));
				itemTv2.setTextColor(getResources().getColor(
						R.color.myaccount_text_light));
				line0.setVisibility(View.GONE);
				line1.setVisibility(View.GONE);
				line2.setVisibility(View.GONE);
				line3.setVisibility(View.VISIBLE);

			} else {
				// 银行充值
				itemTv1.setText(getResources().getString(
						R.string.alipay_recharge));
				itemTv1.setTextColor(getResources().getColor(
						R.color.myaccount_text_dark));
				itemTv2.setText(getResources()
						.getString(R.string.bank_recharge));
				itemTv2.setTextColor(getResources().getColor(
						R.color.myaccount_text_light));
				line0.setVisibility(View.GONE);
				line1.setVisibility(View.VISIBLE);
				line2.setVisibility(View.GONE);
				line3.setVisibility(View.GONE);

			}

			break;
		case R.id.title_left_btn:
//			manager.hideSoftInputFromWindow(getCurrentFocus()
//					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			finish();
			break;

		}
	}

}
