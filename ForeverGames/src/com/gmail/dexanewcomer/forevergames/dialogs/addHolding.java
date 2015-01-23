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
import com.gmail.dexanewcomer.http.aHttpClient.OnJsonListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class addHolding extends Dialog implements android.view.View.OnClickListener  {
	private static JSONObject reader;
	private String lot;
	static double balance, price = 1;
	static int max;
	EditText summ;
	private static SeekBar seekbar;
	private Activity mActivity;
	private aHttpClient client;
	private static TextView priceTV, total;
	public addHolding(Activity context, String lot) {
		super(context);
		this.mActivity = context;
		this.lot = lot;
	}

	protected void onStart() {
        super.onStart();
        setContentView(R.layout.add_holding);
        client = new aHttpClient(mActivity);
         seekbar = (SeekBar)findViewById(R.id.seekBar);
        summ =(EditText) this.findViewById(R.id.summ);
        summ.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try{
				seekbar.setProgress(Integer.parseInt(summ.getText().toString()));
				if(Integer.parseInt(summ.getText().toString()) > max)
					summ.setText(String.valueOf(max));
				}
				catch(NumberFormatException nan){}
				
			}});
        total = (TextView) this.findViewById(R.id.total);
       
        seekbar.setMax(25);
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				summ.setText(String.valueOf(seekBar.getProgress()));
				total.setText((price*seekBar.getProgress()) + "$");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
				
			}});
        priceTV  =(TextView) this.findViewById(R.id.price);
        client.setOnJsonListener(new OnJsonListener(){
			@Override
			public void onJson() {
				try {
					System.out.println("price :" + client.json);
					reader = new JSONObject(client.json);
					String curPrice = reader.getString("price");
					System.out.println("price :" + curPrice);
					priceTV.setText(mActivity.getString(R.string.current_cours) + " "  + curPrice + "руб.");
					System.out.println(mActivity.getString(R.string.current_cours)  + " "  + curPrice + "руб.");
					JSONObject user = new JSONObject(reader.getString("user"));
					balance = user.getInt("balance");
					price = Double.parseDouble(curPrice);
					max =  (int) Math.round(balance / price);
					seekbar.setMax((int) Math.round(max));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}});
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("login", Global.LOGIN));
		nameValuePairs.add(new BasicNameValuePair("pass", Global.PASS));
		nameValuePairs.add(new BasicNameValuePair("act", "price"));
		nameValuePairs.add(new BasicNameValuePair("lot", lot));
		client.post(Global.SERVER + "/api.php", nameValuePairs);
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


}
