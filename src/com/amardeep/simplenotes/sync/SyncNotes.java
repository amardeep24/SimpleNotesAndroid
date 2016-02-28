package com.amardeep.simplenotes.sync;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.constants.SimpleNotesConstants;
import com.amardeep.simplenotes.util.NoteNetworkUtil;
import com.amardeep.simplenotes.util.SqlUtil;

public class SyncNotes {

	public String syncNotesWeb(Context context) {
		String response="";
		List<NoteBean> webNoteList=new ArrayList<NoteBean>();
		List<NoteBean> localNoteList;
		SqlUtil sqlUtil=new SqlUtil(context);
		// check for new notes in server
		response=NoteNetworkUtil.doGet(SimpleNotesConstants.NOTE_GET_URL,context);
		try {
			JSONArray jsonArray=new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
		        JSONObject noteObject = jsonArray.getJSONObject(i);
		        NoteBean note=new NoteBean(noteObject.getString("noteId"), noteObject.getString("noteTitle"),
		        		noteObject.getString("noteContent"), noteObject.getString("noteDate"), true);
		        webNoteList.add(note);
		        //sqlUtil.addNote(note);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		localNoteList=sqlUtil.getAllNotes();
		
		for(int i=0;i<webNoteList.size();i++)
		{
			boolean add=false;
			for(int j=0;j<localNoteList.size();j++)
			{
				if((webNoteList.get(i).getNoteId()).equals(localNoteList.get(j).getNoteId()))
				{
					add=true;
				}
			}
			if(!add)
			{
				sqlUtil.addNote(webNoteList.get(i));
				
			}
		}
		// check for edited notes in server
		for(int i=0;i<webNoteList.size();i++)
		{
			boolean edit=false;
			for(int j=0;j<localNoteList.size();j++)
			{
				if((webNoteList.get(i).getNoteTitle()).equals(localNoteList.get(j).getNoteTitle())&&
						(webNoteList.get(i).getNoteContent()).equals(localNoteList.get(j).getNoteContent()) )
				{
					edit=true;
				}
			}
			if(!edit)
			{
				sqlUtil.updateNote(webNoteList.get(i),webNoteList.get(i).getNoteId());
				
			}
		}
		
		
		// check for deleted notes in server
		
		for(int i=0;i<localNoteList.size();i++)
		{
			boolean delete=true;
			for(int j=0;j<webNoteList.size();j++)
			{
				if((localNoteList.get(i).getNoteId()).equals(webNoteList.get(j).getNoteId()))
				{
					
					delete=false;
					
				}
			}
			if(delete)
			{	
				sqlUtil.deleteNote(localNoteList.get(i).getNoteId());
				
			}
		}
		

		return "success";
	}

	public String syncNotesPhone() {
		// check for new notes in phone

		// check for edited notes in phone

		// check for deleted notes in phone
		
		return null;
	}
}
