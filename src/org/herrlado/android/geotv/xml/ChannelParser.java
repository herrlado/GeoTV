package org.herrlado.android.geotv.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.herrlado.android.geotv.Channel;
import org.herrlado.android.geotv.LogoFactory;
import org.herrlado.android.geotv.Stream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class ChannelParser {

	private static final String TAG = "GeoTV.ChannelParser";

	public static ArrayList<Channel> parse(Context context, int resId) {
		ArrayList<Channel> list = new ArrayList<Channel>();
		Channel c = new Channel();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setValidating(false);
			XmlPullParser myxml = factory.newPullParser();
			InputStream raw = context.getAssets().open("channels.xml");
			myxml.setInput(raw, null);

			int eventType = myxml.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String name = myxml.getName();
					if(name.equals("channel")){
						c = new Channel();
						String title = myxml.getAttributeValue(null, "title");
						String description = myxml.getAttributeValue(null, "description");
						String url = myxml.getAttributeValue(null, "url");
						String logo = myxml.getAttributeValue(null, "logo");
						c.setTitle(title);
						c.setUrl(url);
						c.setLogo(LogoFactory.create(context, logo));
						c.setDescription(description);
						list.add(c);
					} else if(name.equals("stream")){
						Stream s = new Stream();
						String title = myxml.getAttributeValue(null, "title");
						String description = myxml.getAttributeValue(null, "description");
						String uri = myxml.getAttributeValue(null, "uri");
						String id = myxml.getAttributeValue(null, "id");
						s.setTitle(title);
						s.setDescription(description);
						s.setUri(uri);
						s.setId(id);
						c.getStreams().add(s);
					}
				}

//				} else if (eventType == XmlPullParser.END_TAG) {
//					Log.d(TAG, "In end tag = " + myxml.getName());
//
//				} else if (eventType == XmlPullParser.TEXT) {
//					Log.d(TAG, "Have text = " + myxml.getText());
//				}
				eventType = myxml.next();
			}
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

}
