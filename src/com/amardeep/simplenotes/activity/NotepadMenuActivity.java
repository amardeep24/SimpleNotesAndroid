package com.amardeep.simplenotes.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.amardeep.simplenotes.R;
import com.amardeep.simplenotes.adapter.NoteAdapter;
import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.util.GraphicsUtil;
import com.amardeep.simplenotes.util.SqlUtil;
import com.amardeep.simplenotes.util.TimeDateUtil;


public class NotepadMenuActivity extends Activity {
	/*FileUtil fileUtil;
	List<String> fileList;*/
	private static final String DELETE="Delete";
	private static final String SHARE="Share";
	private static final String SET_REMINDER="Set Reminder";
	SqlUtil sqlUtil;
	List<String> noteViewList;
	List<NoteBean> noteList;
	ArrayAdapter adapter;
	String noteId;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad_menu);
        GraphicsUtil.changeStatusBarColor(this);
        Button addNote=(Button)findViewById(R.id.button1);
        Log.i("File","Before entry");
        addNote.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent newNoteIntent=new Intent(NotepadMenuActivity.this,NewNoteActivity.class);
				startActivity(newNoteIntent);
			}
        	
        }) ;
        Log.i("File","Before entry");
        //setting action bar color
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9933")));
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayHomeAsUpEnabled(false);
        //Fetching previous notes
        refreshActivity();
       
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
		SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    @Override
    public void onResume(){
    	//Toast.makeText(this, "on resume", Toast.LENGTH_LONG).show();
    	super.onResume();
    	refreshActivity();
    }
    @Override
    public void onStop(){
    	super.onStop();
    	/* LinearLayout parent=(LinearLayout)findViewById(R.id.parent);
    	 parent.removeAllViewsInLayout();*/
    	ListView listView = (ListView) findViewById(R.id.parent);
    	listView.invalidate();
    	
    }
    @Override
    public void onPause(){
    	super.onPause();
    	/* LinearLayout parent=(LinearLayout)findViewById(R.id.parent);
    	 parent.removeAllViewsInLayout();*/
    	ListView listView = (ListView) findViewById(R.id.parent);
    	listView.invalidate();
    	refreshActivity();
    	
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
       /* if(id == R.id.action_search)
        {
        	Log.i("search","pressed");
        	onSearchRequested();
        	return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
    public void refreshActivity()
    {
    	sqlUtil=new SqlUtil(NotepadMenuActivity.this);
    	noteList=sqlUtil.getAllNotes();
    	if(noteList.size()>0)
    	{
	    	/*noteViewList=new ArrayList<String>();
	    	for(NoteBean note:noteList)
	    	{
	    		noteViewList.add(note.getNoteTitle()+"\n"+note.getNoteDate());
	    		
	    	}*/
	    	adapter = new NoteAdapter(this,R.layout.note, noteList);
         	final ListView listView = (ListView) findViewById(R.id.parent);
         	listView.invalidate();
         	listView.setAdapter(adapter);
         	listView.setDividerHeight(0);
         	listView.setOnItemClickListener(new OnItemClickListener() {
 				
 				@Override
 				public void onItemClick(AdapterView<?> parent, View view,
 						int position, long id) {
 					//TextView textTitle=(TextView)view;
 					//String titleName=textTitle.getText().toString();
 					Intent viewFileIntent=new Intent(NotepadMenuActivity.this,ViewNoteActivity.class);
 					//int hashGen=(titleName.substring(0,titleName.indexOf("\n"))+titleName.substring(titleName.indexOf("\n")+1,titleName.length())).hashCode();
 					LinearLayout noteView=(LinearLayout)view;
 					noteId=(String)noteView.getTag();
 					//noteId=String.valueOf(hashGen);
 					Log.i("Refresh",noteId);
 					viewFileIntent.putExtra("noteId",noteId);
 					startActivity(viewFileIntent);
 					
 				}
 			});
         	
         	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

 				@Override
 				public boolean onItemLongClick(AdapterView<?> parent,
 						final View view, final int position, long id) {
 					LinearLayout item=(LinearLayout)view;
 					TextView textTitle=(TextView)item.findViewById(R.id.noteTitle);
 					String noteTitle=textTitle.getText().toString();
 					showListOptionsDialog(view,position, noteTitle);
 					/*final TextView textTitle=(TextView)view;
 					final String titleName=textTitle.getText().toString();*/
 					/*AlertDialog.Builder alert=new AlertDialog.Builder(NotepadMenuActivity.this);
 					alert.setTitle("Delete note?");
 					alert.setPositiveButton("OK", new AlertDialog.OnClickListener() {
 						
 						
 						@Override
 						public void onClick(DialogInterface dialog, int which) {
 							//String noteId=String.valueOf((titleName.substring(0,titleName.indexOf("\n"))+titleName.substring(titleName.indexOf("\n")+1,titleName.length())).hashCode());
 							LinearLayout noteView=(LinearLayout)view;
 		 					noteId=(String)noteView.getTag();
 							sqlUtil.deleteNote(noteId);
 							noteList.remove(position);
 							adapter.notifyDataSetChanged();
 						}
 					});
 					alert.setNegativeButton("Cancel", null);
 					alert.show();*/
 					return true;
 				}
 			});
         	
         	
    	}
    }
   /* public void refreshActivity()
    {
    	Log.i("Refresh","inside refresh activity");
    	 fileUtil=new FileUtil();
         if(fileUtil.checkFile()){
         	Log.i("File","check file entry");
         String[] listFiles=fileUtil.fetchFiles();
         if(listFiles.length>0){
         	Log.i("File","length gretaer than zero");
         	for(int i=0;i<listFiles.length;i++){
         		listFiles[i]=listFiles[i]+"\n"+"22/08/2015";
         	}
         	fileList=new LinkedList<String>(Arrays.asList(listFiles));
         	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.note, fileList);
         	final ListView listView = (ListView) findViewById(R.id.parent);
         	listView.invalidate();
         	
         	listView.setAdapter(adapter);
         	listView.setOnItemClickListener(new OnItemClickListener() {
 				
 				@Override
 				public void onItemClick(AdapterView<?> parent, View view,
 						int position, long id) {
 					TextView textTitle=(TextView)view;
 					String fileName=textTitle.getText().toString();
 					Intent viewFileIntent=new Intent(NotepadMenuActivity.this,ViewNoteActivity.class);
 					viewFileIntent.putExtra("fileName",fileName);
 					startActivity(viewFileIntent);
 					
 				}
 			});
         	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

 				@Override
 				public boolean onItemLongClick(AdapterView<?> parent,
 						View view, final int position, long id) {
 					final TextView textTitle=(TextView)view;
 					final String fileName=textTitle.getText().toString();
 					AlertDialog.Builder alert=new AlertDialog.Builder(NotepadMenuActivity.this);
 					alert.setTitle("Delete note?");
 					alert.setPositiveButton("OK", new AlertDialog.OnClickListener() {
 						
 						
 						@Override
 						public void onClick(DialogInterface dialog, int which) {
 							fileUtil.deleteFile(fileName);
 							fileList.remove(position);
 							adapter.notifyDataSetChanged();
 						}
 					});
 					alert.setNegativeButton("Cancel", null);
 					alert.show();
 					return true;
 				}
 			});
         	
         }
         }
    }*/
    public void showListOptionsDialog(final View viewMain,final int positionMain,final String noteTitle)
    {
    	AlertDialog.Builder listDialog= new AlertDialog.Builder(NotepadMenuActivity.this);
    	LayoutInflater listLayout=getLayoutInflater();
    	View listView=(View)listLayout.inflate(R.layout.notepad_options_list,null);
    	listDialog.setView(listView);
    	List<String> listOfOptions=new ArrayList<String>();
    	listOfOptions.add(DELETE);
    	listOfOptions.add(SHARE);
    	listOfOptions.add(SET_REMINDER);
    	ArrayAdapter<String> array=new ArrayAdapter<String>(NotepadMenuActivity.this,R.layout.note_option,listOfOptions);
    	ListView listOptions=(ListView)listView.findViewById(R.id.optionsList);
    	listOptions.setAdapter(array);
    	listOptions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					 int position, long id) {
				TextView optionView=(TextView)view;
				String option=optionView.getText().toString();
				if(option.equals(DELETE))
				{
					AlertDialog.Builder alert=new AlertDialog.Builder(NotepadMenuActivity.this);
 					alert.setTitle("Delete note?");
 					alert.setPositiveButton("OK", new AlertDialog.OnClickListener() {
 						
 						
 						@Override
 						public void onClick(DialogInterface dialog, int which) {
 							//String noteId=String.valueOf((titleName.substring(0,titleName.indexOf("\n"))+titleName.substring(titleName.indexOf("\n")+1,titleName.length())).hashCode());
 							LinearLayout noteView=(LinearLayout)viewMain;
 		 					noteId=(String)noteView.getTag();
 							sqlUtil.deleteNote(noteId);
 							noteList.remove(positionMain);
 							adapter.notifyDataSetChanged();
 						}
 					});
 					alert.setNegativeButton("Cancel", null);
 					alert.show();
 					return;
 					
				}
				else if(option.equals(SHARE))
				{
					
				}
				else if(option.equals(SET_REMINDER))
				{
					TimeDateUtil timeDate =new TimeDateUtil();
	        		
				}
				
			}
    		
    	
    	});
    	listDialog.show();
    	
    }
}
