package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.beans.OrderDetailBean;
import cn.icnt.dinners.beans.OrderDetailBean.OrderDetail;
import cn.icnt.dinners.beans.OrderDetailBean.OrderDetail.OrderListItem;
import cn.icnt.dinners.beans.OrderListBean;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.ToastUtil;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ShowOrderDetailActivity extends Activity {
    @ViewInject(R.id.title_left_btn)
    private RelativeLayout title_left_btn;
    @ViewInject(R.id.title_left_btn_img)
    private ImageView title_left_btn_img;
    @ViewInject(R.id.title_center_text)
    private TextView title_center_text;

    @ViewInject(R.id.gridview)
    private GridView gridview;
    @ViewInject(R.id.order_detail_info)
    private TextView order_detail_info;// 订单详情
    @ViewInject(R.id.order_total_price)
    private TextView order_total_price;// 商品总价
    @ViewInject(R.id.order_discount)
    private TextView order_discount;// 优惠金额
    @ViewInject(R.id.order_price)
    private TextView order_price;// 折后价格
    @ViewInject(R.id.control_order)
    private RelativeLayout control_order;// 控制“删除、支付”行
    @ViewInject(R.id.del_bucket_img)
    private RelativeLayout del_bucket_img;// 删除
    @ViewInject(R.id.order_pay_online)
    private RelativeLayout order_pay_online;// 线上支付
    private String order_id_str;

    // @ViewInject(R.id.orders_evaluates)
    // private RelativeLayout orders_evaluates;// 订单评价行
    // @ViewInject(R.id.order_evaluate)
    // private Button order_evaluate;// 订单评价

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.order_detail);
	order_id_str = (String) getIntent().getExtras().get("OrderInfo");
	initView();
    }

    private void initView() {
	ViewUtils.inject(this);

	title_center_text.setText("订单详情");
	initData();
	// GridAdapter adapter = new GridAdapter(this, null);
	// gridview.setAdapter(adapter);

    };

    @OnClick({ R.id.del_bucket_img, R.id.title_left_btn, R.id.order_pay_online })
    public void clickMethod(View v) {
	switch (v.getId()) {
	case R.id.title_left_btn:
	    finish();
	    break;
	case R.id.del_bucket_img:
	    // ToastUtil.show(ShowOrderDetailActivity.this, "删除订单");
	    deleteOrder();
	    finish();
	    break;
	// case R.id.order_evaluate:
	// ToastUtil.show(ShowOrderDetailActivity.this, "评论订单");
	// showProgressDialog(ShowOrderDetailActivity.this, 9);
	// // finish();
	// break;
	case R.id.order_pay_online:
	    ToastUtil.show(ShowOrderDetailActivity.this, "支付订单");
	    finish();
	    break;

	}
    }

    private OrderDetail orderInfo = null;

    private void initData() {
	MapPackage mp = new MapPackage();
	mp.setHead(this);
	mp.setPara("order_id", order_id_str);
	Map<String, Object> map = mp.getssMap();
	RequestParams params = GsonTools.GetParams(map);
	HttpUtils http = new HttpUtils();
	http.send(HttpRequest.HttpMethod.POST, Container.ORDERDETAIL, params,
		new RequestCallBack<String>() {
		    @Override
		    public void onStart() {
			ToastUtil.showProgressDialog(ShowOrderDetailActivity.this);
		    }

		    @Override
		    public void onLoading(long total, long current, boolean isUploading) {
		    }

		    @Override
		    public void onSuccess(ResponseInfo<String> responseInfo) {
			Log.i("order_detail", responseInfo.result);
			ToastUtil.closeProgressDialog();
			Gson gson = new Gson();
			OrderDetailBean orderDetails = gson.fromJson(responseInfo.result,
				OrderDetailBean.class);
			if (orderDetails != null) {
			    if (StringUtils.equals("10000", orderDetails.head.code)) {
				orderInfo = orderDetails.result;
				List<OrderListItem> goods_lists = orderInfo.goods_list;
				if (goods_lists != null) {
				    GridAdapter adapter = new GridAdapter(
					    ShowOrderDetailActivity.this, goods_lists);
				    gridview.setAdapter(adapter);
				}
				if (orderInfo.order_state == 1) {
				    control_order.setVisibility(View.VISIBLE);
				}
				if (orderInfo != null) {
				    order_detail_info.setText(orderInfo.order_info
					    .toString());
				    order_total_price.setText("￥"+orderInfo.order_total_price
					    .toString());
				    order_discount.setText(orderInfo.order_discount
					    .toString());
				    order_price.setText("￥"+orderInfo.order_price.toString());
				}
				ToastUtil.closeProgressDialog();
			    }
			}
		    }

		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});
    }

    private class GridAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	List<OrderListItem> list;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public GridAdapter(Context context, List<OrderListItem> list) {
	    this.inflater = LayoutInflater.from(context);
	    this.list = list;
	    imageLoader = ImageLoader.getInstance();
	    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		    context).build();
	    imageLoader.init(config);
	    options = new DisplayImageOptions.Builder()
		    .showStubImage(R.drawable.ic_launcher)
		    .showImageOnFail(R.drawable.ic_launcher)
		    .showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory(true)
		    .cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(300))
		    .imageScaleType(ImageScaleType.NONE).build();
	}

	@Override
	public int getCount() {
	    return list.size();
	    // return list.size();
	}

	@Override
	public Object getItem(int position) {
	    return position;
	}

	@Override
	public long getItemId(int position) {
	    return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    if (convertView == null) {
		holder = new ViewHolder();
		convertView = this.inflater.inflate(R.layout.orderdetails_gridview_item,
			null);
		holder.orderdetails_img = (ImageView) convertView
			.findViewById(R.id.orderdetails_img);
		convertView.setTag(holder);
	    } else {
		holder = (ViewHolder) convertView.getTag();
	    }
	    imageLoader.displayImage(Container.URL + list.get(position).img_url,
		    holder.orderdetails_img, options);
	    return convertView;
	}

	private class ViewHolder {
	    ImageView orderdetails_img;
	}

    }

    private void deleteOrder() {
	MapPackage mp = new MapPackage();
	mp.setHead(this);
	mp.setPara("order_id", order_id_str);
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
			// Log.e("order11", arg0.result);

			Gson gson = new Gson();
			OrderListBean fromJson = gson.fromJson(arg0.result,
				OrderListBean.class);
			if (fromJson == null) {
			} else if ("10000".equals(fromJson.head.code)) {
			    ToastUtil.show(ShowOrderDetailActivity.this,
				    fromJson.head.msg);
			    finish();
			} else {
			    ToastUtil.show(ShowOrderDetailActivity.this,
				    fromJson.head.msg);
			}
		    }

		});
    }

    // private static EditText order_msg_edit;
    //
    // private static RelativeLayout order_msg_send;
    // private Dialog mDeleteDialog;
    // public void showProgressDialog(Context mContext, int mPosition) {
    //
    // mDeleteDialog = new Dialog(mContext, R.style.order_dialog_msg);
    // WindowManager.LayoutParams params = mDeleteDialog.getWindow()
    // .getAttributes();
    // params.width = 200;
    // params.height = 200;
    // mDeleteDialog.getWindow().setAttributes(params);
    // LayoutInflater inflater = (LayoutInflater) mContext
    // .getSystemService(LAYOUT_INFLATER_SERVICE);
    // View layout = inflater.inflate(R.layout.order_evaluate_dialog, null);
    // mDeleteDialog.setContentView(layout);
    // order_msg_edit = (EditText) layout.findViewById(R.id.order_msg_edit);
    // order_msg_send = (RelativeLayout) layout
    // .findViewById(R.id.order_msg_send);
    // order_msg_send.setOnClickListener(new OnClickListener() {
    // @Override
    // public void onClick(View v) {
    // // switch (v.getId()) {
    // // case R.id.order_msg_send:
    // String msg = order_msg_edit.getText().toString();
    // Log.i("order", msg +"评论成功");
    // // SendMsg(positions);
    // // break;
    // ToastUtil.show(ShowOrderDetailActivity.this, msg +"评论成功");
    // // }
    // mDeleteDialog.dismiss();
    // }
    // });
    // mDeleteDialog.show();
    // }
}
