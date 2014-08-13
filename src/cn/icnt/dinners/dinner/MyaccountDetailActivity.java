/*
 * MyaccountDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MyaccountDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月14日 上午10:21:30
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.List;

import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.MyOrderActivity.MyOnPageChangeListener;
import cn.icnt.dinners.fragment.FragmentAlipay;
import cn.icnt.dinners.fragment.FragmentBank;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * cn.icnt.dinners.dinner.MyaccountDetailActivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年7月14日 上午10:21:30
 */
public class MyaccountDetailActivity extends FragmentActivity implements
		OnClickListener {
	private static final String TAG = "MyaccountDetailActivity";
	private LinearLayout lv1;
	private TextView tv1;
	private TextView tv2;
	private boolean flag=true;
	private boolean flag1=true;
	private boolean flag2=true;
	private TextView itemTv1;
	private TextView itemTv2;
	private TextView itemTv3;
	private TextView itemTv4;
	private TextView itemTv5;
	private TextView itemTv6;
	private LinearLayout line0;
	private LinearLayout line1;
	private RelativeLayout title;
	private InputMethodManager manager;
	private EditText et;
	private Button bt;
	

	private FragmentTransaction transaction;
	private FragmentAlipay alipay;
	private FragmentBank bank;
	private Bundle savedInstanceState;

	private Intent intent;
	private String account;

	private ViewPager viewPager;
	private List<View> listViews;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount_detail);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		this.savedInstanceState = savedInstanceState;
		// 设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.my_account));

		title = (RelativeLayout) findViewById(R.id.title_left_btn);
		title.setOnClickListener(this);
		
		
		

