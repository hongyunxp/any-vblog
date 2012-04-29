package com.debugi.app.anyvblog.model.netease;

import java.util.HashMap;
import java.util.Map;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.open.oauth.model.Parameters;

public class SendOneData4Netease implements SendData {
	private String status;
	private float lat;
	private float lon;//long
	private String vid;
	
	

	public SendOneData4Netease(String status) {
		this(status, 0, 0);
	}

	public SendOneData4Netease(String status, float lat,
			float lon) {
		this(status, lat, lon, "");
	}

	public SendOneData4Netease(String status, float lat,
			float lon, String vid) {
		this.status = status;
		this.lat = lat;
		this.lon = lon;
		this.vid = vid;
	}

	@Override
	public Parameters conver() {
		init();
		Map<String, String> data = new HashMap<String, String>();
		data.put("status", status);
		data.put("lat", lat + "");
		data.put("long", lon + "");
		if (vid != null && !"".equals(vid)) {
			data.put("vid", vid);
		}
		return new Parameters(data);
	}
	
	private void init() {
		if (status == null || "".equals(status)) {
			status = Constants.DEFAULT_MSG;
		} else if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
			lat = 0;
			lon = 0;
		}
	}

}
