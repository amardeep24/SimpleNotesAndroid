package com.amardeep.simplenotes.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;


public class GraphicsUtil {

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static void changeStatusBarColor(Activity activity)
	{
		Window window = activity.getWindow();

		// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		
		window.setStatusBarColor(Color.parseColor("#E65C00"));

	}
	
}
