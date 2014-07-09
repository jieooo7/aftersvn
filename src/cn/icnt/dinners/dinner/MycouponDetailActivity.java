/*
 * MycouponDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MycouponDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月8日 上午10:37:45
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * cn.icnt.dinners.dinner.MycouponDetailActivity
 * @author Andrew Lee <br/>
 * create at 2014年7月8日 上午10:37:45
 */
public class MycouponDetailActivity extends Activity{
	private static final String TAG = "MycouponDetailActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycoupon_detail);
		((TextView) findViewById(R.id.title_center_text))
		.setText(getResources().getString(R.string.my_favor));
	}
	
}
