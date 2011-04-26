package spam.me;

public class Status {
	private String statusMsg;
	private boolean isActive; 
	public Status (){
		isActive = false;
	}
	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	} 
	
	public void activateStatus(){
		isActive = true; 
	}
	
	public void deactivateStatus(){
		isActive = false; 
	}
}
