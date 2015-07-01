package com.app.silownia.database.tables;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class ExerciseTable {

	public static final String TABLE_NAME = "Exercise";

	public static class ExerciseColumns implements BaseColumns {
		public static final String NAME = "name";
		public static final String ACTIVE = "active";
	}

	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + ExerciseTable.TABLE_NAME + " (");
		sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(ExerciseColumns.NAME + " TEXT, ");
		sb.append(ExerciseColumns.ACTIVE + " INTEGER ");
		sb.append(");");
		db.execSQL(sb.toString());
		
		backupAddDataTmp(db);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ExerciseTable.TABLE_NAME);
		ExerciseTable.onCreate(db);
	}

	private static void backupAddDataTmp(SQLiteDatabase db) {
		String ROW1 = "INSERT INTO " + ExerciseTable.TABLE_NAME + " ("
				+ ExerciseColumns.NAME + ", " + ExerciseColumns.ACTIVE
				+ ") Values ('Brzuszki 1', '1')";
		db.execSQL(ROW1);
		
		ROW1 = "INSERT INTO " + ExerciseTable.TABLE_NAME + " ("
				+ ExerciseColumns.NAME + ", " + ExerciseColumns.ACTIVE
				+ ") Values ('Brzuszki 2', '1')";
		db.execSQL(ROW1);
		
		ROW1 = "INSERT INTO " + ExerciseTable.TABLE_NAME + " ("
				+ ExerciseColumns.NAME + ", " + ExerciseColumns.ACTIVE
				+ ") Values ('Sztanga suwnica', '1')";
		db.execSQL(ROW1);
		
		ROW1 = "INSERT INTO " + ExerciseTable.TABLE_NAME + " ("
				+ ExerciseColumns.NAME + ", " + ExerciseColumns.ACTIVE
				+ ") Values ('Sztanga plaska', '1')";
		db.execSQL(ROW1);
		
		ROW1 = "INSERT INTO " + ExerciseTable.TABLE_NAME + " ("
				+ ExerciseColumns.NAME + ", " + ExerciseColumns.ACTIVE
				+ ") Values ('Rozpiêtki', '1')";
		db.execSQL(ROW1);
	}
}
