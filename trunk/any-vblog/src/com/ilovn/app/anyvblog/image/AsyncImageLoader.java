package com.ilovn.app.anyvblog.image;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.ilovn.app.anyvblog.utils.Config;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncImageLoader {
	private static final String TAG = "AsyncImageLoader";
	private static AsyncImageLoader asyncImageLoader = null;
	private Map<String, SoftReference<Drawable>> imageCache;

	private AsyncImageLoader() {
		this.imageCache = new HashMap<String, SoftReference<Drawable>>();
	}
	
	public static AsyncImageLoader getInstance() {
		if (asyncImageLoader == null) {
			return new AsyncImageLoader();
		}
		return asyncImageLoader;
	}
	
	public Drawable loadDrawable(final String imageUrl, final ImageView imageView, final ImageCallback imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				Config.debug(TAG, "find drawable in softRefefence");
				return drawable;
			}
		}
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				imageCallback.imageLoaded((Drawable) msg.obj, imageView, imageUrl);
			}
			
		};
		new Thread() {

			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
			
		}.start();
		return null;
	}
	 public static Drawable loadImageFromUrl(String url){
	        Drawable d = null;
			try {
				d = Drawable.createFromStream(new URL(url).openStream(), "src");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return d;
	    }
	public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl);
    }
}
