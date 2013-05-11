package alarms;

import com.example.mt_study.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;


//SUPER INEFFICIENT CLASS XD YEAH!
public class StopRingtone extends DisplayNotification  {
	@Override
	public void onCreate(Bundle SavedInstanceState) {
		super.onCreate(SavedInstanceState);
		
		Intent extras = getIntent();
		
		//basically a work around to bypass the activity/context issue.
		if (extras != null) {
			update();
		}		
	}
}