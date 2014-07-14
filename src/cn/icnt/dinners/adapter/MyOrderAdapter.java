/*
 * MycollectAdapter.java
 * classes : cn.icnt.dinners.adapter.MycollectAdapter
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月8日 下午2:21:44
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
import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.R;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

/**
 * cn.icnt.dinners.adapter.MycollectAdapter
 * @author Andrew Lee <br/>
 * create at 2014年7月8日 下午2:21:44
 */
public class MyOrderAdapter extends BaseAdapter {



	private ImageLoader mImageLoader;
	private Context mContext;
	private List<Map<String, String>> list;
	private BitmapUtils utils;
	private BitmapDisplayConfig config;

	public MyOrderAdapter(Context context, List<Map<String, String>> list) {
		this.list = list;
		this.mContext = context;
		utils = new BitmapUtils(context);  
	    config = new BitmapDisplayConfig();  
	    config.setLoadingDrawable(context.getResources().getDrawable(R.drawable.replace));  
	    config.setLoadFailedDrawable(context.getResources().getDrawable(R.drawable.replace));  
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
					R.layout.order_listview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.order_store = (TextView) convertView
					.findViewById(R.id.order_store);
			viewHolder.order_state = (TextView) convertView
					.findViewById(R.id.order_state);
			viewHolder.order_info = (TextView) convertView
					.findViewById(R.id.order_info);
			viewHolder.order_total_price = (TextView) convertView
					.findViewById(R.id.order_total_price);
			viewHolder.order_discount = (TextView) convertView
					.findViewById(R.id.order_discount);
			viewHolder.order_price = (TextView) convertView
					.findViewById(R.id.order_price);
			viewHolder.order_img = (ImageView) convertView
					.findViewById(R.id.order_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.order_img.setImageResource(R.drawable.replace);
//		DebugUtil.i("test....result",
//				"44444----" + list.get(position).get("img_url"));
//	
		return convertView;
	}

	static class ViewHolder {
		TextView order_store;
		TextView order_state;
		TextView order_info;
		TextView order_total_price;
		TextView order_discount;
		TextView order_price;
		ImageView order_img;
	}
}