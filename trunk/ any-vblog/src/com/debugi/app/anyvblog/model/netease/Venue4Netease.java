package com.debugi.app.anyvblog.model.netease;

import java.util.List;

public class Venue4Netease {
	private String id;
	private String name;
	private String address;
	private String city;
	private String province;
	private String state;
	private List<String> coordinates;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<String> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}
	@Override
	public String toString() {
		return "Venue4Netease [id=" + id + ", name=" + name + ", address="
				+ address + ", city=" + city + ", province=" + province
				+ ", state=" + state + ", coordinates=" + coordinates + "]";
	}
}
