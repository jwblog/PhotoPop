package com.jarrah.photopop.demo;

import java.io.File;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.jarrah.photo.ActivityPhtotoPop;
import com.jarrah.photo.ImageUtil;
import com.jarrah.photo.PhotoPicker;


public class MainActivity extends ActivityPhtotoPop {

    private ImageView mImageView;
	private ToggleButton mToggle;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.pop).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popup(MainActivity.this);
			}
		});
        
        mImageView = (ImageView)findViewById(R.id.imageView);
        mToggle = (ToggleButton)findViewById(R.id.crop);
    }
    
    
    @Override
    protected void onCaptureComplete(File captureFile) {
    	
    	if (mToggle.isChecked()) {
    		PhotoPicker.startCrop(this, captureFile.getAbsolutePath(), FROM_CROP, false);
			return;
		}
    	
    	if (captureFile != null) {
    		Bitmap bmp = ImageUtil.getResizedImage(captureFile.getAbsolutePath(), null, 500, true, 0);
    		mImageView.setImageBitmap(bmp);
    	}
    }
    
    @Override
    protected void onGalleryComplete(String path) {
    	
    	if (mToggle.isChecked()) {
    		PhotoPicker.startCrop(this, path, FROM_CROP, false);
			return;
		}
    	
    	if (path != null) {
    		Bitmap bmp = ImageUtil.getResizedImage(path, null, 500, true, 0);
    		mImageView.setImageBitmap(bmp);
		}
    }
    
    @Override
    protected void onCropComplete(Bitmap bitmap) {
    	if(bitmap != null)
    		mImageView.setImageBitmap(bitmap);
    }
}
