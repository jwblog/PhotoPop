package com.jarrah.photo;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author jarrah
 * 
 */
public class ActivityPhtotoPop extends Activity implements ReqeustCode {

	private Dialog dialog;

	protected File captureFile;

	@SuppressLint("InflateParams")
	protected void popup(Context context) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View holder = inflater.inflate(R.layout.view_popup_button, null, false);
		View gallery = holder.findViewById(R.id.btnPhoto);
		View capture = holder.findViewById(R.id.btnCapture);
		View cancel = holder.findViewById(R.id.btnCanel);

		ButtonClick click = new ButtonClick(this);
		gallery.setOnClickListener(click);
		capture.setOnClickListener(click);
		cancel.setOnClickListener(click);

		dialog = PopupUtil.makePopup(context, holder);
		dialog.show();
	}

	public class ButtonClick implements View.OnClickListener {

		private Activity activity;

		public ButtonClick(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View v) {

			if (dialog != null) {
				dialog.dismiss();
			}

			if (v.getId() == R.id.btnPhoto) {
				PhotoPicker.launchGallery(activity, FROM_GALLERY);
			}

			if (v.getId() == R.id.btnCapture) {
				captureFile = FileUtil.getCaptureFile(activity);
				PhotoPicker.launchCamera(activity, FROM_CAPTURE, captureFile);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			if (requestCode == FROM_GALLERY) {
				if (data != null) {
					String path = PhotoPicker
							.getPhotoPathByLocalUri(this, data);
					onGalleryComplete(path);
				}
			} else if (requestCode == FROM_CAPTURE) {
				
				onCaptureComplete(captureFile);
				
			} else if (requestCode == FROM_CROP) {
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					onCropComplete(bitmap);
				}
			}

		}
	}

	protected void onGalleryComplete(String path) {

	}

	protected void onCropComplete(Bitmap bitmap) {

	}

	protected void onCaptureComplete(File captureFile) {

	}
}
