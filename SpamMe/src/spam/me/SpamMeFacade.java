package spam.me;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SpamMeFacade {
	private List<GroupChat> groupChatList; 
	private PhoneInterface myPhoneInterface; 
	private final SpamMeDb mySpamMeDb;
	private SpamMePreferences mySpamMePreferences;
	
	public SpamMeFacade (Context c) {
		myPhoneInterface = new PhoneInterface();
		mySpamMeDb = new SpamMeDb(c);
		mySpamMePreferences = new SpamMePreferences();
	}
	
	public void addFriend(View v){
		List<Person> myContacts;
		//myContacts = myPhoneInterface.getContactList();
		
		// How should we display the list of contacts?
	}
	/**
	 * Method makes the necessary calls to add a new group to the database
	 * Returns -1 if entry was not added to the database
	 * Returns 1 if name was added successfully
	 */
	public long addNewGroup(GroupChat group){
		//Check for group name
		if (!group.getGroupName().equals(null)){
			long i = mySpamMeDb.addGroupChat(group.getGroupName());
			System.out.println("SpamMeFacade addNewGroup: " + i );
			System.out.println("SpamMeFacade groupname: " + group.getGroupName());
			group.setGroupId(i);
			return i;
		}
		else{
			return -1;
		}
	}
	
	/**
	 * Makes the necessary calls to add a particular group  member to the group chat
	 */
	public int addNewGroupMember (GroupChat group, Person p){
		return 0;
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
		System.out.println(statusMsg);
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
