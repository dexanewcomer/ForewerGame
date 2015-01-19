package com.gmail.dexanewcomer.forevergames.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.dexanewcomer.forevergames.Global;
import com.gmail.dexanewcomer.forevergames.R;
import com.gmail.dexanewcomer.http.aHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class addHolding extends Dialog implements android.view.View.OnClickListener {
	private static JSONObject reader;
	private String pass, login, lot; 
	private Activity mActivity;
	private static TextView price;
	public addHolding(Activity context, String lot) {
		super(context);
		this.mActivity = context;
		this.lot = lot;
	}
	protected void onStart() {
        super.onStart();
        

       
        setContentView(R.layout.add_holding);
        price  =(TextView) this.findViewById(R.id.price);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("login", Global.LOGIN));
		nameValuePairs.add(new BasicNameValuePair("pass", Global.PASS));
		nameValuePairs.add(new BasicNameValuePair("act", "price"));
		nameValuePairs.add(new BasicNameValuePair("lot", lot));
		aHttpClient client = new aHttpClient(mActivity);
		client.post(Global.SERVER + "/api.php", nameValuePairs,client.PRICE);

    
        getWindow().setFlags(4, 4);
        getWindow().setBackgroundDrawableResource(R.drawable.frame);
       // getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setTitleColor(Color.BLACK);
        setTitle("Укажите сумму вклада");
        Button next = (Button) this.findViewById(R.id.nextBT);
        next.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		this.dismiss();
		
	}
	public static void setPrice(String json, Activity mActivity2) {
		// TODO Auto-generated method stub
		try {
			reader = new JSONObject(json);
			String curPrice = reader.getString("price");
			price.setText(mActivity2.getString(R.string.current_cours) + " "  + curPrice + "руб.");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


}
