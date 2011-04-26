package spam.me;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{
	private SpamMeFacade mySpamMeFacade;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		mySpamMeFacade = new SpamMeFacade(context);
		
		//Get the SMS message passed in
		Bundle bundle = intent.getExtras();
		SmsMessage [] msgs = null;
		String str = "";
		String senderPhoneNumber = "";
		Long rcvGroupID = (long) -1;
		String rcvMsg = "";
		String rcvSender = "";

		if (bundle != null){
			//Retrieve the SMS message
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i=0; i<msgs.length; i++){
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				str += "SMS from " + msgs[i].getOriginatingAddress();
				//Set the sender phone number
				senderPhoneNumber = msgs[i].getOriginatingAddress().substring(7);
				str += " :";
				str += msgs[i].getMessageBody().toString();
				str += "\n";


				//Save message to database 
				//get the group ID 
				//save to message table
				//Parsing message
				Pattern p = Pattern.compile(":");
				String[] items = p.split(msgs[i].getMessageBody().toString());
				System.out.println("RECEIVED: " + msgs[i].getMessageBody().toString());
				for (int j = 0; j<items.length; j++){
					if (j==0){
						rcvGroupID = mySpamMeFacade.findGroupIDByName(items[j]);
						//rcvGroupID = Long.valueOf(items[j]);
					} else if (j==1){
						rcvSender = items[j];
					} else if (j==2){
						rcvMsg = items[j];
					}
				}
				rcvSender = msgs[i].getOriginatingAddress();
			}
			
			//Check to see if the group exists and if the sender exists in that group, 
			//and if so add the message to it
			if (doesGroupExist(rcvGroupID)) {
				//Retrieve the sender's name from the DB using their phone number and rcvGroupID
		        String senderName = mySpamMeFacade.getPersonNameViaPhone(senderPhoneNumber, rcvGroupID);
				//Append the senderName to the message
		        rcvMsg = senderName + ": " + rcvMsg;
		        
				//Create Message from groupID, phone number, and message
				Message m = mySpamMeFacade.createMessage(rcvGroupID, rcvSender, rcvMsg);
				mySpamMeFacade.addMessage(m);

				//Update the chat room if this group is currently being displayed
			}
			//DEBUG
			else {
				Toast.makeText(context, "Chat room does not exist", Toast.LENGTH_SHORT).show();
			}
			
			//toast the SMS message
			Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		}

	}
	
	/*
	* If the Chat group exists, returns true, if not, returns false
	*
	*/
	public boolean doesGroupExist(long groupExistsStatus) {
		return (groupExistsStatus!=(long)(-1));
	}
}
