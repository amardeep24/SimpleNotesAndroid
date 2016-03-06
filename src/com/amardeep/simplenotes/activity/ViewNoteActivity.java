package com.amardeep.simplenotes.activity;

import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amardeep.simplenotes.R;
import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.constants.SimpleNotesConstants;
import com.amardeep.simplenotes.sync.DeleteTask;
import com.amardeep.simplenotes.sync.EditTask;
import com.amardeep.simplenotes.util.GraphicsUtil;
import com.amardeep.simplenotes.util.ImageUtil;
import com.amardeep.simplenotes.util.NoteNetworkUtil;
import com.amardeep.simplenotes.util.SqlUtil;
import com.amardeep.simplenotes.util.TimeDateUtil;

public class ViewNoteActivity extends Activity {
public static final CharSequence EDIT_BUTTON="Edit";
public static final CharSequence SAVE_BUTTON="Save";
static boolean editFlag=false;
String contentText;
String titleText;
String editedContentText;
String editedTitleText;
String noteId;
String imageBase64String;
SqlUtil sqlUtil;
TimeDateUtil timeDate;
NoteBean note;
MenuItem edit;
MenuItem save;
MenuItem delete;
MenuItem attach;
TextView title;
TextView date;
TextView content;
ImageView imageView;
ImageView imageEditView;
EditText editNote;
EditText editTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_note);
		//stting status bar color
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			GraphicsUtil.changeStatusBarColor(this);
		//setting action bar color
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9933")));
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
		editNote=(EditText)findViewById(R.id.editContent);   
		editTitle=(EditText)findViewById(R.id.editTitle);
		title=(TextView)findViewById(R.id.title);
		date=(TextView)findViewById(R.id.date);
		content=(TextView)findViewById(R.id.content);
		imageView=(ImageView)findViewById(R.id.noteViewImage);
		imageEditView=(ImageView)findViewById(R.id.noteEditImage);
		Log.i("boolean flag",String.valueOf(editFlag));
		if(editFlag)
		{
			
				content.setVisibility(View.GONE);
				title.setVisibility(View.GONE);
				imageView.setVisibility(View.GONE);
		}
		else{
			
				editNote.setVisibility(View.GONE);
				editTitle.setVisibility(View.GONE);
				imageEditView.setVisibility(View.GONE);
				
		}
		noteId=getIntent().getStringExtra("noteId");
		sqlUtil=new SqlUtil(this);
		note=sqlUtil.getNote(noteId);
		if(note!=null)
		{
			title.setText(note.getNoteTitle());
			/*SpannableString underlinedDate = new SpannableString(note.getNoteDate());
			underlinedDate.setSpan(new UnderlineSpan(), 0, underlinedDate.length(), 0);*/
			date.setText(note.getNoteDate());
			content.setText(note.getNoteContent());
			//decoding image from base64 string
			if(note.getNoteImage()!=null)
			{
			String encodedString=note.getNoteImage();
			String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
			byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);	
			Bitmap bitmap=BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
			if(bitmap!=null){
				int scaleFactor = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
	            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 512, scaleFactor, true);
				imageView.setImageBitmap(scaledBitmap);
				imageEditView.setImageBitmap(scaledBitmap);
				}
			}
		}
		//dev mode
				StrictMode.ThreadPolicy policy = new StrictMode.
						ThreadPolicy.Builder().permitAll().build();
						StrictMode.setThreadPolicy(policy); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuBar=getMenuInflater();
		menuBar.inflate(R.menu.view_note, menu);
		edit=(MenuItem)menu.findItem(R.id.action_edit);
		save=(MenuItem)menu.findItem(R.id.action_note_save);
		delete=(MenuItem)menu.findItem(R.id.action_delete);
		attach=(MenuItem)menu.findItem(R.id.action_attach);
		if(editFlag)
		{
			save.setVisible(true);
			attach.setVisible(true);
			edit.setVisible(false);
			delete.setVisible(false);
		}
		else{
			save.setVisible(false);
			attach.setVisible(false);
			edit.setVisible(true);
			delete.setVisible(true);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId())
		{
		case R.id.action_edit:
		{
			editFlag=true;
			Log.i("boolean flag on click",String.valueOf(editFlag));
			edit.setVisible(false);
			delete.setVisible(false);
			save.setVisible(true);
			attach.setVisible(true);
			contentText=content.getText().toString();
			titleText=title.getText().toString();
			// contentText=contentText.substring(0,contentText.lastIndexOf("\n"));
			content.setVisibility(View.GONE);
			title.setVisibility(View.GONE);
			imageView.setVisibility(View.GONE);
		    editNote.setVisibility(View.VISIBLE);
		    editTitle.setVisibility(View.VISIBLE);
		    imageEditView.setVisibility(View.VISIBLE);
			editNote.setText(contentText);
			editTitle.setText(titleText);
			Log.i("ViewSwitcher","button text changed to save");
			return true;
		}
		case R.id.action_note_save:
		{
			String response=null;
			editedContentText=editNote.getText().toString();
			editedTitleText=editTitle.getText().toString();
			if(editedTitleText.equals("")||editedTitleText==null)
			{
				Toast.makeText(ViewNoteActivity.this,"Title cannot be empty!",Toast.LENGTH_SHORT).show();
				return true;
			}
			editFlag=false;
			delete.setVisible(true);
			edit.setVisible(true);
			save.setVisible(false);
			attach.setVisible(false);
		    Log.i("ViewSwitcher",editedContentText);
			content.setText(editedContentText);
			title.setText(editedTitleText);
			editNote.setVisibility(View.GONE);
			editTitle.setVisibility(View.GONE);
			imageEditView.setVisibility(View.GONE);
			content.setVisibility(View.VISIBLE);
			title.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.VISIBLE);
			String noteDate=TimeDateUtil.returnCurrentTime();
			date.setText(noteDate);
			//String newNoteId=String.valueOf((titleName+noteDate).hashCode());
			String titleName=title.getText().toString();
			Boolean noteSyncFlag=false;
			NoteBean noteBean=new NoteBean(noteId,titleName,editedContentText,noteDate,noteSyncFlag);
			if(imageBase64String!=null)
			{
				Log.d("ViewNoteActivity edit",imageBase64String);
				noteBean.setNoteImage(imageBase64String);
			}
			if(NoteNetworkUtil.checkNetwork(this))
			{
				Toast.makeText(getApplicationContext(), "Updating note...", Toast.LENGTH_LONG).show();
				//response=NoteNetworkUtil.doPost(SimpleNotesConstants.NOTE_EDIT_URL, noteBean,this);
				new EditTask().execute(noteBean,ViewNoteActivity.this);
				noteSyncFlag=true;
			}
			noteBean.setNoteSyncFlag(noteSyncFlag);
			sqlUtil.updateNote(noteBean,noteId);
			Toast.makeText(this, "Note Saved!", Toast.LENGTH_LONG).show();
			Log.i("ViewSwitcher","button text changed to edit");
			return true;
		}
		case R.id.action_delete:
		{
			AlertDialog.Builder alert=new AlertDialog.Builder(ViewNoteActivity.this);
				alert.setTitle("Delete note?");
				alert.setPositiveButton("OK", new AlertDialog.OnClickListener() {
					
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String response=null;
						//String noteId=String.valueOf((titleName.substring(0,titleName.indexOf("\n"))+titleName.substring(titleName.indexOf("\n")+1,titleName.length())).hashCode());
						if(NoteNetworkUtil.checkNetwork(ViewNoteActivity.this))
						{
							Toast.makeText(getApplicationContext(), "Deleting note...", Toast.LENGTH_LONG).show();
							//response=NoteNetworkUtil.doPost(SimpleNotesConstants.NOTE_DELETE_URL, note,ViewNoteActivity.this);
							new DeleteTask().execute(note,ViewNoteActivity.this);
						}
						//Toast.makeText(ViewNoteActivity.this, response, Toast.LENGTH_LONG).show();
						sqlUtil.deleteNote(noteId);
						Toast.makeText(ViewNoteActivity.this,"Note Deleted!",Toast.LENGTH_SHORT).show();
						ViewNoteActivity.this.finish();
						
					}
				});
				alert.setNegativeButton("Cancel", null);
				alert.show();
				return true;
		}
		case R.id.action_attach:
		{
			imageView.setImageDrawable(null);
			imageEditView.setImageDrawable(null);
			showFileChooser();
			return true;
		}
		
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onResume()
	{
		super.onResume();
		
		if(!editFlag)
		{
			editNote.setVisibility(View.GONE);
			editTitle.setVisibility(View.GONE);
			imageEditView.setVisibility(View.GONE);
		}
		else{
			content.setVisibility(View.GONE);
			title.setVisibility(View.GONE);
			imageView.setVisibility(View.GONE);
		}
	}
	@Override
	public void onRestart()
	{
		super.onResume();
		if(!editFlag)
		{
			editNote.setVisibility(View.GONE);
			editTitle.setVisibility(View.GONE);
			imageEditView.setVisibility(View.GONE);
		}
		else{
			content.setVisibility(View.GONE);
			title.setVisibility(View.GONE);
			imageView.setVisibility(View.GONE);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SimpleNotesConstants.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
			 
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                int scaleFactor = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 512, scaleFactor, true);
                imageEditView.setImageBitmap(scaledBitmap);
                imageView.setImageBitmap(scaledBitmap);
                imageBase64String=ImageUtil.encodeString(bitmap);
                Log.d("NewNoteActivity",imageBase64String);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"),SimpleNotesConstants.PICK_IMAGE_REQUEST);
    }
	
	
}
