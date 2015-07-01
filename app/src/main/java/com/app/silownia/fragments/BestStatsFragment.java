package com.app.silownia.fragments;

import com.app.silownia.R;
import com.app.silownia.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BestStatsFragment extends StatsFragment {
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

		/*bindComponentsInFragment();
		setSpinnerAdapter();
		setListenersForComponents();*/
	}
}
