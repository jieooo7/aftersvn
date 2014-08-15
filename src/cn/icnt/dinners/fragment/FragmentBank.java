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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.model.CityModel;
import cn.icnt.dinners.model.CountyModel;
import cn.icnt.dinners.model.ProvinceModel;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * cn.icnt.dinners.fragment.FragmentBank
 * @author Andrew Lee <br/>
 * create at 2014年8月12日 下午3:42:27
 */
public class FragmentBank extends Fragment{
	private static final String TAG = "FragmentBank";
	private int pPosition;
	private int cPosition;
	private String AddressXML;
	private List<ProvinceModel> provinceList;
	private List<CityModel> cityList;
	private List<CountyModel> countyList;
	private List<String> provinceList1;
	private List<String> cityList1;
	private List<String> areaList1;
 	private Spinner provinceSpinner = null;  //省级（省、直辖市）
	private Spinner citySpinner = null;     //地级市
	private Spinner countySpinner = null;    //县级（区、县、县级市）
//	ArrayAdapter<String> provinceAdapter = null;  //省级适配器
//	ArrayAdapter<String> cityAdapter = null;    //地级适配器
//	ArrayAdapter<String> countyAdapter = null;    //县级适配器
	static int provincePosition = 13;
	    
	
	
	
	
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		        // Inflate the layout for this fragment
		 View view =inflater.inflate(R.layout.myaccount_fragment_bank, container, false);
		 
		 provinceSpinner = (Spinner)view.findViewById(R.id.bank_frag_province);
	     citySpinner = (Spinner)view.findViewById(R.id.bank_frag_city);
	     countySpinner = (Spinner)view.findViewById(R.id.bank_frag_zone);
		 
		 initData();
		 
		 
		 return view;

}
	 
	 public void initData(){        
			AddressXML = getRawAddress().toString();//获取中国省市区信息
			try {
				analysisXML(AddressXML);
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
//			
//			provinceList1=new ArrayList<String>();
//			cityList1=new ArrayList<String>();
//			areaList1=new ArrayList<String>();
//			for(int i=0;i<provinceList.size();i++){
//				
//				provinceList1.add(provinceList.get(i).getProvince());
//				for(int j=0;j<provinceList.get(i).getCity_list().size();j++){
//					cityList1.add(provinceList.get(i).getCity_list().get(j).getCity());
//					for(int k=0;k<provinceList.get(i).getCity_list().get(j).getCounty_list().size();k++){
//						
//						areaList1.add(provinceList.get(i).getCity_list().get(j).getCounty_list().get(k).getCounty());
//					}
//					
//				}
//				
//				
//			}
			
			
				ProvinceAdapter provinceAdapter = new ProvinceAdapter(provinceList);
		        provinceSpinner.setAdapter(provinceAdapter);
		        provinceSpinner.setSelection(13,true);  //设置默认选中项，此处为默认选中第4个值
		        
		        CityAdapter cityAdapter = new CityAdapter(provinceList.get(13).getCity_list());
		        citySpinner.setAdapter(cityAdapter);
		        citySpinner.setSelection(2,true); 
		        CountyAdapter countyAdapter = new CountyAdapter(provinceList.get(13).getCity_list().get(2).getCounty_list());
		        countySpinner.setAdapter(countyAdapter);
		        countySpinner.setSelection(3,true); 
		        
		        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		        {

		            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
		            @Override
		            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
		            {
		                //position为当前省级选中的值的序号
		                
		                //将地级适配器的值改变为city[position]中的值
		            	pPosition = position;
		            	CityAdapter cityAdapter = new CityAdapter(provinceList.get(pPosition).getCity_list());
		 		        citySpinner.setAdapter(cityAdapter);
		            }

		            @Override
		            public void onNothingSelected(AdapterView<?> arg0)
		            {
		                
		            }
		            
		        });
		        
		        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		        {

		            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
		            @Override
		            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
		            {
		                //position为当前省级选中的值的序号
		                
		                //将地级适配器的值改变为city[position]中的值
		            	cPosition = position;
		            	CountyAdapter countyAdapter = new CountyAdapter(provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list());
		            	countySpinner.setAdapter(countyAdapter);
		            }

		            @Override
		            public void onNothingSelected(AdapterView<?> arg0)
		            {
		                
		            }
		            
		        });
		        
			//初始化button数据
//			provinceSpinner.setText(provinceList.get(0).getProvince());
//			btn_city.setText(provinceList.get(0).getCity_list().get(0).getCity());
//			btn_county.setText(provinceList.get(0).getCity_list().get(0).getCounty_list().get(0).getCounty());
			//初始化列表下标
//			pPosition = 0;
//			cPosition = 0;
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
				 LayoutInflater inflater=LayoutInflater.from(getActivity());
				 arg1=inflater.inflate(R.layout.city_spinner_item, null); 
				 if(arg1!=null) {
					 TextView tv=(TextView)arg1.findViewById(R.id.city_spinner_tv);
					 
					 tv.setText(adapter_list.get(position).getProvince());
					 
				 }
				 return arg1;
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
//			 LayoutInflater inflater=LayoutInflater.from(getActivity());
				 LayoutInflater inflater=LayoutInflater.from(getActivity());
				 arg1=inflater.inflate(R.layout.city_spinner_item, null); 
				 if(arg1!=null) {
					 TextView tv=(TextView)arg1.findViewById(R.id.city_spinner_tv);
					 
					 tv.setText(adapter_list.get(position).getCity());
					 
				 }
				 return arg1;
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
				 LayoutInflater inflater=LayoutInflater.from(getActivity());
				 arg1=inflater.inflate(R.layout.city_spinner_item, null); 
				 if(arg1!=null) {
					 TextView tv=(TextView)arg1.findViewById(R.id.city_spinner_tv);
					 
					 tv.setText(adapter_list.get(position).getCounty());
					 
				 }
				 return arg1;
//				tv.setPadding(20, 20, 20, 20);
//				tv.setTextSize(18);
			}
			
		}		

		
		

	 
}
