package net.satis.eventtest;

import net.satis.d2a.*;
import net.satis.d2a.extras._EventStep;

public class EventTest extends _Main {
	MyCanvas canvas;

	String[] str;
	boolean[] touch_down;
	boolean[] touch_move;
	boolean[] touch_up;

	MyTouchStep touchStep;

	@Override
	public int orientation(){ return ORIENTATION_NOSENSOR; }

	@Override
	public void start(){
		canvas = new MyCanvas();
		setCurrent( canvas );

		touchStep = new MyTouchStep();
	}

	@Override
	public void resume(){
		for( int i = 0; i < canvas.touchNum(); i++ ){
			touch_down[i] = false;
			touch_move[i] = false;
			touch_up  [i] = false;
		}
	}

	public class MyCanvas extends _Canvas {
		_ScalableGraphics g;

		@Override
		public int frameTime(){ return 33/*1000 / 30*/; }

		@Override
		public int touchNum(){ return 6; }

		@Override
		public void init(){
			int i;

			g = new _ScalableGraphics();
			g.setScale( (float)getWidth() / 480.0f );

			g.setGraphics( getGraphics() );
			g.setAntiAlias( true );
			g.setFontSize( 24 );
			g.setStrokeWidth( 1.0f );

			str = new String[5];
			str[0] = new String( "KEY_EVENT" );
			str[1] = new String( "param : " );
			str[2] = new String( "TRACKBALL_EVENT" );
			str[3] = new String( "trackballX : " );
			str[4] = new String( "trackballY : " );

			touch_down = new boolean[touchNum()];
			touch_move = new boolean[touchNum()];
			touch_up   = new boolean[touchNum()];
			for( i = 0; i < touchNum(); i++ ){
				touch_down[i] = false;
				touch_move[i] = false;
				touch_up  [i] = false;
			}
		}

		@Override
		public void paint( _Graphics _g ){
			g.setGraphics( _g );

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( 0, 0, g.getWidth(), g.getHeight() );

			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );

			g.drawString( str[0], 0, 30 );
			g.drawString( str[1], 0, 60 );

			g.drawLine( 0, 80, g.getWidth(), 80 );

			g.drawString( str[2], 0, 120 );
			g.drawString( str[3], 0, 150 );
			g.drawString( str[4], 0, 180 );

			g.drawLine( 0, 200, g.getWidth(), 200 );

			g.drawString( "TOUCH_EVENT", 0, 240 );
			for( int i = 0; i < touchNum(); i++ ){
				g.drawString( touch_down[i] ? "DOWN" : "", i * 80, 270 );
				g.drawString( touch_move[i] ? "MOVE" : "", i * 80, 300 );
				g.drawString( touch_up  [i] ? "UP"   : "", i * 80, 330 );
				g.drawString( "x : " + (int)((float)getTouchX( i ) / g.scale()), i * 80, 360 );
				g.drawString( "y : " + (int)((float)getTouchY( i ) / g.scale()), i * 80, 390 );
			}

			g.drawLine( 0, 410, g.getWidth(), 410 );

			g.drawString( "_EventStep TEST", 0, 450 );
			g.drawString( "touch step : " + touchStep.step(), 0, 480 );
			if( touchStep.isTimeout() ){
				g.drawString( "TIMEOUT", 0, 510 );
			}

			g.unlock();
		}

		@Override
		public void processEvent( int type, int param ){
			touchStep.handleEvent( type, param );

			switch( type ){
			case KEY_PRESSED_EVENT:
				str[0] = "KEY_PRESSED_EVENT";
				str[1] = "param : " + param;	// KeyEvent.KEYCODE_*
				break;
			case KEY_RELEASED_EVENT:
				str[0] = "KEY_RELEASED_EVENT";
				str[1] = "param : " + param;	// KeyEvent.KEYCODE_*
				break;
			case TOUCH_DOWN_EVENT:
				touch_down[param] = true;
				touch_move[param] = false;
				touch_up  [param] = false;
				break;
			case TOUCH_MOVE_EVENT:
				touch_move[param] = true;
				break;
			case TOUCH_UP_EVENT:
				touch_down[param] = false;
				touch_move[param] = false;
				touch_up  [param] = true;
				break;
			case TRACKBALL_DOWN_EVENT:
				str[2] = "TRACKBALL_DOWN_EVENT";
				str[3] = "trackballX : " + getTrackballX();
				str[4] = "trackballY : " + getTrackballY();
				break;
			case TRACKBALL_MOVE_EVENT:
				str[2] = "TRACKBALL_MOVE_EVENT";
				str[3] = "trackballX : " + getTrackballX();
				str[4] = "trackballY : " + getTrackballY();
				break;
			case TRACKBALL_UP_EVENT:
				str[2] = "TRACKBALL_UP_EVENT";
				str[3] = "trackballX : " + getTrackballX();
				str[4] = "trackballY : " + getTrackballY();
				break;
			}
		}
	}

	public class MyTouchStep extends _EventStep {
		public MyTouchStep(){
			start( _Canvas.TOUCH_DOWN_EVENT, _Canvas.TOUCH_UP_EVENT );
		}

		public boolean checkParam( int param ){
			return true;
		}

		public int checkTime(){ return 200/*1000 / 5*/; }
	}
}
