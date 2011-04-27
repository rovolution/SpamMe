package spam.me;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SpamMeActivity extends Activity {
	SharedPreferences preferences;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// Initialize preferences
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	//Creates the Options Menu created by clicking on Menu
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	//Handler for when Options Menu items are clicked on
	public boolean onOptionsItemSelected(MenuItem item) {
		int myReqCode = 1;
		Intent otherIntent;
		switch (item.getItemId()) {
		//Route to main page
		case R.id.mainPageButton:
			otherIntent = new Intent(this, SpamMe.class); 
			startActivityIfNeeded(otherIntent, myReqCode); 
			break;
			//Route to Create Chat page
		case R.id.createChatPageButton:
			otherIntent = new Intent(this, CreateGroupChatUI.class); 
			startActivityIfNeeded(otherIntent, myReqCode); 
			break;
		}
		return true;
	}
}
