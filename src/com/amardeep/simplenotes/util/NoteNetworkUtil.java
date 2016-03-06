package com.amardeep.simplenotes.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import android.widget.Toast;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.amardeep.simplenotes.bean.NoteBean;
import com.amardeep.simplenotes.constants.SimpleNotesConstants;

public class NoteNetworkUtil {
	public static boolean checkNetwork(Context context) {
		
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}

	}

	public static String doPost(String requestURL, NoteBean note,Context context) {
		URL url;
		String response = "";
		try {
			url = new URL(requestURL);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(SimpleNotesConstants.READ_TIME_OUT);
			connection.setConnectTimeout(SimpleNotesConstants.CONNECTION_TIME_OUT);
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			Log.d("Connection opened", "connected");

			JSONObject noteJsonObject = new JSONObject();

			noteJsonObject.put("noteId", note.getNoteId());
			noteJsonObject.put("noteTitle", note.getNoteTitle());
			noteJsonObject.put("noteContent", note.getNoteContent());
			noteJsonObject.put("flag", true);
			noteJsonObject.put("noteDate", note.getNoteDate());
			noteJsonObject.put("noteImage", note.getNoteImage());
			
			
			String noteJson = noteJsonObject.toString();
			Log.d("JSON sent",noteJson);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(noteJson);
			writer.flush();
			writer.close();

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {

				//StringBuffer responseString=new StringBuffer();
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;

				}
				Log.d("OK response", response);
			} else {
				response = "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	public static String doGet(String requestURL,Context context) {
		URL url;
		String response = "";
		try {
			url = new URL(requestURL);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(SimpleNotesConstants.READ_TIME_OUT);
			connection.setConnectTimeout(SimpleNotesConstants.CONNECTION_TIME_OUT);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setRequestProperty("Accept", "application/json");
			Log.d("Connection opened", "connected");
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {

				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;

				}
				Log.d("OK response", response);
			} else {
				response = "error";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
