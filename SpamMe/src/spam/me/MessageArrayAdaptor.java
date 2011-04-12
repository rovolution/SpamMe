				
package spam.me;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageArrayAdaptor extends ArrayAdapter<String> {
	private final String[] values;
	private LayoutInflater inflator;

	static class ViewHolder {
		public TextView text;
		public ImageView image;
	}

	public MessageArrayAdaptor(Context context, String[] values) {
		super(context, R.id.TextView01, values);
		this.values = values;
		inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return (position % 2 == 0) ? 0 : 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			//Set the individual row views (currently set based on whether it is odd/even)
			//In completed example, we will check the values[position] entry here to see
			//if the user has sent this message or if this is a received msg. We can 
			//check the first few characters of the values[position] string to determine 
			//whether the user sent it or someone else. 
			//"Me:" indicates we sent it
			//anything else otherwise
			if (getItemViewType(position) == 0) {
				rowView = inflator.inflate(R.layout.receivedmsgentry, null);
			} else {
				rowView = inflator.inflate(R.layout.sentmsgentry, null);
			}
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.TextView01);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.ImageView01);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		//Set the message text
		holder.text.setText(values[position]);
		//Return the row view
		return rowView;
	}
}

	