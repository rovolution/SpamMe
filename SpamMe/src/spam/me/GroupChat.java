package spam.me;

import java.util.ArrayList;
import java.util.List;

public class GroupChat {
	private List<Person> membersList;
	private List<Message> messageChain;
	private String groupName;
	private long groupID;
	
	//Default Constructor
	// Initialize everything and set the groupID to -1
	public GroupChat(){
		membersList = new ArrayList<Person>();
		messageChain = new ArrayList<Message>();
		groupName = "";
		groupID = -1;
	}
	public void addPerson(Person newPerson){
		membersList.add(newPerson);
	}
	
	public void removePerson(Person oldPerson){
		membersList.remove(oldPerson);
	}
	
	public void addMessage (Message newMessage){
		messageChain.add(newMessage);
	}
	
	public List<Person> getMembersList(){
		return membersList;
	}
	
	/*
	 * 
	 * Returns number of messages in this GroupChat
	 */
	public int getMessageCount(){
		return messageChain.size();
	}
	
	public String[] getMessageChain(){
		int index;
		//Create-initialize a return array of Strings
		String[] retStrArray = new String[messageChain.size()];
		//Loop through the messages in the array and add them to the return array
		index = 0;
		for (Message msg:messageChain) {
			retStrArray[index] = msg.getContent();
			index++;
		}
		return retStrArray;
	}
	
	public void setMembersList (List<Person> p){
		membersList = p;
	}
	public void setMessageChain (List<Message>m){
		messageChain = m;
	}
	public void setGroupName (String name)
	{
		groupName = name;
	}
	
	public String getGroupName ()
	{
		return groupName;
	}
	
	public void setGroupId (long id)
	{
		groupID = id;
	}
	
	public long getGroupId ()
	{
		return groupID;
	}
	
	public String toString()
	{
		return (groupName);
	}
	public String getMemberWithNumber(String phoneNumber) {
		for (Person p:membersList) {
			if (p.getPhoneNum().contains(phoneNumber) || phoneNumber.contains(p.getPhoneNum())) {
				return p.getName();
			}
		}
		return "Unknown";
	}
	
}
