package com.app.silownia.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.app.silownia.R;
import com.app.silownia.SilowniaApp;
import com.app.silownia.components.MyAutoCompleteTextView;

public class NewTrainingAddDialogFragment extends DialogFragment {

	MyAutoCompleteTextView mTextView;
	
	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface NewTrainingAddDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog, String input);
		public void onDialogNegativeClick(DialogFragment dialog);
	}

	// Use this instance of the interface to deliver action events
	NewTrainingAddDialogListener mListener;

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			mListener = (NewTrainingAddDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Build the dialog and set up the button click handlers
		SilowniaApp app = (SilowniaApp) getActivity().getApplication();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		ArrayAdapter adapter = new ArrayAdapter(getActivity(),
				android.R.layout.simple_dropdown_item_1line, app.getDatabaseManager()
				.getAllExcercises());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_new_training_add, null);

		builder.setView(view);

		builder.setMessage(R.string.dialog_new_training_add_title)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Send the positive button event back to the
								// host activity
								mListener
										.onDialogPositiveClick(NewTrainingAddDialogFragment.this, mTextView.getText().toString());
							}
						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Send the negative button event back to the
								// host activity
								mListener
										.onDialogNegativeClick(NewTrainingAddDialogFragment.this);
							}
						});

		mTextView = (MyAutoCompleteTextView) view
				.findViewById(R.id.exercise_name);
		mTextView.setAdapter(adapter);
		
		return builder.create();
	}

}