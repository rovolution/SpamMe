package spam.me;

import java.util.List;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

public class SpamMeFacade {
	private List<GroupChat> groupChatList; 
	private PhoneInterface myPhoneInterface; 
	private SpamMeDb mySpamMeDb;
	private SpamMePreferences mySpamMePreferences;
	
	public SpamMeFacade () {
		myPhoneInterface = new PhoneInterface();
		mySpamMeDb = new SpamMeDb();
		mySpamMePreferences = new SpamMePreferences();
	}
	
	public void addFriend(View v)
	{
		List<Person> myContacts;
		myContacts = myPhoneInterface.getContactList();
		
		// How should we display the list of contacts?
	}
	
	public void removeMe(int groupId){
		
	}
	public void sendMsg(String msg, String number){
		myPhoneInterface.sendSMS(msg, number);
		
	}
	public void receiveMsg(){
		
	}
	
	public void setStatusText(SharedPreferences preferences, String statusMsg)
	{
		mySpamMePreferences.setStatusText(preferences, statusMsg);
	}
	
	public void enableStatus(){
		
	}
	public void disableStatus(){
		
	}
	public GroupChat getGroupChat(int groupId){
		return null;
		
	}
	public void startNewChat(){
		
	}
}
