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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.icnt.dinners.beans.OrderCompanyBean;
import cn.icnt.dinners.beans.OrderCompanyBean.OrderCompanydesc;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.dinner.RestaurantDetailActivity;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.DensityUtil;
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
import com.umeng.analytics.MobclickAgent;

/**
 * 餐厅推荐
 * 
 * @author Administrator
 * 
 */
public class FragmentRestaurant extends Fragment {
    private ListView mlv;
    private int widthPixels;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	DisplayMetrics dm = new DisplayMetrics();
	getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
	widthPixels = dm.widthPixels / 4;
	View view = inflater.inflate(R.layout.fragment_coupon, null);

	mlv = (ListView) view.findViewById(R.id.coupon_list);
	initData();
	return view;
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
		    private Intent intent;
		    private Bundle bundle;
		    private List<OrderCompanydesc> companyList;

		    @Override
		    public void onStart() {
		    }

		    @Override
		    public void onLoading(long total, long current, boolean isUploading) {
		    }

		    @Override
		    public void onSuccess(ResponseInfo<String> responseInfo) {
			Log.e("order", "FragmentRestaurant\r\n" + responseInfo.result);
			Gson gson = new Gson();
			OrderCompanyBean orderCompanyBean = gson.fromJson(
				responseInfo.result, OrderCompanyBean.class);
			if (orderCompanyBean != null) {
			    if (StringUtils.equals("10000", orderCompanyBean.head.code)) {
				companyList = orderCompanyBean.result;
				MyDishesAdapter myDishesAdapter = new MyDishesAdapter(
					getActivity(), companyList);
				mlv.setAdapter(myDishesAdapter);
				intent = new Intent(getActivity(),
					RestaurantDetailActivity.class);
				bundle = new Bundle();

				OnItemClickListener ocl = new OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> parent,
					    View view, int position, long id) {
					// 这是测试log,应该是在另一个activity里面打开的
					ToastUtil.show(getActivity(), position + "");
					bundle.putSerializable("result",
						companyList.get(position));
					intent.putExtras(bundle);
					getActivity().startActivity(intent);
				    }
				};
				mlv.setOnItemClickListener(ocl);
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
	private int size;

	public MyDishesAdapter(Context context, List<OrderCompanydesc> list) {
	    this.mInflater = LayoutInflater.from(context);
	    this.list = list;
	    size = DensityUtil.dip2px(context, 19f);
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
	    ViewHoldersRes holderRes;
	    MyListener myListener = new MyListener(list, position);
	    if (convertView == null) {
		holderRes = new ViewHoldersRes();
		convertView = mInflater.inflate(R.layout.fragment_restaurant_item, null);
		holderRes.order_dishes_img = (ImageView) convertView
			.findViewById(R.id.order_dishes_img);
		holderRes.order_dishes_img.setMaxHeight(widthPixels);
		holderRes.order_dishes_img.setMinimumHeight(widthPixels);
		holderRes.order_dishes_img.setMinimumWidth(widthPixels);
		holderRes.order_restaurant_tv = (TextView) convertView
			.findViewById(R.id.order_restaurant_tv);
		holderRes.order_restaurant_annex_tv = (TextView) convertView
			.findViewById(R.id.order_restaurant_annex_tv);
		holderRes.order_restaurant_desc_tv = (TextView) convertView
			.findViewById(R.id.order_restaurant_desc_tv);
		holderRes.order_dishes_name = (TextView) convertView
			.findViewById(R.id.order_dishes_name);
		holderRes.order_message_btn = (LinearLayout) convertView
			.findViewById(R.id.order_message_btn);
		holderRes.dishes_restaurant_heart = (LinearLayout) convertView
			.findViewById(R.id.dishes_restaurant_heart);
		holderRes.order_message_num = (TextView) convertView
			.findViewById(R.id.order_message_num);
		holderRes.dishes_restaurant_heart_num = (TextView) convertView
			.findViewById(R.id.dishes_restaurant_heart_num);
		// holderRes.order_restaurant_item = (LinearLayout) convertView
		// .findViewById(R.id.order_restaurant_item);
		convertView.setTag(holderRes);
	    } else {
		holderRes = (ViewHoldersRes) convertView.getTag();
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
		    holderRes.order_dishes_img, options);
	    holderRes.order_restaurant_tv.setText(list.get(position).name_store + ":");
	    holderRes.order_restaurant_annex_tv.setText(list.get(position).store_str);
	    holderRes.order_restaurant_desc_tv.setText(builder);
	    holderRes.order_dishes_name.setText(list.get(position).food_name);
	    holderRes.order_message_num.setText(list.get(position).comment_num);
	    holderRes.dishes_restaurant_heart_num.setText(list.get(position).share_count);
	    holderRes.order_message_btn.setTag(position);
	    holderRes.dishes_restaurant_heart.setTag(position);
	    // holderRes.order_restaurant_item.setTag(position);
	    holderRes.order_message_btn.setOnClickListener(myListener);
	    holderRes.dishes_restaurant_heart.setOnClickListener(myListener);
	    // holderRes.order_restaurant_item.setOnClickListener(myListener);
	    return convertView;
	}

	private class MyListener implements OnClickListener {
	    int mPosition;
	    List<OrderCompanydesc> list;

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
		}
	    }
	}

	private class ViewHoldersRes {
	    public ImageView order_dishes_img;// 图
	    public TextView order_restaurant_tv; // 餐厅名
	    public TextView order_restaurant_annex_tv; // 分店信息
	    public TextView order_restaurant_desc_tv; // 店铺简介
	    public TextView order_dishes_name; // 特殊菜名
	    public LinearLayout order_message_btn; // 评价按钮
	    public TextView order_message_num; // 评价数量
	    public LinearLayout dishes_restaurant_heart; // 点赞按钮
	    public TextView dishes_restaurant_heart_num; // 点赞数量
	    // public LinearLayout order_restaurant_item; //
	}
    }

    public void onResume() {
	super.onResume();
	MobclickAgent.onPageStart("FragmentRestaurant"); // 统计页面
    }

    public void onPause() {
	super.onPause();
	MobclickAgent.onPageEnd("FragmentRestaurant");
    }
}
