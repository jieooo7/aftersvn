/*
 * FragmentAlipay.java
 * classes : cn.icnt.dinners.fragment.FragmentAlipay
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年8月12日 下午3:42:51
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.fragment;

import cn.icnt.dinners.dinner.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * cn.icnt.dinners.fragment.FragmentAlipay
 * @author Andrew Lee <br/>
 * create at 2014年8月12日 下午3:42:51
 */
public class FragmentAlipay extends Fragment{
	private static final String TAG = "FragmentAlipay";
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		        // Inflate the layout for this fragment
		 return inflater.inflate(R.layout.myaccount_fragment_alipay, container, false);

}
}
