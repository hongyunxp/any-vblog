package com.ilovn.app.anyvblog.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.ilovn.app.anyvblog.R;

public class ImageUtils {
	public static void saveImage(Bitmap bitmap, String name) throws IOException {
		File dir = new File(Config.cachePath + "save" + File.separator);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, name + ".png");
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		outputStream.flush();
		outputStream.close();
	}
	/**
	 * 从网络获取图片
	 * @param url 图片地址
	 * @return
	 */
	public static Bitmap loadImageFromUrl(String url){
        Bitmap b = null;
		try {
			b = BitmapFactory.decodeStream(new URL(url).openStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return b;
    }
	/**
	 * 显示图片
	 * @param drawable 图片资源
	 * @param imageView 显示的View
	 * @param defaultResource 默认资源ID
	 */
	public static void showImage(Drawable drawable, ImageView imageView, int... defaultResource) {
		if (drawable == null) {
			if (defaultResource.length > 0) {
				imageView.setImageResource(defaultResource[0]);
			} else {
				imageView.setImageResource(R.anim.loading);
			}
		} else {
			imageView.setImageDrawable(drawable);
		}
	}
	/**
	 * 转换byte[]->bitmap
	 * @param data
	 * @return
	 */
	public static Bitmap conver2Bitmap(byte[] data) {
		if (data == null) {
			return null;
		}
		ByteArrayInputStream stream = new ByteArrayInputStream(data);
		return BitmapFactory.decodeStream(stream);
	}
	/**
	 * 转换bitmap->byte[]
	 * @param bitmap
	 * @return
	 */
	public static byte[] conver2Byte(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		return outputStream.toByteArray();
	}
}
