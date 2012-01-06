package org.herrlado.android.geotv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelAdapter extends ArrayAdapter<Channel> {

	private Context context;

	private static LayoutInflater inflater = null;

	public ChannelAdapter(Context context) {
		super(context, android.R.layout.simple_expandable_list_item_2);
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	private static class ChannelHolder {
		private TextView title;
		private ImageView logo;
		private TextView description;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ChannelHolder channelHolder = null;
		// recycle view?
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.channel, null);
			channelHolder = new ChannelHolder();
			channelHolder.title = (TextView) view.findViewById(R.id.title);
			channelHolder.description = (TextView) view
					.findViewById(R.id.description);
			channelHolder.logo = (ImageView) view.findViewById(R.id.logo);
			view.setTag(channelHolder);
		} else {
			channelHolder = (ChannelHolder) view.getTag();
		}

		Channel c = getItem(position);

		channelHolder.title.setText(c.getTitle());
		channelHolder.description.setText(c.getDescription());
		Logo logo = c.getLogo();
		if(logo instanceof RLogo){
			channelHolder.logo.setImageResource(((RLogo)logo).logo);	
		}
		return view;
	}
}
