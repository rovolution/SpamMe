package spam.me;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SpamMeDb extends SQLiteOpenHelper{
	//for logging/debugging purposes:
	private static final String TAG = "SpamMeDB";
	
	
	private static SQLiteDatabase Db;
	private static final int DATABASE_VERSION = 14;
	private static final String DATABASE_NAME = "spamMeDB";
	private final Context spamMeCtx;
	
	/**
	 * Table creation statements
	 * Creating 3 tables: Group, Messages, and GroupMembers
	 */
	private static final String TABLE_CREATE_GROUPS =
	    	"create table groups (groupsID integer primary key autoincrement, "
	        + "name text not null);";
	 
	private static final String TABLE_CREATE_MESSAGES =
    	"create table messages (id integer primary key autoincrement, "
        + "groupID integer not null,"
        + "sender text not null,"
        + "message text not null,"
        + "FOREIGN KEY(groupID) REFERENCES groups(groupsID));";
	
	private static final String TABLE_CREATE_GROUPMEMBERS =
    	"create table groupmembers (id integer primary key autoincrement, "
        + "groupID integer not null, "
        + "member text not null, "
        + "phoneNumber text not, "
        + "FOREIGN KEY(groupID) REFERENCES groups(groupsID));";
	
	/**
	 * SpamMe Database creation statement
	 *
	 */
	private static final String DATABASE_CREATE =
        TABLE_CREATE_GROUPS +";"+ TABLE_CREATE_MESSAGES + ";" + TABLE_CREATE_GROUPMEMBERS + ";";
	
	private static final String TABLE_GROUPS = "groups";
	private static final String TABLE_MESSAGES = "messages";
	private static final String TABLE_GROUPMEMBERS = "groupmembers";
	private static final String KEY_NAME = "name";
	private static final String KEY_GROUPSID = "groupsID";
	private static final String KEY_ID = "id";
	private static final String KEY_MEMBER = "member";
	private static final String KEY_PHONENUMBER = "phoneNumber";
	private static final String KEY_GROUPID = "groupID";
	
	 @Override
	public void onCreate(SQLiteDatabase db){
		 try{
         	db.execSQL(TABLE_CREATE_GROUPS);
         	db.execSQL(TABLE_CREATE_MESSAGES);
         	db.execSQL(TABLE_CREATE_GROUPMEMBERS);
         	//DEBUG
         	Log.i(TAG, "DB TABLE Successfully");
         }
         catch (SQLException e){
         	Log.i(TAG, "DB Create failed " + e.getMessage() );
         }
		
	}
	 
	 @Override
     /**
      * Called when the database needs to be updated. All tables will be dropped.
      *
      */
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w(TAG, "Upgrading database from  " + oldVersion + " to "
        		 + newVersion + ", which will destroy all old data");
         db.execSQL("DROP TABLE IF EXISTS groups");
         db.execSQL("DROP TABLE IF EXISTS messages");
         db.execSQL("DROP TABLE IF EXISTS groupmembers");
         onCreate(db);
     }
	
	 /**
	  * Constructor
	  */
	 public SpamMeDb(Context ctx){
		 super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	        //DB Test   
		 
	        try{open();} //Opens or creates the database  
	        catch(Exception e1)
	        {
	      	  Log.i(TAG, "DB open EXCEPTION");  
	      	  e1.printStackTrace();  
	        }
	    	this.spamMeCtx = ctx;
	    	
	 }
	 
	 public void open() throws SQLException {
	        setDb(getWritableDatabase());

	    }

	 public void close() {
	       getDb().close();
	    }
	    
	 /**
	  * Setter for Db
	  * @return Db
	  */
	 private static void setDb(SQLiteDatabase database) {
		 Db = database;
	 }
	 
	 /**
	  * Getter for Db
	  * @return the mDb
	  */
	 public static SQLiteDatabase getDb() {
		 	return Db;
	 }
	public void addMessage(){
		
	}
	/**
	 * Method adds the new group name to the database
	 * If the name already exists or empty string returns -1
	 * If the name was added successfully returns 1
	 * If the there was an error throws SQLException
	 */
	public int addGroupChat(String name)throws SQLException{
		Log.i("SpamMeDB: ", "addNewGroupChat name: " + name);
		if (name == ""){
			return -1;
		}
		Cursor mCursor = null;
		//Check to see if the name is already in the database
		mCursor = getDb().query(true, TABLE_GROUPS, new String[] {KEY_GROUPSID}, KEY_NAME + "=" + "'" + name + "'",
				null, null, null, null, null);
		
		//Name already exists don't create a new entry
		if (mCursor != null && mCursor.moveToFirst()){
			return -1;
		}
		
		//Name doesn't exist, create a new entry
		else{
			long rowID;
	    	ContentValues inputValue = new ContentValues();
	    	inputValue.put(KEY_NAME, name);
	    	rowID = getDb().insert(TABLE_GROUPS, null, inputValue);
				mCursor.close();
				mCursor.deactivate();
	    	if (rowID >= 0){
	    		return 1;
	    	}
	    	else{
	    		throw new SQLException();
	    	}
		}
		
	}
	/**
	 * Removes the name from the Groups table
	 * Returns true on sucess and false on failure
	 */
	public boolean removeGroupChat(String removeName){
		Cursor mCursor = null;
		//Check to see if the name is already in the database
		mCursor = getDb().query(false, TABLE_GROUPS, new String[] {KEY_GROUPSID}, KEY_NAME + "=" + "'" + removeName + "'",
				null, null, null, null, null);
		
		//Name exists delete Group Name
		if (mCursor != null && mCursor.moveToFirst()){
			Log.i("SpamMeDB: ", "removeGroupChat name: " + removeName); 
			int deleteSuccess = getDb().delete(TABLE_GROUPS, KEY_GROUPSID + "==" + (mCursor.getInt(mCursor.getColumnIndex(KEY_GROUPSID))), null);
			
			if (deleteSuccess == 1){
				return true;
			}
			return false;
		}
		return false;
		
	}
	
	/**
	 * Method adds the new member to the database (Table_GroupMembers)
	 * If the name already exists or empty string returns -1
	 * If the name was added successfully returns 1
	 * If the there was an error throws SQLException
	 * NEEDS TO BE CHECKED
	 */
	public int addMember(GroupChat group, Person newPerson){
		Cursor mCursor = null;
		
		//Check to see if the name is already in the database
		mCursor = getDb().query(false, 
				TABLE_GROUPMEMBERS, new String[] {KEY_ID}, 
				KEY_MEMBER + "=" + "'" + newPerson.getName() + "'" + "and" + KEY_GROUPID + "=" + group.getGroupId(),
				null, null, null, null, null);
		
		//Name already exists don't create a new entry
		if (mCursor != null && mCursor.moveToFirst()){
			return -1;
		}
		
		//Name doesn't exist, create a new entry
		else{
			long rowID;
	    	ContentValues inputValue = new ContentValues();
	    	inputValue.put(KEY_MEMBER, newPerson.getName());
	    	inputValue.put(KEY_PHONENUMBER, newPerson.getPhoneNum());
	    	inputValue.put(KEY_GROUPID, group.getGroupId());
	    	rowID = getDb().insert(TABLE_GROUPMEMBERS, null, inputValue);
				mCursor.close();
				mCursor.deactivate();
	    	if (rowID >= 0){
	    		return 1;
	    	}
	    	else{
	    		throw new SQLException();
	    	}
		}
	}
	public void removeMember(Person removePerson){
		
	}
	public GroupChat getGroupChat(int groupId){
		return null;	
	}
	
}
