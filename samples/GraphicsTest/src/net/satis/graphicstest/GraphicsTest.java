package net.satis.graphicstest;

import android.content.res.*;
import android.graphics.*;
import net.satis.d2a.*;

public class GraphicsTest extends _Main {
	_Image img1;
	_Image img2;
	_Image img3;
	String str;

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		// リソースから作成する
		Resources r = getResources();
		img1 = _Image.createImage( r, R.drawable.sample );
		img2 = _Image.createImage( r, R.drawable.sample );
		img2.mutable();

		// オフスクリーンを作成する
		img3 = _Image.createImage( 100, 100 );

		str = new String( "Graphics Test" );

		int x, y;
		int w = img2.getWidth();
		int h = img2.getHeight();
		int pixels[] = img2.getPixels( 0, 0, w, h, null, 0 );
		for( y = 0; y < h; y++ ){
			for( x = 0; x < w; x++ ){
				int pixel = pixels[w * y + x];
				pixels[w * y + x] = Color.argb(
					Color.alpha( pixel ),
					255 - Color.red( pixel ),
					255 - Color.green( pixel ),
					255 - Color.blue( pixel )
					);
			}
		}
		img2.setPixels( 0, 0, w, h, pixels, 0 );
		_Graphics g2 = img2.getGraphics();
		g2.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
		g2.setStrokeWidth( 5 );
		g2.drawRect( 2, 2, 100, 100 );
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

			// オフスクリーンに描画
			_Graphics g2 = img3.getGraphics();
			g2.drawImage( img1, -50, -50 );

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 128, 128, 255 ) );
			g.fillRect( 0, 0, g.getWidth(), g.getHeight() );

			// クリッピングのテスト
			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( 50, 400, 300, 300 );
			g.setClip( 100, 450, 200, 200 );
			g.setColor( _Graphics.getColorOfRGB( 0, 255, 255 ) );
			g.fillRect( 50, 400, 300, 300 );
			g.clearClip();

			// 描画の際の座標原点指定のテスト
			g.setOrigin( 50, 50 );

			g.drawImage( img1, 30, 50 );
			g.setAlpha( 128 );
			g.drawImage( img1, 80, 100 );
			g.setAlpha( 255 );
			g.drawImage( img1, 30, 350, 50, 50, 150, 100 );
			g.drawScaledImage( img1, 30, 500, 300, 100, 50, 50, 150, 150 );

			g.drawImage( img2, 180, 350, 50, 50, 150, 100 );
			g.drawScaledImage( img2, 30, 600, 300, 100, 50, 50, 150, 150 );

			g.drawImage( img3, 320, 50 );
			g.drawScaledImage( img3, 300, 200, 150, 200, 0, 0, 100, 100 );

			g.setColor( _Graphics.getColorOfRGB( 255, 0, 0 ) );
			g.drawLine( 100, 100, 200, 200 );
			g.setAntiAlias( true );
			g.setStrokeWidth( 1.5f );
			g.drawLine( 200, 100, 300, 200 );
			g.setStrokeWidth( 2.0f );
			g.drawLine( 300, 100, 400, 200 );
			g.setStrokeWidth( 1.0f );
			g.setAntiAlias( false );

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 0 ) );
			g.drawRect( 100, 250, 50, 50 );
			g.drawRoundRect( 100, 320, 50, 50, 8, 8 );

			g.setColor( _Graphics.getColorOfRGB( 0, 255, 0 ) );
			g.setAlpha( 128 );
			g.fillRect( 200, 250, 50, 50 );
			g.fillRoundRect( 200, 320, 50, 50, 8, 8 );
			g.setAlpha( 64 );
			g.fillRect( 250, 250, 50, 50 );
			g.setAlpha( 255 );

			g.setFontSize( 24 );
			g.setColor( _Graphics.getColorOfRGB( 255, 0, 255 ) );
			g.drawRect( 0, 0, g.stringWidth( str ), g.fontHeight() );
			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.drawString( str, 0, g.fontHeight() );

			g.setFontSize( 48 );
			g.setColor( _Graphics.getColorOfRGB( 255, 0, 255 ) );
			g.drawRect( 150, 0, g.stringWidth( str ), g.fontHeight() );
			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );
			g.drawString( str, 150, g.fontHeight() );

			g.setColor( _Graphics.getColorOfRGB( 255, 0, 255 ) );
			g.drawOval( 10, 10, 50, 50 );
			g.fillOval( 70, 10, 50, 50 );
			g.setColor( _Graphics.getColorOfRGB( 0, 255, 255 ) );
			g.drawCircle( 35, 95, 25 );
			g.fillCircle( 95, 95, 25 );

			g.setOrigin( 0, 0 );

			g.unlock();
		}
	}
}
