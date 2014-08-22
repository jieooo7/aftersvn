/*
 * MainActivity.java
 * classes : com.cloud.app.dinner.MainActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��25�� ����9:27:36
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.adapter.MyFragmentPagerAdapter;
import cn.icnt.dinners.fragment.FragmentDishes;
import cn.icnt.dinners.fragment.FragmentGroup;
import cn.icnt.dinners.fragment.FragmentRestaurant;
import cn.icnt.dinners.utils.ActivityList;
import cn.icnt.dinners.utils.PreferencesUtils;
import cn.icnt.dinners.utils.ToastUtil;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends FragmentActivity implements OnClickListener {
    @ViewInject(R.id.title_left_btn_img)
    private ImageView title_left_btn_img;
    @ViewInject(R.id.title_left_btn)
    private RelativeLayout title_left_btn;
    @ViewInject(R.id.title_center_text)
    private TextView title_center_text;
    @ViewInject(R.id.title_right_btn_img)
    private ImageView title_right_btn_img;
    @ViewInject(R.id.title_right_btn)
    private RelativeLayout title_right_btn;
    /*********************************/
    private TextView loginTv5;
    private LinearLayout line0, line1, line2, line3, line4, line5, line6, ivSet,
	    menu_myorder, left_menu_l0;
    /**************************/
    private ViewPager mViewPager;
    private LinearLayout linearLayout1;
    private TextView textView0, textView1, textView2;
    private ArrayList<Fragment> fragmentsList;
    private Resources resources;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions options;
    private Button mSurrounding;
    private SlidingMenu menu;
    private Intent intent;
//    public LocationClient mLocationClient = null;
    private Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            // TODO 接收消息并且去更新UI线程上的控件内容
	            switch (msg.what) {
		    }
	            super.handleMessage(msg);
	        }
	    };

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	resources = this.getResources();
	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	setContentView(R.layout.right_content);
	ViewUtils.inject(this);
