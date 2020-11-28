package net.satis.launchtest;

import net.satis.d2a.*;

public class LaunchTest extends _Main {
	int step;
	int elapse;

	String error;

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		step = 0;
		elapse = 0;

		error = new String( "" );
	}

	@Override
	public void suspend(){
		step++;
	}

	@Override
	public void resume(){
		step++;
	}

	public class MyCanvas extends _Canvas {
		@Override
		public int frameTime(){ return 33/*1000 / 30*/; }

		@Override
		public void init(){
			_Graphics g = getGraphics();
			g.setAntiAlias( true );
			g.setFontSize( 32 );
		}

		@Override
		public void paint( _Graphics g ){
			elapse++;

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( 0, 0, g.getWidth(), g.getHeight() );

			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.drawString( "step : " + step, 0, 40 );

			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.drawString( "" + elapse, 0, 80 );

			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.drawString( error, 0, 120 );

			g.unlock();
		}

		@Override
		public void processEvent( int type, int param ){
			if( type == TOUCH_UP_EVENT ){
				try {
					String[] args = new String[4];
					args[0] = new String( "user" );
					args[1] = new String( "guest" );
					args[2] = new String( "score" );
					args[3] = new String( "" + elapse );
					launch( "net.satis.launchsub", "LaunchSub", args );
				} catch( Exception e ){
					error = "起動に失敗しました";
				}
			}
		}
	}
}
