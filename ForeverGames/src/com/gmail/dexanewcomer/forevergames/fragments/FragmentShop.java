package com.gmail.dexanewcomer.forevergames.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.dexanewcomer.forevergames.HomeActivity;
import com.gmail.dexanewcomer.forevergames.R;


public class FragmentShop extends Fragment {
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {		
		rootView = inflater.inflate(R.layout.fragment_shop, container,false);
		EditText test = (EditText) rootView.findViewById(R.id.editText1);
		test.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					HomeActivity.hide();
				
			}});
		return rootView;
	}

}