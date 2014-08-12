package cn.icnt.dinners.fragment;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import cn.icnt.dinners.adapter.FragmentCouponAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.icnt.dinners.dinner.DetailsActivity;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.entity.Present;
import cn.icnt.dinners.entity.Present.ResultList;
import cn.icnt.dinners.entity.Result;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Constants;
import cn.icnt.dinners.utils.CustomProgressDialog;
import cn.icnt.dinners.utils.NetChecker;

public class FragmentCoupon extends Fragment {
	private static final String TAG = "FragmentCoupon";
	private static final String TAG1 = "FragmentCoupon";
	private static final String TAG3 = "FragmentCoupon";
	private CustomProgressDialog progressDialog;
	private Toast mToast;
	private boolean mIsPause;
	private ListView mlv;
	private List<Map<String, String>> list;
	private FragmentCouponAdapter adapter;
	private Result result;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_coupon, null);
		getAdInfo();

		mlv = (ListView) view.findViewById(R.id.coupon_list);
//		((TextView) findViewById(R.id.title_center_text))
//		.setText(getResources().getString(R.string.my_accounts));
		MapPackage mp = new MapPackage();
		mp.setPath("discount");
		
		mp.setHead(getActivity());
		mp.setPara("order", "1");
//		mp.setPara("company_id", 1);
//	
		mp.setPara("sub_company_id", 2);
		mp.setRes("start", "0");
		mp.setRes("count", "4");
	

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

		adapter = new FragmentCouponAdapter(getActivity(), list);
		if (list != null) {
			mlv.setAdapter(adapter);
//			mlv.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					
//					  
//	              
//					
//				}
//
//			
//			});
		}

		return view;

	}

	private TextView findViewById(int titleCenterText) {
		// TODO Auto-generated method stub
		return null;
	}

	private void getAdInfo() {
		if (!NetChecker.checkNet(getActivity())) {
//			showLoadingDialog();
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
