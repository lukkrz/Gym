package com.app.silownia.database.dao;

import org.json.JSONException;
import org.json.JSONObject;

public interface DAO<T> {
	void remove(long id);
	T get(long id);
}