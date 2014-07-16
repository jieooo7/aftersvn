/*
 * MycouponDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MycouponDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月8日 上午10:37:45
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import cn.icnt.dinners.adapter.MycollectAdapter;
import cn.icnt.dinners.adapter.MycouponAdapter;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * cn.icnt.dinners.dinner.MycouponDetailActivity
 * @author Andrew Lee <br/>
 * create at 2014年7月8日 上午10:37:45
 */
public class MycouponDetailActivity extends Activity implements OnClickListener {
	private static final String TAG = "MycouponDetailActivity";
	private ListView lv0;
	private RelativeLayout title;
	private MycouponAdapter adapter;
	private List<Map<String, String>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycoupon_detail);
		((TextView) findViewById(R.id.title_center_text))
		.setText(getResources().getString(R.string.my_coupon));
		lv0=(ListView) findViewById(R.id.mycoupon_lv);
		title=(RelativeLayout) findViewById(R.id.title_left_btn);
		title.setOnClickListener(this);
		
		MapPackage mp = new MapPackage();
		mp.setPath("ticket_list");
		mp.setHead(this);
//		mp.setPara("favorite_type", "1");
		mp.setRes("start", "1");
		mp.setRes("count", "10");
		try {
			mp.send();
		} catch (Exception e) {
			if (HttpSendRecv.netStat)
				Toast.makeText(getApplicationContext(), "网络错误，请重试",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "出错了>_<",
						Toast.LENGTH_LONG).show();

		} finally {

		}
		list = mp.getBackResult();
		adapter=new MycouponAdapter(this,list);
		if(list!=null){
		lv0.setAdapter(adapter);
		
	}
	
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			break;
			
		}
	}}
