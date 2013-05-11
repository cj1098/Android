package flashCards;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Card implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String question;
	private String answer;
	private Bitmap picture_aid;
	
	//default constructor
	public Card() {
		this.question = "";
		this.answer = "";
		this.picture_aid = null;
	}
	
	//constructor for just a question and answer
	public Card(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	//constructor for question answer and picture
	public Card(String question, String answer, Bitmap picture_aid) {
		this.question = question;
		this.answer = answer;
		this.picture_aid = picture_aid;
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * @return the picture_aid
	 */
	public Bitmap getPicture_aid() {
		return picture_aid;
	}
	/**
	 * @param picture_aid the picture_aid to set
	 */
	public void setPicture_aid(Bitmap picture_aid) {
		this.picture_aid = picture_aid;
	}
	
	
}
