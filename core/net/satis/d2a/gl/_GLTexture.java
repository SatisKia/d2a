/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.gl;

import android.content.res.*;
import android.graphics.*;
import android.opengl.GLUtils;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;
import net.satis.d2a.*;

public class _GLTexture extends _Scalable {
	private GL10 _gl;
	private Resources _res;

	private int _num;
	private int[] _id;
	private boolean[] _use_id;
	private int[] _index2id;
	private int[] _width;
	private int[] _height;

	private Bitmap[] _bitmap;
	private int[][] _t_rgba;
	private int[][] _t_a;
	private int[] _t_trans;
	private boolean[] _t_alpha;

	private int _gen_num;
	private boolean _gen;

	private int _canvas_height;

	// イメージ描画時の反転方法
	public static final int FLIP_NONE       = 0;
	public static final int FLIP_HORIZONTAL = 1;
	public static final int FLIP_VERTICAL   = 2;
	public static final int FLIP_ROTATE     = 3;
	protected int _flipmode = FLIP_NONE;

	private int[] _draw_rect;

	private int _lock_tex = -1;

	public _GLTexture( GL10 gl, Resources res, int index_num, int gen_num ){
		int i;

		_gl = gl;
		_res = res;

		_num = index_num;
		_gen_num = gen_num;

		_id = new int[_gen_num];
		_gl.glGenTextures( _gen_num, _id, 0 );
		_gen = true;

		_use_id = new boolean[_gen_num];
		for( i = 0; i < _gen_num; i++ ){
			_use_id[i] = false;
		}

		_index2id = new int[_num];

		_width = new int[_num];
		_height = new int[_num];

		_bitmap = new Bitmap[_num];
		_t_rgba = new int[_num][];
		_t_a = new int[_num][];
		_t_trans = new int[_num];
		_t_alpha = new boolean[_num];

		_draw_rect = new int[4];

		for( i = 0; i < _num; i++ ){
			_index2id[i] = -1;

			_bitmap[i] = null;
			_t_rgba[i] = null;
			_t_a[i] = null;
		}
	}

	public void dispose(){
		for( int i = 0; i < _num; i++ ){
			unuse( i );
		}

		if( _gen ){
			_gl.glDeleteTextures( _gen_num, _id, 0 );
			_gen = false;
		}
	}

	public static int getTextureSize( int size ){
		int tmp = 1;
		while( true ){
			if( tmp >= size ){
				break;
			}
			tmp *= 2;
		}
		return tmp;
	}

	public void use( int index, boolean use_trans ){
		if( _index2id[index] >= 0 ){
			return;
		}

		int i;

		_index2id[index] = 0;
		for( i = 0; i < _gen_num; i++ ){
			if( !_use_id[i] ){
				_use_id[i] = true;
				_index2id[index] = i;
				break;
			}
		}

		// 画像ファイル読み込み
		Bitmap tmp = createBitmap( _res, index );
		if( tmp.isMutable() ){
			_bitmap[index] = tmp;
		} else {
			_bitmap[index] = tmp.copy( Bitmap.Config.ARGB_8888, true );
			tmp.recycle();
		}
		_width [index] = _bitmap[index].getWidth();
		_height[index] = _bitmap[index].getHeight();
		if( use_trans ){
			int len = _width[index] * _height[index];
			_t_rgba[index] = new int[len];
			_bitmap[index].getPixels( _t_rgba[index], 0, _width[index], 0, 0, _width[index], _height[index] );
			_t_a[index] = new int[len];
			for( i = 0; i < len; i++ ){
				_t_a[index][i] = (_t_rgba[index][i] >> 24) & 0xff;	// アルファ値を保持
			}
		}

		_t_trans[index] = 255;
		_t_alpha[index] = alphaFlag( index );

		// テクスチャを構築する
		_gl.glPixelStorei( GL10.GL_UNPACK_ALIGNMENT, 1 );
		_gl.glEnable( GL10.GL_TEXTURE_2D );
		_gl.glBindTexture( GL10.GL_TEXTURE_2D, _id[_index2id[index]] );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, _bitmap[index], 0 );

