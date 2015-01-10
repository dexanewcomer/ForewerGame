package com.gmail.dexanewcomer.forevergames.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.dexanewcomer.forevergames.MainActivity;
import com.gmail.dexanewcomer.forevergames.R;


public class FragmentShop extends Fragment {
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {		
		rootView = inflater.inflate(R.layout.fragment_shop, container,false);
		return rootView;
	}

}