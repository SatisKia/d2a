package net.satis.graphicstest2;

import android.content.res.*;
import net.satis.d2a.*;

public class GraphicsTest2 extends _Main {
	_Image img;
	int step;
	int x, y;
	int angle;

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		// リソースから作成する
		Resources r = getResources();
		img = _Image.createImage( r, R.drawable.sample );

		step = 0;
		x = 0;
		y = 0;
		angle = 0;
	}

	public class MyCanvas extends _Canvas {
		_ScalableGraphics g;

		@Override
		public int frameTime(){ return 33/*1000 / 30*/; }

		@Override
		public void init(){
			g = new _ScalableGraphics();
			g.setScale( (float)getWidth() / 480.0f );
		}

		@Override
		public void paint( _Graphics _g ){
			g.setGraphics( _g );

			switch( step ){
			case 0:
				x++; if( x >= 60 ) step++;
				break;
			case 1:
				y++; if( y >= 60 ) step++;
				break;
			case 2:
				x--; if( x <= 0 ) step++;
				break;
			case 3:
				y--; if( y <= 0 ) step = 0;
				break;
			}

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 128, 128, 255 ) );
			g.fillRect( 0, 0, g.getWidth(), g.getHeight() );

			g.setAlpha( 192 );

			g.setColor( _Graphics.getColorOfRGB( 255, 0, 0 ) );
			g.fillRect( 80, 80, 200, 200 );
			g.setColor( _Graphics.getColorOfRGB( 0, 255, 0 ) );
			g.fillRect( 160, 160, 200, 200 );
			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.fillRect( 240, 240, 200, 200 );

			g.drawScaledImage( img, 0, 0, 100, 100, x, y, 120, 120 );
			g.setFlipMode( _Graphics.FLIP_HORIZONTAL );
			g.drawScaledImage( img, 120, 0, 100, 100, x, y, 120, 120 );
			g.setFlipMode( _Graphics.FLIP_VERTICAL );
			g.drawScaledImage( img, 240, 0, 100, 100, x, y, 120, 120 );
			g.setFlipMode( _Graphics.FLIP_ROTATE );
			g.drawScaledImage( img, 360, 0, 100, 100, x, y, 120, 120 );
			g.setFlipMode( _Graphics.FLIP_NONE );

			// drawTransImage() では setFlipMode() が効かないことを示しています
			g.drawTransImage( img, 0, 160, x, y, 120, 120, 0, 120, 45, 100, 100 );
			g.setFlipMode( _Graphics.FLIP_HORIZONTAL );
			g.drawTransImage( img, 120, 160, x, y, 120, 120, 0, 120, 45, 100, 100 );
			g.setFlipMode( _Graphics.FLIP_VERTICAL );
			g.drawTransImage( img, 240, 160, x, y, 120, 120, 0, 120, 45, 100, 100 );
			g.setFlipMode( _Graphics.FLIP_ROTATE );
			g.drawTransImage( img, 360, 160, x, y, 120, 120, 0, 120, 45, 100, 100 );
			g.setFlipMode( _Graphics.FLIP_NONE );

			g.drawLine( 0, 160, g.getWidth(), 160 );

			// drawTransImage() で反転させるには、拡大率をマイナス値にします
			g.drawTransImage( img, 60, 300, x, y, 120, 120, 60, 60, angle, 150, 100 );
			g.drawTransImage( img, 180, 300, x, y, 120, 120, 60, 60, angle, -100, 150 );
			g.drawTransImage( img, 300, 300, x, y, 120, 120, 60, 60, angle, 150, -100 );
			g.drawTransImage( img, 420, 300, x, y, 120, 120, 60, 60, angle, -100, -150 );
			angle++;

			g.drawLine( 0, 300, g.getWidth(), 300 );

			g.setAlpha( 255 );

			g.unlock();
		}
	}
}
