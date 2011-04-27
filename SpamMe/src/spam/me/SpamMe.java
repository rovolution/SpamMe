package spam.me;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.PhoneLookup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpamMe extends SpamMeActivity {
	private SharedPreferences statusStatePreference; //saving the state of status
	private User mySelf;
	private Spinner dropDownMenu;
	private SpamMeFacade spamMeFacade; 
	private ArrayAdapter adapter;
	private RadioButton statusOn;
	private RadioButton statusOff;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//Initializations
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		spamMeFacade = new SpamMeFacade(this);
		mySelf = new User();
		statusStatePreference = PreferenceManager.getDefaultSharedPreferences(this);
		statusOn = (RadioButton)findViewById(R.id.statusOn);
		statusOff = (RadioButton)findViewById(R.id.statusOff);
		
		//If the status is set to active - set the radio buttons
		if (spamMeFacade.getStatus(statusStatePreference)){
			statusOn.setChecked(true);
			statusOff.setChecked(false);
		}
		else{ //If the status is set to inactive - set the radio buttons
			statusOn.setChecked(false);
			statusOff.setChecked(true);
		}
		
		setStatusHint();

		//Initializing the drop down menu
		dropDownMenu = (Spinner)findViewById(R.id.savedChatsDropDown);

		//Query for the saved groups in the database and set the drop down menu
		GroupChat [] savedGroups;
		savedGroups = spamMeFacade.getSavedGroups();
		adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, savedGroups);
		dropDownMenu.setAdapter(adapter);
		
		dropDownMenu.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			/**
			 * Handler for when item is selected in the drop down menu
			 * Starts the activity with the appropriate group chat
			 */
			public void onItemSelected(AdapterView<?> parent, View arg1,int pos, long arg3) {
				if (arg3 != 0){
					//Get the selected group chat
					GroupChat gc = (GroupChat)dropDownMenu.getSelectedItem();
					//Start activity for the selected group chat
					int myReqCode = 0;
					Intent groupChatTabHost = new Intent(arg1.getContext(), GroupChatTabHostUI.class); 
					groupChatTabHost.putExtra("newGroupChatID", gc.getGroupId());
					startActivityIfNeeded(groupChatTabHost, myReqCode);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	@Override
	public void onResume(){
		super.onResume();
		GroupChat [] savedGroups;
		savedGroups = spamMeFacade.getSavedGroups();
		adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, savedGroups);
		dropDownMenu.setAdapter(adapter);
	}

	/**
	 * Handler for "Start a new group chat" button 
	 * Starts the activity with a new group chat
	 */
	public void newGroupChatClicked (View v){
		int myReqCode = 1;
		Intent newGroupChat = new Intent(v.getContext(), CreateGroupChatUI.class); 
		startActivityIfNeeded(newGroupChat, myReqCode); 
	}

	/**
	 * Handler for the status bar 
	 * Sets the status when the user hits "Set Status"
	 * @param v
	 */
	public void StatusClicked (View v){
		TextView statusText = (TextView) findViewById(R.id.statusText);
		String statusMsg = statusText.getText().toString();

		mySelf.setMyStatus(statusMsg);
		spamMeFacade.setStatusText(preferences, statusMsg);
		
		Toast.makeText(getBaseContext(), "New Status set " + statusMsg, 3).show();
		setStatusHint();
		statusText.setText("");
		
	}
	/**
	 * Handler for Status radio button (on)
	 * @param View v
	 */
	public void statusOnClicked (View v) {
		//Activate the status 
		mySelf.activateStatus();
		spamMeFacade.enableStatus(statusStatePreference);
		
		//Debug
		Toast.makeText(getBaseContext(), "Status enabled", 3).show();
	}

	/**
	 * Handler for Staus radio button (off)
	 * @param View v
	 */
	public void statusOffClicked (View v) {	
		//Deactivate the status
		mySelf.deactivateStatus();
		spamMeFacade.disableStatus(statusStatePreference);
		
		//Debug
		Toast.makeText(getBaseContext(), "Status disabled", 3).show();
	}

	private void setStatusHint() {	
		TextView statusText = (TextView) findViewById(R.id.statusText);

		String myStatus = preferences.getString("statusMessage", "");
		if (statusText != null && myStatus != null)
		{
			statusText.setHint(myStatus);
		}	
	}
}
