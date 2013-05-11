package flashCards;

import java.util.ArrayList;

import com.example.mt_study.Main_screen;
import com.example.mt_study.R;
import com.example.mt_study.R.id;
import com.example.mt_study.R.layout;
import com.example.mt_study.R.menu;
import com.example.statics.Statics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DisplayAllFlashCards extends Activity {

	private Button home;
	private ListView flashCardList;
	private ArrayAdapter<Deck> listAdapter;
	private ArrayList<Deck> tempList;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_all_flash_cards);
        
        Statics.listOfDecks.clear();
        Statics.readFlashCardDataFromFile(this);
        Statics.setAllDecks(this);
        tempList = Statics.getAllDecks();
        
        
        flashCardList = (ListView)findViewById(R.id.flashCardList);
        listAdapter = new AllFlashCardListViewAdapter(this, tempList);
        flashCardList.setAdapter(listAdapter);
    }
}
