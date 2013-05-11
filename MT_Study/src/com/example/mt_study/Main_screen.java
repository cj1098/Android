package com.example.mt_study;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import theTUTORIALS.MainScreenTutorial;

import com.example.statics.Statics;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Main_screen extends Activity {

	private ListView Main_screen_listView;
	private ArrayAdapter<String> listAdapter;
	private ArrayList<String> classList;
	private Boolean truth = false;
	private ImageButton Assignments;
	private ImageButton FlashCards;
	private ImageButton ImportClasses;
	private ImageButton addClass;
	private int alreadyRun;
	
	private static float startX;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);
        
      //check to see if tutorials has already been run
      SharedPreferences sp = getSharedPreferences("tutorialCheck", MODE_PRIVATE);
      alreadyRun = sp.getInt("hasRun", 0);
      
      if (alreadyRun == 0) {
    	  //do tutorials
    	  Intent startTutorials = new Intent(this, theTUTORIALS.MainScreenTutorial.class);
    	  startTutorials.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	  startActivity(startTutorials);
      }
      else {
    	  
        //assign classList to listOfCourses and figure out how to deal with reading in data and
        //displaying it to the front page.
        if (isDataInFile()) {
        	Statics.readClassesFromFile(this);
        }
        else {
        	Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show();
        }
        
        initControls();
      }
    }
    




	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putStringArrayList("classListt", classList);
	}





	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		classList = savedInstanceState.getStringArrayList("classListt");
	}

	private void initControls() {
        ImportClasses = (ImageButton)findViewById(R.id.mainScreen_importClasses);
        addClass = (ImageButton)findViewById(R.id.mainScreen_addClasses);
        Main_screen_listView = (ListView)findViewById(R.id.classList);
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Statics.listOfCourses);
        Main_screen_listView.setAdapter(listAdapter);
        
        ImportClasses.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				Intent importCourse = new Intent(Main_screen.this, ImportScheduleLogin.class);
				startActivity(importCourse);
			}
        });
        addClass.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//add a class to your current courses or create one without syncing with pipeline
				final AlertDialog.Builder myDialog = new AlertDialog.Builder(Main_screen.this);
				final EditText title = new EditText(Main_screen.this);
				myDialog.setTitle("Course Title")
						.setView(title)
						.setPositiveButton("Save", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								classList = new ArrayList<String>();
								classList.add(title.getEditableText().toString());
								Statics.addClassesIntoFile(Main_screen.this, classList);
								Statics.readClassesFromFile(Main_screen.this);
								listAdapter.notifyDataSetChanged();
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						
				myDialog.show();
			}
        });
        
        
        Main_screen_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, final int position,
					long arg3) {
					//go to assignments or flashcards.
					//onTouchHandler myOnTouch = new onTouchHandler(Main_screen.this, classList);
					//MotionEvent ev = null;
					//classList = myOnTouch.motionHandler(ev, position, view);
					//listAdapter.notifyDataSetChanged();
					AlertDialog.Builder alertBox = new AlertDialog.Builder(Main_screen.this);
					alertBox.setTitle("Options")
					.setPositiveButton("Assignments", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent currentAssignments = new Intent(Main_screen.this, assignments.Assignments.class);
							//currentAssignments.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							currentAssignments.putExtra("position", position);
							startActivity(currentAssignments);
						}
					})
					.setNegativeButton("FlashCards", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent currentFlashCards = new Intent(Main_screen.this, flashCards.FlashCard.class);
							//currentFlashCards.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							currentFlashCards.putExtra("position", position);
							startActivity(currentFlashCards);
						}
					})
					.show();
			}
        });
        
        Main_screen_listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					final int position, long arg3) {
				final Animation animation = AnimationUtils.loadAnimation(
						Main_screen.this, android.R.anim.slide_out_right);
					view.startAnimation(animation);
					Handler handle = new Handler();
					handle.postDelayed(new Runnable() {
						@Override
						public void run() {
							
							Statics.deleteClassesInFile(Main_screen.this, Statics.listOfCourses.get(position), position);
							Statics.deleteDataFromFile(Main_screen.this, position);
							Statics.deleteAssignmentDataFromFile(Main_screen.this, position);
							Statics.allAssignments.clear();
							Statics.listOfDecks.clear();
							listAdapter.notifyDataSetChanged();
							animation.setDuration(500);
						}
					}, 100);
				return true;
			}
        });
    }
    
    //check for data in the file
    private boolean isDataInFile() {
		FileInputStream myIn;
		String line;
		String total = "";
		try {
			myIn = openFileInput(Statics.CLASSLIST_FILENAME);
			
			InputStreamReader inputReader = new InputStreamReader(myIn);
			BufferedReader BR = new BufferedReader(inputReader);
				while ((line = BR.readLine()) != null) {
					total += line;
				}
				if (!total.equals("")) {
					return true;
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return false;
    	
    }
    
    
    @Override
    public void onPause() {
    	super.onPause();
    	//listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
    	super.onResume();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
