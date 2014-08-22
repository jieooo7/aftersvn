package cn.icnt.dinners.fragment;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import cn.icnt.dinners.beans.HomeGroupBean;
import cn.icnt.dinners.beans.HomeDishesBean.HomeDishesdesc;
import cn.icnt.dinners.beans.HomeGroupBean.GroupInfo;
import cn.icnt.dinners.dinner.DishesDetailActivity;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.dinner.RestaurantDetailActivity;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.ToastUtil;

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

public class FragmentGroup extends Fragment {

    private int widthPixels;
    private ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_dish, null);
	DisplayMetrics dm = new DisplayMetrics();
	getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
	widthPixels = dm.widthPixels / 4;
	listView = (ListView) view.findViewById(R.id.dish_list);
	initData();
	return view;
    }

    private void initData() {
	MapPackage mp = new MapPackage();
	mp.setHead(getActivity());
	mp.setRes("start", 0);
	mp.setRes("count", 10);
	Map<String, Object> maps = mp.getMap();
	RequestParams params = GsonTools.GetParams(maps);
	HttpUtils http = new HttpUtils();
	http.send(HttpRequest.HttpMethod.POST, Container.GROUP, params,
		new RequestCallBack<String>() {
		    private List<GroupInfo> listInfo;
		    private Intent intent;
		    private Bundle bundle;

		    @Override
		    public void onStart() {
		    }

		    @Override
		    public void onLoading(long total, long current, boolean isUploading) {
		    }

		    @Override
		    public void onSuccess(ResponseInfo<String> responseInfo) {
			Log.e("orders", "FragmentGroup\r\n" + responseInfo.result);
			HomeGroupBean person = GsonTools.getPerson(responseInfo.result,
				HomeGroupBean.class);
			if (person.head.code.equals("10000")) {
			    listInfo = person.result;
			    MyGroupAdapter adapter = new MyGroupAdapter(getActivity(),
				    listInfo);
			    listView.setAdapter(adapter);
			    intent = new Intent(getActivity(),
				    RestaurantDetailActivity.class);
			    bundle = new Bundle();

			    OnItemClickListener ocl = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				    // 这是测试log,应该是在另一个activity里面打开的
				    ToastUtil.show(getActivity(), position + "");
				    bundle.putSerializable("result",
					    listInfo.get(position));
				    intent.putExtras(bundle);
				    getActivity().startActivity(intent);
				}
			    };
			    listView.setOnItemClickListener(ocl);
			}
		    }

		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});
    }

    private class MyGroupAdapter extends BaseAdapter {
	private List<GroupInfo> list;
	private LayoutInflater mInflater;
	// private BitmapUtils utils;
	// private BitmapDisplayConfig img_config;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;

	public MyGroupAdapter(Context context, List<GroupInfo> result) {
	    this.mInflater = LayoutInflater.from(context);
	    this.list = result;
	    imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	    options = new DisplayImageOptions.Builder()
		    .showStubImage(R.drawable.ic_launcher)
		    // 设置图片下载期间显示的图片
		    .showImageForEmptyUri(R.drawable.ic_launcher)
		    // 设置图片Uri为空或是错误的时候显示的图片
		    .showImageOnFail(R.drawable.ic_launcher)
		    // 设置图片加载或解码过程中发生错误显示的图片
		    .cacheInMemory(true)
		    // 设置下载的图片是否缓存在内存中
		    .bitmapConfig(Bitmap.Config.RGB_565)
		    .imageScaleType(ImageScaleType.EXACTLY) // default
		    .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		    .build();
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
	    ViewHolderDishes holder_dishes;
	    MyListener myListener = new MyListener(list, position);
	    if (convertView == null) {
		holder_dishes = new ViewHolderDishes();
		convertView = mInflater.inflate(R.layout.fragment_group_item, null);
		holder_dishes.order_dishes_img = (ImageView) convertView
			.findViewById(R.id.order_dishes_img);
		holder_dishes.order_dishes_img.setMaxHeight(widthPixels);
		holder_dishes.order_dishes_img.setMaxWidth(widthPixels);
		holder_dishes.order_dishes_img.setMinimumHeight(widthPixels);
		holder_dishes.order_dishes_img.setMinimumWidth(widthPixels);
		holder_dishes.order_restaurant_tv = (TextView) convertView
			.findViewById(R.id.order_restaurant_tv);
		holder_dishes.order_group_sub = (TextView) convertView
			.findViewById(R.id.order_group_sub);
		holder_dishes.order_message_btn = (LinearLayout) convertView
			.findViewById(R.id.order_message_btn);
		holder_dishes.dishes_restaurant_heart = (LinearLayout) convertView
			.findViewById(R.id.dishes_restaurant_heart);
		holder_dishes.order_message_num = (TextView) convertView
			.findViewById(R.id.order_message_num);
		holder_dishes.dishes_restaurant_heart_num = (TextView) convertView
			.findViewById(R.id.dishes_restaurant_heart_num);
		holder_dishes.order_dishes_desc_tv = (TextView) convertView
			.findViewById(R.id.order_dishes_desc_tv);
		holder_dishes.order_dishes_restaurant_infos = (TextView) convertView
			.findViewById(R.id.order_dishes_restaurant_infos);
		holder_dishes.order_dishes_orther_desc = (TextView) convertView
			.findViewById(R.id.order_dishes_orther_desc);
		holder_dishes.dishes_restaurant_heart_num = (TextView) convertView
			.findViewById(R.id.dishes_restaurant_heart_num);
		holder_dishes.order_message_num = (TextView) convertView
			.findViewById(R.id.order_message_num);
		convertView.setTag(holder_dishes);
	    } else {
		holder_dishes = (ViewHolderDishes) convertView.getTag();
	    }
	    imageLoader.displayImage(Container.URL + list.get(position).food_url.get(0),
		    holder_dishes.order_dishes_img, options);
	    holder_dishes.order_restaurant_tv
		    .setText(list.get(position).name_store + ":");
	    holder_dishes.order_group_sub.setText("仅限" + list.get(position).sub_name);
	    holder_dishes.order_dishes_desc_tv
		    .setText(list.get(position).information_str);
	    holder_dishes.order_dishes_restaurant_infos
		    .setText(list.get(position).food_name);
	    holder_dishes.order_dishes_orther_desc.setText(list.get(position).start_time
		    + "-" + list.get(position).end_time);
	    holder_dishes.order_message_num.setText(list.get(position).comment_num);
	    holder_dishes.dishes_restaurant_heart_num
		    .setText(list.get(position).share_count);

	    // holder_dishes.order_restaurant_item.setTag(position);
	    holder_dishes.dishes_restaurant_heart.setTag(position);
	    holder_dishes.order_message_btn.setTag(position);
	    holder_dishes.dishes_restaurant_heart.setOnClickListener(myListener);
	    holder_dishes.order_message_btn.setOnClickListener(myListener);
	    return convertView;
	}

	private class MyListener implements OnClickListener {
	    int mPosition;
	    List<GroupInfo> list;
	    private Intent intent;

	    public MyListener(List<GroupInfo> lists, int inPosition) {
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

	private class ViewHolderDishes {
	    public ImageView order_dishes_img;// 图
	    public TextView order_restaurant_tv; // 餐厅名
	    public TextView order_group_sub; // 餐厅名
	    public TextView order_dishes_desc_tv; // 菜品详情
	    public TextView order_dishes_restaurant_infos; // 菜品信息
	    public TextView order_dishes_orther_desc; // 有效期
	    public TextView dishes_restaurant_heart_num; // 点赞数
	    public TextView order_message_num; // 回复数
	    public LinearLayout order_message_btn; // 回复按钮
	    public LinearLayout dishes_restaurant_heart; // 点赞按钮
	}
    }

    public void onResume() {
	super.onResume();
	MobclickAgent.onPageStart("FragmentRes"); // 统计页面
    }

    public void onPause() {
	super.onPause();
	MobclickAgent.onPageEnd("FragmentRes");
    }
}
