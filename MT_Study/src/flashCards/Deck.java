package flashCards;

import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private ArrayList<Card> Deck = new ArrayList<Card>();
	
	//default constructor
	public Deck() {
		this.title = "No Title";
		this.Deck = new ArrayList<Card>();
	}
	
	//constructor for setting the title and deck
	public Deck(String title, ArrayList<Card> deck) {
		this.title = title;
		this.Deck = deck;
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
	 * @return the deck
	 */
	public ArrayList<Card> getDeck() {
		return Deck;
	}
	/**
	 * @param deck the deck to set
	 */
	public void setDeck(ArrayList<Card> deck) {
		Deck = deck;
	}
	
}
