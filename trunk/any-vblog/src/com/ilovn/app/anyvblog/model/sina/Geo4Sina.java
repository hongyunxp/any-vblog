package com.ilovn.app.anyvblog.model.sina;

import java.util.List;

public class Geo4Sina {
	/**
	 * geo":{"type": "Point","coordinates": [27.67227, 105.56227]}
	 */
	private String type;
	private List<String> coordinates;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}
	@Override
	public String toString() {
		return "Geo4Sina [type=" + type + ", coordinates=" + coordinates + "]";
	}
	
}