//		flag = true;

		lv1 = (LinearLayout) findViewById(R.id.myaccount_lv1);


		listViews = new ArrayList<View>();
		listViews.add(View.inflate(getApplicationContext(),
				R.layout.myaccount_viewpager_recharge, null));
		listViews.add(View.inflate(getApplicationContext(),
				R.layout.myaccount_viewpager_get_money, null));

		viewPager = (ViewPager) findViewById(R.id.account_pager);

		viewPager.setAdapter(new MyPagerAdapter(listViews));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		InitTextView();
		// Intent intent = getIntent();
		// String account = "  " + intent.getStringExtra("account") + "元";
		// ((TextView) findViewById(R.id.myaccount_money)).setText(account);
		// flag = true;
		// // 充值 提现布局
		// lv1 = (LinearLayout) findViewById(R.id.myaccount_lv1);
		// lv2 = (LinearLayout) findViewById(R.id.myaccount_lv2);
		// title = (RelativeLayout) findViewById(R.id.title_left_btn);
		// title.setOnClickListener(this);
		// lv1.setOnClickListener(this);
		// lv2.setOnClickListener(this);
		// tv1 = (TextView) findViewById(R.id.myaccount_tv1);
		// tv2 = (TextView) findViewById(R.id.myaccount_tv2);
		//
		// // 银行 支付宝支付 提现
		// itemTv1 = (TextView) findViewById(R.id.myaccount_alipay_recharge);
		// itemTv2 = (TextView) findViewById(R.id.myaccount_bank_recharge);
		// // itemTv1.setOnClickListener(this);
		// // itemTv2.setOnClickListener(this);
		//
		// // 四层布局
		// line0 = (LinearLayout) findViewById(R.id.myaccount_line0);
		// line1 = (LinearLayout) findViewById(R.id.myaccount_line1);
		// line2 = (LinearLayout) findViewById(R.id.myaccount_line2);
		// line3 = (LinearLayout) findViewById(R.id.myaccount_line3);
		//
		// // 支付宝包裹
		// lv3 = (LinearLayout) findViewById(R.id.myaccount_alipay_ll);
		// lv3.setOnClickListener(this);
		// // 银行卡包裹
		// lv4 = (LinearLayout) findViewById(R.id.myaccount_bank_ll);
		// lv4.setOnClickListener(this);

	}

	private void InitTextView() {
		tv1 = (TextView) findViewById(R.id.myaccount_tv1);
		tv2 = (TextView) findViewById(R.id.myaccount_tv2);
		tv1.setOnClickListener(new MyOnClickListener(0));
		tv2.setOnClickListener(new MyOnClickListener(1));
		
		
//		et=(EditText)findViewById(R.id.myaccount_recharge_et);
//		bt=(Button)findViewById(R.id.myaccount_recharge_bt);
//		bt.setOnClickListener(this);
		
		alipay=new FragmentAlipay();
		bank=new FragmentBank();
		

//		transaction = getSupportFragmentManager().beginTransaction();
		

	}

	private class MyPagerAdapter extends PagerAdapter {

		private List<View> mListView;

		public MyPagerAdapter(List<View> list) {
			// TODO Auto-generated method stub
			this.mListView = list;
			
		}

		// 销毁position位置的界面
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewGroup) arg0).removeView(mListView.get(arg1));

		}

		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			return this.mListView.size();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.view.PagerAdapter#isViewFromObject(android.view
		 * .View, java.lang.Object)
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			((ViewGroup) arg0).addView(mListView.get(arg1), 0);// 添加页卡
			if(arg1==0&&flag2){
				et=(EditText)findViewById(R.id.myaccount_recharge_et);
				bt=(Button)findViewById(R.id.myaccount_recharge_bt);
				bt.setOnClickListener(MyaccountDetailActivity.this);
				
				
				flag2=false;
				
				
			}
			
			return mListView.get(arg1);
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	protected class MyOnPageChangeListener implements OnPageChangeListener {

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

	private void setTextTitleSelectedColor(int arg0) {
		int count = viewPager.getChildCount();
		for (int i = 0; i < count; i++) {
			TextView mTextView = (TextView) lv1.getChildAt(i);
			if (arg0 == i) {
				mTextView.setTextColor(getResources().getColor(
						R.color.mycoupon_item));
				mTextView.setBackgroundResource(R.color.mycredit_back_select);
			} else {
				mTextView.setTextColor(getResources().getColor(
						R.color.order_tab_text));
				mTextView.setBackgroundResource(R.color.mycoupon_textback);
			}
		}
		if(arg0==0&&flag2){
			et=(EditText)findViewById(R.id.myaccount_recharge_et);
			bt=(Button)findViewById(R.id.myaccount_recharge_bt);
			bt.setOnClickListener(this);
			
			
			flag2=false;
			
			
		}
		if (arg0 == 1&&flag1) {

			intent = getIntent();
			this.account = "  " + intent.getStringExtra("account") + "元";
			itemTv1 = (TextView) findViewById(R.id.myacount_money_ali);
			itemTv1.setText(account);
			itemTv3 = (TextView) findViewById(R.id.myacount_money_bank);
			itemTv3.setText(account);
			itemTv2 = (TextView) findViewById(R.id.myaccount_overrage_ali);
			itemTv4 = (TextView) findViewById(R.id.myaccount_overrage_bank);
			itemTv5 = (TextView) findViewById(R.id.myaccount_alipay_recharge);
			itemTv6 = (TextView) findViewById(R.id.myaccount_bank_recharge);

			line0 = (LinearLayout) findViewById(R.id.myaccount_alipay_ll);
			line1 = (LinearLayout) findViewById(R.id.myaccount_bank_ll);
			line0.setOnClickListener(this);
			line1.setOnClickListener(this);

			if (findViewById(R.id.myaccount_container) != null) {
				if (savedInstanceState != null) {
					return;
				}
				

				transaction = getSupportFragmentManager().beginTransaction();

				transaction.add(R.id.myaccount_container, alipay).commit();
				flag1=false;
			}
		}
	}

	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
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
	
	
	   public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        // TODO Auto-generated method stub  
	        if(keyCode == KeyEvent.KEYCODE_BACK)  
	           {    
	               finish();      //调用双击退出函数  
	           }  
	        return false;  
	    } 

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			break;
		case R.id.myaccount_alipay_ll:
			if (!flag) {
				itemTv1.setVisibility(View.VISIBLE);
				itemTv2.setVisibility(View.VISIBLE);
				itemTv3.setVisibility(View.GONE);
				itemTv4.setVisibility(View.GONE);
				itemTv5.setTextColor(getResources().getColor(
						R.color.myaccount_text_light));
				itemTv6.setTextColor(getResources().getColor(
						R.color.myaccount_text_dark));
				transaction = getSupportFragmentManager().beginTransaction();

				transaction.replace(R.id.myaccount_container, alipay);
				transaction.addToBackStack(null);

				transaction.commit();

				flag = true;
			}

			break;
		case R.id.myaccount_bank_ll:
			if (flag) {
				itemTv3.setVisibility(View.VISIBLE);
				itemTv4.setVisibility(View.VISIBLE);
				itemTv1.setVisibility(View.GONE);
				itemTv2.setVisibility(View.GONE);
				itemTv6.setTextColor(getResources().getColor(
						R.color.myaccount_text_light));
				itemTv5.setTextColor(getResources().getColor(
						R.color.myaccount_text_dark));
				transaction = getSupportFragmentManager().beginTransaction();

				transaction.replace(R.id.myaccount_container, bank);
				transaction.addToBackStack(null);

				transaction.commit();

				flag = false;
			}

			break;
		case R.id.myaccount_recharge_bt:
			DebugUtil.i("点击测试", "我被打了");
			break;

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.myaccount_lv1:
	// // manager.hideSoftInputFromWindow(getCurrentFocus()
	// // .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	// // 点击充值 默认页面 第一个
	// flag = true;
	// tv1.setVisibility(View.VISIBLE);
	// tv2.setVisibility(View.INVISIBLE);
	// itemTv1.setText(getResources().getString(R.string.alipay_recharge));
	// itemTv1.setTextColor(getResources().getColor(
	// R.color.myaccount_text_light));
	// itemTv2.setText(getResources().getString(R.string.bank_recharge));
	// itemTv2.setTextColor(getResources().getColor(
	// R.color.myaccount_text_dark));
	// line0.setVisibility(View.VISIBLE);
	// line1.setVisibility(View.GONE);
	// line2.setVisibility(View.GONE);
	// line3.setVisibility(View.GONE);
	// break;
	// case R.id.myaccount_lv2:
	// // manager.hideSoftInputFromWindow(getCurrentFocus()
	// // .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	// // 点击提现默认页面，默认第一个
	// flag = false;
	// tv2.setVisibility(View.VISIBLE);
	// tv1.setVisibility(View.INVISIBLE);
	// itemTv1.setText(getResources().getString(R.string.alipay_get_cash));
	// itemTv1.setTextColor(getResources().getColor(
	// R.color.myaccount_text_light));
	// itemTv2.setText(getResources().getString(R.string.bank_get_cash));
	// itemTv2.setTextColor(getResources().getColor(
	// R.color.myaccount_text_dark));
	// line0.setVisibility(View.GONE);
	// line1.setVisibility(View.GONE);
	// line2.setVisibility(View.VISIBLE);
	// line3.setVisibility(View.GONE);
	// break;
	// case R.id.myaccount_alipay_ll:
	// // manager.hideSoftInputFromWindow(getCurrentFocus()
	// // .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	// if (flag) {
	// // 支付宝充值
	// itemTv1.setText(getResources().getString(
	// R.string.alipay_recharge));
	// itemTv1.setTextColor(getResources().getColor(
	// R.color.myaccount_text_light));
	// itemTv2.setText(getResources()
	// .getString(R.string.bank_recharge));
	// itemTv2.setTextColor(getResources().getColor(
	// R.color.myaccount_text_dark));
	// line0.setVisibility(View.VISIBLE);
	// line1.setVisibility(View.GONE);
	// line2.setVisibility(View.GONE);
	// line3.setVisibility(View.GONE);
	//
	// } else {
	// // 支付宝提现
	// itemTv1.setText(getResources().getString(
	// R.string.alipay_get_cash));
	// itemTv1.setTextColor(getResources().getColor(
	// R.color.myaccount_text_light));
	// itemTv2.setText(getResources()
	// .getString(R.string.bank_get_cash));
	// itemTv2.setTextColor(getResources().getColor(
	// R.color.myaccount_text_dark));
	// line0.setVisibility(View.GONE);
	// line1.setVisibility(View.GONE);
	// line2.setVisibility(View.VISIBLE);
	// line3.setVisibility(View.GONE);
	//
	// }
	//
	// break;
	// case R.id.myaccount_bank_ll:
	// // manager.hideSoftInputFromWindow(getCurrentFocus()
	// // .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	// if (!flag) {
	// // 银行提现
	// itemTv1.setText(getResources().getString(
	// R.string.alipay_get_cash));
	// itemTv1.setTextColor(getResources().getColor(
	// R.color.myaccount_text_dark));
	// itemTv2.setText(getResources()
	// .getString(R.string.bank_get_cash));
	// itemTv2.setTextColor(getResources().getColor(
	// R.color.myaccount_text_light));
	// line0.setVisibility(View.GONE);
	// line1.setVisibility(View.GONE);
	// line2.setVisibility(View.GONE);
	// line3.setVisibility(View.VISIBLE);
	//
	// } else {
	// // 银行充值
	// itemTv1.setText(getResources().getString(
	// R.string.alipay_recharge));
	// itemTv1.setTextColor(getResources().getColor(
	// R.color.myaccount_text_dark));
	// itemTv2.setText(getResources()
	// .getString(R.string.bank_recharge));
	// itemTv2.setTextColor(getResources().getColor(
	// R.color.myaccount_text_light));
	// line0.setVisibility(View.GONE);
	// line1.setVisibility(View.VISIBLE);
	// line2.setVisibility(View.GONE);
	// line3.setVisibility(View.GONE);
	//
	// }
	//
	// break;
	// case R.id.title_left_btn:
	// // manager.hideSoftInputFromWindow(getCurrentFocus()
	// // .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	// finish();
	// break;
	//
	// }
	// }

}

/*
 * 至少重写一下四个方法 instantiateItem(ViewGroup, int) destroyItem(ViewGroup, int,
 * Object) getCount() isViewFromObject(View, Object)
 */