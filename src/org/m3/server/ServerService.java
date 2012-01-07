package org.m3.server;

import java.io.InputStream;

import org.apache.http.message.BasicNameValuePair;
import org.m3.http.HttpRetriever;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class ServerService {
	private final HttpRetriever httpRetriever;
	private final String SERVER_IP;
	
	public ServerService(Context context) {
		httpRetriever = new HttpRetriever();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    //SERVER_IP = prefs.getString(context.getString(R.string.server_ip), "192.168.0.100");
		SERVER_IP = "192.168.0.100";
	    
	}
	
	public void connectMaster(String videoName) {
		StringBuilder url = new StringBuilder();
		url.append("http://").append(SERVER_IP);
		httpRetriever.retrieve(url.toString(), new BasicNameValuePair("MASTER", "|"+videoName+"|"));
	}
	
	public void connectClient(String videoName) {
		StringBuilder url = new StringBuilder();
		url.append("http://").append(SERVER_IP);
		
		httpRetriever.retrieve(url.toString(), new BasicNameValuePair("CLIENT", "|"+videoName+"|"));
	}
	
	public String getVideoURI(String videoName) {
		StringBuilder url = new StringBuilder();
		url.append("http://").append(SERVER_IP).append("/").append(videoName).append("/");
		
		return url.toString();
	}
	
	public InputStream getStream(String videoName) {
		StringBuilder url = new StringBuilder();
		url.append("http://").append(SERVER_IP).append("/").append(videoName).append("/");
		
		return httpRetriever.retrieveStream(url.toString());
	}
	
}
