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
	private static final String TAG = "ICUPDb";
	
	
	private static SQLiteDatabase Db;
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "spamMeDB";
	private final Context spamMeCtx;
	
	/**
	 * Table creation statements
	 * Creating 3 tables: Group, Messages, and GroupMembers
	 */
	private static final String TABLE_CREATE_GROUPS =
	    	"create table groups (id integer primary key autoincrement, "
	        + "name text not null);";
	 
	private static final String TABLE_CREATE_MESSAGES =
    	"create table messages (id integer primary key autoincrement, "
        + "groupID integer not null, " 
        + "sender text not null, "
        + "message text not null, "
        + "FORIEGN KEY(groupID) REFERENCES group(id);";
	
	private static final String TABLE_CREATE_GROUPMEMBERS =
    	"create table groupmembers (id integer primary key autoincrement, "
        + "groupID integer not null, "
        + "member text not null, "
        + "FOREIGN KEY(groupID) REFERENCE group(id);";
	
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
	
	 @Override
	public void onCreate(SQLiteDatabase db){
		 try{
         	Db.execSQL(TABLE_CREATE_GROUPS);
         	//Db.execSQL(TABLE_CREATE_MESSAGES);
         	//Db.execSQL(TABLE_CREATE_GROUPMEMBERS);
         	//DEBUG
         	Log.i(TAG, "DB TABLE Successfully");
         }
         catch (SQLException e){
         	Log.i(TAG, "DB Create failed");
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
         db.execSQL("DROP TABLE IF EXISTS group");
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
	 * If the name already exists returns -1
	 * If the name was added successfully returns 1
	 * If the there was an error throws SQLException
	 */
	public int addGroupChat(String name)throws SQLException{
		Cursor mCursor = null;
		//Check to see if the name is already in the database
		mCursor = getDb().query(true, TABLE_GROUPS, new String[] {"id"}, KEY_NAME + "=" + "'" + name + "'",
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
	
	public void removeGroupChat(){
		
	}
	public void addMember(Person newPerson){
		
	}
	public void removeMember(Person removePerson){
		
	}
	public GroupChat getGroupChat(int groupId){
		return null;	
	}
	
}
