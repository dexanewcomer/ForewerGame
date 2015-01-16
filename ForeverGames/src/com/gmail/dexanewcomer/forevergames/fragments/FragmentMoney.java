package com.gmail.dexanewcomer.forevergames.fragments;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.dexanewcomer.forevergames.R;
import com.gmail.dexanewcomer.forevergames.dialogs.MessageBox;
import com.gmail.dexanewcomer.http.aHttpClient;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;


public class FragmentMoney extends Fragment {

	private static String title;
	private Activity mActivity;
	private Button payBT,giveBT;
	private EditText summET,idET,commET;
	private String id,summ,comment,userinfo;
	
	private SharedPreferences mSettings;
	private String server;
    private static final String 		APP_PREFERENCES_SERVER = 		"server";



	private View payView, withdrawalView,rootView;

	public FragmentMoney(String userinfo) {
		// TODO Auto-generated constructor stub
		this.userinfo = userinfo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_money, null);
		mSettings=PreferenceManager.getDefaultSharedPreferences(getActivity());
		server = mSettings.getString(APP_PREFERENCES_SERVER, null) + "/api.php";
		payView = inflater.inflate(R.layout.pay, null);
		withdrawalView = inflater.inflate(R.layout.withdrawal, null);
		TabHost tabs = (TabHost) rootView.findViewById(android.R.id.tabhost);
		
		mActivity = getActivity();
		//Ввод средств
		payBT = (Button) payView.findViewById(R.id.payBT);
		summET = (EditText) payView.findViewById(R.id.summToPay);
		idET = (EditText) payView.findViewById(R.id.idToPay);
		commET = (EditText)payView.findViewById(R.id.commentToPay);
		
		payBT.setOnClickListener(new OnClickListener(){

			private String login;
			private String pass;

			@Override
			public void onClick(View v) {
				
				summ 		= summET.getText().toString();
				comment		= commET.getText().toString();
				id 			= idET.getText().toString();
				try {
					JSONObject reader = new JSONObject(userinfo);
					login = reader.getString("login");
					pass = reader.getString("pass");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
				nameValuePairs.add(new BasicNameValuePair("login", login));
				nameValuePairs.add(new BasicNameValuePair("pass", pass));
				nameValuePairs.add(new BasicNameValuePair("act", "pay"));
				nameValuePairs.add(new BasicNameValuePair("transaction", id));
				nameValuePairs.add(new BasicNameValuePair("summ", summ));
				nameValuePairs.add(new BasicNameValuePair("comment", comment));				
				aHttpClient client = new aHttpClient(mActivity);
				client.post(server, nameValuePairs,client.PAY);
				
			}});

		tabs.setup();		
		TabHost.TabSpec tab1 = tabs.newTabSpec("tab1");
	    tab1.setIndicator("ВВОД");
	    tab1.setContent(new TabHost.TabContentFactory() {
	        @Override
	        public View createTabContent(String tag) {
	            
	            return payView;
	        }
	    });

	   tabs.addTab(tab1);
	    
	    TabHost.TabSpec tab2 = tabs.newTabSpec("tab2");
	    tab2.setIndicator("ВЫВОД");
	    tab2.setContent(new TabHost.TabContentFactory() {
	        @Override
	        public View createTabContent(String tag) {
	            
	            return withdrawalView;
	        }
	    });
	    
	    tabs.addTab(tab2);





		tabs.setCurrentTab(0);
		
		return rootView;
	}
	
	public static void pay(String json,Activity mActivity){
		JSONObject reader;
		System.out.println(json);
		try {
			reader = new JSONObject(json);
			String text = reader.getString("content");
			boolean error = reader.getBoolean("error");
			if(error){
				
				title = "Ошибка!";
				MessageBox msbox = new MessageBox(mActivity,text,title);
				msbox.show();
			} else{
				title = "Операция выполнена";
				MessageBox msbox = new MessageBox(mActivity,text,title);
				msbox.show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
