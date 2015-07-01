package com.app.silownia.model;

public class Exercise {

	private int mId;
	private String mName;
	private boolean mActive;

	public Exercise(int id, String name, boolean active) {
		mId = id;
		mName = name;
		mActive = active;
	}
	
	public Exercise(int id, String name, int active) {
		mId = id;
		mName = name;
		mActive = ((active>0)?true:false);
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public boolean getActive() {
		return mActive;
	}

	public void setActive(boolean active) {
		this.mActive = active;
	}
	
	public String toString() {
		return mName;
	}
}
