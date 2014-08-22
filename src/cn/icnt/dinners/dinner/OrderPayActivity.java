package cn.icnt.dinners.dinner;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.utils.PreferencesUtils;
import cn.icnt.dinners.utils.ToastUtil;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

public class OrderPayActivity extends Activity {
	@ViewInject(R.id.title_left_btn)
	private RelativeLayout title_left_btn;
	@ViewInject(R.id.title_center_text)
	private TextView title_center_text;
	@ViewInject(R.id.user_balance_tv)
	private TextView user_balance_tv;
	@ViewInject(R.id.order_total_price)
	private TextView order_total_price;
	@ViewInject(R.id.order_pay_num_et)
	private EditText order_pay_num_et;
	@ViewInject(R.id.lost_paypwd_tv)
	private TextView lost_paypwd_tv;
	@ViewInject(R.id.order_pay)
	private Button order_pay;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_pay_activity);
		initview();
	}

	private void initview() {
		String order_price = (String) getIntent().getExtras().get(
				"order_total_price");
		String accountNum = PreferencesUtils.getValueFromSPMap(this,
				PreferencesUtils.Keys.ACCOUNT_NO);
		ViewUtils.inject(this);
		title_center_text.setText("付款");
		order_total_price.setText("￥" + order_price);
		user_balance_tv.setText("余额："+accountNum+"元");
	}

	@OnClick({ R.id.title_left_btn,R.id.lost_paypwd_tv,R.id.order_pay })
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			break;
		case R.id.lost_paypwd_tv:
			ToastUtil.show(this, "忘记密码");
			break;
		case R.id.order_pay:
			String payPWD = order_pay_num_et.getText().toString().trim();
			if (StringUtils.isEmpty(payPWD)) {
				ToastUtil.show(this, "请输入密码");
			}else {
				ToastUtil.show(this, "跳转界面");
			}
			break;
		}
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	    }
	    public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	    }
}
