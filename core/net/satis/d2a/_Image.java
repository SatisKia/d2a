/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.res.*;
import android.graphics.*;
//import java.util.Arrays;

public class _Image extends Object {
	private Bitmap _bitmap;
	private boolean _create;
	private int _width;
	private int _height;
	private _Graphics _g = null;

	public _Image( boolean create ){
		_create = create;
	}

	public static _Image createImage( Resources res, int id ){
		_Image img = new _Image( true );
		img._bitmap = BitmapFactory.decodeResource( res, id );
		img._width  = img._bitmap.getWidth();
		img._height = img._bitmap.getHeight();
		return img;
	}
	public static _Image createImage( int width, int height, boolean use_g ){
		_Image img = new _Image( true );
		img._width  = width;
		img._height = height;
		img._bitmap = Bitmap.createBitmap( img._width, img._height, Bitmap.Config.ARGB_8888 );
		img.clear();
		if( use_g ){
			img._g = new _Graphics( img._bitmap );
		}
		return img;
	}
	public static _Image createImage( int width, int height ){
		return createImage( width, height, true );
	}

	public static _Image attachBitmap( Bitmap bitmap ){
		_Image img = new _Image( false );
		img._bitmap = bitmap;
		img._width  = img._bitmap.getWidth();
		img._height = img._bitmap.getHeight();
		return img;
	}

	public void dispose(){
		if( _create ) {
			_bitmap.recycle();
		}
	}

	public void mutable( boolean use_g ){
		if( !(_bitmap.isMutable()) ){
			Bitmap tmp = _bitmap.copy( Bitmap.Config.ARGB_8888, true );
			if( _create ) {
				_bitmap.recycle();
			}
			_bitmap = tmp;
			_create = true;
			if( use_g ){
				_g = new _Graphics( _bitmap );
			}
		}
	}
	public void mutable(){
		mutable( true );
	}

//	public void clear( int[] pixels ){
//		if( _bitmap.isMutable() ){
//			if( pixels == null ){
//				pixels = new int[_width * _height];
//			}
//			Arrays.fill( pixels, 0 );
//			_bitmap.setPixels( pixels, 0, _width, 0, 0, _width, _height );
//		}
//	}
	public void clear(){
//		clear( null );
		if( _bitmap.isMutable() ){
			_bitmap.eraseColor( 0 );
		}
	}

	public Bitmap getBitmap(){
		return _bitmap;
	}

	public Bitmap createBitmap(){
		return _bitmap.copy( Bitmap.Config.ARGB_8888, true );
	}

	public int getWidth(){
		return _width;
	}
	public int getHeight(){
		return _height;
	}

	public _Graphics getGraphics(){
		return _g;
	}

	public int getPixel( int x, int y ){
		return _bitmap.getPixel( x, y );
	}

	public int[] getPixels( int x, int y, int width, int height, int[] pixels, int off ){
		if( pixels == null ){
			pixels = new int[off + width * height];
		}
		_bitmap.getPixels( pixels, off, width, x, y, width, height );
		return pixels;
	}

	public void setPixel( int x, int y, int color ){
		_bitmap.setPixel( x, y, color );
	}

	public void setPixels( int x, int y, int width, int height, int[] pixels, int off ){
		_bitmap.setPixels( pixels, off, width, x, y, width, height );
	}
}
