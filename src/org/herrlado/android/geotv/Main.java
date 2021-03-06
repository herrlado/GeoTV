package org.herrlado.android.geotv;

import java.util.ArrayList;
import java.util.List;

import org.herrlado.android.geotv.xml.ChannelParser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.flazr.io.flv.FlvWriter;
import com.flazr.rtmp.RtmpMessage;
import com.flazr.rtmp.client.ClientOptions;
import com.flazr.rtmp.client.RtmpClient;
import com.flazr.util.Utils;

public class Main extends ListActivity implements OnItemClickListener,
		OnItemLongClickListener {

	private static final String TAG = "GeoTV";

	ArrayList<Channel> channels = new ArrayList<Channel>();

	ChannelAdapter adapter;

	// public Main() {
	//
	// Channel r = new Channel();
	// r.setTitle("áƒ áƒ£áƒ¡áƒ—áƒ�áƒ•áƒ˜ 2");
	// r.setLogo(new RLogo(R.drawable.rustavi2));
	// r.setDescription("Rustavi 2 description text here");
	// Stream s = new Stream();
	// s.setTitle("áƒ¡áƒ�áƒ¥áƒ�áƒ áƒ—áƒ•áƒ”áƒšáƒ�");
	// s.setUri("mms://208.75.229.18/live1");
	// s.setDescription("áƒ™áƒ�áƒ áƒ’áƒ�áƒ“ áƒ›áƒ£áƒ¨áƒ�áƒ�áƒ‘áƒ¡ áƒ¡áƒ�áƒ¥áƒ�áƒ áƒ—áƒ•áƒ”áƒšáƒ�áƒ“áƒ�áƒœ");
	// r.getStreams().add(s);
	//
	// channels.add(r);
	//
	// r = new Channel();
	// r.setLogo(new RLogo(R.drawable.firsttv));
	// r.setTitle("áƒžáƒ˜áƒ áƒ•áƒ”áƒšáƒ˜ áƒ�áƒ áƒ®áƒ˜");
	// r.setDescription("áƒ¡áƒ�áƒ¥áƒ�áƒ áƒ—áƒ•áƒ”áƒšáƒ�áƒ¡ áƒ¡áƒ�áƒ®áƒ”áƒšáƒ›áƒ¬áƒ˜áƒ¤áƒ� áƒ�áƒ áƒ®áƒ˜");
	// s = new Stream();
	//
	// s = new Stream();
	// s.setTitle("áƒ�áƒ›áƒ”áƒ áƒ˜áƒ™áƒ�");
	// s.setUri("mms://208.75.229.58/1tv");
	// s.setDescription("áƒ™áƒ�áƒ áƒ’áƒ�áƒ“ áƒ›áƒ£áƒ¨áƒ�áƒ�áƒ‘áƒ¡ áƒ�áƒ›áƒ”áƒ áƒ˜áƒ™áƒ˜áƒ“áƒ�áƒœ áƒ�áƒœ áƒ”áƒ•áƒ áƒ�áƒžáƒ˜áƒ“áƒ�áƒœ");
	// r.getStreams().add(s);
	//
	// s = new Stream();
	// s.setTitle("áƒ¡áƒ�áƒ¥áƒ�áƒ áƒ—áƒ•áƒ”áƒšáƒ�");
	// s.setUri("mms://80.241.246.154/1tv");
	// s.setDescription("áƒ™áƒ�áƒ áƒ’áƒ�áƒ“ áƒ›áƒ£áƒ¨áƒ�áƒ�áƒ‘áƒ¡ áƒ¡áƒ�áƒ¥áƒ�áƒ áƒ—áƒ•áƒ”áƒšáƒ�áƒ“áƒ�áƒœ");
	// r.getStreams().add(s);
	//
	// channels.add(r);
	//
	// r = new Channel();
	// r.setLogo(new RLogo(R.drawable.imeditv));
	// r.setTitle("áƒ˜áƒ›áƒ”áƒ“áƒ˜");
	// r.setDescription("áƒ˜áƒ›áƒ”áƒ“áƒ˜ áƒ˜áƒ§áƒ� áƒ™áƒ�áƒ áƒ’áƒ˜ áƒ�áƒ áƒ®áƒ˜?!");
	//
	// s = new Stream();
	// s.setTitle("rtmp");
	// s.setUri("rtmp://s1.iptv.ge/live-imedi/imedi.sdp");
	// s.setDescription("áƒ™áƒ�áƒ áƒ’áƒ�áƒ“ áƒ›áƒ£áƒ¨áƒ�áƒ�áƒ‘áƒ¡ áƒ�áƒ›áƒ”áƒ áƒ˜áƒ™áƒ˜áƒ“áƒ�áƒœ áƒ�áƒœ áƒ”áƒ•áƒ áƒ�áƒžáƒ˜áƒ“áƒ�áƒœ");
	// r.getStreams().add(s);
	//
	// channels.add(r);
	//
	// }

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		channels = ChannelParser.parse(this, -1);

		adapter = new ChannelAdapter(this);
		for (Channel c : channels) {
			adapter.add(c);
		}
		ListView lv = getListView();
		lv.setOnItemClickListener(this);
		lv.setOnItemLongClickListener(this);
		this.setListAdapter(this.adapter);
		registerForContextMenu(lv);

		List<Channel> channels = ChannelParser.parse(this, -1);
		System.out.println(channels);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {

		final Dialog dialog = new Dialog(this);
		dialog.setTitle("Select stream:");
		dialog.setContentView(R.layout.channelselector);
		ListView lv = (ListView) dialog.findViewById(R.id.channels);
		ChannelSelectAdapter cAdapter = new ChannelSelectAdapter(this);
		final Channel c = adapter.getItem(position);
		for (Stream s : c.getStreams()) {
			cAdapter.add(s);
		}
		lv.setAdapter(cAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				doClick(c, position);
				dialog.dismiss();
			}
		});
		
		dialog.show();

		return parent.showContextMenu();
		// Intent myIntent = new Intent(view.getContext(), DetailView.class);
		// startActivityForResult(myIntent, 0);
		// return true;
	}

	MyAsyncTask loader;

	RtmpClient client;

	public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

		Stream stream;

		public MyAsyncTask(Stream s) {
			stream = s;
		}

		protected Void doInBackground(Void... params) {
			ClientOptions options = new ClientOptions();
			options.parseUrl(stream.getUri());
			options.setSaveAs("/sdcard/" + options.getStreamName() + ".flv");
			options.setSwfSize(99201);
			options.setSwfHash(Utils
					.fromHex("32B4CC51D05F5D6670D3EB26697F77DCF6150DF0B48132756B969ABCE854DDF0"));
			// final Intent intent = new Intent(Intent.ACTION_VIEW);
			// intent.setDataAndType(Uri.parse(options.getSaveAs()), "video/*");
			// startActivity(intent);
			// options.setWriterToSave(writerToSave)
			// options.setWriterToSave(new
			// FlvWriter(options.getStreamName()){@Override
			// public void write(RtmpMessage message) {
			// TODO Auto-generated method stub
			// super.write(message);
			// }});
			client = new RtmpClient(options);
			return (Void) null;
		}
	}

	private void streamRtmp(final Stream stream) {
		loader = new MyAsyncTask(stream);
		loader.execute((Void) null);
	}

	@Override
	protected void onPause() {
		if (loader != null && loader.isCancelled() == false) {
			loader.cancel(true);
		}
		super.onPause();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Channel c = adapter.getItem(arg2);
		doClick(c, 0);
	}

	public void doClick(Channel c, int index) {
		Stream s = c.getStreams().get(index);

		if (s.getUri().startsWith("rtmp")) {
			streamRtmp(s);
			return;
		}
		String uri = s.getUri();
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse(uri), "video/*");
		try {
			startActivity(intent);
		} catch (Exception ex) {
			Log.w(TAG, ex);
			new AlertDialog.Builder(this).setMessage(ex.getMessage())
					.setPositiveButton(android.R.string.ok, null).create()
					.show();
		}
	}
}