package sar.id.j2d.merge;


import android.app.*;
import android.os.*;

import android.view.Display;
import android.widget.LinearLayout;
import android.view.WindowManager;
import android.content.Context;
import java.io.File;
import java.io.IOException;

import java.lang.Process;


import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.Process;


public class MyApplication extends Application {
    
   
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
		
		
		public void onCreate() {
		
		
		
		  this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable throwable) {
				Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.putExtra("error", Log.getStackTraceString(throwable));
				PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT);

				AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);

				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);

				uncaughtExceptionHandler.uncaughtException(thread, throwable);
			}
		});
		super.onCreate();
		
}
}
