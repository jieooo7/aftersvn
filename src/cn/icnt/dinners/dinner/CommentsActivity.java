/*
 * CommentsACtivity.java
 * classes : cn.icnt.dinners.dinner.CommentsACtivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年8月20日 下午2:29:48
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.icnt.dinners.adapter.CommentsAdapter;
import cn.icnt.dinners.beans.Comments;
import cn.icnt.dinners.beans.HomeDishesBean.HomeDishesdesc;
import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.model.CommentsModel;
import cn.icnt.dinners.utils.Container;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * cn.icnt.dinners.dinner.CommentsACtivity
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年8月20日 下午2:29:48
 */
public class CommentsActivity extends Activity {
    private static final String TAG = "CommentsACtivity";
    private String good_id;
    private String large_url;
    private ImageView iv;
    private ExpandableListView listView;
    private RelativeLayout rl;
    private ImageLoader mImageLoader;

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.comments);
	mImageLoader = new ImageLoader(this);
	iv = (ImageView) findViewById(R.id.comments_pic);
	rl=(RelativeLayout) findViewById(R.id.comments_left_btn);
	rl.setOnClickListener(new OnClickListener() {
        
        public void onClick(View v) {
            // TODO Auto-generated method stub
        	finish();
        }
    });
	good_id = (String) getIntent().getExtras().get("goods_id");
	DebugUtil.i("菜品id", good_id);
	send();
	mImageLoader.DisplayImage(
				MapPackage.PATH + large_url,
				iv, false);
	listView = (ExpandableListView) findViewById(R.id.comments_ex_list);
	
    }

    protected void send() {
	MapPackage mp = new MapPackage();
	mp.setHead(this);
	mp.setPara("goods_id", this.good_id);
	mp.setRes("start", "0");
	mp.setRes("count", "10");
	Map<String, Object> maps = mp.getMap();
	RequestParams params = GsonTools.GetParams(maps);
	HttpUtils http = new HttpUtils();
	http.send(HttpRequest.HttpMethod.POST, Container.COMMENT_LIST, params,
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
			CommentsModel person = GsonTools.getPerson(responseInfo.result,
				CommentsModel.class);
			if (person != null && "10000".equals(person.head.code)) {
				large_url=person.head.lager_url;
			    CommentsAdapter adapter = new CommentsAdapter(
				    CommentsActivity.this, person.para);
			    listView.setAdapter(adapter);
			    int groupCount = listView.getCount();
			    for (int i=0; i<groupCount; i++) {
			    	listView.expandGroup(i);
			        };
			    listView.setGroupIndicator(null); 
			}
		    }

		    @Override
		    public void onFailure(HttpException error, String msg) {

		    }
		});
    }

}
