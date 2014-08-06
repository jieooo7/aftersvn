package cn.icnt.dinners.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.icnt.dinners.adapter.FragmentCouponAdapter.ViewHolder;
import cn.icnt.dinners.adapter.FragmentDishAdapter.MyListenerr;
import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.DetailaActivity;
import cn.icnt.dinners.dinner.DetailcActivity;
import cn.icnt.dinners.dinner.DetailsActivity;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.entity.Dishes;
import cn.icnt.dinners.entity.Present;
import cn.icnt.dinners.entity.Store;
import cn.icnt.dinners.http.MapPackage;

public class FragmentResAdapter extends BaseAdapter {
	private static final String TAG = "LoaderAdapter";

	private ImageLoader mImageLoader;
	private Context mContext;
	private List<Map<String, String>> list;

	public FragmentResAdapter(Context context, List<Map<String, String>> list) {
		this.list = list;
		this.mContext = context;
		mImageLoader = new ImageLoader(context);
	}

	public void setList(List<Map<String, String>> lists, boolean loadMore) {
		if (!loadMore) {
			list.clear();
		}
		if (list != null) {
			list.addAll(lists);
		}
		this.notifyDataSetChanged();
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
					R.layout.activity_list_store, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView0 = (TextView) convertView
					.findViewById(R.id.list_tv_storea);
			viewHolder.mTextView1 = (TextView) convertView
					.findViewById(R.id.liset_tv_neara);

			viewHolder.mTextView3 = (TextView) convertView
					.findViewById(R.id.list_tv_Informationa);
			viewHolder.mTextView4 = (TextView) convertView
					.findViewById(R.id.list_tv_introducea);
			viewHolder.mTextView5 = (TextView) convertView
					.findViewById(R.id.list_tv_endTimea);
			viewHolder.mTextView6 = (TextView) convertView
					.findViewById(R.id.list_tv_sharea);
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.icon_iva);
			viewHolder.mLinear = (LinearLayout) convertView
					.findViewById(R.id.list_ll_layouta);
			viewHolder.mLinear.setOnClickListener(new MyListenerr(position));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.mImageView.setImageResource(R.drawable.replace);
		DebugUtil.i("test....result",
				"44444----" + list.get(position).get("name_url"));

		mImageLoader.DisplayImage(
				MapPackage.PATH + list.get(position).get("name_url"),
				viewHolder.mImageView, false);
		viewHolder.mTextView0.setText(list.get(position).get("name_store")
				+ ":");
		viewHolder.mTextView1.setText(list.get(position).get("store_str"));

		viewHolder.mTextView3.setText(list.get(position).get("contact_tel")
				+ ":");
		viewHolder.mTextView4.setText(list.get(position).get("description"));
		viewHolder.mTextView5.setText(list.get(position).get("food_name"));

		viewHolder.mTextView6.setText(list.get(position).get("share_count"));
		return convertView;
	}

	static class ViewHolder {
		TextView mTextView0;
		TextView mTextView1;

		TextView mTextView3;
		TextView mTextView4;
		TextView mTextView5;
		TextView mTextView6;

		LinearLayout mLinear;
		ImageView mImageView;
	}

	class MyListenerr implements OnClickListener {
		private int position;

		MyListenerr(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.list_ll_layouta:
				Intent intent = new Intent(mContext, DetailaActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						"result",
						new Store(position, position, list.get(position).get("contact_tel"), list.get(position).get(
										"description"), list.get(position).get(
												"end_time"), position, list.get(
																position).get("food_name"), list.get(
																		position).get("lager_picture"),  list.get(
																				position).get("name_store"), list.get(
																						position).get("name_url"), list.get(
																								position).get("remark"),  list.get(
																										position).get("remind"), position, list.get(
																														position).get("start_time"), list.get(position).get("store_str"), position));
//						new Store(list.get(position).get("comment_num"),
//								position, list.get(position).get(
//						"contact_tel"), list.get(position).get(
//										"description"), list.get(position).get(
//										"end_time"),  list.get(position).get(
//						"favorite"), list.get(
//										position).get("food_name"), list.get(
//										position).get("lager_picture"), list.get(
//										position).get("name_store"), list.get(
//										position).get("name_url"), list.get(
//										position).get("remark"), list.get(
//										position).get("remind"), list.get(
//										position).get("share_count"), list.get(
//										position).get("start_time"), position,
//								list.get(position).get("store_str"), position,
//								position));
				
				intent.putExtras(bundle);
				mContext.startActivity(intent);
				break;

			}

		}
	}
}