package com.codepath.apps.twitterapp;

import org.json.JSONObject;
import org.scribe.model.Token;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.twitterapp.models.User;
import com.codepath.oauth.OAuthLoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
    @Override
    public void onLoginSuccess() {
    	// Intent i = new Intent(this, PhotosActivity.class);
    	// startActivity(i);
    	Token tk=getClient().checkAccessToken();
    	String txt[]=tk.getToken().split("-");
    	
    	getClient().getUserInfo(txt[0], new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonUser) {
						
				User myuser=User.fromJson(jsonUser);
				Log.d("DEBUG", myuser.getScreenName());
				SharedPreferences pref=getSharedPreferences("mytwitter", MODE_PRIVATE);
				Editor edit= pref.edit();
				edit.putString("screen_name", myuser.getScreenName());
				edit.putLong("user_id", myuser.getId());
				edit.putString("profile_image_url", myuser.getProfileImageUrl());
				edit.commit();
			}
			
		});
    	
    	Intent i =new Intent(this,TimelineActivity.class);
    	startActivity(i);
    }
    
    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }
    
    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void loginToRest(View view) {
        getClient().connect();
    }

}
