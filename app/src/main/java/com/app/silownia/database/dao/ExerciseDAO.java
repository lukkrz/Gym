package com.app.silownia.database.dao;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

import com.app.silownia.database.tables.ExerciseTable;
import com.app.silownia.database.tables.ExerciseTable.ExerciseColumns;
import com.app.silownia.model.Exercise;

public class ExerciseDAO implements DAO<Exercise> {

	private static final String INSERT = "insert into "
			+ ExerciseTable.TABLE_NAME + "(" + ExerciseColumns._ID + ", "
			+ ExerciseColumns.NAME + ", " + ExerciseColumns.ACTIVE
			+ ") values (?, ?, ?)";

	private static final String TAG = "ExerciseDAO";

	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	public ExerciseDAO(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(ExerciseDAO.INSERT);
	}

	public long insert(Exercise exercise) {
		insertStatement.clearBindings();
		insertStatement.bindString(2, exercise.getName());
		insertStatement.bindLong(3, ((exercise.getActive()) ? 1 : 0));
		return insertStatement.executeInsert();
	}

	public void update(Exercise exercise) {
		final ContentValues values = new ContentValues();
		values.put(ExerciseColumns.NAME, exercise.getName());
		values.put(ExerciseColumns.ACTIVE, exercise.getActive());
		db.update(ExerciseTable.TABLE_NAME, values, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(exercise.getId()) });
	}

	public Exercise get(String nameOfExercise) {
		Exercise exercise = null;
		Cursor c = db.query(ExerciseTable.TABLE_NAME,
				new String[] { BaseColumns._ID, ExerciseColumns.NAME,
						ExerciseColumns.ACTIVE },
				ExerciseColumns.NAME + " = ?",
				new String[] { nameOfExercise }, null, null, null);
		if (c.moveToFirst()) {
			exercise = new Exercise(c.getInt(0), c.getString(1),
					(int) c.getLong(2));
		}
		if (!c.isClosed()) {
			c.close();
		}
		return exercise;
	}

	public void update(ArrayList<Exercise> exerciseArray) {
		for (Exercise exercise : exerciseArray) {
			update(exercise);
		}
	}

	public void remove(long id) {
		db.delete(ExerciseTable.TABLE_NAME, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public Exercise get(long id) {
		Exercise exercise = null;
		Cursor c = db
				.query(ExerciseTable.TABLE_NAME, new String[] {
						ExerciseColumns.NAME, ExerciseColumns.ACTIVE },
						BaseColumns._ID + " = ?",
						new String[] { String.valueOf(id) }, null, null, null);
		if (c.moveToFirst()) {
			exercise = new Exercise((int) id, c.getString(0),
					(int) c.getLong(1));
		}
		if (!c.isClosed()) {
			c.close();
		}
		return exercise;
	}

	public ArrayList<String> getAll() {
		ArrayList<String> arrayList = new ArrayList<String>();
		Cursor c = db.query(ExerciseTable.TABLE_NAME,
				new String[] { ExerciseColumns.NAME }, null, null, null, null,
				null);
		while (c.moveToNext()) {
			arrayList.add(c.getString(0));
		}
		return arrayList;
	}

	public ArrayList<Exercise> getAllExercises() {
		ArrayList<Exercise> arrayList = new ArrayList<Exercise>();
		Cursor c = db.query(ExerciseTable.TABLE_NAME, new String[] {
				ExerciseColumns._ID, ExerciseColumns.NAME,
				ExerciseColumns.ACTIVE }, null, null, null, null, null);
		while (c.moveToNext()) {
			arrayList.add(new Exercise(c.getInt(0), c.getString(1), (int) c
					.getLong(2)));
		}
		return arrayList;
	}

	public ArrayList<Exercise> getAllActiveExercises() {
		ArrayList<Exercise> arrayList = new ArrayList<Exercise>();
		Cursor c = db.query(ExerciseTable.TABLE_NAME, new String[] {
				ExerciseColumns._ID, ExerciseColumns.NAME,
				ExerciseColumns.ACTIVE }, ExerciseColumns.ACTIVE + " = ?",
				new String[] { String.valueOf(1) }, null, null, null);
		while (c.moveToNext()) {
			arrayList.add(new Exercise(c.getInt(0), c.getString(1), (int) c
					.getLong(2)));
		}
		Log.d(TAG, "getAllActiveExercises() size: " + arrayList.size());
		return arrayList;
	}

}
