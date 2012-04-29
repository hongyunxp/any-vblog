package com.ilovn.app.anyvblog.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.ilovn.app.anyvblog.image.AsyncImageLoader.ImageCallback;

public class ImageCallbackImpl implements ImageCallback {
	@Override
	public void imageLoaded(Drawable imageDrawable, ImageView imageView,
			String imageUrl) {
		imageView.setImageDrawable(imageDrawable);
	}

}
