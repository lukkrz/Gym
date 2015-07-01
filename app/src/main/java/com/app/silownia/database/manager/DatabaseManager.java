package com.app.silownia.database.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.silownia.database.dao.ExerciseDAO;
import com.app.silownia.database.dao.ExerciseRecordDAO;
import com.app.silownia.database.dao.TrainingDAO;
import com.app.silownia.model.Exercise;
import com.app.silownia.model.ExerciseRecord;
import com.app.silownia.model.Training;

public class DatabaseManager {

	static final String DATABASE_NAME = "Silownia";
	static final int DATABASE_VERSION = 33;
	private static final String TAG = "DatabaseManager";

	private Context mContext;
	private SQLiteDatabase mDb;

	private ExerciseDAO mExerciseDAO;
	private ExerciseRecordDAO mExerciseRecordDAO;
	private TrainingDAO mTrainingDAO;

	public DatabaseManager(Context context) {

		mContext = context;

		SQLiteOpenHelper openHelper = new OpenHelper(this.mContext);
		mDb = openHelper.getWritableDatabase();

		mTrainingDAO = new TrainingDAO(mDb);
		mExerciseDAO = new ExerciseDAO(mDb);
		mExerciseRecordDAO = new ExerciseRecordDAO(mDb);
	}

	public SQLiteDatabase gedDb() {
		return mDb;
	}

	// ################ Excercise operations ##############
	public long insertExcercise(Exercise exercise) {
		return mExerciseDAO.insert(exercise);
	}

	public Exercise getExcercise(long id) {
		return mExerciseDAO.get(id);
	}
	
	public Exercise getExcercise(String nameOfExercise) {
		return mExerciseDAO.get(nameOfExercise);
	}

	public ArrayList<String> getAllExcercisesString() {
		return mExerciseDAO.getAll();
	}

	public ArrayList<Exercise> getAllExcercises() {
		return mExerciseDAO.getAllExercises();
	}

	public ArrayList<Exercise> getAllActiveExcercises() {
		return mExerciseDAO.getAllActiveExercises();
	}

	public void updateExercise(Exercise exercise) {
		mExerciseDAO.update(exercise);
	}

	public void updateExerciseState(ArrayList<Exercise> exerciseArray,
			boolean active) {
		for (Exercise exercise : exerciseArray) {
			exercise.setActive(active);
			mExerciseDAO.update(exercise);
		}
	}

	// ################ ExcerciseRecords operations ##############

	public long insertExcerciseRecord(ExerciseRecord exerciseRecord) {
		Training training = mTrainingDAO.getActiveTraining();
		if (training == null)
			return -1;

		exerciseRecord.setmTrainingId(training.getmId());
		long excerciseId = mExerciseRecordDAO.insert(exerciseRecord);
		Log.d(TAG, "insertExcerciseRecord(): New ExcerciseRecord: "
				+ excerciseId + " added to Training: " + training.getmId());
		return excerciseId;
	}

	public ArrayList<ExerciseRecord> getAllExcerciseRecords() {
		ArrayList<ExerciseRecord> arrayList = mExerciseRecordDAO.getAll();
		if (arrayList.size() != 0) {
			Log.d(TAG, "getAllExcerciseRecords(): List has elements: "
					+ arrayList.size());
		} else {
			Log.d(TAG, "getAllExcerciseRecords(): List is empty.");
		}
		return arrayList;
	}

	public ArrayList<ExerciseRecord> getAllExcerciseRecordsInCurrentTraining(
			int nameId) {
		ArrayList<ExerciseRecord> arrayList = mExerciseRecordDAO.getAll(nameId,
				getActiveTraining().getmId());
		if (arrayList.size() != 0) {
			Log.d(TAG,
					"getAllExcerciseRecordsInCurrentTraining(): List has elements: "
							+ arrayList.size());
		} else {
			Log.d(TAG,
					"getAllExcerciseRecordsInCurrentTraining(): List is empty.");
		}
		return arrayList;
	}

