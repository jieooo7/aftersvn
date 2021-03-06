package cn.icnt.dinners.adapter;

import java.util.List;
import java.util.Map;

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

import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.DetailsActivity;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.entity.Result;
import cn.icnt.dinners.http.MapPackage;


	public class SurroundingAdapter extends BaseAdapter {

		private static final String TAG = "LoaderAdapter";

		private ImageLoader mImageLoader;
		private Context mContext;
		private List<Map<String, String>> list;

		public SurroundingAdapter(Context context,
				List<Map<String, String>> list) {
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
						R.layout.activity_near_item, null);
				viewHolder = new ViewHolder();
				viewHolder.mTextView0 = (TextView) convertView
						.findViewById(R.id.tv_longitude);
				viewHolder.mTextView1 = (TextView) convertView
						.findViewById(R.id.tv_store);
				viewHolder.mTextView2 = (TextView) convertView
						.findViewById(R.id.tv_branch);
				viewHolder.mTextView3 = (TextView) convertView
						.findViewById(R.id.tv_speciality);

				viewHolder.mTextView5 = (TextView) convertView
						.findViewById(R.id.tv_address);
				viewHolder.mTextView6 = (TextView) convertView
						.findViewById(R.id.tv_phone);
				viewHolder.mImageView = (ImageView) convertView
						.findViewById(R.id.iv_maneul);
//				viewHolder.mLinear = (LinearLayout) convertView
//						.findViewById(R.id.ll_layout_Surround);
//				viewHolder.mLinear
//						.setOnClickListener(new MyListenerr(position));
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
			viewHolder.mTextView0.setText(list.get(position).get("mile") + ":");
			viewHolder.mTextView1.setText(list.get(position).get("name_store"));
			viewHolder.mTextView2.setText(list.get(position).get("store_str")
					+ ":");
			viewHolder.mTextView3.setText(list.get(position).get("food_name"));

			viewHolder.mTextView5
					.setText(list.get(position).get("address_str"));
			viewHolder.mTextView6.setText(list.get(position).get("phone_str"));
			// viewHolder.mTextView7.setText(list.get(position).get("company_id"));
			// viewHolder.mTextView8.setText(list.get(position).get("sub_company_id"));

			return convertView;
		}

		static class ViewHolder {
			TextView mTextView0;
			TextView mTextView1;
			TextView mTextView2;
			TextView mTextView3;
			TextView mTextView4;
			TextView mTextView5;
			TextView mTextView6;

			LinearLayout mLinear;
			ImageView mImageView;
		}

//		class MyListenerr implements OnClickListener {
//			private int position;
//
//			MyListenerr(int pos) {
//				position = pos;
//			}

//			@Override
//			public void onClick(View v) {
//				switch (v.getId()) {
//				case R.id.ll_layout_Surround:

//					Intent intent = new Intent(mContext, DetailsActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putSerializable(
//							"result",
//							new Result(list.get(position).get("address_str"),
//									position, position, list.get(position).get(
//											"information_str"), list.get(
//											position).get("name_store"), list
//											.get(position).get("name_url"),
//									list.get(position).get("phone_str"), list
//											.get(position).get("remind"), list
//											.get(position).get("store_str"),
//									position, list.get(position).get(
//											"description"), list.get(position)
//											.get("end_time"),
//									list.get(position).get("lager_picture"),
//									position));
//					intent.putExtras(bundle);
//					mContext.startActivity(intent);
//					break;

//				}
//
//			}
//
//		}
	}

