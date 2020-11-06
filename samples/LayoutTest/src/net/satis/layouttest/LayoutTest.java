package net.satis.layouttest;

import net.satis.d2a.*;

public class LayoutTest extends _Main {
	String[] str;
	_Layout layout;

	@Override
	public boolean fullScreen(){ return true; }

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		str = new String[9];
		for( int i = 0; i < 9; i++ ){
			str[i] = new String( "" );
		}
	}

	public class MyCanvas extends _Canvas {
		@Override
		public int frameTime(){ return 33/*1000 / 30*/; }

		@Override
		public int touchNum(){ return 6; }

		@Override
		public void init(){
			_Graphics g = getGraphics();
			g.setAntiAlias( true );
			g.setStrokeWidth( 4 );
			g.setFontSize( 30 );

			layout = new _Layout( false );
			layout.add(  20,  20, 120, 120, 0 );
			layout.add( 180,  20, 120, 120, 1 );
			layout.add( 340,  20, 120, 120, 2 );
			layout.add(  20, 180, 120, 120, 3 );
			layout.add( 180, 180, 120, 120, 4 );
			layout.add( 340, 180, 120, 120, 5 );
			layout.add(  20, 340, 120, 120, 6 );
			layout.add( 180, 340, 120, 120, 7 );
			layout.add( 340, 340, 120, 120, 8 );
			setLayout( layout );
		}

		@Override
		public void paint( _Graphics g ){
			int state = getLayoutState();

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( 0, 0, getWidth(), getHeight() );

			for( int i = 0; i < 9; i++ ){
				g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
				if( (state & (1 << i)) != 0 ){
					g.fillRoundRect( layout.getLeft( i ), layout.getTop( i ), 120, 120, 20, 20 );
					g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
					g.drawString( str[i], layout.getLeft( i ) + 60 - g.stringWidth( str[i] ) / 2, layout.getTop( i ) + 60 + g.fontHeight() / 2 );
				} else {
					g.drawRoundRect( layout.getLeft( i ), layout.getTop( i ), 120, 120, 20, 20 );
					g.drawString( str[i], layout.getLeft( i ) + 60 - g.stringWidth( str[i] ) / 2, layout.getTop( i ) + 60 + g.fontHeight() / 2 );
				}
			}

			g.unlock();
		}

		@Override
		public void processEvent( int type, int param ){
			switch( type ){
			case LAYOUT_DOWN_EVENT:
				str[param] = "DOWN";
				break;
			case LAYOUT_UP_EVENT:
				str[param] = "UP";
				break;
			}
		}
	}
}
