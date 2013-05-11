package flashCards;

import java.io.Serializable;
import java.util.ArrayList;

import android.R.string;
import assignments.Assignment;




public class Course implements Serializable  {
	
	/**
	 *  class used when user creates a new class without syncing from pipeline
	 */
	private static final long serialVersionUID = 1L;
	
	//each class has its own set of assignments and a flashCard deck(or decks) associated with it.
	private ArrayList<Assignment> classAssignment = new ArrayList<Assignment>();
	private ArrayList<Deck> classDeck = new ArrayList<Deck>();
	private String title;
	private String meetingDates;
	private String meetingTime;

	/**
	 * @return the classAssignment
	 */
	public ArrayList<Assignment> getClassAssignment() {
		return classAssignment;
	}
	/**
	 * @param classAssignment the classAssignment to set
	 */
	public void setClassAssignment(ArrayList<Assignment> classAssignment) {
		this.classAssignment = classAssignment;
	}
	/**
	 * @return the classDeck
	 */
	public ArrayList<Deck> getClassDeck() {
		return classDeck;
	}
	/**
	 * @param classDeck the classDeck to set
	 */
	public void setClassDeck(ArrayList<Deck> classDeck) {
		this.classDeck = classDeck;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the meetingDates
	 */
	public String getMeetingDates() {
		return meetingDates;
	}
	/**
	 * @param meetingDates the meetingDates to set
	 */
	public void setMeetingDates(String meetingDates) {
		this.meetingDates = meetingDates;
	}
	/**
	 * @return the meetingTime
	 */
	public String getMeetingTime() {
		return meetingTime;
	}
	/**
	 * @param meetingTime the meetingTime to set
	 */
	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}
	
	
}