/*
 * FragmentBank.java
 * classes : cn.icnt.dinners.fragment.FragmentBank
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年8月12日 下午3:42:27
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.fragment;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.alipay.android.app.sdk.AliPay;
import com.umeng.analytics.MobclickAgent;

import cn.icnt.dinners.alipay.Result;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.http.HttpSendRecv;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.model.CityModel;
import cn.icnt.dinners.model.CountyModel;
import cn.icnt.dinners.model.ProvinceModel;
import cn.icnt.dinners.utils.MD5;
import cn.icnt.dinners.utils.PreferencesUtils;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * cn.icnt.dinners.fragment.FragmentBank
 * @author Andrew Lee <br/>
 * create at 2014年8月12日 下午3:42:27
 */
public class FragmentBank extends Fragment implements OnClickListener{
	private static final String TAG = "FragmentBank";
	private String AddressXML;		         //xml格式的中国省市区信息
	private Button btn_province;
	private Button btn_city;
	private Button btn_county;
	private Button btn_bank;
	private Button btn_submit;
	private EditText et_name;
	private EditText et_card_no;
	private EditText et_card_place;
	private EditText et_card_money;
	private EditText et_pay_passwd;
	private List<String> bankList;
	private static final int UPDATE_UI = 1;
	private TextView tv0;
	private TextView tv1;
	private String acc;
	
	private float f;
	private float f1;
	private List<ProvinceModel> provinceList; //地址列表
	private int pPosition;
	private int cPosition;
	private boolean isCity = true;
	private boolean isCounty = true;
	
	    
	
	
	
	
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		        // Inflate the layout for this fragment
		 View view =inflater.inflate(R.layout.myaccount_fragment_bank, container, false);
		 
		 btn_province = (Button)view.findViewById(R.id.bank_frag_province);
		 btn_city = (Button)view.findViewById(R.id.bank_frag_city);
		 btn_county = (Button)view.findViewById(R.id.bank_frag_zone);
		 btn_bank = (Button)view.findViewById(R.id.bank_frag_bank);	
		 btn_submit=(Button)view.findViewById(R.id.bank_frag_submit);
		 
		 
		 et_name=(EditText)view.findViewById(R.id.bank_frag_name);
		 et_card_no=(EditText)view.findViewById(R.id.bank_frag_card_no);
		 et_card_place=(EditText)view.findViewById(R.id.bank_frag_card_place);
		 et_card_money=(EditText)view.findViewById(R.id.bank_frag_card_money);
		 et_pay_passwd=(EditText)view.findViewById(R.id.bank_frag_card_passwd);
		 
		 
		 
