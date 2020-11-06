package net.satis.launchsub;

import net.satis.d2a.*;

public class LaunchSub extends _Main {
	String user;
	int score;

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		user = getParameter( "user", "-" );
		score = getParameter( "score", 0 );
	}

	public class MyCanvas extends _Canvas {
		@Override
		public void init(){
			_Graphics g = getGraphics();
			g.setAntiAlias( true );
			g.setFontSize( 32 );
		}

		@Override
		public void paint( _Graphics g ){
			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 255, 128, 255 ) );
			g.fillRect( 0, 0, g.getWidth(), g.getHeight() );

			g.setColor( _Graphics.getColorOfRGB( 255, 0, 0 ) );
			g.drawString( "user : " + user, 0, 40 );

			g.setColor( _Graphics.getColorOfRGB( 255, 0, 0 ) );
			g.drawString( "score : " + score, 0, 80 );

			g.unlock();
		}
	}
}
