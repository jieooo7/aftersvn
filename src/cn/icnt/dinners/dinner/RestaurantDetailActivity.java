package cn.icnt.dinners.dinner;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.beans.FindPwdBean;
import cn.icnt.dinners.beans.OrderCompanyBean.OrderCompanydesc;
import cn.icnt.dinners.fragment.SharePopupWindow;
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
import com.umeng.analytics.MobclickAgent;


/**
 * 餐厅详情
 * 
 * @author Administrator
 * 
 */
public class RestaurantDetailActivity extends Activity implements OnClickListener {

    private LinearLayout mCollection;
    private LinearLayout mComments;
    private LinearLayout mShare;
    private LinearLayout mOrder;
    private ImageView imageView;
    private TextView tv_text_food;
    private TextView mtv2;
    private TextView mtv3;
    private TextView mtv4;
    private TextView mtv5;
    private TextView mtv6;
    private TextView mtv7;
    private TextView mtv_it;
    private OrderCompanydesc ocd;
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
	setContentView(R.layout.restaurant_detail_activity);
	((TextView) this.findViewById(R.id.title_center_text)).setText("");
	((RelativeLayout) this.findViewById(R.id.title_left_btn))
		.setOnClickListener(this);
	Bundle bundle = getIntent().getExtras();
	ocd = (OrderCompanydesc) bundle.getSerializable("result");
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
	mtv2 = (TextView) this.findViewById(R.id.tv_remaind);
	mtv3 = (TextView) this.findViewById(R.id.tv_data);
	mtv4 = (TextView) this.findViewById(R.id.tv_details_activity);
	mtv5 = (TextView) this.findViewById(R.id.tv_youhuixinxi);
	mtv6 = (TextView) this.findViewById(R.id.tv_introduce);
	mtv7 = (TextView) this.findViewById(R.id.tv_zdai);
	initView();
    }

    private void initView() {
	imageLoader.displayImage(Container.URL + ocd.lager_picture, imageView, options);
	tv_text_food.setText(ocd.name_store);
	mtv2.setText(ocd.remind);
	mtv3.setText("地址:" + ocd.address_str);
	mtv4.setText("电话:" + ocd.contact_tel);
	mtv5.setText("营业时间:" + ocd.start_time + "-" + ocd.end_time);
	mtv6.setText("\n"+ocd.description);
	mtv7.setText("注意:" + (StringUtils.isEmpty(ocd.remark) ? "" : ocd.remark));
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
	    share();
	    break;
	case R.id.restaurant_order:
	    intent = new Intent();
	    intent.setClass(this, OrderDishesActivity.class);
	    startActivity(intent);
	    break;
	}
    }

    /************** 分享 ******************/
    SharePopupWindow pop;
    private OnClickListener itemsOnClick = new OnClickListener() {

	public void onClick(View v) {
	    pop.dismiss();
	    switch (v.getId()) {
	    case R.id.share_sina:
		break;
	    case R.id.share_qq:
		break;
	    case R.id.share_wechat:
		break;
	    }
	}
    };

    private void share() {
	pop = new SharePopupWindow(RestaurantDetailActivity.this, itemsOnClick);
	// 显示窗口
	pop.showAtLocation(RestaurantDetailActivity.this.findViewById(R.id.tv_zdai), Gravity.BOTTOM
		| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
    }

    /********************************/

    /**
     * 收藏
     */
    private void collect() {
	mp = new MapPackage();
	mp.setHead(this);
	mp.setPara("favorite_type", "3");
	mp.setPara("company_id", ocd.company_id);
	mp.setPara("sub_company_id", ocd.sub_company_id);
	mp.setPara("goods_id", 0);
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
			    ToastUtil.showProgressDialogs(RestaurantDetailActivity.this,
				    head.head.msg);
			}
		    }

		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});
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
