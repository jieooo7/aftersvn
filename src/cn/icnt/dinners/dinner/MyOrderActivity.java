package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;

public class MyOrderActivity extends Activity {

    @ViewInject(R.id.title_left_btn)
    private RelativeLayout title_left_btn;

    @ViewInject(R.id.title_center_text)
    private TextView title_center_text;
    ViewPager mViewPager;
    private LinearLayout linearLayout1;
    private TextView textView1, textView2, textView3;
    // private int currIndex = 0;// 当前页卡编号
    // private int textViewW = 0;// 页卡标题的宽度
    private List<View> listViews;
    private Resources resources;
    private View view1, view2, view3;
    // private LinearLayout layout1, layout2, layout3;
    private ListView listview1, listview2, listview3;
    // private SimpleAdapter list1, list2, list3;

    private List<OrderList> listInfo;
    private List<OrderList> unpayList;
    private List<OrderList> payedList;
    /* mList是用来存放要显示的数据 */
    // private List<HashMap<String, Object>> mList = new
    // ArrayList<HashMap<String, Object>>();

    private OrderAdapter allAdapter;

    private OrderAdapter unpayAdapter;
    private OrderAdapter payedAdapter;

    private List<OrderList> clickList;

    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.myorder);
	resources = this.getResources();
	ViewUtils.inject(this);
	title_center_text.setText("我的订单");
	initData();

    }

    private void initControl() {
	linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
	mViewPager = (ViewPager) findViewById(R.id.pvr_user_pager);
	mViewPager.setOffscreenPageLimit(2);/* 预加载页面 */
    }

    /* 初始化ViewPager */
    private void initViewPager() {
	listViews = new ArrayList<View>();
	LayoutInflater mInflater = getLayoutInflater();
	view1 = mInflater.inflate(R.layout.my_order_viewpager, null);
	view2 = mInflater.inflate(R.layout.my_order_viewpager, null);
	view3 = mInflater.inflate(R.layout.my_order_viewpager, null);
	listViews.add(view1);
	listViews.add(view2);
	listViews.add(view3);
	mViewPager.setAdapter(new MyPagerAdapter(listViews));
	mViewPager.setCurrentItem(0);
	mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	initList();
    }

    /* 初始化各个页卡列表 */
    private void initList() {
	if (listInfo != null) {
	    unpayList = getUnpayList(listInfo, 1);
	    payedList = getUnpayList(listInfo, 2);
	    allAdapter = new OrderAdapter(MyOrderActivity.this, listInfo);
	    // /* 页卡数据 */
	    unpayAdapter = new OrderAdapter(MyOrderActivity.this, unpayList);
	    payedAdapter = new OrderAdapter(MyOrderActivity.this, payedList);
	}
	listview1 = (ListView) view1.findViewById(R.id.mylistview);
	listview2 = (ListView) view2.findViewById(R.id.mylistview);
	listview3 = (ListView) view3.findViewById(R.id.mylistview);
	//
	listview1.setAdapter(allAdapter);
	listview2.setAdapter(unpayAdapter);
	listview3.setAdapter(payedAdapter);
    }

    /* 返回列表数据 */
    private List<OrderList> getUnpayList(List<OrderList> listInfo2, int i) {
	int is = i;
	List<OrderList> list = new ArrayList<OrderList>();
	for (OrderList orderInfo : listInfo2) {
	    if (orderInfo.order_state == is) {
		list.add(orderInfo);
	    }
	}
	return list;
    }

    /* 初始化页卡标题 */
    private void InitTextView() {
	textView1 = (TextView) findViewById(R.id.text1);
	textView2 = (TextView) findViewById(R.id.text2);
	textView3 = (TextView) findViewById(R.id.text3);

	textView1.setOnClickListener(new MyOnClickListener(0));
	textView2.setOnClickListener(new MyOnClickListener(1));
	textView3.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     * ViewPager适配器
     */
    public class MyPagerAdapter extends PagerAdapter {
	public List<View> mListViews;

	public MyPagerAdapter(List<View> mListViews) {
	    this.mListViews = mListViews;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
	    ((ViewPager) arg0).removeView(mListViews.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
	    // return Integer.MAX_VALUE;
	    return mListViews.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
	    ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
	    return mListViews.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
	    return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
	    return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}
    }

    /* 页卡切换监听 */
    public class MyOnPageChangeListener implements OnPageChangeListener {

	@Override
	public void onPageSelected(int arg0) {
	    setTextTitleSelectedColor(arg0);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
    }

    /* 设置标题文本的颜色 */
    private void setTextTitleSelectedColor(int arg0) {
	int count = mViewPager.getChildCount();
	for (int i = 0; i < count; i++) {
	    TextView mTextView = (TextView) linearLayout1.getChildAt(i);
	    if (arg0 == i) {
		mTextView.setTextColor(resources.getColor(R.color.tab_font));
		mTextView.setBackgroundResource(R.color.mycredit_back_select);
	    } else {
		mTextView.setTextColor(resources
			.getColor(R.color.myorder_title_text_unclick));
		mTextView.setBackgroundResource(R.color.myorder_title_unclick);
	    }
	}
    }

    /* 标题点击监听 */
    private class MyOnClickListener implements OnClickListener {
	private int index = 0;

	public MyOnClickListener(int i) {
	    index = i;
	}

	public void onClick(View v) {
	    mViewPager.setCurrentItem(index);
	}
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
		    public void onLoading(long total, long current, boolean isUploading) {
		    }

		    @Override
		    public void onSuccess(ResponseInfo<String> responseInfo) {
			ToastUtil.closeProgressDialog();
			Log.i("order", responseInfo.result);
			Gson gson = new Gson();
			OrderListBean jsonBean = gson.fromJson(responseInfo.result,
				OrderListBean.class);
			if (jsonBean != null) {
			    if (StringUtils.equals("10000", jsonBean.head.code)) {
				listInfo = jsonBean.result;
				initControl();
				initViewPager();
				InitTextView();
			    }
			}
		    }

		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});

    }

    public class OrderAdapter extends BaseAdapter {
	public List<OrderList> lists;
	private LayoutInflater mInflater;
	// private BitmapUtils utils;
	// private BitmapDisplayConfig img_config;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;

	public OrderAdapter(Context context, List<OrderList> list) {
	    this.mInflater = LayoutInflater.from(context);
	    this.lists = list;
	    imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	    options = new DisplayImageOptions.Builder().showStubImage(R.drawable.replace) // 设置图片下载期间显示的图片
		    .showImageForEmptyUri(R.drawable.replace) // 设置图片Uri为空或是错误的时候显示的图片
		    .showImageOnFail(R.drawable.replace) // 设置图片加载或解码过程中发生错误显示的图片
		    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		    .cacheOnDisc(false) // 设置下载的图片是否缓存在SD卡中
		    .displayer(new RoundedBitmapDisplayer(1)) // 设置成圆角图片
		    .build();
	    // utils = new BitmapUtils(context);
	    // img_config = new BitmapDisplayConfig();
	    // img_config.setLoadingDrawable(getResources().getDrawable(R.drawable.replace));
	    // img_config.setLoadFailedDrawable(getResources().getDrawable(
	    // R.drawable.replace));
	    // img_config.setBitmapConfig(Bitmap.Config.RGB_565);
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
	    MyListener myListener  = new MyListener(lists, position);
	    if (convertView == null) {
		holder = new ViewHolder();
		// 可以理解为从vlist获取view 之后把view返回给ListView
		convertView = mInflater.inflate(R.layout.order_listview_item, null);
		holder.order_refund = (TextView) convertView
			.findViewById(R.id.order_refund);
		holder.order_store = (TextView) convertView
			.findViewById(R.id.order_store);
		holder.order_state = (TextView) convertView
			.findViewById(R.id.order_state);
		holder.order_info = (TextView) convertView.findViewById(R.id.order_info);
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
		holder.order_img = (ImageView) convertView.findViewById(R.id.order_img);
		holder.control_order = (RelativeLayout) convertView
			.findViewById(R.id.control_order);
		holder.order_desc = (RelativeLayout) convertView
			.findViewById(R.id.order_desc);
		holder.order_desc = (RelativeLayout) convertView
			.findViewById(R.id.order_desc);
		holder.control_order_refund = (RelativeLayout) convertView
			.findViewById(R.id.control_order_refund);
		convertView.setTag(holder);
	    } else {
		holder = (ViewHolder) convertView.getTag();
	    }
	    if (lists.get(position).order_state == 1) {
		holder.order_state.setText("订单未支付");
		holder.control_order.setVisibility(View.VISIBLE);
		holder.control_order_refund.setVisibility(View.GONE);
	    } else if (lists.get(position).order_state == 2) {
		holder.order_state.setText("支付完成");
		holder.control_order.setVisibility(View.GONE);
		holder.control_order_refund.setVisibility(View.VISIBLE);
	    } else if (lists.get(position).order_state == 3) {
		holder.order_state.setText("交易完成");
		holder.control_order.setVisibility(View.GONE);
		holder.control_order_refund.setVisibility(View.GONE);
	    } else if (lists.get(position).order_state == 5) {
		holder.order_state.setText("退款申请中");
		holder.control_order.setVisibility(View.GONE);
		holder.control_order_refund.setVisibility(View.GONE);
	    } else if (lists.get(position).order_state == 6) {
		holder.order_state.setText("退款成功");
		holder.control_order.setVisibility(View.GONE);
		holder.control_order_refund.setVisibility(View.GONE);
	    } else {
		holder.order_state.setText("交易进行中");
		holder.order_state
			.setTextColor(getResources().getColor(R.color.tab_font));
		holder.control_order.setVisibility(View.VISIBLE);
	    }
	    holder.order_info.setText(lists.get(position).order_info);
	    holder.order_store.setText(lists.get(position).order_store);
	    holder.order_total_price.setText("￥" + lists.get(position).order_total_price);
	    holder.order_discount.setText(lists.get(position).order_discount);
	    holder.order_price.setText("￥" + lists.get(position).order_price);
	    // utils.display(holder.order_img, Container.URL +
	    // lists.get(position).img_url,
	    // img_config);
	    imageLoader.displayImage(Container.URL + lists.get(position).img_url,
		    holder.order_img, options);
	    // 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
	    holder.del_bucket_img.setTag(position);
	    holder.order_pay_online.setTag(position);
	    holder.order_desc.setTag(position);
	    holder.order_refund.setTag(position);

	    holder.del_bucket_img.setOnClickListener(myListener);
	    holder.order_pay_online.setOnClickListener(myListener);
	    holder.order_desc.setOnClickListener(myListener);
	    holder.order_refund.setOnClickListener(myListener);
	    return convertView;
	}

	private class MyListener implements OnClickListener {
	    int mPosition;
	    List<OrderList> list;
	    private Intent is;

	    public MyListener(List<OrderList> lists, int inPosition) {
		mPosition = inPosition;
		list = lists;
	    }

	    @Override
	    public void onClick(View v) {
		int position = Integer.parseInt(v.getTag().toString());
		switch (v.getId()) {
		case R.id.order_refund: // 退款
		    is = new Intent();
		    is.putExtra("order_total_price", list.get(position).order_total_price);
		    is.setClass(MyOrderActivity.this, OrderPayActivity.class);
		    startActivity(is);
		    break;
		case R.id.order_pay_online: // 在线支付
		    Intent i = new Intent();
		    i.putExtra("order_total_price", list.get(position).order_total_price);
		    i.setClass(MyOrderActivity.this, OrderPayActivity.class);
		    startActivity(i);
		    break;
		case R.id.del_bucket_img: // 订单删除
		    deleteOrder(list, mPosition);
		    break;
		case R.id.order_desc: // item onclick
		    ToastUtil.show(MyOrderActivity.this, "R.id.order_desc" + position);
		    is = new Intent();
		    is.putExtra("OrderInfo", list.get(position).order_id);
		    is.setClass(MyOrderActivity.this, ShowOrderDetailActivity.class);
		    startActivity(is);
		    break;
		}

	    }

	}

	public final class ViewHolder {
	    public TextView order_refund;// 退款
	    public TextView order_store;
	    public TextView order_state;
	    public TextView order_info;
	    public TextView order_total_price;
	    public TextView order_discount;
	    public TextView order_price;
	    public RelativeLayout del_bucket_img;
	    public TextView order_pay_online;
	    public ImageView order_img;
	    public RelativeLayout control_order; // 在线支付控制栏
	    public RelativeLayout order_desc;// 订单详情
	    public RelativeLayout control_order_refund; // 退款控制栏
	}

    }

    private void deleteOrder(List<OrderList> list, int mPosition2) {
	clickList = list;
	num = mPosition2;
	MapPackage mp = new MapPackage();
	mp.setHead(this);
	mp.setPara("order_info_id", listInfo.get(mPosition2).order_info_id);
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
			    ToastUtil.show(MyOrderActivity.this, fromJson.head.msg);
			    clickList.remove(num);
			    allAdapter.notifyDataSetChanged();
			    unpayAdapter.notifyDataSetChanged();
			} else {
			}
		    }
		});
    }

    @OnClick({ R.id.title_left_btn })
    public void clickMethod(View v) {
	switch (v.getId()) {
	case R.id.title_left_btn:
	    finish();
	    break;
	}
    }
    public void onResume() {
	super.onResume();
	MobclickAgent.onResume(this);
	}
	public void onPause() {
	super.onPause();
	MobclickAgent.onPause(this);
	}

}
