package com.app.silownia.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.silownia.NewTrainingActivity;
import com.app.silownia.R;
import com.app.silownia.SilowniaApp;
import com.app.silownia.model.Exercise;
import com.app.silownia.model.ExerciseRecord;

public class NewTrainingFragment extends Fragment {

	private static final double INTERVAL_WEIGHT = 2.5;
	private static final int INTERVAL_REPETITION = 1;
	private static String TAG = "NewTrainingFragment";
	private static String NEXT_EXERCISE_INTENT = "next_exercise";

	OnExerciseSpinnerListener mCallback;

	Spinner mChooseExercise;
	EditText mWeight;
	EditText mRepetition;
	Button mAddSerie;
	Button mFinishExercise;
	Button mExercseWeightPlus;
	Button mExercseWeightMinus;
	Button mExercseRepetitionPlus;
	Button mExercseRepetitionMinus;
	int mNumberOfSeries = 1;

	// Container Activity must implement this interface
	public interface OnExerciseSpinnerListener {
		public void onExerciseSelected(Exercise exercise);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (OnExerciseSpinnerListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnExerciseSpinnerListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_new_training,
				container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		bindComponentsInFragment();
		setSpinnerAdapter();
		setListenersForComponents();

		if (savedInstanceState != null) {
			mNumberOfSeries = savedInstanceState.getInt("number_of_series", 1);
		} else {
			SilowniaApp app = (SilowniaApp) getActivity().getApplication();
			if (app.getDatabaseManager().isActiveTrainingOn()) {
				updateNumberOfSeries();
			}
		}

		if (isSpinnerAdapterValid()) {
		} else {
			showDialogFinishTrainingView();
		}

		getActivity().getIntent().removeExtra(NEXT_EXERCISE_INTENT);
	}

	private boolean inSeriesMode() {
		return mNumberOfSeries > 1;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("number_of_series", mNumberOfSeries);
	}

	private void bindComponentsInFragment() {
		mChooseExercise = (Spinner) getActivity().findViewById(
				R.id.exercise_spinner);
		mWeight = (EditText) getActivity().findViewById(
				R.id.exercise_weight_edit);
		mRepetition = (EditText) getActivity().findViewById(
				R.id.exercise_repetition_edit);
		mAddSerie = (Button) getActivity().findViewById(
				R.id.exercise_add_serie_button);
		mFinishExercise = (Button) getActivity().findViewById(
				R.id.exercise_confirm_button);
		mExercseWeightPlus = (Button) getActivity().findViewById(
				R.id.exercise_weight_edit_plus);
		mExercseWeightMinus = (Button) getActivity().findViewById(
				R.id.exercise_weight_edit_minus);
		mExercseRepetitionPlus = (Button) getActivity().findViewById(
				R.id.exercise_repetition_edit_plus);
		mExercseRepetitionMinus = (Button) getActivity().findViewById(
				R.id.exercise_repetition_edit_minus);
	}

	private void setSpinnerAdapter() {
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		ArrayAdapter listOfExerciseAdapter = new ArrayAdapter(getActivity(),
				R.layout.spinner_new_training_add, app.getDatabaseManager()
						.getAllActiveExcercises());
		listOfExerciseAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mChooseExercise.setAdapter(listOfExerciseAdapter);
	}

	private void setListenersForComponents() {
		mChooseExercise.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				Exercise exercise = (Exercise) parentView
						.getItemAtPosition(position);
				mCallback.onExerciseSelected(exercise);
				getNumberOfSeries(exercise);
				setSeriesView();
				Log.d(TAG, "onItemSelected: " + position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				mAddSerie.setEnabled(false);
				mFinishExercise.setEnabled(false);
			}
		});

