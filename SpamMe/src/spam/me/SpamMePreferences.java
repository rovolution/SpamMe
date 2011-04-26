package spam.me;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SpamMePreferences extends PreferenceActivity {
	public SpamMePreferences () {
		
	}
	
	public void setStatusText(SharedPreferences preferences, String statusMsg)
	{
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("statusMessage", statusMsg);
		editor.commit();
	}
	/**
	 * Method sets the state of the status (inactive or active) 
	 * Parameter of value "true" --> active
	 * Parameter of value "false" --> inactive
	 * @param preferences
	 * @param state
	 */
	public void setStatusState(SharedPreferences preferences, Boolean state){
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("statusState", state);
		editor.commit();
	}
	
	/**
	 * Method returns the state of the status
	 * @param p
	 * @return boolean
	 * 		true = active
	 * 		false = inactive
	 */
	public boolean getStatusState(SharedPreferences p){
		boolean state = p.getBoolean("statusState", false);
		return state;
	}
}
