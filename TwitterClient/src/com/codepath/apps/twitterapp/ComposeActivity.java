package com.codepath.apps.twitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		SharedPreferences pref = getSharedPreferences("mytwitter", MODE_PRIVATE);
		String screen_name="@" + pref.getString("screen_name", "N/A");
		String profile_image_url=pref.getString("profile_image_url", "N/A");
		
		TextView tvUser=(TextView) findViewById(R.id.tvUser);
		tvUser.setText(screen_name);
		
        ImageView ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        ImageLoader.getInstance().displayImage(profile_image_url, ivPhoto);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void sendBack(View v){
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void submitTweet(View v){
		EditText etTweet=(EditText) findViewById(R.id.etTweet);
		String tweetText=etTweet.getText().toString();
		
		if(tweetText.length() == 0){
			Toast.makeText(this, "Please add some words in textbox", Toast.LENGTH_SHORT).show();			
		}else{
			
			MyTwitterApp.getRestClient().submitTweet(tweetText, new JsonHttpResponseHandler(){

				@Override
				public void onSuccess(JSONObject jsonResponse) {
					
					Log.d("DEBUG",jsonResponse.toString());
					
					Tweet mytweet=Tweet.fromJson(jsonResponse);
					if(mytweet.getBody().length() > 0){
						Toast.makeText(getApplicationContext(), "Your tweet submitted!", Toast.LENGTH_SHORT).show();
					}
					
					//finish this activity
					setResult(RESULT_OK);
					finish();
					
				}

				@Override
				public void onFailure(Throwable arg0, JSONObject arg1) {
					Log.d("DEBUG","Failed with "+arg1.toString());
				}
				
			});
			
		}
	}

}
