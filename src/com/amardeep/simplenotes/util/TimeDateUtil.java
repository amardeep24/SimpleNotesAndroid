package com.amardeep.simplenotes.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amardeep.simplenotes.receiver.AlarmReceiver;

public class TimeDateUtil {
	
	int currentYear;
	int currentMonth;
	int currentDay;
	int currentHour;
	int currentMinute;
	
	public  int yearSelected,monthSelected,daySelected,hourSelected,minuteSelected;
	
	DatePickerDialog.OnDateSetListener datePicker;
	TimePickerDialog.OnTimeSetListener timePicker;
	Calendar calendar;
	
	public TimeDateUtil(){
	calendar=Calendar.getInstance();
	this.currentYear=calendar.get(Calendar.YEAR);
	this.currentMonth=calendar.get(Calendar.MONTH);
	this.currentDay=calendar.get(Calendar.DAY_OF_MONTH);
	this.currentHour=calendar.get(Calendar.HOUR_OF_DAY);
	this.currentMinute=calendar.get(Calendar.MINUTE);
	calendar.getTimeInMillis();
	
	}
	public Calendar registerTimeDateListener(final Context context)
	{
		Log.i("TimeDateUtil","register");
		datePicker=new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
				yearSelected=year;
				monthSelected=monthOfYear;
				daySelected=dayOfMonth;
				Log.i("TimeDateUtil","onDateSet");
		}
	};
		timePicker =new OnTimeSetListener() {
		
		        @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                    hourSelected = hourOfDay;
                    minuteSelected = min;
                    Log.i("TimeDateUtil","onTimeSet");
                    Log.i("TimeDateUtil",new Integer(daySelected).toString());
                    Log.i("TimeDateUtil",new Integer(monthSelected).toString());
           		 	Log.i("TimeDateUtil",new Integer(yearSelected).toString());
           		 	Log.i("TimeDateUtil",new Integer(hourSelected).toString());
           		 	Log.i("TimeDateUtil",new Integer(minuteSelected).toString());
                    calendar.set(yearSelected, monthSelected, daySelected, hourSelected, minuteSelected,0);
                   
		        }
            };
            return calendar;
	}
	public Calendar showTimeDateDialog(Context context)
	{
		Log.i("TimeDateUtil","showTimeDateDialog");
		Calendar cal=registerTimeDateListener(context);
		DatePickerDialog dateDialog=new DatePickerDialog(context,datePicker, currentYear, currentMonth, currentDay);
		TimePickerDialog timeDialog=new TimePickerDialog(context, timePicker, currentHour, currentMinute, false);
		timeDialog.setTitle("Select time:");
		dateDialog.setTitle("Select date:");
		timeDialog.show();
		dateDialog.show();
		return cal;
	}
	public Calendar getCalendar(){
		return this.calendar;
		
	}
	public void setAlarm(String noteId,String title,Calendar calendar,Context context){
		Intent activityIntent=new Intent(context,AlarmReceiver.class);
		activityIntent.putExtra("noteId", noteId);
		activityIntent.putExtra("reminderTitle",title);
		//Log.i("setAlarm",noteId);
		PendingIntent pendingIntent=PendingIntent.getBroadcast(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarm=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
		String time=TimeDateUtil.returnCurrentTime();
		Toast.makeText(context.getApplicationContext(), "Reminder set for: "+time, Toast.LENGTH_SHORT).show();
	}
	public static String returnCurrentTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy h:mm a");
		Calendar calendar=Calendar.getInstance();
		String date=dateFormat.format(calendar.getTime());
		return date;
	}

}
