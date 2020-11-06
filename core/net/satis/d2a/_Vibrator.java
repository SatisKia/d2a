/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.Context;
import android.os.Vibrator;

public class _Vibrator {
	private Vibrator _vibrator;

	public _Vibrator( _Main m ){
		_vibrator = (Vibrator)m.getSystemService( Context.VIBRATOR_SERVICE );
	}

	public void startVibrate(){
		vibrate( 99999 );
	}

	public void stopVibrate(){
		if( _vibrator != null ){
			try {
				_vibrator.cancel();
			} catch( Exception e ){}
		}
	}

	public void vibrate( long time ){
		if( _vibrator != null ){
			try {
				_vibrator.vibrate( time );
			} catch( Exception e ){}
		}
	}
}
