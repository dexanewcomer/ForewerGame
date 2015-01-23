package com.gmail.dexanewcomer.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;

import com.gmail.dexanewcomer.forevergames.LoginActivity;
import com.gmail.dexanewcomer.forevergames.dialogs.addHolding;
import com.gmail.dexanewcomer.forevergames.fragments.FragmentMain;
import com.gmail.dexanewcomer.forevergames.fragments.FragmentMoney;

public class aHttpClient {
	OnJsonListener onJson;
	public String json;
	Activity mActivity;
	private OnJsonListener mListener;
	
	public static final  int 	LOGIN			 = 0;
	public static final  int 	PAY			 	 = 1;
	public static final  int 	WITHDRAWAL    	 = 2;
	public static final  int 	PRICE			 = 3;
	public static final  int 	PRICEALL		 = 4;
	
	public aHttpClient(Activity mActivity){
		this.mActivity = mActivity;
		
	}
	
	public void post(final String url, final List<NameValuePair> nameValuePairs) {
		
		 new Thread(new Runnable() {
			
	        	public void run() {
	        	
	        		HttpParams httpParams = new BasicHttpParams();
	                HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
	                HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
	                HttpProtocolParams.setHttpElementCharset(httpParams, "UTF-8");

	        		HttpClient httpclient = new DefaultHttpClient(httpParams);
	        		HttpPost httppost = new HttpPost(url);
	        		

	        		try {
	        		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
	        		HttpResponse response = httpclient.execute(httppost);
	        		if(response.getStatusLine().getStatusCode() == 200)
                    {
	        			HttpEntity entity = response.getEntity();
	        			
	        			if (entity != null) 
                        {
	        				String responseBody = EntityUtils.toString(entity);
	        				json = responseBody.toString();
	        				try{
	        					if (mListener != null)
	        						 mListener.onJson();
	        				}
	        				catch(Exception e){}
	        				
                        }
                    }
	        		} catch (ClientProtocolException e) {
	        			System.err.println(e.toString());
	        		} catch (IOException e) {
	        			System.err.println(e.toString());
	        		}
	        		catch(IllegalStateException e){
	        			System.err.println(e.toString());
	    			}
	        	}
		 }).start();   
		}  

	



	
	public interface OnJsonListener {
		public void onJson();

	}
	public void setOnJsonListener(OnJsonListener eventListener) {
	 mListener=eventListener;
	}
	
	
	

}
