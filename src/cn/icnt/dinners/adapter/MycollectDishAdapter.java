/*
 * MycollectDishAdapter.java
 * classes : cn.icnt.dinners.adapter.MycollectDishAdapter
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月18日 下午2:44:06
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
import cn.icnt.dinners.adapter.MycollectAdapter.ViewHolder;
import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.http.MapPackage;

/**
 * cn.icnt.dinners.adapter.MycollectDishAdapter
 * @author Andrew Lee <br/>
 * create at 2014年7月18日 下午2:44:06
 */
public class MycollectDishAdapter extends BaseAdapter {

	private static final String TAG = "LoaderAdapter";
	private boolean mBusy = false;

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}

	private ImageLoader mImageLoader;
	private Context mContext;
	private List<Map<String, String>> list;

	public MycollectDishAdapter(Context context, List<Map<String, String>> list) {
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
					R.layout.mycollect_list_item_dish, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView0 = (TextView) convertView
					.findViewById(R.id.mycollect_item_res_name);
			viewHolder.mTextView1 = (TextView) convertView
					.findViewById(R.id.mycollect_item_dish_name);
			viewHolder.mTextView2 = (TextView) convertView
					.findViewById(R.id.mycollect_item_dish_tv2);
			viewHolder.mTextView3 = (TextView) convertView
					.findViewById(R.id.mycollect_item_dish_tv3);
			viewHolder.mTextView4 = (TextView) convertView
					.findViewById(R.id.mycollect_item_dish_tv4);
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.mycollect_item_dish_iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.mImageView.setImageResource(R.drawable.replace);
		DebugUtil.i("test....result",
				"44444----" + list.get(position).get("img_url"));
		if (!mBusy) {
			mImageLoader.DisplayImage(
					MapPackage.PATH + list.get(position).get("name_url"),
					viewHolder.mImageView, false);
			viewHolder.mTextView0.setText(list.get(position).get("name_store"));
			viewHolder.mTextView1.setText(list.get(position).get("food_str")+":");
			viewHolder.mTextView2.setText(list.get(position).get("information_str"));
			viewHolder.mTextView3.setText(list.get(position).get("description"));
			viewHolder.mTextView4.setText(list.get(position).get("gaving_str"));
		} else {
			mImageLoader.DisplayImage(
					MapPackage.PATH + list.get(position).get("name_url"),
					viewHolder.mImageView, false);
			viewHolder.mTextView0.setText(list.get(position).get("name_store"));
			viewHolder.mTextView1.setText(list.get(position).get("food_str")+":");
			viewHolder.mTextView2.setText(list.get(position).get("information_str"));
			viewHolder.mTextView3.setText(list.get(position).get("description"));
			viewHolder.mTextView4.setText(list.get(position).get("gaving_str"));
		}
		return convertView;
	}

	static class ViewHolder {
		TextView mTextView0;
		TextView mTextView1;
		TextView mTextView2;
		TextView mTextView3;
		TextView mTextView4;
		ImageView mImageView;
	}
}