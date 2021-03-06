package spam.me;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
	
	public int addFriend(View v, GroupChat myGroupChat, Person newMember){
		return mySpamMeDb.addMember(myGroupChat, newMember);
	}
	
	public void addMessage(Message m){
		mySpamMeDb.addMessage(m);
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
	 * Method to retrieve all the saved groups
	 * Returns an array of GroupChats
	 */
	public GroupChat[] getSavedGroups(){
		return mySpamMeDb.getAllGroupChatNames();
	}
	
	/**
	 * Makes the necessary calls to add a particular group  member to the group chat
	 */
	public int addNewGroupMember (GroupChat group, Person p){
		return 0;
	}
	
	public void removeMe(long groupId){
		mySpamMeDb.removeGroupChat(groupId);
	}
	
	public void removeMember(String name, String groupName){
		mySpamMeDb.removeMember(name, groupName);
	}
	
	public void sendMsg(String msg, String []numbers, long groupID){
		//Send the message via myPhoneInterface
		myPhoneInterface.sendSMS(msg, numbers);
		
		//Add the message to the list of messages
		//Strip out the message content from the existing message
		String msgContent[] = msg.split(":");
		//Add the "Me: " prefix to the message
		msg = "Me: " + msgContent[2];
		//Add the sent message to the groupChat message list
		Message sentMsg = createMessage(groupID, "myNumber", msg);
		mySpamMeDb.addMessage(sentMsg);
	}
	public void receiveMsg(){
		
	}
	
	/**
	 * Method creates a message object 
	 * @param id
	 * @param senderNumber
	 * @param msg
	 */
	public Message createMessage(long id, String senderNumber, String msg){
		Message m = new Message(); 
		m.setGroupID(id);
		m.setContent(msg);
		m.setOwner(mySpamMeDb.getMember(senderNumber), senderNumber);
		return m;
	}
	
	public void setStatusText(SharedPreferences preferences, String statusMsg){
		mySpamMePreferences.setStatusText(preferences, statusMsg);
	}
	
	public void enableStatus(SharedPreferences preferences){
		mySpamMePreferences.setStatusState(preferences, true);
	}
	
	public void disableStatus(SharedPreferences preferences){
		mySpamMePreferences.setStatusState(preferences, false);
	}
	
	public boolean getStatus(SharedPreferences preferences){
		return mySpamMePreferences.getStatusState(preferences);
	}
	public String getStatusText(SharedPreferences preferences){
		return mySpamMePreferences.getStatusText(preferences);
	}
	
	public GroupChat getGroupChat(long groupID){
		return mySpamMeDb.getGroupChat(groupID);
		
	}
	
	public void startNewChat(){
		
	}
	
	public long findGroupIDByName(String groupName) {
		return mySpamMeDb.getGroupIDFromGroupName(groupName);
	}
	
	public String getPersonNameViaPhone(String phoneNumber, long groupID) {
		//Get the group associated with groupID
		GroupChat group = getGroupChat(groupID);
		//Get the Name of the person from the group using the phone #
		String phoneNum = group.getMemberWithNumber(phoneNumber);
		return phoneNum;
	}
	

}
