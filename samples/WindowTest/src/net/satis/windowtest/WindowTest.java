package net.satis.windowtest;

import net.satis.d2a.*;

public class WindowTest extends _Main {
	int win_left;
	int win_top;
	int win_right;
	int win_bottom;
	int win_width;
	int win_height;

	@Override
	public void start(){
		setCurrent( new MyCanvas() );
	}

	public class MyCanvas extends _Canvas {
		@Override
		public int frameTime(){ return 33/*1000 / 30*/; }

		@Override
		public void init(){
			_Graphics g = getGraphics();
			g.setAntiAlias( true );
			g.setFontSize( 24 );

			if( getWidth() > getHeight() ){
				win_top = 50;
				win_left = (getWidth() - (getHeight() - 100)) / 2;
				win_bottom = getHeight() - 50;
				win_right = win_left + (getHeight() - 100);
			} else {
				win_left   = 50;
				win_top    = (getHeight() - (getWidth() - 100)) / 2;
				win_right  = getWidth() - 50;
				win_bottom = win_top + (getWidth() - 100);
			}
			win_width  = 240;
			win_height = 240;
			setWindow( win_left, win_top, win_right, win_bottom, win_width, win_height );
		}

		@Override
		public void paint( _Graphics g ){
			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 128, 128, 255 ) );
			g.fillRect( 0, 0, getWidth(), getHeight() );

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( getWindowLeft(), getWindowTop(), getWindowRight() - getWindowLeft(), getWindowBottom() - getWindowTop() );

			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.drawString( "" + windowX( getTouchX( 0 ) ), 0, 30 );
			g.drawString( "" + windowY( getTouchY( 0 ) ), 0, 60 );

			g.unlock();
		}
	}
}
