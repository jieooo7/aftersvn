package cn.icnt.dinners.dinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ShowDetailActivity extends Activity {
	@ViewInject(R.id.title_left_btn)
	private RelativeLayout title_left_btn;
	@ViewInject(R.id.title_left_btn_img)
	private ImageView title_left_btn_img;
	@ViewInject(R.id.title_center_text)
	private TextView title_center_text;

	@ViewInject(R.id.gridview)
	private GridView gridview;
	@ViewInject(R.id.order_detail_info)
	private TextView order_detail_info;// 店名
	@ViewInject(R.id.order_total_price)
	private TextView order_total_price;// 商品总价
	@ViewInject(R.id.order_discount)
	private TextView order_discount;// 优惠金额
	@ViewInject(R.id.order_price)
	private TextView order_price;// 折后价格
	@ViewInject(R.id.control_order)
	private RelativeLayout control_order;// 控制“删除、支付”行
	@ViewInject(R.id.del_bucket_img)
	private RelativeLayout del_bucket_img;// 删除
	@ViewInject(R.id.orders_evaluates)
	private RelativeLayout orders_evaluates;// 订单评价行
	@ViewInject(R.id.order_evaluate)
	private Button order_evaluate;// 订单评价
	@ViewInject(R.id.order_pay_online)
	private Button order_pay_online;// 订单评价

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		initView();
	}

	private void initView() {
		title_center_text.setText("订单详情");
	};

	@OnClick({ R.id.order_evaluate, R.id.del_bucket_img, R.id.title_left_btn,
			R.id.order_pay_online })
	public void clickMethod(View v) {

	}
}
