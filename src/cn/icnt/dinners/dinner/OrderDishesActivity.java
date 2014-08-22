package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.beans.OrderCompanyBean.OrderCompanydesc;
import cn.icnt.dinners.utils.DensityUtil;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.umeng.analytics.MobclickAgent;

/**
 * 点菜
 * 
 * @author Administrator
 * 
 */
public class OrderDishesActivity extends Activity {
    @ViewInject(R.id.title_left_btn)
    private RelativeLayout title_left_btn;
    @ViewInject(R.id.title_center_text)
    private TextView title_center_text;
    @ViewInject(R.id.order_dishes_listview)
    private ListView order_dishes_listview;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.order_dishes_activity);
	initview();
    }

    private void initview() {
	ViewUtils.inject(this);
	title_center_text.setText("点菜");
	ArrayList list = new ArrayList();
	Myadapters adapters = new Myadapters(this, list);
	order_dishes_listview.setAdapter(adapters);
    }

    private class Myadapters extends BaseAdapter {
	private LayoutInflater mInflater;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;
	private int img_size;

	public Myadapters(Context context, List<OrderCompanydesc> list) {
	    this.mInflater = LayoutInflater.from(context);
	    img_size = DensityUtil.dip2px(context, 110f);// 图片
	    
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

	}

	@Override
	public int getCount() {
	    return 12;
	}

	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public long getItemId(int position) {
	    return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolders holder = null;
	    if (convertView == null) {
		holder = new ViewHolders();
		convertView = mInflater
			.inflate(R.layout.order_dishes_listview_item, null);
		holder.order_dishes_img = (ImageView) convertView
			.findViewById(R.id.order_dishes_img);
		holder.order_dishes_img.setMaxHeight(img_size);
		holder.order_dishes_img.setMinimumHeight(img_size);
		holder.order_dishes_img.setMinimumWidth(img_size);
		convertView.setTag(holder);
	    } else {
		holder = (ViewHolders) convertView.getTag();
	    }

	    return convertView;
	}

	private class ViewHolders {
	    public ImageView order_dishes_img;// 图
	    public TextView order_dishes_name;// 菜品名称
	    public TextView order_dishes_prices_tv;// 价格
	    public TextView order_dishes_desc;// 菜品介绍
	    public TextView order_dishes_del_tv;// 删除按钮
	    public ImageView order_dishes_minus;// 减
	    public ImageView order_dishes_plus;// 加
	    public TextView order_dishes_num;// 数
	}

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
