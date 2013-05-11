package flashCards;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.mt_study.R;
import com.example.statics.Statics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class FlashCardEditDeleteArrayAdapter extends ArrayAdapter<Card> {

	
	/**
	 * context for the class that calls this adapter
	 */
	private Context otherClass;
	
	/**
	 * position from which deck was clicked in the previous activity
	 */
	private int listPosition;
	
	/**
	 * layoutInflater for the getView function
	 */
	private LayoutInflater layoutInflater;
	
	/**
	 * holder for the data that will be passed from the other activity
	 */
	private Hashtable<Integer, ArrayList<Deck>> deckList;
	

	
	private class CardViewHolder {
		TextView question;
		TextView answer;
		
		/**
		 * constructor to be used in getView
		 */
		CardViewHolder(TextView question, TextView answer) {
			this.question = question;
			this.answer = answer;
		}
		
		/**
		 * @return the question
		 */
		public TextView getQuestion() {
			return question;
		}
		/**
		 * @param question the question to set
		 */
		public void setQuestion(TextView question) {
			this.question = question;
		}
		/**
		 * @return the answer
		 */
		public TextView getAnswer() {
			return answer;
		}
		/**
		 * @param answer the answer to set
		 */
		public void setAnswer(TextView answer) {
			this.answer = answer;
		}
	}
	
	FlashCardEditDeleteArrayAdapter(Context context, Hashtable<Integer, ArrayList<Deck>> data,
			int position) {
		super(context, R.id.edit_question, R.id.edit_answer);
		layoutInflater = LayoutInflater.from(context);
		deckList = data;
		otherClass = context;
		listPosition = position;
		
	}


	public int getCount() {
		return deckList.get(Statics.mainPageListPosition).get(listPosition).getDeck().size();
	}

	public Card getItem(int position) {
		return deckList.get(Statics.mainPageListPosition).get(listPosition).getDeck().get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Card card = this.getItem(position);
		
		TextView question;
		TextView answer;
		
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.flash_card_edit_delete_row, null);
			
			question = (TextView)convertView.findViewById(R.id.flash_card_edit_delete_question);
			answer = (TextView)convertView.findViewById(R.id.flash_card_edit_delete_answer);
			
			convertView.setTag(new CardViewHolder(question, answer));
		}
		else {
			CardViewHolder viewHolder = (CardViewHolder)convertView.getTag();
			
			question = viewHolder.getQuestion();
			answer = viewHolder.getAnswer();
		}
		
		question.setText(card.getQuestion());
		answer.setText(card.getAnswer());


		return convertView;
	}
	
}
