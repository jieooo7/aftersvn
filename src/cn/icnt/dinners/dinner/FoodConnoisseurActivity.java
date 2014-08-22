package cn.icnt.dinners.dinner;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.beans.OrderListBean;
import cn.icnt.dinners.beans.OrderListBean.OrderList;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

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
//
//	@ViewInject(R.id.connoisseur_user_img)
//	private ImageView connoisseur_user_img; // 用户头像
//	
//	@ViewInject(R.id.connoisseur_user_name)
//	private TextView connoisseur_user_name; // 用户名
//	@ViewInject(R.id.connoisseur_user_desc)
//	private TextView connoisseur_user_desc; // 用户评价内容
//	@ViewInject(R.id.connoisseur_user_reply_time)
//	private TextView connoisseur_user_reply_time; // 用户评价时间
//	@ViewInject(R.id.connoisseur_user_endorse_num)
//	private TextView connoisseur_user_endorse_num; // 用户赞同数
//	@ViewInject(R.id.connoisseur_user_endorse)
//	private LinearLayout connoisseur_user_endorse; // 用户赞同
//	@ViewInject(R.id.connoisseur_user_reply)
//	private LinearLayout connoisseur_user_reply; // 用户回复
//	@ViewInject(R.id.connoisseur_user_reply_list)
//	private ListView connoisseur_user_reply_list; // 其他用户回复列表
	
	
	
	private InputMethodManager manager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_connoisseur_activity);
		initview();
	}

	private void initview() {
		ViewUtils.inject(this);
		title_left_btn_img.setImageDrawable(getResources().getDrawable(R.drawable.connoisseur_title_img));
		title_center_text.setText("美 食 行 家");
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		OrderAdapter adapter = new OrderAdapter(this,list);
//		food_connoisseur_list.setAdapter(adapter);
	}

	@OnClick({ R.id.title_left_btn, R.id.user_regiest, R.id.forget_password,
			R.id.login })
	public void clickMethod(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
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
	
	public class OrderAdapter extends BaseAdapter {
		public List<OrderList> lists = new ArrayList<OrderListBean.OrderList>();
		private LayoutInflater mInflater;
		private BitmapUtils utils;
		private BitmapDisplayConfig img_config;
		private Context mContext;

		public OrderAdapter(Context context, List<OrderList> list) {
			this.mInflater = LayoutInflater.from(context);
			this.lists = list;
			mContext = context;
			utils = new BitmapUtils(context);
			img_config = new BitmapDisplayConfig();
			img_config.setLoadingDrawable(getResources().getDrawable(
					R.drawable.replace));
			img_config.setLoadFailedDrawable(getResources().getDrawable(
					R.drawable.replace));
			img_config.setBitmapConfig(Bitmap.Config.RGB_565);
		}

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			MyListener myListener = null;
			myListener = new MyListener(position);
			if (convertView == null) {
				holder = new ViewHolder();
				// 可以理解为从vlist获取view 之后把view返回给ListView
				convertView = mInflater.inflate(R.layout.food_connoisseur_item,
						null);
				holder.connoisseur_item = (LinearLayout) convertView
						.findViewById(R.id.connoisseur_item);
				holder.connoisseur_user_reply_time = (TextView) convertView
						.findViewById(R.id.connoisseur_user_reply_time);
				holder.connoisseur_user_img = (ImageView) convertView
						.findViewById(R.id.connoisseur_user_img);
				holder.connoisseur_user_name = (TextView) convertView
						.findViewById(R.id.connoisseur_user_name);
				holder.connoisseur_user_desc = (TextView) convertView
						.findViewById(R.id.connoisseur_user_desc);
				holder.connoisseur_user_endorse_num = (TextView) convertView
						.findViewById(R.id.connoisseur_user_endorse_num);
				holder.connoisseur_user_endorse = (LinearLayout) convertView
						.findViewById(R.id.connoisseur_user_endorse);
				holder.connoisseur_user_reply = (LinearLayout) convertView
						.findViewById(R.id.connoisseur_user_reply);
			
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.connoisseur_user_endorse.setTag(position);
			holder.connoisseur_user_endorse.setOnClickListener(myListener);
			holder.connoisseur_user_reply.setTag(position);
			holder.connoisseur_user_reply.setOnClickListener(myListener);
//			holder.order_info.setText(lists.get(position).order_info);
			

			return convertView;
		}

		private class MyListener implements OnClickListener {
			int mPosition;

			public MyListener(int inPosition) {
				mPosition = inPosition;
			}

			@Override
			public void onClick(View v) {
				int position = Integer.parseInt(v.getTag().toString());
				switch (v.getId()) {
				case R.id.connoisseur_user_endorse: // 
				
					break;
				case R.id.connoisseur_user_reply: // 
				
					break;
				}
			}
		}

		public final class ViewHolder {
			public LinearLayout connoisseur_item;
			public TextView connoisseur_user_reply_time;
			public ImageView connoisseur_user_img;
			public TextView connoisseur_user_name;
			public TextView connoisseur_user_desc;
		
			public TextView connoisseur_user_endorse_num; //赞同数量
			public LinearLayout connoisseur_user_endorse;
			public LinearLayout connoisseur_user_reply; //回复按钮
//			public ListView connoisseur_user_reply_list; // 回复列表 用于显示 互动回复
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
