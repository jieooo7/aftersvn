package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.entity.Store;
import cn.icnt.dinners.http.MapPackage;

public class DetailaActivity extends Activity implements OnClickListener {
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
	private TextView mtv7;
	private ImageLoader mImageLoader;
	private TextView mtv_it;

	// MyBaiduLotion myLotion;
	// MyLocation myLocation;
	// String strlocation = "";
//餐厅推选
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_detail_activity);
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
		mtv7 = (TextView) this.findViewById(R.id.tv_zdai);
		
		mCollection.setOnClickListener(this);
		mComments.setOnClickListener(this);
		mShare.setOnClickListener(this);
		mOrder.setOnClickListener(this);
		Bundle bundle = getIntent().getExtras();
		Store store = (Store) bundle.getSerializable("result");
		mtv7.setText(store.getRemark());
		mtv6.setText(store.getDescription());
		mtv5.setText(store.getStart_time()+"-"+store.getEnd_time());
		mtv4.setText(store.getContact_tel());
		mtv3.setText(store.getStore_str());
		mtv2.setText(store.getRemind());
		mtv1.setText(store.getName_store());
		
		mImageLoader = new ImageLoader(this);
		mImageLoader.DisplayImage(MapPackage.PATH + store.getLager_picture(), mtv,
				false);
		System.out.println(store);
		((TextView) findViewById(R.id.title_center_text))
		.setText(getResources().getString(R.string.my_accounts));
		((RelativeLayout)findViewById(R.id.title_left_btn)).setOnClickListener(this);

	}

	// MapPackage mp = new MapPackage();
	// mp.setPath("discount");
	//
	// mp.setHead(this);
	// mp.setPara("order", "1");
	// mp.setRes("start", "1");
	// mp.setRes("count", "3");
	//
	// try {
	// mp.send();
	//
	// } catch (Exception e) {
	// if (HttpSendRecv.netStat)
	// Toast.makeText(this, "网络错误，请重试", Toast.LENGTH_LONG)
	// .show();
	// else
	// Toast.makeText(this, "出错了^_^", Toast.LENGTH_LONG)
	// .show();
	//
	// } finally {
	//
	// }
	//
	//
	// }
	// }
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

	// }

	// Handler myHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// super.handleMessage(msg);
	// locationInfoTextView.setText(strlocation);
	// }
	//
	// };

	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// // myLotion.desClient();
	// }

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.iv_collection:
	// Intent intent=new Intent(this,ShareActivity.class);
	// startActivity(intent);
	// break;
	// case R.id.iv_comments:
	// Intent intent11=new Intent(this,ShareActivity.class);
	// startActivity(intent11);
	// break;
	// case R.id.iv_share:
	// Intent intent1=new Intent(this,ShareActivity.class);
	// startActivity(intent1);
	// break;
	// case R.id.iv_order:
	// Intent intent111=new Intent(this,ShareActivity.class);
	// startActivity(intent111);
	// break;
	//
	//
	// }
	//
	// }

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
		case R.id.title_left_btn:
//			Intent intent=new Intent(DetailaActivity.this,MainActivity.class);
//			startActivity(intent);
			finish();
			break;

		}

	}

}
