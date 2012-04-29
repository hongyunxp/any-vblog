package com.ilovn.app.anyvblog.model.netease;

import java.util.List;

public class Geo4Netease {
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
		return "Geo4Netease [type=" + type + ", coordinates=" + coordinates
				+ "]";
	}
	
}
