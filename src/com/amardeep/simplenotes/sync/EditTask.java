package com.amardeep.simplenotes.sync;

import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.constants.SimpleNotesConstants;
import com.amardeep.simplenotes.util.NoteNetworkUtil;

import android.content.Context;
import android.os.AsyncTask;

public class EditTask extends AsyncTask<Object,Void,String>{

	@Override
	protected String doInBackground(Object... params) {
		NoteBean note=(NoteBean)params[0];
		Context context=(Context)params[1];
		String response=NoteNetworkUtil.doPost(SimpleNotesConstants.NOTE_EDIT_URL, note,context);	
		return response;
	}

}
