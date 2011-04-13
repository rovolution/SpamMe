package spam.me;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class GroupChatTabHostUI extends Activity
{
	//SpamMeFacade - API for application
	private SpamMeFacade spamMeFacade;

	GroupChat myGroupChat;
	EditText inputPhoneNo;
	EditText inputMsg;
	private ListView list;
	private TextView errorMsg;
	public int groupID;

	protected PopupWindow popup;

	/** Called when the activity is first created. */ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        spamMeFacade = new SpamMeFacade(this);
        
        //Getting the groupID from CreateGroupChatUI
        Bundle extras = getIntent().getExtras();
        extras.getInt("newGroupChatID");
        
        //Setting xml file for UI
        setContentView(R.layout.groupchattabhost);
        
        //Initializing the edit texts
        inputPhoneNo = (EditText)findViewById(R.id.PhoneNoTxt);
        inputMsg = (EditText)findViewById(R.id.messageTxt);
        
        //Setting up tabs
        TabHost tabHost = (TabHost) this.findViewById(R.id.groupchattabhost);  // The activity TabHost
        doTabSetup(tabHost);
        tabHost.setCurrentTab(0);
        
		//Dummy data for messages (NOTE: Later replace with read messages from DB)
		String[] messages = new String[] { "Bob: werwerwerwerwer", "Me: Blue screen of death", "Me: Loading forever", "Suse: wooho",
		"Ubuntu: sudo rm *" };
		//Find the messageList and msgListEmpty error msg
		list=(ListView)findViewById(R.id.msgList);
		errorMsg=(TextView)findViewById(R.id.msgListEmpty);
		//Check the list to see if it is empty too see whether to display it or not
		setListVisibility(messages.length, list, errorMsg);
		//By using setAdpater method in listview we an add members array in memberList.
		ArrayAdapter<String> msgAdapter = new MessageArrayAdaptor(this, messages);
		list.setAdapter(msgAdapter);

		//Dummy data for members list
		String[] members = new String[] { "Bob", "Windows7", "Tim", "Suse",
				"Ubuntu", "Solaris", "Android", "iPhone", "Billy Bob","Sammyboy" };
		//Find the memberList
		list=(ListView)findViewById(R.id.memberList);
		errorMsg=(TextView)findViewById(R.id.memberListEmpty);
		//Check the list to see if it is empty too see whether to display it or not
		setListVisibility(members.length, list, errorMsg);
		//By using setAdpater method in listview we an add members array in memberList.
		list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , members));
		
	}

	//Handler for SendSMS button
	public void SendSMSButtonClicked(View v){
		Toast.makeText(getBaseContext(), 
				"Send SMS got clicked", 
				Toast.LENGTH_SHORT).show();

		String number = inputPhoneNo.getText().toString();
		String msg = inputMsg.getText().toString();

		if (number.length()>0 && msg.length()>0){
			/*
			Bundle bundle = new Bundle();
			bundle.putString("message", msg); 
			bundle.putString("phoneNumber", number);
			Intent phoneInterfaceIntent = new Intent(this.getApplicationContext(), PhoneInterface.class);
			phoneInterfaceIntent.putExtras(bundle);
			startActivityForResult(phoneInterfaceIntent, 0);
			 */
			spamMeFacade.sendMsg(msg, number);
			//sendSMS(msg, number);
		}
		else {
			Toast.makeText(getBaseContext(), 
					"Please enter both number and message.", 
					Toast.LENGTH_SHORT).show();
		}
	}

	public void addNewMemberClicked(View v){
		ArrayList<Person> myHomies = new ArrayList<Person>();

		myHomies = (ArrayList<Person>) getContactList();
		if (myHomies != null || !myHomies.isEmpty())
		{
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.contactspopup, null);

			int popupHeight = (int) ((getApplicationContext().getResources().getDisplayMetrics().heightPixels) - 25);
			int popupWidth = (int) ((getApplicationContext().getResources().getDisplayMetrics().widthPixels));

			popup = new PopupWindow(v, popupWidth, popupHeight, false);
			popup.setOutsideTouchable(false);
			popup.showAtLocation(findViewById(R.id.groupchattabhost), Gravity.CENTER, 0, 0);

			ListView contactList = (ListView)popup.getContentView().findViewById(R.id.contactList);
			ArrayAdapter myAdapter = new ContactAdapter(this, v.getContext(), R.layout.contactitem, myHomies);
			contactList.setAdapter(myAdapter);
			contactList.setVisibility(View.VISIBLE);
			
			TextView emptyContacts =(TextView) popup.getContentView().findViewById(R.id.contactListEmpty);
			emptyContacts.setVisibility(View.GONE);

			spamMeFacade.addFriend(v);
		}
		else
		{
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.contactspopup, null);

			int popupHeight = (int) ((getApplicationContext().getResources().getDisplayMetrics().heightPixels) - 25)/2;
			int popupWidth = (int) ((getApplicationContext().getResources().getDisplayMetrics().widthPixels))/2;

			popup = new PopupWindow(v, popupWidth, popupHeight, false);
			popup.setOutsideTouchable(false);
			popup.showAtLocation(findViewById(R.id.groupchattabhost), Gravity.CENTER, 0, 0);

			ListView contactList = (ListView) popup.getContentView().findViewById(R.id.contactList);
			TextView emptyContacts =(TextView) popup.getContentView().findViewById(R.id.contactListEmpty);

			contactList.setVisibility(View.GONE);
			emptyContacts.setVisibility(View.VISIBLE);
			System.out.println("myHomies is null");
		}
	}

	public List<Person> getContactList(){
		List<Person> contactList = new ArrayList<Person>();

		Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
		String[] PROJECTION = new String[] {
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER,
		};
		String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
		Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, SELECTION, null, null);


		if (contacts.getCount() > 0)
		{
			while(contacts.moveToNext()) {
				Person aContact = new Person();
				int idFieldColumnIndex = 0;
				int nameFieldColumnIndex = 0;
				int numberFieldColumnIndex = 0;

				String contactId = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));

				nameFieldColumnIndex = contacts.getColumnIndex(PhoneLookup.DISPLAY_NAME);
				if (nameFieldColumnIndex > -1)
				{
					aContact.setName(contacts.getString(nameFieldColumnIndex));
				}

				PROJECTION = new String[] {Phone.NUMBER};
				final Cursor phone = managedQuery(Phone.CONTENT_URI, PROJECTION, Data.CONTACT_ID + "=?", new String[]{String.valueOf(contactId)}, null);
				if(phone.moveToFirst()) {
					while(!phone.isAfterLast())
					{
						numberFieldColumnIndex = phone.getColumnIndex(Phone.NUMBER);
						if (numberFieldColumnIndex > -1)
						{
							aContact.setPhoneNum(phone.getString(numberFieldColumnIndex));
							phone.moveToNext();
							contactList.add(aContact);
						}
					}
				}
				phone.close();
			}

			contacts.close();
		}
		
		return contactList;
	}

	/*
	public void sendSMS(String msg, String number){
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, GroupChatTabHostUI.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(number, null, msg, pi, null);
	}
	 */
	/*
	 * Set up the tabs in the tab host
	 */
	private void doTabSetup(TabHost tHost){
		Resources res = getResources(); // Resource object to get Drawables

		TabHost.TabSpec spec;  // Reusable TabSpec for each tab
		tHost.setup();

		// Set the tabs to change views when clicked
		spec = tHost.newTabSpec("messages");
		spec.setIndicator("Messages", res.getDrawable(R.drawable.icon));
		spec.setContent(R.id.messagetab);
		tHost.addTab(spec);

		spec = tHost.newTabSpec("members");
		spec.setIndicator("Members", res.getDrawable(R.drawable.icon));
		spec.setContent(R.id.memberstab);
		tHost.addTab(spec);	

		spec = tHost.newTabSpec("options");
		spec.setIndicator("Options", res.getDrawable(R.drawable.icon));
		spec.setContent(R.id.optionstab);
		tHost.addTab(spec);		
	}

	/*
	 * Checks to see if a given list has items. If so, it will display the list in the XML
	 * If not, the appropriate error message will be displayed instead indicating an empty list
	 */
	private void setListVisibility(int hasItems, ListView inputList, TextView inputErrorMsg) {
		//If there ARE NO messages in the chat room:
		if (hasItems == 0) {
			//Make the message list invisible
			inputList.setVisibility(View.INVISIBLE);
			//Make the msgListEmpty visible
			inputErrorMsg.setVisibility(View.VISIBLE);
		}
		//If there ARE messages in the chat room:
		else {
			//Make the message list visible
			inputList.setVisibility(View.VISIBLE);
			//Make the msgListEmpty invisible
			inputErrorMsg.setVisibility(View.INVISIBLE);
		}		
	}
	
	
	//Used to expand a message bubble's text if it is clicked on
    public void expandMsgClicked(View v) {
      
    	//Prepare the alert box
    	AlertDialog.Builder fullMsgDisplay = new AlertDialog.Builder(this);

    	//Extract the text from the message bubble
    	TextView msgExpand = (TextView)v;
    	String msgContent = (String) msgExpand.getText();

    	//Set the message to display
    	fullMsgDisplay.setMessage(msgContent);

    	//Add a confirm button to the alert box and assign a click listener
    	fullMsgDisplay.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
    		//Click listener on the alert box
    		public void onClick(DialogInterface arg0, int arg1) {
    			//Do nothing after the user clicks "OK"
    		}
    	});
    	//Display to screen
    	fullMsgDisplay.show();   	
     }
}
