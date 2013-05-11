package com.example.mt_study;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FlashCardSettings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_card_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_card_settings, menu);
		return true;
	}

}
