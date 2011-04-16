package spam.me;

public class Message {
	private String content;
	private Person owner = new Person();
	private enum msgType {REMOVE, ADD, TEXT};
	private msgType type;
	private long groupID;
	
	
	public String getContent(){
		return content; 
	}
	
	public void setContent(String msg){
	}
	
	public Person getOwner(){
		return owner;
	}
	
	public void setOwner(String name, String number){
		owner.setName(name);
		owner.setPhoneNum(number);
	}
	
	public msgType getType(){
		return type;
	}
	
	public void setType(msgType newType){
		
	}
	
	public long getGroupID(){
		return groupID;
	}
	
	public void setGroupID(long id){
		groupID = id;
	}
}
