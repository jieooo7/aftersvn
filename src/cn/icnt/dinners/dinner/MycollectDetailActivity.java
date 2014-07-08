/*
 * MycollectDetailActivity.java
 * classes : cn.icnt.dinners.dinner.MycollectDetailActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月3日 下午2:12:25
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.adapter.AdapterSet;
import cn.icnt.dinners.adapter.LoaderAdapter;
import cn.icnt.dinners.adapter.MycollectAdapter;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.EasyFile;

/**
 * cn.icnt.dinners.dinner.MycollectDetailActivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年7月3日 下午2:12:25
 */

public class MycollectDetailActivity extends Activity implements
		OnClickListener {
	private static final String TAG = "MycollectDetailActivity";
	private MycollectAdapter adapter;
	private List<Map<String, String>> list;

	private ListView lv0;
	private ListView lv1;
	private ListView lv2;
	private RelativeLayout r0;
	private RelativeLayout r1;
	private RelativeLayout r2;
	private boolean flag0;
	private boolean flag1;
	private boolean flag2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycollect_detail);
		flag0 = true;
		flag1 = true;
		flag2 = true;
//		设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.my_favor));
		r0 = (RelativeLayout) findViewById(R.id.r0);
		r1 = (RelativeLayout) findViewById(R.id.r1);
		r2 = (RelativeLayout) findViewById(R.id.r2);
		r0.setOnClickListener(this);
		r1.setOnClickListener(this);
		r2.setOnClickListener(this);
		lv0 = (ListView) findViewById(R.id.lv0);
		lv1 = (ListView) findViewById(R.id.lv1);
		lv2 = (ListView) findViewById(R.id.lv2);
		MapPackage mp = new MapPackage();
		mp.setPath("discount");
		mp.setHead(this);
		mp.setPara("order", "1");
		mp.setRes("start", "1");
		mp.setRes("count", "3");
		try {
			mp.send();
			// list = mp.getBackResult();
			// if (list != null) {
			// boolean bs = EasyFile.writeFile("test", list);
			//
			// DebugUtil.i("test....path", mp.getpath());
			// DebugUtil.i("test....head",
			// "11111----" + mp.getBackHead().get("code"));
			// DebugUtil.i("test....para",
			// "22222----" + mp.getBackPara().get("total"));
			// DebugUtil.i("test....result", "33333----"
			// + mp.getBackResult().get(1).get("goods_no"));
			// DebugUtil.i("test....resultimg", "img----"
			// + mp.getBackResult().get(1).get("img_url"));
			// adapter = new LoaderAdapter(this, list);
			//
			// lv0.setAdapter(adapter);
			// lv1.setAdapter(adapter);
			// lv2.setAdapter(adapter);
			// }else{
			// if (EasyFile.readFile("test") != null) {
			//
			// list = EasyFile.readFile("test");
			// adapter = new LoaderAdapter(this, list);
			//
			// lv0.setAdapter(adapter);
			// lv1.setAdapter(adapter);
			// lv2.setAdapter(adapter);
			//
			// }
			// }
		} catch (Exception e) {
			if (HttpSendRecv.netStat)
				Toast.makeText(getApplicationContext(), "网络错误，请重试",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "出错了^_^",
						Toast.LENGTH_LONG).show();

		} finally {

		}
		list = mp.getBackResult();
		adapter=new MycollectAdapter(this,list);
		if(list!=null){
		lv0.setAdapter(adapter);
		lv1.setAdapter(adapter);
		lv2.setAdapter(adapter);
		}
//		AdapterSet.setAdapter( mp, list, adapter, lv0, "test0");
//		AdapterSet.setAdapter( mp, list, adapter, lv1, "test1");
//		AdapterSet.setAdapter( mp, list, adapter, lv2, "test2");

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	// protected void setAdapter(MapPackage mp,List<Map<String, String>>
	// list,BaseAdapter adapter,ListView lv0,String fileName){
	// // mp.send();
	// list = mp.getBackResult();
	// if (list != null) {
	// boolean bs = EasyFile.writeFile(fileName, list);
	//
	// DebugUtil.i("test....path", mp.getpath());
	// DebugUtil.i("test....head",
	// "11111----" + mp.getBackHead().get("code"));
	// DebugUtil.i("test....para",
	// "22222----" + mp.getBackPara().get("total"));
	// DebugUtil.i("test....result", "33333----"
	// + mp.getBackResult().get(1).get("goods_no"));
	// DebugUtil.i("test....resultimg", "img----"
	// + mp.getBackResult().get(1).get("img_url"));
	// adapter = new LoaderAdapter(this, list);
	//
	// lv0.setAdapter(adapter);
	// // lv1.setAdapter(adapter);
	// // lv2.setAdapter(adapter);
	// }else{
	// if (EasyFile.readFile(fileName) != null) {
	//
	// list = EasyFile.readFile(fileName);
	// adapter = new LoaderAdapter(this, list);
	//
	// lv0.setAdapter(adapter);
	// // lv1.setAdapter(adapter);
	// // lv2.setAdapter(adapter);
	//
	// }
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.r0:
			if (flag0) {
				r0.setBackgroundColor(getResources().getColor(
						R.color.tab_unselect));
				r1.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				r2.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				lv0.setVisibility(View.VISIBLE);
				lv1.setVisibility(View.GONE);
				lv2.setVisibility(View.GONE);
				flag0 = false;
				flag1 = true;
				flag2 = true;
				DebugUtil.w("000000", "ok");
			} else {
				r0.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				lv0.setVisibility(View.GONE);
				flag0 = true;
				DebugUtil.w("000000", "not ok");

			}
			break;

		case R.id.r1:
			if (flag1) {
				r1.setBackgroundColor(getResources().getColor(
						R.color.tab_unselect));
				r0.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				r2.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				lv1.setVisibility(View.VISIBLE);
				lv0.setVisibility(View.GONE);
				lv2.setVisibility(View.GONE);
				flag0 = true;
				flag2 = true;
				flag1 = false;
				DebugUtil.w("111111", "ok");
			} else {
				r1.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				lv1.setVisibility(View.GONE);
				flag1 = true;
				DebugUtil.w("111111", "not ok");

			}
			break;

		case R.id.r2:
			if (flag2) {
				r2.setBackgroundColor(getResources().getColor(
						R.color.tab_unselect));
				r0.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				r1.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				lv2.setVisibility(View.VISIBLE);
				lv1.setVisibility(View.GONE);
				lv0.setVisibility(View.GONE);
				flag0 = true;
				flag1 = true;
				flag2 = false;
				DebugUtil.w("222222", "ok");
			} else {
				r2.setBackgroundColor(getResources().getColor(
						R.color.mycollect_unselect));
				lv2.setVisibility(View.GONE);
				flag2 = true;
				DebugUtil.w("222222", "not ok");

			}
			break;

		}
	}

}
