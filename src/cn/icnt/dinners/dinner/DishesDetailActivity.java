package cn.icnt.dinners.dinner;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.beans.FindPwdBean;
import cn.icnt.dinners.beans.HomeDishesBean.HomeDishesdesc;
import cn.icnt.dinners.beans.OrderCompanyBean.OrderCompanydesc;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
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

/**
 * 菜品详情页
 * 
 * @author Administrator
 * 
 */
public class DishesDetailActivity extends Activity implements OnClickListener {

    private LinearLayout mCollection;
    private LinearLayout mComments;
    private LinearLayout mShare;
    private LinearLayout mOrder;
    private ImageView imageView;
    private TextView tv_text_food, tv_data, tv_details_activity, original_price,
	    tv_open_time, tv_notice, tv_remaind, tv_introduce, food_str;
    private HomeDishesdesc ocd;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions options;
    private Map<String, Object> maps;
    private MapPackage mp;
    private RequestParams params;
    private Intent intent;

    // 餐厅推选
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dishes_detail_activity);
	((TextView) this.findViewById(R.id.title_center_text)).setText("");
	((RelativeLayout) this.findViewById(R.id.title_left_btn))
		.setOnClickListener(this);
	Bundle bundle = getIntent().getExtras();
	ocd = (HomeDishesdesc) bundle.getSerializable("result");
	/************************/
	imageLoader.init(ImageLoaderConfiguration.createDefault(this));
	options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_images)
		// 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.icon_images)
		// 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.icon_images)
		// 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true)
		// 设置下载的图片是否缓存在内存中
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.EXACTLY) // default
		.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		.build();

	/************************/
	mCollection = (LinearLayout) this.findViewById(R.id.restaurant_collection);
	mComments = (LinearLayout) this.findViewById(R.id.restaurant__comments);
	mShare = (LinearLayout) this.findViewById(R.id.restaurant_share);
	mOrder = (LinearLayout) this.findViewById(R.id.restaurant_order);
	imageView = (ImageView) this.findViewById(R.id.iv_images);
	tv_text_food = (TextView) this.findViewById(R.id.tv_text_food);
	original_price = (TextView) this.findViewById(R.id.original_price);// 菜品单价
	food_str = (TextView) this.findViewById(R.id.food_str);// 菜名
	tv_introduce = (TextView) this.findViewById(R.id.tv_introduce); // 菜品详情
	tv_data = (TextView) this.findViewById(R.id.tv_data); // 地址
	tv_details_activity = (TextView) this.findViewById(R.id.tv_details_activity); // 电话
	tv_open_time = (TextView) this.findViewById(R.id.tv_open_time);// 时间
	tv_notice = (TextView) this.findViewById(R.id.tv_notice);// 注意
	tv_remaind = (TextView) this.findViewById(R.id.tv_remaind);// 版权
	initView();
    }

    private void initView() {
	imageLoader.displayImage(Container.URL + ocd.lager_picture, imageView, options);
	tv_text_food.setText(ocd.name_store);
	if (StringUtils.isEmpty(ocd.original_price)) {
	    food_str.setText(ocd.food_str);
	    original_price.setText(ocd.original_price + "");
	} else {
	    food_str.setText(ocd.food_str + ": ");
	    original_price.setText(ocd.original_price + "元/份");
	}
	tv_remaind.setText(ocd.remind);
	tv_data.setText("地址: " + ocd.address_str);
	tv_details_activity.setText("电话: " + ocd.phone_str);
	tv_open_time.setText("营业时间: " + ocd.start_time + "-" + ocd.end_time);
	tv_introduce.setText(ocd.description);
	tv_notice.setText("注意: "
		+ (StringUtils.isEmpty(ocd.giving_str) ? "" : ocd.giving_str));
	mCollection.setOnClickListener(this); // 收藏
	mComments.setOnClickListener(this);// 评论
	mShare.setOnClickListener(this);// 分享
	mOrder.setOnClickListener(this); // 点赞
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.title_left_btn:
	    finish();
	    break;
	case R.id.restaurant_collection:
	    collect();
	    break;
	case R.id.restaurant__comments:
	    break;
	case R.id.restaurant_share:
	    break;
	case R.id.restaurant_order:
	    intent = new Intent();
	    intent.setClass(this, OrderDishesActivity.class);
	    startActivity(intent);
	    break;
	}
    }

    /**
     * 收藏
     */
    private void collect() {
	mp = new MapPackage();
	mp.setHead(this);
	mp.setPara("favorite_type", "2");
	mp.setPara("company_id", ocd.company_id);
	 mp.setPara("sub_company_id", ocd.sub_company_id);
	mp.setPara("goods_id", ocd.goods_id);
	maps = mp.getMap();
	params = GsonTools.GetParams(maps);
	HttpUtils http = new HttpUtils();
	http.send(HttpRequest.HttpMethod.POST, Container.COLLECT, params,
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
			FindPwdBean head = gson.fromJson(responseInfo.result,
				FindPwdBean.class);
			if (head != null) {
			    ToastUtil.showProgressDialogs(DishesDetailActivity.this,
				    head.head.msg);
			}
		    }

		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});
    }
}
