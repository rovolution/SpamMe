package spam.me;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{
	private SpamMeFacade mySpamMeFacade;
	private SharedPreferences preferences;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		mySpamMeFacade = new SpamMeFacade(context);
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		
		//Get the SMS message passed in
		Bundle bundle = intent.getExtras();
		SmsMessage [] msgs = null;
		String str = "";
		String senderPhoneNumber = "";
		String senderNumber = "";
		Long rcvGroupID = (long) -1;
		String rcvMsg = "";
		String rcvSender = "";
		String rcvGroupName = "";
		
		String [] newMemberData;
		Person newMember;
		GroupChat theGroup;

		if (bundle != null){
			//Retrieve the SMS message
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i=0; i<msgs.length; i++){
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				str += "SMS from " + msgs[i].getOriginatingAddress();
				senderNumber = msgs[i].getOriginatingAddress();
				//Set the sender phone number
				//senderPhoneNumber = msgs[i].getOriginatingAddress().substring(7);
				senderPhoneNumber = msgs[i].getOriginatingAddress();
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
						rcvGroupName = items[j];
						rcvGroupID = mySpamMeFacade.findGroupIDByName(items[j]);
						//rcvGroupID = Long.valueOf(items[j]);
					} else if (j==1){
						rcvSender = items[j];
						if (rcvSender.contains("spamMeAdd"))
						{
							theGroup = new GroupChat();
							theGroup.setGroupId(rcvGroupID);
							theGroup.setGroupName(items[j]);
							
							newMember = new Person();
							newMemberData = rcvSender.substring(rcvSender.indexOf("=")+1).split("=");
							newMember.setPhoneNum(newMemberData[0]);
							newMember.setName(newMemberData[1]);
							mySpamMeFacade.addFriend(null, theGroup, newMember);
						}
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
			}
			else {
				Toast.makeText(context, "Chat room does not exist", Toast.LENGTH_SHORT).show();
			}
			
			//Check to see if the status is set
			//Send a message if the status is ACTIVE
			if (mySpamMeFacade.getStatus(preferences)){
				//Debug
				//Toast.makeText(context, "STATUS IS ACTIVE and Status: " + mySpamMeFacade.getStatusText(preferences), Toast.LENGTH_LONG).show();
				//Toast.makeText(context, "NUMBER: " + senderNumber, Toast.LENGTH_LONG).show();
				String[] senderNumArray = new String [1]; 
				senderNumArray[0] = senderNumber;
				//Formatting a message to send the status
				GroupChat gc = 	mySpamMeFacade.getGroupChat(rcvGroupID);
				String statusMsg = gc.getGroupName() +
				":" + "my name" + 
				":" + "(AUTO-RESPONSE) "+ mySpamMeFacade.getStatusText(preferences);
				
				mySpamMeFacade.sendMsg(statusMsg, senderNumArray, rcvGroupID);
			}
			System.out.println("Received message: " + rcvMsg);
			//Check the message to see if it's a LEAVE message
			if (rcvMsg.contains("I'm leaving " + rcvGroupName)){
				//Remove member
				String senderName = mySpamMeFacade.getPersonNameViaPhone(senderPhoneNumber, rcvGroupID);
				mySpamMeFacade.removeMember(senderName, rcvGroupName);
				
			}
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
