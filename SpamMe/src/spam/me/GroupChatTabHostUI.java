package spam.me;

import spam.me.R;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class GroupChatTabHostUI extends Activity{
	/** Called when the activity is first created. */ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Setting xml file for UI
        setContentView(R.layout.groupchattabhost);
        
        //Setting up tabs
        TabHost tabHost = (TabHost) this.findViewById(R.id.groupchattabhost);  // The activity TabHost
        doTabSetup(tabHost);

        tabHost.setCurrentTab(0);
    }
	
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
}
