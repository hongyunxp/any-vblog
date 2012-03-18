package com.debugi.app.anyvblog.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.MapsUtils;
import com.debugi.app.anyvblog.utils.MyHandler;

public class LocationService {
	private static final String TAG = "LocationService";
	private static final int Time_Delay = 1000 * 3;
	private Context context;
	private LocationManager locationManager;
	private Location lastKonwLocation;
	private LocationListener listener;
	private Handler handler;
	private LocationModel locationModel;
	/**
	 * 使用Geocoder服务，使用Location获取Address
	 * @param context
	 * @param location
	 * @param maxResults
	 * @return
	 */
	@SuppressWarnings("unused")
	private static List<Address> converAddress(Context context, Location location, int maxResults) {
		if (maxResults <= 0) {
			maxResults = 1;
		}
		Geocoder geocoder = new Geocoder(context);
		List<Address> addressList = new ArrayList<Address>();
		try {
			addressList = geocoder.getFromLocation(
					location.getLatitude(),
					location.getLongitude(), maxResults);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Address address : addressList) {
			Config.debug(TAG, "address=>" + address.toString());
		}
		return addressList;
	}
	public static String getAddress(Location loca) {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(MapsUtils.GEO_URL + "&latlng=" + loca.getLatitude() + "," + loca.getLongitude() + "&sensor=false&region=cn");
		StringBuilder sb = new StringBuilder();
		try {
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			InputStream inputStream = entity.getContent();
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
			String temp;
			while ((temp = bf.readLine()) != null) {
				sb.append(temp);
				temp = null;
			}
			Config.debug(TAG, sb);
			JSONObject jsonObject = new JSONObject(sb.toString());
//			JSONArray jsonArray = jsonObject.getJSONArray("results");
//			for (int i = 0; i < jsonArray.length(); i ++) {
//				System.out.println(jsonArray.getJSONObject(i).getString("formatted_address"));;
//			}
			return jsonObject.getJSONArray("results").getJSONObject(0).getString("formatted_address");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public LocationService(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
		locationManager = (LocationManager) this.context
				.getSystemService(Context.LOCATION_SERVICE);
		listener = new MyLocationListener();
	}

	public void getLocation() {
		locationModel = new LocationModel();
		String provider = getProvider();
		if (provider == null) {
			Config.debug(TAG, "no provider for Location");
			return ;
		}
		Config.debug(TAG, "the provider = " + provider);
		Config.debug(TAG, "get last location start");
		lastKonwLocation = locationManager.getLastKnownLocation(provider);
		Config.debug(TAG, "get last location finish");
		//注册位置更新监听器
		locationManager.requestLocationUpdates(provider, 1000, 0,
				listener);
		Config.debug(TAG, "start looper to get Locate");
		Looper.loop();
		Config.debug(TAG, "looper finish");
	}

	private String getProvider() {
		Criteria criteria = new Criteria();
//		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setCostAllowed(false);
		Config.debug(TAG, "get best provider");
		return locationManager.getBestProvider(criteria, true);
	}

	class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			if (isBetterLocation(location, lastKonwLocation)) {
				Config.debug(TAG, "update location");
				lastKonwLocation = location;
				if (lastKonwLocation != null) {
					Config.debug(TAG, "get location ->" + lastKonwLocation);
					locationModel.setLatitude(lastKonwLocation.getLatitude());
					locationModel.setLongitude(lastKonwLocation.getLongitude());
					locationModel.setLocation(lastKonwLocation);
				}
				//取消looper
				Looper.myLooper().quit();
				//取消监听器
				finish();
				//发送消息
				Message message = new Message();
				message.what = MyHandler.Handler_Location;
				message.obj = locationModel;
				handler.sendMessage(message);
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Config.debug(TAG, "onStatusChanged=" + status);

		}

		@Override
		public void onProviderEnabled(String provider) {
			Config.debug(TAG, "onProviderEnabled=" + provider);
		}

		@Override
		public void onProviderDisabled(String provider) {
			Config.debug(TAG, "onProviderDisabled=" + provider);

		}

	}

	public void finish() {
		if (locationManager != null) {
			locationManager.removeUpdates(listener);
		}
	}

	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		} else if (location == null) {
			return false;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > Time_Delay;
		boolean isSignificantlyOlder = timeDelta < -Time_Delay;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}
