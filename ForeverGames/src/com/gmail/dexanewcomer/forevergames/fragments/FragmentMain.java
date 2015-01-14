package com.gmail.dexanewcomer.forevergames.fragments;


import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.dexanewcomer.forevergames.R;

import android.app.Fragment;
import android.os.Bundle;
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
	private TextView  fullname, account;
	private ImageView addHolding;
	
	public FragmentMain(String json){
		
			System.out.println("json= " + json);
			this.userinfo = json;
			
			
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView =  inflater.inflate(R.layout.fragment_main, null);
		fullname = (TextView) rootView.findViewById(R.id.fullname);
		account = (TextView) rootView.findViewById(R.id.account);
		addHolding = (ImageView) rootView.findViewById(R.id.addHolding);
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

			private com.gmail.dexanewcomer.forevergames.dialogs.addHolding mDialog;

			@Override
			public void onClick(View v) {
				mDialog = new com.gmail.dexanewcomer.forevergames.dialogs.addHolding(getActivity());
	        	mDialog.setCancelable(false);
	        	mDialog.show();
				
			}});
			
		
		return rootView;
	}
	
}
