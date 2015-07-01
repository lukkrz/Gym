package com.app.silownia.database.dao;

import java.util.ArrayList;

import org.json.JSONException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.app.silownia.database.tables.ExerciseRecordTable;
import com.app.silownia.database.tables.TrainingTable;
import com.app.silownia.database.tables.ExerciseRecordTable.ExerciseRecordColumns;
import com.app.silownia.database.tables.TrainingTable.TrainingColumns;
import com.app.silownia.model.ExerciseRecord;
import com.app.silownia.model.Training;

public class ExerciseRecordDAO implements DAO<ExerciseRecord> {

	private static final String INSERT = "insert into "
			+ ExerciseRecordTable.TABLE_NAME + "(" + ExerciseRecordColumns._ID
			+ ", " + ExerciseRecordColumns.EXERCISE_ID + ", "
			+ ExerciseRecordColumns.NAME_ID + ", "
			+ ExerciseRecordColumns.WEIGHT + ", "
			+ ExerciseRecordColumns.REPETITION + ", "
			+ ExerciseRecordColumns.TRAINING_ID + ") values (?, ?, ?, ?, ?, ?)";

	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	public ExerciseRecordDAO(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(ExerciseRecordDAO.INSERT);
	}

	public long insert(ExerciseRecord exerciseRecord) {
		insertStatement.clearBindings();
		insertStatement.bindLong(2, exerciseRecord.getmExerciseId());
		insertStatement.bindLong(3, exerciseRecord.getmExerciseNameId());
		insertStatement.bindDouble(4, exerciseRecord.getmWeight());
		insertStatement.bindLong(5, exerciseRecord.getmRepetition());
		insertStatement.bindLong(6, exerciseRecord.getmTrainingId());
		return insertStatement.executeInsert();
	}

	@Override
	public void remove(long id) {
		db.delete(ExerciseRecordTable.TABLE_NAME, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	@Override
	public ExerciseRecord get(long id) {
		ExerciseRecord exerciseRecord = null;
		Cursor c = db.query(ExerciseRecordTable.TABLE_NAME, new String[] {
				ExerciseRecordColumns.EXERCISE_ID,
				ExerciseRecordColumns.NAME_ID, ExerciseRecordColumns.WEIGHT,
				ExerciseRecordColumns.REPETITION,
				ExerciseRecordColumns.TRAINING_ID }, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (c.moveToFirst()) {
			exerciseRecord = new ExerciseRecord((int) id, c.getInt(0),
					c.getInt(1), c.getDouble(2), c.getInt(3), c.getInt(4));
		}
		if (!c.isClosed()) {
			c.close();
		}
		return exerciseRecord;
	}

	public ExerciseRecord getLastInTraining(long trainingId) {
		ExerciseRecord exerciseRecord = null;
		Cursor c = db.query(ExerciseRecordTable.TABLE_NAME, new String[] {
				BaseColumns._ID, ExerciseRecordColumns.EXERCISE_ID,
				ExerciseRecordColumns.NAME_ID, ExerciseRecordColumns.WEIGHT,
				ExerciseRecordColumns.REPETITION,
				ExerciseRecordColumns.TRAINING_ID },
				ExerciseRecordColumns.TRAINING_ID + " = ?",
				new String[] { String.valueOf(trainingId) }, null, null, null);
		if (c.moveToLast()) {
			exerciseRecord = new ExerciseRecord(c.getInt(0), c.getInt(1),
					c.getInt(2), c.getDouble(3), c.getInt(4), c.getInt(5));
		}
		if (!c.isClosed()) {
			c.close();
		}
		return exerciseRecord;
	}
	
	public ArrayList<ExerciseRecord> getAll(long nameId, long trainingId) {
		ArrayList<ExerciseRecord> arrayList = new ArrayList<ExerciseRecord>();
		Cursor c = db.query(ExerciseRecordTable.TABLE_NAME, new String[] {
				BaseColumns._ID, ExerciseRecordColumns.EXERCISE_ID,
				ExerciseRecordColumns.NAME_ID, ExerciseRecordColumns.WEIGHT,
				ExerciseRecordColumns.REPETITION,
				ExerciseRecordColumns.TRAINING_ID },
				ExerciseRecordColumns.NAME_ID + " = ? AND " + ExerciseRecordColumns.TRAINING_ID + " = ?",
				new String[] { String.valueOf(nameId), String.valueOf(trainingId) }, null, null, null);
		while (c.moveToNext()) {
			ExerciseRecord ExerciseRecord = null;
			ExerciseRecord = new ExerciseRecord(c.getInt(0), c.getInt(1),
					c.getInt(2), c.getDouble(3), c.getInt(4), c.getInt(5));
			arrayList.add(ExerciseRecord);
		}
		return arrayList;
	}
	
	public ArrayList<ExerciseRecord> getAll(long nameId) {
		ArrayList<ExerciseRecord> arrayList = new ArrayList<ExerciseRecord>();
		Cursor c = db.query(ExerciseRecordTable.TABLE_NAME, new String[] {
				BaseColumns._ID, ExerciseRecordColumns.EXERCISE_ID,
				ExerciseRecordColumns.NAME_ID, ExerciseRecordColumns.WEIGHT,
				ExerciseRecordColumns.REPETITION,
				ExerciseRecordColumns.TRAINING_ID },
				ExerciseRecordColumns.NAME_ID + " = ? ",
				new String[] { String.valueOf(nameId) }, null, null, null);
		while (c.moveToNext()) {
			ExerciseRecord ExerciseRecord = null;
			ExerciseRecord = new ExerciseRecord(c.getInt(0), c.getInt(1),
					c.getInt(2), c.getDouble(3), c.getInt(4), c.getInt(5));
			arrayList.add(ExerciseRecord);
		}
		return arrayList;
	}

	/*
	 * public ExerciseRecord getLast(String ) { ExerciseRecord exerciseRecord =
	 * null; Cursor c = db.query(ExerciseRecordTable.TABLE_NAME, new String[] {
	 * ExerciseRecordColumns.NAME, ExerciseRecordColumns.WEIGHT,
	 * ExerciseRecordColumns.REPETITION, ExerciseRecordColumns.TRAINING_ID },
	 * BaseColumns._ID + " = ?", new String[] { String.valueOf(id) }, null,
	 * null, null); if (c.moveToFirst()) { exerciseRecord = new
	 * ExerciseRecord((int) id, c.getString(0), c.getDouble(2), c.getInt(3),
	 * c.getInt(4)); } if (!c.isClosed()) { c.close(); } return exerciseRecord;
	 * }
	 */

	public ArrayList<ExerciseRecord> getAll() {
		ArrayList<ExerciseRecord> arrayList = new ArrayList<ExerciseRecord>();
		Cursor c = db.query(ExerciseRecordTable.TABLE_NAME, new String[] {
				ExerciseRecordColumns._ID, ExerciseRecordColumns.EXERCISE_ID,
				ExerciseRecordColumns.NAME_ID, ExerciseRecordColumns.WEIGHT,
				ExerciseRecordColumns.REPETITION,
				ExerciseRecordColumns.TRAINING_ID }, null, null, null, null,
				null);
		while (c.moveToNext()) {
			ExerciseRecord ExerciseRecord = null;
			ExerciseRecord = new ExerciseRecord(c.getInt(0), c.getInt(1),
					c.getInt(2), c.getDouble(3), c.getInt(4), c.getInt(5));
			arrayList.add(ExerciseRecord);
		}
		return arrayList;
	}

}
