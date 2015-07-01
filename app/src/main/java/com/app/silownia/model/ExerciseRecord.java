package com.app.silownia.model;

public class ExerciseRecord {

	private int mId;
	private int mExerciseId; // currently count of series is here
	private int mExerciseNameId;
	private double mWeight;
	private int mRepetition;
	private int mTrainingId;

	public ExerciseRecord(int id, int exerciseId, int nameId, double weight, int repetition,
			int trainingId) {
		super();
		this.mId = id;
		this.mExerciseId = exerciseId;
		this.mExerciseNameId = nameId;
		this.mWeight = weight;
		this.mRepetition = repetition;
		this.mTrainingId = trainingId;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getmExerciseId() {
		return mExerciseId;
	}

	public void setmExerciseId(int id) {
		this.mExerciseId = id;
	}
	
	public int getmExerciseNameId() {
		return mExerciseNameId;
	}

	public void setmExerciseNameId(int nameId) {
		this.mExerciseNameId = nameId;
	}

	public double getmWeight() {
		return mWeight;
	}

	public void setmWeight(float mWeight) {
		this.mWeight = mWeight;
	}

	public int getmRepetition() {
		return mRepetition;
	}

	public void setmRepetition(int mRepetition) {
		this.mRepetition = mRepetition;
	}

	public int getmTrainingId() {
		return mTrainingId;
	}

	public void setmTrainingId(int mTrainingId) {
		this.mTrainingId = mTrainingId;
	}

}
