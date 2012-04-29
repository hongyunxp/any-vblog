package com.debugi.app.anyvblog.model.sina;

import java.util.HashMap;
import java.util.Map;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.open.oauth.model.Parameters;
/**
 * 发布微博和转播微博使用同一个model
 * @author Administrator
 *
 */
public class SendOneData4Sina implements SendData {
	private String status;
	private float lat;
	private float lon;//long
	private String in_reply_to_status_id;	//转播的源id
	private String annotations;
	
	public SendOneData4Sina(String status) {
		this(status, null);
	}

	public SendOneData4Sina(String status, String in_reply_to_status_id) {
		this(status, in_reply_to_status_id, 0, 0);
	}

	public SendOneData4Sina(String status, String in_reply_to_status_id, float lat, float lon) {
		this(status, in_reply_to_status_id, lat, lon, null);
	}

	public SendOneData4Sina(String status, float lat, float lon) {
		this(status, null, lat, lon);
	}

	public SendOneData4Sina(String status, String in_reply_to_status_id, float lat, float lon,
			String annotations) {
		this.status = status;
		this.lat = lat;
		this.lon = lon;
		this.in_reply_to_status_id = in_reply_to_status_id;
		this.annotations = annotations;
	}

	@Override
	public Parameters conver() {
		init();
		Map<String, String> data = new HashMap<String, String>();
		data.put("status", status);
		data.put("lat", lat + "");
		data.put("long", lon + "");
		if (annotations != null && !"".equals(annotations)) {
			data.put("annotations", annotations);
		} else if (in_reply_to_status_id != null && !"".equals(in_reply_to_status_id)) {
			data.put("in_reply_to_status_id", in_reply_to_status_id);
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
