package com.app.silownia.database.manager;

import com.app.silownia.database.dao.ExerciseDAO;
import com.app.silownia.database.tables.ExerciseRecordTable;
import com.app.silownia.database.tables.ExerciseTable;
import com.app.silownia.database.tables.TrainingTable;
import com.app.silownia.model.Exercise;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenHelper extends SQLiteOpenHelper {

	private static String TAG = "OpenHelper";

	public OpenHelper(final Context context) {
		super(context, DatabaseManager.DATABASE_NAME, null,
				DatabaseManager.DATABASE_VERSION);
	}

	public void onOpen(final SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys=ON;");
			Cursor c = db.rawQuery("PRAGMA foreign_keys", null);
			if (c.moveToFirst()) {
				int result = c.getInt(0);
				Log.i(TAG, "SQLite foreign key support (1 is on, 0 is off): "
						+ result);
			} else {
				Log.i(TAG, "SQLite foreign key support NOT AVAILABLE");
			}
			if (!c.isClosed()) {
				c.close();
			}
		}
	}

	public void onCreate(final SQLiteDatabase db) {
		Log.i(TAG, "DataHelper.OpenHelper onCreate creating database "
				+ DatabaseManager.DATABASE_NAME);
		TrainingTable.onCreate(db);
		Log.i(TAG, "onCreate(): TrainingTable");
		ExerciseTable.onCreate(db);
		Log.i(TAG, "onCreate(): ExerciseTable");
		ExerciseRecordTable.onCreate(db);
		Log.i(TAG, "onCreate(): ExerciseRecordTable");

	}

	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		Log.i(TAG, "SQLiteOpenHelper onUpgrade - oldVersion: " + oldVersion
				+ " newVersion: " + newVersion);
		TrainingTable.onUpgrade(db, oldVersion, newVersion);
		ExerciseTable.onUpgrade(db, oldVersion, newVersion);
		ExerciseRecordTable.onUpgrade(db, oldVersion, newVersion);

		addDataOnFirstInstall(db);
	}

	private void addDataOnFirstInstall(SQLiteDatabase db) {
		// Add data needed only on first install of app
		ExerciseDAO exerciseDAO = new ExerciseDAO(db);
		exerciseDAO.insert(new Exercise(-1, "£awka sztanga", true));
		exerciseDAO.insert(new Exercise(-1,"£awka sztanga suwnica", true));
		exerciseDAO.insert(new Exercise(-1,"Rozpietki", true));
		exerciseDAO.insert(new Exercise(-1,"Brzuszki 1", true));
		exerciseDAO.insert(new Exercise(-1,"Brzuszki 2", true));
		exerciseDAO.insert(new Exercise(-1,"Skocznia", true));
	}
}
