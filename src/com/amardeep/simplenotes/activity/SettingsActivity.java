package com.amardeep.simplenotes.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.amardeep.simplenotes.R;
import com.amardeep.simplenotes.constants.SimpleNotesConstants;
import com.amardeep.simplenotes.util.GraphicsUtil;

public class SettingsActivity extends Activity {
private CheckBox autoSyncCheckBox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			GraphicsUtil.changeStatusBarColor(this);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9933")));
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		autoSyncCheckBox=(CheckBox)findViewById(R.id.auto_sync_check);
		SharedPreferences settingPreference=getSharedPreferences(SimpleNotesConstants.SETTING_PREFERNCE,0);
		final SharedPreferences.Editor editor = settingPreference.edit();
		autoSyncCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(autoSyncCheckBox.isChecked())
				{
					editor.putBoolean(SimpleNotesConstants.AUTO_SYNC_ENABLED,true);
					editor.commit();
				}
				else
				{
					editor.putBoolean(SimpleNotesConstants.AUTO_SYNC_ENABLED,false);
					editor.commit();
				}
				
			}
		});
		
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
	}*/
}
