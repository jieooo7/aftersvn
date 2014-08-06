
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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.adapter.MycollectAdapter;
import cn.icnt.dinners.adapter.MycollectDishAdapter;
import cn.icnt.dinners.adapter.MycollectResAdapter;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.entity.Dishes;
import cn.icnt.dinners.entity.Result;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;

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
	private MycollectDishAdapter adapter1;
	private MycollectResAdapter adapter2;
	private List<Map<String, String>> list;
	private List<Map<String, String>> list1;
	private List<Map<String, String>> list2;
	
	private Map<String, String> map;
	private Map<String, String> map1;
	private Map<String, String> map2;
//	private MapPackage mp;

	private ListView lv0;
	private ListView lv1;
	private ListView lv2;
	private RelativeLayout r0;
	private RelativeLayout r1;
	private RelativeLayout r2;
	
	private TextView tv0;//左侧数字
	private TextView tv1;
	private TextView tv2;
	private RelativeLayout title;
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
		title=(RelativeLayout) findViewById(R.id.title_left_btn);
		r0.setOnClickListener(this);
		r1.setOnClickListener(this);
		r2.setOnClickListener(this);
		title.setOnClickListener(this);
		lv0 = (ListView) findViewById(R.id.lv0);
		lv1 = (ListView) findViewById(R.id.lv1);
		lv2 = (ListView) findViewById(R.id.lv2);
		
		tv0=(TextView)findViewById(R.id.mycollect_tv0);
		tv1=(TextView)findViewById(R.id.mycollect_tv1);
		tv2=(TextView)findViewById(R.id.mycollect_tv2);
		
		tv0.setText("");
		tv1.setText("");
		tv2.setText("");
		
		MapPackage mp = new MapPackage();
		mp.setPath("favorite_list");
		mp.setHead(this);
		mp.setPara("favorite_type", "1");
		mp.setRes("start", "0");
		mp.setRes("count", "");
		MapPackage mp1 = new MapPackage();
		mp1.setPath("favorite_goods");
		mp1.setHead(this);
		mp1.setPara("favorite_type", "2");
		mp1.setRes("start", "0");
		mp1.setRes("count", "");
		MapPackage mp2 = new MapPackage();
		mp2.setPath("favorite_company");
		mp2.setHead(this);
		mp2.setPara("favorite_type", "3");
		mp2.setRes("start", "0");
		mp2.setRes("count", "");
		try {
			mp.send();
			mp1.send();
			mp2.send();
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
				Toast.makeText(getApplicationContext(), "出错了>_<",
						Toast.LENGTH_LONG).show();

		} finally {

		}
		list = mp.getBackResult();
		list1 = mp1.getBackResult();
		list2 = mp2.getBackResult();
		map=mp.getBackPara();
		map1=mp1.getBackPara();
		map2=mp2.getBackPara();
		
		if(map!=null&&map1!=null&&map2!=null){
		tv0.setText(getNo(map.get("total")));
		
		tv1.setText(getNo(map1.get("total")));
		tv2.setText(getNo(map2.get("total")));
		}
		adapter=new MycollectAdapter(this,list);
		adapter1=new MycollectDishAdapter(this,list1);
		adapter2=new MycollectResAdapter(this,list2);
		
		if(list!=null&&list1!=null&&list2!=null){
		lv0.setAdapter(adapter);
		lv1.setAdapter(adapter1);
		lv2.setAdapter(adapter2);
		lv0.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				Intent intent = new Intent(MycollectDetailActivity.this, DetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						"result",
						new Result(list.get(position).get("address_str"),
								position, position, list.get(position).get(
										"information_str"), list.get(position)
										.get("name_store"), list.get(position)
										.get("name_url"), list.get(position)
										.get("phone_str"), list.get(position)
										.get("remind"), list.get(position).get(
										"store_str"), position, list.get(
										position).get("description"), list.get(
										position).get("end_time"), list.get(
										position).get("lager_picture"),
								position));
				intent.putExtras(bundle);
				MycollectDetailActivity.this.startActivity(intent);

			}
		});
		lv1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				Intent intent = new Intent(MycollectDetailActivity.this, DetailcActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						"result",
						new Dishes(list1.get(position).get("address_str"),
								position, position, list1.get(position).get(
										"description"), list1.get(position).get(
										"end_time"), position, list1.get(
										position).get("food_str"), list1.get(
										position).get("giving_str"), list1.get(
										position).get("information_str"), list1.get(
										position).get("name_store"), list1.get(
										position).get("name_url"), list1.get(
										position).get("phone_str"), list1.get(
										position).get("remark"), list1.get(
										position).get("remind"), position,
								list1.get(position).get("start_time"), list1.get(
										position).get("store_str"), position,
								position,list1.get(
										position).get("store_str")));
				
				intent.putExtras(bundle);
				MycollectDetailActivity.this.startActivity(intent);

			}
		});
		lv2.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				Intent intent = new Intent(MycollectDetailActivity.this, DetailaActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						"result",
						new Dishes(list2.get(position).get("address_str"),
								position, position, list2.get(position).get(
										"description"), list2.get(position).get(
										"end_time"), position, list2.get(
										position).get("food_str"), list2.get(
										position).get("giving_str"), list2.get(
										position).get("information_str"), list2.get(
										position).get("name_store"), list2.get(
										position).get("name_url"), list2.get(
										position).get("phone_str"), list2.get(
										position).get("remark"), list2.get(
										position).get("remind"), position,
								list2.get(position).get("start_time"), list2.get(
										position).get("store_str"), position,
								position,list2.get(
										position).get("store_str")));
				
				intent.putExtras(bundle);
				MycollectDetailActivity.this.startActivity(intent);
			}
		});
		}

		
		
//	设置默认显示第一个	
//		r0.setBackgroundColor(getResources().getColor(
//				R.color.tab_unselect));
//		r1.setBackgroundColor(getResources().getColor(
//				R.color.mycollect_unselect));
//		r2.setBackgroundColor(getResources().getColor(
//				R.color.mycollect_unselect));
//		lv0.setVisibility(View.VISIBLE);
//		lv1.setVisibility(View.GONE);
//		lv2.setVisibility(View.GONE);
//		flag0 = false;
//		flag1 = true;
//		flag2 = true;
//		DebugUtil.w("000000", "ok");

	}
	
	
	protected String getNo(String no){
		
		if(no.equals("0")){
			
			return "";
		}else{
			
			return no;
		}
		
		
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
			
		case R.id.title_left_btn:
			finish();
			break;

		}
	}

}

