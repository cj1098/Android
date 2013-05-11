package theTUTORIALS;

import java.util.Timer;
import java.util.TimerTask;

import com.animations.UpDownArrowAnimation;
import com.example.mt_study.Main_screen;
import com.example.mt_study.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;






public class MainScreenTutorial extends Activity {
	private ImageView addCurrentClassesArrow;
	private ImageView addClassesArrow;
	private TextView firstArrowText;
	private TextView secondArrowText;
	private TextView tutorialHeader;
	private Animation bouncingArrow;
	private Animation bouncingArrow2;
	private Animation leadingText;
	private Button next;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.begining_tutorial);
		initControls();
	}
	
	
	private void initControls() {
		firstArrowText = (TextView)findViewById(R.id.first_instruction_text);
		secondArrowText = (TextView)findViewById(R.id.second_instruction_text);
		tutorialHeader = (TextView)findViewById(R.id.tutorial_text_heading);
		addCurrentClassesArrow = (ImageView)findViewById(R.id.add_current_class_arrow);
		addClassesArrow = (ImageView)findViewById(R.id.add_class_arrow);
		next = (Button)findViewById(R.id.beginning_tutorial_next);
		
		addClassesArrow.setVisibility(View.INVISIBLE);
		/*bouncingArrow = new UpDownArrowAnimation(addCurrentClassesArrow, addClassesArrow, firstArrowText,
				secondArrowText, this);*/
		bouncingArrow = AnimationUtils.loadAnimation(this, R.anim.bounce);	
		addCurrentClassesArrow.startAnimation(bouncingArrow);
		bouncingArrow.setAnimationListener(new AnimationListener()  {

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				Animation fadeOut = AnimationUtils.loadAnimation(MainScreenTutorial.this, android.R.anim.fade_out);
				addCurrentClassesArrow.startAnimation(fadeOut);
				addCurrentClassesArrow.setVisibility(View.INVISIBLE);
				firstArrowText.startAnimation(fadeOut);
				firstArrowText.setVisibility(View.INVISIBLE);
				secondArrowText.setText("This makes your own classes!");
				addClassesArrow.setVisibility(View.VISIBLE);
				bouncingArrow2 = AnimationUtils.loadAnimation(MainScreenTutorial.this, R.anim.bounce);
				bouncingArrow2.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationEnd(Animation animation) {
						Animation fadeOut = AnimationUtils.loadAnimation(MainScreenTutorial.this, android.R.anim.fade_out);
						secondArrowText.startAnimation(fadeOut);
						addClassesArrow.setVisibility(View.INVISIBLE);
						secondArrowText.setText("");
						Intent longPressTutorials = new Intent(MainScreenTutorial.this, theTUTORIALS.LongPressTutorial.class);
						overridePendingTransition(R.anim.bounce,R.anim.bounce);
						longPressTutorials.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(longPressTutorials);
						finish();
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						//dont need this
					}

					@Override
					public void onAnimationStart(Animation animation) {
						//dont need this either
					}
				});
				addClassesArrow.startAnimation(bouncingArrow2);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				//dont need this
			}

			@Override
			public void onAnimationStart(Animation animation) {
				//dont need this either
			}
		});
			
		leadingText = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		tutorialHeader.startAnimation(leadingText);
		
		
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent goForward = new Intent(MainScreenTutorial.this, LongPressTutorial.class);
				startActivity(goForward);
				finish();
			}
		});
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent goBack = new Intent(MainScreenTutorial.this, Main_screen.class);
		goBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		SharedPreferences sp = getSharedPreferences("tutorialCheck", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("hasRun", 1);
		editor.commit();
		startActivity(goBack);
	}
	
}