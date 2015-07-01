package com.app.silownia;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.app.silownia.HistoryFragmentListAdapter.ViewHolder;
import com.app.silownia.database.manager.DatabaseManager;
import com.app.silownia.model.Exercise;

public class ListOfExerciseFragmentListAdapter extends BaseAdapter {

	private ArrayList<Exercise> mArrayList;
	private LayoutInflater inflater;
	private Context mContext;

	public ListOfExerciseFragmentListAdapter(Context context,
			ArrayList<Exercise> arrayList) {
		inflater = LayoutInflater.from(context);
		mArrayList = arrayList;
		mContext = context;
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
			convertView = inflater.inflate(R.layout.list_item_list_of_exercise,
					parent, false);
			holder.nameOfExercise = (TextView) convertView
					.findViewById(R.id.name_of_exercise);
			holder.checkboxOfExercise = (CheckBox) convertView
					.findViewById(R.id.checkbox_of_exercise);

			holder.checkboxOfExercise
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							CheckBox cb = (CheckBox) v;
							Exercise exercise = (Exercise) cb.getTag();
							Toast.makeText(
									mContext.getApplicationContext(),
									"Clicked on Checkbox: " + cb.getText()
											+ " is " + cb.isChecked(),
									Toast.LENGTH_LONG).show();
							exercise.setActive((cb.isChecked()));
							SilowniaApp app = (SilowniaApp) mContext
									.getApplicationContext();
							app.getDatabaseManager().updateExercise(exercise);
						}
					});
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Exercise exercise = mArrayList.get(position);
		holder.nameOfExercise.setText(" (" + exercise.getName() + ")");
		holder.checkboxOfExercise.setText(exercise.getName());
		holder.checkboxOfExercise.setChecked(exercise.getActive());
		holder.checkboxOfExercise.setTag(exercise);

		return convertView;
	}

	class ViewHolder {
		TextView nameOfExercise;
		CheckBox checkboxOfExercise;
	}

}
