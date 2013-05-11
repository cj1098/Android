package assignments;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.mt_study.R;
import com.example.statics.Statics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AssignmentListViewAdapter extends ArrayAdapter<Assignment> {

	private LayoutInflater inflater;
	private Hashtable<Integer, ArrayList<Assignment>> assignmentList;
	
	// class used to hold views when the user scrolls on the listView
	// it stores them and then we can re-use their id's as the user scrolls
	// down or up the screen so we don't have to keep calling findViewById
	private class assignmentViewHolder {
		TextView Title;
		TextView Date;

		public assignmentViewHolder(TextView assignmentTitle, TextView Date) {
			this.Title = assignmentTitle;
			this.Date = Date;
		}

		public TextView getTitle() {
			return Title;
		}

		public TextView getDate() {
			return Date;
		}
	}

	public AssignmentListViewAdapter(Context context,
			Hashtable<Integer, ArrayList<Assignment>> data) {
		super(context, R.id.assignment_row_title, R.id.assignment_row_date);
		inflater = LayoutInflater.from(context);
		assignmentList = data;
	}

	// set of functions useful for the getView function
	public int getCount() {
			return assignmentList.get(Statics.mainPageListPosition).size();
	}

	public Assignment getItem(int position) {
			return assignmentList.get(Statics.mainPageListPosition).get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Assignment assignment = this.getItem(position);

		TextView assignmentTitle;
		TextView assignmentDate;

		// if theres no view yet created on the screen, create one
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.assignment_row, null);

			// give the views their respective ID's
			assignmentTitle = (TextView) convertView
					.findViewById(R.id.assignment_row_title);
			assignmentDate = (TextView) convertView
					.findViewById(R.id.assignment_row_date);

			// set the tag so we can just use the viewHolder instead of calling
			// findViewById over. It saves memory
			convertView.setTag(new assignmentViewHolder(assignmentTitle,
					assignmentDate));
		}
		// this gets called when the user scrolls down or up the screen and the
		// adapter
		// wants to maximize efficiency by just calling the viewHolder instead
		// of findViewById
		else {
			assignmentViewHolder viewHolder = (assignmentViewHolder) convertView
					.getTag();
			assignmentTitle = viewHolder.getTitle();
			assignmentDate = viewHolder.getDate();

		}
		// down here is where you set the values for all your views/objects
		// such as Buttons or textViews.
		assignmentTitle.setText(assignment.getTitle());

		return convertView;
	}
}