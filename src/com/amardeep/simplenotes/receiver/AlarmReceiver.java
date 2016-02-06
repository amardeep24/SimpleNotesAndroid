package com.amardeep.simplenotes.receiver;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amardeep.simplenotes.R;
import com.amardeep.simplenotes.activity.ViewNoteActivity;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("receiver","onReceives");
		String reminderTitle=intent.getStringExtra("reminderTitle");
		String noteId=intent.getStringExtra("noteId");
		Intent resultIntent = new Intent(context, ViewNoteActivity.class);
		resultIntent.putExtra("noteId", noteId);
		PendingIntent resultPendingIntent =PendingIntent.getActivity(context,0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Reminder for:")
		        .setContentText(reminderTitle)
		        .setAutoCancel(true)
		        .setContentIntent(resultPendingIntent);
		mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
		NotificationManager mNotificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Log.i("receiver","set");
		int mId=0001;
		mNotificationManager.notify(mId, mBuilder.build());
		
	}

}
