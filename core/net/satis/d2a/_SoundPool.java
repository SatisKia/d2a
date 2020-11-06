/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.media.*;

public class _SoundPool {
	private _Main _m;
	private int _num;
	private SoundPool _pool;
	private int[] _load_id;
	private int[] _stream_id;

	private int _volume = 100;

	public _SoundPool( _Main m, int num ){
		_m = m;
		_num = num;
		_pool = new SoundPool( _num, AudioManager.STREAM_MUSIC, 0 );
		_load_id = new int[_num];
		_stream_id = new int[_num];
		for( int i = 0; i < _num; i++ ){
			_load_id[i] = -1;
			_stream_id[i] = -1;
		}
	}

	public void create( int index, int res_id ){
		dispose( index );
		_load_id[index] = _pool.load( _m, res_id, 1 );
	}

	public void dispose( int index ){
		if( _load_id[index] >= 0 ){
			_pool.unload( _load_id[index] );
			_load_id[index] = -1;
			_stream_id[index] = -1;
		}
	}

	public void play( int index ){
		if( _load_id[index] >= 0 ){
			if( _m.getAudioManager().getRingerMode() == AudioManager.RINGER_MODE_NORMAL ){
				float volume = (float)(_volume * _m.volumeSound() / 100) / 100.0f;
				_stream_id[index] = _pool.play( _load_id[index], volume, volume, 1, 0, 1.0f );
			}
		}
	}

	public void stop( int index ){
		if( _stream_id[index] >= 0 ){
			_pool.stop( _stream_id[index] );
			_stream_id[index] = -1;
		}
	}

	public void setVolume( int volume ){
		_volume = volume;
	}

	public int volume(){
		return _volume;
	}

	public void release(){
		_pool.release();
		_pool = new SoundPool( _num, AudioManager.STREAM_MUSIC, 0 );
		for( int i = 0; i < _num; i++ ){
			_load_id[i] = -1;
			_stream_id[i] = -1;
		}
	}
}
