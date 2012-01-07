package org.m3;

import java.io.IOException;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.view.Surface;

public class Recorder {
    private MediaRecorder recorder;
    
    public Recorder() {
    }

    public void open() {
        recorder = new MediaRecorder();
    }
    
    public void close() {
		if(recorder != null) {
		    recorder.release();
		    recorder = null;
		}
    }
    
    public void start(String filename) {
		recorder.setOutputFile(filename);
		try {
			recorder.prepare();
	        recorder.start();
	    } catch(IllegalStateException e) {
		    e.printStackTrace();
	    } catch (IOException e) {
		    e.printStackTrace();
        }
    }

    public void stop() {
    	try {
    		recorder.stop();
    		recorder.reset();
    	} catch(IllegalStateException e) {
		    e.printStackTrace();
	    }
	}
    
    public void setPreview(Surface surface) {
    	recorder.setPreviewDisplay(surface);
    }
    
    public void setCamera(Camera camera) {
    	recorder.setCamera(camera);	
	}
    
    public void setRecorderParams(int vbr, int abr, int asr, int ach, int vfr, int w, int h, int md, int mfs) {
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        /*recorder.setVideoEncodingBitRate(vbr);
        recorder.setAudioEncodingBitRate(abr);
        recorder.setAudioSamplingRate(asr);
        recorder.setAudioChannels(ach);*/
        recorder.setVideoFrameRate(vfr);
        recorder.setVideoSize(w, h);
        recorder.setMaxDuration(md);
        recorder.setMaxFileSize(mfs);
    }
    
}
