/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.Context;
import android.text.InputType;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class _EditText {
	private EditText _edit;
	private int _x;
	private int _y;
	private int _width;
	private int _height;

	public _EditText( Context context, int x, int y, int width, int height, int padding, int fontSize, float scale ){
		_x      = x;
		_y      = y;
		_width  = width;
		_height = height;
		x      = (int)((float)x      * scale);
		y      = (int)((float)y      * scale);
		width  = (int)((float)width  * scale);
		height = (int)((float)height * scale);

		_edit = new EditText( context );
		_edit.setInputType( InputType.TYPE_CLASS_TEXT );

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( width, height );
		params.leftMargin = x;
		params.topMargin  = y;
		_edit.setLayoutParams( params );

		padding = (int)((float)padding * scale);
		_edit.setPadding( padding, padding, padding, padding );

		_edit.setTextSize( TypedValue.COMPLEX_UNIT_PX, (float)fontSize * scale );
	}

	public void setTextColor( int color ){
		_edit.setTextColor( 0xFF000000 | color );
	}
	public void setBackgroundColor( int color ){
		_edit.setBackgroundColor( 0xFF000000 | color );
	}

	public void setText( String text ){
		_edit.setText( text.toCharArray(), 0, text.length() );
	}

	public EditText getEditText(){
		return _edit;
	}
	public int getX(){
		return _x;
	}
	public int getY(){
		return _y;
	}
	public int getWidth(){
		return _width;
	}
	public int getHeight(){
		return _height;
	}
	public String getText(){
		return _edit.getText().toString();
	}
}
