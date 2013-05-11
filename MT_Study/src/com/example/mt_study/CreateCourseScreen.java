package com.example.mt_study;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.example.statics.Statics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class CreateCourseScreen extends Activity {


	private CheckBox assignments;
	private CheckBox flashCards;
	private EditText title;
	private EditText days;
	private EditText time;
	private Button save;
	
	private ArrayList<String> classList;
	
	private String FILENAME = "new_course_created";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_course_screen);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

     
        Bundle extras;
        extras = getIntent().getExtras();
        if (extras != null) {
        	classList = extras.getStringArrayList("NewClass");
        }
        else {
        	classList = new ArrayList<String>();
        }
        
        initControls();
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_course_screen, menu);
        return true;
    }
    
    public void initControls() {
           title = (EditText)findViewById(R.id.course_title);
           save = (Button)findViewById(R.id.accept_new_course);
           
           /*try {
			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE | Context.MODE_APPEND);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			
			
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           catch (IOException e) {
        	   e.printStackTrace();
           }*/
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				classList.add(title.getEditableText().toString());
				Statics.addClassesIntoFile(CreateCourseScreen.this, classList);
				Intent returnToMain = new Intent(CreateCourseScreen.this, Main_screen.class);
				returnToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(returnToMain);
			}
		});
		
		
           
    }
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    }
    
}
