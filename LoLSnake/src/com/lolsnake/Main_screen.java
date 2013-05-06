package com.lolsnake;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Main_screen extends Activity {
	
	private Button play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		initControls();
		
	}

	public void initControls() {
		play = (Button)findViewById(R.id.home_screen_play_button);
		//play.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_forever));
		
		
		play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent startGame = new Intent(Main_screen.this, Game_screen.class);
				//possibly... havent' decided whether user clicking back is acceptable
				//startGame.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(startGame);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
