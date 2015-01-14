package com.gmail.dexanewcomer.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;

import com.gmail.dexanewcomer.forevergames.LoginActivity;
import com.gmail.dexanewcomer.forevergames.fragments.FragmentMoney;

public class aHttpClient {
	public String json;
	Activity mActivity;
	public static final  int 	LOGIN			 = 0;
	public static final  int 	PAY			 	 = 1;
	public static final  int 	WITHDRAWAL    	 = 2;
	
	public aHttpClient(Activity mActivity){
		this.mActivity = mActivity;
		
	}
	
	public void post(final String url, final List<NameValuePair> nameValuePairs,final int callback) {
		
		 new Thread(new Runnable() {
			
	        	public void run() {
	        	
	        	    
	        		HttpClient httpclient = new DefaultHttpClient();
	        		HttpPost httppost = new HttpPost(url);

	        		try {
	        		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        		HttpResponse response = httpclient.execute(httppost);
	        		if(response.getStatusLine().getStatusCode() == 200)
                    {
	        			HttpEntity entity = response.getEntity();
	        			System.out.println("Entity:"+entity);
	        			if (entity != null) 
                        {
	        				String responseBody = EntityUtils.toString(entity);
	        				json = responseBody.toString();
	        				callback(mActivity,callback);
	        				
                        }
                    }
	        		} catch (ClientProtocolException e) {
	        			System.err.println(e.toString());
	        		} catch (IOException e) {
	        			System.err.println(e.toString());
	        		}
	        	}
		 }).start();   
		}  
	private void callback(final Activity mActivity,final int argv){
		mActivity.runOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	switch (argv){
		    	case LOGIN :
		    		LoginActivity.login(json,mActivity);
		    	break;
		    	case PAY:
		    		FragmentMoney.pay(json);
		    	break;
		    	}
		    }
		});
	}
	
	
	
	

}
