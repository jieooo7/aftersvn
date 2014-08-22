package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.adapter.SurroundingAdapter;
import cn.icnt.dinners.beans.HomeDishesBean.HomeDishesdesc;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.ToastUtil;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.analytics.MobclickAgent;

public class NearActivity extends Activity implements android.view.View.OnClickListener {

    public ListView mlist;

    private List<Map<String, String>> list;
    private SurroundingAdapter adapter;
    public LocationClient mLocationClient = null;
    private Double la;
    private Double lo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_surrounding);
	initLocation();
	mlist = (ListView) this.findViewById(R.id.surrounding_list);
	((TextView) findViewById(R.id.title_center_text)).setText("我的周边");
	((RelativeLayout) findViewById(R.id.title_left_btn)).setOnClickListener(this);
	// Bundle bundle = getIntent().getExtras();
	// Mopp mopp = (Mopp) bundle.getSerializable("mopp");
	MapPackage mp = new MapPackage();
	mp.setPath("near_company.do");
	mp.setHead(this);
	ToastUtil.show(NearActivity.this, la
		+ "========================================" + lo);
    }

    // list = mp.getBackResult();
    // adapter = new SurroundingAdapter(this, list);
    // if (list != null) {
    // mlist.setAdapter(adapter);

    private void initLocation() {
	mLocationClient = new LocationClient(getApplicationContext());
	LocationClientOption option = new LocationClientOption();
	option.setLocationMode(LocationMode.Battery_Saving);// 设置定位模式
	option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
	option.setScanSpan(10000);// 设置发起定位请求的间隔时间为5000ms
	option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
	option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
	mLocationClient.setLocOption(option);

	mLocationClient.registerLocationListener(new BDLocationListener() {


	    @Override
	    public void onReceiveLocation(BDLocation location) {
		la = location.getLatitude();
		lo = location.getLongitude();
		
		Log.e("main", "la" + la + "\r\n lo" + lo);
		// ToastUtil.show(MainActivity.this, la + "ssss" + lo);
		initData(la, lo);
	    }

	});
    }

    private void initData(Double la2, Double lo2) {
	MapPackage mp = new MapPackage();
	mp.setHead(this);
	mp.setPara("order", 1);
	mp.setRes("start", "0");
	mp.setRes("count", "10");
	mp.setPara("latitude", la2 + "");
	mp.setPara("longitude", lo2 + "");
	Map<String, Object> maps = mp.getMap();
	RequestParams params = GsonTools.GetParams(maps);
	HttpUtils http = new HttpUtils();
	http.send(HttpRequest.HttpMethod.POST, Container.CIRCUM, params,
		new RequestCallBack<String>() {
		    private Intent intent;
		    private Bundle bundle;
		    private List<HomeDishesdesc> lists;

		    @Override
		    public void onStart() {
		    }

		    @Override
		    public void onLoading(long total, long current, boolean isUploading) {
		    }

		    @Override
		    public void onSuccess(ResponseInfo<String> responseInfo) {
			Log.e("orders", "CIRCUM\r\n" + responseInfo.result);
		    }

		    @Override
		    public void onFailure(HttpException error, String msg) {
		    }
		});
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
	if (event.getAction() == KeyEvent.ACTION_DOWN
		&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	    finish();
	    return true;
	}
	return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {

	case R.id.title_left_btn:
	    finish();
	    break;

	}

    }

    public void onResume() {
	mLocationClient.start();
	super.onResume();
	MobclickAgent.onResume(this);
    }

    public void onPause() {
	mLocationClient.stop();
	super.onPause();
	MobclickAgent.onPause(this);
    }
}
