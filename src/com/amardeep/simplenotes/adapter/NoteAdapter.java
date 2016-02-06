package com.amardeep.simplenotes.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amardeep.simplenotes.R;
import com.amardeep.simplenotes.bean.NoteBean;

public class NoteAdapter extends ArrayAdapter<NoteBean> {

	Context context;
	int textViewResourceId;
	List<NoteBean> noteBeans;
	//public static int[] colorArray=new int[]{0xFFFFAD33,0xFFFFFFCC,0xFF66FF66,0xFFFFC49B,0xFFB2B2FF,0xFFA9D4BF,0xFFB5B591,0xFFEB99EB,0xFF66E0FF,0xFFB2B2B2};
	public NoteAdapter(Context context,int textViewResourceId,List<NoteBean> objects) {
		super(context,textViewResourceId, objects);
		this.context=context;
		this.textViewResourceId=textViewResourceId;
		this.noteBeans=objects;
	}
	@Override
	public View getView(int position,View view,ViewGroup parent)
	{
		View row=view;
		
		if(row==null)
		{
			LayoutInflater layoutInflator=((Activity) context).getLayoutInflater();
			row=layoutInflator.inflate(textViewResourceId,parent,false);
			
		}
		NoteBean noteBean=noteBeans.get(position);
		if(noteBean!=null)
		{
			TextView note=(TextView)row.findViewById(R.id.noteTitle);
			note.setText(noteBean.getNoteTitle());
			note.setTextColor(Color.parseColor("#FF9933"));
			TextView date=(TextView)row.findViewById(R.id.noteDate);
			date.setText(noteBean.getNoteDate());
			LinearLayout noteView=(LinearLayout)row.findViewById(R.id.noteView);
			noteView.setTag(noteBean.getNoteId());
			//note.setBackgroundColor(colorArray[RandomUtil.generateRandomIndex()]);
			
		}
		
		return row;
		
	}

}
