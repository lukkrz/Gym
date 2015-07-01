package com.app.silownia.database.tables;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class ExerciseRecordTable {
	public static final String TABLE_NAME = "ExerciseRecord";

	public static class ExerciseRecordColumns implements BaseColumns {
		public static final String EXERCISE_ID = "exercise_id";
		public static final String NAME_ID = "name_id";
		public static final String WEIGHT = "weight";
		public static final String REPETITION = "repetition";
		public static final String TRAINING_ID = "training_id";
	}

	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + ExerciseRecordTable.TABLE_NAME + " (");
		sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(ExerciseRecordColumns.EXERCISE_ID + " INTEGER, ");
		sb.append(ExerciseRecordColumns.NAME_ID + " INTEGER, ");
		sb.append(ExerciseRecordColumns.WEIGHT + " REAL, ");
		sb.append(ExerciseRecordColumns.REPETITION + " INTEGER, ");
		sb.append(ExerciseRecordColumns.TRAINING_ID + " INTEGER, ");
		sb.append("FOREIGN KEY(" + ExerciseRecordColumns.NAME_ID + ") " +
				"REFERENCES " + ExerciseTable.TABLE_NAME + "("
					+ BaseColumns._ID + "), ");
		sb.append("FOREIGN KEY(" + ExerciseRecordColumns.TRAINING_ID + ") " +
				"REFERENCES " + TrainingTable.TABLE_NAME + "("
					+ BaseColumns._ID + ")");
		sb.append(");");
		db.execSQL(sb.toString());
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ExerciseRecordTable.TABLE_NAME);
		ExerciseRecordTable.onCreate(db);
	}
}
