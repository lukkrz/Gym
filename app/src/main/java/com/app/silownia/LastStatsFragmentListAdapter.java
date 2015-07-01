package com.app.silownia;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.app.silownia.model.ExerciseRecord;

public class LastStatsFragmentListAdapter extends BaseAdapter {
	private static final String TAG = "LastStatsFragmentListAdapter";
	private ArrayList<ExerciseRecord> mArrayList;
	private LayoutInflater inflater;
	private Context mContext;

	public LastStatsFragmentListAdapter(Context context,
			ArrayList<ExerciseRecord> arrayList) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		mArrayList = arrayList;
	}

	public int getCount() {
		return mArrayList.size();
	}

	public Object getItem(int position) {
		return mArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		ExerciseRecord exerciseRecord = (ExerciseRecord) mArrayList
				.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_last_stats,
					parent, false);
			holder.id = position;
			holder.numberOfSerie = (TextView) convertView
					.findViewById(R.id.number_of_serie);
			holder.weight = (TextView) convertView.findViewById(R.id.weight);
			holder.repetition = (TextView) convertView
					.findViewById(R.id.repetition);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.id = position;
		holder.numberOfSerie.setText(Integer.toString(exerciseRecord
				.getmExerciseId()));
		holder.weight.setText(Double.toString(exerciseRecord.getmWeight()));
		holder.repetition.setText(Integer.toString(exerciseRecord
				.getmRepetition()));

		return convertView;
	}

	class ViewHolder {
		int id;
		TextView numberOfSerie;
		TextView weight;
		TextView repetition;
	}
}
