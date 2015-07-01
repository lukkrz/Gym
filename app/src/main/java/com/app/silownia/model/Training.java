package com.app.silownia.model;

import java.util.Date;

public class Training {
	private int mId;
	private String mStartDate;
	private String mEndDate;
	private boolean mActive;

	public Training(int id, String startDate, String endDate, boolean active) {
		super();
		mId = id;
		mStartDate = startDate;
		mEndDate = endDate;
		mActive = active;
	}
	
	public Training(int id, String startDate, String endDate, int active) {
		super();
		mId = id;
		mStartDate = startDate;
		mEndDate = endDate;
		mActive = ((active>0)?true:false);
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int id) {
		mId = id;
	}

	public String getmStartDate() {
		return mStartDate;
	}

	public void setmStartDate(String startDate) {
		mStartDate = startDate;
	}

	public String getmEndDate() {
		return mEndDate;
	}

	public void setmEndDate(String endDate) {
		mEndDate = endDate;
	}
	
	public void setmActive(boolean active) {
		mActive = active;
	}

	public boolean getmActive() {
		return mActive;
	}

}
