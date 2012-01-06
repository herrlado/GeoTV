package org.herrlado.android.geotv;

public class Log {

	private static final String TAG  = "GeoTV";
	
	public static void debug(String msg, Object... args){
		android.util.Log.d(TAG, String.format(msg, args));
	}
	
	
	public static void info(String msg, Object... args){
		android.util.Log.i(TAG, String.format(msg, args));
	}
}
