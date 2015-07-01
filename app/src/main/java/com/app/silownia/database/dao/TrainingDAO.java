package com.app.silownia.database.dao;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.app.silownia.database.tables.ExerciseTable;
import com.app.silownia.database.tables.TrainingTable;
import com.app.silownia.database.tables.ExerciseTable.ExerciseColumns;
import com.app.silownia.database.tables.TrainingTable.TrainingColumns;
import com.app.silownia.model.Exercise;
import com.app.silownia.model.Training;

public class TrainingDAO implements DAO<Training> {

	private static final String INSERT = "insert into "
			+ TrainingTable.TABLE_NAME + "(" + TrainingColumns._ID + ", "
			+ TrainingColumns.START_DATE + ", " + TrainingColumns.END_DATE
			+ ", " + TrainingColumns.ACTIVE + ") values (?, ?, ?, ?)";

	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	public TrainingDAO(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(TrainingDAO.INSERT);
	}

	public long insert(Training training) {
		if (isDuringTraining()) {
			return -1;
		} 
		insertStatement.clearBindings();
		insertStatement.bindString(2, training.getmStartDate());
		insertStatement.bindString(3, training.getmEndDate());
		insertStatement.bindLong(4, (int) ((training.getmActive()) ? 1 : 0));
		return insertStatement.executeInsert();
	}

	@Override
	public void remove(long id) {
		db.delete(TrainingTable.TABLE_NAME, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public void update(Training training) {
		final ContentValues values = new ContentValues();
		values.put(TrainingColumns.START_DATE, training.getmStartDate());
		values.put(TrainingColumns.END_DATE, training.getmEndDate());
		values.put(TrainingColumns.ACTIVE, training.getmActive());
		db.update(TrainingTable.TABLE_NAME, values, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(training.getmId()) });
	}

	@Override
	public Training get(long id) {
		Training training = null;
		Cursor c = db.query(TrainingTable.TABLE_NAME, new String[] {
				TrainingColumns.START_DATE, TrainingColumns.END_DATE,
				TrainingColumns.ACTIVE }, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (c.moveToFirst()) {
			training = new Training((int) id, c.getString(0), c.getString(1),
					(int) c.getLong(2));
		}
		if (!c.isClosed()) {
			c.close();
		}
		return training;
	}

	public Training getActiveTraining() {
		Training training = null;
		Cursor c = db.query(TrainingTable.TABLE_NAME, new String[] {
				TrainingColumns._ID, TrainingColumns.START_DATE,
				TrainingColumns.END_DATE, TrainingColumns.ACTIVE },
				TrainingColumns.ACTIVE + " = ?",
				new String[] { String.valueOf(1) }, null, null, null);
		if (c.moveToFirst()) {
			training = new Training(c.getInt(0), c.getString(1), c.getString(2),
					(int) c.getLong(3));
		}
		if (!c.isClosed()) {
			c.close();
		}
		return training;
	}
	
	public ArrayList<Training> getAll() {
		ArrayList<Training> arrayList = new ArrayList<Training>();
		Cursor c = db.query(TrainingTable.TABLE_NAME,
				new String[] { TrainingColumns._ID, TrainingColumns.START_DATE,
				TrainingColumns.END_DATE, TrainingColumns.ACTIVE },
				null, null,
				null, null, null);
		while (c.moveToNext()) {
			Training training = null;
			training = new Training(c.getInt(0), c.getString(1), c.getString(2),
					(int) c.getLong(3));
			arrayList.add(training);
		}
		return arrayList;
	}

	private boolean isDuringTraining() {
		if (getActiveTraining()!=null) 
			return true;
		else 
			return false;
	}
	
}
