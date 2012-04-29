package com.ilovn.app.anyvblog.model.sohu;

import com.ilovn.app.anyvblog.model.Counts;

public class Counts4Sohu {
	//{"id":194802546,"comments_count":324,"transmit_count":235}
	private String id;
	private int comments_count;
	private int transmit_count;
	public Counts conver() {
		return new Counts(id, comments_count, transmit_count);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getComments_count() {
		return comments_count;
	}
	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}
	public int getTransmit_count() {
		return transmit_count;
	}
	public void setTransmit_count(int transmit_count) {
		this.transmit_count = transmit_count;
	}
}
