package com.amardeep.simplenotes.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.constants.SimpleNotesConstants;
import com.amardeep.simplenotes.util.NoteNetworkUtil;

public class DeleteTask extends AsyncTask<Object,Void,String>{

	@Override
	protected String doInBackground(Object... params) {
		NoteBean note=(NoteBean)params[0];
		Context context=(Context)params[1];
		String response=NoteNetworkUtil.doDelete(SimpleNotesConstants.NOTE_DELETE_URL, note,context);	
		return response;
	}

}
