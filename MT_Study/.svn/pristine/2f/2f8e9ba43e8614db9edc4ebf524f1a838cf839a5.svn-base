package com.example.statics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import java.util.Map.Entry;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import assignments.Assignment;
import assignments.Assignments;

import flashCards.Card;
import flashCards.Course;
import flashCards.Deck;

public class Statics {

	//ok this class is actually more of a utility class but
	//im too lazy to make other classes so -_-

	
	//position on the main page that a user will click for one of
	//their classes
	public static int mainPageListPosition;
	
	//delimiter used in all the split functions
	public static String myDelimiter = "<GAY>";
	
	//splash screen on/off boolean
	public static boolean splashScreen;
	
	//easy to access list of every assignment for the All assignments button
	private static ArrayList<Assignment> homeDisplayAllAssignments = new ArrayList<Assignment>();
	
	//easy to access list of every flashCard for the ALL flashCards button
	private static ArrayList<Deck> homeDisplayAllDecks = new ArrayList<Deck>();

	//overall list of user assignments.
	public static Hashtable<Integer, ArrayList<Assignment>> allAssignments = new Hashtable<Integer, ArrayList<Assignment>>();

	// local list of Decks user has stored on their phone.
	public static Hashtable<Integer, ArrayList<Deck>> listOfDecks = new Hashtable<Integer, ArrayList<Deck>>();

	// list of classes user has stored on their phone (each have their own
	// respective deck and assignment Lists)
	public static ArrayList<String> listOfCourses = new ArrayList<String>();
	

	//Filenames
	public static String ASSIGNMENTS_FILENAME = "all_assignments";
	public static String FLASHCARDS_FILENAME = "all_flashcards";
	public static String CLASSLIST_FILENAME = "all_classes";
	public static String ALARMS_FILENAME = "list_of_alarms";
	
	
	
	public static void setAllAssignments() {
		
		for (int i = 0; i < allAssignments.size(); i++) {
			ArrayList<Assignment> temp = allAssignments.get(i);
			if (temp != null) {
				for (int p = 0; p < allAssignments.get(i).size(); p++) {
					homeDisplayAllAssignments.add(allAssignments.get(i).get(p));
				}
			}
		}
	}
	
	public static ArrayList<Assignment> getAllAssignments() {
		return homeDisplayAllAssignments;
	}
	
	public static void setAllDecks(Context context) {
		int nameCounter;
		for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
			homeDisplayAllDecks.addAll(i.getValue());
		}
		
