package com.app.silownia.fragments;

import com.app.silownia.model.Exercise;

import android.view.View;

public interface SeriesStatsFragmentInterface {
	void updateSelectedExercise(Exercise exercise);
	void removeSerieFromTrainingOnClickHandler(View v);
}