	public ArrayList<ExerciseRecord> getAllExcerciseRecords(int nameId) {
		ArrayList<ExerciseRecord> arrayList = mExerciseRecordDAO.getAll(nameId);
		if (arrayList.size() != 0) {
			Log.d(TAG,
					"getAllExcerciseRecordsInCurrentTraining(): List has elements: "
							+ arrayList.size());
		} else {
			Log.d(TAG,
					"getAllExcerciseRecordsInCurrentTraining(): List is empty.");
		}
		return arrayList;
	}

	public ArrayList<ExerciseRecord> getAllExcerciseRecordsForPreviousTraining(
			int nameId) {
		ArrayList<ExerciseRecord> arrayListCurrent = getAllExcerciseRecordsInCurrentTraining(nameId);
		ArrayList<ExerciseRecord> arrayList = getAllExcerciseRecords(nameId);

		int secondlargest = Integer.MIN_VALUE;
		int largest = Integer.MIN_VALUE;
		for (ExerciseRecord exerciseRecord : arrayList) {
			if (largest < exerciseRecord.getmTrainingId()) {
				secondlargest = largest;
				largest = exerciseRecord.getmTrainingId();

			}
			if (secondlargest < exerciseRecord.getmTrainingId()
					&& largest != exerciseRecord.getmTrainingId())
				secondlargest = exerciseRecord.getmTrainingId();

			Log.d(TAG, "ExerciseId: " + exerciseRecord.getmTrainingId());

		}
		Log.d(TAG, "getAllExcerciseRecordsForPreviousTrainingê size: "
				+ arrayList.size());
		Log.d(TAG,
				"getAllExcerciseRecordsForPreviousTraining(): secondLargest: "
						+ secondlargest);

		if (secondlargest != Integer.MIN_VALUE || largest != Integer.MIN_VALUE) {
			arrayList = mExerciseRecordDAO.getAll(nameId,
					(arrayListCurrent.size() > 0 ? secondlargest : largest));
		} else {
			return new ArrayList<ExerciseRecord>();
		}

		return arrayList;
	}

	public ExerciseRecord getLastExerciseRecordInTraining(int trainingId) {
		return mExerciseRecordDAO.getLastInTraining(trainingId);
	}

	public void removeExerciseRecord(int id) {
		mExerciseRecordDAO.remove(id);
	}

	// ################ Trainings operations ##############

	public boolean isActiveTrainingOn() {
		Training training = mTrainingDAO.getActiveTraining();
		return (training != null) ? true : false;
	}

	public Training getActiveTraining() {
		Training training = mTrainingDAO.getActiveTraining();
		return training;
	}

	public long startTraining() {
		SimpleDateFormat startDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateandTime = startDate.format(new Date());

		Training training = new Training(-1, currentDateandTime, "", true);
		return mTrainingDAO.insert(training);
	}

	public void finishTraining() {
		Training training = mTrainingDAO.getActiveTraining();
		if (training != null) {
			SimpleDateFormat endDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String currentDateandTime = endDate.format(new Date());

			training.setmActive(false);
			training.setmEndDate(currentDateandTime);

			mTrainingDAO.update(training);
		} else {
			Log.e(TAG, "finishTraining(): There is not running active training");
		}
	}

	public ArrayList<String> getAllTrainings() {
		ArrayList<Training> arrayList = mTrainingDAO.getAll();
		ArrayList<String> arrayStringList = new ArrayList<String>();
		if (arrayList.size() != 0) {
			for (Training training : arrayList) {
				arrayStringList.add(training.getmStartDate() + " "
						+ training.getmEndDate());
			}
			Log.d(TAG, "getAllTrainings(): List has elements: "
					+ arrayStringList.size());
		} else {
			Log.d(TAG, "getAllTrainings(): List is empty.");
		}
		return arrayStringList;
	}

	public Training getTraining(long id) {
		return mTrainingDAO.get(id);
	}

}