		 initFindView();
		 initData();
		 
		 
		 return view;

}
	 
	 
	 
	 /**
	 *@param account 账号
	 *@param amount 金额
	 *@param payment_pwd 密码
	 *@param name 姓名
	 *@param province 省份
	 *@param city 城市
	 *@param zone 区县
	 *@param account_bank 开户行
	 *@param bank 银行
	 *@return
	 */
	protected Map<String, String> send(String account,String amount,String payment_pwd,String name,String province,String city,String zone,String account_bank,String bank) {
			MapPackage mp = new MapPackage();
			mp.setPath("apply_money");
			mp.setHead(getActivity());
			mp.setPara("type","1");//银行卡提现
			mp.setPara("account",account);
			mp.setPara("bank",bank);
			mp.setPara("name",name);
			mp.setPara("province",province);
			mp.setPara("city",city);
			mp.setPara("zone",zone);
			
			mp.setPara("account_bank",account_bank);
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

	 
	 public void initFindView(){
			
			btn_province.setOnClickListener(this);
			btn_city.setOnClickListener(this);
			btn_county.setOnClickListener(this);
			btn_bank.setOnClickListener(this);
			btn_submit.setOnClickListener(this);
			bankList=new ArrayList<String>();
			bankList.add("中国建设银行");
			bankList.add("中国工商银行");
			bankList.add("中国银行");
			bankList.add("中国农业银行");
			bankList.add("中国交通银行");
			bankList.add("中国邮政银行");
			bankList.add("中国招商银行");
			bankList.add("中国民生银行");
			bankList.add("中国兴业银行");
			bankList.add("中国中信银行");
			bankList.add("中国光大银行");
			bankList.add("中国广发银行");
			bankList.add("中国华夏银行");
			bankList.add("中国浦发银行");
			bankList.add("深圳发展银行");
		}
	 
		
		public void initData(){        
			AddressXML = getRawAddress().toString();//获取中国省市区信息
			try {
				analysisXML(AddressXML);
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//初始化button数据
			btn_province.setText(provinceList.get(12).getProvince());
			btn_city.setText(provinceList.get(12).getCity_list().get(1).getCity());
			btn_county.setText(provinceList.get(12).getCity_list().get(1).getCounty_list().get(2).getCounty());
			btn_bank.setText(bankList.get(0));
			//初始化列表下标
			pPosition = 12;
			cPosition = 1;
		}
		
		/**
		 * 获取地区raw里的地址xml内容
		 * */
		public StringBuffer getRawAddress(){
			InputStream in = getResources().openRawResource(R.raw.address);
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				br.close();
				isr.close();
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return sb;
		}
		
		/**
		 * 解析省市区xml，
		 * 采用的是pull解析，
		 * 为什么选择pull解析：因为pull解析简单浅显易懂！
		 * */
		public void analysisXML(String data) throws XmlPullParserException{
			try {
				ProvinceModel provinceModel = null;
				CityModel cityModel= null;
				CountyModel countyModel= null;
				List<CityModel> cityList = null;
				List<CountyModel> countyList= null;
				
				InputStream xmlData = new ByteArrayInputStream(data.getBytes("UTF-8"));
				XmlPullParserFactory factory = null;
				factory = XmlPullParserFactory.newInstance();
				XmlPullParser parser;
				parser = factory.newPullParser();
				parser.setInput(xmlData, "utf-8");
				String currentTag = null;
				
				String province;
				String city;
				String county;
				
				int type = parser.getEventType();
				while (type != XmlPullParser.END_DOCUMENT) {
					String typeName = parser.getName();
					
					if (type == XmlPullParser.START_TAG) {
						if("root".equals(typeName)){
							provinceList = new ArrayList<ProvinceModel>();
							
						}else if("province".equals(typeName)){
							province = parser.getAttributeValue(0);//获取标签里第一个属性,例如<city name="北京市" index="1">中的name属性
							provinceModel = new ProvinceModel();
							provinceModel.setProvince(province);
							cityList = new ArrayList<CityModel>();
							
						}else if("city".equals(typeName)){
							city = parser.getAttributeValue(0);	
							cityModel = new CityModel();
							cityModel.setCity(city);
							countyList = new ArrayList<CountyModel>();
							
						}else if("area".equals(typeName)){
							county = parser.getAttributeValue(0);	
							countyModel  = new CountyModel();
							countyModel.setCounty(county);
							
						}
						
						currentTag = typeName;
						
					} else if (type == XmlPullParser.END_TAG) {
						if("root".equals(typeName)){
							
						}else if("province".equals(typeName)){
							provinceModel.setCity_list(cityList);
							provinceList.add(provinceModel);
							
						}else if("city".equals(typeName)){
							cityModel.setCounty_list(countyList);
							cityList.add(cityModel);
							
						}else if("area".equals(typeName)){
							countyList.add(countyModel);
						}
						
						
					} else if (type == XmlPullParser.TEXT) {
						
						currentTag = null;
					}
					
						type = parser.next();
				}
			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bank_frag_province:
				createDialog(1);
				break;
				
			case R.id.bank_frag_city:
				if(isCity == true){
					createDialog(2);
				}
				break;
			case R.id.bank_frag_zone:
				if(isCounty == true){
					createDialog(3);
				}
				break;
			case R.id.bank_frag_bank:
					createDialog(4);
				break;
			case R.id.bank_frag_submit:
				LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
				View convertView=layoutInflater.inflate(R.layout.myaccount_viewpager_get_money, null);
				if(convertView!=null)
				{
					tv0=(TextView)convertView.findViewById(R.id.myacount_money_ali);
					tv1=(TextView)convertView.findViewById(R.id.myacount_money_bank);
				}
				String account=et_card_no.getText().toString();
				String payment_pwd=et_pay_passwd.getText().toString();
				String amount=et_card_money.getText().toString();
				String name=et_name.getText().toString();
				String province=btn_province.getText().toString();
				String city=btn_city.getText().toString();
				String zone=btn_county.getText().toString();
				String account_bank=et_card_place.getText().toString();
				String bank=btn_bank.getText().toString();
				String money=PreferencesUtils
						.getValueFromSPMap(getActivity(), PreferencesUtils.Keys.ACCOUNT_NO,
							"0.00");
				try {
					f = Float.parseFloat(amount);
					f1=Float.parseFloat(money);
				} catch (Exception e) {
				}
				
			if(StringUtils.isEmpty(account)||StringUtils.isEmpty(amount)||StringUtils.isEmpty(payment_pwd)||StringUtils.isEmpty(account_bank)||StringUtils.isEmpty(name)){
					
					Toast.makeText(getActivity(), "姓名，卡号，开户行，提现金额或者支付密码不能为空",
							Toast.LENGTH_SHORT).show();
				}else{
					if(f<1.00){
						Toast.makeText(getActivity(), "提现金额不能小于一元",
								Toast.LENGTH_SHORT).show();
						
					}else if(f>f1){
						Toast.makeText(getActivity(), "提现金额不能大于余额",
								Toast.LENGTH_SHORT).show();
						
					}else{
						Map<String, String> map = send(account,amount,payment_pwd,name,province,city,zone,account_bank,bank);
						if (map != null && map.get("code").equals("10000")) {

							// 调用支付宝
							Toast.makeText(getActivity(), map.get("content"),
									Toast.LENGTH_LONG).show();
							PreferencesUtils.putValueToSPMap(getActivity(), PreferencesUtils.Keys.ACCOUNT_NO, map.get("balance"));
							acc = "  " + PreferencesUtils
									.getValueFromSPMap(getActivity(), PreferencesUtils.Keys.ACCOUNT_NO,
											"0.00") + "元";
							
							tv0.setText(acc);//启用handle更新ui线程
							tv1.setText(acc);
							
						} else {
							Toast.makeText(getActivity(), "提现失败，请稍后重试",
									Toast.LENGTH_LONG).show();
						}}}
				
				
			break;
			default:
				break;
			}
		}
		
		/**
		 * 根据调用类型显示相应的数据列表
		 * @param type 显示类型 1.省；2.市；3.县、区
		 */
		public void createDialog(final int type){
			ListView lv = new ListView(getActivity()); 
			final Dialog dialog = new Dialog(getActivity(),R.style.MyDialog_province);
			dialog.setTitle("请选择");
			
			if(type == 1){
				ProvinceAdapter pAdapter = new ProvinceAdapter(provinceList);
				lv.setAdapter(pAdapter);
				
			}else if(type == 2){
				CityAdapter cAdapter = new CityAdapter(provinceList.get(pPosition).getCity_list());
				lv.setAdapter(cAdapter);
			}else if(type ==3){
				CountyAdapter coAdapter = new CountyAdapter(provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list());
				lv.setAdapter(coAdapter);
			}else if(type == 4){
				BankAdapter bankAdapter=new BankAdapter(bankList);
				lv.setAdapter(bankAdapter);
			}
			
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					if(type == 1){
						pPosition = position;
						btn_province.setText(provinceList.get(position).getProvince());
						//判断该省下是否有市级
						if(provinceList.get(position).getCity_list().size() < 1){
							btn_city.setText("");
							btn_county.setText("");
							isCity = false;
							isCounty = false;
						}else{
							isCity = true;
							btn_city.setText(provinceList.get(position).getCity_list().get(0).getCity());
							cPosition = 0;
							//判断该市下是否有区级或县级
							if (provinceList.get(position).getCity_list().get(0).getCounty_list().size() < 1) {
								btn_county.setText("");
								isCounty = false;
							
							}else{
								isCounty = true;
								btn_county.setText(provinceList.get(position).getCity_list().get(0).getCounty_list().get(0).getCounty());
							}
							
						}
						
					}else if(type == 2){
						cPosition = position;
						btn_city.setText(provinceList.get(pPosition).getCity_list().get(position).getCity());
						if (provinceList.get(pPosition).getCity_list().get(position).getCounty_list().size() < 1) {
							btn_county.setText("");
							isCounty = false;
						}else{
							isCounty = true;
							btn_county.setText(provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list().get(0).getCounty());
						}
						
					}else if(type == 3){
						btn_county.setText(provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list().get(position).getCounty());
					
					}else if(type == 4){
						btn_bank.setText(bankList.get(position));
					}
					
					dialog.dismiss();
				}
			});
			
			dialog.setContentView(lv);
			dialog.show();
		}
		
		class ProvinceAdapter extends BaseAdapter{
			public List<ProvinceModel> adapter_list;
			public ProvinceAdapter(List<ProvinceModel> list){
				adapter_list = list;
			}
			
			@Override
			public int getCount() {
				return adapter_list.size();
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public View getView(int position, View arg1, ViewGroup arg2) {
				TextView tv = new TextView(getActivity());
				tv.setPadding(20, 20, 20, 20);
				tv.setTextSize(18);
				tv.setText(adapter_list.get(position).getProvince());
				return tv;
			}
			
		}
		
		class CityAdapter extends BaseAdapter{
			public List<CityModel> adapter_list;
			public CityAdapter(List<CityModel> list){
				adapter_list = list;
			}
			
			@Override
			public int getCount() {
				return adapter_list.size();
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public View getView(int position, View arg1, ViewGroup arg2) {
				TextView tv = new TextView(getActivity());
				tv.setPadding(20, 20, 20, 20);
				tv.setTextSize(18);
				tv.setText(adapter_list.get(position).getCity());
				return tv;
			}
			
		}
		
		class CountyAdapter extends BaseAdapter{
			public List<CountyModel> adapter_list;
			public CountyAdapter(List<CountyModel> list){
				adapter_list = list;
			}
			
			@Override
			public int getCount() {
				return adapter_list.size();
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public View getView(int position, View arg1, ViewGroup arg2) {
				TextView tv = new TextView(getActivity());
				tv.setPadding(20, 20, 20, 20);
				tv.setTextSize(18);
				tv.setText(adapter_list.get(position).getCounty());
				return tv;
			}
			
		}
		class BankAdapter extends BaseAdapter{
			public List<String> adapter_list;
			public BankAdapter(List<String> list){
				adapter_list = list;
			}
			
			@Override
			public int getCount() {
				return adapter_list.size();
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public View getView(int position, View arg1, ViewGroup arg2) {
				TextView tv = new TextView(getActivity());
				tv.setPadding(20, 20, 20, 20);
				tv.setTextSize(18);
				tv.setText(adapter_list.get(position));
				return tv;
			}
			
		}
		
		 public void onResume() {
		     super.onResume();
		     MobclickAgent.onPageStart("FragmentBank"); //统计页面
		 }
		 public void onPause() {
		     super.onPause();
		     MobclickAgent.onPageEnd("FragmentBank"); 
		 } 
}
