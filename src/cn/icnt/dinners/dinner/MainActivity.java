/*
 * MainActivity.java
 * classes : com.cloud.app.dinner.MainActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��25�� ����9:27:36
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.fragment.FragmentCoupon;
import cn.icnt.dinners.fragment.FragmentDish;
import cn.icnt.dinners.fragment.FragmentRes;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * com.cloud.app.dinner.MainActivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年6月27日 下午5:39:18
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private FragmentTabHost mTabHost;
	private View indicator1 = null;
	private View indicator2 = null;
	private View indicator3 = null;
	private SlidingMenu menu;
	private RelativeLayout ivUser;
	private RelativeLayout ivSearch;
	private EditText editSearch;
	private TextView loginTv5;
	private LinearLayout line0;
	private LinearLayout line1;
	private LinearLayout line2;
	private LinearLayout line3;
	private LinearLayout line4;
	private LinearLayout line5;
	private LinearLayout line6;
	private LinearLayout left_menu_l0;

	private Intent intent;
	private FragmentManager manage;

	private SharedPreferences mainShared = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		manage = getSupportFragmentManager();
		setContentView(R.layout.right_content);
		mainShared = getApplicationContext().getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		// 写入sharepreference 操作

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, manage, R.id.realcontent);
		indicator1 = getIndicatorView("优惠信息", R.layout.tab_wid);
		mTabHost.addTab(mTabHost.newTabSpec("tab0").setIndicator(indicator1),
				FragmentCoupon.class, null);
		indicator2 = getIndicatorView("菜品推荐", R.layout.tab_wid);
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(indicator2),
				FragmentDish.class, null);
		indicator3 = getIndicatorView("餐厅推荐", R.layout.tab_wid);
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(indicator3),
				FragmentRes.class, null);

		ivUser = (RelativeLayout) findViewById(R.id.left_button);
		ivUser.setOnClickListener(this);
		ivSearch = (RelativeLayout) findViewById(R.id.right_search_button);
		ivSearch.setOnClickListener(this);
		editSearch = (EditText) findViewById(R.id.right_search_edit);

		initSlidingMenu();
		initLeftMenu();
		loginTv5 = (TextView) findViewById(R.id.login);
		loginTv5.setOnClickListener(this);
		line0 = (LinearLayout) findViewById(R.id.line0);
		line0.setOnClickListener(this);
		// SlidingMenu menu = new SlidingMenu(this);
		// menu.setMode(SlidingMenu.LEFT);

	}

	public void initSlidingMenu() {
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

		left_menu_l0 = (LinearLayout) findViewById(R.id.left_menu_l0);
		left_menu_l0.setOnClickListener(this);
	}

	private View getIndicatorView(String name, int layoutId) {
		View v = getLayoutInflater().inflate(layoutId, null);
		TextView tv = (TextView) v.findViewById(R.id.tabText);
		tv.setText(name);
		return v;
	}

	protected void initLeftMenu() {

		// String orderStv0 = mainShared.getString("order", "0");
		// String collectStv1 = mainShared.getString("collect", "0");
		// String couponStv2 = mainShared.getString("coupon", "0");
		// String creditsStv3 = mainShared.getString("credits", "0");
		// String accountStv4 = mainShared.getString("account", "0");
		// String loginStv5 = mainShared.getString("login", "登陆/注册");
		((TextView) findViewById(R.id.orderTv0)).setText(mainShared.getString(
				"order", "0"));
		;
		((TextView) findViewById(R.id.collectTv1)).setText(mainShared
				.getString("collect", "0"));
		((TextView) findViewById(R.id.couponTv2)).setText(mainShared.getString(
				"coupon", "0"));
		((TextView) findViewById(R.id.creditsTv3)).setText(mainShared
				.getString("credits", "0"));
		((TextView) findViewById(R.id.accountTv4)).setText(mainShared
				.getString("account", "0"));
		((TextView) findViewById(R.id.login)).setText(mainShared.getString(
				"login", "登陆/注册"));
		// this.orderTv0.setText(orderStv0);
		// this.collectTv1.setText(collectStv1);
		// this.couponTv2.setText(couponStv2);
		// this.creditsTv3.setText(creditsStv3);
		// this.accountTv4.setText(accountStv4);
		// this.loginTv5.setText(loginStv5);

	}

	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTabHost = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_button:
			menu.toggle();
			break;

		case R.id.right_search_button:
			if (editSearch.getVisibility() == View.GONE) {
				editSearch.setVisibility(View.VISIBLE);
			} else {
				editSearch.setVisibility(View.GONE);
			}
			break;

		case R.id.line0:
			menu.toggle();
			break;

		case R.id.login:
		case R.id.left_menu_l0:
			// android.support.v4.app.FragmentTransaction transaction =
			// manage.beginTransaction();
			// transaction.replace(R.layout.right_content, new LoginActivity(),
			// "login");
			// transaction.commit();

			intent = new Intent();
			intent.setClass(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}