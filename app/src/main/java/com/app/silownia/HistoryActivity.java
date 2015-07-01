package com.app.silownia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class HistoryActivity extends FragmentActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_history_of_trainings);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.new_training, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
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
			
		}
		
		private void endActivity() {
			
		}

}

