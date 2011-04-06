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
}
