package com.example.expandinglistrows;

import android.graphics.Bitmap;


public class ListItem {
	
	private String string;
	private Bitmap bitmap;
	
	public ListItem(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
	
	public void setString(String string) {
		this.string = string;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}
