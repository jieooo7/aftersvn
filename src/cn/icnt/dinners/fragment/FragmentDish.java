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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import cn.icnt.dinners.adapter.FragmentCouponAdapter;
import cn.icnt.dinners.adapter.FragmentDishAdapter;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.CustomProgressDialog;
import cn.icnt.dinners.utils.NetChecker;


/**
 * com.cloud.app.fragment.FragmentDish
 * @author Andrew Lee <br/>
 * create at 2014��6��27�� ����11:18:55
 */
public class FragmentDish extends Fragment{
	private static final String TAG = "FragmentCoupon";
	private CustomProgressDialog progressDialog;
	private Toast mToast;
	private boolean mIsPause;
	private ListView mlv;
	private List<Map<String, String>> list;
	private FragmentDishAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_dish, null);
		getAdInfo();

		mlv = (ListView) view.findViewById(R.id.dish_list);

		MapPackage mp = new MapPackage();
		mp.setPath("goods.do?");
		System.out.println("111111111111111111");
		mp.setHead(getActivity());
		mp.setPara("order", "1");
		mp.setRes("start", "1");
		mp.setRes("count", "3");

		try {
			mp.send();

		} catch (Exception e) {
			if (HttpSendRecv.netStat)
				Toast.makeText(getActivity(), "网络错误，请重试", Toast.LENGTH_LONG)
						.show();
			else
				Toast.makeText(getActivity(), "出错了^_^", Toast.LENGTH_LONG)
						.show();

		} finally {

		}
		list = mp.getBackResult();

		adapter = new FragmentDishAdapter(getActivity(), list);
		if (list != null) {
			mlv.setAdapter(adapter);

		}

		return view;

	}

	private void getAdInfo() {
		if (!NetChecker.checkNet(getActivity())) {
			showLoadingDialog();
			return;
		}

	}

	// private Handler handler = new Handler()
	// private Runnable r = new Runnable() {
	// @Override
	// public void run() {
	// try {
	// Thread.sleep(2000);
	// dismissLoadingDialog();
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	//
	// }
	//

	public void toastIfActive(int errorNetworkAvailable) {
		if (!this.mIsPause) {
			if (mToast == null) {
				mToast = Toast.makeText(getActivity(), errorNetworkAvailable,
						Toast.LENGTH_SHORT);
				mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			} else {
				mToast.setText(errorNetworkAvailable);
			}
			mToast.show();
		}
	}

	public void showLoadingDialog() {
		if (getActivity().isFinishing())
			return;

		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(getActivity());
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


