/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.gl;

import java.nio.*;

import javax.microedition.khronos.opengles.GL10;

public class _GLSprite extends _GLPrimitive {
	private float[] _coord;
	private float[] _map;
	private float[] _uv;
	private boolean _uv_f = true;
	private byte[] _strip;
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
	private ByteBuffer _strip_buffer;
	private void allocStripBuffer(){
		_strip_buffer = ByteBuffer.allocateDirect( _strip.length * 1 );
	}
	private void putStripBuffer(){
		_strip_buffer.put( _strip );
		_strip_buffer.position( 0 );
	}

	public _GLSprite( boolean depth ){
		setType( _GLPrimitive.TYPE_SPRITE );
		setDepth( depth );

		_coord = new float[12];
		_map = new float[8];
		_uv = new float[8];
		_strip = new byte[4];

		allocCoordBuffer();
		allocUvBuffer();
		allocStripBuffer();

		_coord[0] = -1.0f; _coord[ 1] = -1.0f; _coord[ 2] = 0.0f;
		_coord[3] =  1.0f; _coord[ 4] = -1.0f; _coord[ 5] = 0.0f;
		_coord[6] = -1.0f; _coord[ 7] =  1.0f; _coord[ 8] = 0.0f;
		_coord[9] =  1.0f; _coord[10] =  1.0f; _coord[11] = 0.0f;
		putCoordBuffer();

		_uv[0] = 0.0f; _uv[1] = 1.0f;
		_uv[2] = 1.0f; _uv[3] = 1.0f;
		_uv[4] = 0.0f; _uv[5] = 0.0f;
		_uv[6] = 1.0f; _uv[7] = 0.0f;
		putUvBuffer();

		_strip[0] = 0;
		_strip[1] = 1;
		_strip[2] = 2;
		_strip[3] = 3;
		putStripBuffer();
	}

	public void setCoord( float[] coord ){
		for( int i = 0; i < 12; i++ ){
			_coord[i] = coord[i];
		}
		putCoordBuffer();
	}

	public void setMap( float[] map, boolean uv ){
		_uv_f = uv;
		if( _uv_f ){
			for( int i = 0; i < 8; i++ ){
				_uv[i] = map[i];
			}
			putUvBuffer();
		} else {
			for( int i = 0; i < 8; i++ ){
				_map[i] = map[i];
			}
		}
	}

	public void setStrip( byte[] strip ){
		for( int i = 0; i < 4; i++ ){
			_strip[i] = strip[i];
		}
		putStripBuffer();
	}

	public boolean textureAlpha( _GLTexture glt, int tex_index ){
		glt.use( tex_index );
		glt.setTransparency( tex_index, transparency() );
		return (glt.alpha( tex_index ) && !depth());
	}

	public void draw( GL10 gl, _GLTexture glt, int tex_index, float[] mat, boolean alpha ){
		boolean alpha2 = textureAlpha( glt, tex_index );
		if( transparency() != 255 ){
			alpha2 = true;
		}
		if( alpha2 != alpha ){
			return;
		}

		gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, _coord_buffer );

		if( !_uv_f ){
			int width  = glt.width( tex_index );
			int height = glt.height( tex_index );
			for( int i = 0; i < 4; i++ ){
				_uv[i * 2    ] = _map[i * 2    ] / (float)width;
				_uv[i * 2 + 1] = _map[i * 2 + 1] / (float)height;
			}
			putUvBuffer();
		}

		gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glBindTexture( GL10.GL_TEXTURE_2D, glt.id( tex_index ) );
		gl.glTexCoordPointer( 2, GL10.GL_FLOAT, 0, _uv_buffer );
		gl.glTexEnvf( GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE );

		if( glt.alpha( tex_index ) ){
			gl.glEnable( GL10.GL_ALPHA_TEST );
		}
		if( alpha2 ){
			gl.glEnable( GL10.GL_BLEND );
			gl.glDepthMask( false );
		}

		gl.glPushMatrix();
		multMatrix( gl, mat );
		gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, _strip_buffer );
		gl.glPopMatrix();

		if( glt.alpha( tex_index ) ){
			gl.glDisable( GL10.GL_ALPHA_TEST );
		}
		if( alpha2 ){
			gl.glDisable( GL10.GL_BLEND );
			gl.glDepthMask( true );
		}
	}
}
