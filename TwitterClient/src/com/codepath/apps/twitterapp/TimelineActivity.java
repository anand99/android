package com.codepath.apps.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				Integer len=jsonTweets.length();
				String text="Total items are: " + len;
				Log.d("DEBUG", text);
				
				SharedPreferences pref = getSharedPreferences("mytwitter", MODE_PRIVATE);
				String screen_name=pref.getString("screen_name", "N/A");
				setTitle("@"+screen_name);
				Log.d("DEBUG", "Screen name is= "+screen_name);
				
				ArrayList<Tweet> tweets= Tweet.fromJson(jsonTweets);
				ListView lvTweets=(ListView)findViewById(R.id.lvTweets);
				TweetsAdapter adapter=new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onComposeClick(MenuItem item){
		//Toast.makeText(this, "Composing", Toast.LENGTH_SHORT).show();
		Intent i =new Intent(this,ComposeActivity.class);
    	startActivityForResult(i,10);
    	
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode==10){
			if(resultCode == RESULT_OK){
				
				MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){

					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("DEBUG", "Refreshing...");
										
						ArrayList<Tweet> tweets= Tweet.fromJson(jsonTweets);
						ListView lvTweets=(ListView)findViewById(R.id.lvTweets);
						TweetsAdapter adapter=new TweetsAdapter(getBaseContext(), tweets);
						lvTweets.setAdapter(adapter);
					}
					
				});
				
			}else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Compose cancelled...", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public void onRefreshClick(MenuItem item){
		//Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
		ListView lvt=(ListView)findViewById(R.id.lvTweets);
		lvt.setAdapter(null);
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				Log.d("DEBUG", "Refreshing...");
								
				ArrayList<Tweet> tweets= Tweet.fromJson(jsonTweets);
				ListView lvTweets=(ListView)findViewById(R.id.lvTweets);
				TweetsAdapter adapter=new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}
			
		});
	}

}
