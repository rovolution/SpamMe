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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpamMe extends Activity {
	private SharedPreferences preferences;
	private User mySelf;
	private Spinner dropDownMenu;
	//SpamMeFacade - API for application
	private SpamMeFacade spamMeFacade;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		spamMeFacade = new SpamMeFacade(this);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		setStatusHint();
		
        //Initializing the drop down menu
        dropDownMenu = (Spinner)findViewById(R.id.savedChatsDropDown);
        String [] groupNames = new String[] {"group1", "group2", "group3"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, groupNames);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.id.savedChatsDropDown, groupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);
        dropDownMenu.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
    
	public void newGroupChatClicked (View v)
	{
    	int myReqCode = 1;
    	Intent newGroupChat = new Intent(v.getContext(), CreateGroupChatUI.class); 
    	startActivityIfNeeded(newGroupChat, myReqCode); 
    }
    
	public void StatusClicked (View v)
	{
		TextView statusText = (TextView) findViewById(R.id.statusText);
		String statusMsg = statusText.getText().toString();
		
		spamMeFacade.setStatusText(preferences, statusMsg);
		
		Toast.makeText(getBaseContext(), "New Status set", 3).show();
		setStatusHint();
		statusText.setText("");
	}
	
	public void statusOnClicked (View v) {	
		Toast.makeText(getBaseContext(), "Status enabled", 3).show();
	}
	
	public void statusOffClicked (View v) {	
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
	public class MyOnItemSelectedListener implements OnItemSelectedListener{

		@Override
	    public void onItemSelected(AdapterView<?> parent,
	            View view, int pos, long id) {
	          Toast.makeText(parent.getContext(), "The group is " +
	              parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	        }
        @Override
		public void onNothingSelected(AdapterView parent) {
	          // Do nothing.
	        }
		
	}

	
}