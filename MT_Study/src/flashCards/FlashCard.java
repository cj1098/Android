package flashCards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.example.mt_study.Main_screen;
import com.example.mt_study.R;
import com.example.mt_study.R.color;
import com.example.statics.Statics;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


public class FlashCard extends Activity {

	private ListView flashCardList;
	private ArrayAdapter<Deck> listAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_card_main_ui);
        
        flashCardList = (ListView)findViewById(R.id.flashCardList);
        
        //get the position on the main screen that was clicked.
        //That is how we differentiate between which class is which
        //and which deck/assignments go with which class.
        Bundle extras;
		extras = getIntent().getExtras();
		if (extras != null) {
			Statics.mainPageListPosition = extras.getInt("position");
		}
		
		if (dataInFile()) {
			//check to see if there is data in the file for
			//this particular class. If there is, clear
			//the flashCard hashtable and read from the file
			//filling up the hashtable with the data from the
			//file.
			Statics.listOfDecks.clear();
			Statics.readFlashCardDataFromFile(FlashCard.this);
		}
		else if (dataInFile() == false && Statics.listOfDecks.isEmpty()) {
			//if they don't have any flashCards for this particular
			//class, ask them if they would like to make some
			AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
			alertBox.setTitle("FlashCards")
					.setMessage("You don't seem to have any flash Cards for this deck, would you like to make some?)")
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//Title of the deck
							final EditText input = new EditText(FlashCard.this);
							AlertDialog.Builder alertBox = new AlertDialog.Builder(FlashCard.this);
							alertBox.setTitle("Deck Title")
							.setMessage("Please Input the title of your deck")
							.setView(input)
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									//They've now made a deck with a title. Next they'll 
									//put what question and answer they want on their flashCard
									//need to check for duplicate deck titles
									if (input.getText().toString().equals("")) {
										Toast.makeText(FlashCard.this, "You must have a Title to save a Deck", Toast.LENGTH_SHORT).show();
									}
									else {
									final Dialog createFlashCards = new Dialog(FlashCard.this);
									createFlashCards.setContentView(R.layout.flash_card_create);
									final EditText Question = (EditText)createFlashCards.findViewById(R.id.FlashCard_CreateQuestionEditText);
									final EditText Answer = (EditText)createFlashCards.findViewById(R.id.FlashCard_CreateAnswerEditText);
									Button SaveFlashCard = (Button)createFlashCards.findViewById(R.id.create_new_saved_FlashCard);
									createFlashCards.show();
									//saves the flashCard to the file on their phone
									SaveFlashCard.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View arg0) {
											//have to check for empty questions or answers
											if (!Question.getText().toString().equals("") && !Answer.getText().toString().equals("")) {
												Statics.addDataIntoFlashCardFile(FlashCard.this, input.getText().toString(), Question.getText().toString().trim(),
														Answer.getText().toString(), Statics.mainPageListPosition);
												Toast.makeText(FlashCard.this, "FlashCard successfully created", Toast.LENGTH_SHORT).show();
											}
											else {
												Toast.makeText(FlashCard.this, "Must have a question and an answer to save a flashCard", Toast.LENGTH_SHORT).show();
											}
											Question.setText("");
											Answer.setText("");
										}
									});
								}
								}
							})
							.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									
								}
							})
							.show();
								}
							})
							.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									finish();
								}
							})
							.show();
		}
        
        
		//have to check one more time just in case on loading
		//the activity it doesn't erase the hashtable
		if (Statics.listOfDecks.isEmpty() == false) {
			//set the adapter for the listView, send it the arrayList of decks.
			listAdapter = new FlashCardListViewAdapter(FlashCard.this, Statics.listOfDecks);
			flashCardList.setAdapter(listAdapter);
		}
    }


    
    private Boolean dataInFile() {
		// Opens a file based on the file name stored in FILENAME.
		FileInputStream myIn;
		String line;
		String total = "";
		String[] each;
		try {
			myIn = openFileInput(Statics.FLASHCARDS_FILENAME);
			// Initializes readers to read the file.
			InputStreamReader inputReader = new InputStreamReader(myIn);
			BufferedReader BR = new BufferedReader(inputReader);

			File file = FlashCard.this
					.getFileStreamPath(Statics.FLASHCARDS_FILENAME);
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.flash_card_options, menu);
    	return true;
    }
    
    //pressing the physical menu button on an android will
    //make this menu pop up
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	//there is only create_new for now.
    		case R.id.menu_create: 
    			final EditText input = new EditText(FlashCard.this);
    			AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
    			alertBox.setTitle("Please input the title of your new deck")
    					.setView(input)
    					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//Once they've clicked this, they have input their deck title
								//next, save title and prompt for card creation.
								if (input.getText().toString().equals("")) {
									Toast.makeText(FlashCard.this, "You must have a Title to save a Deck", Toast.LENGTH_SHORT).show();
								}
								else {
								final Dialog createNewCard = new Dialog(FlashCard.this);
								createNewCard.setContentView(R.layout.flash_card_create);
								//try and make this blue title sometime
								createNewCard.setTitle("Make your own!");
								final EditText Question = (EditText)createNewCard.findViewById(R.id.FlashCard_CreateQuestionEditText);
								final EditText Answer = (EditText)createNewCard.findViewById(R.id.FlashCard_CreateAnswerEditText);
								Button saveFlashCard = (Button)createNewCard.findViewById(R.id.create_new_saved_FlashCard);
								createNewCard.show();
								
								saveFlashCard.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										//have to check for empty questions or answers
										
										/*
										 * Have to check for people using new Lines in there creating of questions and answers
										 */
										if (!Question.getText().toString().equals("") && !Answer.getText().toString().equals("")) {
											Statics.addDataIntoFlashCardFile(FlashCard.this, input.getText().toString(), Question.getText().toString().trim(),
													Answer.getText().toString(), Statics.mainPageListPosition);
											Toast.makeText(FlashCard.this, "FlashCard successfully created", Toast.LENGTH_SHORT).show();
										}
										else {
											Toast.makeText(FlashCard.this, "Must have a question and an answer to save a FlashCard", Toast.LENGTH_SHORT).show();
										}
										Question.setText("");
										Answer.setText("");
									}
								});
								}
							}
    					})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						})
						.show();
    			
    			//create a delete option where they click on the deck they want to delete
    			//and it askes them if they are sure.
    			return true;	
    			default:
    				return super.onOptionsItemSelected(item);
    	}
    }
    
    //necessary so that Decks gets cleared before re-entry.. giggidy.
    @Override 
    public void onBackPressed() {
    	super.onBackPressed();
    	Statics.listOfDecks.clear();
    }
}
