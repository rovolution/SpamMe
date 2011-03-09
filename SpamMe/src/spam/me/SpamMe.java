package spam.me;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.PhoneLookup;
import android.view.View;

public class SpamMe extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	}
    public void newGroupChatClicked(View v){
    	int myReqCode = 1;
    	//Intent featuredEvent = new Intent(v.getContext(), FeaturedEvent.class);
    	//startActivityIfNeeded(featuredEvent, myReqCode);
    	Intent newGroupChat = new Intent(v.getContext(), CreateGroupChatUI.class); 
    	startActivityIfNeeded(newGroupChat, myReqCode); 
    }
}