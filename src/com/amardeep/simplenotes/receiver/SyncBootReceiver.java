package com.amardeep.simplenotes.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class SyncBootReceiver extends BroadcastReceiver {

	   @Override
	   public void onReceive(Context context, Intent intent) {
	      Log.i("SyncBootReceiver :", "DealBootReceiver invoked, configuring AlarmManager");
	      AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	      PendingIntent pendingIntent =
	               PendingIntent.getBroadcast(context, 0, new Intent(context, SyncServiceReceiver.class), 0);

	      // use inexact repeating which is easier on battery (system can phase events and not wake at exact times)
	      alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+5000,
	               10000, pendingIntent);
	   }
}
