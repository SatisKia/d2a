package net.satis.graphicstest3;

import android.content.res.*;
import javax.microedition.khronos.opengles.GL10;
import net.satis.d2a.*;
import net.satis.d2a.gl.*;

public class GraphicsTest3 extends _Main {
	int step;
	int x, y;
	int angle;

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		step = 0;
		x = 0;
		y = 0;
		angle = 0;
	}

	public class MyGLTexture extends _GLTexture2 {
		public MyGLTexture( GL10 gl, Resources res, int index_num, int gen_num ){
			super( gl, res, index_num, gen_num );
		}

		@Override
		public int resourceId( int index ){
			switch( index ){
			case 0: return R.drawable.sample;
			}
			return -1;
		}
	}

	public class MyCanvas extends _Canvas3D {
		MyGLTexture glt;
		_GLGraphics g;

		int width2D;
		int height2D;

		@Override
		public int frameTime(){ return 16/*1000 / 60*/; }

		@Override
		public void init3D( GL10 gl ){
			int width  = getWidth();
			int height = getHeight();

			glt = new MyGLTexture( gl, getResources(), 256, 1 );
			glt.setCanvasHeight( height );
			glt.setScale( (float)width / 400.0f );

			// 2D描画用のテクスチャ・イメージ
			width2D  = (int)((float)width  / glt.scale());
			height2D = (int)((float)height / glt.scale());
			glt.create2D( width2D, height2D );

			g = new _GLGraphics( gl, glt );
			g.setSize( width, height );
			g.setScale( (float)width / 400.0f );

//			gl.glDisable( GL10.GL_DITHER );	// ディザ処理を無効化し、なめらかな表示に

//			gl.glHint( GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST );	// GL_FASTEST or GL_NICEST

//			gl.glShadeModel( GL10.GL_SMOOTH );	// GL_FLAT or GL_SMOOTH

			gl.glViewport( 0, 0, width, height );

			gl.glMatrixMode( GL10.GL_PROJECTION );
			gl.glLoadIdentity();
			gl.glOrthof( 0.0f, (float)width, 0.0f, (float)height, -1.0f, 1.0f );

			gl.glMatrixMode( GL10.GL_MODELVIEW );
		}

		@Override
		public void end3D( GL10 gl ){
			glt.dispose();
		}

		@Override
		public void paint3D( GL10 gl ){
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

			lock3D();

			gl.glClearColor( 0.5f, 0.5f, 1.0f, 1.0f );
			gl.glClear( GL10.GL_COLOR_BUFFER_BIT );

			glt.lock( 0 );

			glt.draw( 0, 0, 0, 75, 75, x, y, 120, 120 );
			glt.setFlipMode( _GLTexture.FLIP_HORIZONTAL );
			glt.draw( 0, 90, 0, 75, 75, x, y, 120, 120 );
			glt.setFlipMode( _GLTexture.FLIP_VERTICAL );
			glt.draw( 0, 180, 0, 75, 75, x, y, 120, 120 );
			glt.setFlipMode( _GLTexture.FLIP_ROTATE );
			glt.draw( 0, 270, 0, 75, 75, x, y, 120, 120 );
			glt.setFlipMode( _GLTexture.FLIP_NONE );

			glt.unlock();

			// 描画の際の座標点指定のテスト
//			g.setOrigin( 20, 50 );

			g.setAlpha( 192 );

			g.setColor( _GLGraphics.getColorOfRGB( 0, 255, 0 ) );
			g.fillRect( 120, 120, 150, 150 );
			g.setColor( _GLGraphics.getColorOfRGB( 0, 0, 255 ) );
			g.fillRect( 180, 180, 150, 150 );
			g.setColor( _GLGraphics.getColorOfRGB( 255, 0, 0 ) );
			g.fillRect( 60, 60, 150, 150 );

			g.setColor( _GLGraphics.getColorOfRGB( 255, 255, 0 ) );
			g.setLineWidth( 5 );
			g.setAlpha( 64 );
			g.drawLine( 60, 60, 209, 209 );
			g.drawRect( 61, 61, 150, 150 );
			g.setLineWidth( 1 );
			g.setAlpha( 255 );
			g.drawLine( 60, 60, 209, 209 );
			g.drawRect( 61, 61, 150, 150 );
			g.setAlpha( 192 );

			g.lockTexture( 0 );

			g.drawScaledTexture( 0, 0, 90, 75, 75, x, y, 120, 120 );
			g.setFlipMode( _GLGraphics.FLIP_HORIZONTAL );
			g.drawScaledTexture( 0, 90, 90, 75, 75, x, y, 120, 120 );
			g.setFlipMode( _GLGraphics.FLIP_VERTICAL );
			g.drawScaledTexture( 0, 180, 90, 75, 75, x, y, 120, 120 );
			g.setFlipMode( _GLGraphics.FLIP_ROTATE );
			g.drawScaledTexture( 0, 270, 90, 75, 75, x, y, 120, 120 );
			g.setFlipMode( _GLGraphics.FLIP_NONE );

			// _Graphics クラスの drawTransTexture() と違って、
			// _GLGraphics クラスの drawTransTexture() は setFlipMode() が有効です
			g.drawTransTexture( 0, 0, 240, x, y, 120, 120, 0, 120, 45, 100, 100 );
			g.setFlipMode( _GLGraphics.FLIP_HORIZONTAL );
			g.drawTransTexture( 0, 90, 240, x, y, 120, 120, 0, 120, 45, 100, 100 );
			g.setFlipMode( _GLGraphics.FLIP_VERTICAL );
			g.drawTransTexture( 0, 180, 240, x, y, 120, 120, 0, 120, 45, 100, 100 );
			g.setFlipMode( _GLGraphics.FLIP_ROTATE );
			g.drawTransTexture( 0, 270, 240, x, y, 120, 120, 0, 120, 45, 100, 100 );
			g.setFlipMode( _GLGraphics.FLIP_NONE );

			// 拡大率をマイナス値にすることでも反転させることができます
			g.drawTransTexture( 0, 45, 390, x, y, 120, 120, 60, 60, angle, 150, 100 );
			g.drawTransTexture( 0, 135, 390, x, y, 120, 120, 60, 60, angle, -100, 150 );
			g.drawTransTexture( 0, 225, 390, x, y, 120, 120, 60, 60, angle, 150, -100 );
			g.drawTransTexture( 0, 315, 390, x, y, 120, 120, 60, 60, angle, -100, -150 );
			angle++;

			g.unlockTexture();

			g.drawLine( 0, 240, g.getWidth(), 240 );
			g.drawLine( 0, 390, g.getWidth(), 390 );

			g.setAlpha( 255 );

			// 2D描画（オフスクリーン→テクスチャ）
			_Graphics g2 = glt.lock2D();
			g2.setFontSize( 24 );
			g2.setColor( _Graphics.getColorOfRGB( 0, 0, 0 ) );
			g2.setAlpha( 128 );
			g2.fillRect( 1, 1, width2D - 2, height2D - 2 );
			g2.setAlpha( 255 );
			g2.setColor( _Graphics.getColorOfRGB( 255, 255, 0 ) );
			g2.drawString( "" + width2D + " " + height2D, 5, 35 );
			g2.drawString( "" + glt.getImage2D().getWidth() + " " + glt.getImage2D().getHeight(), 5, 65 );
			g2.drawString( "" + angle, 5, 95 );
			glt.unlock2D( true );

			unlock3D();
		}
	}
}
