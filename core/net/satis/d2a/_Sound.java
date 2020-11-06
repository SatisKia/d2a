/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.media.*;

public class _Sound {
	private _Main _m;
	private int _num;
	private MediaPlayer[] _sound;
	private boolean[] _playing;

	private int _volume = 100;

	public _Sound( _Main m, int num ){
		_m = m;
		_num = num;
		_sound = new MediaPlayer[num];
		_playing = new boolean[num];
		for( int i = 0; i < _num; i++ ){
			_sound[i] = null;
			_playing[i] = false;
		}
	}

	public void create( int index, int res_id ){
		dispose( index );
		_sound[index] = MediaPlayer.create( _m, res_id );
//		try {
//			_sound[index].prepare();
//		} catch( Exception e ){}
	}

	public void dispose( int index ){
		stop( index );
		if( _sound[index] != null ){
			try {
				_sound[index].stop();
			} catch( Exception e ){}
			_sound[index].release();
			_sound[index] = null;
		}
	}

	public void play( int index, boolean loop ){
		stop( index );
		if( _sound[index] != null ){
			if( !(_m.silentMode()) ){
				float volume = (float)(_volume * _m.volumeSound() / 100) / 100.0f;
				try {
					_sound[index].setLooping( loop );
					_sound[index].setVolume( volume, volume );
					_sound[index].seekTo( 0 );
					_sound[index].start();
				} catch( Exception e ){}
			}
			_playing[index] = true;
		}
	}
	public void play( int index ){
		play( index, false );
	}

	public void stop( int index ){
		if( _playing[index] ){
			if( !(_m.silentMode()) ){
				try {
					_sound[index].pause();
				} catch( Exception e ){}
			}
			_playing[index] = false;
		}
	}

	public void setVolume( int volume ){
		_volume = volume;
	}

	public int volume(){
		return _volume;
	}

	public boolean isPlaying( int index ){
		if( _playing[index] ){
			try {
				return _sound[index].isPlaying();
			} catch( Exception e ){}
		}
		return false;
	}

	public void release(){
		for( int i = 0; i < _num; i++ ){
			dispose( i );
		}
	}
}
