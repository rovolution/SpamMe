package spam.me;

import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
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
		String sender = "";
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
				sender = msgs[i].getOriginatingAddress();
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
			
			//Check to see if the group exists, if so add the message to it
			if (doesGroupExist(rcvGroupID)) {
				//Create Message from groupID, phone number, and message
				Message m = mySpamMeFacade.createMessage(rcvGroupID, rcvSender, rcvMsg);
				mySpamMeFacade.addMessage(m);	
			}
			//DEBUG
			else {
				Toast.makeText(context, "Chat room does not exist", Toast.LENGTH_SHORT).show();
			}
			
			//toast the SMS message
			Toast.makeText(context, rcvMsg, Toast.LENGTH_SHORT).show();

			Toast.makeText(context, sender, Toast.LENGTH_SHORT).show();
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
