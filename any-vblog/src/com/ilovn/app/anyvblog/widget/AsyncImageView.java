package com.ilovn.app.anyvblog.widget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AsyncImageView extends ImageView {
	/**
	 * 异步task加载器
	 */
	private AsyncLoadImage mAsyncLoad;

	/**
	 * 下载回来的图片缓存存活时间，单位：秒(s)
	 */
	private long mCacheLiveTime = -1;

	public AsyncImageView(Context context) {
		super(context);
	}

	public AsyncImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AsyncImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		if (mAsyncLoad != null) {
			mAsyncLoad.cancel(true);
			mAsyncLoad = null;
		}
		super.setImageDrawable(drawable);
	}

	/**
	 * 重写下面几个设置图片资源的方法，目地是取消网络加载
	 */
	@Override
	public void setImageResource(int resId) {
		cancelLoad();
		super.setImageResource(resId);
	}

	@Override
	public void setImageURI(Uri uri) {
		cancelLoad();
		super.setImageURI(uri);
	}

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		cancelLoad();
		super.setImageBitmap(bitmap);
	}

	/**
	 * 取消正在进行的异步task
	 */
	public void cancelLoad() {
		if (mAsyncLoad != null) {
			mAsyncLoad.cancel(true);
			mAsyncLoad = null;
		}
	}

	/**
	 * 设置图片存活时间
	 * 
	 * @param second
	 *            存活时间，单位【秒】，如果等于0或null，则不缓存,-1表示一直缓存
	 */
	public void setCacheLiveTime(long second) {
		if (second == -1) {
			this.mCacheLiveTime = -1;
		} else if (second == 0) {
			this.mCacheLiveTime = 0;
		} else if (second >= 0) {
			this.mCacheLiveTime = second * 1000;
		}
	}

	/**
	 * 从网络异步加载
	 * 
	 * @param url
	 * @param saveFileName
	 */
	public void asyncLoadBitmapFromUrl(String url, String saveFileName) {
		if (mAsyncLoad != null) {
			mAsyncLoad.cancel(true);
		}
		// AsyncTask不可重用，所以每次重新实例
		mAsyncLoad = new AsyncLoadImage();
		mAsyncLoad.execute(url, saveFileName);
	}

	/**
	 * 异步加载器
	 */
	private class AsyncLoadImage extends AsyncTask<String, Void, Bitmap> {
		/**
		 * 是否取消
		 */
		private boolean isCancel = false;

		@Override
		protected Bitmap doInBackground(String... params) {
			if (isCancel) {
				return null;
			}
			String url = params[0];
			String fileName = params[1];
			try {
				return getBitmap(url, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			System.out.println("async load imgae cancel");
			isCancel = true;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (!isCancel && result != null) {
				AsyncImageView.this.setImageBitmap(result);
			}
		}

	}

	/**
	 * 下载图片
	 * 
	 * @param urlString
	 *            url下载地址
	 * @param fileName
	 *            缓存文件路径
	 * @throws IOException
	 */
	private Bitmap getBitmap(String urlString, String fileName)
			throws IOException {
		if (fileName == null || "".equals(fileName.trim())) {
			InputStream input = getBitmapInputStreamFromUrl(urlString);
			return BitmapFactory.decodeStream(input);
		}

		File file = new File(fileName);
		//若已存在文件且缓存期为无限，则直接解析本地文件
		if (file.isFile() && mCacheLiveTime == -1) {
			return BitmapFactory.decodeFile(file.getAbsolutePath());
		} else if (!file.isFile()
				|| (mCacheLiveTime > 0 && (System.currentTimeMillis()
						- file.lastModified() > mCacheLiveTime))) {
			InputStream input = getBitmapInputStreamFromUrl(urlString);
			file = saveImage(input, fileName);
			// 如果文件结构创建失败，则直接从输入流解码图片
			if (file == null || !file.exists() || !file.canRead()) {
				return BitmapFactory.decodeStream(input);
			}
		}
		return BitmapFactory.decodeFile(file.getAbsolutePath());
	}

	/**
	 * 下载图片，输入InputStream
	 * 
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	private InputStream getBitmapInputStreamFromUrl(String urlString)
			throws IOException {
		System.out.println("load urlimg=>" + urlString);
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(25000);
		connection.setReadTimeout(90000);
		return connection.getInputStream();
	}

	/**
	 * 从输入流保存图片到文件系统
	 * 
	 * @param fileName
	 * @param input
	 * @return
	 */
	private File saveImage(InputStream input, String fileName) {
		if ("".equals(fileName.trim()) || input == null) {
			return null;
		}
		File file = new File(fileName);
		if (file == null || file.getParentFile() == null) {
			System.out.println("*******************null *******************");
		}
		OutputStream output = null;
		try {
			file.getParentFile().mkdirs();
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			if (!file.createNewFile()) {
				return null;
			}
			output = new FileOutputStream(file);
			byte[] buffer = new byte[4 * 1024];
			do {
				// 循环读取
				int numread = input.read(buffer);
				if (numread == -1) {
					break;
				}
				output.write(buffer, 0, numread);
			} while (true);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return file;
	}

}
