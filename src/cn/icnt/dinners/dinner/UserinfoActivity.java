/*
 * UserinfoActivity.java
 * classes : cn.icnt.dinners.dinner.UserinfoActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月15日 上午9:52:34
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.PreferencesUtils;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * cn.icnt.dinners.dinner.UserinfoActivity
 * @author Andrew Lee <br/>
 * create at 2014年7月15日 上午9:52:34
 */
public class UserinfoActivity extends Activity implements OnClickListener{
	private static final String TAG = "UserinfoActivity";
	private RelativeLayout title;
	private EditText ev;
	private Button bt;
	private String petName;//昵称
	private TextView tv0;//修改头像
	private TextView tv1;//相机
	private TextView tv2;//从相册选取
	private TextView tv3;//取消
	
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	private ImageView mFace;
	private Bitmap bitmap;

	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private File tempFile;
	
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);

		// 设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.person_info));
		
		title = (RelativeLayout) findViewById(R.id.title_left_btn);
		ev=(EditText) findViewById(R.id.user_info_petname);
		bt=(Button) findViewById(R.id.user_info_sendpet);
		bt.setOnClickListener(this);
		title.setOnClickListener(this);
		
		this.mFace = (ImageView) this.findViewById(R.id.user_info_iv_portrait);
		
		tv0=(TextView) findViewById(R.id.user_info_change_portrait);
		tv1=(TextView) findViewById(R.id.user_info_camera);
		tv2=(TextView) findViewById(R.id.user_info_from_phone);
		tv3=(TextView) findViewById(R.id.user_info_cancel);
		tv0.setOnClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		
	}
	
	
	protected String send(String pet){
		
		MapPackage mp=new MapPackage();
		mp.setPath("update_nickname");
		mp.setHead(this);
		mp.setPara("nickname", pet);
		try{
			mp.send();
			
			
		}catch(Exception e){
			if (HttpSendRecv.netStat)
				Toast.makeText(getApplicationContext(), "网络错误，请重试",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "出错了>_<",
						Toast.LENGTH_LONG).show();
		}
		if(mp.getBackHead()!=null){
		return mp.getBackHead().get("code");
		}else{
			return null;
		}
		
	}
	
	

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_left_btn:
			finish();
			break;
		case R.id.user_info_sendpet:
			
			
			petName=ev.getText().toString();
			String send=send(petName);
			
			if(StringUtils.isEmpty(petName) ||StringUtils.isEmpty(petName)){
				Toast.makeText(getApplicationContext(), "昵称不能为空",
						Toast.LENGTH_SHORT).show();
			}
			else if(petName.length()>6){
					Toast.makeText(getApplicationContext(), "昵称必须小于6位",
							Toast.LENGTH_SHORT).show();
				}
			
			else{
				if(send!=null&&send.equals("10000")){
					Toast.makeText(getApplicationContext(), "昵称设置成功",
							Toast.LENGTH_LONG).show();
					PreferencesUtils.putValueToSPMap(getApplicationContext(), PreferencesUtils.Keys.NICKNAME, petName);
				}else{
					Toast.makeText(getApplicationContext(), "昵称设置失败，请重新设置",
							Toast.LENGTH_LONG).show();
				}
			}
			
			
			break;
		
		}
	}
}
