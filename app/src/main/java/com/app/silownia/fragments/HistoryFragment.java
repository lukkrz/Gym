package com.app.silownia.fragments;

import java.util.ArrayList;

import com.app.silownia.HistoryFragmentListAdapter;
import com.app.silownia.R;
import com.app.silownia.SilowniaApp;
import com.app.silownia.R.id;
import com.app.silownia.R.layout;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HistoryFragment extends Fragment {

	private static final String TAG = "HistoryFragment";
	private StickyListHeadersListView listView;
	private HistoryFragmentListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_history, container,
				false);

		listView = (StickyListHeadersListView) rootView.findViewById(R.id.list);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		SilowniaApp app = (SilowniaApp) getActivity().getApplication();

		ArrayList arraylist = app.getDatabaseManager().getAllExcerciseRecords();
		Log.d(TAG, "OnActivityCreated(): Size of list: " + arraylist.size());
		mAdapter = new HistoryFragmentListAdapter(getActivity(), app.getDatabaseManager()
				.getAllExcerciseRecords(), app.getDatabaseManager());
		listView.setAdapter(mAdapter);

		/*
		 * mAdapter = new ArrayAdapter<String>(getActivity(),
		 * android.R.layout.simple_list_item_1,
		 * app.getDatabaseManager().getAllTrainings());
		 * 
		 * setListAdapter(mAdapter);
		 */

		/*
		 * mAdapter = new CustomArrayAdapter(getActivity(), android.R.id.list);
		 * listView.setAdapter(mAdapter);
		 */
		/*
		 * int num = getArguments().getInt(ARG_SECTION_NUMBER); // GlobalList is
		 * a class that holds global variables, arrays etc // getMenuCategories
		 * returns global arraylist which is initialized in GlobalList class
		 * menuItems = GlobalList.getMenuCategories().get(num).getMenu();
		 * mAdapter = new CustomArrayAdapter(getActivity(), android.R.id.list,
		 * menuItems); listView.setAdapter(mAdapter);
		 */
	}
}
