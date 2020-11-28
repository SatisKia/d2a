/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.Context;
import android.view.*;

public class _Canvas implements SurfaceHolder.Callback, Runnable {
	private MyView _view = null;
	private SurfaceHolder _holder = null;
	private _Graphics _g = null;
	private Thread _thread;
	private boolean _run = false;
	private boolean _suspend = false;
	private boolean _hide = false;

	// ウィンドウ設定
	private int _win_left;
	private int _win_top;
	private int _win_right;
	private int _win_bottom;
	private int _win_width;
	private int _win_height;

	// レイアウト
	private _Layout _layout = null;

	// イベント
	public static final int KEY_PRESSED_EVENT    = 0;
	public static final int KEY_RELEASED_EVENT   = 1;
	public static final int TOUCH_DOWN_EVENT     = 2;
	public static final int TOUCH_MOVE_EVENT     = 3;
	public static final int TOUCH_UP_EVENT       = 4;
	public static final int TRACKBALL_DOWN_EVENT = 5;
	public static final int TRACKBALL_MOVE_EVENT = 6;
	public static final int TRACKBALL_UP_EVENT   = 7;
	public static final int LAYOUT_DOWN_EVENT    = 8;
	public static final int LAYOUT_UP_EVENT      = 9;

	// タッチイベント用
	private boolean[] _touch_down;
	private float[] _touch_x;
	private float[] _touch_y;
	private boolean[] _old_touch_down;

	// トラックボールイベント用
	private float _trackball_x;
	private float _trackball_y;

	public static final int POINTER_INDEX_MASK  = 0x0000ff00;
	public static final int POINTER_INDEX_SHIFT = 0x00000008;

	private class MyView extends SurfaceView {
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

	public SurfaceView create( Context context ){
		if( _view == null ){
			// タッチイベント用
			initTouch();

			// サーフェイスビュー
			_view = new MyView( context );

			// サーフェイスホルダー
			_holder = _view.getHolder();
			_holder.addCallback( this );

			_g = new _Graphics( this );
		}
		return _view;
	}

	public void suspend(){
		if( !_suspend ){
			for( int i = touchNum() - 1; i >= 0; i-- ){
				if( _touch_down[i] ){
					_touch_down[i] = false;
					if( !setLayoutEvent( LAYOUT_UP_EVENT, i ) ){
						processEvent( TOUCH_UP_EVENT, i );
					}
				}
			}
			_suspend = true;
		}
	}

	public void resume(){
		if( _suspend ){
			_suspend = false;
		}
	}

	public boolean hideFlag(){
		return _hide;
	}

	public void setHide( boolean flag ){
		if( flag ){
			suspend();
			hide();
		} else {
			show();
			resume();
		}
		_hide = flag;
	}

	// サーフェイスイベント
	public void surfaceCreated( SurfaceHolder holder ){
		int width  = getWidth();
		int height = getHeight();

		_holder.setFixedSize( width, height );

		// ウィンドウ設定
		setWindow( 0, 0, width, height, width, height );

		init();

		_thread = new Thread( this );
		_thread.start();
	}
	public void surfaceDestroyed( SurfaceHolder holder ){
		_thread = null;
		while( _run ){
			try {
				Thread.sleep( frameTime() );
			} catch( Exception e ){}
		}
		end();
	}
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ){
	}

	public void run(){
		long start_time;
		int paint_time;
		int sleep_time;
		_run = true;
		while( _thread != null ){
			start_time = _System.currentTimeMillis();
			if( !_suspend ){
				try {
					paint( _g );
				} catch( Exception e ){}
			}
			paint_time = (int)(_System.currentTimeMillis() - start_time);
			paintTime( paint_time );
			sleep_time = frameTime() - paint_time;
			if( sleep_time > 0 ){
				try {
					Thread.sleep( sleep_time );
				} catch( Exception e ){}
			}
		}
		_run = false;
	}

	public SurfaceView getView(){
		return _view;
	}

	public SurfaceHolder getHolder(){
		return _holder;
	}

	public int getWidth(){
		return _view.getWidth();
	}
	public int getHeight(){
		return _view.getHeight();
	}

	public _Graphics getGraphics(){
		return _g;
	}

