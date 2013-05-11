package theTUTORIALS;

import com.animations.LeftRightArrowAnimaton;
import com.example.mt_study.Main_screen;
import com.example.mt_study.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;





public class LongPressTutorial extends Activity {
	
	private ImageView screenShot;
	private ImageView screenShotArrow;
	private Animation screenShotArrowAnimation;
	private TextView screenShotArrowText;
	private Button finishTutorial;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.long_press_tutorial);
		initControls();
	}
	
	
	
	public void initControls() {
		screenShot = (ImageView)findViewById(R.id.long_press_screen_shot);
		//screenShotArrow = (ImageView)findViewById(R.id.long_press_arrow);
		screenShotArrowText = (TextView)findViewById(R.id.long_press_text);
		finishTutorial = (Button)findViewById(R.id.finish_tutorial_next);
		//screenShotArrowAnimation = new LeftRightArrowAnimaton(screenShotArrow, this);
		//screenShotArrow.startAnimation(screenShotArrowAnimation);
		
		finishTutorial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goBack = new Intent(LongPressTutorial.this, Main_screen.class);
				goBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				SharedPreferences sp = getSharedPreferences("tutorialCheck", MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putInt("hasRun", 1);
				editor.commit();
				overridePendingTransition(0,0);
				startActivity(goBack);
			}
		});
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent goBack = new Intent(LongPressTutorial.this, Main_screen.class);
		goBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		SharedPreferences sp = getSharedPreferences("tutorialCheck", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("hasRun", 1);
		editor.commit();
		startActivity(goBack);
	}
	
}