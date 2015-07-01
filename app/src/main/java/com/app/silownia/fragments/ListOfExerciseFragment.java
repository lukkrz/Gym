package com.app.silownia.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.silownia.ListOfExerciseFragmentListAdapter;
import com.app.silownia.R;
import com.app.silownia.SilowniaApp;
import com.app.silownia.model.Exercise;

public class ListOfExerciseFragment extends Fragment {

	private static final String TAG = "ListOfExerciseFragment";
	private ListView mListView;
	private ListOfExerciseFragmentListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_list_of_exercise,
				container, false);

		mListView = (ListView) rootView
				.findViewById(R.id.list_of_exercise_listview);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		SilowniaApp app = (SilowniaApp) getActivity().getApplication();

		bindComponentsInFragment();
		setListenersForComponents();
		updateListOfExerciseListView();

	}

	private void bindComponentsInFragment() {
		mListView = (ListView) getActivity().findViewById(
				R.id.list_of_exercise_listview);
	}

	private void setListenersForComponents() {
		Button myButton = (Button) getActivity()
				.findViewById(R.id.findSelected);
		myButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				StringBuffer responseText = new StringBuffer();
				responseText.append("The following were selected...\n");

				SilowniaApp app = (SilowniaApp) getActivity().getApplication();
				ArrayList<Exercise> exerciseList = app.getDatabaseManager()
						.getAllExcercises();
				for (int i = 0; i < exerciseList.size(); i++) {
					Exercise exercise = exerciseList.get(i);
					if (exercise.getActive()) {
						responseText.append("\n" + exercise.getName());
					}
				}

				Toast.makeText(getActivity().getApplicationContext(),
						responseText, Toast.LENGTH_LONG).show();

			}
		});
	}

	public void updateListOfExerciseListView() {
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		ArrayList<Exercise> arraylist = app.getDatabaseManager()
				.getAllExcercises();
		mAdapter = new ListOfExerciseFragmentListAdapter(getActivity(),
				arraylist);
		mListView.setAdapter(mAdapter);
	}

	public void showDialogAddView() {
		DialogFragment newFragment = new NewTrainingAddDialogFragment();
		newFragment.show(getActivity().getSupportFragmentManager(), "AddNewExercise");
	}
	
}
