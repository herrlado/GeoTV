package org.herrlado.android.geotv;

import java.util.ArrayList;

public class Channel extends HasDescription {

	private ArrayList<Stream> streams = new ArrayList<Stream>();

	private Logo logo;
	
	private String url;
	
	/**
	 * id of the current select stream
	 */
	private String current;

	public ArrayList<Stream> getStreams() {
		return streams;
	}

	public String getCurrent() {
		return current;
	}

	public void setStreams(ArrayList<Stream> streams) {
		this.streams = streams;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}
	
	
}
