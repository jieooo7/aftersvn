package cn.icnt.dinners.fragment;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import cn.icnt.dinners.adapter.FragmentCouponAdapter;
import cn.icnt.dinners.beans.OrderListBean;
import cn.icnt.dinners.dinner.MyOrderActivity;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.entity.Result;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.CustomProgressDialog;
import cn.icnt.dinners.utils.NetChecker;
import cn.icnt.dinners.utils.ToastUtil;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

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
//	MapPackage mp = new MapPackage();
//	mp.setPath("discount");
//	mp.setHead(getActivity());
//	mp.setPara("order", "1");
//	mp.setPara("sub_company_id", 2);
//	mp.setRes("start", "0");
//	mp.setRes("count", "10");
//	try {
//	    mp.send();
//
//	} catch (Exception e) {
//	    if (HttpSendRecv.netStat)
//		Toast.makeText(getActivity(), "网络错误，请重试", Toast.LENGTH_LONG).show();
//	    else
//		Toast.makeText(getActivity(), "出错了^_^", Toast.LENGTH_LONG).show();
//	} finally {
//	}
//	list = mp.getBackResult();
//	adapter = new FragmentCouponAdapter(getActivity(), list);
//	if (list != null) {
//	    mlv.setAdapter(adapter);
//	}
	initData();
	return view;
    }

    private void getAdInfo() {
	if (!NetChecker.checkNet(getActivity())) {
	    return;
	}
    }

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
    private void initData() {
	MapPackage mp = new MapPackage();
	mp.setHead(getActivity());
	mp.setRes("start", 0);
	mp.setRes("count", 10);
	Map<String, Object> maps = mp.getMap();
	RequestParams params = GsonTools.GetParams(maps);

	HttpUtils http = new HttpUtils();
	http.send(HttpRequest.HttpMethod.POST, Container.MYORDERLIST, params,
		new RequestCallBack<String>() {
		    @Override
		    public void onStart() {
		    }

		    @Override
		    public void onLoading(long total, long current, boolean isUploading) {
		    }

		    @Override
		    public void onSuccess(ResponseInfo<String> responseInfo) {
			Log.i("order", responseInfo.result);
			Gson gson = new Gson();
			OrderListBean jsonBean = gson.fromJson(responseInfo.result,
				OrderListBean.class);
			if (jsonBean != null) {
			    if (StringUtils.equals("10000", jsonBean.head.code)) {
			    }
			}
		    }
		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});

    }
}
