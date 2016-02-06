package com.amardeep.simplenotes.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.util.Log;

public class FileUtil {
	public boolean writeFile(String title,String content){
		boolean status=false;
		try{
			if(title==null||title.equals(""))
			{
			title="myNote.dat";
			}
			File noteFolder=new File(Environment.getExternalStorageDirectory().getPath(),"My Notes");
			noteFolder.mkdir();
			File noteFile=new File(Environment.getExternalStorageDirectory().getPath()+"/My Notes",title);
			FileOutputStream fout=new FileOutputStream(noteFile);
			OutputStreamWriter out=new OutputStreamWriter(fout);
			/*Calendar calendar=Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy h:mm a");
			String timeStamp=dateFormat.format(calendar.getTime());*/
			try {
				out.write(content);
				out.flush();
				status=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return status;
	}
	public String readFile(String title)
	{
		String content="";
		try{
		FileReader fr=new FileReader(Environment.getExternalStorageDirectory().getPath()+"/My Notes/"+title);
		BufferedReader br=new BufferedReader(fr);
		String line="";
		try{
		while((line=br.readLine())!=null){
			content=content+line+"\n";
		}
		Log.i("Read",content);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return content;
	}
	public String[] fetchFiles(){
		File file=new File(Environment.getExternalStorageDirectory().getPath()+"/My Notes/");
		return file.list();
	}
	public boolean checkFile(){
		File f = new File(Environment.getExternalStorageDirectory().getPath()+"/My Notes/");
		return f.exists();
		
	}
	public boolean deleteFile(String fileName)
	{
		boolean status=false;
		File noteFile=new File(Environment.getExternalStorageDirectory().getPath()+"/My Notes",fileName);
		if(noteFile.exists())
		{
			if(noteFile.delete())
			{
				status=true;
			}
		}
		return status;
	}
	public String getFileCreationTime(String title){
		String timeStamp = "";
		try{
			File file=new File(Environment.getExternalStorageDirectory().getPath()+"/My Notes/"+title);
			if(file.exists())
			{
				Date creationDate=new Date(file.lastModified());
				SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy h:mm a");
				timeStamp=dateFormat.format(creationDate);
			}
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return timeStamp;
	}
}
