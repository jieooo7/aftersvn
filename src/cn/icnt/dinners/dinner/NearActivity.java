package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.icnt.dinners.adapter.SurroundingAdapter;
import cn.icnt.dinners.entity.Mopp;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.server.LocationServer;
import cn.icnt.dinners.utils.DataCleanManager;

public class NearActivity extends Activity implements
		android.view.View.OnClickListener {

	public ListView mlist;

	private List<Map<String, String>> list;
	private SurroundingAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surrounding);
		mlist = (ListView) this.findViewById(R.id.surrounding_list);
//		((TextView) findViewById(R.id.title_center_text))
//				.setText(getResources().getString(R.string.my_near));
		((RelativeLayout)findViewById(R.id.title_left_btn)).setOnClickListener(this);
		Bundle bundle = getIntent().getExtras();
		Mopp mopp = (Mopp) bundle.getSerializable("mopp");
		MapPackage mp = new MapPackage();
		mp.setPath("near_company.do");
		mp.setHead(this);
		mp.setPara("order", 1);
		mp.setRes("start", "0");
		mp.setRes("count", "4");
		mp.setPara("latitude", mopp.getLongitude());
		mp.setPara("longitude", mopp.getLatitude());

		try {
			mp.send();

		} catch (Exception e) {
			if (HttpSendRecv.netStat)
				Toast.makeText(this, "�������������", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(this, "������^_^", Toast.LENGTH_LONG).show();

		} finally {

		}

		list = mp.getBackResult();
		adapter = new SurroundingAdapter(this, list);
		if (list != null) {
			mlist.setAdapter(adapter);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			new LocationServer().closeLocation();
			new LocationServer().initLocationTime();
			new LocationServer().closeLocationTask();
			DataCleanManager dc = new DataCleanManager();
			dc.cleanInternalCache(getApplicationContext());
			dc.cleanSharedPreference(getApplicationContext());
			NearActivity.this.stopService(new Intent(NearActivity.this,
					LocationServer.class));
			finish();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	// private void doBack() {
	// AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
	// localBuilder.setTitle("提示").setMessage("亲确定要退出定位吗?");
	// localBuilder.setPositiveButton(R.string.ok,
	// new DialogInterface.OnClickListener() {
	//
	// @SuppressWarnings("static-access")
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// NearActivity.this.stopService(new Intent(NearActivity.this,
	// LocationServer.class));
	// new LocationServer().stopSelf();
	// new LocationServer().closeLocation();
	// new LocationServer().initLocationTime();
	// new LocationServer().closeLocationTask();
	//
	// DataCleanManager dc=new DataCleanManager();
	// dc.cleanInternalCache(getApplicationContext());
	// dc.cleanSharedPreference(getApplicationContext());
	//
	// finish();
	// }
	//
	//
	//
	// });
	// localBuilder.setNegativeButton(R.string.ca, null);
	// localBuilder.create().show();
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.title_left_btn:
//			Intent intent = new Intent(this, MainActivity.class);
//			startActivity(intent);
			finish();
			break;

		}

	}
}
