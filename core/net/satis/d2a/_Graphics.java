/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.graphics.*;

public class _Graphics {
	private _Canvas _c = null;
	private Bitmap _bitmap = null;
	private Canvas _canvas;

	private Paint _paint;
	private Paint _paint_img;
	private Rect _rect_src;
	private Rect _rect_dst;
	private RectF _rectf_src;
	private RectF _rectf_dst;
	private Matrix _mat;

	// 透明度
	private int _alpha = 255;

	// ラスターオペレーション
	public static final int ROP_COPY = 0;
	public static final int ROP_ADD  = 1;
	private int _rop = ROP_COPY;

	// イメージ描画時の反転方法
	public static final int FLIP_NONE         = 0;
	public static final int FLIP_HORIZONTAL   = 1;
	public static final int FLIP_VERTICAL     = 2;
	public static final int FLIP_ROTATE       = 3;
	public static final int FLIP_ROTATE_LEFT  = 4;
	public static final int FLIP_ROTATE_RIGHT = 5;
	private int _flipmode = FLIP_NONE;

	// 描画の際の座標原点
	private int _origin_x = 0;
	private int _origin_y = 0;

	private int _expand = 0;

	public _Graphics( _Canvas c ){
		_c = c;

		_paint     = new Paint();
		_paint_img = new Paint();
		_rect_src  = new Rect();
		_rect_dst  = new Rect();
		_rectf_src = new RectF();
		_rectf_dst = new RectF();
		_mat       = new Matrix();
	}
	public _Graphics( Bitmap bitmap ){
		_bitmap = bitmap;
		_canvas = new Canvas( _bitmap );

		_paint     = new Paint();
		_paint_img = new Paint();
		_rect_src  = new Rect();
		_rect_dst  = new Rect();
		_rectf_src = new RectF();
		_rectf_dst = new RectF();
		_mat       = new Matrix();
	}

	public int getWidth(){
		if( _c != null ){
			return _c.getWidth();
		}
		if( _bitmap != null ){
			return _bitmap.getWidth();
		}
		return 0;
	}
	public int getHeight(){
		if( _c != null ){
			return _c.getHeight();
		}
		if( _bitmap != null ){
			return _bitmap.getHeight();
		}
		return 0;
	}

	public static int getColorOfRGB( int r, int g, int b ){
		return Color.rgb( r, g, b );
	}

	public void setAntiAlias( boolean aa ){
		_paint.setAntiAlias( aa );
	}

	public void setStrokeWidth( float width ){
		_paint.setStrokeWidth( width );
		_expand = (int)width / 2;
	}

	public void setColor( int color ){
		_paint.setColor( color );
		_paint.setAlpha( _alpha );
	}
	public void setAlpha( int a ){
		_alpha = a;
		_paint.setAlpha( a );
	}

	public void setROP( int mode ){
		_rop = mode;
	}

	public void setFlipMode( int flipmode ){
		_flipmode = flipmode;
	}

	public void setOrigin( int x, int y ){
		_origin_x = x;
		_origin_y = y;
	}

	public void setFontSize( int size ){
		_paint.setTextSize( size );
	}

	public int stringWidth( String str ){
		return (int)_paint.measureText( str );
	}
	public int fontHeight(){
		return (int)(-_paint.ascent() + _paint.descent());
	}

	public void lock(){
		if( _c != null ){
			_canvas = _c.getHolder().lockCanvas();
		}
	}
	public void unlock(){
		if( _c != null ){
			_c.getHolder().unlockCanvasAndPost( _canvas );
		}
	}

	public Canvas getCanvas(){
		return _canvas;
	}

	public Paint getPaint(){
		return _paint;
	}

	public void setClip( int x, int y, int width, int height ){
		_canvas.clipRect( x, y, x + width, y + height, Region.Op.REPLACE );
	}
	public void clearClip(){
		setClip( 0, 0, _canvas.getWidth(), _canvas.getHeight() );
	}

