package com.amardeep.simplenotes.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amardeep.simplenotes.R;
import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.util.GraphicsUtil;
import com.amardeep.simplenotes.util.RandomUtil;
import com.amardeep.simplenotes.util.SqlUtil;
import com.amardeep.simplenotes.util.TimeDateUtil;

public class NewNoteActivity extends Activity {

	TimeDateUtil timeDate;
	EditText contentText;
	EditText titleText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		GraphicsUtil.changeStatusBarColor(this);
		/*LayoutInflater inflator=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v=inflator.inflate(R.layout.new_note_activity_bar,null);
		*/
		//setting action bar characteristics
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9933")));
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
       /* bar.setDisplayShowCustomEnabled(true);
        bar.setCustomView(v);*/
        
		titleText=(EditText)findViewById(R.id.titleText);
		contentText=(EditText)findViewById(R.id.contentText);
		
		//Button save=(Button)findViewById(R.id.save);
		//final FileUtil fileUtil=new FileUtil();
		
		/*final ToggleButton toggle = (ToggleButton) findViewById(R.id.reminder);
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        		timeDate =new TimeDateUtil();
		        		final String title=titleText.getText().toString();
		        		timeDate.showTimeDateDialog(NewNoteActivity.this);
		        } else {
		           return;
		        }
		    }
		});*/
		/*save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String content=contentText.getText().toString();
				final String title=titleText.getText().toString();
				final String noteDate=TimeDateUtil.returnCurrentTime();
				final String noteId=RandomUtil.generateNoteId();
				Log.d("note id hashcode generated during saving operation",noteId+noteDate);
				NoteBean note=new NoteBean(noteId,title,content,noteDate);
				SqlUtil sqlUtil=new SqlUtil(NewNoteActivity.this);
				long result=sqlUtil.addNote(note);
				if(timeDate!=null)
				{
					timeDate.setAlarm(noteId,title,timeDate.getCalendar(), NewNoteActivity.this);
				}
				titleText.setText("");
				contentText.setText("");
				toggle.setChecked(false);
				timeDate=null;
				if(result>0)
				Toast.makeText(getApplicationContext(), "Note saved!", Toast.LENGTH_LONG).show();
					if(fileUtil.writeFile(title,content))
						{
							if(timeDate!=null)
							{
								timeDate.setAlarm(title, timeDate.getCalendar(), NewNoteActivity.this);
							}
							titleText.setText("");
							contentText.setText("");
							toggle.setChecked(false);
							timeDate=null;
							Toast.makeText(getApplicationContext(), "Note saved!", Toast.LENGTH_LONG).show();
						};
			}
		});*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId())
		{
		case R.id.action_save:{
			final String content=contentText.getText().toString();
			final String title=titleText.getText().toString();
			final String noteDate=TimeDateUtil.returnCurrentTime();
			final String noteId=RandomUtil.generateNoteId();
			if(title.equals(""))
			{
				Toast.makeText(getApplicationContext(), "Title cannot be blank!", Toast.LENGTH_LONG).show();
				break;
			}
			Log.d("note id hashcode generated during saving operation",noteId+noteDate);
			NoteBean note=new NoteBean(noteId,title,content,noteDate);
			SqlUtil sqlUtil=new SqlUtil(NewNoteActivity.this);
			long result=sqlUtil.addNote(note);
			if(timeDate!=null)
			{
				timeDate.setAlarm(noteId,title,timeDate.getCalendar(), NewNoteActivity.this);
			}
			titleText.setText("");
			contentText.setText("");
			//if alarm is set change icon here
			
			timeDate=null;
			if(result>0)
			Toast.makeText(getApplicationContext(), "Note saved!", Toast.LENGTH_LONG).show();
			return true;
			}
		case R.id.action_reminder:
			{
				timeDate =new TimeDateUtil();
        		final String title=titleText.getText().toString();
        		timeDate.showTimeDateDialog(NewNoteActivity.this);
        		return true;
			}
		case R.id.action_redo:
		{
			return true;
		}
		case R.id.action_undo:
		{
			return true;
		}
			
			
		}
		return super.onOptionsItemSelected(item);
	}
	
}
