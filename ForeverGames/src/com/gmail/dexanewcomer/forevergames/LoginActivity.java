package com.gmail.dexanewcomer.forevergames;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.dexanewcomer.http.aHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	LinearLayout reggroup;
	RadioGroup sex;
	RadioButton male,female;
	Button submit;
	TextView regorlogin;
	CheckBox auto;
	boolean registration = false;
	static EditText loginET, passET, cpassET, mailET, phoneET, fullnameET;
	Activity mActivity = this;
	String login, pass;
	boolean autologin;
	private final String server = "http://192.168.0.102/api.php";

	private SharedPreferences mSettings;
	private static final String 		APP_PREFERENCES_LOGIN = 		"login";
    private static final String 		APP_PREFERENCES_PASSWORD = 		"password";
    private static final String 		APP_PREFERENCES_AUTOLOGIN = 	"autologin";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mSettings=PreferenceManager.getDefaultSharedPreferences(this);
		login = mSettings.getString(APP_PREFERENCES_LOGIN, null);
		pass = mSettings.getString(APP_PREFERENCES_PASSWORD, null);
		autologin = mSettings.getBoolean(APP_PREFERENCES_AUTOLOGIN, false);
		if(autologin){
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("act", "login"));
			nameValuePairs.add(new BasicNameValuePair("login", login));
			nameValuePairs.add(new BasicNameValuePair("pass", pass));
			aHttpClient client = new aHttpClient(mActivity);
			client.post(server, nameValuePairs);
			
		}
		regorlogin = (TextView) this.findViewById(R.id.regorlogin);
		reggroup = (LinearLayout) this.findViewById(R.id.reggroup);
		loginET = (EditText) this.findViewById(R.id.login);
		passET = (EditText) this.findViewById(R.id.pass);
		cpassET = (EditText) this.findViewById(R.id.cpass);
		mailET = (EditText) this.findViewById(R.id.mail);
		phoneET = (EditText) this.findViewById(R.id.phone);
		fullnameET = (EditText) this.findViewById(R.id.fullname);
		submit = (Button) this.findViewById(R.id.submit);
		final RadioButton male = (RadioButton) this.findViewById(R.id.male);	
		auto = (CheckBox) this.findViewById(R.id.autologin);
		auto.setChecked(autologin);
		auto.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				autologin = (auto.isChecked()) ? true : false;
				
			}});
		
		loginET.setText(login);
		passET.setText(pass);
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				login = loginET.getText().toString();
				pass = passET.getText().toString();
				Editor editor = mSettings.edit();
		    	editor.putString(APP_PREFERENCES_LOGIN, login);
		    	editor.putString(APP_PREFERENCES_PASSWORD, pass);
		    	editor.putBoolean(APP_PREFERENCES_AUTOLOGIN, autologin);
		    	editor.apply();
				int pairs = (registration) ? 8 : 3;
				String action = (registration) ? "registration" : "login";

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(pairs);
				nameValuePairs.add(new BasicNameValuePair("act", action));
				nameValuePairs.add(new BasicNameValuePair("login", login));
				nameValuePairs.add(new BasicNameValuePair("pass", pass));
				if(registration){
				nameValuePairs.add(new BasicNameValuePair("fullname", fullnameET.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("sex", (male.isChecked()) ? "0" : "1"));
				nameValuePairs.add(new BasicNameValuePair("cpass", cpassET.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("mail", mailET.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("phone", phoneET.getText().toString()));
				
				}
			
				aHttpClient client = new aHttpClient(mActivity);
				client.post(server, nameValuePairs);
				
				
				
			}});
		regorlogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				registration = (registration) ? false : true;
				reggroup.setVisibility((registration) ? View.VISIBLE : View.GONE);
				regorlogin.setText((registration) ? R.string.enter : R.string.registration);
			}});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static void login(String json, Activity mActivity){
		System.out.println("answer: " + json);
		try {
			JSONObject reader = new JSONObject(json);
			boolean error = reader.getBoolean("error");
			if(error){
				String text = reader.getString("content");
				String type = reader.getString("type");
				setError(mActivity,type,text);
				
				
			}
			else{
				if(reader.getBoolean("isUser")){

    Bundle bundle = new Bundle();
    bundle.putString("user", reader.getString("user"));
	Intent enterIntent = new Intent(mActivity, MainActivity.class);
	enterIntent.putExtras(bundle);
	enterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	mActivity.startActivity(enterIntent);
	mActivity.finish();


				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void setError(Context context,String type, String text) {
		// TODO Auto-generated method stub
		if(type.equalsIgnoreCase("popup")){
			Toast toast = Toast.makeText(context,text, Toast.LENGTH_SHORT); 
					toast.show(); 
		}
		if(type.equalsIgnoreCase("login")){
			loginET.setError(text);
		}
		else if(type.equalsIgnoreCase("pass")){
			passET.setError(text);
		}
		else if(type.equalsIgnoreCase("cpass")){
			cpassET.setError(text);
		}
		else if(type.equalsIgnoreCase("mail")){
			mailET.setError(text);
		}
		
	}
}
