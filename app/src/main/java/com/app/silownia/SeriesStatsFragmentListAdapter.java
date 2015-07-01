package com.app.silownia;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.app.silownia.model.ExerciseRecord;

public class SeriesStatsFragmentListAdapter extends BaseAdapter {

	private static final String TAG = "SeriesStatsFragmentListAdapter";
	private ArrayList<ExerciseRecord> mArrayList;
	private LayoutInflater inflater;
	private Context mContext;

	public SeriesStatsFragmentListAdapter(Context context,
			ArrayList<ExerciseRecord> arrayList) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		mArrayList = arrayList;
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
		ViewHolderButton holderButton;

		ExerciseRecord exerciseRecord = (ExerciseRecord) mArrayList
				.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_series, parent,
					false);
			holder.id = position;
			holder.numberOfSerie = (TextView) convertView
					.findViewById(R.id.number_of_serie);
			holder.weight = (TextView) convertView.findViewById(R.id.weight);
			holder.repetition = (TextView) convertView
					.findViewById(R.id.repetition);
			holder.removeButton = (Button) convertView
					.findViewById(R.id.remove_serie);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position != (mArrayList.size() - 1)) {
			holder.removeButton.setEnabled(false);
		}

		holderButton = new ViewHolderButton();

		holderButton.idInDB = exerciseRecord.getmId();
		holderButton.position = position;
		holder.removeButton.setTag(holderButton);

		holder.id = position;
		holder.numberOfSerie.setText(Integer.toString(exerciseRecord
				.getmExerciseId()));
		holder.weight.setText(Double.toString(exerciseRecord.getmWeight()));
		holder.repetition.setText(Integer.toString(exerciseRecord
				.getmRepetition()));

		return convertView;
	}

	public class ViewHolderButton {
		public int position;
		public int idInDB;
	}

	class ViewHolder {
		int id;
		TextView numberOfSerie;
		TextView weight;
		TextView repetition;
		Button removeButton;
	}

}
