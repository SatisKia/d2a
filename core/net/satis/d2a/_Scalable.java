/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

public class _Scalable {
	protected float _scale = 1.0f;

	public void setScale( float scale ){
		_scale = scale;
	}

	public float scale(){
		return _scale;
	}

	public int scaledValue( int val ){
		return (int)((float)val * scale());
	}
}
