/*
 * HttpSendRecv.java
 * classes : com.cloud.app.http.HttpSendRecv
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��6�� ����11:20:25
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.http;

import java.io.IOException;





import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;



/**
 * com.cloud.app.http.HttpSendRecv
 * @author Andrew Lee <br/>
 * create at 2014年7月1日 下午3:55:05
 */
public class HttpSendRecv extends AsyncTask<Void, Void, String>{
	private static final String TAG = "HttpSendRecv";
	public static boolean netStat=false;
	private String path;
	private String st;
//	�������������ݲ���
	public HttpSendRecv(String path,String st){
		this.path=path;
		this.st=st;
	}
	

    /** 
     *���� Post���� 
     * @throws IOException 
     * @throws ClientProtocolException 
     */  
   
	public synchronized String HttpSendRecve(String path,String st){
		HttpPost httpRequest = new HttpPost(path);
		httpRequest.setHeader("content-type", "application/json");
		String strResult="";
		 try  
	        {  
			 
		         
		      StringEntity se = new StringEntity(st);
	          /* �������������������*/  
	          httpRequest.setEntity(se); ;  
	          /*�������󲢵ȴ���Ӧ*/  
	          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);  
	          /*��״̬��Ϊ200 ok*/  
	          if(httpResponse.getStatusLine().getStatusCode() == 200)   
	          {  
	            /*����������*/  
	             strResult = EntityUtils.toString(httpResponse.getEntity());  
	             
	          }  
	          else  
	          {  
	            netStat=true;
	          }  
	        }  
	        catch (ClientProtocolException e)  
	        {   
	          netStat=true;
	          e.printStackTrace();  
	        }  
	        catch (IOException e)  
	        {   
	          netStat=true;
	          e.printStackTrace();  
	        }  
	        catch (Exception e)  
	        {   
	          netStat=true;
	          e.printStackTrace();   
	        } 
		return strResult;
	}
	
	/* 
	 * ��̨�߳�ִ�еķ���
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(Void... params) {
		String s;
		s=HttpSendRecve(path,st);
		return s;
	}
	

}
