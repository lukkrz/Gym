package com.app.silownia.database.tables;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class TrainingTable {

	public static final String TABLE_NAME = "Training";

	public static class TrainingColumns implements BaseColumns {
		public static final String START_DATE = "start_date";
		public static final String END_DATE = "end_date";
		public static final String ACTIVE = "active";
	}

	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TrainingTable.TABLE_NAME + " (");
		sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(TrainingColumns.START_DATE + " STRING, ");
		sb.append(TrainingColumns.END_DATE + " STRING, ");
		sb.append(TrainingColumns.ACTIVE + " INTEGER ");
		sb.append(");");
		db.execSQL(sb.toString());
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TrainingTable.TABLE_NAME);
		TrainingTable.onCreate(db);
	}
}
