package org.m3.http;

import java.io.IOException;
import java.io.InputStream;


public class Utils {

	public static void closeStreamQuietly(InputStream inputStream) {
		try {
			if(inputStream != null) {
				inputStream.close(); 
			}
		} catch(IOException e) {
			// ignore exception
		}
	}
	
}
