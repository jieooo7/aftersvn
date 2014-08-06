package cn.icnt.dinners.server;

//www.javaapk.com
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import cn.icnt.dinners.adapter.FragmentCouponAdapter;
import cn.icnt.dinners.adapter.SurroundingAdapter;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;

import com.baidu.location.LocServiceMode;
import com.baidu.location.LocationChangedListener;
import com.baidu.location.LocationClient;
import com.baidu.location.ReceiveListener;


import cn.icnt.dinners.dinner.DetailsActivity;
import cn.icnt.dinners.dinner.NearActivity;
import cn.icnt.dinners.entity.Mopp;
import cn.icnt.dinners.entity.Result;

public class LocationServer extends Service  {

	// 错误标记
	private static String TAG = "locationApplicationBeanError";
	// 时间间隔
	private static int myTime = 5 * 1000;
	private LocationClient mLocationClient = null; // 定位类
	private String mData; // 获取的数据
	// 定位时间间隔
	private int myLocationTime = 5 * 1000;
	// 是否启动了定位API
	private boolean isOpenLocation = false;
	// 是否启动了定位线程
	private boolean isOpenLocationTask = false;
	// 经纬度
	private double jingweidu[] = new double[2];
	public String longitude;
	public String latitude;
	

	@Override
	public void onCreate() {
		mLocationClient = new LocationClient(this);
		
		openLocationTask();
		
		
	}

	/***
	 * 打开定位定时器线程
	 */
	public void openLocationTask() {

		try {
			if (!isOpenLocationTask) // /如果不是打开状态，则打开线程
			{
				startLocation();// 启动定位更新经纬度
				// 开启定时器
				initLocationTimeAndTimeTask(); // 初始化定时器和定时线程
				myLocationTimer.schedule(myLocationTimerTask, myTime);
				Log.i(TAG, " 打开了定位定时器线程 ");
				isOpenLocationTask = true; // 标记为打开了定时线程
			} else {
				Log.i(TAG, " 已经开启了定位定时器线程 ");
			}
		} catch (Exception e) {
			Log.i(TAG, "打开定位定时器线程 异常" + e.toString());
		}
	}

	/**
	 * start定位
	 */
	private void startLocation() {
		try {
			if (!isOpenLocation) // 如果没有打开
			{
				mLocationClient.setCoorType("bd09ll"); // 设置返回的坐标类型
				mLocationClient.setTimeSpan(myLocationTime); // 设置时间
				mLocationClient.setAddrType("street_number"); // 返回地址类型
				mLocationClient.setServiceMode(LocServiceMode.Immediat); // 定位方式为：即时定位
				mLocationClient.addRecerveListener(new MyReceiveListenner());
				mLocationClient.start(); // 打开定位
				isOpenLocation = true; // 标识为已经打开了定位
			}
		} catch (Exception e) {
			Log.i(TAG, "打开定位异常" + e.toString());
		}
	}

	/***
	 * 初始化 time 对象 和 timetask 对象
	 */
	private void initLocationTimeAndTimeTask() {
		initLocationTime();
		initLocationTimeTask();
	}

	/***
	 * 初始化定时器
	 */
	public void initLocationTime() {
		if (myLocationTimer == null) {
			Log.i(TAG, "myLocationTimer 已经被清空了");
			myLocationTimer = new Timer();
		} else {
			Log.i(TAG, "myLocationTimer 已经存在");
		}
	}

	/***
	 * 初始化 定时器线程
	 */
	private void initLocationTimeTask() {
		myLocationTimerTask = new TimerTask() {
			/***
			 * 定时器线程方法
			 */
			@Override
			public void run() {
				handler.sendEmptyMessage(1); // 发送消息
			}
		};
	}

	/***
	 * 定时器的回调函数
	 */
	private Handler handler = new Handler() {
		// 更新的操作
		@Override
		public void handleMessage(Message msg) {
			getLocationInfo(); // 获取经纬度

			Log.i(TAG, "调用了获取经纬度方法");
			super.handleMessage(msg);
		}
	};

	/***
	 * 获取经纬度
	 */
	public void getLocationInfo() {
		/**
		 * 0：正常。
		 * 
		 * 1：SDK还未启动。
		 * 
		 * 2：没有监听函数。
		 * 
		 * 6：请求间隔过短。
		 */
		int i = mLocationClient.getLocation();
		String TAGfont = "getLocationInfo() : ";
		switch (i) {
		case 0:
			Log.i(TAG, TAGfont + "正常。");
			break;
		case 1:
			Log.i(TAG, TAGfont + "SDK还未启动。");
			break;
		case 2:
			Log.i(TAG, TAGfont + "没有监听函数。 ");
			break;
		case 6:
			Log.i(TAG, TAGfont + "请求间隔过短。 ");
			break;
		default:
			Log.i(TAG, TAGfont + "其他原因	");
		}
	}

