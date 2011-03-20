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
import android.widget.TextView;

public class SpamMe extends Activity {
	SharedPreferences preferences;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		TextView statusText = (TextView) this.findViewById(R.id.statusText);
		String myStatus = preferences.getString("statusMessage", "");
		if (statusText != null && myStatus != null)
		{
			statusText.setHint(myStatus);
		}
	}
    
	public void newGroupChatClicked (View v)
	{
    	int myReqCode = 1;
    	//Intent featuredEvent = new Intent(v.getContext(), FeaturedEvent.class);
    	//startActivityIfNeeded(featuredEvent, myReqCode);
    	Intent newGroupChat = new Intent(v.getContext(), CreateGroupChatUI.class); 
    	startActivityIfNeeded(newGroupChat, myReqCode); 
    }
    
	public void StatusClicked (View v)
	{
		TextView statusText = (TextView) findViewById(R.id.statusText);
		System.out.println(statusText.getText().toString());
		String statusMsg = statusText.getText().toString();
		
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("statusMessage", statusMsg);
		editor.commit();
	}
}