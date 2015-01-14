package com.gmail.dexanewcomer.forevergames.fragments;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.gmail.dexanewcomer.forevergames.R;
import com.gmail.dexanewcomer.http.aHttpClient;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;


public class FragmentMoney extends Fragment {

	private Activity mActivity;
	private Button payBT,giveBT;
	private EditText summET,idET,commET;
	private String id,summ,comment;
	
	private final String server = "http://192.168.0.102/api.php";

	private View payView, withdrawalView,rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_money, null);
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

			@Override
			public void onClick(View v) {
				
				summ 		= summET.getText().toString();
				comment		= commET.getText().toString();
				id 			= idET.getText().toString();
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
				nameValuePairs.add(new BasicNameValuePair("act", "pay"));
				nameValuePairs.add(new BasicNameValuePair("id", id));
				nameValuePairs.add(new BasicNameValuePair("summ", summ));
				nameValuePairs.add(new BasicNameValuePair("comm", comment));				
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
	
	public static void pay(String json){
		System.out.println(json);//Чтоб посмотреть работает ли этот метод вообще.
	}
	
}
