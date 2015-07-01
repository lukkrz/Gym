package com.app.silownia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.silownia.fragments.LastStatsFragment;
import com.app.silownia.fragments.NewTrainingAddDialogFragment;
import com.app.silownia.fragments.NewTrainingFragment;
import com.app.silownia.fragments.NewTrainingFinishDialogFragment;
import com.app.silownia.fragments.NewTrainingFragment.OnExerciseSpinnerListener;
import com.app.silownia.fragments.SeriesStatsFragment;
import com.app.silownia.fragments.SeriesStatsFragmentInterface;
import com.app.silownia.model.Exercise;

public class NewTrainingActivity extends FragmentActivity implements
		OnExerciseSpinnerListener,
		NewTrainingAddDialogFragment.NewTrainingAddDialogListener,
		NewTrainingFinishDialogFragment.NewTrainingFinishDialogListener {

	private static final String TAG = "NewTrainingActivity";
	private NewTrainingFragment newTrainingFragment;
	private SeriesStatsFragmentInterface seriesStatsFragment;
	private LastStatsFragment lastStatsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_training);
		lastStatsFragment = (LastStatsFragment) getSupportFragmentManager()
				.findFragmentById(R.id.last_stats_fragment);
		seriesStatsFragment = (SeriesStatsFragment) getSupportFragmentManager()
				.findFragmentById(R.id.series_stats_fragment);
		newTrainingFragment = (NewTrainingFragment) getSupportFragmentManager()
				.findFragmentById(R.id.new_training_fragment);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_training, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_training:
			showDialogAddView();
			return true;
		case R.id.action_exit_training:
			showDialogExitView();
			return true;
		case R.id.action_info_training:
			showDialogStatsView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showDialogStatsView() {

	}

	private void showDialogAddView() {
		newTrainingFragment.showDialogAddView();
	}

	private void showDialogExitView() {
		new AlertDialog.Builder(this)
				.setTitle(
						getResources().getString(R.string.action_exit_training))
				.setMessage(
						getResources().getString(
								R.string.action_exit_training_dialog))
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								saveTraining();
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

	private void saveTraining() {
		SilowniaApp app = (SilowniaApp) getApplication();
		app.getDatabaseManager().finishTraining();
	}

	private void endActivity() {
		super.onBackPressed();
	}

	@Override
	public void onExerciseSelected(Exercise exercise) {
		seriesStatsFragment.updateSelectedExercise(exercise);
		lastStatsFragment.updateSelectedExercise(exercise);
	}

	public void removeSerieFromTrainingOnClickHandler(View v) {
		seriesStatsFragment.removeSerieFromTrainingOnClickHandler(v);
		newTrainingFragment.updateNumberOfSeries();
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
		newTrainingFragment.updateSelectedExercise(exercise);
		seriesStatsFragment.updateSelectedExercise(exercise);
		lastStatsFragment.updateSelectedExercise(exercise);

	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDialogAddExerciseClick(DialogFragment dialog, String input) {
		Log.d(TAG, "onDialogAddExerciseClick() name of input Exercise: "
				+ input);
		SilowniaApp app = (SilowniaApp) getApplication();
		Exercise exercise = app.getDatabaseManager().getExcercise(input);
		if (exercise != null) {
			exercise.setActive(true);
			app.getDatabaseManager().updateExercise(exercise);
		} else {
			exercise = new Exercise(-1, input, true);
			app.getDatabaseManager().insertExcercise(exercise);
		}
		newTrainingFragment.updateSelectedExercise(exercise);
		seriesStatsFragment.updateSelectedExercise(exercise);
		lastStatsFragment.updateSelectedExercise(exercise);
	}

	@Override
	public void onDialogFinishTrainingClick(DialogFragment dialog) {
		saveTraining();
		endActivity();

	}

}
