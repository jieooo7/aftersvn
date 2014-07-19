package cn.icnt.dinners.dinner;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.icnt.dinners.beans.UserLoginBean;
import cn.icnt.dinners.http.GsonTools;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.Container;
import cn.icnt.dinners.utils.PreferencesUtils;
import cn.icnt.dinners.utils.ToastUtil;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 美食行家
 * 
 * @author Administrator
 * 
 */
public class FoodConnoisseurActivity extends Activity {
	@ViewInject(R.id.title_left_btn)
	private RelativeLayout back; // 返回按钮
	@ViewInject(R.id.title_left_btn_img)
	private ImageView title_left_btn_img; // 
	@ViewInject(R.id.title_center_text)
	private TextView title_center_text; // 
	@ViewInject(R.id.food_connoisseur_img)
	private ImageView food_connoisseur_img; // 
	@ViewInject(R.id.food_connoisseur_list)
	private ListView food_connoisseur_list; // 

	private InputMethodManager manager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_connoisseur_activity);
		initview();
	}

	private void initview() {
		ViewUtils.inject(this);
		title_left_btn_img.setBackground(getResources().getDrawable(R.drawable.connoisseur_title_img));
		title_center_text.setText("美 食 行 家");
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@OnClick({ R.id.title_left_btn, R.id.user_regiest, R.id.forget_password,
			R.id.login })
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			break;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
	
}