	private void addPixels( Bitmap bitmap, int x, int y, int alpha ){
		int sx = 0;
		int sy = 0;
		int dx = x;
		int dy = y;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		if( dx < 0 ){
			sx = -dx;
			width += dx;
			dx = 0;
		}
		if( dy < 0 ){
			sy = -dy;
			height += dy;
			dy = 0;
		}
		if( dx + width > _bitmap.getWidth() ){
			width = _bitmap.getWidth() - dx;
		}
		if( dy + height > _bitmap.getHeight() ){
			height = _bitmap.getHeight() - dy;
		}
		int len = width * height;
		if( len <= 0 ){
			return;
		}
		int[] src = new int[len];
		int[] dst = new int[len];
		bitmap.getPixels( src, 0, width, sx, sy, width, height );
		_bitmap.getPixels( dst, 0, width, dx, dy, width, height );
		int a, r, g, b;
		for( int i = 0; i < len; i++ ){
			a = ((src[i] >> 24) & 0xff) * alpha / 255;
			r = ((dst[i] >> 16) & 0xff) + ((src[i] >> 16) & 0xff) * a / 255; if( r > 255 ) r = 255;
			g = ((dst[i] >>  8) & 0xff) + ((src[i] >>  8) & 0xff) * a / 255; if( g > 255 ) g = 255;
			b = ( dst[i]        & 0xff) + ( src[i]        & 0xff) * a / 255; if( b > 255 ) b = 255;
			dst[i] = (dst[i] & 0xff000000) | (r << 16) | (g << 8) | b;
		}
		_bitmap.setPixels( dst, 0, width, dx, dy, width, height );
	}

