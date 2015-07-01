package com.app.silownia.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.silownia.R;
import com.app.silownia.SeriesStatsFragmentListAdapter;
import com.app.silownia.SilowniaApp;
import com.app.silownia.R.id;
import com.app.silownia.R.layout;
import com.app.silownia.SeriesStatsFragmentListAdapter.ViewHolderButton;
import com.app.silownia.model.Exercise;
import com.app.silownia.model.ExerciseRecord;

public class SeriesStatsFragment extends StatsFragment implements
		SeriesStatsFragmentInterface {

	private static final String TAG = "SeriesStatsFragment";
	private ListView mListView;
	private SeriesStatsFragmentListAdapter mAdapter;
	private int mIOfSelectedExercise;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_series_stats,
				container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		bindComponentsInFragment();
		setListenersForComponents();
		updateSeriesStatsListView(-1);

	}

	private void bindComponentsInFragment() {
		mListView = (ListView) getActivity().findViewById(
				R.id.series_stats_listview);
	}

	private void setListenersForComponents() {

	}

	public void updateSelectedExercise(Exercise exercise) {
		if (exercise != null) {
			updateSeriesStatsListView(exercise.getId());
		} else {
			updateSeriesStatsListView(-1);
		}
	}

	private void updateSeriesStatsListView(int idOfSelectedExercise) {
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		mIOfSelectedExercise = idOfSelectedExercise;
		ArrayList<ExerciseRecord> arraylist = app.getDatabaseManager()
				.getAllExcerciseRecordsInCurrentTraining(idOfSelectedExercise);
		mAdapter = new SeriesStatsFragmentListAdapter(getActivity(), arraylist);
		mListView.setAdapter(mAdapter);
		super.setListViewHeightBasedOnChildren(mListView);
	}

	public void removeSerieFromTrainingOnClickHandler(View v) {
		ViewHolderButton viewHolderButton = (ViewHolderButton) v.getTag();
		SilowniaApp app = (SilowniaApp) getActivity().getApplicationContext();
		app.getDatabaseManager().removeExerciseRecord(viewHolderButton.idInDB);
		Toast toast = Toast.makeText(getActivity().getApplicationContext(),
				"Delete ExerciseRecord id: " + viewHolderButton.idInDB,
				Toast.LENGTH_LONG);
		toast.show();
		updateSeriesStatsListView(mIOfSelectedExercise);

		Log.d(TAG, "removeButton onClick(): Delete ExerciseRecord id: "
				+ viewHolderButton.idInDB + " position: "
				+ viewHolderButton.position);
	}

}
