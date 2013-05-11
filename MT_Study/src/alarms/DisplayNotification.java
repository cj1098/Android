package alarms;

import java.io.IOException;

import com.example.mt_study.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

//holy S***********************F********* batman this class is complicated/dumb
public class DisplayNotification extends Activity {
	//created a static mediaplayer to deal with my starting/stopping 
	//the mediaPlayer from different activies with alarms.
	public static MediaPlayer mMediaPlayer;
	
	@Override
	public void onCreate(Bundle SavedInstanceState) {
		super.onCreate(SavedInstanceState);
		
		//getting information from the user
		int notifID = getIntent().getExtras().getInt("NotificationId");
		String name = getIntent().getExtras().getString("assignmentName");
		boolean vibrate = getIntent().getExtras().getBoolean("Vibrate");
		int vibrateType = getIntent().getExtras().getInt("vibrateType");
		boolean musix = getIntent().getExtras().getBoolean("Musix");
		String musixType = getIntent().getExtras().getString("MusixType");
		
		Intent i = new Intent(this, assignments.Assignments.class);
		i.putExtra("NotificationId", notifID);
		
		
		PendingIntent displayAlarm = PendingIntent.getActivity(this, 0, i, 0);

		// create the notification for the status bar
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notif = new Notification(R.drawable.book, "Time's up!",
				System.currentTimeMillis());

		CharSequence from = name;
		CharSequence message = "This assignment is due soon!";
		notif.setLatestEventInfo(this, from, message, displayAlarm);
		// for the user if they click the notification in the notification bar
		// it erases the notification
		notif.flags = Notification.FLAG_AUTO_CANCEL;
		
		//IF the user checked for a ringtone, DO STUFF.
		if (musix) {

			//parse the ringtone from the previous class
			Uri ringtone;
			ringtone = Uri.parse(musixType);
			mMediaPlayer = MediaPlayer.create(DisplayNotification.this,
					ringtone);
			mMediaPlayer.setLooping(false);
			mMediaPlayer.start();
			mMediaPlayer.setVolume(100, 100);

			//set an alarm to stop the ringtone after a few seconds.
			AlarmManager aM = (AlarmManager) getSystemService(ALARM_SERVICE);
			
			Intent trigger = new Intent(DisplayNotification.this, StopRingtone.class);
			trigger.putExtra("songId", notifID);
			
			PendingIntent stopSong = PendingIntent.getActivity(this, 0, trigger, 0);
			
			aM.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2000, stopSong);


		}

	        
	        
	        //---100ms delay, vibrate for 250ms, pause for 100 ms and
	        // then vibrate for 500ms---
	        if (vibrate) {
	        	switch(vibrateType) {
	        	case 0:
		        	notif.vibrate = new long[] { 100, 250, 100, 500};
		        	break;
	        	case 1:
	        		notif.vibrate = new long[] { 100, 400, 100, 500};
	        		break;
	        	case 2:
	        		notif.vibrate = new long[] {100, 200, 100, 200, 150, 400};
	        		break;
	        	case 3:
	        		notif.vibrate = new long[] {50, 100, 50, 150, 50, 200};
	        		break;
	        	case 4:
	        		notif.vibrate = new long[] {100, 700};
	        		break;
	        	case 5:
	        		notif.vibrate = new long[] {5000, 10000};
	        		break;
	        	default:
	        		notif.vibrate = new long[] {500, 10000};	        	}
	        }
        	nm.notify(notifID, notif);
	        //---destroy the activity---
	        finish();
	}

	//function that gets called after a few seconds from the AlarmManager
	public void update() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}