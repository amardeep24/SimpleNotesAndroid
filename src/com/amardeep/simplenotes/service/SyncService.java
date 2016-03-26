package com.amardeep.simplenotes.service;

import com.amardeep.simplenotes.sync.SyncNotes;

import android.app.IntentService;
import android.content.Intent;

public class SyncService extends IntentService{

	public SyncService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		SyncNotes syncService=new SyncNotes();
		syncService.syncNotesWeb(getApplicationContext());
		syncService.syncNotesPhone(getApplicationContext());
	}

}
