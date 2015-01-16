package com.gmail.dexanewcomer.forevergames.dialogs;

import com.gmail.dexanewcomer.forevergames.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageBox extends Dialog implements android.view.View.OnClickListener {
	private String title, message;

	public MessageBox(Context context,String message,String title) {
		super(context);
		// TODO Auto-generated constructor stub
		this.message = message;
		this.title = title;
	}
	protected void onStart() {
        super.onStart();

		
        setContentView(R.layout.message_box);
        TextView msg = (TextView) findViewById(R.id.msg);
        msg.setText(message);
    
        getWindow().setFlags(4, 4);
        getWindow().setBackgroundDrawableResource(R.drawable.frame);
       // getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setTitleColor(Color.BLACK);
        setTitle(title);
        Button next = (Button) this.findViewById(R.id.ok);
        next.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		this.dismiss();
		
	}


}
