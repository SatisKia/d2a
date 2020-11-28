/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.gl;

import android.graphics.*;
import java.nio.*;
import javax.microedition.khronos.opengles.GL10;
import net.satis.d2a.*;

public class _GLGraphics extends _Scalable {
	private GL10 _gl;
	private _GLTexture _glt;

	private int _width;
	private int _height;

	// 線幅
	private float _line_width;
	private float _line_expand;

	// 色
	private float _r;
	private float _g;
	private float _b;

	// 透明度
//	private int _a255;
	private float _a;

	// ラスターオペレーション
	public static final int ROP_COPY = 0;
	public static final int ROP_ADD  = 1;
	private int _rop;

	// イメージ描画時の反転方法
	public static final int FLIP_NONE       = 0;
	public static final int FLIP_HORIZONTAL = 1;
	public static final int FLIP_VERTICAL   = 2;
	public static final int FLIP_ROTATE     = 3;
	private int _flipmode = FLIP_NONE;

	// 描画の際の座標原点
	private int _origin_x = 0;
	private int _origin_y = 0;

	private float[] _coord;
	private float[] _color;
	private float[] _alpha;
	private float[] _map;
	private byte[] _strip;
	private float[] _uv;
	private FloatBuffer _coord_buffer;
	private void allocCoordBuffer(){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _coord.length * 4 );
		byteBuffer.order( ByteOrder.nativeOrder() );
		_coord_buffer = byteBuffer.asFloatBuffer();
	}
	private void putCoordBuffer(){
		_coord_buffer.put( _coord );
		_coord_buffer.position( 0 );
	}
	private FloatBuffer _color_buffer;
	private void allocColorBuffer(){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _color.length * 4 );
		byteBuffer.order( ByteOrder.nativeOrder() );
		_color_buffer = byteBuffer.asFloatBuffer();
	}
	private void putColorBuffer(){
		_color_buffer.put( _color );
		_color_buffer.position( 0 );
	}
	private FloatBuffer _alpha_buffer;
	private void allocAlphaBuffer(){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _alpha.length * 4 );
		byteBuffer.order( ByteOrder.nativeOrder() );
		_alpha_buffer = byteBuffer.asFloatBuffer();
	}
	private void putAlphaBuffer(){
		_alpha_buffer.put( _alpha );
		_alpha_buffer.position( 0 );
	}
	private ByteBuffer _strip_buffer;
	private void allocStripBuffer(){
		_strip_buffer = ByteBuffer.allocateDirect( _strip.length * 1 );
	}
	private void putStripBuffer(){
		_strip_buffer.put( _strip );
		_strip_buffer.position( 0 );
	}
	private FloatBuffer _uv_buffer;
	private void allocUvBuffer(){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _uv.length * 4 );
		byteBuffer.order( ByteOrder.nativeOrder() );
		_uv_buffer = byteBuffer.asFloatBuffer();
	}
	private void putUvBuffer(){
		_uv_buffer.put( _uv );
		_uv_buffer.position( 0 );
	}

	private boolean _lock_tex_f = false;
	private int _lock_tex = -1;
	private float _tex_width;
	private float _tex_height;

	public _GLGraphics( GL10 gl, _GLTexture glt ){
		_gl = gl;
		_glt = glt;

		_coord = new float[12];
		_color = new float[16];
		_alpha = new float[16];
		_map = new float[8];
		_strip = new byte[4];
		_uv = new float[8];

		allocCoordBuffer();
		allocColorBuffer();
		allocAlphaBuffer();
		allocStripBuffer();
		allocUvBuffer();

		_coord[ 2] = 0.0f;
		_coord[ 5] = 0.0f;
		_coord[ 8] = 0.0f;
		_coord[11] = 0.0f;

		_color[ 3] = 1.0f;
		_color[ 7] = 1.0f;
		_color[11] = 1.0f;
		_color[15] = 1.0f;
		setColor( 0 );

		_alpha[ 0] = 1.0f; _alpha[ 1] = 1.0f; _alpha[ 2] = 1.0f;
		_alpha[ 4] = 1.0f; _alpha[ 5] = 1.0f; _alpha[ 6] = 1.0f;
		_alpha[ 8] = 1.0f; _alpha[ 9] = 1.0f; _alpha[10] = 1.0f;
		_alpha[12] = 1.0f; _alpha[13] = 1.0f; _alpha[14] = 1.0f;
		setAlpha( 255 );

		_strip[0] = 0;
		_strip[1] = 1;
		_strip[2] = 2;
		_strip[3] = 3;
		putStripBuffer();

		setROP( ROP_COPY );
	}

	public static int getColorOfRGB( int r, int g, int b ){
		return Color.rgb( r, g, b );
	}

	public void setSize( int width, int height ){
		_width  = width;
		_height = height;
	}

	public int getWidth(){
		return (int)((float)_width / _scale);
	}
	public int getHeight(){
		return (int)((float)_height / _scale);
	}

	public void setLineWidth( float width ){
		_line_width = width * _scale;
		if( _line_width > 1.0f ){
			_line_expand = (_line_width - 1.0f) / 2.0f;
		} else {
			_line_width  = 1.0f;
			_line_expand = 0.0f;
		}
	}

	public void setColor( int color ){
		_r = Color.red  ( color );
		_g = Color.green( color );
		_b = Color.blue ( color );

		_color[ 0] = _r; _color[ 1] = _g; _color[ 2] = _b;
		_color[ 4] = _r; _color[ 5] = _g; _color[ 6] = _b;
		_color[ 8] = _r; _color[ 9] = _g; _color[10] = _b;
		_color[12] = _r; _color[13] = _g; _color[14] = _b;
		putColorBuffer();
	}
	public void setAlpha( int a ){
		_a = (float)a / 255.0f;
//		_a255 = a;

		_color[ 3] = _a;
		_color[ 7] = _a;
		_color[11] = _a;
		_color[15] = _a;
		putColorBuffer();

		_alpha[ 3] = _a;
		_alpha[ 7] = _a;
		_alpha[11] = _a;
		_alpha[15] = _a;
		putAlphaBuffer();
	}

	public void setROP( int mode ){
		_rop = mode;
		switch( _rop ){
		case ROP_COPY:
			_gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
			break;
		case ROP_ADD:
			_gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
			break;
		}
	}

	public void setFlipMode( int flipmode ){
		_flipmode = flipmode;

		// _GLTexture にも通知
		_glt.setFlipMode( _flipmode );
	}

	public void setOrigin( int x, int y ){
		if( _scale != 1.0f ){
			x = scaledValue( x );
			y = scaledValue( y );
		}

		_origin_x = x;
		_origin_y = y;
	}

	private void drawLineH( int y, int x1, int x2 ){
		if( _scale != 1.0f ){
			y  = scaledValue( y  );
			x1 = scaledValue( x1 );
			x2 = scaledValue( x2 );
		}

		x1 += _origin_x;
		x2 += _origin_x;
		y = _height - (y + _origin_y);

		float y2 = (float)y - _line_expand;
		float y3 = y2 + _line_width;
		_coord[0] = x1; _coord[ 1] = y2;
		_coord[3] = x2; _coord[ 4] = y2;
		_coord[6] = x1; _coord[ 7] = y3;
		_coord[9] = x2; _coord[10] = y3;
		putCoordBuffer();

		_gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, _coord_buffer );

		_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
		_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _color_buffer );

		_gl.glDisable( GL10.GL_TEXTURE_2D );

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );

		_gl.glLoadIdentity();
		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
	}
	private void drawLineV( int x, int y1, int y2 ){
		if( _scale != 1.0f ){
			x  = scaledValue( x  );
			y1 = scaledValue( y1 );
			y2 = scaledValue( y2 );
		}

		x += _origin_x;
		y1 = _height - (y1 + _origin_y);
		y2 = _height - (y2 + _origin_y);

		float x2 = (float)x - _line_expand;
		float x3 = x2 + _line_width;
		_coord[0] = x2; _coord[ 1] = y1;
		_coord[3] = x3; _coord[ 4] = y1;
		_coord[6] = x2; _coord[ 7] = y2;
		_coord[9] = x3; _coord[10] = y2;
		putCoordBuffer();

		_gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, _coord_buffer );

		_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
		_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _color_buffer );

		_gl.glDisable( GL10.GL_TEXTURE_2D );

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );

		_gl.glLoadIdentity();
		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
	}
	public void drawLine( int x1, int y1, int x2, int y2 ){
		if( y1 == y2 ){
			drawLineH( y1, x1, x2 );
			return;
		}
		if( x1 == x2 ){
			drawLineV( x1, y1, y2 );
			return;
		}

		if( _scale != 1.0f ){
			x1 = scaledValue( x1 );
			y1 = scaledValue( y1 );
			x2 = scaledValue( x2 );
			y2 = scaledValue( y2 );
		}

		float width = (float)Math.sqrt( (double)((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)) );

		float x3 = -0.5f;
		float x4 = x3 + width;
		float y3 = -_line_width / 2.0f;
		float y4 = y3 + _line_width;
		_coord[0] = x3; _coord[ 1] = y3;
		_coord[3] = x4; _coord[ 4] = y3;
		_coord[6] = x3; _coord[ 7] = y4;
		_coord[9] = x4; _coord[10] = y4;
		putCoordBuffer();

		_gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, _coord_buffer );

		_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
		_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _color_buffer );

		_gl.glDisable( GL10.GL_TEXTURE_2D );

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );

		float r = ((float)Math.atan2( (double)(y2 - y1), (double)(x2 - x1) ) * 180.0f) / 3.14159265358979323846264f;

		x1 += _origin_x;
		y1 = _height - (y1 + _origin_y);

		_gl.glLoadIdentity();
		_gl.glTranslatef( (float)x1 + 0.5f, (float)y1 + 0.5f, 0.0f );
		_gl.glRotatef( -r, 0.0f, 0.0f, 1.0f );
		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
	}

	public void drawRect( int x, int y, int width, int height ){
		if( _scale != 1.0f ){
			x      = scaledValue( x      );
			y      = scaledValue( y      );
			width  = scaledValue( width  );
			height = scaledValue( height );
		}

		x += _origin_x;
		y = _height - (y + _origin_y) - height - 1;

		_gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, _coord_buffer );

		_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
		_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _color_buffer );

		_gl.glDisable( GL10.GL_TEXTURE_2D );

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );

		_gl.glLoadIdentity();

		float x2 = (float)x - _line_expand;
		float y2 = (float)y - _line_expand;
		float x3 = x2 + _line_width;
		float y3 = y2 + _line_width;
		float x4 = x2 + (float)width;
		float y4 = y2 + (float)height;
		float x5 = x4 + _line_width;
		float y5 = y4 + _line_width;

		_coord[0] = x2; _coord[ 1] = y2;
		_coord[3] = x5; _coord[ 4] = y2;
		_coord[6] = x2; _coord[ 7] = y3;
		_coord[9] = x5; _coord[10] = y3;
		putCoordBuffer();
		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_coord[0] = x2; _coord[ 1] = y3;
		_coord[3] = x3; _coord[ 4] = y3;
		_coord[6] = x2; _coord[ 7] = y5;
		_coord[9] = x3; _coord[10] = y5;
		putCoordBuffer();
		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_coord[0] = x4; _coord[ 1] = y3;
		_coord[3] = x5; _coord[ 4] = y3;
		_coord[6] = x4; _coord[ 7] = y5;
		_coord[9] = x5; _coord[10] = y5;
		putCoordBuffer();
		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_coord[0] = x3; _coord[ 1] = y4;
		_coord[3] = x4; _coord[ 4] = y4;
		_coord[6] = x3; _coord[ 7] = y5;
		_coord[9] = x4; _coord[10] = y5;
		putCoordBuffer();
		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
	}

	public void fillRect( int x, int y, int width, int height ){
		if( _scale != 1.0f ){
			x      = scaledValue( x      );
			y      = scaledValue( y      );
			width  = scaledValue( width  );
			height = scaledValue( height );
		}

		x += _origin_x;
		y = _height - (y + _origin_y) - height;

		_coord[0] = x        ; _coord[ 1] = y         ;
		_coord[3] = x + width; _coord[ 4] = y         ;
		_coord[6] = x        ; _coord[ 7] = y + height;
		_coord[9] = x + width; _coord[10] = y + height;
		putCoordBuffer();

		_gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, _coord_buffer );

		_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
		_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _color_buffer );

		_gl.glDisable( GL10.GL_TEXTURE_2D );

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );

		_gl.glLoadIdentity();
		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
	}

	public void lockTexture( int tex_index ){
		_lock_tex_f = true;
		_lock_tex = tex_index;

		if( _lock_tex >= 0 ){
			_glt.use( _lock_tex );
//			_glt.setTransparency( _lock_tex, _a255 );

			_tex_width  = (float)_glt.width ( _lock_tex );
			_tex_height = (float)_glt.height( _lock_tex );

			_gl.glEnable( GL10.GL_TEXTURE_2D );
			_gl.glBindTexture( GL10.GL_TEXTURE_2D, _glt.id( _lock_tex ) );
			_gl.glTexEnvf( GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE/*GL_REPLACE*/ );
		} else {
			_gl.glDisable( GL10.GL_TEXTURE_2D );
		}

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );
	}

	public void unlockTexture(){
		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		if( _lock_tex >= 0 ){
			_gl.glDisable( GL10.GL_TEXTURE_2D );
		}

		_lock_tex_f = false;
		_lock_tex = -1;
	}

	private void draw(){
		if( _lock_tex >= 0 ){
			for( int i = 0; i < 4; i++ ){
				_uv[i * 2    ] = _map[i * 2    ] / _tex_width;
				_uv[i * 2 + 1] = _map[i * 2 + 1] / _tex_height;
			}
			putUvBuffer();
		}

		_gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, _coord_buffer );

		if( _lock_tex >= 0 ){
			_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
			_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _alpha_buffer );

			_gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
			_gl.glTexCoordPointer( 2, GL10.GL_FLOAT, 0, _uv_buffer );
		} else {
			_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
			_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _color_buffer );
		}

		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
		if( _lock_tex >= 0 ){
			_gl.glDisableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		}
	}
	private void draw( int tex_index ){
		if( tex_index >= 0 ){
			_glt.use( tex_index );
//			_glt.setTransparency( tex_index, _a255 );

			float width  = (float)_glt.width ( tex_index );
			float height = (float)_glt.height( tex_index );
			for( int i = 0; i < 4; i++ ){
				_uv[i * 2    ] = _map[i * 2    ] / width;
				_uv[i * 2 + 1] = _map[i * 2 + 1] / height;
			}
			putUvBuffer();
		}

		_gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, _coord_buffer );

		if( tex_index >= 0 ){
			_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
			_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _alpha_buffer );

			_gl.glEnable( GL10.GL_TEXTURE_2D );
			_gl.glBindTexture( GL10.GL_TEXTURE_2D, _glt.id( tex_index ) );
			_gl.glTexEnvf( GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE/*GL_REPLACE*/ );

			_gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
			_gl.glTexCoordPointer( 2, GL10.GL_FLOAT, 0, _uv_buffer );
		} else {
			_gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
			_gl.glColorPointer( 4, GL10.GL_FLOAT, 0, _color_buffer );

			_gl.glDisable( GL10.GL_TEXTURE_2D );
		}

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );

		_gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );

		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		_gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
		if( tex_index >= 0 ){
			_gl.glDisable( GL10.GL_TEXTURE_2D );
			_gl.glDisableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		}
	}

	public void drawScaledTexture( int tex_index, int dx, int dy, int width, int height, int sx, int sy, int swidth, int sheight ){
		if( _lock_tex_f ){
			tex_index = _lock_tex;
		}

		if( _scale != 1.0f ){
			dx     = scaledValue( dx     );
			dy     = scaledValue( dy     );
			width  = scaledValue( width  );
			height = scaledValue( height );
		}

		dx += _origin_x;
		dy = _height - (dy + _origin_y) - height;

		float sx2 = (float)sx + 0.5f;
		float sy2 = (float)sy + 0.5f;
		float sx3 = sx2 + (float)swidth  - 1.0f;
		float sy3 = sy2 + (float)sheight - 1.0f;

		_coord[0] = 0.0f ; _coord[ 1] = 0.0f  ;
		_coord[3] = width; _coord[ 4] = 0.0f  ;
		_coord[6] = 0.0f ; _coord[ 7] = height;
		_coord[9] = width; _coord[10] = height;
		putCoordBuffer();

		if( tex_index >= 0 ){
			switch( _flipmode ){
			case FLIP_NONE:
				_map[0] = sx2; _map[1] = sy3;
				_map[2] = sx3; _map[3] = sy3;
				_map[4] = sx2; _map[5] = sy2;
				_map[6] = sx3; _map[7] = sy2;
				break;
			case FLIP_HORIZONTAL:
				_map[0] = sx3; _map[1] = sy3;
				_map[2] = sx2; _map[3] = sy3;
				_map[4] = sx3; _map[5] = sy2;
				_map[6] = sx2; _map[7] = sy2;
				break;
			case FLIP_VERTICAL:
				_map[0] = sx2; _map[1] = sy2;
				_map[2] = sx3; _map[3] = sy2;
				_map[4] = sx2; _map[5] = sy3;
				_map[6] = sx3; _map[7] = sy3;
				break;
			case FLIP_ROTATE:
				_map[0] = sx3; _map[1] = sy2;
				_map[2] = sx2; _map[3] = sy2;
				_map[4] = sx3; _map[5] = sy3;
				_map[6] = sx2; _map[7] = sy3;
				break;
			}
		}

		_gl.glLoadIdentity();
		_gl.glTranslatef( dx, dy, 0.0f );
		if( _lock_tex_f ){
			draw();
		} else {
			draw( tex_index );
		}
	}

	public void drawTexture( int tex_index, int x, int y ){
		if( _lock_tex_f ){
			int width  = (int)_tex_width;
			int height = (int)_tex_height;
			drawScaledTexture( _lock_tex, x, y, width, height, 0, 0, width, height );
		} else {
			int width  = _glt.width ( tex_index );
			int height = _glt.height( tex_index );
			drawScaledTexture( tex_index, x, y, width, height, 0, 0, width, height );
		}
	}

	public void drawTexture( int tex_index, int dx, int dy, int sx, int sy, int width, int height ){
		drawScaledTexture( tex_index, dx, dy, width, height, sx, sy, width, height );
	}

	public void drawTransTexture( int tex_index, float dx, float dy, int sx, int sy, int width, int height, float cx, float cy, float r360, float z128x, float z128y ){
		if( _lock_tex_f ){
			tex_index = _lock_tex;
		}

		if( _scale != 1.0f ){
			dx    *= _scale;
			dy    *= _scale;
			z128x *= _scale;
			z128y *= _scale;
		}

		dx += (float)_origin_x;
		dy = (float)_height - (dy + (float)_origin_y);

		float sx2 = (float)sx + 0.5f;
		float sy2 = (float)sy + 0.5f;
		float sx3 = sx2 + (float)width  - 1.0f;
		float sy3 = sy2 + (float)height - 1.0f;

		_coord[0] = -cx               ; _coord[ 1] = cy - (float)height;
		_coord[3] = -cx + (float)width; _coord[ 4] = cy - (float)height;
		_coord[6] = -cx               ; _coord[ 7] = cy                ;
		_coord[9] = -cx + (float)width; _coord[10] = cy                ;
		putCoordBuffer();

		if( tex_index >= 0 ){
			switch( _flipmode ){
			case FLIP_NONE:
				_map[0] = sx2; _map[1] = sy3;
				_map[2] = sx3; _map[3] = sy3;
				_map[4] = sx2; _map[5] = sy2;
				_map[6] = sx3; _map[7] = sy2;
				break;
			case FLIP_HORIZONTAL:
				_map[0] = sx3; _map[1] = sy3;
				_map[2] = sx2; _map[3] = sy3;
				_map[4] = sx3; _map[5] = sy2;
				_map[6] = sx2; _map[7] = sy2;
				break;
			case FLIP_VERTICAL:
				_map[0] = sx2; _map[1] = sy2;
				_map[2] = sx3; _map[3] = sy2;
				_map[4] = sx2; _map[5] = sy3;
				_map[6] = sx3; _map[7] = sy3;
				break;
			case FLIP_ROTATE:
				_map[0] = sx3; _map[1] = sy2;
				_map[2] = sx2; _map[3] = sy2;
				_map[4] = sx3; _map[5] = sy3;
				_map[6] = sx2; _map[7] = sy3;
				break;
			}
		}

		_gl.glLoadIdentity();
		_gl.glTranslatef( dx, dy, 0.0f );
		_gl.glRotatef( -r360, 0.0f, 0.0f, 1.0f );
		_gl.glScalef( z128x / 128.0f, z128y / 128.0f, 1.0f );
		if( _lock_tex_f ){
			draw();
		} else {
			draw( tex_index );
		}
	}
}
