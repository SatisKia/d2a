/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.*;

public class _Preference {
	private SharedPreferences _pref;
	private SharedPreferences.Editor _editor;
	private int _index;

	public _Preference( _Main m, String name ){
		_pref = m.getSharedPreferences( name, Context.MODE_PRIVATE );
	}

	public String getParameter( String key, String defValue ){
		return _pref.getString( key, defValue );
	}

	public void setParameter( String key, String value ){
		_editor = _pref.edit();
		_editor.putString( key, value );
		_editor.commit();
	}

	public void beginRead(){
		_index = -1;
	}

	public String read( String defValue ){
		_index++;
		return _pref.getString( "" + _index, defValue );
	}

	public void endRead(){
	}

	public void beginWrite(){
		_editor = _pref.edit();
		_index = -1;
	}

	public void write( String value ){
		_index++;
		_editor.putString( "" + _index, value );
	}

	public void endWrite(){
		_editor.commit();
	}
}