		_gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, (float)filter( index ) );
		_gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, (float)filter( index ) );
		_gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, (float)wrap( index ) );
		_gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, (float)wrap( index ) );
	}
	public void use( int index ){
		use( index, false );
	}

	public void unuse( int index ){
		if( _index2id[index] >= 0 ){
			_use_id[_index2id[index]] = false;

			_bitmap[index].recycle();
			_bitmap[index] = null;
			_t_rgba[index] = null;
			_t_a[index] = null;

			_index2id[index] = -1;
		}
	}

	public void unuse(){
		for( int i = 0; i < _num; i++ ){
			unuse( i );
		}

		_gl.glDeleteTextures( _gen_num, _id, 0 );
		_gl.glGenTextures( _gen_num, _id, 0 );
	}

	public void update( int index, int[] pixels, int length ){
		if( _index2id[index] >= 0 ){
			int len = _width[index] * _height[index];
			if( length == len ){
				_bitmap[index].setPixels( pixels, 0, _width[index], 0, 0, _width[index], _height[index] );

				if( (_t_rgba[index] != null) && (_t_a[index] != null) ){
					System.arraycopy( pixels, 0, _t_rgba[index], 0, len );
					for( int i = 0; i < len; i++ ){
						_t_a[index][i] = (_t_rgba[index][i] >> 24) & 0xff;	// アルファ値を保持
					}
				}

				_t_trans[index] = 255;
				_t_alpha[index] = alphaFlag( index );

				// テクスチャを再構築する
				_gl.glPixelStorei( GL10.GL_UNPACK_ALIGNMENT, 1 );
				_gl.glEnable( GL10.GL_TEXTURE_2D );
				_gl.glBindTexture( GL10.GL_TEXTURE_2D, _id[_index2id[index]] );
				GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, _bitmap[index], 0 );
			}
		}
	}
	public void update( int index, Bitmap bitmap ){
		if( _index2id[index] >= 0 ){
			if( (bitmap.getWidth() == _width[index]) && (bitmap.getHeight() == _height[index]) ){
				int len = _width[index] * _height[index];
				if( (_t_rgba[index] == null) || (_t_a[index] == null) ){
					int[] pixels = new int[len];
					bitmap.getPixels( pixels, 0, _width[index], 0, 0, _width[index], _height[index] );
					_bitmap[index].setPixels( pixels, 0, _width[index], 0, 0, _width[index], _height[index] );
				} else {
					bitmap.getPixels( _t_rgba[index], 0, _width[index], 0, 0, _width[index], _height[index] );
					_bitmap[index].setPixels( _t_rgba[index], 0, _width[index], 0, 0, _width[index], _height[index] );
					for( int i = 0; i < len; i++ ){
						_t_a[index][i] = (_t_rgba[index][i] >> 24) & 0xff;	// アルファ値を保持
					}
				}

				_t_trans[index] = 255;
				_t_alpha[index] = alphaFlag( index );

				// テクスチャを再構築する
				_gl.glPixelStorei( GL10.GL_UNPACK_ALIGNMENT, 1 );
				_gl.glEnable( GL10.GL_TEXTURE_2D );
				_gl.glBindTexture( GL10.GL_TEXTURE_2D, _id[_index2id[index]] );
				GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, _bitmap[index], 0 );
			}
		}
	}

	public void setTransparency( int index, int trans ){
		if( (_t_rgba[index] == null) || (_t_a[index] == null) ){
			return;
		}

		use( index );

		if( trans == _t_trans[index] ){
			return;
		}
		_t_trans[index] = trans;

		// アルファ値を操作する
		int len = _width[index] * _height[index];
		int a, r, g, b;
		for( int i = 0; i < len; i++ ){
			a = _t_a[index][i] * _t_trans[index] / 255;
			r = (_t_rgba[index][i] >> 16) & 0xff;
			g = (_t_rgba[index][i] >>  8) & 0xff;
			b =  _t_rgba[index][i]        & 0xff;
			_t_rgba[index][i] = (a << 24) | (r << 16) | (g << 8) | b;
		}
		_bitmap[index].setPixels( _t_rgba[index], 0, _width[index], 0, 0, _width[index], _height[index] );

		_t_alpha[index] = (_t_trans[index] == 255) ? alphaFlag( index ) : true;

		// テクスチャを再構築する
		_gl.glPixelStorei( GL10.GL_UNPACK_ALIGNMENT, 1 );
		_gl.glEnable( GL10.GL_TEXTURE_2D );
		_gl.glBindTexture( GL10.GL_TEXTURE_2D, _id[_index2id[index]] );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, _bitmap[index], 0 );
	}

	public int id( int index ){
		return _id[_index2id[index]];
	}

	public int width( int index ){
		return _width[index];
	}

	public int height( int index ){
		return _height[index];
	}

	public boolean alpha( int index ){
		return _t_alpha[index];
	}

	public boolean depth( int index ){
		return depthFlag( index );
	}

	public void setCanvasHeight( int height ){
		_canvas_height = height;
	}

	public void setFlipMode( int flipmode ){
		_flipmode = flipmode;
	}

	public void lock( int index ){
		_lock_tex = index;

		use( _lock_tex );

		_gl.glEnable( GL10.GL_TEXTURE_2D );
		_gl.glBindTexture( GL10.GL_TEXTURE_2D, _id[_index2id[_lock_tex]] );

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );
	}

	public void unlock(){
		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisable( GL10.GL_TEXTURE_2D );

		_lock_tex = -1;
	}

	private void draw( int dx, int dy ){
		dy = _canvas_height - _height[_lock_tex] - dy;

		switch( _flipmode ){
		case FLIP_NONE:
			_draw_rect[0] = 0;
			_draw_rect[1] = _height[_lock_tex];
			_draw_rect[2] = _width[_lock_tex];
			_draw_rect[3] = -_height[_lock_tex];
			break;
		case FLIP_HORIZONTAL:
			_draw_rect[0] = _width[_lock_tex];
			_draw_rect[1] = _height[_lock_tex];
			_draw_rect[2] = -_width[_lock_tex];
			_draw_rect[3] = -_height[_lock_tex];
			break;
		case FLIP_VERTICAL:
			_draw_rect[0] = 0;
			_draw_rect[1] = 0;
			_draw_rect[2] = _width[_lock_tex];
			_draw_rect[3] = _height[_lock_tex];
			break;
		case FLIP_ROTATE:
			_draw_rect[0] = _width[_lock_tex];
			_draw_rect[1] = 0;
			_draw_rect[2] = -_width[_lock_tex];
			_draw_rect[3] = _height[_lock_tex];
			break;
		}
		((GL11)_gl).glTexParameteriv( GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, _draw_rect, 0 );

		((GL11Ext)_gl).glDrawTexfOES( dx, dy, 0.0f, _width[_lock_tex], _height[_lock_tex] );
	}
	public void draw( int index, int dx, int dy ){
		if( _lock_tex >= 0 ){
			if( _scale != 1.0f ){
				draw( dx, dy, _width[_lock_tex], _height[_lock_tex], 0, 0, _width[_lock_tex], _height[_lock_tex] );
			} else {
				draw( dx, dy );
			}
			return;
		}

		if( _scale != 1.0f ){
			draw( index, dx, dy, _width[index], _height[index], 0, 0, _width[index], _height[index] );
			return;
		}

		use( index );

		dy = _canvas_height - _height[index] - dy;

		_gl.glEnable( GL10.GL_TEXTURE_2D );
		_gl.glBindTexture( GL10.GL_TEXTURE_2D, _id[_index2id[index]] );

		switch( _flipmode ){
		case FLIP_NONE:
			_draw_rect[0] = 0;
			_draw_rect[1] = _height[index];
			_draw_rect[2] = _width[index];
			_draw_rect[3] = -_height[index];
			break;
		case FLIP_HORIZONTAL:
			_draw_rect[0] = _width[index];
			_draw_rect[1] = _height[index];
			_draw_rect[2] = -_width[index];
			_draw_rect[3] = -_height[index];
			break;
		case FLIP_VERTICAL:
			_draw_rect[0] = 0;
			_draw_rect[1] = 0;
			_draw_rect[2] = _width[index];
			_draw_rect[3] = _height[index];
			break;
		case FLIP_ROTATE:
			_draw_rect[0] = _width[index];
			_draw_rect[1] = 0;
			_draw_rect[2] = -_width[index];
			_draw_rect[3] = _height[index];
			break;
		}
		((GL11)_gl).glTexParameteriv( GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, _draw_rect, 0 );

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );

		((GL11Ext)_gl).glDrawTexfOES( dx, dy, 0.0f, _width[index], _height[index] );

		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisable( GL10.GL_TEXTURE_2D );
	}

	private void draw( int dx, int dy, int dwidth, int dheight, int sx, int sy, int swidth, int sheight ){
		if( _scale != 1.0f ){
			dx      = scaledValue( dx      );
			dy      = scaledValue( dy      );
			dwidth  = scaledValue( dwidth  );
			dheight = scaledValue( dheight );
		}

		dy = _canvas_height - dheight - dy;
		sy = _height[_lock_tex] - sy - sheight;

		switch( _flipmode ){
		case FLIP_NONE:
			_draw_rect[0] = sx;
			_draw_rect[1] = _height[_lock_tex] - sy;
			_draw_rect[2] = swidth;
			_draw_rect[3] = -sheight;
			break;
		case FLIP_HORIZONTAL:
			_draw_rect[0] = sx + swidth;
			_draw_rect[1] = _height[_lock_tex] - sy;
			_draw_rect[2] = -swidth;
			_draw_rect[3] = -sheight;
			break;
		case FLIP_VERTICAL:
			_draw_rect[0] = sx;
			_draw_rect[1] = _height[_lock_tex] - sy - sheight;
			_draw_rect[2] = swidth;
			_draw_rect[3] = sheight;
			break;
		case FLIP_ROTATE:
			_draw_rect[0] = sx + swidth;
			_draw_rect[1] = _height[_lock_tex] - sy - sheight;
			_draw_rect[2] = -swidth;
			_draw_rect[3] = sheight;
			break;
		}
		((GL11)_gl).glTexParameteriv( GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, _draw_rect, 0 );

		((GL11Ext)_gl).glDrawTexfOES( dx, dy, 0.0f, dwidth, dheight );
	}
	public void draw( int index, int dx, int dy, int dwidth, int dheight, int sx, int sy, int swidth, int sheight ){
		if( _lock_tex >= 0 ){
			draw( dx, dy, dwidth, dheight, sx, sy, swidth, sheight );
			return;
		}

		if( _scale != 1.0f ){
			dx      = scaledValue( dx      );
			dy      = scaledValue( dy      );
			dwidth  = scaledValue( dwidth  );
			dheight = scaledValue( dheight );
		}

		use( index );

		dy = _canvas_height - dheight - dy;
		sy = _height[index] - sy - sheight;

		_gl.glEnable( GL10.GL_TEXTURE_2D );
		_gl.glBindTexture( GL10.GL_TEXTURE_2D, _id[_index2id[index]] );

		switch( _flipmode ){
		case FLIP_NONE:
			_draw_rect[0] = sx;
			_draw_rect[1] = _height[index] - sy;
			_draw_rect[2] = swidth;
			_draw_rect[3] = -sheight;
			break;
		case FLIP_HORIZONTAL:
			_draw_rect[0] = sx + swidth;
			_draw_rect[1] = _height[index] - sy;
			_draw_rect[2] = -swidth;
			_draw_rect[3] = -sheight;
			break;
		case FLIP_VERTICAL:
			_draw_rect[0] = sx;
			_draw_rect[1] = _height[index] - sy - sheight;
			_draw_rect[2] = swidth;
			_draw_rect[3] = sheight;
			break;
		case FLIP_ROTATE:
			_draw_rect[0] = sx + swidth;
			_draw_rect[1] = _height[index] - sy - sheight;
			_draw_rect[2] = -swidth;
			_draw_rect[3] = sheight;
			break;
		}
		((GL11)_gl).glTexParameteriv( GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, _draw_rect, 0 );

		_gl.glEnable( GL10.GL_BLEND );
		_gl.glDepthMask( false );

		((GL11Ext)_gl).glDrawTexfOES( dx, dy, 0.0f, dwidth, dheight );

		_gl.glDisable( GL10.GL_BLEND );
		_gl.glDepthMask( true );

		_gl.glDisable( GL10.GL_TEXTURE_2D );
	}
	public void draw( int index, int dx, int dy, int sx, int sy, int swidth, int sheight ){
		if( _lock_tex >= 0 ){
			draw( dx, dy, swidth, sheight, sx, sy, swidth, sheight );
		} else {
			draw( index, dx, dy, swidth, sheight, sx, sy, swidth, sheight );
		}
	}

	public int resourceId( int index ){ return -1; }
	public Bitmap createBitmap( Resources res, int index ){
		return BitmapFactory.decodeResource( res, resourceId( index ) );
	}
	public boolean alphaFlag( int index ){ return true; }
	public boolean depthFlag( int index ){ return true; }
	public int filter( int index ){ return GL10.GL_LINEAR; }
	public int wrap( int index ){ return GL10.GL_CLAMP_TO_EDGE; }
}
