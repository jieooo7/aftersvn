package cn.icnt.dinners.dinner;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.moments.WechatMoments;


public class ShareActivity extends Activity implements OnClickListener {
	private ImageView miv;
	private ImageView miv1;
	private ImageView miv2;
	private View mtv;
	private ShareParams sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_details_share);
		ShareSDK.initSDK(this);
		miv = (ImageView) this.findViewById(R.id.iv_tencent);
		miv1 = (ImageView) this.findViewById(R.id.iv_sian);
		miv2 = (ImageView) this.findViewById(R.id.iv_letter);


		miv.setOnClickListener(this);
		miv1.setOnClickListener(this);
		miv2.setOnClickListener(this);
		((TextView) findViewById(R.id.title_center_text))
		.setText(getResources().getString(R.string.my_accoun));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_sian:
			sp = new ShareParams();
			sp.setText("123");
			sp.setImageUrl("http://www.xiaopi.com/skin_img/logo.png");

			// 获取单个平台
			Platform platt = ShareSDK.getPlatform(this, SinaWeibo.NAME);
			platt.setPlatformActionListener(new PlatformActionListener() {

				public void onError(Platform platform, int action, Throwable t) {

				}

				public void onCancel(Platform platform, int action) {
					// 操作取消的处理代码
				}

				@Override
				public void onComplete(Platform arg0, int arg1,
						HashMap<String, Object> arg2) {
					// TODO Auto-generated method stub

				}

			});
			platt.share(sp);
			break;
		case R.id.iv_tencent:
			sp = new ShareParams();
			sp.setText("123");
			sp.setImageUrl("http://www.xiaopi.com/skin_img/logo.png");

			// 获取单个平台
			platt = ShareSDK.getPlatform(this, TencentWeibo.NAME);
			platt.setPlatformActionListener(new PlatformActionListener() {

				public void onError(Platform platform, int action, Throwable t) {

				}

				public void onCancel(Platform platform, int action) {
					// 操作取消的处理代码
				}

				@Override
				public void onComplete(Platform arg0, int arg1,
						HashMap<String, Object> arg2) {
					// TODO Auto-generated method stub

				}

			});
			platt.share(sp);
			break;
		case R.id.iv_letter:
			sp = new ShareParams();
			sp.setText("123");
			sp.setImageUrl("http://www.xiaopi.com/skin_img/logo.png");

			// 获取单个平台
			platt = ShareSDK.getPlatform(this, WechatMoments.NAME);
			platt.setPlatformActionListener(new PlatformActionListener() {

				@Override
				public void onError(Platform arg0, int arg1, Throwable arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onComplete(Platform arg0, int arg1,
						HashMap<String, Object> arg2) {

				}

				@Override
				public void onCancel(Platform arg0, int arg1) {
					// TODO Auto-generated method stub

				}
			});
			platt.share(sp);
			break;
		case R.id.login_title:
			
			Intent intent=new Intent(this,DetailsActivity.class);
			startActivity(intent);
			
			
			break;
		}
	}

}
