/*
 * FragmentDish.java
 * classes : com.cloud.app.fragment.FragmentDish
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��27�� ����11:18:55
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.icnt.dinners.dinner.R;


/**
 * com.cloud.app.fragment.FragmentDish
 * @author Andrew Lee <br/>
 * create at 2014��6��27�� ����11:18:55
 */
public class FragmentDish extends Fragment{
	private static final String TAG = "FragmentDish";
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		        // Inflate the layout for this fragment
		 return inflater.inflate(R.layout.fragment_dish, container, false);

}
}
