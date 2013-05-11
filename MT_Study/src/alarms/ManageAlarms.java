package alarms;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import com.example.mt_study.R;
import com.example.statics.Statics;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class ManageAlarms extends Activity {

	private TimePicker timePicker;
	private DatePicker datePicker;
	private Calendar myCal;
	private Button setAlarm;
	private Spinner vibrateSettings;
	private ImageButton fileSearch;
	private CheckBox vibrateOnOff;
	private CheckBox ringtoneOnOff;
	private String[] vibrateNames = { "vibrate 1", "vibrate 2", "vibrate 3",
			"vibrate 4", "vibrate 5", "vibrate 6" };
	private int vibrateType;
	private String assignmentName;
	private String ringtone = null;
	public static int counter = 0;

	@Override
	public void onCreate(Bundle SavedInstanceState) {
		super.onCreate(SavedInstanceState);
		Intent receive = getIntent();
		assignmentName = receive.getStringExtra("AssignmentName");
		setContentView(R.layout.alarm_set);
		initControls();
	}

	public void initControls() {
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		setAlarm = (Button) findViewById(R.id.setAlarm);
		vibrateSettings = (Spinner) findViewById(R.id.vibrate_settings);
		fileSearch = (ImageButton) findViewById(R.id.ringtone_selector);
		vibrateOnOff = (CheckBox) findViewById(R.id.vibrate_on_off);
		ringtoneOnOff = (CheckBox) findViewById(R.id.ringtone_on_off);
		myCal = Calendar.getInstance();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, vibrateNames);
		vibrateSettings.setAdapter(adapter);
		
		
		//user selects and item vs never selecting one.
		vibrateSettings.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				vibrateType = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				vibrateType = 1;
			}
		});
		
		fileSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				RingtoneManager RTM = new RingtoneManager(ManageAlarms.this);
				Intent ringtonePicka = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				ringtonePicka.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.TYPE_ALL);
				
				if (ringtone != null) {
					ringtonePicka.putExtra(
							RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
							Uri.parse(ringtone));
				} else {
					ringtonePicka.putExtra(
							RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
							(Uri) null);
				}
				startActivityForResult(ringtonePicka, 0);
			}
		});

		// will set the alarm for the first time if the user doesn't restart
		// their
		// phone this alarm will go off.
		setAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

				myCal.set(Calendar.YEAR, datePicker.getYear());
				myCal.set(Calendar.MONTH, datePicker.getMonth());
				myCal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
				myCal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
				myCal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
				myCal.set(Calendar.SECOND, 0);

				// precaution if the user restarts their phone. Saves the alarm
				// to a file
				// and when user restarts their phone register a broadcast
				// receiver to pick up
				// the broadcast and then re-create the alarms that were stored.
				setAlarms(datePicker.getYear(), datePicker.getMonth(),
						datePicker.getDayOfMonth(),
						timePicker.getCurrentHour(),
						timePicker.getCurrentMinute(), assignmentName);

				Intent triggered = new Intent(ManageAlarms.this,
						alarms.DisplayNotification.class);
				triggered.putExtra("NotificationId", counter);
				triggered.putExtra("assignmentName", assignmentName);

				if (vibrateOnOff.isChecked()) {
					triggered.putExtra("Vibrate", true);
					triggered.putExtra("vibrateType", vibrateType);
				}

				if (ringtoneOnOff.isChecked()) {
					if (ringtone == null) {
						Toast.makeText(ManageAlarms.this, "You must select a ringtone first", Toast.LENGTH_SHORT).show();
					}
					else {
						triggered.putExtra("Musix", true);
						triggered.putExtra("MusixType", ringtone);
					}
				}
				PendingIntent displayIntent = PendingIntent.getActivity(
						ManageAlarms.this, counter, triggered, 0);

				/*
				 * boolean alarmActive =
				 * (PendingIntent.getActivity(getBaseContext(), 0, triggered,
				 * PendingIntent.FLAG_NO_CREATE) != null);
				 * 
				 * if (alarmActive) { alarmManager.cancel(displayIntent); }
				 */
				alarmManager.cancel(displayIntent);

				alarmManager.set(AlarmManager.RTC_WAKEUP,
						myCal.getTimeInMillis(), displayIntent);
				counter++;

				Toast.makeText(ManageAlarms.this, "Alarm set",
						Toast.LENGTH_SHORT).show();

			}
		});
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Uri ringtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
			if (ringtone != null) {
				this.ringtone = ringtone.toString();
			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent goBack = new Intent(ManageAlarms.this,
				assignments.Assignments.class);
		goBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(goBack);
		overridePendingTransition(0, 0);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return timePicker;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		timePicker = (TimePicker) getLastNonConfigurationInstance();
	}

	// function to save the alarm into a file for resetting it later.
	public void setAlarms(int year, int month, int DoM, Integer hour,
			Integer minute, String assignmentName) {
		try {
			FileOutputStream fos = ManageAlarms.this.openFileOutput(
					Statics.ALARMS_FILENAME, Context.MODE_APPEND);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write(assignmentName + "<GAY>" + year + "<GAY>" + month
					+ "<GAY>" + DoM + "<GAY>" + hour + "<GAY>" + minute
					+ "<GAY>");

			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}