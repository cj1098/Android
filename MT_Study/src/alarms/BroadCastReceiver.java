package alarms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import com.example.statics.Statics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadCastReceiver extends BroadcastReceiver {
	Calendar myCal = Calendar.getInstance();

	@Override
	public void onReceive(Context context, Intent action) {
		//when receiving the broadcast from alarmManager
		//re-register the alarm. Used when user resets their phone

		
		if (action.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			try {
				
				FileInputStream myIn = context.openFileInput(Statics.ALARMS_FILENAME);
				InputStreamReader reader = new InputStreamReader(myIn);
				BufferedReader BR = new BufferedReader(reader);
				
				AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); 
				
				String name;
				String input;
				String total = "";

				while ((input = BR.readLine()) != null) {
					total += input;
				}
				
				String every[] = total.split(Statics.myDelimiter);
				
				for (int i = 0; i <= every.length; i++) {
					if ((i != 0) && (i % 6 == 0)) {
						name = every[i-6];
						myCal.set(Calendar.YEAR, Integer.parseInt(every[i-5]));
						myCal.set(Calendar.MONTH, Integer.parseInt(every[i-4]));
						myCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(every[i-3]));
						myCal.set(Calendar.HOUR, Integer.parseInt(every[i-2]));
						myCal.set(Calendar.MINUTE, Integer.parseInt(every[i-1]));
						myCal.set(Calendar.SECOND, 0);
						
						Intent triggered = new Intent(context, alarms.DisplayNotification.class);
						triggered.putExtra("NotificationId", 1);
						triggered.putExtra("assignmentName", name);
						
						PendingIntent displayIntent = PendingIntent.getActivity(
			                    context, ManageAlarms.counter, triggered, 0);
						
						alarmManager.cancel(displayIntent);
						
						alarmManager.set(AlarmManager.RTC_WAKEUP, 
			                    myCal.getTimeInMillis(), displayIntent);
						ManageAlarms.counter++;				
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}