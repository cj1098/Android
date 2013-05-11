package com.example.mt_study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.example.statics.Statics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ImportScheduleLogin extends Activity {

	// variables, yay!
	private ProgressDialog progressDialog, submitProgressDialog;
	private final int TIMEOUT_VALUE = 7000;	// 7 seconds
	private EditText password;
	private EditText userName;
	private String term_code;
	private ArrayList<String> term_codes;
	private ArrayList<String> term_names;
	private ArrayList<String> classList;
	private ArrayAdapter<String> adapter;
	private Button submit;
	private Spinner spinner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_login);
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		
		// initialize variables
		userName = (EditText) this.findViewById(R.id.inputName);
		password = (EditText) this.findViewById(R.id.inputPassword);
		
		// populate the term list in the spinner
		getTermList getList = new getTermList(progressDialog); 
		getList.execute();
	}
    
    // listener for the submit button, assigned in XML
    public void submitClickHandler(View target) {
        // Do stuff
        try {
        	if (!(userName.getText().toString().equals("") || 
        		  password.getText().toString().equals("")))
        	{
		    	final getSchedule newSchedule = new getSchedule(submitProgressDialog);
		        newSchedule.execute();
        	}
        	else
        		Toast.makeText(getApplicationContext(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();
	    } catch	(Exception e) {}

	}
    
    // listener for the term spinner
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
	    @Override
		public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	term_code = term_codes.get(pos);
	    }

	    @Override
		public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
    
	// ******************************** ASYNC TASKS ******************************************
	/**
	 * Loads the student's selected schedule from the server and extracts the class names into classList.
	 */
	public class getSchedule extends AsyncTask<HttpResponse,String,Boolean> {
		ProgressDialog progress;
		String message = null;
		
		public getSchedule(ProgressDialog progress)
		{
			this.progress = progress;
		}
		
		protected void onPreExecute() {
			progress = ProgressDialog.show(ImportScheduleLogin.this, "Accessing", "Verifying credentials and checking schedule.");
			progress.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		
		@Override
		protected Boolean doInBackground(HttpResponse... httpResponses) {
			//Log.e("ENTRANCE INTO BACKGROUND THREAD", "Successful entrance!");
			// Add data for Post
			BufferedReader in = null;
			
			// set up http client with timeout constraints (to avoid server overloading).
	        HttpParams httpParams = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_VALUE);
	        HttpClient httpclient = new DefaultHttpClient(httpParams);
	        
		    HttpPost httppost = new HttpPost("https://itdapp06prod.fsa.mtsu.edu/MobileApps/CourseSchedule");
		    
		    try {
		        // Add data for Post
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("loginName", userName.getText().toString().trim()));
		        nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString().trim()));
		        nameValuePairs.add(new BasicNameValuePair("term", term_code));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        // get input stream and send to xml handler
		        
		        CourseHandler ch = new CourseHandler();
		        if (!ch.getCourseList(response))
		        {
		        	message = "Invalid username and/or password. Please try again.";
		        	return false;
		        }
		    }
		    catch (Exception e) {
		    	//e.printStackTrace();
		    	return false;
		    }
		    //Log.e("EXIT OUT OF BACKGROUND THREAD", "Successful exit!");  
		    //progress.dismiss();
		    
		    return true ;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			progress.dismiss();
			super.onPostExecute(result);
			if (!result)
			{
				if (message == null) {
					Toast.makeText(getApplicationContext(), "Could not load schedule import at this time.", Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				String sch_str = "These classes have been imported:";
				for (String str : classList)
				{
					sch_str += "\n" + str;
				}
				//Toast.makeText(getApplicationContext(), sch_str, Toast.LENGTH_LONG).show();

				Statics.addClassesIntoFile(ImportScheduleLogin.this, classList);
		        Intent CYA = new Intent(ImportScheduleLogin.this, Main_screen.class);
		        CYA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(CYA);
			}
		}
	}
	
	
	/**
	 * Loads the list of available terms from the server
	 */
	public class getTermList extends AsyncTask<HttpResponse,String,Boolean> {
		ProgressDialog progress;
		
		public getTermList(ProgressDialog progress)
		{
			this.progress = progress;
		}
		
		protected void onPreExecute() {
			progress = ProgressDialog.show(ImportScheduleLogin.this, "Loading", "Retrieving list of semesters.");
			progress.setCanceledOnTouchOutside(false);
			super.onPreExecute();
		}
		
		@Override
		protected Boolean doInBackground(HttpResponse... httpResponses) {
			//Log.e("ENTRANCE INTO BACKGROUND THREAD", "Successful entrance!");
			// Add data for Post
			BufferedReader in = null;
			
			// set up http client with timeout constraints (to avoid server overloading).
	        try {
	        	TermHandler th = new TermHandler();
	        	return th.getTermList();
	        } catch (Exception e) {
		    	// if the server is being unresponsive, inform the user and exit.
		    	Toast.makeText(getApplicationContext(), "Cannot retrieve schedule info at this time.", Toast.LENGTH_LONG).show();
				finish();
		    }		   
		    //Log.e("EXIT OUT OF BACKGROUND THREAD", "Successful exit!");  
		    //progress.dismiss();
		    
		    return null ;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (!result)
			{
				Toast.makeText(getApplicationContext(), "Could not load schedule import at this time.", Toast.LENGTH_LONG).show();
				finish();
			}
			else
			{
				// populate the course dropdown and set its OnItemSelectedListener.
				spinner = (Spinner) findViewById(R.id.spinner1);
				adapter = new ArrayAdapter <String>(ImportScheduleLogin.this, android.R.layout.simple_spinner_item, term_names);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    
			    spinner.setAdapter(adapter);
			    spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
			}
		}
	}
	// ******************************* END ASYNC TASKS ******************************************

	
	// ********************************** XML HANDLERS ******************************************
	/**
	 * Class used to parse the course list XML
	 */
	public class TermHandler extends DefaultHandler{
		private String current_name, current_code;
		private int numTerms = 0;
		StringBuffer chars = new StringBuffer();
		URL url;
		
		@Override
		public void characters(char ch[], int start, int length) {
			chars.append(new String(ch, start, length));
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes atts) {
			chars = new StringBuffer();
		}
				
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (localName.equalsIgnoreCase("termCode"))
			{
				term_codes.add(chars.toString());
			}
			else if (localName.equalsIgnoreCase("termDesc"))
			{
				term_names.add(chars.toString());
			}
			else if (localName.equalsIgnoreCase("term"))
			{
				++numTerms;
			}
			else if (localName.equalsIgnoreCase("classSchedule"))
			{
				throw new SAXException();
			}
		}//end EndElement
		
		/**
		 * This function populates the hash table "terms" with available terms.
		 * @return False if unsuccessful, otherwise true.
		 */
		public boolean getTermList() {
			try {
				
				term_codes = new ArrayList<String>();
				term_names = new ArrayList<String>();

				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				
				url = new URL ("https://itdapp06prod.fsa.mtsu.edu/MobileApps/CourseSchedule");
				URLConnection urlConn = url.openConnection();
				urlConn.setConnectTimeout(TIMEOUT_VALUE);

				xr.setContentHandler(this);
				xr.parse(new InputSource(urlConn.getInputStream()));
			} catch (IOException e) {
				// if the server isn't responding, return false
				return false;
			}
			catch (SAXException e) {}
			catch (ParserConfigurationException e) {}

			return true;
		}
	}  
	// end parsing handler
	
	/**
	 * Class used to parse the course list XML
	 */
	public class CourseHandler extends DefaultHandler{
		private String current_name, current_code;
		private int numTerms = 0;
		StringBuffer chars = new StringBuffer();
		URL url;
		
		@Override
		public void characters(char ch[], int start, int length) {
			chars.append(new String(ch, start, length));
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes atts) {
			chars = new StringBuffer();
		}
				
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (localName.equalsIgnoreCase("title"))
			{
				classList.add(chars.toString());

			}
		}//end EndElement
		
		/**
		 * This function populates the hash table "terms" with available terms.
		 * @return False if unsuccessful, otherwise true.
		 */
		public boolean getCourseList(HttpResponse response) {
			try {
				classList = new ArrayList<String>();
				
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				
				url = new URL ("https://itdapp06prod.fsa.mtsu.edu/MobileApps/CourseSchedule");
				URLConnection urlConn = url.openConnection();
				urlConn.setConnectTimeout(TIMEOUT_VALUE);

				xr.setContentHandler(this);
				xr.parse(new InputSource(response.getEntity().getContent()));
			} catch (IOException e) {
				// if the server isn't responding, return false
				return false;
			}
			catch (SAXException e) {
				return false;
			}
			catch (ParserConfigurationException e) {}

			return true;
		}
	}
	// end parsing handler
	// ****************************** END XML HANDLERS ******************************************
}