package com.example.tip_calculator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//following line relates xml to java
		setContentView(R.layout.activity_main);  // this is glue which tied xml layout to java activity
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//this method called when someone click button
	public void calculateTip(View view) {
		
		EditText totalText=(EditText)findViewById(R.id.totalText);
		TextView resultView=(TextView)findViewById(R.id.resultText);
		
		//get the data from input box in string format
		String textdata=totalText.getText().toString();
		
		//make sure string is non-empty
		if(textdata.isEmpty()){
			Context context = getApplicationContext();		
			CharSequence text = "Please enter total amount.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();		//Display message to user asking for input
			resultView.setText("");
			return;
		}
		
		//this is common method, so need to find out which button was clicked.
		//depending on which button was clicked, we decide factor to multiply with amount.
		double factor=0;
		switch(view.getId()){
		case R.id.btn10:
			factor=0.1;
			break;
		case R.id.btn15:
			factor=0.15;
			break;
		case R.id.btn20:
			factor=0.2;
			break;
		default:
			throw new RuntimeException("Unknow button ID");
		}
		
		//now convert that string to double.
		double total=Double.parseDouble(textdata);
		double tip = factor * total;
		
		//format it to 2 decimal places and put $ in beginning
		DecimalFormat fmt = new DecimalFormat("0.00");
		String result="Tip is:  $" + fmt.format(tip);
		
		//put result in textview
		resultView.setText(result);
	}

}
