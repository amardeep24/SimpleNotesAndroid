package com.amardeep.simplenotes.bean;

public class NoteBean {
	private String noteTitle;
	private String noteContent;
	private String noteDate;
	private String noteId;
	 public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public NoteBean(String noteId,String noteTitle,String noteContent,String noteDate)
	 {
		 this.noteTitle=noteTitle;
		 this.noteContent=noteContent;
		 this.noteDate=noteDate;
		 this.noteId=noteId;
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
	

}
