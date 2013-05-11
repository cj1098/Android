package flashCards;

import java.util.ArrayList;

import com.example.mt_study.R;
import com.example.statics.Statics;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;





//class used to display user's FlashCards as a question/answer 
//in a listView. Will allow the user easy access to choose which
//FlashCard they would like to edit.
public class FlashCardEdit extends Activity {
	
	private ListView flashCardList;
	private ArrayAdapter<Card> listAdapter;
	private int position;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_card_edit);
		
		Bundle extras;
		extras = getIntent().getExtras();
		position = extras.getInt("Position");
		
		flashCardList = (ListView)findViewById(R.id.questionAnswerListView);
		listAdapter = new FlashCardEditListViewAdapter(this, Statics.listOfDecks, position);
		flashCardList.setDivider(null);
		flashCardList.setAdapter(listAdapter);
		
		flashCardList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int cardPosition,
					long id) {
				
				
				final Dialog newCard = new Dialog(FlashCardEdit.this);
				newCard.setTitle("Please input your new Question and Answer");
				newCard.setContentView(R.layout.edit_flash_card_click);
				
				final EditText newCardQ = (EditText)newCard.findViewById(R.id.edit_question_edit_clicked);
				final EditText newCardA = (EditText)newCard.findViewById(R.id.edit_answer_edit_clicked);
				Button save = (Button)newCard.findViewById(R.id.save_edits_flash_card);
				
				newCard.show();
				
				save.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (newCardQ.getText().toString().equals("") && newCardA.getText().toString().equals("")) {
							Toast.makeText(FlashCardEdit.this,
									"Must have a question and an answer to save", Toast.LENGTH_SHORT).show();
						}
						else if (!newCardQ.getText().toString().equals("") && newCardA.getText().toString().equals("")) {
							Statics.editDataInFlashCardFile(FlashCardEdit.this,
									Statics.listOfDecks.get(Statics.mainPageListPosition).get(position).getDeck().get(cardPosition).getQuestion(),
									newCardQ.getText().toString(),
									Statics.listOfDecks.get(Statics.mainPageListPosition).get(position).getDeck().get(cardPosition).getAnswer(), 
									Statics.listOfDecks.get(Statics.mainPageListPosition).get(position).getDeck().get(cardPosition).getAnswer(), cardPosition);
									
								newCardQ.setText("");
								newCardA.setText("");
								listAdapter.notifyDataSetChanged();
								newCard.dismiss();
						}
						else if (newCardQ.getText().toString().equals("") && !newCardA.getText().toString().equals("")) {
							Statics.editDataInFlashCardFile(FlashCardEdit.this,
									Statics.listOfDecks.get(Statics.mainPageListPosition).get(position).getDeck().get(cardPosition).getQuestion(),
									Statics.listOfDecks.get(Statics.mainPageListPosition).get(position).getDeck().get(cardPosition).getQuestion(),
									Statics.listOfDecks.get(Statics.mainPageListPosition).get(position).getDeck().get(cardPosition).getAnswer(), 
									newCardA.getText().toString(), cardPosition);
									
								newCardQ.setText("");
								newCardA.setText("");
								listAdapter.notifyDataSetChanged();
								newCard.dismiss();
						} else {
							Statics.editDataInFlashCardFile(FlashCardEdit.this,
								Statics.listOfDecks.get(Statics.mainPageListPosition).get(position).getDeck().get(cardPosition).getQuestion(),
								newCardQ.getText().toString(),
								Statics.listOfDecks.get(Statics.mainPageListPosition).get(position).getDeck().get(cardPosition).getAnswer(), 
								newCardA.getText().toString(), cardPosition);
							listAdapter.notifyDataSetChanged();
							newCard.dismiss();
						}
					}
				});
			}
		});
	}
}
