package net.satis.helloworld;

import net.satis.d2a.*;

public class HelloWorld extends _Main {
	String str;
	int w, h;
	int x, y;
	int dx, dy;

	int elapse = 0;

	_Vibrator vibrator;

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		vibrator = new _Vibrator( this );
	}

	@Override
	public void suspend(){
		vibrator.stopVibrate();
	}

	public class MyCanvas extends _Canvas {
		@Override
		public int frameTime(){ return 33/*1000 / 30*/; }

		@Override
		public void init(){
			_Graphics g = getGraphics();
			g.setAntiAlias( true );
			g.setFontSize( 24 );

			str = new String( "Hello World !!" );
			w = g.stringWidth( str );
			h = g.fontHeight();
			x = 0;
			y = h;
			dx = 5;
			dy = 5;
		}

		@Override
		public void paint( _Graphics g ){
			elapse++;

			x += dx;
			if( (x <= 0) || (x >= getWidth() - w) ){
				dx = -dx;
				vibrator.vibrate( 100 );
			}
			y += dy;
			if( (y <= h) || (y >= getHeight()) ){
				dy = -dy;
				vibrator.vibrate( 100 );
			}

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( 0, 0, getWidth(), getHeight() );

			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.drawString( "" + elapse, 0, 30 );
			g.drawString( str, x, y );

			g.unlock();
		}
	}
}
