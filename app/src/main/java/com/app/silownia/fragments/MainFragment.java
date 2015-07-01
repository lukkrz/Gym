package com.app.silownia.fragments;

import java.util.ArrayList;

import com.app.silownia.HistoryActivity;
import com.app.silownia.ListOfExerciseActivity;
import com.app.silownia.NewTrainingActivity;
import com.app.silownia.R;
import com.app.silownia.SilowniaApp;
import com.app.silownia.R.id;
import com.app.silownia.R.layout;
import com.app.silownia.R.string;
import com.app.silownia.model.Exercise;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

	private static final String TAG = "MainFragment";
	Button mNewTrainingButton;
	Button mLastTrainingButton;
	Button mHistoryOfTrainingButton;
	Button mListOfExerciseButton;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_main, container,
				false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bindComponentsInFragment();
		setListenersForComponents();
	}

	@Override
	public void onStart() {
		super.onStart();
		setTrainingState();
	}

	private void bindComponentsInFragment() {
		mNewTrainingButton = (Button) getActivity().findViewById(
				R.id.new_training_button);
		mLastTrainingButton = (Button) getActivity().findViewById(
				R.id.last_training_button);
		mHistoryOfTrainingButton = (Button) getActivity().findViewById(
				R.id.history_of_training);
		mListOfExerciseButton = (Button) getActivity().findViewById(
				R.id.list_of_exercise);
	}

	private void setTrainingState() {
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();

		if (app.getDatabaseManager().isActiveTrainingOn()) {
			mNewTrainingButton.setText(getResources().getString(
					R.string.resume_training));
			mNewTrainingButton.setBackgroundColor(Color.BLACK);
			mNewTrainingButton.setTextColor(Color.WHITE);
			Log.d(TAG, "Active training on");
		} else {
			mNewTrainingButton.setText(getResources().getString(
					R.string.new_training));
			mNewTrainingButton.setBackgroundColor(Color.WHITE);
			mNewTrainingButton.setTextColor(Color.BLACK);
			Log.d(TAG, "Active training off");
		}
	}

	private void setListenersForComponents() {

		mNewTrainingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				newTrainingIntent();
				newTrainingStart();
			}
		});

		mLastTrainingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lastTrainingIntent();
			}
		});

		mHistoryOfTrainingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				historyOfTrainingIntent();
			}
		});
		
		mListOfExerciseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listOfExerciseIntent();
			}
		});
	}

	private void newTrainingIntent() {
		Intent intent = new Intent(getActivity(), NewTrainingActivity.class);
		startActivity(intent);
	}

	private void lastTrainingIntent() {
		//Intent intent = new Intent(getActivity(), NewTrainingActivity.class);
		//startActivity(intent);
	}

	private void historyOfTrainingIntent() {
		Intent intent = new Intent(getActivity(), HistoryActivity.class);
		startActivity(intent);
	}
	
	private void listOfExerciseIntent() {
		Intent intent = new Intent(getActivity(), ListOfExerciseActivity.class);
		startActivity(intent);
	}

	private void newTrainingStart() {
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		long trainingId = app.getDatabaseManager().startTraining();
		Log.d(TAG, "newTrainingStart(): Training added with id: " + trainingId);
		if (trainingId>-1) {
			ArrayList<Exercise> exerciseArray = app.getDatabaseManager().getAllExcercises();
			app.getDatabaseManager().updateExerciseState(exerciseArray, true);
			Log.d(TAG, "newTrainingStart(): reset state of Exercises");
		}
	}

}