package spam.me;

import spam.me.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGroupChatUI extends Activity{
	private GroupChat myGroupChat;
	String groupName;
	EditText inputGroupName;
	//SpamMeFacade - API for application
	private SpamMeFacade spamMeFacade;
	
	/** Called when the activity is first created. */ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategroupchatui);
        
        inputGroupName = (EditText)findViewById(R.id.groupNameText);
        
    }
	
	//Handler for okay button when creating a new group chat
	public void okayClicked(View v){
		//Get the user's input for Group Name
		groupName = inputGroupName.getText().toString();
		Toast.makeText(getBaseContext(), 
				groupName, 
				Toast.LENGTH_SHORT).show();
		
		//Save group name to the database: 
		spamMeFacade.addNewGroupName(groupName);
		/*
		int myReqCode = 0;
    	Intent groupChatTabHost = new Intent(v.getContext(), GroupChatTabHostUI.class); 
    	startActivityIfNeeded(groupChatTabHost, myReqCode);
    	*/ 
    	
    	
	}
}
