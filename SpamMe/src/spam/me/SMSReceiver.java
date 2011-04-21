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
		Long rcvGroupID;
		String rcvMsg;
		String rcvSender;
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
			System.out.println("RECEIVED: ");
				for (int j = 0; j<items.length; j++){
					if (j==0){
						rcvGroupID = Long.valueOf(items[j]);
					}
					else if (j==1){
						rcvSender = items[j];
					}
					else if (j==2){
						rcvMsg = items[j];
					}
					System.out.println(items[j]);
				}
				rcvSender = msgs[i].getOriginatingAddress();
			}
			
			
			
			//toast the SMS message
			Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
			
			Toast.makeText(context, sender, Toast.LENGTH_SHORT).show();
		}
		
	}

}
