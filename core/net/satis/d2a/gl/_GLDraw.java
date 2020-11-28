/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.gl;

import java.util.*;
import javax.microedition.khronos.opengles.GL10;

public class _GLDraw {
	private ArrayList<Object> _draw;

	private class _GLDrawPrimitive extends Object {
		private _GLPrimitive _p;
		private int _index;
		private int _tex_index;
		private float[] _mat;

		private int _trans;

		public _GLDrawPrimitive( _GLPrimitive p, int index, int tex_index, float[] mat, int trans ){
			_p = p;
			_index = index;
			_tex_index = tex_index;
			_mat = new float[16];
			for( int i = 0; i < 16; i++ ){
				_mat[i] = mat[i];
			}
			_trans = (trans >= 0) ? trans : p.transparency();
		}

//		public int textureIndex(){
//			switch( _p.type() ){
//			case _GLPrimitive.TYPE_MODEL:
//				if( _tex_index < 0 ){
//					return ((_GLModel)_p).textureIndex( _index );
//				}
//				break;
//			case _GLPrimitive.TYPE_SPRITE:
//				break;
//			}
//			return _tex_index;
//		}

		public void draw( GL10 gl, _GLTexture glt, boolean alpha ){
			switch( _p.type() ){
			case _GLPrimitive.TYPE_MODEL:
				((_GLModel)_p).setTransparency( _trans );
				((_GLModel)_p).draw( gl, glt, _index, _tex_index, _mat, alpha );
				break;
			case _GLPrimitive.TYPE_SPRITE:
				((_GLSprite)_p).setTransparency( _trans );
				((_GLSprite)_p).draw( gl, glt, _tex_index, _mat, alpha );
				break;
			}
		}
	}

	public _GLDraw(){
		_draw = new ArrayList<Object>();
	}

	public void clear(){
		_draw.clear();
	}

	public void add( _GLPrimitive p, int index, int tex_index, float[] mat, int trans ){
		_draw.add( new _GLDrawPrimitive( p, index, tex_index, mat, trans ) );
	}

	public void addSprite( _GLUtility glu, _GLPrimitive p, int tex_index, float x, float y, float z, int trans ){
		_draw.add( new _GLDrawPrimitive( p, -1, tex_index, glu.spriteMatrix( x, y, z ), trans ) );
	}

	public void draw( GL10 gl, _GLTexture glt ){
		int i;
		_GLDrawPrimitive tmp;

		int count = _draw.size();

		// まず、アルファ情報のない物体を描画する
		for( i = 0; i < count; i++ ){
			tmp = (_GLDrawPrimitive)_draw.get( i );
			tmp.draw( gl, glt, false );
		}

		// 次に、アルファ情報のある物体を描画する
		for( i = 0; i < count; i++ ){
			tmp = (_GLDrawPrimitive)_draw.get( i );
			tmp.draw( gl, glt, true );
		}
	}
}
