package com.amardeep.simplenotes.sync;

import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.constants.SimpleNotesConstants;
import com.amardeep.simplenotes.util.NoteNetworkUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class EditTask extends AsyncTask<Object,Void,String>{

	@Override
	protected String doInBackground(Object... params) {
		NoteBean note=(NoteBean)params[0];
		Context context=(Context)params[1];
		String response=NoteNetworkUtil.doPut(SimpleNotesConstants.NOTE_EDIT_URL, note,context);	
		Log.d("EditTask",response);
		return response;
	}

}
