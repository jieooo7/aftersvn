/*
 * MycollectDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MycollectDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月3日 下午2:12:25
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.beans.OrderListBean;
import cn.icnt.dinners.beans.OrderListBean.OrderList;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.MD5;
import cn.icnt.dinners.utils.ToastUtil;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * cn.icnt.dinners.dinner.MycollectDetailActivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年7月3日 下午2:12:25
 */

public class MyOrderActivity extends Activity {

	@ViewInject(R.id.title_left_btn)
	private RelativeLayout title_left_btn;

	@ViewInject(R.id.title_center_text)
	private TextView title_center_text;

	@ViewInject(R.id.order_list)
	private ListView order_list;

	@ViewInject(R.id.order_all)
	private LinearLayout order_all;
	@ViewInject(R.id.order_all_text)
	private TextView order_all_text;
	@ViewInject(R.id.order_all_line)
	private ImageView order_all_line;

	@ViewInject(R.id.order_obligation)
	private LinearLayout order_obligation;
	@ViewInject(R.id.order_obligation_text)
	private TextView order_obligation_text;
	@ViewInject(R.id.order_obligation_line)
	private ImageView order_obligation_line;

	@ViewInject(R.id.order_evaluate)
	private LinearLayout order_evaluate;
	@ViewInject(R.id.order_evaluate_text)
	private TextView order_evaluate_text;
	@ViewInject(R.id.order_evaluate_line)
	private ImageView order_evaluate_line;

	private int curCheckId = R.id.order_all;
	private OrderAdapter adapter;
	private BitmapUtils butils;

