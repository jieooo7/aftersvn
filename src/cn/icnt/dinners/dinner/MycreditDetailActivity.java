/*
 * MycreditDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MycreditDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月11日 下午5:55:51
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * cn.icnt.dinners.dinner.MycreditDetailActivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年7月11日 下午5:55:51
 */
public class MycreditDetailActivity extends Activity implements OnClickListener {
	private static final String TAG = "MycreditDetailActivity";
	private TextView tv1;
	private TextView tv2;
	private ListView lv;
	private RelativeLayout title;
	
	private SimpleAdapter adapter1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycredit_detail);

		// 设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.my_intergral));

		tv1 = (TextView) findViewById(R.id.mycredit_tv1);
		tv2 = (TextView) findViewById(R.id.mycredit_tv2);
		title = (RelativeLayout) findViewById(R.id.title_left_btn);
		title.setOnClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv1.setText("");
		
		lv=(ListView) findViewById(R.id.mycredit_lv);

		String[] itemKey = new String[] {};
		int[] itemId = new int[] {};
		SimpleAdapter adapter1 = new SimpleAdapter(null, null, 0, itemKey,
				itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.mycredit_tv1:
			tv1.setBackgroundColor(getResources().getColor(
					R.color.mycredit_back_select));
			tv1.setTextColor(getResources().getColor(R.color.mycoupon_item));
			tv2.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			tv2.setTextColor(getResources().getColor(R.color.order_tab_text));
			lv.setAdapter(adapter1);
			break;

		case R.id.mycredit_tv2:
			tv2.setBackgroundColor(getResources().getColor(
					R.color.mycredit_back_select));
			tv2.setTextColor(getResources().getColor(R.color.mycoupon_item));
			tv1.setBackgroundColor(getResources().getColor(
					R.color.mycoupon_textback));
			tv1.setTextColor(getResources().getColor(R.color.order_tab_text));
			lv.setAdapter(adapter1);
			break;
			
		case R.id.title_left_btn:
			finish();
			break;

		}

	}

}
