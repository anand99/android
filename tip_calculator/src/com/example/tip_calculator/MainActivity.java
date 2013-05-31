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
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void calculateTip(View view) {
		
		EditText totalText=(EditText)findViewById(R.id.totalText);
		TextView resultView=(TextView)findViewById(R.id.resultText);
		
		String textdata=totalText.getText().toString();
		
		if(textdata.isEmpty()){
			Context context = getApplicationContext();		
			CharSequence text = "Please enter total amount.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			resultView.setText("");
			return;
		}
		
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
		
		double total=Double.parseDouble(textdata);
		double tip = factor * total;
		
		DecimalFormat fmt = new DecimalFormat("0.00");
		String result="Tip is:  $" + fmt.format(tip);
		
		resultView.setText(result);
	}

}
