/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.gl;

import android.content.res.*;
import android.graphics.*;
import javax.microedition.khronos.opengles.GL10;
import net.satis.d2a.*;

public class _GLTexture2 extends _GLTexture {
	// 2D描画用のテクスチャ・イメージ
	private _Image _img2D = null;
	int[] _pixels2D;
	private int _index2D;

	public _GLTexture2( GL10 gl, Resources res, int index_num, int gen_num ){
		super( gl, res, index_num, gen_num + 1 );

		// 2D描画用のテクスチャ・イメージ
		_index2D = gen_num;
	}

	@Override
	public void dispose(){
		super.dispose();

		if( _img2D != null ){
			_img2D.dispose();
		}
	}

	public void create2D( int width, int height ){
		// 2D描画用のテクスチャ・イメージ
		_img2D = _Image.createImage( _GLTexture.getTextureSize( width ), _GLTexture.getTextureSize( height ) );
		_pixels2D = new int[_img2D.getWidth() * _img2D.getHeight()];
	}

	public _Image getImage2D(){
		return _img2D;
	}

	public _Graphics lock2D(){
//		_img2D.clear( _pixels2D );
		_img2D.clear();
		return _img2D.getGraphics();
	}

	public void unlock2D( boolean applyScale ){
		// テクスチャ更新
		int width  = _img2D.getWidth();
		int height = _img2D.getHeight();
		_img2D.getBitmap().getPixels( _pixels2D, 0, width, 0, 0, width, height );
		update( _index2D, _pixels2D, width * height );

		// 画面に表示
		draw2D( applyScale );
	}

	public void draw2D( boolean applyScale ){
		float scale = _scale;
		int flipmode = _flipmode;

		if( !applyScale ){
			setScale( 1.0f );
		}
		setFlipMode( _GLTexture.FLIP_NONE );

		draw( _index2D, 0, 0 );

		if( !applyScale ){
			setScale( scale );
		}
		setFlipMode( flipmode );
	}

	@Override
	public Bitmap createBitmap( Resources res, int index ){
		if( index == _index2D ){
			return _img2D.createBitmap();
		}
		return super.createBitmap( res, index );
	}
}
