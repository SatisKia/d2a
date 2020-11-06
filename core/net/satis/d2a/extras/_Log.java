/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.extras;

public class _Log {
	private String[] _log;
	private int _len;
	private int _top;
	private int _cur;

	public _Log( int len ){
		_len = len;
		_log = new String[_len];
		for( int i = 0; i < _len; i++ ){
			_log[i] = new String( "" );
		}
		_top = _len;
		_cur = 0;
	}

	public void clear(){
		for( int i = 0; i < _len; i++ ){
			_log[i] = "";
		}
		_top = _len;
	}

	public void add( String str ){
		if( _top > 0 ){
			_top--;
		}
		for( int i = _top; i < _len - 1; i++ ){
			_log[i] = _log[i + 1];
		}
		_log[_len - 1] = str;
	}

	public void beginGet(){
		_cur = _top;
	}

	public String get(){
		if( _cur >= _len ){
			return null;
		}
		_cur++;
		return _log[_cur - 1];
	}

	public int lineNum(){
		return _cur - _top;
	}
}
