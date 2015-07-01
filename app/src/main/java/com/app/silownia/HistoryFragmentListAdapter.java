package com.app.silownia;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.silownia.database.manager.DatabaseManager;
import com.app.silownia.model.Exercise;
import com.app.silownia.model.ExerciseRecord;
import com.app.silownia.model.Training;

public class HistoryFragmentListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

	private ArrayList<ExerciseRecord> mArrayList;
	private LayoutInflater inflater;
	private DatabaseManager mDb;
	
	public HistoryFragmentListAdapter(Context context, ArrayList<ExerciseRecord> arrayList, DatabaseManager db) {
		inflater = LayoutInflater.from(context);
		mArrayList = arrayList;
		mDb = db;
	}

	@Override
	public int getCount() {
		return mArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return mArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_history, parent,
					false);
			holder.nameOfExercise = (TextView) convertView
					.findViewById(R.id.name_of_exercise);
			holder.weight = (TextView) convertView.findViewById(R.id.weight);
			holder.repetition = (TextView) convertView
					.findViewById(R.id.repetition);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ExerciseRecord exerciseRecord = (ExerciseRecord) mArrayList
				.get(position);
		Exercise exercise = (Exercise) mDb.getExcercise(exerciseRecord.getmExerciseNameId());
		holder.nameOfExercise.setText(exercise.getName());
		holder.weight.setText(Double.toString(exerciseRecord.getmWeight()));
		holder.repetition.setText(Integer.toString(exerciseRecord.getmRepetition()));
		
		return convertView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;
		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.list_item_history_header,
					parent, false);
			holder.idOfTraining = (TextView) convertView
					.findViewById(R.id.id_of_training);
			holder.dateOfTrainingStart = (TextView) convertView
					.findViewById(R.id.start_date_of_training);
			holder.dateOfTrainingEnd = (TextView) convertView
					.findViewById(R.id.end_date_of_training);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		
		ExerciseRecord exerciseRecord = (ExerciseRecord) mArrayList
				.get(position);
		int trainingId = exerciseRecord
				.getmTrainingId();
		Training training = mDb.getTraining(trainingId);
		holder.idOfTraining.setText(Integer.toString(training.getmId()));
		holder.dateOfTrainingStart.setText(training.getmStartDate());
		holder.dateOfTrainingEnd.setText(training.getmEndDate());
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		ExerciseRecord exerciseRecord = (ExerciseRecord) mArrayList
				.get(position);
		return exerciseRecord.getmTrainingId();
	}

	class HeaderViewHolder {
		TextView idOfTraining;
		TextView dateOfTrainingStart;
		TextView dateOfTrainingEnd;
	}

	class ViewHolder {
		TextView nameOfExercise;
		TextView weight;
		TextView repetition;
	}

}
