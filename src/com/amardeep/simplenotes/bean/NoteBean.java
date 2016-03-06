package com.amardeep.simplenotes.bean;

public class NoteBean {
	private String noteTitle;
	private String noteContent;
	private String noteDate;
	private String noteId;
	private String noteImage;
	private Boolean noteSyncFlag;
	
	public NoteBean(String noteId,String noteTitle,String noteContent,String noteDate)
	 {
		 this.noteTitle=noteTitle;
		 this.noteContent=noteContent;
		 this.noteDate=noteDate;
		 this.noteId=noteId;
	 }
	public NoteBean(String noteId,String noteTitle,String noteContent,String noteDate,Boolean noteSyncFlag)
	 {
		 this.noteTitle=noteTitle;
		 this.noteContent=noteContent;
		 this.noteDate=noteDate;
		 this.noteId=noteId;
		 this.noteSyncFlag=noteSyncFlag;
	 }
	 public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getNoteDate() {
		return noteDate;
	}
	public void setNoteDate(String noteDate) {
		this.noteDate = noteDate;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public String getNoteImage() {
		return noteImage;
	}
	public void setNoteImage(String noteImage) {
		this.noteImage = noteImage;
	}
	public Boolean getNoteSyncFlag() {
		return noteSyncFlag;
	}
	public void setNoteSyncFlag(Boolean noteSyncFlag) {
		this.noteSyncFlag = noteSyncFlag;
	}
	

}
