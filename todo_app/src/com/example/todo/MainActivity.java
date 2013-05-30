package com.example.todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
//import android.content.Context;
//import android.graphics.Color;
//import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ArrayList<String> items;
	private ListView lvItems;
	private ArrayAdapter<String> itemsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        items=new ArrayList<String>();
//        items.add("item1");
//        items.add("item2");
		Context context = getApplicationContext();		
		CharSequence text = "Welcome to amodh todo list!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
        
        readItems();
        //R is to access id from java side
        //in xml we use @ for access id
        
        //create adapter
        //adapter responsible for underlying data and how to display it
        itemsAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        
        lvItems=(ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        
        lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
        	@Override
        	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId){
        		items.remove(position);
        		itemsAdapter.notifyDataSetChanged();
        		
        		writeItems();
        		return true;
        	}
        });
        
//        lvItems.setOnItemClickListener( new OnItemClickListener(){
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position,
//					long rowId) {
//				
//				Context context = getApplicationContext();
//				int col= lvItems.getChildAt(position).getSolidColor();
//				CharSequence text = "Color:"+col;
//				int duration = Toast.LENGTH_SHORT;
//
//				Toast toast = Toast.makeText(context, text, duration);
//				toast.show();
//				//lvItems.setBackgroundColor(Color.GREEN);
//				if(lvItems.getChildAt(position).getSolidColor() != Color.GREEN){
//					lvItems.getChildAt(position).setBackgroundColor(Color.GREEN);
//				}else{
//					lvItems.getChildAt(position).setBackgroundColor(0);
//				}
//			}
//
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onNewItem(View view){
    	EditText etNewItem=(EditText)findViewById(R.id.etAddNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	
    	writeItems();
    }
    
    private void readItems(){
    	File filesDir=getFilesDir();
    	File todoFile=new File(filesDir,"todo.txt");
    	try{
    		items=new ArrayList<String>(FileUtils.readLines(todoFile));
    	}catch(IOException e){
    		items=new ArrayList<String>();
    		e.printStackTrace();
    	}
    	
    }
    
    private void writeItems(){
    	File filesDir=getFilesDir();
    	File todoFile=new File(filesDir,"todo.txt");
    	
    	try{
    		FileUtils.writeLines(todoFile, items);
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
}
