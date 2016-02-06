package com.amardeep.simplenotes.activity;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amardeep.simplenotes.R;
import com.amardeep.simplenotes.adapter.NoteAdapter;
import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.util.GraphicsUtil;
import com.amardeep.simplenotes.util.SqlUtil;


public class SearchableActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		GraphicsUtil.changeStatusBarColor(this);
		//setting action bar color
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9933")));
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayHomeAsUpEnabled(false);
		List<NoteBean> noteList=handleIntent(getIntent());
		ArrayAdapter adapter=new NoteAdapter(this,R.layout.note, noteList);
		ListView list=(ListView) findViewById(R.id.searchView);
		list.setAdapter(adapter);
		list.setDividerHeight(0);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(SearchableActivity.this,ViewNoteActivity.class);
				TextView text=(TextView)view;
				String noteId=(String)text.getTag();
				intent.putExtra("noteId",noteId);
				startActivity(intent);
				
			}
			
		});
		
	}
	@Override
	protected void onNewIntent(Intent intent)
	{
		handleIntent(intent);
	}
	private List<NoteBean> handleIntent(Intent intent)
	{
		if(Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
			String query=intent.getStringExtra(SearchManager.QUERY);
			SqlUtil sqlUtil=new SqlUtil(this);
			List<NoteBean> noteList=sqlUtil.searchNotes(query);
			return noteList;
			
		}
		return null;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
