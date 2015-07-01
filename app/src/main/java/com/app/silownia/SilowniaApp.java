package com.app.silownia;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.app.silownia.database.manager.DatabaseManager;

public class SilowniaApp extends Application {
	
	private DatabaseManager mDatabaseManager;

	public DatabaseManager getDatabaseManager() {
		return mDatabaseManager;
	}
	
	public void onCreate() {
		super.onCreate();
		
		mDatabaseManager = new DatabaseManager(this);
	}
	
	public static long getAppFirstInstallTime(Context context) {
		PackageInfo packageInfo;
		try {
			if (Build.VERSION.SDK_INT > 8/* Build.VERSION_CODES.FROYO */) {
				packageInfo = context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0);
				return packageInfo.firstInstallTime;
			} else {
				ApplicationInfo appInfo = context.getPackageManager()
						.getApplicationInfo(context.getPackageName(), 0);
				String sAppFile = appInfo.sourceDir;
				return new File(sAppFile).lastModified();
			}
		} catch (NameNotFoundException e) {
			return 0;
		}
	}
}
