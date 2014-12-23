package com.jarrah.photo;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

//在onActivityResult方法中根据requestCode和resultCode来获取当前拍照的图片地址。
//注意：这里有个问题，在有些机型当中（如SamsungI939、note2等）遇见了当拍照并存储之后，intent当中得到的data为空：
/**
 * data = null 的情况主要是由于拍照的时候横屏了,导致重新create,
 * 普通的解决方法可以在sharedpreference里面保存拍照文件的路径(onSaveInstance保存),
 * 在onRestoreSaveInstance里面在获取出来. 最简单的可以用fileUtil 里面的一个静态变量保存起来..
 * */

public class PhotoPicker {

	private static final String IMAGE_TYPE = "image/*";

	/**
	 * 打开照相机
	 * 
	 * @param activity
	 *            当前的activity
	 * @param requestCode
	 *            拍照成功时activity forResult 的时候的requestCode
	 * @param photoFile
	 *            拍照完毕时,图片保存的位置
	 */
	public static void launchCamera(Activity activity, int requestCode,
			File photoFile) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 本地照片调用
	 * 
	 * @param activity
	 * @param requestCode
	 */
	public static void launchGallery(Activity activity, int requestCode) {
		if (launchSys(activity, requestCode)
				&& launch3partyBroswer(activity, requestCode)
				&& launchFinally(activity));
	}

	/**
	 * PopupMenu打开本地相册.
	 */
	private static boolean launchSys(Activity activity, int actResultCode) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_TYPE);
		try {
			activity.startActivityForResult(intent, actResultCode);

		} catch (android.content.ActivityNotFoundException e) {

			return true;
		}

		return false;
	}

	/**
	 * 打开其他的一文件浏览器,如果没有本地相册的话
	 */
	private static boolean launch3partyBroswer(Activity activity, int requestCode) {
		Toast.makeText(activity, "没有相册软件，运行文件浏览器", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
		intent.setType(IMAGE_TYPE); // 查看类型 String IMAGE_UNSPECIFIED =
									// "image/*";
		Intent wrapperIntent = Intent.createChooser(intent, null);
		try {
			activity.startActivityForResult(wrapperIntent, requestCode);
		} catch (android.content.ActivityNotFoundException e1) {
			return true;
		}
		return false;
	}

	/**
	 * 这个是找不到相关的图片浏览器,或者相册
	 */
	private static boolean launchFinally(Activity activity) {
		Toast.makeText(activity, "您的系统没有文件浏览器或则相册支持,请安装！", Toast.LENGTH_LONG)
				.show();
		return false;
	}

	/**
	 * 获取从本地图库返回来的时候的URI解析出来的文件路径
	 * 
	 * @return
	 */
	public static String getPhotoPathByLocalUri(Context context, Intent data) {
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	/**
	 * 剪切图片 在 onActivityResult的时候 从 返回的 Intent 里面获取 data 就是bitmap >>>> Bitmap
	 * bitmap = data.getParcelableExtra("data");
	 * 
	 * @param activity
	 * @param imagePath
	 *            文件路径
	 * @param requestCode
	 *            返回码
	 */
	public static void startCrop(Activity activity, String imagePath,
			int requestCode, boolean isLarge) {
		File f = new File(imagePath);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.fromFile(f), "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 4); // 裁剪框比例 4 : 3
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", isLarge ? 400 : 200);
		intent.putExtra("outputY", isLarge ? 300 : 150);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true); // 设置为true 的时候才能有返回
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}

}