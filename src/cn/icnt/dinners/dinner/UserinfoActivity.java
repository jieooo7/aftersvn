/*
 * UserinfoActivity.java
 * classes : cn.icnt.dinners.dinner.UserinfoActivity
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年7月15日 上午9:52:34
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * cn.icnt.dinners.dinner.UserinfoActivity
 * @author Andrew Lee <br/>
 * create at 2014年7月15日 上午9:52:34
 */
public class UserinfoActivity extends Activity implements OnClickListener{
	private static final String TAG = "UserinfoActivity";
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);

		// 设置include的title布局，标题文字
		((TextView) findViewById(R.id.title_center_text))
				.setText(getResources().getString(R.string.person_info));
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
	}
}
