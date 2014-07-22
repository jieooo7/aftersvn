/*
 * UserinfoActivity.java
 * classes : cn.icnt.dinners.dinner.UserinfoActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月15日 上午9:52:34
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.http.Header;
import org.apache.commons.lang.StringUtils;

import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.uploadimg.AsyncHttpClient;
import cn.icnt.dinners.uploadimg.AsyncHttpResponseHandler;
import cn.icnt.dinners.uploadimg.RequestParams;
import cn.icnt.dinners.utils.PreferencesUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
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
@SuppressLint("NewApi") public class UserinfoActivity extends Activity implements OnClickListener{
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
	
	
	
	public void upload() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			byte[] buffer = out.toByteArray();

			byte[] encode = Base64.encode(buffer, Base64.DEFAULT);//需要api8 以上
			String photo = new String(encode);

			RequestParams params = new RequestParams();
			params.put("photo", photo);
			String url = "http://115.29.13.164/upload_photo.do";

			AsyncHttpClient client = new AsyncHttpClient();
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					try {
						if (statusCode == 200) {

							Toast.makeText(UserinfoActivity.this, "头像上传成功!", 0)
									.show();
						} else {
							Toast.makeText(UserinfoActivity.this,
									"网络访问异常，错误码：" + statusCode, 0).show();

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					Toast.makeText(UserinfoActivity.this,
							"网络访问异常，错误码  > " + statusCode, 0).show();

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	/*
	 * 从相机获取
	 */
	public void camera() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
		}
		startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(this, "未找到存储卡，无法存储照片！", 0).show();
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				bitmap = data.getParcelableExtra("data");
				this.mFace.setImageBitmap(bitmap);
				boolean delete = tempFile.delete();
				System.out.println("delete = " + delete);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 剪切图片
	 * 
	 * @function:
	 * @author:Jerry
	 * @date:2013-12-30
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
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
		case R.id.user_info_camera:
			camera();
			break;
			
		case R.id.user_info_from_phone:
			gallery();
			break;
		case R.id.user_info_cancel:
			finish();
			break;
		case R.id.user_info_change_portrait:
			upload();
			DebugUtil.i("上传头像", "头像");
			break;
		}
	}
}
