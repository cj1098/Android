package assignments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.example.mt_study.Main_screen;
import com.example.mt_study.R;
import com.example.statics.Statics;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Assignments extends Activity {
	private Button home_button;
	private ArrayAdapter<Assignment> listAdapter;
	private ListView assignmentListView;
	private String FILENAME = "assignment_lists";
	private boolean mediaPlayerSTOP;

	// if user has no assignments make them make one. Otherwise, if they have
	// some in their save file direct them straight to the options menu for
	// assignments.
	// It will have create new/edit/delete/show all.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		Bundle extras;
		extras = getIntent().getExtras();
		if (extras != null) {
			Statics.mainPageListPosition = extras.getInt("position");
		}

		//Toast.makeText(Assignments.this, Integer.toString(Statics.mainPageListPosition), Toast.LENGTH_SHORT).show();
		// check to see if user has any assignments in their save file
		if (dataInFile() == true) {
			// Statics.assignmentList.clear();
			// readDataFromFile();
			Statics.allAssignments.clear();
			Statics.readDataFromFile(Assignments.this);
		}
		// if they do not, prompt them to make some now or later
		else if (dataInFile() == false && Statics.allAssignments.isEmpty()) {
			AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
			alertBox.setTitle("Assignment List is Empty!")
					.setMessage(
							"You have no assignments yet, Please go make some!")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// go to create a new assignment
									
									AlertDialog.Builder alertBox = new AlertDialog.Builder(Assignments.this);

									final EditText Title = new EditText(Assignments.this);
									alertBox.setTitle("Title")
											.setMessage("Please put your assignment name")
											.setView(Title)
											.setPositiveButton("Save", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													
													// when user has no assignments for the
													// first time, writes and creates a file
													// with assignments in it.
													String assignmentTitle = Title
															.getText()
															.toString();

														
														Statics.addDataIntoFile(
																Assignments.this,
																assignmentTitle,
																Statics.mainPageListPosition);

														// refreshes the
														// activity so it can
														// read from file again
														// and correctly display
														// data
														Intent refresh = new Intent(
																Assignments.this,
																Assignments.class);
														refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
														startActivity(refresh);
														overridePendingTransition(0,0);

												}
											})
											.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													finish();
												}
											})
									
											.show();
								}
							})
					// the "Not now" button when user has no assignments
					.setNegativeButton("Not Now",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).show();
		}

		setContentView(R.layout.assignment_list);
		home_button = (Button) findViewById(R.id.home_button);
		home_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent home = new Intent(Assignments.this, Main_screen.class);
				home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(home);
			}
		});

		if (Statics.allAssignments.isEmpty()) {
		} else {
			// set the adapter for the listView
			Statics.readDataFromFile(Assignments.this);
			assignmentListView = (ListView) findViewById(R.id.assignment_list);
			listAdapter = new AssignmentListViewAdapter(this,
					Statics.allAssignments);
			//listAdapter.notifyDataSetChanged();
			assignmentListView.setAdapter(listAdapter);
			
			//Toast.makeText(Assignments.this, Integer.toString(Statics.allAssignments.get(0).size()), Toast.LENGTH_LONG).show();

			// give the user the same options as the menu button click.
			assignmentListView
					.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View arg1,
								final int position, long arg3) {
							Dialog listClick = new Dialog(Assignments.this);
							listClick.setTitle("Choose from these options");
							listClick
									.setContentView(R.layout.assignment_list_click);
							Button Delete = (Button) listClick
									.findViewById(R.id.assignment_listClick_Delete);
							Button Edit = (Button) listClick
									.findViewById(R.id.assignment_listClick_Edit);
							Button setTimer = (Button) listClick
									.findViewById(R.id.assignment_set_timer);

							Delete.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									/*deleteDataFromFile(Statics.assignmentList
											.get(position).getTitle(),
											Statics.assignmentList
													.get(position)
													.getDate_due());*/
									// refreshes the activity so it can read
									// from file again and correctly display
									// data
									Statics.deleteSingleAssignmentFromFile(Assignments.this, 
											Statics.allAssignments.get(Statics.mainPageListPosition).get(position).getTitle());
									Intent refresh = new Intent(
											Assignments.this, Assignments.class);
									refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									Statics.allAssignments.clear();
									startActivity(refresh);
									overridePendingTransition(0,0);
								}
							});

							// button allowing users to edit previously entered
							// assignments
							Edit.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									final Dialog edit = new Dialog(
											Assignments.this);
									edit.setContentView(R.layout.assignment_list_dialog);

									final EditText Title = (EditText) edit
											.findViewById(R.id.assignment_title);
									final EditText DueDate = (EditText) edit
											.findViewById(R.id.assignment_dueDate);
									Button accept_assignments = (Button) edit
											.findViewById(R.id.accept_assignments);

									Title.setText(Statics.allAssignments.get(Statics.mainPageListPosition).get(
											position).getTitle());
									
									

									// when user has entered in all their data,
									// this writes to file.
									accept_assignments
											.setOnClickListener(new OnClickListener() {
												public void onClick(View v) {
													// refreshes the activity so
													// it can read from file
													// again and correctly
													// display data
													String titleHolder = Statics.allAssignments.get(Statics.mainPageListPosition).get(position).getTitle();
													Statics.editDataInFile(Assignments.this, titleHolder, Title.getText().toString(), position);
													Intent refresh = new Intent(
															Assignments.this,
															Assignments.class);
													refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
													startActivity(refresh);
												}
											});
									edit.show();
								}
						
							});
							
							
							setTimer.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									Intent startAlarmPicking = new Intent(Assignments.this, alarms.ManageAlarms.class);
									startAlarmPicking.putExtra("AssignmentName", 
											Statics.allAssignments.get(Statics.mainPageListPosition).get(position).getTitle());
									startActivity(startAlarmPicking);
									overridePendingTransition(0,0);
								}
							});
							
							listClick.show();
						}
					});
		}
	
	}

	private Boolean dataInFile() {
		// Opens a file based on the file name stored in FILENAME.
		FileInputStream myIn;
		String line;
		String total = "";
		String[] each;
		try {
			myIn = openFileInput(Statics.ASSIGNMENTS_FILENAME);
			// Initializes readers to read the file.
			InputStreamReader inputReader = new InputStreamReader(myIn);
			BufferedReader BR = new BufferedReader(inputReader);

			File file = Assignments.this
					.getFileStreamPath(Statics.ASSIGNMENTS_FILENAME);
			if (file.exists()) {
				while ((line = BR.readLine()) != null) {
					total += line;
				}
				each = total.split(Statics.myDelimiter);
				for (int i = 0; i < each.length; i++) {
					if (Statics.isInteger(each[i]) && (!each[i].equals(""))) {
						if (Integer.parseInt(each[i]) == Statics.mainPageListPosition)
							return true;
					}
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// needed otherwise Statics.assignmentList will be doubled when reloaded.
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Statics.allAssignments.clear();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.assignment_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.create_new:
			AlertDialog.Builder alertBox = new AlertDialog.Builder(Assignments.this);

			final EditText Title = new EditText(Assignments.this);
			alertBox.setTitle("Title")
					.setMessage("Please put your assignment name")
					.setView(Title)
					.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							// when user has no assignments for the
							// first time, writes and creates a file
							// with assignments in it.
							String assignmentTitle = Title
									.getText()
									.toString();

								
								Statics.addDataIntoFile(
										Assignments.this,
										assignmentTitle,
										Statics.mainPageListPosition);

								// refreshes the
								// activity so it can
								// read from file again
								// and correctly display
								// data
								Intent refresh = new Intent(
										Assignments.this,
										Assignments.class);
								refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(refresh);
								overridePendingTransition(0,0);

						}
					})
					.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					})
			
					.show();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
