package com.amardeep.simplenotes.sync;

import com.amardeep.simplenotes.activity.NotepadMenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

public class SyncTask extends AsyncTask<Activity,Void,String> {
Activity notePadMenuActivity;
	@Override
	protected String doInBackground(Activity... activity) {
		this.notePadMenuActivity=activity[0];
		String response="";
		SyncNotes syncService=new SyncNotes();
		response=syncService.syncNotesWeb(activity[0]);
		return response;
	}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(this.notePadMenuActivity!=null)
		{
			this.notePadMenuActivity.startActivity(new Intent(notePadMenuActivity,NotepadMenuActivity.class));
		}
	}

}
