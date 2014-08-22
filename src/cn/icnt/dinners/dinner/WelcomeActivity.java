/*
 * Welcome.java
 * classes : com.cloud.app.dinner.Welcome
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��23�� ����10:00:11
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import cn.icnt.dinners.utils.PreferencesUtils;

import com.umeng.analytics.MobclickAgent;

/**
 * ��ӭ���� com.cloud.app.dinner.Welcome
 * 
 * @author Andrew Lee <br/>
 *         create at 2014��6��23�� ����10:00:11
 */
public class WelcomeActivity extends Activity {
    private static final String TAG = "WelcomeActivity";

    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	MobclickAgent.updateOnlineConfig(this);
	// requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
	setContentView(R.layout.welcome);
	init();
	Thread mt = new Thread(mThread);
	mt.start();
    }

    public void onResume() {
	super.onResume();
	MobclickAgent.onResume(this);
    }

    public void onPause() {
	super.onPause();
	MobclickAgent.onPause(this);
    }

    private Handler mHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);

	    if ((String) msg.obj == TAG) {
		// ��ת
		Intent intent = new Intent();
		intent.setClass(WelcomeActivity.this, MainActivity.class);
		WelcomeActivity.this.startActivity(intent);
		finish();
	    }
	}
    };

    Runnable mThread = new Runnable() {
	@Override
	public void run() {
	    // TODO Auto-generated method stub
	    // ����Ϣ�ػ�ȡ��Ϣ,����message
	    Message msg = mHandler.obtainMessage();
	    // ��ʱ2��
	    try {
		Thread.sleep(2000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    msg.obj = TAG;
	    // ������Ϣ����Ϣ����
	    mHandler.sendMessage(msg);
	}

    };
    /**
     * 获取信息并保存， 获取请求head相关数据
     */
    private PreferencesUtils preferencesUtils;

    private void init() {
	TelephonyManager tm = (TelephonyManager) this
		.getSystemService(Context.TELEPHONY_SERVICE);
	String tel = tm.getLine1Number();
	String imei = tm.getSimSerialNumber();
	preferencesUtils = new PreferencesUtils();
	preferencesUtils.putValueToSPMap(this, PreferencesUtils.Keys.VERSION,
		getVersion());
	preferencesUtils.putValueToSPMap(this, PreferencesUtils.Keys.PHONE, tel);
	preferencesUtils.putValueToSPMap(this, PreferencesUtils.Keys.NO, imei);

    }

    /**
     * 获取版本号
     * 
     * @return 当前应用的版本号
     */
    public String getVersion() {
	try {
	    PackageManager manager = this.getPackageManager();
	    PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	    String version = info.versionName;
	    return version;
	} catch (Exception e) {
	    e.printStackTrace();
	    return "";
	}
    }
}