//	initLocation();
	title_center_text.setText("厦门");
	title_left_btn_img.setImageResource(R.drawable.user);
	title_right_btn.setVisibility(View.VISIBLE);
	title_right_btn_img.setImageResource(R.drawable.search);
	initSlidingMenu();
	init();
	initLeftMenu();
	initControl();
	initViewPager();
	InitTextView();
	initClick();
    }

    private void initControl() {
	linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
	mViewPager = (ViewPager) findViewById(R.id.pvr_user_pager);
	mViewPager.setOffscreenPageLimit(1);/* 预加载页面 */
    }

    /* 初始化ViewPager */
    private void initViewPager() {
	fragmentsList = new ArrayList<Fragment>();
	fragmentsList.add(new FragmentGroup()); // 团购信息
	fragmentsList.add(new FragmentDishes());// 菜品推荐
	fragmentsList.add(new FragmentRestaurant()); // 餐厅推荐

	mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),
		fragmentsList));
	mViewPager.setCurrentItem(0);
	mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /* 初始化页卡标题 */
    private void InitTextView() {
	textView0 = (TextView) findViewById(R.id.text0);
	textView1 = (TextView) findViewById(R.id.text1);
	textView2 = (TextView) findViewById(R.id.text2);

	textView0.setOnClickListener(new MyOnClickListener(0));
	textView1.setOnClickListener(new MyOnClickListener(1));
	textView2.setOnClickListener(new MyOnClickListener(2));
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
	// int count = mViewPager.getChildCount();
	int count = 3;
	for (int i = 0; i < count; i++) {
	    TextView mTextView = (TextView) linearLayout1.getChildAt(i);
	    // mTextView.setTextColor(resources.getColor(R.color.white_text));
	    // mTextView.setBackgroundResource(R.color.tab_unselect);
	    if (arg0 == i) {
		mTextView.setTextColor(resources.getColor(R.color.tab_font));
		mTextView.setBackgroundResource(R.color.tab_select);
	    } else {
		mTextView.setTextColor(resources.getColor(R.color.white_text));
		mTextView.setBackgroundResource(R.color.tab_unselect);
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

    public void initSlidingMenu() {
	imageLoader.init(ImageLoaderConfiguration.createDefault(MainActivity.this));
	options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.logo) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.logo) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		.displayer(new RoundedBitmapDisplayer(90)) // 设置成圆角图片
		.build();
	menu = new SlidingMenu(this);
	menu.setMode(SlidingMenu.LEFT);
	// menu.setShadowDrawable(R.drawable.bt_download);// 设置 SlidingMenu
	// 边缘的阴影效果
	// menu.setShadowWidthRes(R.dimen.SlidingMenu_WidthRes_margin);// 设置

	// menu.setBehindWidth(500);//设置 SlidingMenu 的宽度 和下面的设置二选一
	menu.setBehindOffsetRes(R.dimen.slide);// 设置SlidingMenu主页面的宽度
	menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);// 允许从屏幕的什么范围滑出SlidingMenu
	menu.setMenu(R.layout.left_menu);// 设置SlidingMenu使用的layout文件
	menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);// 附加到activity上
    }

    private void init() {
	loginTv5 = (TextView) findViewById(R.id.login);
	left_menu_l0 = (LinearLayout) findViewById(R.id.left_menu_l0);
	line0 = (LinearLayout) findViewById(R.id.line0);
	menu_myorder = (LinearLayout) findViewById(R.id.menu_myorder);
	line1 = (LinearLayout) findViewById(R.id.line1);
	line2 = (LinearLayout) findViewById(R.id.line2);
	line3 = (LinearLayout) findViewById(R.id.line3);
	line5 = (LinearLayout) findViewById(R.id.line5);
	line6 = (LinearLayout) findViewById(R.id.line6);
	ivSet = (LinearLayout) findViewById(R.id.set_small);
	mSurrounding = (Button) this.findViewById(R.id.button_bt);
	line4 = (LinearLayout) findViewById(R.id.line4);
	userImg = (ImageView) findViewById(R.id.left_menu_user);
    }

    protected void initLeftMenu() {
	((TextView) findViewById(R.id.orderTv0)).setText(PreferencesUtils
		.getValueFromSPMap(MainActivity.this, PreferencesUtils.Keys.ORDERNO_NO,
			"0"));
	((TextView) findViewById(R.id.collectTv1)).setText(PreferencesUtils
		.getValueFromSPMap(MainActivity.this, PreferencesUtils.Keys.COLLECT_NO,
			"0"));
	((TextView) findViewById(R.id.couponTv2)).setText(PreferencesUtils
		.getValueFromSPMap(MainActivity.this, PreferencesUtils.Keys.COUPON_NO,
			"0"));
	((TextView) findViewById(R.id.creditsTv3)).setText(PreferencesUtils
		.getValueFromSPMap(MainActivity.this, PreferencesUtils.Keys.CREDITS_NO,
			"0"));
	((TextView) findViewById(R.id.accountTv4)).setText(PreferencesUtils
		.getValueFromSPMap(MainActivity.this, PreferencesUtils.Keys.ACCOUNT_NO,
			"0.00"));
	loginTv5.setText(PreferencesUtils.getValueFromSPMap(MainActivity.this,
		PreferencesUtils.Keys.NICKNAME, "登陆/注册"));
	String st = PreferencesUtils.getValueFromSPMap(getApplicationContext(),
		PreferencesUtils.Keys.USER_PORTRAIT, "");
	// *******用户登录则显示 注销按钮
	if (!StringUtils.equals(
		"-1",
		StringUtils.isEmpty(PreferencesUtils.getValueFromSPMap(MainActivity.this,
			PreferencesUtils.Keys.UID)) ? "-1" : PreferencesUtils
			.getValueFromSPMap(MainActivity.this, PreferencesUtils.Keys.UID))) {
	    line6.setVisibility(View.VISIBLE);
	} else {
	    line6.setVisibility(View.GONE);
	}
	// if (!StringUtils.isEmpty(st)) {
	// // imageLoader.displayImage(MapPackage.PATH + st, userImg, options);
	imageLoader.displayImage(PreferencesUtils.getValueFromSPMap(this,
		PreferencesUtils.Keys.USER_PORTRAIT), userImg, options);
	// }
    }

    @OnClick({ R.id.title_left_btn, R.id.title_right_btn })
    public void clickMethod(View v) {
	switch (v.getId()) {
	case R.id.title_left_btn:
	    menu.toggle();
	    break;
	// 首页搜索按钮
	case R.id.title_right_btn:
	    ToastUtil.show(getApplication(), "搜索");
	    break;
	}
    }

    // 侧滑菜单事件绑定
    protected void initClick() {
	// 登陆注册事件
	loginTv5.setOnClickListener(this);
	// 登陆注册事件
	left_menu_l0.setOnClickListener(this);
	// 首页item
	line0.setOnClickListener(this);
	ActivityList.activitys.add(this);
	// 我的订单事件
	menu_myorder.setOnClickListener(this);
	// 我的收藏item
	line1.setOnClickListener(this);
	// 我的优惠券item
	line2.setOnClickListener(this);
	// 我的积分item
	line3.setOnClickListener(this);
	// 我的账户item
	line4.setOnClickListener(this);
	// 设置item
	line5.setOnClickListener(this);
	String userId = PreferencesUtils.getValueFromSPMap(this,
		PreferencesUtils.Keys.UID);
	if (StringUtils.equals("-1", userId)) {
	    line6.setVisibility(View.GONE);
	}
	line6.setOnClickListener(this);
	// 个人中心设置
	ivSet.setOnClickListener(this);
	mSurrounding.setOnClickListener(this);
	userImg.setOnClickListener(this);
    }

    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.line0:
	    menu.toggle();
	    break;
	// 侧滑注册登录
	case R.id.login:
	case R.id.left_menu_user:
	    intent = new Intent();
	    intent.setClass(MainActivity.this, LoginActivity.class);
	    startActivity(intent);
	    // finish();
	    menu.toggle();
	    break;
	// 侧滑订单item
	case R.id.menu_myorder:
	    Intent i = new Intent();
	    i.setClass(MainActivity.this, MyOrderActivity.class);
	    startActivity(i);
	    break;
	// 侧滑我的收藏 item
	case R.id.line1:
	    Intent l1 = new Intent();
	    l1.setClass(MainActivity.this, MycollectDetailActivity.class);
	    l1.putExtra("collect", ((TextView) findViewById(R.id.collectTv1)).getText());
	    startActivity(l1);
	    break;
	// 侧滑我的优惠券item
	case R.id.line2:
	    Intent l2 = new Intent();
	    l2.setClass(MainActivity.this, MycouponDetailActivity.class);
	    l2.putExtra("coupon", ((TextView) findViewById(R.id.couponTv2)).getText());
	    startActivity(l2);
	    break;
	// 侧滑我的积分 item
	case R.id.line3:
	    Toast.makeText(getApplicationContext(), "你还没有登陆,请登陆", Toast.LENGTH_SHORT)
		    .show();
	    break;
	// 侧滑我的账户 item
	case R.id.line4:
	    Intent l4 = new Intent();
	    l4.setClass(MainActivity.this, MyaccountDetailActivity.class);
	    l4.putExtra("account", ((TextView) findViewById(R.id.accountTv4)).getText());
	    startActivity(l4);
	    break;
	// 侧滑设置 item
	case R.id.line5:
	    Intent l5 = new Intent();
	    l5.setClass(MainActivity.this, MysettingDetailActivity.class);
	    startActivity(l5);
	    break;
	case R.id.line6:
	    menu.toggle();
	    PreferencesUtils.logout_del(MainActivity.this);
	    MainActivity.this.initLeftMenu();
	    line6.setVisibility(View.GONE);
	    break;
	// 侧滑设置 个人中心
	case R.id.set_small:
	    Intent setSmall = new Intent();
	    setSmall.setClass(MainActivity.this, UserinfoActivity.class);
	    startActivity(setSmall);
	    break;
	case R.id.button_bt:
	    // ToastUtil.show(this,
	    // mLocationClient.getLastKnownLocation().getLatitude()+";;;;;"+mLocationClient.getLastKnownLocation().getLongitude());
	    // this.startService(new Intent(this, LocationServer.class));
	    Intent intent2 = new Intent();
	    intent2.setClass(this, NearActivity.class);
