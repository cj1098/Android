package assignments;

import java.util.ArrayList;

import com.example.mt_study.R;
import com.example.statics.Statics;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AllAssignments extends Activity {

	private ArrayList<Assignment> tempList = new ArrayList<Assignment>();
	private ArrayAdapter<Assignment> listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_assignments);

		// populate the temporary arrayList for displaying all Assignments
		
		Statics.readDataFromFile(AllAssignments.this);
		Statics.setAllAssignments();
		tempList = Statics.getAllAssignments();

		// set the ListView Adapter
		ListView allAssignments = (ListView) findViewById(R.id.all_assignments);
		listAdapter = new allAssignmentsListViewAdapter(this, tempList);
		allAssignments.setAdapter(listAdapter);

		// set on Click listeners for the listView the same as in
		// the assignments class
		allAssignments.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				Dialog listClick = new Dialog(AllAssignments.this);
				listClick.setTitle("Choose from these options");
				listClick.setContentView(R.layout.assignment_list_click);
				Button Delete = (Button) listClick
						.findViewById(R.id.assignment_listClick_Delete);
				Button Edit = (Button) listClick
						.findViewById(R.id.assignment_listClick_Edit);

				Delete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						/*
						 * deleteDataFromFile(Statics.assignmentList
						 * .get(position).getTitle(), Statics.assignmentList
						 * .get(position) .getDate_due());
						 */
						// refreshes the activity so it can read
						// from file again and correctly display
						// data
						
						Statics.deleteSingleAssignmentFromFile(AllAssignments.this, tempList.get(position).getTitle());
						Intent refresh = new Intent(AllAssignments.this,
								AllAssignments.class);
						refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(refresh);
					}
				});

				// button allowing users to edit previously entered
				// assignments
				Edit.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						final Dialog edit = new Dialog(AllAssignments.this);
						edit.setContentView(R.layout.assignment_list_dialog);

						final EditText Title = (EditText) edit
								.findViewById(R.id.assignment_title);
						Button accept_assignments = (Button) edit
								.findViewById(R.id.accept_assignments);

						Title.setText(tempList.get(position).getTitle());

						// when user has entered in all their data,
						// this writes to file.
						accept_assignments
								.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										// refreshes the activity so
										// it can read from file
										// again and correctly
										// display data
										String titleHolder = tempList.get(
												position).getTitle();
										
										Statics.editDataInFile(
												AllAssignments.this,
												titleHolder, Title.getText()
														.toString(), position);
										Intent refresh = new Intent(
												AllAssignments.this,
												AllAssignments.class);
										refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(refresh);
									}
								});
						edit.show();
					}
				});
				listClick.show();
			}
		});

	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Statics.allAssignments.clear();
	}

}