		mAddSerie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isDataInComponentsValid()) {
					addNewExcerciseToCurrentTraining();
					addNewExcerciseToSerie();
					setSeriesView();
				} else {
					Toast toast = Toast.makeText(getActivity(), "Invalid data",
							Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});

		mFinishExercise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isDataInComponentsValid()) {
					addNewExcerciseToCurrentTraining();
					addNewExcerciseToSerie();
					finishCurrentExercise();
					newTrainingIntent();
				} else if (inSeriesMode()) {
					finishCurrentExercise();
					newTrainingIntent();
					Toast toast = Toast.makeText(getActivity(),
							"Next exercise", Toast.LENGTH_LONG);
					toast.show();
				} else {
					Toast toast = Toast.makeText(getActivity(), "Invalid data",
							Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});

		mExercseWeightPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mWeight.getText().toString().isEmpty()) {
					double weight = Double.parseDouble(mWeight.getText()
							.toString());
					mWeight.setText(String.valueOf(weight + INTERVAL_WEIGHT));
				} else {
					mWeight.setText(String
							.valueOf(0));
				}
			}
		});

		mExercseWeightMinus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mWeight.getText().toString().isEmpty()) {
					double weight = Double.parseDouble(mWeight.getText()
							.toString());
					if (weight - INTERVAL_WEIGHT >= 0)
						mWeight.setText(String
								.valueOf(weight - INTERVAL_WEIGHT));
				} else {
					mWeight.setText(String
							.valueOf(0));
				}
			}
		});

		mExercseRepetitionPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mRepetition.getText().toString().isEmpty()) {
					int repetition = Integer.parseInt(mRepetition
							.getText().toString());
					mRepetition.setText(String.valueOf(repetition
							+ INTERVAL_REPETITION));
				} else {
					mRepetition.setText(String
							.valueOf(0));
				}
			}
		});

		mExercseRepetitionMinus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mRepetition.getText().toString().isEmpty()) {
					int repetition = Integer.parseInt(mRepetition
							.getText().toString());
					if (repetition - INTERVAL_REPETITION >= 0)
						mRepetition.setText(String.valueOf(repetition
								- INTERVAL_REPETITION));
				} else {
					mRepetition.setText(String
							.valueOf(0));
				}
			}
		});
	}

	private void addNewExcerciseToCurrentTraining() {
		ExerciseRecord excerciseRecord = prepareData();
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		long excerciseRecordId = app.getDatabaseManager()
				.insertExcerciseRecord(excerciseRecord);
		if (excerciseRecordId > -1) {
			Exercise exercise = app.getDatabaseManager().getExcercise(
					excerciseRecord.getmExerciseNameId());
			Toast toast = Toast.makeText(getActivity(), exercise.getName()
					+ " [" + mNumberOfSeries + "] was added succesfully.",
					Toast.LENGTH_LONG);
			toast.show();
		} else {
			Toast toast = Toast.makeText(getActivity(), "Error during adding "
					+ excerciseRecord.getmExerciseNameId() + ".",
					Toast.LENGTH_LONG);
			toast.show();
		}
	}

	private ExerciseRecord prepareData() {
		Exercise spinnerData = getSelectedExercise();
		Log.d(TAG, "prepareData()" + spinnerData);
		double weightData = Double.parseDouble(mWeight.getText().toString());
		int repetitionData = Integer.parseInt(mRepetition.getText().toString());
		ExerciseRecord excerciseRecord = new ExerciseRecord(-1,
				mNumberOfSeries, spinnerData.getId(), weightData,
				repetitionData, -1);
		return excerciseRecord;
	}

	private void finishCurrentExercise() {
		Exercise exercise = getSelectedExercise();
		exercise.setActive(false);
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		app.getDatabaseManager().updateExercise(exercise);
	}

	private void newTrainingIntent() {
		Intent intent = new Intent(getActivity(), NewTrainingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(NEXT_EXERCISE_INTENT, true);
		startActivity(intent);
	}

	private void addNewExcerciseToSerie() {
		mNumberOfSeries++;
		Exercise exercise = getSelectedExercise();
		mCallback.onExerciseSelected(exercise);
	}

	private Exercise getSelectedExercise() {
		return (Exercise) mChooseExercise.getSelectedItem();
	}

	private void setSeriesView() {
		mWeight.setText("");
		mRepetition.setText("");
		mAddSerie.setText(getResources().getString(R.string.exercise_serie)
				+ " " + mNumberOfSeries);
	}

	private boolean isDataInComponentsValid() {
		return !(isEmpty(mWeight) || isEmpty(mRepetition) || getSelectedExercise() == null);
	}

	private boolean isSpinnerAdapterValid() {
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		ArrayList<Exercise> arrayList = app.getDatabaseManager()
				.getAllActiveExcercises();
		return (arrayList.size() > 0) ? true : false;
	}

	private boolean isEmpty(EditText etText) {
		if (etText.getText().toString().trim().length() > 0) {
			return false;
		} else {
			return true;
		}
	}

	private void getNumberOfSeries(Exercise exercise) {
		if (exercise == null) {
			mNumberOfSeries = 1;
			return;
		}
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		ArrayList<ExerciseRecord> exerciseArray = app.getDatabaseManager()
				.getAllExcerciseRecordsInCurrentTraining(exercise.getId());
		ExerciseRecord exerciseRecord = null;
		if (exerciseArray.size() > 0) {
			exerciseRecord = exerciseArray.get(exerciseArray.size() - 1);
		}
		if (exerciseRecord != null) {
			mNumberOfSeries = exerciseRecord.getmExerciseId() + 1;
		} else {
			mNumberOfSeries = 1;
		}
	}

	public void updateNumberOfSeries() {
		Exercise exercise = getSelectedExercise();
		getNumberOfSeries(exercise);
		setSeriesView();
	}

	public void showDialogAddView() {
		DialogFragment newFragment = new NewTrainingAddDialogFragment();
		newFragment.show(getActivity().getSupportFragmentManager(),
				"AddNewExercise");
	}

	public void showDialogFinishTrainingView() {
		DialogFragment newFragment = new NewTrainingFinishDialogFragment();
		newFragment.show(getActivity().getSupportFragmentManager(),
				"FinishTraining");
	}

	public void updateSelectedExercise(Exercise exercise) {
		setSpinnerAdapter();
		if (isSpinnerAdapterValid()) {
			Log.d(TAG, "index: "
					+ getIndex(mChooseExercise, exercise.getName()));
			mChooseExercise.setSelection(getIndex(mChooseExercise,
					exercise.getName()));
			Log.d(TAG, "updateSelectedExercise() + " + exercise.getName()
					+ " id: " + exercise.getId());
		} else {
			showDialogFinishTrainingView();
		}

	}

	private int getIndex(Spinner spinner, String myString) {
		int index = 0;
		for (int i = 0; i < spinner.getCount(); i++) {

			if (spinner.getItemAtPosition(i).toString().equals(myString)) {
				index = i;
				Log.d(TAG, "FOUND");
			}
			Log.d(TAG, "Spinner count: " + spinner.getCount() + " string: "
					+ spinner.getItemAtPosition(i));
		}
		return index;
	}
}