	// 获取到经纬的回调操作
	public void logMsg(String str) {
		try {
			mData = str.trim();
			/**
			 * { "result":{ "time":"2011-10-11 17:06:07","error":"161"},
			 * "content":{
			 * "point":{"x":"117.13045277196","y":"39.104208211327"},
			 * "":"130", "addr":{"street_number":"206号"} } } }
			 */

			Log.i(TAG, "进入了定位 定时器 更新了经纬度方法  -- 信息：" + mData);
			// 解析经纬度
			JSONObject jsonObject = new JSONObject(mData);
			JSONObject jsonjingweidu = jsonObject.getJSONObject("content")
					.getJSONObject("point");
			longitude = jsonjingweidu.getString("y");
			latitude = jsonjingweidu.getString("x");
			jingweidu = new double[] { stringToDouble(longitude),
					stringToDouble(latitude) };
			Log.i(TAG, "longitude :" + jingweidu[0] + "latitude : "
					+ jingweidu[1]);

			int r = setLocalJingweidu(); // 经纬度保存到本地
			if (r == 1) {
				double[] temp = getLocalJingweidu();
				Log.i(TAG, "保存经纬度到本地成功  ,经度：" + temp[0] + "纬度：" + temp[1]);

				Toast.makeText(this, "经度：" + temp[0] + "纬度：" + temp[1], 2000)
						.show();
			

				
			} else {
				Log.i(TAG, "保存经纬度到本地失败");
			}
		} catch (Exception e) {
			Log.i(TAG, "更新操作异常" + e.toString());
		}
		
//		return str;
	}

	/***
	 * 将 string 转换成 double 类型
	 * 
	 * @param str
	 * @return
	 */
	private double stringToDouble(String str) {
		double v = 0.0;
		try {
			if (str != null && !str.trim().equals("")) {
				v = Double.parseDouble(str.trim());
			}
		} catch (NumberFormatException e) {
			Log.i(TAG, "string转换成double 失败 ");
		}

		return v;
	}

	// 位置发生改变
	private class MyLocationChangedListener implements LocationChangedListener {
		@Override
		public void onLocationChanged() {
			// logMsg("LocationChangedListener: ");
		}
	}

	// 接受定位得到的消息
	private class MyReceiveListenner implements ReceiveListener {
		@Override
		public void onReceive(String strData) {
			logMsg(strData); // 调用回调函数
		}
	}

	/**
	 * end 定位
	 */
	 public void closeLocation() {
		try {
			mLocationClient.stop(); // 结束定位
			isOpenLocation = false; // 标识为已经结束了定位
		} catch (Exception e) {
			Log.i(TAG, "结束定位异常" + e.toString());
		}
	}

	// 定时器
	private Timer myLocationTimer = null;
	// 定时线程
	private TimerTask myLocationTimerTask = null;

	/***
	 * 销毁 time 对象 和 timetask 对象
	 */
	private void destroyLocationTimeAndTimeTask() {
		myLocationTimer = null;
		myLocationTimerTask = null;
	}

	/***
	 * 关闭定位定时器线程
	 */
	public void closeLocationTask() {
		try {
			if (isOpenLocationTask) // 如果是打开状态，则关闭
			{
				closeLocation();
				// 关闭定时器
				myLocationTimer.cancel();
				destroyLocationTimeAndTimeTask();

				Log.i(TAG, " 关闭了定位定时器线程 ");
				isOpenLocationTask = false; // 标记为关闭了定时线程
			} else {
				Log.i(TAG, " 已经关闭了定位定时器线程 ");
			}

		} catch (Exception e) {
			Log.i(TAG, "关闭定位定时器线程异常: " + e.toString());
		}
	}

	/***
	 * 将经纬度保存在本地
	 * 
	 * @return
	 */

	private int setLocalJingweidu() {

		int r = 0;
		try {
			// 获取活动的 preferences 对象
			SharedPreferences usersetting = getApplicationContext()
					.getSharedPreferences("test", Activity.MODE_PRIVATE);
			// 获取编辑对象
			Editor userinfoeditor = usersetting.edit();
			userinfoeditor.putString("Location_longitude", jingweidu[0] + "");// 经度
			userinfoeditor.putString("Location_latitude", jingweidu[1] + ""); // 纬度
			userinfoeditor.commit();
			return 1;
			
		

		} catch (Exception e) {
			Log.i(TAG, "  保存经纬度，到本地失败" + e.toString());
			r = 0;
		}
		return r;
		
	}

	/***
	 * 获取本地的经纬度
	 * 
	 * @return
	 */
	public double[] getLocalJingweidu() {
		double[] jinweidu = new double[] { 0, 0 };
		try {
			// 获取活动的 preferences 对象
			SharedPreferences usersetting = getApplicationContext()
					.getSharedPreferences("test", Activity.MODE_PRIVATE);
			double longitude = Double.parseDouble(usersetting.getString(
					"Location_longitude", "0")); // 经度
			Log.i(TAG, "空值异常" + longitude);
			double latitude = Double.parseDouble(usersetting.getString(
					"Location_latitude", "0")); // 纬度
			Log.i(TAG, "空值异常" + latitude);
			jinweidu[0] = longitude;
		 jinweidu[1] = latitude;
		} catch (NumberFormatException e) {
			jinweidu = new double[] { 0, 0 };
			Log.i(TAG, "空值异常" + e.toString());
		} catch (Exception e) {
			jinweidu = new double[] { 0, 0 };
			Log.i(TAG, "获取异常" + e.toString());
		}
		Intent intent = new Intent(this, NearActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("mopp",new Mopp(longitude, latitude));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtras(bundle);
		startActivity(intent);
		return jinweidu;
		
	
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}
}
