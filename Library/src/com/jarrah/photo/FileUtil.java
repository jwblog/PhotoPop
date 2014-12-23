package com.jarrah.photo;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;


public class FileUtil {
	private static final String CAPTURE_FILE_NAME = "_capture.jpg";

	/**
	 * 获取应用保存的位置
	 * 
	 * @param context
	 * @return
	 */
	public static String getStoragePathIfMounted(Context context) {
		
		File dir = context.getFilesDir();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			dir = Environment.getExternalStorageDirectory();
		}

		String name = context.getString(R.string.app_name);
		File storageFolder = buildPath(dir, name);
		if (!storageFolder.exists()) {
			storageFolder.mkdirs();
		}

		Log.e("storage path", storageFolder.getAbsolutePath());

		return storageFolder.getAbsolutePath();
	}

	public static File getCaptureFile(Context context) {
		String name = SystemClock.elapsedRealtime() + CAPTURE_FILE_NAME;
		File captureFile = new File(getStoragePathIfMounted(context), name);
		return captureFile;
	}

	public static File buildPath(File base, String... segments) {
		File cur = base;
		for (String segment : segments) {
			if (cur == null) {
				cur = new File(segment);
			} else {
				cur = new File(cur, segment);
			}
		}
		return cur;
	}
}
