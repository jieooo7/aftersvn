package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.beans.OrderListBean;
import cn.icnt.dinners.beans.OrderListBean.OrderList;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.ToastUtil;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
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
	// @ViewInject(R.id.order_list_nosend)
	// private ListView order_list_nosend;
	// @ViewInject(R.id.order_list_nopay)
	// private ListView order_list_nopay;

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
	private OrderAdapter adapter1;
	private OrderAdapter adapter2;
	private BitmapUtils butils;

	// private int orderState = 0;
	private OrderListBean json;

	private List<OrderList> resultList; // 原始
	private List<OrderList> newlist;// 条件
	private List<OrderList> newlist1;// 条件1
	private List<OrderList> newlist2;// 条件2

	private static EditText order_msg_edit;

	private static RelativeLayout order_msg_send;

	public static int num = 0;

	private Dialog mDeleteDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder);
		ViewUtils.inject(this);
		title_center_text.setText("我的订单");
		initView();
		initData();
		// order_list.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// ToastUtil.show(MyOrderActivity.this, position+"####");
		// }
		// });
	}

	private void initView() {

		checkedState(curCheckId);
	}

	private void initData() {
		MapPackage mp = new MapPackage();
		mp.setHead(this);
		mp.setRes("start", 0);
		mp.setRes("count", 10);
		Map<String, Object> maps = mp.getMap();
		RequestParams params = GsonTools.GetParams(maps);

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, Container.MYORDERLIST, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						ToastUtil.showProgressDialog(MyOrderActivity.this);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						ToastUtil.closeProgressDialog();
						Log.i("order", responseInfo.result);
						Gson gson = new Gson();
						json = gson.fromJson(responseInfo.result,
								OrderListBean.class);
						if (json == null) {
						} else if (!("10000".equals(json.head.code))) {
							ToastUtil.show(getApplication(), json.head.msg);
						} else {
							if (json.result == null) {
							} else {
								resultList = json.result;
								newlist = json.result;
								adapter = new OrderAdapter(
										MyOrderActivity.this, newlist);
								order_list.setAdapter(adapter);
								// newlist = resultList;
								// order_list.setVerticalScrollBarEnabled(true);
								// order_list.setOnItemClickListener(new
								// OnItemClickListener() {
								// @Override
								// public void onItemClick(
								// AdapterView<?> arg0, View arg1,
								// int arg2, long arg3) {
								// }
								// });
							}
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});

	}

	// @ViewInject(R.id.order_list)
	// private ListView order_list;
	// @ViewInject(R.id.order_list_nosend)
	// private ListView order_list_nosend;
	// @ViewInject(R.id.order_list_nopay)
	// private ListView order_list_nopay;
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
				// // adapter= new OrderAdapter(
				// // MyOrderActivity.this, newlist);
				// // order_list.setAdapter(adapter);
				// // order_list.setVisibility(View.VISIBLE);
				// // order_list_nosend.setVisibility(View.GONE);
				// // order_list_nopay.setVisibility(View.GONE);
				// // orderState = 0;
				// newlist = resultList;
				// // adapter = new OrderAdapter(MyOrderActivity.this, result);
				// // order_list.setAdapter(adapter);
				// adapter.notifyDataSetChanged();
			}
			break;
		case R.id.order_obligation:
			if (curCheckId == R.id.order_obligation) {
			} else {
				checkedState(R.id.order_obligation);
				curCheckId = R.id.order_obligation;
				// orderState = 1;
				// newlist.clear();
				// for (OrderList ol : resultList) {
				// if (ol.order_state == 2) {
				// newlist.add(ol);
				// }
				// }
				// adapter.notifyDataSetChanged();
				// adapter1 = new OrderAdapter(MyOrderActivity.this, newlist1);
				// order_list.setAdapter(adapter1);
				// adapter.notifyDataSetChanged();
				// unpay.clear();
				// for (OrderList orderli : json.result) {
				// if (orderli.order_state == 1) {
				// unpay.add(orderli);
				// }
				// }
				// adapter = new OrderAdapter(MyOrderActivity.this, unpay);
				// adapter.notifyDataSetChanged();
				// order_list.setAdapter(adapter);
				// adapter.notifyDataSetChanged();
			}
			break;
		case R.id.order_evaluate: // 待支付
			if (curCheckId == R.id.order_evaluate) {
			} else {
				checkedState(R.id.order_evaluate);
				curCheckId = R.id.order_evaluate;
				// // orderState = 2;
				// newlist.clear();
				// for (OrderList ol : resultList) {
				// newlist.add(ol);
				// }
				// adapter.notifyDataSetChanged();
				// adapter2 = new OrderAdapter(MyOrderActivity.this, newlist2);
				// order_list.setAdapter(adapter2);
				// OrderAdapter orderAdapter2 = new OrderAdapter(
				// MyOrderActivity.this, newlist);
				//
				// order_list_nopay.setAdapter(orderAdapter2);

				// unpay.clear();
				// for (OrderList orderli : json.result) {
				// if (orderli.order_state == 3) {
				//
				// unpay.add(orderli);
				// }
				// }
				// adapter = new OrderAdapter(MyOrderActivity.this, unpay);
				// // adapter.notifyDataSetChanged();
				// order_list.setAdapter(adapter);
				// // order_list.notify();
				// order_list.notify();
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
			order_list.setVisibility(View.VISIBLE);
			break;
		case R.id.order_obligation:
			order_obligation_text.setTextColor(getResources().getColor(
					R.color.tab_font));
			order_obligation_line.setVisibility(View.VISIBLE);
			// order_list_nosend.setVisibility(View.VISIBLE);
			break;
		case R.id.order_evaluate:
			order_evaluate_text.setTextColor(getResources().getColor(
					R.color.tab_font));
			order_evaluate_line.setVisibility(View.VISIBLE);
			// order_list_nopay.setVisibility(View.VISIBLE);
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

		// order_list.setVisibility(View.GONE);
		// order_list_nosend.setVisibility(View.GONE);
		// order_list_nopay.setVisibility(View.GONE);
	}

	public class OrderAdapter extends BaseAdapter {
		public List<OrderList> lists = new ArrayList<OrderListBean.OrderList>();
		private LayoutInflater mInflater;
		private BitmapUtils utils;
		private BitmapDisplayConfig img_config;
		private Context mContext;

		public OrderAdapter(Context context, List<OrderList> list) {
			this.mInflater = LayoutInflater.from(context);
			this.lists = list;
			mContext = context;
			utils = new BitmapUtils(context);
			img_config = new BitmapDisplayConfig();
			img_config.setLoadingDrawable(getResources().getDrawable(
					R.drawable.replace));
			img_config.setLoadFailedDrawable(getResources().getDrawable(
					R.drawable.replace));
			img_config.setBitmapConfig(Bitmap.Config.RGB_565);

		}

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			MyListener myListener = null;
			myListener = new MyListener(position);
			if (convertView == null) {
				holder = new ViewHolder();
				// 可以理解为从vlist获取view 之后把view返回给ListView
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
				holder.del_bucket_img = (RelativeLayout) convertView
						.findViewById(R.id.del_bucket_img);
				holder.order_price = (TextView) convertView
						.findViewById(R.id.order_price);
				holder.order_pay_online = (TextView) convertView
						.findViewById(R.id.order_pay_online);
				holder.order_img = (ImageView) convertView
						.findViewById(R.id.order_img);
				holder.order_title_line_one = (ImageView) convertView
						.findViewById(R.id.order_title_line_one);
				holder.order_evaluate = (Button) convertView
						.findViewById(R.id.order_evaluate);
				holder.control_order = (RelativeLayout) convertView
						.findViewById(R.id.control_order);
				holder.orders_evaluates = (RelativeLayout) convertView
						.findViewById(R.id.orders_evaluates);
				holder.order_desc = (RelativeLayout) convertView
						.findViewById(R.id.order_desc);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// butils.display(holder.order_img,
			// lists.get(position).goods_list.get(0).img_url);
			if (lists.get(position).order_state == 1) {
				holder.order_state.setText("订单未支付");
				holder.control_order.setVisibility(View.VISIBLE);
				holder.orders_evaluates.setVisibility(View.GONE);
			} else if (lists.get(position).order_state == 2) {
				holder.order_state.setText("支付完成");
				holder.orders_evaluates.setVisibility(View.VISIBLE);
				holder.control_order.setVisibility(View.GONE);
			} else {
				holder.order_state.setText("交易进行中");
				holder.order_state.setTextColor(getResources().getColor(
						R.color.tab_font));
				holder.orders_evaluates.setVisibility(View.GONE);
				holder.control_order.setVisibility(View.VISIBLE);
			}
			if (position == 0) {
				holder.order_title_line_one.setVisibility(View.GONE);
			} else {
				holder.order_title_line_one.setVisibility(View.VISIBLE);
			}
			holder.order_info.setText(lists.get(position).order_info);
			holder.order_store.setText(lists.get(position).order_store);
			holder.order_total_price.setText("￥"
					+ lists.get(position).order_total_price);
			holder.order_discount.setText(lists.get(position).order_discount);
			holder.order_price.setText("￥" + lists.get(position).order_price);
			utils.display(holder.order_img, Container.URL
					+ lists.get(position).img_url, img_config);
			// 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
			holder.del_bucket_img.setTag(position);
			holder.order_pay_online.setTag(position);
			holder.order_evaluate.setTag(position);
			holder.order_desc.setTag(position);

			holder.del_bucket_img.setOnClickListener(myListener);
			holder.order_pay_online.setOnClickListener(myListener);
			holder.order_evaluate.setOnClickListener(myListener);
			holder.order_desc.setOnClickListener(myListener);
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
				int position = Integer.parseInt(v.getTag().toString());
				switch (v.getId()) {
				case R.id.order_pay_online: // 在线支付
					ToastUtil.show(MyOrderActivity.this, "即将上线，敬请期待！");
					break;
				case R.id.del_bucket_img: // 订单删除
					deleteOrder(mPosition);
					break;
				case R.id.order_evaluate: // 订单评价
					ToastUtil.show(MyOrderActivity.this, "R.id.order_evaluate"
							+ position);
					showProgressDialog(mContext, mPosition);
					break;
				case R.id.order_desc: // item onclick
					ToastUtil.show(MyOrderActivity.this, "R.id.order_desc"
							+ position);
					Intent i = new Intent();
					i.setClass(MyOrderActivity.this, ShowDetailActivity.class);
					startActivity(i);
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
			public RelativeLayout del_bucket_img;
			public TextView order_pay_online;
			public ImageView order_img;
			public ImageView order_title_line_one;
			public Button order_evaluate;
			public RelativeLayout control_order;
			public RelativeLayout orders_evaluates;
			public RelativeLayout order_desc;
		}

	}

	private void deleteOrder(int mPosition2) {
		num = mPosition2;
		MapPackage mp = new MapPackage();
		mp.setHead(this);
		mp.setPara("order_info_id", newlist.get(mPosition2).order_info_id);
		Map<String, Object> maps = mp.getMap();
		RequestParams params = GsonTools.GetParams(maps);

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, Container.DELETEORDER, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Log.e("order11", arg0.result);

						Gson gson = new Gson();
						OrderListBean fromJson = gson.fromJson(arg0.result,
								OrderListBean.class);
						if (fromJson == null) {
						} else if ("10000".equals(fromJson.head.code)) {
							ToastUtil.show(MyOrderActivity.this,
									fromJson.head.msg);
							newlist.remove(num);
							adapter.notifyDataSetChanged();
							// Intent ii = new Intent();
							// ii.setClass(MyOrderActivity.this,
							// MyOrderActivity.class);
							// startActivity(ii);
							// MyOrderActivity.this.finish();
						} else {
						}
					}

				});

	}

	public void showProgressDialog(Context mContext, int mPosition) {

		mDeleteDialog = new Dialog(mContext, R.style.order_dialog_msg);
		WindowManager.LayoutParams params = mDeleteDialog.getWindow()
				.getAttributes();
		params.width = 200;
		params.height = 200;
		mDeleteDialog.getWindow().setAttributes(params);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.order_evaluate_dialog, null);
		mDeleteDialog.setContentView(layout);
		order_msg_edit = (EditText) layout.findViewById(R.id.order_msg_edit);
		order_msg_send = (RelativeLayout) layout
				.findViewById(R.id.order_msg_send);
		order_msg_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// switch (v.getId()) {
				// case R.id.order_msg_send:
				String msg = order_msg_edit.getText().toString();
				Log.i("order", "“"+ msg +"”评价成功");
				
				// SendMsg(positions);
				// break;
				// }
				mDeleteDialog.dismiss();
				}
		});
		mDeleteDialog.show();

		// mDeleteDialog.getWindow().setGravity(Gravity.CENTER);
		// AlertDialog.Builder builder;
		// AlertDialog alertDialog;
		// LayoutInflater inflater = (LayoutInflater)
		// mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		// View layout = inflater.inflate(R.layout.order_evaluate_dialog,
		// null);
		// EditText order_msg_edit = (EditText)
		// layout.findViewById(R.id.order_msg_edit);
		// TextView order_msg_send = (TextView)
		// layout.findViewById(R.id.order_msg_send);
		// new AlertDialog.Builder(mContext,R.style.order_dialog_msg);
		// builder = new AlertDialog.Builder(mContext,R.style.order_dialog_msg);
		// order_msg_send.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.i("order", "msg sendddddddddddd");
		// }
		// });
		// builder.setView(layout);
		// // alertDialog = builder.create();
		// builder.create().show();
		// alertDialog.show();
		// dialog.setIcon(R.drawable.ic_launcher);
		// dialog.setTitle(R.string.app_name);
		// dialog.setMessage("请等候，数据加载中……");
		// dialog.show();
	}

}
