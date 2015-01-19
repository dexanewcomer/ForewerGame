package com.gmail.dexanewcomer.forevergames;

import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.dexanewcomer.forevergames.adapters.menuAdapter;
import com.gmail.dexanewcomer.forevergames.fragments.FragmentMain;
import com.gmail.dexanewcomer.forevergames.fragments.FragmentMoney;
import com.gmail.dexanewcomer.forevergames.fragments.FragmentShop;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class HomeActivity extends Activity {


	private String userinfo;
	private LayoutInflater inflater;
	private ViewGroup container;
	private Fragment fragmentMain;
	private Fragment fragmentMoney;
	private Fragment fragmentShop;
	private int position = 1;
	private CharSequence mTitle;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiti_container);
		Bundle bundle = getIntent().getExtras();
		inflater = this.getLayoutInflater();

		GridView gridview = (GridView) findViewById(R.id.gridMenu);
		gridview.setAdapter(new menuAdapter(this));
		
//////////////////////////////////////////////////////
//Принимаем данные о пользователе в виде json string//
//////////////////////////////////////////////////////

		userinfo = bundle.getString("user");
		
		onSectionAttached(position);
		gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onSectionAttached(position +1);
				
			}});
		

		
	}
	
	
	
	
	public void onSectionAttached(int number) {
		switch (number) {
		default:
		case 1:
			mTitle = getString(R.string.title_main);
			try{
				if(fragmentMain == null)
					fragmentMain = new FragmentMain(userinfo);	
			getFragmentManager()
			.beginTransaction().replace(R.id.container, fragmentMain)
			.setCustomAnimations(R.animator.ltr,R.animator.rtl)
			.commit();
			}
			catch(Exception e){
				
			}
			break;
		case 2:
			mTitle = getString(R.string.title_money);
			fragmentMoney = new FragmentMoney(userinfo);
			try{
			getFragmentManager()
			.beginTransaction()
			.replace(R.id.container, fragmentMoney).setCustomAnimations(R.animator.ltr,R.animator.rtl)
			.commit();
			}
			catch(Exception e){
				
			}
			
			break;
		case 3:
			mTitle = getString(R.string.title_shop);
			fragmentShop = new FragmentShop();
			try{
			getFragmentManager()
			.beginTransaction()
			.replace(R.id.container, fragmentShop).setCustomAnimations(R.animator.ltr,R.animator.rtl)
			.commit();
			}
			catch(Exception e){
				
			}
			break;
		}
		
	}
	 

	
}