//	    intent2.putExtra("Latitude", la);
//	    intent2.putExtra("Longitude", lo);
	    startActivity(intent2);
	    break;
	}
    }

    public void onPause() {
//	mLocationClient.stop();
	super.onPause();
	MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
//	mLocationClient.start();
	super.onResume();
	MobclickAgent.onResume(this);
    }

    @Override
    protected void onRestart() {
	this.initLeftMenu();
	super.onRestart();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    exitBy2Click(); // 调用双击退出函数
	}
	return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    private ImageView userImg;

    private void exitBy2Click() {
	Timer tExit = null;
	if (isExit == false) {
	    isExit = true; // 准备退出
	    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
	    tExit = new Timer();
	    tExit.schedule(new TimerTask() {
		@Override
		public void run() {
		    isExit = false; // 取消退出
		}
	    }, 1000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

	} else {
	    MobclickAgent.onKillProcess(this);
	    finish();
	    System.exit(0);
	}
    }

    private Double la;
    private Double lo;

//    private void initLocation() {
//	mLocationClient = new LocationClient(getApplicationContext());
//	LocationClientOption option = new LocationClientOption();
//	option.setLocationMode(LocationMode.Battery_Saving);// 设置定位模式
//	option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
//	option.setScanSpan(10000);// 设置发起定位请求的间隔时间为5000ms
//	option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
//	option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
//	mLocationClient.setLocOption(option);
//
//	mLocationClient.registerLocationListener(new BDLocationListener() {
//	    @Override
//	    public void onReceiveLocation(BDLocation location) {
//		la = location.getLatitude();
//		lo = location.getLongitude();
//		// Log.e("main", "la"+la +"\r\n lo"+lo);
//		// ToastUtil.show(MainActivity.this, la + "ssss" + lo);
//	    }
//
//	});
//    }
}