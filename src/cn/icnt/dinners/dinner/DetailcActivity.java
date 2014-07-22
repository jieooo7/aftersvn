package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.icnt.dinners.adapter.FragmentCouponAdapter;
import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.entity.Dishes;
import cn.icnt.dinners.entity.Present;
import cn.icnt.dinners.entity.Result;

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
import android.widget.TextView.BufferType;
import android.widget.Toast;

public class DetailcActivity extends Activity implements OnClickListener {
	// private TextView locationInfoTextView = null;
	// private Button startButton = null;
	// private List<Map<String, String>> list;

	private ImageView mCollection;
	private ImageView mComments;
	private ImageView mShare;
	private ImageView mOrder;
	private ListView mlv;
	private ImageView mtv;
	private TextView mtv1;
	private TextView mtv2;
	private TextView mtv3;
	private TextView mtv4;
	private TextView mtv5;
	private TextView mtv6;
	private ImageLoader mImageLoader;

	// MyBaiduLotion myLotion;
	// MyLocation myLocation;
	// String strlocation = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailc_item);
		//
		// locationInfoTextView = (TextView) this
		// .findViewById(R.id.title_center_text);
		mCollection = (ImageView) this.findViewById(R.id.iv_collection);
		mComments = (ImageView) this.findViewById(R.id.iv_comments);
		mShare = (ImageView) this.findViewById(R.id.iv_share);
		mOrder = (ImageView) this.findViewById(R.id.iv_order);
		mtv = (ImageView) this.findViewById(R.id.iv_images);
		mtv1 = (TextView) this.findViewById(R.id.tv_text_food);
		mtv2 = (TextView) this.findViewById(R.id.tv_remaind);
		mtv3 = (TextView) this.findViewById(R.id.tv_data);
		mtv4 = (TextView) this.findViewById(R.id.tv_details_activity);
		mtv5 = (TextView) this.findViewById(R.id.tv_youhuixinxi);
		mtv6 = (TextView) this.findViewById(R.id.tv_introduce);
		mCollection.setOnClickListener(this);
		mComments.setOnClickListener(this);
		mShare.setOnClickListener(this);
		mOrder.setOnClickListener(this);
		Bundle bundle = getIntent().getExtras();
		Dishes dishes = (Dishes) bundle.getSerializable("result");
		
		mtv6.setText(dishes.getDescription());
		mtv5.setText(dishes.getInformation_str());
		mtv4.setText(dishes.getStore_str());
		mtv3.setText(dishes.getName_store());
		mtv2.setText(dishes.getRemind());
		mtv1.setText(dishes.getStore_str());

		mImageLoader = new ImageLoader(this);
		mImageLoader.DisplayImage(MapPackage.PATH + dishes.getLager_picture(), mtv,
				false);
		System.out.println(dishes);
		((TextView) findViewById(R.id.title_center_text))
		.setText(getResources().getString(R.string.my_accounts));

	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_collection:
			Toast.makeText(this, "收藏成功", 0).show();
			break;
		case R.id.iv_comments:
			Toast.makeText(this, "评论成功", 0).show();
			break;
		case R.id.iv_share:
			Intent intent1 = new Intent(this, ShareActivity.class);
			startActivity(intent1);
			break;
		case R.id.iv_order:
			Toast.makeText(this, "点餐成功", 0).show();
			break;

		}

	}

}
