/*
 * MycouponAdapter.java
 * classes : cn.icnt.dinners.adapter.MycouponAdapter
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月10日 上午9:33:24
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.icnt.dinners.adapter.LoaderAdapter.ViewHolder;
import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.http.MapPackage;

/**
 * cn.icnt.dinners.adapter.MycouponAdapter
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年7月10日 上午9:33:24
 */
public class MycouponAdapter extends BaseAdapter{
	private static final String TAG = "MycouponAdapter";
	private boolean mBusy = false;

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}

	private ImageLoader mImageLoader;
	private Context mContext;
	private List<Map<String, String>> list;

	public MycouponAdapter(Context context, List<Map<String, String>> list) {
		this.list = list;
		this.mContext = context;
		mImageLoader = new ImageLoader(context);
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.mycoupon_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView0 = (TextView) convertView
					.findViewById(R.id.mycoupon_item_tv0);
			viewHolder.mTextView1 = (TextView) convertView
					.findViewById(R.id.mycoupon_item_tv1);
			viewHolder.mTextView2 = (TextView) convertView
					.findViewById(R.id.mycoupon_item_tv2);
			viewHolder.mTextView3 = (TextView) convertView
					.findViewById(R.id.mycoupon_item_tv3);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (!mBusy) {
			viewHolder.mTextView0.setText(list.get(position).get("coupon_name"));
			viewHolder.mTextView1.setText("¥ "+list.get(position).get("coupon_price"));
			viewHolder.mTextView2.setText(list.get(position).get("coupon_start_time")+"-"+list.get(position).get("coupon_end_time"));
			viewHolder.mTextView3.setText(list.get(position).get("coupon_limit"));
		} else {
			viewHolder.mTextView0.setText(list.get(position).get("coupon_name"));
			viewHolder.mTextView1.setText("¥ "+list.get(position).get("coupon_price"));
			viewHolder.mTextView2.setText(list.get(position).get("coupon_start_time")+"-"+list.get(position).get("coupon_end_time"));
			viewHolder.mTextView3.setText(list.get(position).get("coupon_limit"));
		}
		return convertView;
	}

	static class ViewHolder {
		TextView mTextView0;
		TextView mTextView1;
		TextView mTextView2;
		TextView mTextView3;
	}
}
