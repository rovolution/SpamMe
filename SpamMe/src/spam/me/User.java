package spam.me;


public class User extends Person {
	private Status myStatus;
	
	public String getMyStatus() {
		return myStatus.getStatusMsg();
	}
	
	public void setMyStatus(String newStatus) {
		myStatus.setStatusMsg(newStatus);
	}
	
	public void activateStatus()
	{
		myStatus.activateStatus();
	}
	
	public void deactivateStatus()
	{
		myStatus.deactivateStatus();
	}
}
