package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import cn.icnt.dinners.adapter.FragmentCouponAdapter;
import cn.icnt.dinners.entity.Present;

import cn.icnt.dinners.entity.Present.ResultList;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.ActivityList;
import cn.icnt.dinners.utils.Constants;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.http.client.HttpRequest;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity implements OnClickListener {
//	private TextView locationInfoTextView = null;
//	private Button startButton = null;
//	private List<Map<String, String>> list;

	private ImageView mCollection;
	private ImageView mComments;
	private ImageView mShare;
	private ImageView mOrder;
	private ListView mlv;
	// MyBaiduLotion myLotion;
//	MyLocation myLocation;
//	String strlocation = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_item);
//
//		locationInfoTextView = (TextView) this
//				.findViewById(R.id.title_center_text);
		mCollection = (ImageView) this.findViewById(R.id.iv_collection);
		mComments = (ImageView) this.findViewById(R.id.iv_comments);
		mShare = (ImageView) this.findViewById(R.id.iv_share);
		mOrder = (ImageView) this.findViewById(R.id.iv_order);
		mCollection.setOnClickListener(this);
		mComments.setOnClickListener(this);
		mShare.setOnClickListener(this);
		mOrder.setOnClickListener(this);
		
		  long lv_item_id = getIntent().getLongExtra("lv_item_id", -1);
	        if(lv_item_id != -1) {

	    	System.out.println(lv_item_id);

	    		MapPackage mp = new MapPackage();
	    		mp.setPath("discount");
	    		
	    		mp.setHead(this);
	    		mp.setPara("order", "1");
	    		mp.setRes("start", "1");
	    		mp.setRes("count", "3");

	    		try {
	    			mp.send();

	    		} catch (Exception e) {
	    			if (HttpSendRecv.netStat)
	    				Toast.makeText(this, "网络错误，请重试", Toast.LENGTH_LONG)
	    						.show();
	    			else
	    				Toast.makeText(this, "出错了^_^", Toast.LENGTH_LONG)
	    						.show();

	    		} finally {

	    		}
	    		

	    		}
	        }
		//
		// startButton.setOnClickListener(new OnClickListener() {

		// @Override
		// public void onClick(View v) {
		// myLotion = new MyBaiduLotion(DetailsActivity.this);
		// myLocation = new MyLocation();
		// myLotion.opetateClient();
		// new LocationTHread().start();
		// }
		// });
		//
		// }

		// class LocationTHread extends Thread {
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// super.run();
		// if (myLotion != null)
		// while (!myLotion.getIsFinish()) {
		// try {
		// sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// if (myLotion.myBDcoordinate != null) {
		// strlocation = myLocation.getAddress(
		// myLotion.getLatValue() + "", myLotion.getLongValue()
		// + "");
		// myHandler.sendEmptyMessage(1);
		// }
		//
		// }

//	}

//	Handler myHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			locationInfoTextView.setText(strlocation);
//		}
//
//	};

//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		// myLotion.desClient();
//	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.iv_collection:
//			Intent intent=new Intent(this,ShareActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.iv_comments:
//			Intent intent11=new Intent(this,ShareActivity.class);
//			startActivity(intent11);
//			break;
//		case R.id.iv_share:
//			Intent intent1=new Intent(this,ShareActivity.class);
//			startActivity(intent1);
//			break;
//		case R.id.iv_order:
//			Intent intent111=new Intent(this,ShareActivity.class);
//			startActivity(intent111);
//			break;
//
//		
//		}
//
//	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_collection:
			Toast.makeText(this,"收藏成功", 0).show();
			break;
		case R.id.iv_comments:
			Toast.makeText(this,"评论成功", 0).show();
			break;
		case R.id.iv_share:
			Intent intent1=new Intent(this,ShareActivity.class);
			startActivity(intent1);
			break;
		case R.id.iv_order:
			Toast.makeText(this,"点餐成功", 0).show();
			break;

		
		}

	}

}
