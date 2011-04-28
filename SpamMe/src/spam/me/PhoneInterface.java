package spam.me;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.widget.Toast;

public class PhoneInterface {
	public PhoneInterface(){
		
	}
	
	public void sendSMS(String msg, String [] number){

		SmsManager sms = SmsManager.getDefault();
		for (int i=0; i < number.length; i++){
			System.out.println("Number being sent " + number[i]);
			sms.sendTextMessage(number[i], null, msg, null, null);
		}		
	}
}
