package flashCards;

import java.util.ArrayList;

import com.example.mt_study.R;
import com.example.mt_study.R.color;
import com.example.statics.Statics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FlashCardEditDelete extends Activity {

	private ArrayAdapter<Card> listAdapter;
	private int position;
	private String deckTitle;
	private ArrayList<Integer> deletedItems = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_card_edit_delete_list_view);
		
		ListView flashCardDelete = (ListView)findViewById(R.id.delete_flashCard_listView);
		Button Save = (Button)findViewById(R.id.save_edit_flashCard_delete);
		Button Cancel = (Button)findViewById(R.id.cancel_flashCard_delete);
		
		Bundle extras;
		extras = getIntent().getExtras();
		position = extras.getInt("Position");
		deckTitle = extras.getString("DeckTitle");
		
		if (Statics.listOfDecks.get(Statics.mainPageListPosition).isEmpty()) {
			
		}
		else {
		listAdapter = new FlashCardEditDeleteArrayAdapter(this, Statics.listOfDecks, position);
		flashCardDelete.setAdapter(listAdapter);
		}
		
		flashCardDelete.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,
					long arg3) {
				//color coded deletion ;D
				for (int i = 0; i < parent.getChildCount(); i++) {
					if (i == position) {
						if (parent.getChildAt(i).getTag().equals(1)) {
							parent.getChildAt(i).setBackgroundColor(getResources().getColor(color.White));
							parent.getChildAt(i).setTag(0);
							for (int p = 0; p < deletedItems.size(); p++) {
								if (deletedItems.get(p).equals(i)) {
									deletedItems.remove(p);
								}
							}
						}
						else {
							parent.getChildAt(i).setBackgroundColor(getResources().getColor(color.Black));
							parent.getChildAt(i).setTag(1);
							deletedItems.add(i);
						}
					}
				}
			}
		});
		
		
		Save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Statics.deleteDataFromFlashCardFile(FlashCardEditDelete.this, false, deckTitle, deletedItems);
				Intent refresh = new Intent(FlashCardEditDelete.this, FlashCard.class);
				refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				finish();
				overridePendingTransition(0, 0);
				Statics.listOfDecks.clear();
				startActivity(refresh);
			}
			
		});
		
		Cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		//tells it to go back to flashCards and refresh everything.
		super.onBackPressed();
		Intent refresh = new Intent(FlashCardEditDelete.this, FlashCard.class);
		refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		overridePendingTransition(0, 0);
		Statics.listOfDecks.clear();
		startActivity(refresh);
	}
}
