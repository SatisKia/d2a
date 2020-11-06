package net.satis.sensortest;

import net.satis.d2a.*;

public class SensorTest extends _Main {
	_Sensor sensor;

	@Override
	public int orientation(){ return ORIENTATION_NOSENSOR; }

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		sensor = new _Sensor();
		sensor.start( this );
	}

	@Override
	public void suspend(){
		sensor.stop();
	}

	@Override
	public void resume(){
		sensor.restart();
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
			g.drawString( "accelX : "       + (int)(sensor.getAccelX() * 100), 0, 30 );
			g.drawString( "accelY : "       + (int)(sensor.getAccelY() * 100), 0, 60 );
			g.drawString( "accelZ : "       + (int)(sensor.getAccelZ() * 100), 0, 90 );
			g.drawString( "gravityX : "     + (int)sensor.getGravityX(), 0, 120 );
			g.drawString( "gravityY : "     + (int)sensor.getGravityY(), 0, 150 );
			g.drawString( "gravityZ : "     + (int)sensor.getGravityZ(), 0, 180 );
			g.drawString( "linearAccelX : " + (int)(sensor.getLinearAccelX() * 100), 0, 210 );
			g.drawString( "linearAccelY : " + (int)(sensor.getLinearAccelY() * 100), 0, 240 );
			g.drawString( "linearAccelZ : " + (int)(sensor.getLinearAccelZ() * 100), 0, 270 );
			g.drawString( "azimuth : "      + (int)sensor.getAzimuth(), 0, 300 );
			g.drawString( "pitch : "        + (int)sensor.getPitch  (), 0, 330 );
			g.drawString( "roll : "         + (int)sensor.getRoll   (), 0, 360 );

			g.unlock();
		}
	}
}
