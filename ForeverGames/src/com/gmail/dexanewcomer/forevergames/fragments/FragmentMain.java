package com.gmail.dexanewcomer.forevergames.fragments;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.dexanewcomer.forevergames.Global;
import com.gmail.dexanewcomer.forevergames.R;
import com.gmail.dexanewcomer.http.aHttpClient;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentMain extends Fragment {
	View rootView;
	private String userinfo;
	private TextView  fullname, account, addHolding;
	private static TextView gold;
	private String login;
	private String pass;
	protected com.gmail.dexanewcomer.forevergames.dialogs.addHolding mDialog;
	
	public FragmentMain(String json){
		
			
			this.userinfo = json;
			try {
				JSONObject reader = new JSONObject(userinfo);
				this.login = reader.getString("login");
				this.pass = reader.getString("pass");
				
				Global.setLOGIN(login);
				Global.setPASS(pass);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView =  inflater.inflate(R.layout.fragment_main, null);
		fullname = (TextView) rootView.findViewById(R.id.fullname);
		account = (TextView) rootView.findViewById(R.id.account);
		addHolding = (TextView) rootView.findViewById(R.id.addHolding);
		gold = (TextView) rootView.findViewById(R.id.gold);
		JSONObject reader;
		System.out.println("userinfo= " + userinfo);
		try {
			reader = new JSONObject(userinfo);
			if(reader.getString("account") != null)
				fullname.setText(reader.getString("fullname"));
				account.setText(reader.getString("account"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addHolding.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				gold.setVisibility(View.VISIBLE);

			}});
			
		gold.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mDialog = new com.gmail.dexanewcomer.forevergames.dialogs.addHolding(getActivity(),"gold");
	        	mDialog.setCancelable(false);
	        	mDialog.show();
				
			}});
		//Получаем цены лотов, ну пока одного лота.
		startTimer();
        
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("login", Global.LOGIN));
		nameValuePairs.add(new BasicNameValuePair("pass", Global.PASS));
		nameValuePairs.add(new BasicNameValuePair("act", "priceall"));
		aHttpClient client = new aHttpClient(this.getActivity());
		client.post(Global.SERVER + "/api.php", nameValuePairs,client.PRICEALL);
		return rootView;
	}
	public static void setPrice(String json, Activity mActivity) {
		try {
			JSONObject reader = new JSONObject(json);
			JSONArray prices = reader.getJSONArray("price");
			for (int i = 0; i < prices.length(); i++) {
	            JSONObject lot = prices.getJSONObject(i); 
	         if(lot.get("name").equals("gold")) {
	        	 gold.setText(mActivity.getString(R.string.gold) + "\t\t\t" + lot.getString("price") + "руб.");
	         }
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void getPrice(){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("login", Global.LOGIN));
		nameValuePairs.add(new BasicNameValuePair("pass", Global.PASS));
		nameValuePairs.add(new BasicNameValuePair("act", "priceall"));
		aHttpClient client = new aHttpClient(this.getActivity());
		client.post(Global.SERVER + "/api.php", nameValuePairs,client.PRICEALL);
	}
	private void startTimer(){
		new CountDownTimer(2000000000, 10000) { //Я установил интервал в 10 секунд.Чтоб проверить пока надо в ручную изменить цену в базе данных.
            public void onTick(long millisUntilFinished) {
            	getPrice();
            	System.out.println("tick");
            }
            public void onFinish() {
            	
            }
        }.start();
	}
	
}
