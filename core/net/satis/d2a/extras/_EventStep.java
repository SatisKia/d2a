/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.extras;

import net.satis.d2a._System;

public class _EventStep extends Object {
	private int _event_on;
	private int _event_off;
	private int _step = 0;
	private long _time;

	public void start( int event_on, int event_off ){
		_event_on = event_on;
		_event_off = event_off;
		_time = _System.currentTimeMillis();
	}

	public void handleEvent( int type, int param ){
		if( (type == _event_on) && checkParam( param ) ){
			long now_time = _System.currentTimeMillis();
			if( isTimeout( now_time ) ){
				_step = 1;
			} else {
				if( (_step % 2) == 0 ){
					_step++;
				}
			}
			_time = now_time;
		}
		else if( (type == _event_off) && checkParam( param ) ){
			long now_time = _System.currentTimeMillis();
			if( isTimeout( now_time ) ){
				_step = 0;
			} else {
				if( (_step % 2) == 1 ){
					_step++;
				}
			}
			_time = now_time;
		}
	}

	public int step(){
		return _step;
	}

	public void reset(){
		_step = 0;
	}

	public boolean isTimeout( long now_time ){
		if( _step == 0 ){
			return false;
		}
		return ((int)(now_time - _time) > checkTime());
	}
	public boolean isTimeout(){
		return isTimeout( _System.currentTimeMillis() );
	}

	public boolean checkParam( int param ){ return true; }
	public int checkTime(){ return 200/*1000 / 5*/; }
}
