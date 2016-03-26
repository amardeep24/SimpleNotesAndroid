package com.amardeep.simplenotes.receiver;

import com.amardeep.simplenotes.service.SyncService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract.Constants;
import android.util.Log;

public class SyncServiceReceiver extends BroadcastReceiver {

	// onReceive must be very quick and not block, so it just fires up a Service
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("SyncServiceReceiver :",
				"DealAlarmReceiver invoked, starting DealService in background");
		context.startService(new Intent(context, SyncService.class));
	}
}