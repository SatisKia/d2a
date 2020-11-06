package net.satis.apptest;

import net.satis.d2a.*;
import net.satis.d2a.extras._Log;

public class AppTest extends _Main {
	@Override
	public int orientation(){ return ORIENTATION_PORTRAIT; }

//	@Override
//	public boolean fullScreen(){ return true; }

	public static final int LOG_NUM = 20;
	private _Log log;

	@Override
	public void start(){
		log = new _Log( LOG_NUM );

		log.add( "start" );

		setCurrent( new MyCanvas() );
	}

	@Override
	public void suspend(){
		log.add( "suspend" );
	}

	@Override
	public void resume(){
		log.add( "resume" );
	}

	public class MyCanvas extends _Canvas {
		public int frameTime(){ return 33/*1000 / 30*/; }

		@Override
		public void init(){
			log.add( "init" );

			_Graphics g = getGraphics();
			g.setAntiAlias( true );
			g.setFontSize( getHeight() / LOG_NUM );
		}

		@Override
		public void end(){
			log.add( "end" );
		}

		@Override
		public void paint( _Graphics g ){
			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( 0, 0, getWidth(), getHeight() );
			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );

			String str;
			log.beginGet();
			while( (str = log.get()) != null ){
				g.drawString( str, 0, (getHeight() / LOG_NUM) * log.lineNum() );
			}

			g.unlock();
		}
	}
}
