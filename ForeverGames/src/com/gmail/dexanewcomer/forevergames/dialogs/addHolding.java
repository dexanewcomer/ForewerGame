package com.gmail.dexanewcomer.forevergames.dialogs;

import com.gmail.dexanewcomer.forevergames.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

public class addHolding extends Dialog implements android.view.View.OnClickListener {

	public addHolding(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	protected void onStart() {
        super.onStart();

		
        setContentView(R.layout.add_holding);
    
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
