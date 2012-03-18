package com.debugi.app.anyvblog.location;

import java.io.Serializable;

import android.location.Location;


public class LocationModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private double latitude;
	private double longitude;
	private Location location;
	private String locationString;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getLocationString() {
		return locationString;
	}
	public void setLocationString(String locationString) {
		this.locationString = locationString;
	}
	@Override
	public String toString() {
		return "LocationModel [latitude=" + latitude + ", longitude="
				+ longitude + ", location=" + location + ", locationString="
				+ locationString + "]";
	}
}
