package flashCards;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.statics.Statics;
import com.example.mt_study.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class FlashCardEditListViewAdapter extends ArrayAdapter<Card> {
	
	/**
	 * 
	 */
	private LayoutInflater layoutInflater;
	private Hashtable<Integer, ArrayList<Deck>> deckList;
	Context otherClass;
	private int listPosition;
	
	
	
	private class cardViewHolder {
		TextView question;
		TextView answer;
		View divider;
		
		
		public cardViewHolder(TextView question, TextView answer, View divider) {
			this.question = question;
			this.answer = answer;
			this.divider = divider;
		}

		public TextView getQuestion() {
			return question;
		}

		public void setQuestion(TextView question) {
			this.question = question;
		}

		public TextView getAnswer() {
			return answer;
		}

		public void setAnswer(TextView answer) {
			this.answer = answer;
		}


		public View getDivider() {
			return divider;
		}


		public void setDivider(View divider) {
			this.divider = divider;
		}
	}
	
	public FlashCardEditListViewAdapter(Context context, Hashtable<Integer, ArrayList<Deck>> data,
			int position) {
		super(context, R.id.edit_question, R.id.edit_answer);
		layoutInflater = LayoutInflater.from(context);
		deckList = data;
		otherClass = context;
		listPosition = position;
	}
	
	
	//set of functions useful for the getView function
		public int getCount() {
				return deckList.get(Statics.mainPageListPosition).get(listPosition).getDeck().size();
		}

		public Card getItem(int position) {
				return deckList.get(Statics.mainPageListPosition).get(listPosition).getDeck().get(position);
		}
		
		public long getItemId(int position)  {
				return position;
		}
		
		public int getViewTypeCount() {
			return 1;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Card card = this.getItem(position);
			
			TextView question;
			TextView answer;
			View divider;
			
			//if null, first time listView item on the screen special case.
			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.flash_card_edit_row, null);
				
				question = (TextView)convertView.findViewById(R.id.edit_question);
				answer = (TextView)convertView.findViewById(R.id.edit_answer);
				divider = (View)convertView.findViewById(R.id.edit_divider);
				
				convertView.setTag(new cardViewHolder(question, answer, divider));
			}
			else {
				cardViewHolder viewHolder = (cardViewHolder)convertView.getTag();
				
				question = viewHolder.getQuestion();
				answer = viewHolder.getAnswer();
				divider = viewHolder.getDivider();
				
			}
			
			question.setText(card.getQuestion());
			answer.setText(card.getAnswer());
			divider.setBackgroundColor(otherClass.getResources().getColor(R.color.Aqua));
			return convertView;
		}
}