	private int orderState = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder);
		ViewUtils.inject(this);
		title_center_text.setText("我的订单");
		initView();
		initData();

	}

	private void initView() {
		checkedState(curCheckId);
		butils = new BitmapUtils(MyOrderActivity.this);
	}

	private void initData() {
		Map<String, Object> headmap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<String, Object>();
		Map<String, Object> resmap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
//		MapPackage mp = new MapPackage();
//		'head'=>array(
//	            'uid'=>'31',
//	            'no'=>11111,
//	            'os'=>1,
//	            'version'=>"1.1.2",
//	            'key'=>"c02bc07f3f7ca113bab32bb334ea472d"
//	        ),
//	        'para'=>array(
//	            'order'=>1,
//	            'favorite_type'=>1,
//	            'company_id'=>1,
//	            'sub_company_id'=>1,
//	            'goods_id'=>128,
//	        ),
//	        'result'=>array(
//	            'start'=>0,
//	            'count'=>4,
//	        ),
		headmap.put("uid", "31");
		headmap.put("no", "11111");
		headmap.put("os", "1");
		headmap.put("version", "1.1.2");
		headmap.put("key","c02bc07f3f7ca113bab32bb334ea472d");
//		headmap.put("key", MD5.getMD5("3112341122112311.1.2"));
		paramap.put("order",1);
		paramap.put("favorite_type", 1);
		paramap.put("company_id", 1);
		paramap.put("sub_company_id", 1);
		paramap.put("goods_id", 128);
		resmap.put("start", 0);
		resmap.put("count", 4);
		map.put("head", headmap);
		map.put("para", paramap);
		map.put("result", resmap);
//		mp.setHead(this);
//		mp.setPara("start", "0");
//		mp.setPara("count", "10");
//		Map<String, Object> maps = mp.getMap();
		RequestParams params = GsonTools.GetParams(map);
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				"http://115.29.13.164/order_list.do?t=", params,
				new RequestCallBack<String>() {

					private List<OrderList> unpay;

					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("order", responseInfo.result);
						 Gson gson = new Gson();
						 OrderListBean json =
						 gson.fromJson(responseInfo.result,
						 OrderListBean.class);
						 if (json == null) {
						 } else if (!("10000".equals(json.head.code))) {
						 ToastUtil.show(getApplication(), json.head.msg);
						 } else {
						 if (json.result == null) {
						 } else {
						 List<OrderList> result = json.result;
						 if (orderState == 1) {
						 for (OrderList orderli : json.result) {
						 if (orderli.order_state == 1) {
						 unpay = new ArrayList<OrderListBean.OrderList>();
						 unpay.add(orderli);
						 }
						 }
						 adapter = new OrderAdapter(
						 MyOrderActivity.this, unpay);
						 adapter.notifyDataSetChanged();
						 } else if (orderState == 2) {
						 for (OrderList orderli : json.result) {
						 if (orderli.order_state == 2) {
						 unpay = new ArrayList<OrderListBean.OrderList>();
						 unpay.add(orderli);
						 }
						 }
						 adapter = new OrderAdapter(
						 MyOrderActivity.this, unpay);
						 adapter.notifyDataSetChanged();
						 }
						 adapter = new OrderAdapter(
						 MyOrderActivity.this, result);
						 order_list.setAdapter(adapter);
						 }
						 }
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});

	}

	/**
	 * 根据不同的button 事件为listview。setadapter 传入不同数据的list 而后notify
	 * 
	 * @param v
	 */
	@OnClick({ R.id.title_left_btn, R.id.order_all, R.id.order_obligation,
			R.id.order_evaluate })
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			break;
		case R.id.order_all:
			if (curCheckId == R.id.order_all) {
			} else {
				checkedState(R.id.order_all);
				curCheckId = R.id.order_all;
				orderState = 0;
				// adapter.notifyDataSetChanged();
			}
			break;
		case R.id.order_obligation:
			if (curCheckId == R.id.order_obligation) {
			} else {
				checkedState(R.id.order_obligation);
				curCheckId = R.id.order_obligation;
				orderState = 1;
				// adapter.notifyDataSetChanged();
			}
			break;
		case R.id.order_evaluate:
			if (curCheckId == R.id.order_evaluate) {
			} else {
				checkedState(R.id.order_evaluate);
				curCheckId = R.id.order_evaluate;
				orderState = 2;
				// adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	private void checkedState(int curCheckId2) {
		initChecked();
		switch (curCheckId2) {
		case R.id.order_all:
			order_all_text.setTextColor(getResources().getColor(
					R.color.tab_font));
			order_all_line.setVisibility(View.VISIBLE);
			break;
		case R.id.order_obligation:
			order_obligation_text.setTextColor(getResources().getColor(
					R.color.tab_font));
			order_obligation_line.setVisibility(View.VISIBLE);
			break;
		case R.id.order_evaluate:
			order_evaluate_text.setTextColor(getResources().getColor(
					R.color.tab_font));
			order_evaluate_line.setVisibility(View.VISIBLE);
			break;
		}
	}

	private void initChecked() {
		order_all_text.setTextColor(getResources().getColor(
				R.color.order_tab_text));
		order_obligation_text.setTextColor(getResources().getColor(
				R.color.order_tab_text));
		order_evaluate_text.setTextColor(getResources().getColor(
				R.color.order_tab_text));

		order_all_line.setVisibility(View.GONE);
		order_obligation_line.setVisibility(View.GONE);
		order_evaluate_line.setVisibility(View.GONE);
	}

	public class OrderAdapter extends BaseAdapter {
		public List<OrderList> lists = new ArrayList<OrderListBean.OrderList>();
		private LayoutInflater mInflater;

		public OrderAdapter(Context context, List<OrderList> list) {
			this.mInflater = LayoutInflater.from(context);
			this.lists = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		// ****************************************第二种方法，高手一般都用此种方法,具体原因，我还不清楚,有待研究

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			MyListener myListener = null;
			if (convertView == null) {
				holder = new ViewHolder();
				// 可以理解为从vlist获取view 之后把view返回给ListView
				myListener = new MyListener(position);
				convertView = mInflater.inflate(R.layout.order_listview_item,
						null);
				holder.order_store = (TextView) convertView
						.findViewById(R.id.order_store);
				holder.order_state = (TextView) convertView
						.findViewById(R.id.order_state);
				holder.order_info = (TextView) convertView
						.findViewById(R.id.order_info);
				holder.order_total_price = (TextView) convertView
						.findViewById(R.id.order_total_price);
				holder.order_discount = (TextView) convertView
						.findViewById(R.id.order_discount);
				holder.del_bucket_img = (ImageView) convertView
						.findViewById(R.id.del_bucket_img);
				holder.order_pay = (TextView) convertView
						.findViewById(R.id.order_pay);
				holder.order_price = (TextView) convertView
						.findViewById(R.id.order_price);
				holder.order_pay_online = (TextView) convertView
						.findViewById(R.id.order_pay_online);
				holder.order_img = (ImageView) convertView
						.findViewById(R.id.order_img);
				holder.order_title_line_one = (ImageView) convertView
						.findViewById(R.id.order_title_line_one);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.del_bucket_img.setTag(position);
			holder.order_pay.setTag(position);
			holder.order_pay_online.setTag(position);

			butils.display(holder.order_img,
					lists.get(position).goods_list.get(0).img_url);
			if (lists.get(position).order_state == 1) {
				holder.order_state.setText("订单未支付");
			} else if (lists.get(position).order_state == 2) {
				holder.order_state.setText("支付完成");
			} else {
				holder.order_state.setText("交易进行中");
			}
			if (position == 0) {
				holder.order_title_line_one.setVisibility(View.GONE);
			}
			holder.order_info.setText(lists.get(position).order_info);
			holder.order_store.setText(lists.get(position).order_store);
			holder.order_total_price
					.setText(lists.get(position).order_total_price);
			holder.order_discount.setText(lists.get(position).order_discount);
			holder.order_price.setText(lists.get(position).order_price);
			// 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
			holder.del_bucket_img.setOnClickListener(myListener);
			holder.order_pay.setOnClickListener(myListener);
			holder.order_pay_online.setOnClickListener(myListener);
			// holder.viewBtn.setOnClickListener(MyListener(position));

			return convertView;
		}

		private class MyListener implements OnClickListener {
			int mPosition;

			public MyListener(int inPosition) {
				mPosition = inPosition;
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(ListViewActivity.this, title[mPosition],
				// Toast.LENGTH_SHORT).show();
				switch (v.getId()) {
				case R.id.order_pay:
					ToastUtil.show(getApplication(), "R.id.order_pay"+mPosition);
					break;
				case R.id.order_pay_online:
					ToastUtil.show(getApplication(), "order_pay_online"+mPosition);
					break;
				case R.id.del_bucket_img:
					ToastUtil.show(getApplication(), "R.id.del_bucket_img"+mPosition);
					break;
				}
				
			}
		}

		public final class ViewHolder {
			public TextView order_store;
			public TextView order_state;
			public TextView order_info;
			public TextView order_total_price;
			public TextView order_discount;
			public TextView order_price;
			public ImageView del_bucket_img;
			public TextView order_pay;
			public TextView order_pay_online;
			public ImageView order_img;
			public ImageView order_title_line_one;
		}

	}

}
