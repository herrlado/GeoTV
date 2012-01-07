package org.m3.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.DateFormat;
import android.util.Log;


public class Utils {
    public static final String TAG = "Utils";
	public static final String CACHE_DIR_NAME = "__vmukti_v_cache";
	public enum VideoQuality { MOBILE, SD, HD };
	private static File cacheDir = null;
	private static boolean cacheDirCreated = false;
	private static SimpleDateFormat srcFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
    
    // ------------------------------ Adapt / Format ---------------------------
    
    public static String crop(String value, int howMuch) {
        return (value.length() <= howMuch) ? value : (value.substring(0, howMuch - 3) + "...");  
    }
    
    public static String quantity(Context context, int resId, int quantity) {
        return context.getResources().getQuantityString(resId, quantity, quantity);
    }
    
    public static String adaptDuration(long duration) {
    	final long remainder = duration % 60; 
        return ((duration - remainder) / 60) + ":" + ((remainder < 10) ? ("0" + remainder) : remainder); 
    }
    
    public static boolean adaptBoolean(int value) {
    	return (value == 0) ? false : true;
    }
    
    // gets date in format yyyy-MM-dd hh:mm:ss
    // returns in dd MMM yyyy hh:mm format
    public static String adaptDate(String date) {
        try {
            return DateFormat.format("dd MMM yyyy kk:mm", srcFormat.parse(date)).toString();
        } catch (ParseException e) {
            return "## #### #### ##:##";
        }
    }
    
    public static String[] extractTags(String source) {
        final List<String> result = new LinkedList<String>();
        for (String tag: source.split(",")) {
            if (tag.trim().length() > 0) result.add(tag.trim());
        }
        return result.toArray(new String[result.size()]);
    }
    
    public static String adaptTags(String[] tags, String noneText, String delimiter) {
    	if (tags.length == 0) return noneText;
    	if (tags.length == 1) return tags[0];
    	final StringBuffer result = new StringBuffer();
    	for (int i = 0; i < (tags.length - 1); i++) {
    		result.append(tags[i]).append(delimiter);
    	}
    	result.append(tags[tags.length - 1]);
    	return result.toString();
    }    
    
    public static String adaptTags(String[] tags, String noneText) {
    	return adaptTags(tags, noneText, " / ");
    }
    
    public static String[] stringArrayFromJson(JSONArray jsonArr) throws JSONException {
        final String[] array = new String[jsonArr.length()];
        for (int i = 0; i < jsonArr.length(); i++) {
            array[i] = jsonArr.getString(i);
        }
        return array;
    }
        
    // ------------------------------ Network ----------------------------------
    
    public static int lookupHost(String hostname) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            return -1;
        }
        byte[] addrBytes;
        int addr;
        addrBytes = inetAddress.getAddress();
        addr = ((addrBytes[3] & 0xff) << 24)
                | ((addrBytes[2] & 0xff) << 16)
                | ((addrBytes[1] & 0xff) << 8)
                |  (addrBytes[0] & 0xff);
        return addr;
    }        
    
    // ------------------------ Files: Streams / Cache -------------------------
    
    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }    
    
    public static void copyFile(File oldLocation, File newLocation) throws IOException {
        if ( oldLocation.exists( )) {
            BufferedInputStream  reader = new BufferedInputStream( new FileInputStream(oldLocation) );
            BufferedOutputStream  writer = new BufferedOutputStream( new FileOutputStream(newLocation, false));
            try {
                byte[]  buff = new byte[8192];
                int numChars;
                while ( (numChars = reader.read(  buff, 0, buff.length ) ) != -1) {
                    writer.write( buff, 0, numChars );
                }
            } catch( IOException ex ) {
                throw new IOException("IOException when transferring " + oldLocation.getPath() + " to " + newLocation.getPath());
            } finally {
                try {
                    if ( reader != null ){                      
                        writer.close();
                        reader.close();
                    }
                } catch( IOException ex ){
                    Log.e(TAG, "Error closing files when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() ); 
                }
            }
        } else {
            throw new IOException("Old location does not exist when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() );
        }
    }  
	
	public static File createCacheDir(Context context, String dirName)  {
		File preparedDir;
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			preparedDir = context.getDir(dirName, Context.MODE_PRIVATE);
            Log.i(TAG, "Cache dir initialized at SD card " + preparedDir.getAbsolutePath());
        } else {
        	preparedDir = context.getCacheDir();
            Log.i(TAG, "Cache dir initialized at phone storage " + preparedDir.getAbsolutePath());
        }
        if(!preparedDir.exists()) {
        	Log.i(TAG, "Cache dir not existed, creating");
        	preparedDir.mkdirs();
        }
        return preparedDir;
	}
    
	public static File getDefaultCacheDir(Context context)  {
		if (cacheDirCreated) return cacheDir;
		else {
			cacheDir = createCacheDir(context, CACHE_DIR_NAME);
	        cacheDirCreated = true;
	        return cacheDir;
		}
	}
	
	public static File newTempFile(Context context, String prefix, String suffix) throws IOException {
		return File.createTempFile(prefix, suffix, getDefaultCacheDir(context));
	}
	
	public static long computeFreeSpace() {
	    File dataDir = Environment.getDataDirectory();
        StatFs stat = new StatFs(dataDir.getPath());
        return stat.getAvailableBlocks() * stat.getBlockSize();
	}
	
}
