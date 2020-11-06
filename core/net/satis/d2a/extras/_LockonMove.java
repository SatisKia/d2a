/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.extras;

public class _LockonMove extends Object {
	private int[] _move_x;
	private int[] _move_y;
	private int _step;

	private float _x, _y;		// 現在位置
	private int _tx, _ty;		// 目的位置
	private int _direction;		// 方向
	private boolean _clockwise;	// 180度反対方向だった場合に時計回りに回すかどうか
	private boolean _clockwise2;

	private float deg2rad( float angle ){
		return (angle * 3.14159265358979323846264f) / 180.0f;
	}

	public _LockonMove( int x0, int y0, int x1, int y1, int step, boolean clockwise ){
		_step = step;
		_move_x = new int[_step];
		_move_y = new int[_step];
		for( int i = 0; i < _step; i++ ){
			float deg = (float)(360 * i) / (float)_step;
			_move_x[i] = (int)(Math.sin( deg2rad( deg          ) ) * 120.0f);
			_move_y[i] = (int)(Math.cos( deg2rad( deg + 180.0f ) ) * 120.0f);
		}

		_x = (float)x0;
		_y = (float)y0;
		_tx = x1;
		_ty = y1;
		_direction = direction( (int)_x, (int)_y, _tx, _ty );
		_clockwise = clockwise;
		_clockwise2 = false;
	}

	public int direction( int x0, int y0, int x1, int y1 ){
		x1 -= x0;
		y1 -= y0;
		int dx = 0;
		int dy = 0;
		int tmp_d = 0;
		int d = 0;
		int j = 0;
		for( int i = 0; i < _step; i++ ){
			dx = x1 - _move_x[i];
			dy = y1 - _move_y[i];
			tmp_d = dx * dx + dy * dy;
			if( (i == 0) || (tmp_d < d) ){
				d = tmp_d;
				j = i;
			}
		}
		return j;
	}
	public float normalizeX( int direction ){
		if( direction < 0 ){
			direction += _step;
		} else if( direction >= _step ){
			direction -= _step;
		}
		return (float)_move_x[direction] / 120.0f;
	}
	public float normalizeY( int direction ){
		if( direction < 0 ){
			direction += _step;
		} else if( direction >= _step ){
			direction -= _step;
		}
		return (float)_move_y[direction] / 120.0f;
	}

	public void addDirection( int add ){
		_direction += add;
		if( _direction < 0 ){
			_direction += _step;
		} else if( _direction >= _step ){
			_direction -= _step;
		}
	}

	public void setTarget( int tx, int ty ){
		_tx = tx;
		_ty = ty;
	}

	public void update( int dist, int step ){
		_x += (float)_move_x[_direction] * (float)dist / 120.0f;
		_y += (float)_move_y[_direction] * (float)dist / 120.0f;
		int tmp = direction( (int)_x, (int)_y, _tx, _ty );
		if( Math.abs( _direction - tmp ) == _step / 2 ){
			if( !_clockwise2 ){
				_clockwise = _clockwise ? false : true;
				_clockwise2 = true;
			}
			if( _clockwise ){
				step = 0 - step;
			}
		} else {
			_clockwise2 = false;
			if(
				((tmp >  _direction) && ((tmp - _direction) >= _step / 2)) ||
				((tmp <= _direction) && ((_direction - tmp) <  _step / 2))
			){
				step = 0 - step;
			}
		}
		_direction = ((_direction + step) + _step) % _step;
	}

	public int getX(){
		return (int)_x;
	}
	public int getY(){
		return (int)_y;
	}
	public int getDirection(){
		return _direction;
	}
}
