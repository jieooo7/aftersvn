/*
 * MycollectDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MycollectDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月3日 下午2:12:25
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.LoaderAdapter.getViewCallback;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.EasyFile;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * cn.icnt.dinners.dinner.MycollectDetailActivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年7月3日 下午2:12:25
 */

public class MycollectDetailActivity extends Activity implements
		OnClickListener,getViewCallback {
	private static final String TAG = "MycollectDetailActivity";

	private LoaderAdapter myAdapter;
	private List<Map<String, String>> myList;
	private ImageLoader mImageLoader;
	private ListView lv;
	private RelativeLayout r0;
	private RelativeLayout r1;
	private RelativeLayout r2;
	private boolean flag0;
	private boolean flag1;
	private boolean flag2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycollect_detail);
		flag0 = true;
		flag1 = true;
		flag2 = true;
		r0 = (RelativeLayout) findViewById(R.id.r0);
		r1 = (RelativeLayout) findViewById(R.id.r1);
		r2 = (RelativeLayout) findViewById(R.id.r2);
		r0.setOnClickListener(this);
		r1.setOnClickListener(this);
		r2.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.lv0);
		mImageLoader = new ImageLoader(this);
		MapPackage mp = new MapPackage();
		mp.setPath("recommend.do?");
		mp.setHead(this);
		mp.setPara("category_id", "0");
		mp.setRes("start", "1");
		mp.setRes("count", "3");
		try {
			mp.send();
			this.myList = mp.getBackResult();
			if (this.myList!= null) {
				boolean bs = EasyFile.writeFile("test", this.myList);
			}
			this.myList = EasyFile.readFile("test");
			myAdapter = new LoaderAdapter(MycollectDetailActivity.this, this.myList);

			lv.setAdapter(myAdapter);

		} catch (Exception e) {
			// TODO: handle exception
			if (HttpSendRecv.netStat)
				Toast.makeText(getApplicationContext(), "网络错误，请重试",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "出错了^_^",
						Toast.LENGTH_LONG).show();
		} finally {
//			this.myList = EasyFile.readFile("test");
//			myAdapter = new LoaderAdapter(this, this.myList);
//
//			lv.setAdapter(myAdapter);
		}
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.r0:
			if (flag0) {
				r0.setBackgroundColor(getResources().getColor(R.color.tab_select));
				flag0 = false;
				DebugUtil.w("000000", "ok");
			} else {
				r0.setBackgroundColor(getResources().getColor(R.color.white_text));
				flag0 = true;
				DebugUtil.w("000000", "not ok");

			}
			break;

		case R.id.r1:
			if (flag1) {
				r1.setBackgroundColor(getResources().getColor(R.color.tab_select));
				flag1 = false;
				DebugUtil.w("111111", "ok");
			} else {
				r1.setBackgroundColor(getResources().getColor(R.color.white_text));
				flag1 = true;
				DebugUtil.w("111111", "not ok");

			}
			break;

		case R.id.r2:
			if (flag2) {
				r2.setBackgroundColor(getResources().getColor(R.color.tab_select));
				flag2 = false;
				DebugUtil.w("222222", "ok");
			} else {
				r2.setBackgroundColor(getResources().getColor(R.color.white_text));
				flag2 = true;
				DebugUtil.w("222222", "not ok");

			}
			break;

		}
	}

	/* (non-Javadoc)
	 * @see cn.icnt.dinners.dinner.LoaderAdapter.getViewCallback#callback(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View callback(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(MycollectDetailActivity.this).inflate(
					R.layout.gcoupon, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) convertView
					.findViewById(R.id.textview2);
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.imageView2);
			DebugUtil.i("issue","1111 test");
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			DebugUtil.i("issue","2222 test");
		}

		viewHolder.mImageView.setImageResource(R.drawable.replace);
		DebugUtil.i("test....result",
				"44444----" + myList.get(position).get("img_url"));
		if (!myAdapter.getFlagBusy()) {
			mImageLoader.DisplayImage(
					MapPackage.PATH + myList.get(position).get("img_url"),
					viewHolder.mImageView, false);
			DebugUtil.i("issue","one test");
			viewHolder.mTextView.setText(myList.get(position).get("goods_no"));
		} else {
			mImageLoader.DisplayImage(
					MapPackage.PATH + myList.get(position).get("img_url"),
					viewHolder.mImageView, false);
			viewHolder.mTextView.setText(myList.get(position).get("goods_no"));
			DebugUtil.i("issue","two test");
		}
		return convertView;
	}

	static class ViewHolder {
		TextView mTextView;
		ImageView mImageView;
	}
	

}
