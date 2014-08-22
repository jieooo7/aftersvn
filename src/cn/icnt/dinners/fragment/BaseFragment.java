/*
 * FragmentDish.java
 * classes : com.cloud.app.fragment.FragmentDish
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��27�� ����11:18:55
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.icnt.dinners.beans.HomeDishesBean;
import cn.icnt.dinners.beans.HomeDishesBean.HomeDishesdesc;
import cn.icnt.dinners.dinner.DishesDetailActivity;
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
 * 菜品推荐
 * 
 * @author Administrator
 * 
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {
    protected Context ct;
    public View rootView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

	super.onActivityCreated(savedInstanceState);
	initData(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	ct = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	rootView = initView(inflater);
	return rootView;
    }

    public View getRootView() {
	return rootView;
    }

    @Override
    public void onClick(View arg0) {
	// TODO Auto-generated method stub

    }

    protected abstract View initView(LayoutInflater inflater);

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void processClick(View v);
}
