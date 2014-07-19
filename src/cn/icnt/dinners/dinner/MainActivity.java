/*
 * MainActivity.java
 * classes : com.cloud.app.dinner.MainActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��25�� ����9:27:36
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.MotionEvent;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.fragment.FragmentCoupon;
import cn.icnt.dinners.fragment.FragmentDish;
import cn.icnt.dinners.fragment.FragmentRes;
import cn.icnt.dinners.utils.ActivityList;
import cn.icnt.dinners.utils.CustomProgressDialog;
import cn.icnt.dinners.utils.PreferencesUtils;

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
	private LinearLayout menu_myorder;
	private EditText editSearch;
	private TextView loginTv5;
	private LinearLayout line0;
	private LinearLayout line1;
	private LinearLayout line2;
	private LinearLayout line3;
	private LinearLayout line4;
	private LinearLayout line5;
	private LinearLayout ivSet;
	private LinearLayout left_menu_l0;

	private InputMethodManager manager;

	private CustomProgressDialog progressDialog;

	private Intent intent;
	private android.support.v4.app.FragmentManager manage;

	private SharedPreferences mainShared = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		manage = getSupportFragmentManager();
		setContentView(R.layout.right_content);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mainShared = getApplicationContext().getSharedPreferences(PreferencesUtils.Keys.USERINFO,
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

		initClick();

		// SlidingMenu menu = new SlidingMenu(this);
		// menu.setMode(SlidingMenu.LEFT);

	}

	// 监听软键盘消失
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	// 侧滑菜单事件绑定
	protected void initClick() {

		// 登陆注册事件
		loginTv5 = (TextView) findViewById(R.id.login);
		loginTv5.setOnClickListener(this);
		// 登陆注册事件
		left_menu_l0 = (LinearLayout) findViewById(R.id.left_menu_l0);
		left_menu_l0.setOnClickListener(this);
		// 首页item
		line0 = (LinearLayout) findViewById(R.id.line0);
		line0.setOnClickListener(this);

		ActivityList.activitys.add(this);

		menu_myorder = (LinearLayout) findViewById(R.id.menu_myorder);

		// 我的订单事件
		menu_myorder = (LinearLayout) findViewById(R.id.menu_myorder);

		menu_myorder.setOnClickListener(this);

		// 我的收藏item
		line1 = (LinearLayout) findViewById(R.id.line1);
		line1.setOnClickListener(this);
		// 我的优惠券item
		line2 = (LinearLayout) findViewById(R.id.line2);
		line2.setOnClickListener(this);
		// 我的积分item
		line3 = (LinearLayout) findViewById(R.id.line3);
		line3.setOnClickListener(this);
		// 我的账户item
		line4 = (LinearLayout) findViewById(R.id.line4);
		line4.setOnClickListener(this);
		// 设置item
		line5 = (LinearLayout) findViewById(R.id.line5);
		line5.setOnClickListener(this);
		// 个人中心设置
		ivSet = (LinearLayout) findViewById(R.id.set_small);
		ivSet.setOnClickListener(this);

		// SlidingMenu menu = new SlidingMenu(this);
		// menu.setMode(SlidingMenu.LEFT);!

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

	}

	private View getIndicatorView(String name, int layoutId) {
		View v = getLayoutInflater().inflate(layoutId, null);
		TextView tv = (TextView) v.findViewById(R.id.tabText);
		tv.setText(name);
		return v;
	}

	protected String nickName() {
		String nickName = "";
//		String nickName =PreferencesUtils.getValueFromSPMap(getApplicationContext(),
//				PreferencesUtils.Keys.NICKNAME, "");
//		if (PreferencesUtils.getValueFromSPMap(getApplicationContext(),
//				PreferencesUtils.Keys.NICKNAME, "").equals("")) {
//			nickName = PreferencesUtils.getValueFromSPMap(
//					getApplicationContext(), PreferencesUtils.Keys.ACCOUNT,
//					"登陆/注册");
//
//		} 
			nickName = PreferencesUtils.getValueFromSPMap(
					getApplicationContext(), PreferencesUtils.Keys.NICKNAME,
					PreferencesUtils.getValueFromSPMap(
							getApplicationContext(), PreferencesUtils.Keys.EMAIL,
							"登陆/注册"));


		return nickName;
	}

	protected void initLeftMenu() {

		// String orderStv0 = mainShared.getString("order", "0");
		// String collectStv1 = mainShared.getString("collect", "0");
		// String couponStv2 = mainShared.getString("coupon", "0");
		// String creditsStv3 = mainShared.getString("credits", "0");
		// String accountStv4 = mainShared.getString("account", "0");
		// String loginStv5 = mainShared.getString("login", "登陆/注册");
		((TextView) findViewById(R.id.orderTv0)).setText(mainShared.getString(
				PreferencesUtils.Keys.ORDERNO_NO, "0"));
		;
		((TextView) findViewById(R.id.collectTv1)).setText(mainShared
				.getString(PreferencesUtils.Keys.COLLECT_NO, "0"));
		((TextView) findViewById(R.id.couponTv2)).setText(mainShared.getString(
				PreferencesUtils.Keys.COUPON_NO, "0"));
		((TextView) findViewById(R.id.creditsTv3)).setText(mainShared
				.getString(PreferencesUtils.Keys.CREDITS_NO, "0"));
		((TextView) findViewById(R.id.accountTv4)).setText(mainShared
				.getString(PreferencesUtils.Keys.ACCOUNT_NO, "0"));
		((TextView) findViewById(R.id.login)).setText(this.nickName());
		// this.orderTv0.setText(orderStv0);
		// this.collectTv1.setText(collectStv1);
		// this.couponTv2.setText(couponStv2);
		// this.creditsTv3.setText(creditsStv3);
		// this.accountTv4.setText(accountStv4);
		// this.loginTv5.setText(loginStv5);

	}

	// private void doBack() {
	// AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
	// localBuilder.setTitle("提示").setMessage("确定退出小皮手游助手?");
	// localBuilder.setPositiveButton(R.string.ok,
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// cn.icnt.dinners.utils.ActivityList.destoryActivity(cn.icnt.dinners.utils.ActivityList.activitys);
	// }
	//
	// });
	// localBuilder.setNegativeButton(R.string.cancel, null);
	// localBuilder.create().show();
	// }
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
		// 首页侧滑按钮
		case R.id.left_button:
			manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			menu.toggle();
			break;
		// 首页搜索按钮
		case R.id.right_search_button:
			manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			if (editSearch.getVisibility() == View.GONE) {
				editSearch.setVisibility(View.VISIBLE);
			} else {
				editSearch.setVisibility(View.GONE);
			}
			break;
		// 侧滑首页item
		case R.id.line0:
			menu.toggle();
			break;
		// 侧滑注册登录
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
		// 侧滑订单item
		case R.id.menu_myorder:
			if (mainShared.getString("uid", "-1").equals("-1") != true) {
				Intent i = new Intent();
				i.setClass(MainActivity.this, MyOrderActivity.class);
				startActivity(i);
			} else {
				Toast.makeText(getApplicationContext(), "你还没有登陆,请登陆",
						Toast.LENGTH_SHORT).show();
			}

			break;
		// 侧滑我的收藏 item
		case R.id.line1:
			if (mainShared.getString("uid", "-1").equals("-1") != true) {
				Intent l1 = new Intent();
				l1.setClass(MainActivity.this, MycollectDetailActivity.class);
				l1.putExtra("collect",
						((TextView) findViewById(R.id.collectTv1)).getText());
				startActivity(l1);
			} else {
				Toast.makeText(getApplicationContext(), "你还没有登陆,请登陆",
						Toast.LENGTH_SHORT).show();
			}
			break;
		// 侧滑我的优惠券item
		case R.id.line2:
			if (mainShared.getString("uid", "-1").equals("-1") != true) {
				Intent l2 = new Intent();
				l2.setClass(MainActivity.this, MycouponDetailActivity.class);
				l2.putExtra("coupon",
						((TextView) findViewById(R.id.couponTv2)).getText());
				startActivity(l2);
			} else {
				Toast.makeText(getApplicationContext(), "你还没有登陆,请登陆",
						Toast.LENGTH_SHORT).show();
			}
			break;
		// 侧滑我的积分 item
		case R.id.line3:
			if (mainShared.getString("uid", "-1").equals("-1") != true) {

				Toast.makeText(getApplicationContext(), "积分兑换即将上线,敬请期待",
						Toast.LENGTH_SHORT).show();
				// Intent l3 = new Intent();
				// l3.setClass(MainActivity.this, MycreditDetailActivity.class);
				// l3.putExtra("credits",
				// ((TextView) findViewById(R.id.creditsTv3)).getText());
				// startActivity(l3);
			} else {
				Toast.makeText(getApplicationContext(), "你还没有登陆,请登陆",
						Toast.LENGTH_SHORT).show();
			}
			break;
		// 侧滑我的账户 item
		case R.id.line4:
			if (mainShared.getString("uid", "-1").equals("-1") != true) {
				Intent l4 = new Intent();
				l4.setClass(MainActivity.this, MyaccountDetailActivity.class);
				l4.putExtra("account",
						((TextView) findViewById(R.id.accountTv4)).getText());
				startActivity(l4);
			} else {
				Toast.makeText(getApplicationContext(), "你还没有登陆,请登陆",
						Toast.LENGTH_SHORT).show();
			}
			break;
		// 侧滑设置 item
		case R.id.line5:
			if (mainShared.getString("uid", "-1").equals("-1") != true) {
				Intent l5 = new Intent();
				l5.setClass(MainActivity.this, MysettingDetailActivity.class);
				startActivity(l5);
			} else {
				Toast.makeText(getApplicationContext(), "你还没有登陆,请登陆",
						Toast.LENGTH_SHORT).show();
			}
			break;
		// 侧滑设置 个人中心
		case R.id.set_small:
			if (mainShared.getString("uid", "-1").equals("-1") != true) {
				Intent setSmall = new Intent();
				setSmall.setClass(MainActivity.this, UserinfoActivity.class);
				startActivity(setSmall);
				break;
			} else {
				Toast.makeText(getApplicationContext(), "你还没有登陆,请登陆",
						Toast.LENGTH_SHORT).show();
			}

		default:
			break;
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			doBack();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private void doBack() {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("提示").setMessage("确定退出食尚聚惠?");
		localBuilder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ActivityList.destoryActivity(ActivityList.activitys);

					}

				});
		localBuilder.setNegativeButton(R.string.ca, null);
		localBuilder.create().show();
	}

	public void showLoadingDialog() {
		if (isFinishing())
			return;

		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
			progressDialog.setMessage(R.string.loading);
		}
		progressDialog.show();

	}

	public void dismissLoadingDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}