		for (int z = 0; z < homeDisplayAllDecks.size(); z++) {
			//deck name counter
			nameCounter = 0;
			for (int r = 0; r < homeDisplayAllDecks.size(); r++) {
				if (homeDisplayAllDecks.get(z).getTitle().equals(homeDisplayAllDecks.get(r).getTitle())) {
					nameCounter++;
					if (nameCounter > 1) {
						homeDisplayAllDecks.remove(z);
					}
				}
			}
		}
	}
	
	//used with/alongside setAllDecks. Used in allAssignments.java
	public static ArrayList<Deck> getAllDecks() {
		return homeDisplayAllDecks;
	}

	public static void editDataInFile(Context context, String oldTitle,
			String newTitle, int position) {
		
		//change the value in the list first, then save it to file
		for (Entry<Integer, ArrayList<Assignment>> i : allAssignments.entrySet()) {
			for (int p = 0 ; p < i.getValue().size(); p++) {
				if (i.getValue().get(p).getTitle().equals(oldTitle)) {
					i.getValue().get(p).setTitle(newTitle);
				}
			}
		}
		
		
		try {
			FileOutputStream fos = context.openFileOutput(ASSIGNMENTS_FILENAME,
					Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			
			//actually writing the changed data to the file
			for (Entry<Integer, ArrayList<Assignment>> i : allAssignments.entrySet()) {
				for (int p = 0; p < i.getValue().size(); p++) {
					osw.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>");
				}
			}
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			// catch errors opening file
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//function to edit the title of a deck
	public static void editDataInFlashCardFile(Context context, String oldTitle,
			String newTitle, int position) {
		//first change the value of the title in the list
		//then save it to the file.
		
		for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
			for (int p = 0; p < i.getValue().size(); p++) {
				if (i.getValue().get(p).getTitle().equals(oldTitle)) {
					i.getValue().get(p).setTitle(newTitle);
				}
			}
		}
		
		
		
		//save to file
		try {
			FileOutputStream fos = context.openFileOutput(FLASHCARDS_FILENAME, Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			
			for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
				for (int p = 0; p < i.getValue().size(); p++) {
					for (int q = 0; q < i.getValue().get(p).getDeck().size(); q++) {
						osw.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>" +
								i.getValue().get(p).getDeck().get(q).getQuestion()
								+ "<GAY>" + i.getValue().get(p).getDeck().get(q).getAnswer() + "<GAY>");
					}
				}
			}
			
			osw.flush();
			osw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//function to edit the flashCards in a deck
	public static void editDataInFlashCardFile(Context context, String oldCardQuestion,
			String newCardQuestion, String oldCardAnswer, String newCardAnswer, int position) {
		//first change the value of the title in the list
		//then save it to the file.
		
		for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
			for (int p = 0; p < i.getValue().size(); p++) {
				for (int q = 0; q < i.getValue().get(p).getDeck().size(); q++) {
					if (i.getValue().get(p).getDeck().get(q).getQuestion()
							.equals(oldCardQuestion)
							&& i.getValue().get(p).getDeck().get(q).getAnswer()
									.equals(oldCardAnswer)) {
						i.getValue().get(p).getDeck().get(q)
								.setQuestion(newCardQuestion);
						i.getValue().get(p).getDeck().get(q)
								.setAnswer(newCardAnswer);
					}
				}
			}
		}
		
		
		
		//save to file
		try {
			FileOutputStream fos = context.openFileOutput(FLASHCARDS_FILENAME, Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			
			for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
				for (int p = 0; p < i.getValue().size(); p++) {
					for (int q = 0; q < i.getValue().get(p).getDeck().size(); q++) {
						osw.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>" +
								i.getValue().get(p).getDeck().get(q).getQuestion()
								+ "<GAY>" + i.getValue().get(p).getDeck().get(q).getAnswer() + "<GAY>");
					}
				}
			}
			
			osw.flush();
			osw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//function is used when the user has clicked on a position
	//in the mainList and has chosen the assignment option. Next
	//they click on an assignment and select the delete option. 
	public static void deleteSingleAssignmentFromFile(Context context, String title) {
		for (Entry<Integer, ArrayList<Assignment>> i : allAssignments.entrySet()) {
			for (int p = 0; p < i.getValue().size(); p++) {
				if (i.getValue().get(p).getTitle().equals(title))  {
					allAssignments.get(i.getKey()).remove(p);
				}
			}
		}
		try {
			FileOutputStream fos;
			OutputStreamWriter osw;
			fos = context.openFileOutput(ASSIGNMENTS_FILENAME, Context.MODE_PRIVATE);
			osw = new OutputStreamWriter(fos);
			
			for (Entry<Integer, ArrayList<Assignment>> i : allAssignments.entrySet()) {
				for (int p = 0; p < i.getValue().size(); p++) {
					osw.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>");
				}
			}

			osw.flush();
			osw.close();
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void deleteDataFromFile(Context context, int position) {

		try {

			//need to reread from file to refresh the values for
			//listOfDecks and allAssignments
			readDataFromFile(context);
			readFlashCardDataFromFile(context);
			
			
			//preemptive check for a null position in the List.
			//if it's null, then that means there was nothing in that position
			//to delete. Still need to decrement in case it the position
			//was greater than the key.
			ArrayList<Deck> troll = listOfDecks.get(position);
			if (troll == null) {
				
				FileOutputStream fos1;
				OutputStreamWriter osw1;
				fos1 = context.openFileOutput(FLASHCARDS_FILENAME, Context.MODE_PRIVATE);
				osw1 = new OutputStreamWriter(fos1);
				
				//figure this out, you're really close. Right here.
				for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
					for (int p = 0; p < i.getValue().size(); p++) {
						for (int q = 0; q < i.getValue().get(p).getDeck().size(); q++) {
							if (i.getKey() > position) {
								osw1.write(i.getKey() - 1 + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>" + 
									i.getValue().get(p).getDeck().get(q).getQuestion() + "<GAY>" +
									i.getValue().get(p).getDeck().get(q).getAnswer() + "<GAY>");
							}
							else {
								osw1.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>" + 
										i.getValue().get(p).getDeck().get(q).getQuestion() + "<GAY>" +
										i.getValue().get(p).getDeck().get(q).getAnswer() + "<GAY>");
							}
						}
					}
				}
				
				osw1.flush();
				osw1.close();
			}
			else {
		
			//delete the value
			listOfDecks.remove(position);
			


			FileOutputStream fos1;
			OutputStreamWriter osw1;
			fos1 = context.openFileOutput(FLASHCARDS_FILENAME, Context.MODE_PRIVATE);
			osw1 = new OutputStreamWriter(fos1);
			
			//decrementing the keys of the values so they 
			//match their respective positions on the mainListView
			for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
				for (int p = 0; p < i.getValue().size(); p++) {
					for (int q = 0; q < i.getValue().get(p).getDeck().size(); q++) {
						if (i.getKey() > position) {
							osw1.write(i.getKey() - 1 + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>" + 
								i.getValue().get(p).getDeck().get(q).getQuestion() + "<GAY>" +
								i.getValue().get(p).getDeck().get(q).getAnswer() + "<GAY>");
						}
						else {
							osw1.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>" + 
									i.getValue().get(p).getDeck().get(q).getQuestion() + "<GAY>" +
									i.getValue().get(p).getDeck().get(q).getAnswer() + "<GAY>");
						}
					}
				}
			}
			
			
			osw1.flush();
			osw1.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void deleteAssignmentDataFromFile(Context context, int position) {
		

			try {

				
				//preemptive check for a null position in the List.
				//if it's null, then that means there was nothing in that position
				//to delete. Still need to decrement in case it the position
				//was greater than the key.
				ArrayList<Assignment> troll = allAssignments.get(position);
				if (troll == null) {
					FileOutputStream fos;
					OutputStreamWriter osw;
					
					fos = context.openFileOutput(ASSIGNMENTS_FILENAME,
							Context.MODE_PRIVATE);
					osw = new OutputStreamWriter(fos);
					
					for (Entry<Integer, ArrayList<Assignment>> i : allAssignments.entrySet()) {
						for (int p = 0; p < i.getValue().size(); p++) {
							if (i.getKey() > position) { 
								osw.write(i.getKey() - 1 + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>");
							}
							else {
								osw.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>");
							}
						}
					}

					osw.flush();
					osw.close();
				}
				
				else {

				//delete the value.
				allAssignments.remove(position);
				
				//everything above the deleted position. The keys need to be decrimented
				FileOutputStream fos;
				OutputStreamWriter osw;
				
				fos = context.openFileOutput(ASSIGNMENTS_FILENAME,
						Context.MODE_PRIVATE);
				osw = new OutputStreamWriter(fos);
				
				for (Entry<Integer, ArrayList<Assignment>> i : allAssignments.entrySet()) {
					for (int p = 0; p < i.getValue().size(); p++) {
						if (i.getKey() > position) { 
							osw.write(i.getKey() - 1 + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>");
						}
						else {
							osw.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>");
						}
					}
				}
				
				osw.flush();
				osw.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}


	}
	
	//this function is used when the user has selected a deck and 
	//has selected the option to delete something in that deck.
	public static void deleteDataFromFlashCardFile(Context context, Boolean fullDelete, String deckTitle,
			ArrayList<Integer> deletedItems) {
		try {

			//if the user selected fullDelete
			if (fullDelete) {
				for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
					for (int p = 0; p < i.getValue().size(); p++) {
						if (i.getValue().get(p).getTitle().equals(deckTitle)) {
							listOfDecks.get(i.getKey()).remove(p);
						}
					}
				}
				FileOutputStream fos;
				OutputStreamWriter osw;
					fos = context.openFileOutput(FLASHCARDS_FILENAME,
							Context.MODE_PRIVATE);
					osw = new OutputStreamWriter(fos);
					for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
						for (int p = 0; p < i.getValue().size(); p++) {
							for (int q = 0; q < i.getValue().get(p).getDeck().size(); q++) {
							osw.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>" +
							i.getValue().get(p).getDeck().get(q).getQuestion() + "<GAY>" +
							i.getValue().get(p).getDeck().get(q).getAnswer() + "<GAY>");
							}
						}
					}

				osw.flush();
				osw.close();
			}
			//otherwise, They must have selected to delete a specific card
			//in the deck. Will use the passed arrayList of deletedItems to see what 
			//card/cards the user wanted deleted. 
			else {

				Collections.sort(deletedItems);
				
				//deleting user's previously selected items
				for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
					for (int p = 0; p < i.getValue().size(); p++) {
						if (i.getValue().get(p).getTitle().equals(deckTitle)) {
							for (int z = 0; z < deletedItems.size(); z++) {
								listOfDecks.get(i.getKey()).get(p).getDeck().remove(deletedItems.get(z).intValue());
								if (listOfDecks.get(i.getKey()).get(p).getDeck().isEmpty()) {
									listOfDecks.get(i.getKey()).remove(p);
								}
								//have to reincrement our list because it gets smaller every time
								//we delete something.
								for (int k = 0; k < deletedItems.size(); k++) {
									deletedItems.set(k, deletedItems.get(k) - 1);
								}
							}
						}
					}
				}
				
			//write changes to file.
			FileOutputStream fos;
			OutputStreamWriter osw;
				fos = context.openFileOutput(FLASHCARDS_FILENAME,
						Context.MODE_PRIVATE);
				osw = new OutputStreamWriter(fos);
				
				for (Entry<Integer, ArrayList<Deck>> i : listOfDecks.entrySet()) {
					for (int p = 0; p < i.getValue().size(); p++) {
						for (int q = 0; q < i.getValue().get(p).getDeck().size(); q++) {
							osw.write(i.getKey() + "<GAY>" + i.getValue().get(p).getTitle() + "<GAY>" +
							i.getValue().get(p).getDeck().get(q).getQuestion() + "<GAY>" +
							i.getValue().get(p).getDeck().get(q).getAnswer() + "<GAY>");
						}
					}
				}

			osw.flush();
			osw.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//adds data into the classes file on user's phone
	//this data set is the names of the classes on the mainListView
	public static void addClassesIntoFile(Context context, ArrayList<String> classList) {
		try {
			FileOutputStream fos = context.openFileOutput(CLASSLIST_FILENAME,
					Context.MODE_PRIVATE | Context.MODE_APPEND);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			for (int i = 0; i < classList.size(); i++) {
				osw.write(classList.get(i) + "<GAY>");
			}
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			// catch errors opening file
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//deletes data in the classes file on the user's phone
	public static void deleteClassesInFile(Context context, String title, int position) {
		try {

			listOfCourses.remove(position);
			
			FileOutputStream fos;
			OutputStreamWriter osw;
				fos = context.openFileOutput(CLASSLIST_FILENAME,
						Context.MODE_PRIVATE);
				osw = new OutputStreamWriter(fos);
				//delete the class from the phone.
				for (int p = 0; p < listOfCourses.size(); p++) {
					osw.write(listOfCourses.get(p) + "<GAY>");
				}
			

			osw.flush();
			osw.close();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//reads classes from the file and stores onto user's phone
	public static void readClassesFromFile(Context context) {
		try {
			listOfCourses.clear();
			FileInputStream myIn;
			// Opens a file based on the file name stored in FILENAME.
			myIn = context.openFileInput(CLASSLIST_FILENAME);
			
			// Initializes readers to read the file.
			InputStreamReader inputReader = new InputStreamReader(myIn);
			BufferedReader BR = new BufferedReader(inputReader);

			// Holds a line from the text file.
			String line;
			
			//Total string needed for the conglamerate of line
			String total = "";
			
			//temp reader for the file
			ArrayList<String> reader = new ArrayList<String>();
			
			while ((line = BR.readLine()) != null) {
				//read in the value from the file and combine it
				//with other line
				total += line;
			}
			String[] each = total.split(Statics.myDelimiter);
			for (int i = 0; i < each.length; i++) {
				listOfCourses.add(each[i]);
			}

			BR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//adds data into the assignment file on user's phone
	public static void addDataIntoFile(Context context, String title, int position) {
		try {
				FileOutputStream fos = context.openFileOutput(
						ASSIGNMENTS_FILENAME, Context.MODE_PRIVATE
								| Context.MODE_APPEND);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				osw.write(position + "<GAY>" + title + "<GAY>");
				osw.flush();
				osw.close();
		} catch (FileNotFoundException e) {
			// catch errors opening file
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//adds data into the flashCard file on user's phone.
	public static void addDataIntoFlashCardFile(Context context, String Title, String Question,
			String Answer, int position) {
		try {
			FileOutputStream fos = context.openFileOutput(
					FLASHCARDS_FILENAME, Context.MODE_PRIVATE
							| Context.MODE_APPEND);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write(position + "<GAY>" + Title + "<GAY>" + Question + "<GAY>" + Answer + "<GAY>");
			
			osw.flush();
			osw.close();
			
	} catch (FileNotFoundException e) {
		// catch errors opening file
		e.printStackTrace();
		Toast.makeText(context, "calles", Toast.LENGTH_SHORT).show();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}

	public static void readFlashCardDataFromFile(Context context) {
		try {
			FileInputStream myIn = context.openFileInput(FLASHCARDS_FILENAME);
			InputStreamReader inputReader = new InputStreamReader(myIn);
			BufferedReader BR = new BufferedReader(inputReader);
			
			//temporary array for holding strings from the file
			ArrayList<String> reader = new ArrayList<String>();
			
			//holds a line from the text file
			String line;
			
			//holds the conglamerate of the entire string
			String total = "";
			
			//read in the whole string and split on <GAY>
			
			while ((line = BR.readLine()) != null) {
				total += line;
			}
			
			String[] each = total.split(Statics.myDelimiter);
			for (int i = 0; i < each.length; i++) {
				reader.add(each[i]);
			}
			
			ArrayList<Deck> deckList = new ArrayList<Deck>();

				//need to make it so that I put a deckList in every position
				//and not just in the mainPageListPosition. (this is also necessary for AllFlashCards)
				for (int i = 0; i < reader.size(); i++) {
					if (isInteger(reader.get(i)) && !reader.get(i).equals("")) {
						deckList = createDeckList(reader, Integer.parseInt(reader.get(i)));
						listOfDecks.put(Integer.parseInt(reader.get(i)), deckList);
					}
				}

			BR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readDataFromFile(Context context) {
		try {
			
			FileInputStream myIn;
			// Opens a file based on the file name stored in FILENAME.
			myIn = context.openFileInput(ASSIGNMENTS_FILENAME);
			// Initializes readers to read the file.
			InputStreamReader inputReader = new InputStreamReader(myIn);
			BufferedReader BR = new BufferedReader(inputReader);

			// Holds a line from the text file.
			String line;
			
			//Total string needed for the conglamerate of line
			String total = "";

			// currentAssignment to add to the list
			Assignment currentAssignment = new Assignment();

			// current AssignmentList to add to the list
			ArrayList<Assignment> currentAssignmentList = new ArrayList<Assignment>();
			
			//temp reader for the file
			ArrayList<String> reader = new ArrayList<String>();
			
			while ((line = BR.readLine()) != null) {
				//read in the value from the file and combine it
				//with other line
				total += line;
			}
			
			String[] each = total.split(Statics.myDelimiter);
			for (int i = 0; i < each.length; i++) {
				reader.add(each[i]);
			}
			
			//populate the hashTable
			for (int r = 0; r < reader.size(); r++) {
				if (isInteger(reader.get(r)) && !reader.get(r).equals("")) {
					currentAssignmentList = collectAssignments(reader, Integer.parseInt(reader.get(r)));
					allAssignments.put(Integer.parseInt(reader.get(r)), currentAssignmentList);
				}
			}
			
			BR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//function to neatly gather and put all assignments associated with
	//a certain position into an arrayList so that it can be added to the
	//hashtable of arrayLists.
	private static ArrayList<Assignment> collectAssignments(ArrayList<String> a, int position) {
		ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();
		Assignment current = new Assignment();
		for (int i = 0; i < a.size(); i++) {
			if (isInteger(a.get(i)) && Integer.parseInt(a.get(i)) == position) {
				current.setTitle(a.get(i + 1));
				assignmentList.add(current);
				current = new Assignment();
			}
		}
		
		return assignmentList;
	}
	
	
	//creates a deck from all the associated cards.
	private static Deck createDeck(ArrayList<String> a, String str, int position) {
		Card tempCard = new Card();
		Deck tempDeck = new Deck();
		for (int i = 0; i < a.size(); i++) {
			if (isInteger(a.get(i))) {
				if (Integer.parseInt(a.get(i)) == position && a.get(i + 1).equals(str)) {
				tempDeck.setTitle(str);
				tempCard.setQuestion(a.get(i + 2));
				//really stupid and ghetto way to solve if user inputs
				//their answer as just a single digit.
				if (isInteger(a.get(i + 3))) {
					tempCard.setAnswer(a.get(i + 3) + ".");
				}
				else {
					tempCard.setAnswer(a.get(i+3));
				}
				tempDeck.getDeck().add(tempCard);
				tempCard = new Card();
				}
			}
		}
		
		return tempDeck;
	}
	
	//serves the same function as collectAssignments except it's for flashCards
	private static ArrayList<Deck> createDeckList(ArrayList<String> a, int position) {
		ArrayList<Deck> tempList = new ArrayList<Deck>();
		Deck tempDeck = new Deck();
		for (int i = 0; i < a.size(); i++) {
			if (isInteger(a.get(i)) && Integer.parseInt(a.get(i)) == position) {
				tempDeck = createDeck(a, a.get(i + 1), position);
				tempList.add(tempDeck);
				tempList = removeDuplicates(tempList, tempDeck.getTitle());
			}
		}
		
		
		return tempList;
	}
	
	//removes duplicate deckTitles from the passed arrayList
	private static ArrayList<Deck> removeDuplicates(ArrayList<Deck> a, String title) {
		int counter = 0;
		
		for (int i = 0; i < a.size(); i++) {

			if (a.get(i).getTitle().equals(title)) {
				counter++;

				if (counter > 1) {
					
					a.remove(i);
					counter--;
				}
			}
		}
		
		return a;
	}

	
	//will need to change this for just one integer by itself
	public static boolean isInteger(String str)
	{
		if (str.length() > 1) {
			return false;
		}
		else if (str.length() == 1) {
			char c = str.charAt(0);
			if (Character.isDigit(c)) {
				return true;
			}
		}
	    return false;
	}
}
