package com.lolsnake;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Game_screen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);
		
		
		GameView game = new GameView(this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_screen, menu);
		return true;
	}

}
