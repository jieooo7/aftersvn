/*
 * AdapterSet.java
 * classes : cn.icnt.dinners.adapter.AdapterSet
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月7日 上午11:06:22
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.EasyFile;

/**
 * cn.icnt.dinners.adapter.AdapterSet
 * @author Andrew Lee <br/>
 * create at 2014年7月7日 上午11:06:22
 */
public class AdapterSet {
	private static final String TAG = "AdapterSet";
	
	public static void setAdapter(MapPackage mp,List<Map<String, String>> list,BaseAdapter adapter,ListView lv0,String fileName){
//		mp.send();
//		list = mp.getBackResult();
		if (list != null) {
			boolean bs = EasyFile.writeFile(fileName, list);

			DebugUtil.i("test....path", mp.getpath());
			DebugUtil.i("test....head",
					"11111----" + mp.getBackHead().get("code"));
			DebugUtil.i("test....para",
					"22222----" + mp.getBackPara().get("total"));
			DebugUtil.i("test....result", "33333----"
					+ mp.getBackResult().get(1).get("goods_no"));
			DebugUtil.i("test....resultimg", "img----"
					+ mp.getBackResult().get(1).get("img_url"));
//			adapter = new LoaderAdapter(context, list);

			lv0.setAdapter(adapter);
//			lv1.setAdapter(adapter);
//			lv2.setAdapter(adapter);
		}else{
			if (EasyFile.readFile(fileName) != null) {

				list = EasyFile.readFile(fileName);
//				adapter = new LoaderAdapter(context, list);

				lv0.setAdapter(adapter);
//				lv1.setAdapter(adapter);
//				lv2.setAdapter(adapter);

			}
		}
	}
	
}