	protected void initTouch(){
		_touch_down = new boolean[touchNum()];
		_touch_x = new float[touchNum()];
		_touch_y = new float[touchNum()];
		_old_touch_down = new boolean[touchNum()];
		for( int i = touchNum() - 1; i >= 0; i-- ){
			_touch_down[i] = false;
			_old_touch_down[i] = false;
		}
	}
	public boolean setTouchEvent( MotionEvent event ){
		int num = touchNum();
		if( num == 1 ){
			_touch_x[0] = event.getX();
			_touch_y[0] = event.getY();
			switch( event.getAction() ){
			case MotionEvent.ACTION_DOWN:
				_touch_down[0] = true;
				if( !setLayoutEvent( LAYOUT_DOWN_EVENT, 0 ) ){
					processEvent( TOUCH_DOWN_EVENT, 0 );
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if( _touch_down[0] ){
					processEvent( TOUCH_MOVE_EVENT, 0 );
				}
				break;
			case MotionEvent.ACTION_UP:
				if( _touch_down[0] ){
					_touch_down[0] = false;
					if( !setLayoutEvent( LAYOUT_UP_EVENT, 0 ) ){
						processEvent( TOUCH_UP_EVENT, 0 );
					}
				}
				break;
			}
		} else {
			int i;
			for( i = 0; i < num; i++ ){
				_old_touch_down[i] = _touch_down[i];
				_touch_down[i] = false;
			}
			int count = event.getPointerCount();
			int id;
			float x, y;
			for( i = 0; i < count; i++ ){
				id = event.getPointerId( i );
				if( id < num ){
					_touch_down[id] = true;
					if( !_old_touch_down[id] ){
						_touch_x[id] = event.getX( i );
						_touch_y[id] = event.getY( i );
						if( !setLayoutEvent( LAYOUT_DOWN_EVENT, id ) ){
							processEvent( TOUCH_DOWN_EVENT, id );
						}
					} else {
						x = event.getX( i );
						y = event.getY( i );
						if( (_touch_x[id] != x) || (_touch_y[id] != y) ){
							_touch_x[id] = x;
							_touch_y[id] = y;
							processEvent( TOUCH_MOVE_EVENT, id );
						}
					}
				}
			}
			int action = event.getAction();
			id = event.getPointerId( (action & POINTER_INDEX_MASK) >> POINTER_INDEX_SHIFT );
			if( id < num ){
				switch( action & MotionEvent.ACTION_MASK ){
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					_touch_down[id] = false;
					break;
				}
			}
			for( i = 0; i < num; i++ ){
				if( _old_touch_down[i] && !_touch_down[i] ){
					if( !setLayoutEvent( LAYOUT_UP_EVENT, i ) ){
						processEvent( TOUCH_UP_EVENT, i );
					}
				}
			}
		}
		return true;
	}

	public int getTouchX( int id ){
		return (int)_touch_x[id];
	}
	public int getTouchY( int id ){
		return (int)_touch_y[id];
	}

	public boolean setTrackballEvent( MotionEvent event ){
		_trackball_x = event.getX() * 100.0f;
		_trackball_y = event.getY() * 100.0f;
		switch( event.getAction() ){
		case MotionEvent.ACTION_DOWN:
			processEvent( TRACKBALL_DOWN_EVENT, 0 );
			break;
		case MotionEvent.ACTION_MOVE:
			processEvent( TRACKBALL_MOVE_EVENT, 0 );
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			processEvent( TRACKBALL_UP_EVENT, 0 );
			break;
		}
		return true;
	}

	public int getTrackballX(){
		return (int)_trackball_x;
	}
	public int getTrackballY(){
		return (int)_trackball_y;
	}

	public void setWindow( int left, int top, int right, int bottom, int width, int height ){
		_win_left   = left;
		_win_top    = top;
		_win_right  = right;
		_win_bottom = bottom;
		_win_width  = width;
		_win_height = height;
	}
	public void setWindow( int width, int height ){
		int w, h;
		if( (float)getHeight() / (float)height < (float)getWidth() / (float)width ){
			h = getHeight();
			w = (width * h) / height;
		} else {
			w = getWidth();
			h = (height * w) / width;
		}
		_win_left   = (getWidth () - w) / 2;
		_win_top    = (getHeight() - h) / 2;
		_win_right  = _win_left + w;
		_win_bottom = _win_top  + h;
		_win_width  = width;
		_win_height = height;
	}
	public int getWindowLeft(){
		return _win_left;
	}
	public int getWindowTop(){
		return _win_top;
	}
	public int getWindowRight(){
		return _win_right;
	}
	public int getWindowBottom(){
		return _win_bottom;
	}

	public int windowX( int x ){
		return (x - _win_left) * _win_width / (_win_right - _win_left);
	}
	public int windowY( int y ){
		return (y - _win_top) * _win_height / (_win_bottom - _win_top);
	}

	public int screenX( int x ){
		return _win_left + x * (_win_right - _win_left) / _win_width;
	}
	public int screenY( int y ){
		return _win_top + y * (_win_bottom - _win_top) / _win_height;
	}
	public int screenWidth( int width ){
		return width * (_win_right - _win_left) / _win_width;
	}
	public int screenHeight( int height ){
		return height * (_win_bottom - _win_top) / _win_height;
	}

	public void setLayout( _Layout layout ){
		_layout = layout;
	}
	private boolean setLayoutEvent( int type, int id ){
		if( _layout != null ){
			int param = -1;
			if( _layout.isWindow() ){
				param = _layout.check( windowX( (int)_touch_x[id] ), windowY( (int)_touch_y[id] ) );
			} else {
				param = _layout.check( (int)_touch_x[id], (int)_touch_y[id] );
			}
			if( param >= 0 ){
				processEvent( type, param );
				return true;
			}
		}
		return false;
	}
	public int getLayoutState(){
		int ret = 0;
		if( _layout != null ){
			for( int i = touchNum() - 1; i >= 0; i-- ){
				if( _touch_down[i] ){
					int id = -1;
					if( _layout.isWindow() ){
						id = _layout.check( windowX( (int)_touch_x[i] ), windowY( (int)_touch_y[i] ) );
					} else {
						id = _layout.check( (int)_touch_x[i], (int)_touch_y[i] );
					}
					if( id >= 0 ){
						ret |= (1 << id);
					}
				}
			}
		}
		return ret;
	}

	public void drawLayout( _Graphics g ){
		if( _layout != null ){
			_layout.draw( this, g );
		}
	}

	public int frameTime(){ return 16/*1000 / 60*/; }
	public int touchNum(){ return 2; }

	public void init(){}
	public void end(){}
	public void paint( _Graphics g ){}
	public void paintTime( int time ){}
	public void hide(){}
	public void show(){}
	public void processEvent( int type, int param ){}
}
