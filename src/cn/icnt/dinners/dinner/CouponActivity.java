package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import cn.icnt.dinners.adapter.LoaderAdapter;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.EasyFile;

public class CouponActivity extends Activity {
	private GridView lv;
	private LoaderAdapter adapter;
	private List<Map<String, String>> list;
	private boolean bs;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		// String url="http://115.29.13.164/recommend.do?t=309343089034";
		// MapPackage.setHead("-1","11111","1","1.1.2");
		// MapPackage.setPara("category_id", "0");
		// MapPackage.setRes("start", "1");
		// MapPackage.setRes("count", "3");
		lv = (GridView) findViewById(R.id.gridView1);
		MapPackage mp = new MapPackage();
		mp.setPath("recommend.do?");
		mp.setHead(this);
		mp.setPara("category_id", "0");
		mp.setRes("start", "1");
		mp.setRes("count", "3");
		try {
			mp.send();
			this.list = mp.getBackResult();
			this.bs = EasyFile.writeFile("test", this.list);
			DebugUtil.i("test....path", mp.getpath());
			DebugUtil.i("test....head",
					"11111----" + mp.getBackHead().get("code"));
			DebugUtil.i("test....para",
					"22222----" + mp.getBackPara().get("total"));
			DebugUtil.i("test....result",
					"33333----" + mp.getBackResult().get(1).get("goods_no"));
			DebugUtil.i("test....resultimg", "img----"
					+ mp.getBackResult().get(1).get("img_url"));
		} catch (Exception e) {
			if (HttpSendRecv.netStat)
				Toast.makeText(getApplicationContext(), "网络错误，请重试",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "出错了>_<",
						Toast.LENGTH_LONG).show();

			this.list = EasyFile.readFile("test");

			// String[] item=new String[]{"image1","text1"};
			// int[] itemId=new int[]{R.id.imageView2,R.id.textview2};
			// List<Map<String,Object>> list=new
			// ArrayList<Map<String,Object>>();
			// Map<String,Object> map=null;
			// for(int i=0;i<50;i++){
			//
			// map=new HashMap<String,Object>();
			// map.put("image1", R.drawable.ic_launcher);
			// map.put("text1",getString(R.string.first));
			//
			// list.add(map);
			//
			// }
			//
			// SimpleAdapter adapterDefault=new SimpleAdapter(this, list,
			// R.layout.gcoupon, item, itemId);
			//
			// lv.setAdapter(adapterDefault);
		} finally {
			adapter = new LoaderAdapter(this, this.list);

			lv.setAdapter(adapter);
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent();
				intent.setClass(CouponActivity.this, ShowDetailActivity.class);
				startActivity(intent);

			}
		});

	}

}
