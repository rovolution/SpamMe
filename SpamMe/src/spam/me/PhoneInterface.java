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

public class PhoneInterface extends Activity {
	public PhoneInterface(){
		
	}
	
	public void sendSMS(String msg, String number){
		/*
		Bundle bundle = this.getIntent().getExtras(); 
		String msg = bundle.getString("message");
		String number = bundle.getString("phoneNumber");
		Toast.makeText(getBaseContext(), 
				"Msg is: " + msg, 
				Toast.LENGTH_SHORT).show();
		*/
		//PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, PhoneInterface.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(number, null, msg, null, null);
	}
	public void receiveSMS(){
	}
}
