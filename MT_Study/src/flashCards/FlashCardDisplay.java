package flashCards;

import java.util.ArrayList;
import java.util.Map.Entry;
import com.example.mt_study.R;
import com.example.statics.Statics;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FlashCardDisplay extends Activity {
	
	private TextView question;
	private TextView answer;
	private ImageButton next;
	private ImageButton previous;
	private Button showAnswer;
	private Button engageSURVIVALMODE;
	private View bloodyEll;
	private int listPosition;
	private int cardCounter = 0;
	private boolean truth = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_card_study);        
        initControls();
        
        Bundle extras;
        extras = getIntent().getExtras();
        
        
		if (extras != null) {
	        truth = extras.getBoolean("ALL_FLASHCARDS");
	        if (truth) {
	        	//if truth == true, then we know that this activity came
	        	//from ALLflashCardListView. So we need to act accordingly.
	        	
	        	Statics.listOfDecks.clear();
	        	Statics.readFlashCardDataFromFile(this);
	        	String search = extras.getString("title");
	        	Deck currentDeck = searchForDeck(search);
	        	if (currentDeck != null) {
	        		question.setText(currentDeck.getDeck().get(0).getQuestion());
	        	}
	        	initControls(true, currentDeck);
	        }
	        else {
	        	//if it didn't act like only one flashCard has been clicked.
	        	Statics.listOfDecks.clear();
	        	Statics.readFlashCardDataFromFile(this);
	        	listPosition = extras.getInt("deckPosition");
	        	question.setText(Statics.listOfDecks
					.get(Statics.mainPageListPosition).get(listPosition)
					.getDeck().get(cardCounter).getQuestion());
	        }
		}
        else if (extras == null) {
        	Toast.makeText(this, "Did not come from an Activity, error", Toast.LENGTH_SHORT).show();
        }
        
	}
	
	//initControls used when the user has come from one assignment
	//instead of viewing "all flashCards"
	public void initControls() {
		question = (TextView)findViewById(R.id.flash_card_study_question);
		answer = (TextView)findViewById(R.id.flash_card_study_answer);
		next = (ImageButton)findViewById(R.id.next_flash_Card);
		previous = (ImageButton)findViewById(R.id.previous_flashCard);
		showAnswer = (Button)findViewById(R.id.flash_card_show_answer);
		bloodyEll = (View)findViewById(R.id.bloody_view);
		engageSURVIVALMODE = (Button)findViewById(R.id.activate_insane_mode);
		
		bloodyEll.setBackgroundColor(getResources().getColor(R.color.TransparentCrimson));
		bloodyEll.setVisibility(View.GONE);
		
		
		
		engageSURVIVALMODE.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
			}
		});
		
		//shows answer when user clicks the answer button
		showAnswer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				answer.setText(Statics.listOfDecks
						.get(Statics.mainPageListPosition).get(listPosition)
						.getDeck().get(cardCounter).getAnswer());
			}
		});
		//when user clicks this button it resets the answer text field to null
		//and increments the deck:card counter
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cardCounter++;
				if (cardCounter >= Statics.listOfDecks.get(Statics.mainPageListPosition).get(listPosition).getDeck().size()) {
					cardCounter = 0;
					question.setText(Statics.listOfDecks
							.get(Statics.mainPageListPosition).get(listPosition)
							.getDeck().get(cardCounter).getQuestion());
						answer.setText("");
				}
				else {
					question.setText(Statics.listOfDecks
						.get(Statics.mainPageListPosition).get(listPosition)
						.getDeck().get(cardCounter).getQuestion());
					answer.setText("");
				}
			}
		});
		//when user clicks this button it resets the answer text field to null
		//and decrements the deck:card counter
		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cardCounter--;
				if (cardCounter < 0) {
					cardCounter = Statics.listOfDecks
							.get(Statics.mainPageListPosition)
							.get(listPosition).getDeck().size() - 1;
					question.setText(Statics.listOfDecks
							.get(Statics.mainPageListPosition)
							.get(listPosition).getDeck().get(cardCounter)
							.getQuestion());
					answer.setText("");
				} else {
					question.setText(Statics.listOfDecks
							.get(Statics.mainPageListPosition)
							.get(listPosition).getDeck().get(cardCounter)
							.getQuestion());
					answer.setText("");
				}
			}
		});
	}
	
	//is invoked when user has clicked on "all flashCards"
	public void initControls(Boolean singular, final Deck d) {
		question = (TextView)findViewById(R.id.flash_card_study_question);
		answer = (TextView)findViewById(R.id.flash_card_study_answer);
		next = (ImageButton)findViewById(R.id.next_flash_Card);
		previous = (ImageButton)findViewById(R.id.previous_flashCard);
		showAnswer = (Button)findViewById(R.id.flash_card_show_answer);
		
		//shows answer when user clicks the answer button
		showAnswer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				answer.setText(d.getDeck().get(cardCounter).getAnswer());
			}
		});
		
		//when user clicks this button it resets the answer text field to null
		//and increments the deck:card counter
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cardCounter++;
				if (cardCounter >= d.getDeck().size()) {
					cardCounter = 0;
					question.setText(d.getDeck().get(0).getQuestion());
						answer.setText("");
				}
				else {
					question.setText(d.getDeck().get(cardCounter).getQuestion());
					answer.setText("");
				}
			}
		});
		//when user clicks this button it resets the answer text field to null and 
		//decrements the deck:card counter
		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cardCounter--;
				if (cardCounter < 0) {
					cardCounter = d.getDeck().size() - 1;
					question.setText(d.getDeck().get(0).getQuestion());
					answer.setText("");
				} else {
					question.setText(d.getDeck().get(cardCounter).getAnswer());
					answer.setText("");
				}
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	public Deck searchForDeck(String title) {
		Deck found = new Deck();
		
		//retrieve deck with the same title
		for (Entry<Integer, ArrayList<Deck>> i : Statics.listOfDecks.entrySet()) {
			for (int p = 0; p < i.getValue().size(); p++) {
				for (int q = 0; q < i.getValue().get(p).getDeck().size(); q++) {
				if (i.getValue().get(p).getTitle().equals(title)) {
					found = i.getValue().get(p);
					//Toast.makeText(this, i.getValue().get(p).getDeck().get(q).getQuestion(), Toast.LENGTH_SHORT).show();
				}
				}
			}
		}
		
		return found;
	}
}