package spam.me;

import java.util.List;

public class GroupChat {
	private List <Person> membersList; 
	private List <Message> messageChain; 
	private String groupName; 
	private int groupID;
	
	public void addPerson(Person newPerson){
		membersList.add(newPerson);
	}
	
	public void removePerson(String phoneNumber){
		
	}
	
	public void addMessage (Message newMessage){
		messageChain.add(newMessage);
	}
	
	public List<Person> getMembersList(){
		return membersList;
	}
	
	public List<Message> getMessageChain(){
		return messageChain;
	}
	
	public void setGroupName (String name)
	{
		groupName = name;
	}
	
	public String getGroupName ()
	{
		return groupName;
	}
	
	public void setGroupId (int id)
	{
		groupID = id;
	}
	
	public int getGroupId ()
	{
		return groupID;
	}
}
