package org.herrlado.android.geotv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChannelSelectAdapter extends ArrayAdapter<Stream> {

	private Context context;
	private LayoutInflater inflater;

	public ChannelSelectAdapter(Context context) {
		super(context, android.R.layout.simple_expandable_list_item_2);

		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	private static class ChannelHolder {
		private TextView title;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ChannelHolder channelHolder = null;
		// recycle view?
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.channelselectoritem, null);
			channelHolder = new ChannelHolder();
			channelHolder.title = (TextView) view.findViewById(R.id.stream);
			view.setTag(channelHolder);
		} else {
			channelHolder = (ChannelHolder) view.getTag();
		}

		Stream s = getItem(position);

		channelHolder.title.setText(s.getTitle());

		return view;
	}
}
