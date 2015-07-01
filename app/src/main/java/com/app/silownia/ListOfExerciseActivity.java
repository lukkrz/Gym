package com.app.silownia;

import com.app.silownia.fragments.ListOfExerciseFragment;
import com.app.silownia.fragments.NewTrainingFragment;
import com.app.silownia.fragments.NewTrainingAddDialogFragment.NewTrainingAddDialogListener;
import com.app.silownia.model.Exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ListOfExerciseActivity extends FragmentActivity implements
		NewTrainingAddDialogListener {

	private static String TAG = "ListOfExerciseActivity";
	private ListOfExerciseFragment listOfExerciseFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_exercise);

		listOfExerciseFragment = (ListOfExerciseFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_list_of_exercise);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_of_exercise_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_training:
			showDialogAddView();
			return true;
		case R.id.action_confirm_edit:
			showDialogExitView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showDialogAddView() {
		listOfExerciseFragment.showDialogAddView();
	}

	private void showDialogExitView() {
		new AlertDialog.Builder(this)
				.setTitle(
						getResources().getString(R.string.action_exit_exercise))
				.setMessage(
						getResources().getString(
								R.string.action_exit_list_of_exercise_dialog))
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								endActivity();
							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
							}
						}).setIcon(R.drawable.ic_action_cancel_light).show();
	}

	private void endActivity() {

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String input) {
		Log.d(TAG, "onDialogPositiveClick() name of input Exercise: " + input);
		SilowniaApp app = (SilowniaApp) getApplication();
		Exercise exercise = app.getDatabaseManager().getExcercise(input);
		if (exercise != null) {
			exercise.setActive(true);
			app.getDatabaseManager().updateExercise(exercise);
		} else {
			exercise = new Exercise(-1, input, true);
			app.getDatabaseManager().insertExcercise(exercise);
		}
		listOfExerciseFragment.updateListOfExerciseListView();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub

	}

}
