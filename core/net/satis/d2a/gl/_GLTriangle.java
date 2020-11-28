/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.gl;

public class _GLTriangle extends Object {
	private int _num;
	private float[][] _coord_x;
	private float[][] _coord_y;
	private float[][] _coord_z;
	private float[] _normal_x;
	private float[] _normal_y;
	private float[] _normal_z;
	private float[] _center_x;
	private float[] _center_y;
	private float[] _center_z;

	public _GLTriangle( _GLUtility glu, _GLModel model, int index ){
		int i, j;

		// 三角形の数を取得する
		_num = 0;
		glu.beginGetTriangle();
		while( glu.getTriangle( model, index, false ) ){
			_num++;
		}

		if( _num > 0 ){
			_coord_x = new float[_num][];
			_coord_y = new float[_num][];
			_coord_z = new float[_num][];
			for( i = 0; i < _num; i++ ){
				_coord_x[i] = new float[3];
				_coord_y[i] = new float[3];
				_coord_z[i] = new float[3];
			}
			_normal_x = new float[_num];
			_normal_y = new float[_num];
			_normal_z = new float[_num];
			_center_x = new float[_num];
			_center_y = new float[_num];
			_center_z = new float[_num];

			j = 0;
			glu.beginGetTriangle();
			while( glu.getTriangle( model, index, false ) ){
				for( i = 0; i < 3; i++ ){
					_coord_x[j][i] = glu.coordX( i );
					_coord_y[j][i] = glu.coordY( i );
					_coord_z[j][i] = glu.coordZ( i );
				}

				glu.getTriangleNormal( model, index, false );
				_normal_x[j] = glu.normalX();
				_normal_y[j] = glu.normalY();
				_normal_z[j] = glu.normalZ();

				_center_x[j] = glu.centerX();
				_center_y[j] = glu.centerY();
				_center_z[j] = glu.centerZ();

				j++;
			}
		}
	}

	public int num(){
		return _num;
	}
	public float[] coord_x( int i ){
		return _coord_x[i];
	}
	public float[] coord_y( int i ){
		return _coord_y[i];
	}
	public float[] coord_z( int i ){
		return _coord_z[i];
	}
	public float normal_x( int i ){
		return _normal_x[i];
	}
	public float normal_y( int i ){
		return _normal_y[i];
	}
	public float normal_z( int i ){
		return _normal_z[i];
	}
	public float center_x( int i ){
		return _center_x[i];
	}
	public float center_y( int i ){
		return _center_y[i];
	}
	public float center_z( int i ){
		return _center_z[i];
	}

	public boolean check( int i, float x_min, float x_max, float y_min, float y_max, float z_min, float z_max ){
		if(
			(_center_x[i] >= x_min) && (_center_x[i] <= x_max) &&
			(_center_y[i] >= y_min) && (_center_y[i] <= y_max) &&
			(_center_z[i] >= z_min) && (_center_z[i] <= z_max)
		){
			return true;
		}
		return false;
	}

	public int hitCheck( _GLUtility glu, float px, float py, float pz, float qx, float qy, float qz, float r ){
		int i;
		for( i = 0; i < _num; i++ ){
			if(
				(_center_x[i] >= px - r) && (_center_x[i] <= px + r) &&
				(_center_y[i] >= py - r) && (_center_y[i] <= py + r) &&
				(_center_z[i] >= pz - r) && (_center_z[i] <= pz + r)
			){
				if( glu.hitCheck( px, py, pz, qx, qy, qz, _coord_x[i], _coord_y[i], _coord_z[i] ) ){
					return i;
				}
			}
		}
		return -1;
	}
}
