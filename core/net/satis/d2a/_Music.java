/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.media.*;

public class _Music implements MediaPlayer.OnCompletionListener {
	private _Main _m;
	private int _num;
	private MediaPlayer[] _music;
	private int _index;

	private int _volume = 100;

	public _Music( _Main m, int num ){
		_m = m;
		_num = num;
		_music = new MediaPlayer[_num];
		for( int i = 0; i < _num; i++ ){
			_music[i] = null;
		}
		_index = -1;
	}

	public void create( int index, int res_id ){
		dispose( index );
		_music[index] = MediaPlayer.create( _m, res_id );
//		try {
//			_music[index].prepare();
//		} catch( Exception e ){}
	}

	public void dispose( int index ){
		stop();
		if( _music[index] != null ){
			try {
				_music[index].stop();
			} catch( Exception e ){}
			_music[index].release();
			_music[index] = null;
		}
	}

	public void play( int index, int time, boolean loop ){
		stop();
		if( _music[index] != null ){
			if( !(_m.silentMode()) ){
				float volume = (float)(_volume * _m.volumeMusic() / 100) / 100.0f;
				try {
					_music[index].setLooping( loop );
					_music[index].setVolume( volume, volume );
					_music[index].seekTo( time );
					_music[index].start();
					_music[index].setOnCompletionListener( this );
				} catch( Exception e ){}
			}
			_index = index;
		}
	}
	public void play( int index, boolean loop ){
		play( index, 0, loop );
	}

	public void stop(){
		if( _index >= 0 ){
			if( _music[_index] != null ){
				if( !(_m.silentMode()) ){
					try {
						_music[_index].setOnCompletionListener( null );
						_music[_index].pause();
					} catch( Exception e ){}
				}
			}
			_index = -1;
		}
	}

	public void setVolume( int volume ){
		_volume = volume;
		if( _index >= 0 ){
			if( _music[_index] != null ){
				float tmp = (float)(_volume * _m.volumeMusic() / 100) / 100.0f;
				_music[_index].setVolume( tmp, tmp );
			}
		}
	}

	public int volume(){
		return _volume;
	}

	public boolean isPlaying(){
		if( _index >= 0 ){
			if( _music[_index] != null ){
				try {
					return _music[_index].isPlaying();
				} catch( Exception e ){}
			}
		}
		return false;
	}

	public void setCurrentTime( int time ){
		if( _index >= 0 ){
			if( _music[_index] != null ){
				try {
					_music[_index].seekTo( time );
				} catch( Exception e ){}
			}
		}
	}

	public int getCurrentTime(){
		if( _index >= 0 ){
			if( _music[_index] != null ){
				return _music[_index].getCurrentPosition();
			}
		}
		return 0;
	}

	public int getTotalTime(){
		if( _index >= 0 ){
			if( _music[_index] != null ){
				return _music[_index].getDuration();
			}
		}
		return 0;
	}

	public void release(){
		for( int i = 0; i < _num; i++ ){
			dispose( i );
		}
		_index = -1;
	}

	public void onCompletion( MediaPlayer mediaPlayer ){
		_m.musicComplete( _index );
    }
}
