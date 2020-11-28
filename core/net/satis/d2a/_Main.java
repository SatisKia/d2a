/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;

public class _Main extends AppCompatActivity {
	private _Canvas _canvas = null;
	private ViewGroup _view = null;
	private boolean _suspend = false;

	private AudioManager _audio;

	// 画面の向き
	public static final int ORIENTATION_NOSENSOR  = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR;
	public static final int ORIENTATION_LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	public static final int ORIENTATION_PORTRAIT  = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );

		if( orientation() == ORIENTATION_NOSENSOR ){
			Configuration config = getResources().getConfiguration();
			if( config.orientation == Configuration.ORIENTATION_LANDSCAPE ){
				setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
			} else if( config.orientation == Configuration.ORIENTATION_PORTRAIT ){
				setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
			}
		} else {
			setRequestedOrientation( orientation() );
		}

		if( fullScreen() ){
			getWindow().addFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN );
		}

		requestWindowFeature( Window.FEATURE_NO_TITLE );

		// オーディオ関連
		setVolumeControlStream( AudioManager.STREAM_MUSIC );
		_audio = (AudioManager)getSystemService( Context.AUDIO_SERVICE );

		start();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		destroy();
	}

	@Override
	public void onPause(){
		super.onPause();
		if( !_suspend ){
			if( _canvas != null ){
				_canvas.suspend();
			}
			suspend();
			_suspend = true;
		}
	}

	@Override
	public void onResume(){
		super.onResume();
		if( _suspend ){
			if( _canvas != null ){
				_canvas.resume();
			}
			resume();
			_suspend = false;
		}
	}

	@Override
	public void onConfigurationChanged( Configuration config ){
		super.onConfigurationChanged( config );
		configurationChanged( config );
	}

	public AudioManager getAudioManager(){
		return _audio;
	}

	public void setCurrent( _Canvas canvas ){
		if( _view != null ){
			return;
		}
		if( _canvas != null ){
			_canvas.setHide( true );
		}
		_canvas = canvas;
		setContentView( _canvas.create( this ) );
		if( _canvas.hideFlag() ){
			_canvas.setHide( false );
		}
	}

	public _Canvas getCurrent(){
		return _canvas;
	}

	public void setView( ViewGroup view ){
		if( _canvas != null ){
			return;
		}
		_view = view;
		setContentView( _view );
	}

	public ViewGroup getView(){
		return _view;
	}

	public void addCanvas( _Canvas canvas ){
		if( _view == null ){
			return;
		}
		if( _canvas != null ){
			return;
		}
		_canvas = canvas;
		_view.addView( _canvas.create( this ) );
	}

	public void terminate(){
		finish();
	}

	public String getParameter( String name ){
		return getIntent().getStringExtra( name );
	}
	public String getParameter( String name, String defString ){
		String string = getParameter( name );
		if( string != null ){
			return string;
		}
		return defString;
	}
	public int getParameter( String name, int defInteger ){
		String string = getParameter( name );
		if( string != null ){
			return Integer.parseInt( string );
		}
		return defInteger;
	}

	public String getResString( int id ){
		return getResources().getString( id );
	}

	public void launch( String packageName, String className, String[] args ){
		Intent intent = new Intent();
		intent.setClassName( packageName, packageName + "." + className );
		intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
		if( args != null ){
			for( int i = 0; i < args.length / 2; i++ ){
				intent.putExtra( args[i * 2], args[i * 2 + 1] );
			}
		}
		startActivity( intent );
	}

	public void openBrowser( String url ){
		Uri uri = Uri.parse( url );
		Intent intent = new Intent( Intent.ACTION_VIEW, uri );
		startActivity( intent );
	}

	public boolean silentMode(){
		if( enableSilentMode() ){
			return (_audio.getRingerMode() != AudioManager.RINGER_MODE_NORMAL);
		}
		return false;
	}

	public int orientation(){ return ORIENTATION_NOSENSOR; }
	public boolean fullScreen(){ return false; }
	public boolean enableSilentMode(){ return false; }

	public void start(){}
	public void destroy(){}
	public void suspend(){}
	public void resume(){}
	public void configurationChanged( Configuration config ){}

	// _Geolocation クラス用
	public void onGeolocation( int code ){}

	// _HttpRequest クラス用
	public void onHttpResponse( InputStream is ){}
	public void onHttpError( int status ){}

	// _Music クラス用
	public int volumeMusic(){ return 100; }
	public void musicComplete( int index ){}

	// _Sound／_SoundPool クラス用
	public int volumeSound(){ return 100; }

	// _WebView クラス用
	public boolean onWebViewShouldStartLoad( String url ){ return false; }
	public void onWebViewStartLoad( String url ){}
	public void onWebViewFinishLoad( String url ){}
	public void onWebViewLoadError( int errorCode, String description, String url ){}
}
