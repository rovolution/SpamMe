package spam.me;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactAdapter extends ArrayAdapter<Person> {
	private ArrayList<Person> items;
	private Activity parentActivity;
	
	// Constructor
	public ContactAdapter(Activity parent, Context context, int textViewResourceId, ArrayList<Person> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.parentActivity = parent;
	}


	// This function actually places the content from the Event Item objects onto the ListView.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Person o = items.get(position);
		LayoutInflater vi = (LayoutInflater)parentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.contactitem, null);

		if (o != null) {
			TextView contactName = (TextView) v.findViewById(R.id.contact_name);
			TextView contactNum = (TextView) v.findViewById(R.id.contact_phone_number);
			if (contactName != null)
			{
				contactName.setText(o.getName());
			}
			if (contactNum != null)
			{
				contactNum.setText(o.getPhoneNum());
			}
		}

		return v;
	}
}
