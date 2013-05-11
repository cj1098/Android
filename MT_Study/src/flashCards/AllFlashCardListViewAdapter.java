package flashCards;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.mt_study.R;
import com.example.statics.Statics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AllFlashCardListViewAdapter extends ArrayAdapter<Deck> {

	
	private LayoutInflater inflater;
	private Context otherClass;
	private ArrayList<Deck> DeckList;
	
	//class used to hold views when the user scrolls on the listView
	//it stores them and then we can re-use their id's as the user scrolls
	//down or up the screen so we don't have to keep calling findViewById
	private class flashCardViewHolder {
		Button study;
		Button edit;
		Button add;
		Button delete;
		TextView title;
		
		public flashCardViewHolder(TextView title, Button study, Button edit, Button add, Button delete) {
			this.study = study;
			this.edit = edit;
			this.add = add;
			this.delete = delete;
			this.title = title;
		}
		
		public TextView getTitle() {
			return title;
		}
		
		public void setTitle(TextView title) {
			this.title = title;
		}

		public Button getStudy() {
			return study;
		}

		public void setStudy(Button study) {
			this.study = study;
		}

		public Button getEdit() {
			return edit;
		}

		public void setEdit(Button edit) {
			this.edit = edit;
		}

		public Button getAdd() {
			return add;
		}

		public void setAdd(Button add) {
			this.add = add;
		}

		public Button getDelete() {
			return delete;
		}

		public void setDelete(Button delete) {
			this.delete = delete;
		}
		
	}
	
	public AllFlashCardListViewAdapter(Context context, ArrayList<Deck> data) {
		super(context, R.id.flash_card_study, R.id.flash_card_edit);
		inflater = LayoutInflater.from(context);
		DeckList = data;
		otherClass = context;
	}
	
	//set of functions useful for the getView function
	public int getCount() {
			return DeckList.size();
	}

	public Deck getItem(int position) {
			return DeckList.get(position);
	}
	
	public long getItemId(int position)  {
			return position;
	}
	
	public int getViewTypeCount() {
		return 1;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Deck Deck = this.getItem(position);
		
		final TextView title;
		Button study;
		Button edit;
		Button add;
		Button delete;
		//if theres no view yet created on the screen, create one
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.flash_card_row, null);

			//give the views their respective ID's
			title = (TextView)convertView.findViewById(R.id.flash_card_title);
			study = (Button)convertView.findViewById(R.id.flash_card_study);
			edit = (Button)convertView.findViewById(R.id.flash_card_edit);
			add = (Button)convertView.findViewById(R.id.flash_card_add_to_deck);
			delete = (Button)convertView.findViewById(R.id.flash_card_delete_from_deck);
			
			convertView.setTag(new flashCardViewHolder(title, study, edit, add, delete));
		}
		//this gets called when the user scrolls down or up the screen and the adapter
		//wants to maximize efficiency by just calling the viewHolder instead of findViewById
		else {
			flashCardViewHolder viewHolder = (flashCardViewHolder)convertView.getTag();
			title = viewHolder.getTitle();
			study = viewHolder.getStudy();
			edit = viewHolder.getEdit();
			add = viewHolder.getAdd();
			delete = viewHolder.getDelete();
		}
		
		//title of the deck
		title.setText(Deck.getTitle());
		
		//down here is where you set the values for all your views/objects
		//such as Buttons or textViews.
		
		//user clicks this to study go into flashCard study
		study.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent goStudy = new Intent(otherClass, FlashCardDisplay.class);
				goStudy.putExtra("ALL_FLASHCARDS", true);
				goStudy.putExtra("title", title.getText().toString());
				otherClass.startActivity(goStudy);
			}
		});
		//user clicks this to edit cards in the deck he/she selected
		edit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//see if they want to edit the Title or the flashCards
				//get the Title of the deck then search for the deck and
				//bring up flashCards in that deck
				AlertDialog.Builder decide = new AlertDialog.Builder(otherClass);
				final String blahhh = title.getText().toString();
				decide.setTitle("Edit!")
						.setMessage("Would you like to edit the Title or a flashCard of this deck?")
						.setPositiveButton("Title", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								final EditText input = new EditText(otherClass);
								AlertDialog.Builder title = new AlertDialog.Builder(otherClass);
								title.setTitle("Enter new title")
									.setView(input)
									.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Statics.editDataInFlashCardFile(otherClass, blahhh,
													input.getText().toString(), position);
											//need to make this a seemless transition
											Intent refresh = new Intent(otherClass, DisplayAllFlashCards.class);
											refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											otherClass.startActivity(refresh);
										}
									})
									.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Intent goBack = new Intent(otherClass, FlashCard.class);
											goBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											otherClass.startActivity(goBack);
										}
									})
									.show();
								
							}
						})
						.setNegativeButton("flashCard", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						})
						.show();
			}
		});
		//user clicks this to add a card to the deck he/she selected
		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
		//user clicks this to delete a card to the deck he/she selected
		//also, possibly delete the deck as a whole.
		delete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		return convertView;
	}

}