	public void drawLine( int x1, int y1, int x2, int y2 ){
		x1 += _origin_x;
		y1 += _origin_y;
		x2 += _origin_x;
		y2 += _origin_y;
		_paint.setStyle( Paint.Style.STROKE );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_canvas.drawLine( x1, y1, x2, y2, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( x2 - x1 + 1 + _expand * 2, y2 - y1 + 1 + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			canvas.drawLine( _expand, _expand, x2 - x1 + _expand, y2 - y1 + _expand, _paint );
			addPixels( tmp, x1 - _expand, y1 - _expand, 255 );
			tmp.recycle();
		}
	}

	public void drawRect( int x, int y, int width, int height ){
		x += _origin_x;
		y += _origin_y;
		_paint.setStyle( Paint.Style.STROKE );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_rect_src.left   = x;
			_rect_src.top    = y;
			_rect_src.right  = x + width;
			_rect_src.bottom = y + height;
			_canvas.drawRect( _rect_src, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( width + 1 + _expand * 2, height + 1 + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			_rect_src.left   = _expand;
			_rect_src.top    = _expand;
			_rect_src.right  = _expand + width;
			_rect_src.bottom = _expand + height;
			canvas.drawRect( _rect_src, _paint );
			addPixels( tmp, x - _expand, y - _expand, 255 );
			tmp.recycle();
		}
	}

	public void fillRect( int x, int y, int width, int height ){
		x += _origin_x;
		y += _origin_y;
		_paint.setStyle( Paint.Style.FILL );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_rect_src.left   = x;
			_rect_src.top    = y;
			_rect_src.right  = x + width;
			_rect_src.bottom = y + height;
			_canvas.drawRect( _rect_src, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( width + _expand * 2, height + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			_rect_src.left   = _expand;
			_rect_src.top    = _expand;
			_rect_src.right  = _expand + width;
			_rect_src.bottom = _expand + height;
			canvas.drawRect( _rect_src, _paint );
			addPixels( tmp, x - _expand, y - _expand, 255 );
			tmp.recycle();
		}
	}

	public void drawRoundRect( int x, int y, int width, int height, int rx, int ry ){
		x += _origin_x;
		y += _origin_y;
		_paint.setStyle( Paint.Style.STROKE );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_rectf_src.left   = (float)x;
			_rectf_src.top    = (float)y;
			_rectf_src.right  = (float)(x + width );
			_rectf_src.bottom = (float)(y + height);
			_canvas.drawRoundRect( _rectf_src, rx, ry, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( width + 1 + _expand * 2, height + 1 + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			_rectf_src.left   = (float)_expand;
			_rectf_src.top    = (float)_expand;
			_rectf_src.right  = (float)(_expand + width );
			_rectf_src.bottom = (float)(_expand + height);
			canvas.drawRoundRect( _rectf_src, rx, ry, _paint );
			addPixels( tmp, x - _expand, y - _expand, 255 );
			tmp.recycle();
		}
	}

	public void fillRoundRect( int x, int y, int width, int height, int rx, int ry ){
		x += _origin_x;
		y += _origin_y;
		_paint.setStyle( Paint.Style.FILL );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_rectf_src.left   = (float)x;
			_rectf_src.top    = (float)y;
			_rectf_src.right  = (float)(x + width );
			_rectf_src.bottom = (float)(y + height);
			_canvas.drawRoundRect( _rectf_src, rx, ry, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( width + _expand * 2, height + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			_rectf_src.left   = (float)_expand;
			_rectf_src.top    = (float)_expand;
			_rectf_src.right  = (float)(_expand + width );
			_rectf_src.bottom = (float)(_expand + height);
			canvas.drawRoundRect( _rectf_src, rx, ry, _paint );
			addPixels( tmp, x - _expand, y - _expand, 255 );
			tmp.recycle();
		}
	}

	public void drawOval( int x, int y, int width, int height ){
		x += _origin_x;
		y += _origin_y;
		_paint.setStyle( Paint.Style.STROKE );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_rectf_src.left   = (float)x;
			_rectf_src.top    = (float)y;
			_rectf_src.right  = (float)(x + width );
			_rectf_src.bottom = (float)(y + height);
			_canvas.drawOval( _rectf_src, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( width + _expand * 2, height + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas(tmp);
			_rectf_src.left   = (float)_expand;
			_rectf_src.top    = (float)_expand;
			_rectf_src.right  = (float)(_expand + width );
			_rectf_src.bottom = (float)(_expand + height);
			canvas.drawOval( _rectf_src, _paint );
			addPixels( tmp, x - _expand, y - _expand, 255 );
			tmp.recycle();
		}
	}

	public void fillOval( int x, int y, int width, int height ){
		x += _origin_x;
		y += _origin_y;
		_paint.setStyle( Paint.Style.FILL );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_rectf_src.left   = (float)x;
			_rectf_src.top    = (float)y;
			_rectf_src.right  = (float)(x + width );
			_rectf_src.bottom = (float)(y + height);
			_canvas.drawOval( _rectf_src, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( width + _expand * 2, height + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			_rectf_src.left   = (float)_expand;
			_rectf_src.top    = (float)_expand;
			_rectf_src.right  = (float)(_expand + width );
			_rectf_src.bottom = (float)(_expand + height);
			canvas.drawOval( _rectf_src, _paint );
			addPixels( tmp, x - _expand, y - _expand, 255 );
			tmp.recycle();
		}
	}

	public void drawCircle( int cx, int cy, int r ){
		cx += _origin_x;
		cy += _origin_y;
		_paint.setStyle( Paint.Style.STROKE );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_canvas.drawCircle( cx, cy, r, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( r * 2 + _expand * 2, r * 2 + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			canvas.drawCircle( r + _expand, r + _expand, r, _paint );
			addPixels( tmp, cx - r - _expand, cy - r - _expand, 255 );
			tmp.recycle();
		}
	}

	public void fillCircle( int cx, int cy, int r ){
		cx += _origin_x;
		cy += _origin_y;
		_paint.setStyle( Paint.Style.FILL );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_canvas.drawCircle( cx, cy, r, _paint );
		} else {
			Bitmap tmp = Bitmap.createBitmap( r * 2 + _expand * 2, r * 2 + _expand * 2, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			canvas.drawCircle( r + _expand, r + _expand, r, _paint );
			addPixels( tmp, cx - r - _expand, cy - r - _expand, 255 );
			tmp.recycle();
		}
	}

	public void drawString( String str, int x, int y ){
		x += _origin_x;
		y += _origin_y;
		_paint.setStyle( Paint.Style.FILL );
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_canvas.drawText( str, x, y - _paint.descent(), _paint );
		} else {
			int height = fontHeight();
			Bitmap tmp = Bitmap.createBitmap( stringWidth( str ), height, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( tmp );
			canvas.drawText( str, 0, height - _paint.descent(), _paint );
			addPixels( tmp, x, y - height, 255 );
			tmp.recycle();
		}
	}

	private void drawBitmap( Bitmap bitmap, float x, float y, Paint paint ){
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_canvas.drawBitmap( bitmap, x, y, paint );
		} else {
			addPixels( bitmap, (int)x, (int)y, paint.getAlpha() );
		}
	}
	private void drawBitmap( Bitmap bitmap, Rect src, Rect dst, Paint paint ){
		if( (_bitmap == null) || (_rop == ROP_COPY) ){
			_canvas.drawBitmap( bitmap, src, dst, paint );
		} else {
			_mat.reset();
			_mat.setScale(
				(float)(dst.right  - dst.left) / (float)(src.right  - src.left),
				(float)(dst.bottom - dst.top ) / (float)(src.bottom - src.top )
				);
			try {
				Bitmap tmp = Bitmap.createBitmap( bitmap, src.left, src.top, src.right - src.left, src.bottom - src.top, _mat, true );
				addPixels( tmp, dst.left, dst.top, paint.getAlpha() );
				tmp.recycle();
			} catch( Exception e ){}
		}
	}
	private void drawBitmap( Bitmap bitmap, int dx, int dy, int sx, int sy, int width, int height, Paint paint ){
		switch( _flipmode ){
		case FLIP_NONE:
			_rect_src.left   = sx;
			_rect_src.top    = sy;
			_rect_src.right  = sx + width;
			_rect_src.bottom = sy + height;
			_rect_dst.left   = dx;
			_rect_dst.top    = dy;
			_rect_dst.right  = dx + width;
			_rect_dst.bottom = dy + height;
			drawBitmap( bitmap, _rect_src, _rect_dst, paint );
			break;
		case FLIP_HORIZONTAL:
		case FLIP_VERTICAL:
		case FLIP_ROTATE:
			_mat.reset();
			switch( _flipmode ){
			case FLIP_HORIZONTAL:
				_mat.setScale( -1.0f, 1.0f );
				break;
			case FLIP_VERTICAL:
				_mat.setScale( 1.0f, -1.0f );
				break;
			case FLIP_ROTATE:
				_mat.setScale( -1.0f, -1.0f );
				break;
			}
			try {
				Bitmap tmp = Bitmap.createBitmap( bitmap, sx, sy, width, height, _mat, true );
				drawBitmap( tmp, (float)dx, (float)dy, paint );
				tmp.recycle();
			} catch( Exception e ){}
			break;
		case FLIP_ROTATE_LEFT:
		case FLIP_ROTATE_RIGHT:
			_mat.reset();
			switch( _flipmode ){
			case FLIP_ROTATE_LEFT:
				_mat.setRotate( -90.0f );
				break;
			case FLIP_ROTATE_RIGHT:
				_mat.setRotate( 90.0f );
				break;
			}
			try {
				Bitmap tmp = Bitmap.createBitmap( bitmap, sx, sy, width, height, _mat, true );
				drawBitmap( tmp, (float)dx, (float)dy, paint );
				tmp.recycle();
			} catch( Exception e ){}
			break;
		}
	}

	public void drawImage( _Image image, int x, int y ){
		x += _origin_x;
		y += _origin_y;
		_paint_img.setAlpha( _alpha );
		drawBitmap( image.getBitmap(), x, y, 0, 0, image.getWidth(), image.getHeight(), _paint_img );
	}
	public void drawImage( _Image image, int dx, int dy, int sx, int sy, int width, int height ){
		dx += _origin_x;
		dy += _origin_y;
		_paint_img.setAlpha( _alpha );
		drawBitmap( image.getBitmap(), dx, dy, sx, sy, width, height, _paint_img );
	}

	public void drawScaledImage( _Image image, int dx, int dy, int width, int height, int sx, int sy, int swidth, int sheight ){
		dx += _origin_x;
		dy += _origin_y;
		_paint_img.setAlpha( _alpha );
		if( _flipmode == FLIP_NONE ){
			_rect_src.left   = sx;
			_rect_src.top    = sy;
			_rect_src.right  = sx + swidth;
			_rect_src.bottom = sy + sheight;
			_rect_dst.left   = dx;
			_rect_dst.top    = dy;
			_rect_dst.right  = dx + width;
			_rect_dst.bottom = dy + height;
			drawBitmap( image.getBitmap(), _rect_src, _rect_dst, _paint_img );
		} else {
			_mat.reset();
			_mat.setScale( (float)width / (float)swidth, (float)height / (float)sheight );
			Bitmap tmp = Bitmap.createBitmap( image.getBitmap(), sx, sy, swidth, sheight, _mat, true );
			drawBitmap( tmp, dx, dy, 0, 0, width, height, _paint_img );
			tmp.recycle();
		}
	}

	public void drawTransImage( _Image image, float dx, float dy, int sx, int sy, int width, int height, float cx, float cy, float r360, float z128x, float z128y ){
		dx += (float)_origin_x;
		dy += (float)_origin_y;
		_paint_img.setAlpha( _alpha );
		_mat.reset();
		_mat.postTranslate( -cx, -cy );
		_mat.postScale( z128x / 128.0f, z128y / 128.0f );
		_mat.postRotate( r360 );
		_mat.postTranslate( dx, dy );
		_rectf_src.left   = 0.0f;
		_rectf_src.top    = 0.0f;
		_rectf_src.right  = (float)width;
		_rectf_src.bottom = (float)height;
		_mat.mapRect( _rectf_dst, _rectf_src );
		try {
			Bitmap tmp = Bitmap.createBitmap( image.getBitmap(), sx, sy, width, height, _mat, true );
			drawBitmap( tmp, _rectf_dst.left, _rectf_dst.top, _paint_img );
			tmp.recycle();
		} catch( Exception e ){}
	}
}
