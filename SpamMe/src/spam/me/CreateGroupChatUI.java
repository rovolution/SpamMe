package spam.me;

import spam.me.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGroupChatUI extends Activity{
	private GroupChat myGroupChat = new GroupChat();
	String groupName;
	EditText inputGroupName;
	//SpamMeFacade - API for application
	private SpamMeFacade spamMeFacade;
	
	/** Called when the activity is first created. */ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spamMeFacade = new SpamMeFacade (this);
        setContentView(R.layout.creategroupchatui);
        
        inputGroupName = (EditText)findViewById(R.id.groupNameText);
        
    }
	
	//Handler for okay button when creating a new group chat
	public void okayClicked(View v){
		//Get the user's input for Group Name
		groupName = inputGroupName.getText().toString();
	
		//Save the group name into a group chat object
		myGroupChat.setGroupName(groupName);
		
		int i = spamMeFacade.addNewGroup(myGroupChat);
	
		//Name the user entered already exists in database, doesn't get added
		if (i == -1){
			Toast.makeText(getBaseContext(), 
					"This name is already used, please enter a different name", 
					Toast.LENGTH_SHORT).show();
		}
		
		//Name gets added and the GroupChatTabHost activity gets started
		else{
			int myReqCode = 0;
			Intent groupChatTabHost = new Intent(v.getContext(), GroupChatTabHostUI.class); 
			groupChatTabHost.putExtra("newGroupChatID", myGroupChat.getGroupId());
			startActivityIfNeeded(groupChatTabHost, myReqCode);
		}
		
	}
}
