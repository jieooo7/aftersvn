/*
 * FragmentAlipay.java
 * classes : cn.icnt.dinners.fragment.FragmentAlipay
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年8月12日 下午3:42:51
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.fragment;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.icnt.dinners.dinner.MainActivity;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.utils.MD5;
import cn.icnt.dinners.utils.PreferencesUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * cn.icnt.dinners.fragment.FragmentAlipay
 * @author Andrew Lee <br/>
 * create at 2014年8月12日 下午3:42:51
 */
public class FragmentAlipay extends Fragment{
	private static final String TAG = "FragmentAlipay";
	private EditText et0;
	private EditText et1;
	private EditText et2;
	private Button bt;
	private TextView tv0;
	private TextView tv1;
	private float f;
	private float f1;
	private String acc;
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		        // Inflate the layout for this fragment
		 
		 View view =inflater.inflate(R.layout.myaccount_fragment_alipay, container, false);
		 et0=(EditText) view.findViewById(R.id.myaccount_frag_ali_no);
		 et1=(EditText) view.findViewById(R.id.myaccount_frag_ali_money);
		 et2=(EditText) view.findViewById(R.id.myaccount_passwd_ali);
		 bt=(Button) view.findViewById(R.id.myaccount_frag_submit);
		 
		 bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
				View convertView=layoutInflater.inflate(R.layout.myaccount_viewpager_get_money, null);
				if(convertView!=null)
				{
					tv0=(TextView)convertView.findViewById(R.id.myacount_money_ali);
					tv1=(TextView)convertView.findViewById(R.id.myacount_money_bank);
				}
				
				String account=et0.getText().toString();
				String amount=et1.getText().toString();
				String payment_pwd=et2.getText().toString();
				String money=PreferencesUtils
				.getValueFromSPMap(getActivity(), PreferencesUtils.Keys.ACCOUNT_NO,
					"0.00");
				try {
					f = Float.parseFloat(amount);
					f1=Float.parseFloat(money);
				} catch (Exception e) {
				}
				if(StringUtils.isEmpty(account)||StringUtils.isEmpty(amount)||StringUtils.isEmpty(payment_pwd)){
					
					Toast.makeText(getActivity(), "支付账号，提现金额或者支付密码不能为空",
							Toast.LENGTH_SHORT).show();
				}else{
					if(f<1.00){
						Toast.makeText(getActivity(), "提现金额不能小于一元",
								Toast.LENGTH_SHORT).show();
						
					}else if(f>f1){
						Toast.makeText(getActivity(), "提现金额不能大于余额",
								Toast.LENGTH_SHORT).show();
						
					}else{
						Map<String, String> map = send(account,amount,payment_pwd);
						if (map != null && map.get("code").equals("10000")) {

							// 调用支付宝
							Toast.makeText(getActivity(), map.get("content"),
									Toast.LENGTH_LONG).show();
							PreferencesUtils.putValueToSPMap(getActivity(), PreferencesUtils.Keys.ACCOUNT_NO, map.get("balance"));
							acc = "  " + PreferencesUtils
									.getValueFromSPMap(getActivity(), PreferencesUtils.Keys.ACCOUNT_NO,
											"0.00") + "元";
							tv0.setText(acc);
							tv1.setText(acc);
						} else {
							Toast.makeText(getActivity(), map.get("content"),
									Toast.LENGTH_LONG).show();
						}

						
						
						
					}
					
					
					
				}
				
				
			}
		});
		 
		 
		 
		 return view;

}
	 
	 protected Map<String, String> send(String account,String amount,String payment_pwd) {
			MapPackage mp = new MapPackage();
			mp.setPath("apply_money");
			mp.setHead(getActivity());
			mp.setPara("type","2");//支付宝提现
			mp.setPara("name",account);
			mp.setPara("bank","支付宝");
			mp.setPara("amount",amount);
			mp.setPara("payment_pwd",MD5.getMD5(payment_pwd));
			

			try {
				mp.send();

			} catch (Exception e) {
				if (HttpSendRecv.netStat)
					Toast.makeText(getActivity(), "网络错误，请重试",
							Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getActivity(), "出错了>_<",
							Toast.LENGTH_LONG).show();
			}
			if (mp.getBackHead() != null) {
				return mp.getBackHead();
			} else {
				return null;
			}

		}

}
