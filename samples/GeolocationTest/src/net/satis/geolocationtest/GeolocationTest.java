package net.satis.geolocationtest;

import java.util.Date;

import net.satis.d2a.*;

public class GeolocationTest extends _Main {
	_Geolocation geolocation;
	String provider = new String( "" );
	int code = -2;
	long start_time = 0;
	long end_time = -1;
	boolean canStop;

	@Override
	public int orientation(){ return ORIENTATION_NOSENSOR; }

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		geolocation = new _Geolocation( this );
		geolocation.setAltitudeRequired( false );
		geolocation.setHeadingRequired( false );
		geolocation.setSpeedRequired( false );
		geolocation.setMaximumAgeSeconds( 5 );
		geolocation.setTimeoutSeconds( 30 );
	}

	@Override
	public void suspend(){
		geolocation.stop();
		code = -2;
	}

	@Override
	public void resume(){
	}

	@Override
	public void onGeolocation( int code ){
		geolocation.stop();

		this.code = code;
		end_time = _System.currentTimeMillis();
	}

	public class MyCanvas extends _Canvas {
		_ScalableGraphics g;

		@Override
		public int frameTime(){ return 33/*1000 / 30*/; }

		@Override
		public void init(){
			g = new _ScalableGraphics();
			g.setScale( (float)getWidth() / 480.0f );

			g.setGraphics( getGraphics() );
			g.setAntiAlias( true );
			g.setFontSize( 24 );
		}

		@Override
		public void paint( _Graphics _g ){
			g.setGraphics( _g );

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( 0, 0, g.getWidth(), g.getHeight() );

			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.drawString( "provider : " + provider, 0, 30 );
			String codeString = new String( "" );
			switch( code ){
			case _Geolocation.ERROR               : codeString = "(ERROR)"               ; break;
			case _Geolocation.PERMISSION_DENIED   : codeString = "(PERMISSION_DENIED)"   ; break;
			case _Geolocation.POSITION_UNAVAILABLE: codeString = "(POSITION_UNAVAILABLE)"; break;
			case _Geolocation.TIMEOUT             : codeString = "(TIMEOUT)"             ; break;
			case _Geolocation.AVAILABLE           : codeString = "(AVAILABLE)"           ; break;
			case _Geolocation.SUCCESS             : codeString = "(SUCCESS)"             ; break;
			case _Geolocation.LAST_KNOWN          : codeString = "(LAST_KNOWN)"          ; break;
			}
			g.drawString( "code : " + code + codeString, 0, 60 );
			g.drawString( "time : " + (end_time - start_time), 0, 90 );
			g.drawString( "緯度 : " + geolocation.latitude(), 0, 120 );
			g.drawString( "経度 : " + geolocation.longitude(), 0, 150 );
			g.drawString( "緯度/経度の精度 : " + geolocation.accuracy(), 0, 180 );
			g.drawString( "高度 : " + geolocation.altitude(), 0, 210 );
			g.drawString( "方角 : " + geolocation.heading(), 0, 240 );
			g.drawString( "速度 : " + geolocation.speed(), 0, 270 );
			g.drawString( "timestamp :", 0, 300 );
			g.drawString( "" + (new Date( geolocation.timestamp() )), 0, 330 );

			g.unlock();
		}

		@Override
		public void processEvent( int type, int param ){
			if( type == TOUCH_DOWN_EVENT ){
				if( code != -1 ){
					code = -1;
					start_time = _System.currentTimeMillis();
					end_time = start_time - 1;
					provider = geolocation.start( _Geolocation.PROVIDER_GPS );
					canStop = true;
				} else if( canStop ){
					canStop = false;
					start_time = _System.currentTimeMillis();
					end_time = start_time - 1;
					provider = geolocation.start( _Geolocation.PROVIDER_NETWORK );
				}
			}
		}
	}
}
