/*
 * MycollectDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MycollectDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月3日 下午2:12:25
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * cn.icnt.dinners.dinner.MycollectDetailActivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年7月3日 下午2:12:25
 */

public class MyOrderActivity extends Activity {

	@ViewInject(R.id.title_left_btn)
	private RelativeLayout title_left_btn;

	@ViewInject(R.id.title_left_btn_img)
	private ImageView title_left_btn_img;

	@ViewInject(R.id.title_center_text)
	private TextView title_center_text;
	// 全部
	@ViewInject(R.id.order_all)
	private RadioButton order_all;
	// 待付款
	@ViewInject(R.id.order_obligation)
	private RadioButton order_obligation;
	// 待评价
	@ViewInject(R.id.order_evaluate)
	private RadioButton order_evaluate;

	@ViewInject(R.id.order_list)
	private ListView order_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder_detail);
		title_center_text.setText("我的订单");
	}

	@OnClick({ R.id.title_left_btn, R.id.order_all, R.id.order_obligation,
			R.id.order_evaluate })
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			break;
		case R.id.order_all:
			break;
		case R.id.order_obligation:
			break;
		case R.id.order_evaluate:
			break;
		}
	}
}
