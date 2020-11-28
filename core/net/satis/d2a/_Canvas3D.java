/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.graphics.*;
import android.view.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class _Canvas3D extends _Canvas implements Renderer {
	private MyView _view = null;
	private int _width;
	private int _height;
	private GL10 _gl;
	private long _start_time;
	private int _paint_time;
	private int _sleep_time;
	private boolean _suspend = false;

	private class MyView extends GLSurfaceView {
		public MyView( Context context ){
			super( context );

			// フォーカス
			setFocusable( true );
			setFocusableInTouchMode( true );
		}

		@Override
		public boolean onKeyDown( int keyCode, KeyEvent event ){
			processEvent( KEY_PRESSED_EVENT, keyCode );
			return super.onKeyDown( keyCode, event );
		}

		@Override
		public boolean onKeyUp( int keyCode, KeyEvent event ){
			processEvent( KEY_RELEASED_EVENT, keyCode );
			return super.onKeyUp( keyCode, event );
		}

		@Override
		public boolean onTouchEvent( MotionEvent event ){
			return setTouchEvent( event );
		}

		@Override
		public boolean onTrackballEvent( MotionEvent event ){
			return setTrackballEvent( event );
		}
	}

	private void initSub(){
		// デプスバッファを有効にする
		_gl.glEnable( GL10.GL_DEPTH_TEST );

		int color = backgroundColor();
		float r = (float)Color.red  ( color ) / 255.0f;
		float g = (float)Color.green( color ) / 255.0f;
		float b = (float)Color.blue ( color ) / 255.0f;
		_gl.glClearColor( r, g, b, 1.0f );
		_gl.glClearDepthf( 1.0f );
	}

	private void endSub(){
	}

	@Override
	public void init(){
		initSub();

		init3D( _gl );
	}

	@Override
	public void end(){
		end3D( _gl );

		endSub();
	}

	@Override
	public SurfaceView create( Context context ){
		if( _view == null ){
			initTouch();

			_view = new MyView( context );
			_view.setRenderer( this );
		}
		resume();
		return _view;
	}

	@Override
	public void suspend(){
		super.suspend();

		if( !_suspend ){
			if( _view != null ){
				_view.onPause();
			}
			end();
			_suspend = true;
		}
	}

	@Override
	public void resume(){
		super.resume();

		if( _suspend ){
			init();
			if( _view != null ){
				_view.onResume();
			}
			_suspend = false;
		}
	}

	public void onSurfaceCreated( GL10 gl, EGLConfig config ){
	}

	public void onSurfaceChanged( GL10 gl, int width, int height ){
		_gl = gl;

		_width  = width;
		_height = height;

		// ウィンドウ設定
		setWindow( 0, 0, _width, _height, _width, _height );

		init();
	}

	public void onDrawFrame( GL10 gl ){
		_gl = gl;

		_start_time = _System.currentTimeMillis();
		try {
			paint3D( _gl );
		} catch( Exception e ){}
		_paint_time = (int)(_System.currentTimeMillis() - _start_time);
		paintTime( _paint_time );
		_sleep_time = frameTime() - _paint_time;
		if( _sleep_time > 0 ){
			try {
				Thread.sleep( _sleep_time );
			} catch( Exception e ){}
		}
	}

	@Override
	public SurfaceView getView(){
		return _view;
	}

	@Override
	public int getWidth(){
		return _width;
	}

	@Override
	public int getHeight(){
		return _height;
	}

	public void lock3D(){
		_gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );
	}

	public void unlock3D(){
	}

	public int backgroundColor(){ return 0; }

	public void init3D( GL10 gl ){}
	public void end3D( GL10 gl ){}
	public void paint3D( GL10 gl ){}
}
