package spam.me;

import spam.me.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateGroupChatUI extends Activity{
	private GroupChat myGroupChat;
	
	/** Called when the activity is first created. */ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategroupchatui);
        
    }
	
	public void okayClicked(View v){
		
		int myReqCode = 0;
    	Intent groupChatTabHost = new Intent(v.getContext(), GroupChatTabHostUI.class); 
    	startActivityIfNeeded(groupChatTabHost, myReqCode); 
	}
}
