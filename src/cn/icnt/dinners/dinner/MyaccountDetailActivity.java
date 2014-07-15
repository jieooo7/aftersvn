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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
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
	private TextView tv1;
	private TextView tv2;
	private boolean flag;
	private TextView itemTv1;
	private TextView itemTv2;
	private LinearLayout line0;
	private LinearLayout line1;
	private LinearLayout line2;
	private LinearLayout line3;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount_detail);

		// 设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.my_account));

		// 充值 提现布局
		lv1 = (LinearLayout) findViewById(R.id.myaccount_lv1);
		lv2 = (LinearLayout) findViewById(R.id.myaccount_lv2);
		lv1.setOnClickListener(this);
		lv2.setOnClickListener(this);
		tv1 = (TextView) findViewById(R.id.myaccount_tv1);
		tv2 = (TextView) findViewById(R.id.myaccount_tv2);

		// 银行 支付宝支付提现
		itemTv1 = (TextView) findViewById(R.id.myaccount_alipay_recharge);
		itemTv2 = (TextView) findViewById(R.id.myaccount_bank_recharge);
		itemTv1.setOnClickListener(this);
		itemTv2.setOnClickListener(this);
		line0 = (LinearLayout) findViewById(R.id.myaccount_line0);
		line1 = (LinearLayout) findViewById(R.id.myaccount_line1);
		line2 = (LinearLayout) findViewById(R.id.myaccount_line2);
		line3 = (LinearLayout) findViewById(R.id.myaccount_line3);

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
//			充值
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
//			提现
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
		case R.id.myaccount_alipay_recharge:
			if (flag) {
//				支付宝充值
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

			} else {
//				支付宝提现
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

			}

			break;
		case R.id.myaccount_bank_recharge:
			if (!flag) {
//				银行提现
				itemTv1.setText(getResources().getString(R.string.alipay_get_cash));
				itemTv1.setTextColor(getResources().getColor(
						R.color.myaccount_text_dark));
				itemTv2.setText(getResources().getString(R.string.bank_get_cash));
				itemTv2.setTextColor(getResources().getColor(
						R.color.myaccount_text_light));
				line0.setVisibility(View.GONE);
				line1.setVisibility(View.GONE);
				line2.setVisibility(View.GONE);
				line3.setVisibility(View.VISIBLE);

			} else {
//				银行充值
				itemTv1.setText(getResources().getString(R.string.alipay_recharge));
				itemTv1.setTextColor(getResources().getColor(
						R.color.myaccount_text_dark));
				itemTv2.setText(getResources().getString(R.string.bank_recharge));
				itemTv2.setTextColor(getResources().getColor(
						R.color.myaccount_text_light));
				line0.setVisibility(View.GONE);
				line1.setVisibility(View.VISIBLE);
				line2.setVisibility(View.GONE);
				line3.setVisibility(View.GONE);

			}

			break;

		}
	}

}
