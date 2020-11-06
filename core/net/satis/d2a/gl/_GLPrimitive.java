/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.gl;

import java.nio.*;
import javax.microedition.khronos.opengles.GL10;

public class _GLPrimitive extends Object {
	public static final int TYPE_MODEL  = 0;
	public static final int TYPE_SPRITE = 1;

	private int _type;
	private boolean _depth;
	private int _trans = 255;

	public void setType( int type ){
		_type = type;
	}

	public void setDepth( boolean depth ){
		_depth = depth;
	}

	public void setTransparency( int trans ){
		_trans = trans;
	}

	public int type(){
		return _type;
	}

	public boolean depth(){
		return _depth;
	}

	public int transparency(){
		return _trans;
	}

	public void multMatrix( GL10 gl, float[] mat ){
		FloatBuffer mat_buffer;
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect( mat.length * 4 );
		byteBuffer.order( ByteOrder.nativeOrder() );
		mat_buffer = byteBuffer.asFloatBuffer();
		mat_buffer.put( mat );
		mat_buffer.position( 0 );
		gl.glMultMatrixf( mat_buffer );
	}
}
