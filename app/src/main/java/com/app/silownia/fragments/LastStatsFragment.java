package com.app.silownia.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.silownia.LastStatsFragmentListAdapter;
import com.app.silownia.R;
import com.app.silownia.SilowniaApp;
import com.app.silownia.R.id;
import com.app.silownia.R.layout;
import com.app.silownia.model.Exercise;
import com.app.silownia.model.ExerciseRecord;

public class LastStatsFragment extends StatsFragment {

	private static final String TAG = "LastStatsFragment";
	private ListView mListView;
	private LastStatsFragmentListAdapter mAdapter;
	private int mIOfSelectedExercise;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_last_stats,
				container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		bindComponentsInFragment();
		setListenersForComponents();
		updateLastStatsListView(-1);
	}

	private void bindComponentsInFragment() {
		mListView = (ListView) getActivity().findViewById(
				R.id.last_stats_listview);
	}

	private void setListenersForComponents() {

	}

	public void updateSelectedExercise(Exercise exercise) {
		if (exercise != null) {
			updateLastStatsListView(exercise.getId());
		} else {
			updateLastStatsListView(-1);
		}
	}

	private void updateLastStatsListView(int idOfSelectedExercise) {
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		mIOfSelectedExercise = idOfSelectedExercise;
		ArrayList<ExerciseRecord> arraylist = app
				.getDatabaseManager()
				.getAllExcerciseRecordsForPreviousTraining(idOfSelectedExercise);
		mAdapter = new LastStatsFragmentListAdapter(getActivity(), arraylist);
		mListView.setAdapter(mAdapter);
		super.setListViewHeightBasedOnChildren(mListView);
	}

}
