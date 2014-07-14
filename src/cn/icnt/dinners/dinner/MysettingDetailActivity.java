/*
 * SettingDetailActivity.java
 * classes : cn.icnt.dinners.dinner.SettingDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月14日 上午10:22:10
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * cn.icnt.dinners.dinner.SettingDetailActivity
 * @author Andrew Lee <br/>
 * create at 2014年7月14日 上午10:22:10
 */
public class MysettingDetailActivity extends Activity implements OnClickListener{
	private static final String TAG = "SettingDetailActivity";
	private TextView tv1;
	private TextView tv2;
	private LinearLayout lv;
	private ImageView iv;
	
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myseting_detail);

		// 设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.setting));
		
		
		tv1=(TextView) findViewById(R.id.myseting_tv1);
		tv2=(TextView) findViewById(R.id.myseting_tv2);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		
		lv=(LinearLayout) findViewById(R.id.myseting_lv);
		iv=(ImageView) findViewById(R.id.myseting_iv);
		
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.myseting_tv1:
			tv1.setBackgroundColor(getResources().getColor(
					R.color.mycredit_back_select));
			tv2.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			lv.setVisibility(View.VISIBLE);
			iv.setVisibility(View.INVISIBLE);
			break;
		case R.id.myseting_tv2:
			tv2.setBackgroundColor(getResources().getColor(
					R.color.mycredit_back_select));
			tv1.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			iv.setVisibility(View.VISIBLE);
			lv.setVisibility(View.INVISIBLE);
			break;
		}
	}
	
}
