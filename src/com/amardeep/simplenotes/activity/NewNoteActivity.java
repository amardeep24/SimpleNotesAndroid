package com.amardeep.simplenotes.activity;

import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amardeep.simplenotes.R;
import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.constants.SimpleNotesConstants;
import com.amardeep.simplenotes.sync.SaveTask;
import com.amardeep.simplenotes.util.GraphicsUtil;
import com.amardeep.simplenotes.util.ImageUtil;
import com.amardeep.simplenotes.util.NoteNetworkUtil;
import com.amardeep.simplenotes.util.RandomUtil;
import com.amardeep.simplenotes.util.SqlUtil;
import com.amardeep.simplenotes.util.TimeDateUtil;

public class NewNoteActivity extends Activity {

	private TimeDateUtil timeDate;
	private EditText contentText;
	private EditText titleText;
	private ImageView imageView;
	private String imageBase64String;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			GraphicsUtil.changeStatusBarColor(this);
		/*
		 * LayoutInflater
		 * inflator=(LayoutInflater)this.getSystemService(Context.
		 * LAYOUT_INFLATER_SERVICE); View
		 * v=inflator.inflate(R.layout.new_note_activity_bar,null);
		 */
		// setting action bar characteristics
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9933")));
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setDisplayShowTitleEnabled(false);
		/*
		 * bar.setDisplayShowCustomEnabled(true); bar.setCustomView(v);
		 */

		titleText = (EditText) findViewById(R.id.titleText);
		contentText = (EditText) findViewById(R.id.contentText);
		imageView=(ImageView)findViewById(R.id.noteNewImage);
		if (!NoteNetworkUtil.checkNetwork(this))
			Toast.makeText(getApplicationContext(), "Not connected !",
					Toast.LENGTH_LONG).show();
		// dev mode
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_save: {
			String response = null;
			final String content = contentText.getText().toString();
			final String title = titleText.getText().toString();
			final String noteDate = TimeDateUtil.returnCurrentTime();
			final String noteId = RandomUtil.generateNoteId();
			if (title.equals("")) {
				Toast.makeText(getApplicationContext(),
						"Title cannot be blank!", Toast.LENGTH_LONG).show();
				break;
			}
			Log.d("note id hashcode generated during saving operation", noteId
					+ noteDate);
			boolean noteSyncFlag = false;
			SqlUtil sqlUtil = new SqlUtil(NewNoteActivity.this);
			NoteBean note = new NoteBean(noteId, title, content, noteDate,
					noteSyncFlag);
			//image added
			if(imageBase64String!=null)
			{
				note.setNoteImage(imageBase64String);
			}
			if (NoteNetworkUtil.checkNetwork(this)) {
				Toast.makeText(getApplicationContext(),
						"Saving note...", Toast.LENGTH_LONG).show();
				/*response = NoteNetworkUtil.doPost(
						SimpleNotesConstants.NOTE_SAVE_URL, note, this);*/
				new SaveTask().execute(note,NewNoteActivity.this);
				noteSyncFlag = true;
			}
			note.setNoteSyncFlag(noteSyncFlag);
			//Toast.makeText(this, response, Toast.LENGTH_LONG).show();
			long result = sqlUtil.addNote(note);
			if (timeDate != null) {

				timeDate.setAlarm(noteId, title, timeDate.getCalendar(),
						NewNoteActivity.this);

			}
			titleText.setText("");
			contentText.setText("");
			imageView.setImageResource(0);
			// if alarm is set change icon here

			timeDate = null;
			if (result > 0)
				Toast.makeText(getApplicationContext(), "Note saved!",
						Toast.LENGTH_LONG).show();
			return true;
		}
		case R.id.action_reminder: {
			timeDate = new TimeDateUtil();
			final String title = titleText.getText().toString();
			timeDate.showTimeDateDialog(NewNoteActivity.this);
			return true;
		}
		case R.id.action_attach: {
			showFileChooser();
			return true;
		}
		case R.id.action_redo: {
			return true;
		}
		case R.id.action_undo: {
			return true;
		}

		}
		return super.onOptionsItemSelected(item);
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
