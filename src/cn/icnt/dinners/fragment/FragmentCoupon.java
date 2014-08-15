package cn.icnt.dinners.fragment;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.icnt.dinners.beans.OrderCompanyBean;
import cn.icnt.dinners.beans.OrderCompanyBean.OrderCompanydesc;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.dinner.RestaurantDetailActivity;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.DensityUtil;
import cn.icnt.dinners.utils.NetChecker;
import cn.icnt.dinners.utils.ToastUtil;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class FragmentCoupon extends Fragment {
    private ListView mlv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	View view = inflater.inflate(R.layout.fragment_coupon, null);
	getAdInfo();

	mlv = (ListView) view.findViewById(R.id.coupon_list);
	initData();
	return view;
    }

    private void getAdInfo() {
	if (!NetChecker.checkNet(getActivity())) {
	    return;
	}
    }

    // public void toastIfActive(int errorNetworkAvailable) {
    // if (!this.mIsPause) {
    // if (mToast == null) {
    // mToast = Toast.makeText(getActivity(), errorNetworkAvailable,
    // Toast.LENGTH_SHORT);
    // mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    // } else {
    // mToast.setText(errorNetworkAvailable);
    // }
    // mToast.show();
    // }
    // }

    private void initData() {
	MapPackage mp = new MapPackage();
	mp.setHead(getActivity());
	mp.setRes("start", 0);
	mp.setRes("count", 10);
	Map<String, Object> maps = mp.getMap();
	RequestParams params = GsonTools.GetParams(maps);
	HttpUtils http = new HttpUtils();
	http.send(HttpRequest.HttpMethod.POST, Container.COMPANY, params,
		new RequestCallBack<String>() {
		    @Override
		    public void onStart() {
		    }

		    @Override
		    public void onLoading(long total, long current, boolean isUploading) {
		    }

		    @Override
		    public void onSuccess(ResponseInfo<String> responseInfo) {
			Log.e("order", responseInfo.result);
			Gson gson = new Gson();
			OrderCompanyBean orderCompanyBean = gson.fromJson(
				responseInfo.result, OrderCompanyBean.class);
			if (orderCompanyBean != null) {
			    if (StringUtils.equals("10000", orderCompanyBean.head.code)) {
				List<OrderCompanydesc> companyList = orderCompanyBean.result;
				MyDishesAdapter myDishesAdapter = new MyDishesAdapter(
					getActivity(), companyList);
				mlv.setAdapter(myDishesAdapter);
			    }
			}
		    }

		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});
    }

    private class MyDishesAdapter extends BaseAdapter {
	private List<OrderCompanydesc> list;
	private LayoutInflater mInflater;
	// private BitmapUtils utils;
	// private BitmapDisplayConfig img_config;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;
	private int size, img_size;

	public MyDishesAdapter(Context context, List<OrderCompanydesc> list) {
	    this.mInflater = LayoutInflater.from(context);
	    this.list = list;
	    size = DensityUtil.dip2px(context, 18f);
	    img_size = DensityUtil.dip2px(context, 110f);
	    imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	    options = new DisplayImageOptions.Builder().showStubImage(R.drawable.logo)
		    // 设置图片下载期间显示的图片
		    .showImageForEmptyUri(R.drawable.logo)
		    // 设置图片Uri为空或是错误的时候显示的图片
		    .showImageOnFail(R.drawable.logo)
		    // 设置图片加载或解码过程中发生错误显示的图片
		    .cacheInMemory(true)
		    // 设置下载的图片是否缓存在内存中
		    .bitmapConfig(Bitmap.Config.RGB_565)
		    .imageScaleType(ImageScaleType.EXACTLY) // default
		    .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		    .build();

	    // this.lists = list;
	    // utils = new BitmapUtils(context);
	    // img_config = new BitmapDisplayConfig();
	    // img_config.setLoadingDrawable(getResources().getDrawable(R.drawable.replace));
	    // img_config.setLoadFailedDrawable(getResources().getDrawable(
	    // R.drawable.replace));
	    // img_config.setBitmapConfig(Bitmap.Config.RGB_565);
	}

	@Override
	public int getCount() {
	    return list.size();
	}

	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return list.get(position);
	}

	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolders holder = null;
	    MyListener myListener = new MyListener(list, position);
	    if (convertView == null) {
		holder = new ViewHolders();
		convertView = mInflater.inflate(R.layout.order_dishes_restaurant_item,
			null);
		holder.order_dishes_img = (ImageView) convertView
			.findViewById(R.id.order_dishes_img);
		holder.order_dishes_img.setMaxHeight(img_size);
		holder.order_dishes_img.setMinimumHeight(img_size);
		holder.order_dishes_img.setMinimumWidth(img_size);
		holder.order_restaurant_tv = (TextView) convertView
			.findViewById(R.id.order_restaurant_tv);
		holder.order_restaurant_annex_tv = (TextView) convertView
			.findViewById(R.id.order_restaurant_annex_tv);
		holder.order_restaurant_desc_tv = (TextView) convertView
			.findViewById(R.id.order_restaurant_desc_tv);
		holder.order_dishes_name = (TextView) convertView
			.findViewById(R.id.order_dishes_name);
		holder.order_message_btn = (LinearLayout) convertView
			.findViewById(R.id.order_message_btn);
		holder.dishes_restaurant_heart = (LinearLayout) convertView
			.findViewById(R.id.dishes_restaurant_heart);
		holder.order_message_num = (TextView) convertView
			.findViewById(R.id.order_message_num);
		holder.dishes_restaurant_heart_num = (TextView) convertView
			.findViewById(R.id.dishes_restaurant_heart_num);
		holder.order_restaurant_item = (LinearLayout) convertView
			.findViewById(R.id.order_restaurant_item);
		convertView.setTag(holder);
	    } else {
		holder = (ViewHolders) convertView.getTag();
	    }
	    /*****************/
	    SpannableStringBuilder builder = new SpannableStringBuilder("简介: "
		    + list.get(position).description);
	    ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources()
		    .getColor(R.color.tab_font));
	    builder.setSpan(redSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	    builder.setSpan(new AbsoluteSizeSpan(size), 0, 3,
		    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    /*****************/
	    imageLoader.displayImage(Container.URL + list.get(position).name_url,
		    holder.order_dishes_img, options);
	    holder.order_restaurant_tv.setText(list.get(position).name_store + ":");
	    holder.order_restaurant_annex_tv.setText(list.get(position).store_str);
	    holder.order_restaurant_desc_tv.setText(builder);
	    holder.order_dishes_name.setText(list.get(position).food_name);
	    holder.order_message_num.setText(list.get(position).comment_num);
	    holder.dishes_restaurant_heart_num.setText(list.get(position).share_count);
	    holder.order_message_btn.setTag(position);
	    holder.dishes_restaurant_heart.setTag(position);
	    holder.order_restaurant_item.setTag(position);
	    holder.order_message_btn.setOnClickListener(myListener);
	    holder.dishes_restaurant_heart.setOnClickListener(myListener);
	    holder.order_restaurant_item.setOnClickListener(myListener);
	    return convertView;
	}

	private class MyListener implements OnClickListener {
	    int mPosition;
	    List<OrderCompanydesc> list;
	    private Intent is;

	    public MyListener(List<OrderCompanydesc> lists, int inPosition) {
		mPosition = inPosition;
		list = lists;
	    }

	    @Override
	    public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dishes_restaurant_heart: // 赞
		    ToastUtil.show(getActivity(), " 赞" + mPosition);
		    break;
		case R.id.order_message_btn: // 评价
		    ToastUtil.show(getActivity(), " 评价" + mPosition);
		    break;
		case R.id.order_restaurant_item: //
		    Intent intent = new Intent(getActivity(),
			    RestaurantDetailActivity.class);
		    Bundle bundle = new Bundle();
		    bundle.putSerializable("result", list.get(mPosition));
		    intent.putExtras(bundle);
		    getActivity().startActivity(intent);
		    break;
		}
	    }
	}

	private class ViewHolders {
	    public ImageView order_dishes_img;// 图
	    public TextView order_restaurant_tv; // 餐厅名
	    public TextView order_restaurant_annex_tv; // 分店信息
	    public TextView order_restaurant_desc_tv; // 店铺简介
	    public TextView order_dishes_name; // 特殊菜名
	    public LinearLayout order_message_btn; // 评价按钮
	    public TextView order_message_num; // 评价数量
	    public LinearLayout dishes_restaurant_heart; // 点赞按钮
	    public TextView dishes_restaurant_heart_num; // 点赞数量
	    public LinearLayout order_restaurant_item; //
	}
    }
}
