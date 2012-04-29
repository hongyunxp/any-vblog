package com.debugi.app.anyvblog.model;

public class Counts {
	private String id;
	private int count_c;
	private int count_f;
	
	public Counts() {
		super();
	}

	public Counts(String id, int count_c, int count_f) {
		this.id = id;
		this.count_c = count_c;
		this.count_f = count_f;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCount_c() {
		return count_c;
	}

	public void setCount_c(int count_c) {
		this.count_c = count_c;
	}

	public int getCount_f() {
		return count_f;
	}

	public void setCount_f(int count_f) {
		this.count_f = count_f;
	}
}
