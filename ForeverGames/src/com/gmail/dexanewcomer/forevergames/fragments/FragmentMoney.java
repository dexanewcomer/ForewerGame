package com.gmail.dexanewcomer.forevergames.fragments;


import com.gmail.dexanewcomer.forevergames.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class FragmentMoney extends Fragment {
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_money, null);
		TabHost tabs = (TabHost) rootView.findViewById(android.R.id.tabhost);

		tabs.setup();

		TabHost.TabSpec spec = tabs.newTabSpec("tag1");

		spec.setContent(R.id.tab1);
		spec.setIndicator("ВВОД");
		tabs.addTab(spec);

		spec = tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("ВЫВОД");
		tabs.addTab(spec);


		tabs.setCurrentTab(0);
		return rootView;
	}
	
}
