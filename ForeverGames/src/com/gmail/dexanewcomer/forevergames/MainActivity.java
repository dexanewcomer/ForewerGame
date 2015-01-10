package com.gmail.dexanewcomer.forevergames;

import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.dexanewcomer.forevergames.fragments.FragmentMain;
import com.gmail.dexanewcomer.forevergames.fragments.FragmentMoney;
import com.gmail.dexanewcomer.forevergames.fragments.FragmentShop;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	private String userinfo;
	private JSONObject reader;
	LayoutInflater inflater;
	ViewGroup container;


	private Fragment fragmentMain;
	private Fragment fragmentMoney;
	private Fragment fragmentShop;
	
	private int position = 1;
	
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		onSectionAttached(position);
		
		 
		Bundle bundle = getIntent().getExtras();
		inflater = this.getLayoutInflater();
		container = (ViewGroup) this.findViewById(R.id.container);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
		
		//////////////////////////////////////////////////////
		//Принимаем данные о пользователе в виде json string//
		//////////////////////////////////////////////////////
		
		userinfo = bundle.getString("user");
		try {
			reader = new JSONObject(userinfo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		this.position = position+1;
		onSectionAttached(position+1);
	}

	public void onSectionAttached(int number) {
		switch (number) {
		default:
		case 1:
			mTitle = getString(R.string.title_main);
			try{
				if(fragmentMain == null)
					fragmentMain = new FragmentMain();	
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
			fragmentMoney = new FragmentMoney();
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

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	

	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setTitle("Реально выйти?")
	        .setMessage("И что, это Вы внатуре надумали выйти???")
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new OnClickListener() {

	            public void onClick(DialogInterface arg0, int arg1) {
	                MainActivity.super.onBackPressed();
	            }
	        }).create().show();
	}

}
