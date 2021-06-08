/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import java.util.*;

public class _Layout extends _Scalable {
	private class MyLayout {
		public int _left;
		public int _top;
		public int _right;
		public int _bottom;
		public int _id;
	}

	private ArrayList<MyLayout> _layout;
	private boolean _window;

	public _Layout( boolean window ){
		_layout = new ArrayList<MyLayout>();
		_window = window;
	}

	public void clear(){
		_layout.clear();
	}

	public void add( int left, int top, int width, int height, int id ){
		MyLayout tmp = new MyLayout();
		tmp._left   = left;
		tmp._top    = top;
		tmp._right  = left + width;
		tmp._bottom = top + height;
		tmp._id     = id;
		_layout.add( tmp );
	}

	public int getLeft( int id ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get( i );
			if( tmp._id == id ){
				return tmp._left;
			}
		}
		return 0;
	}
	public int getTop( int id ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get( i );
			if( tmp._id == id ){
				return tmp._top;
			}
		}
		return 0;
	}
	public int getRight( int id ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get( i );
			if( tmp._id == id ){
				return tmp._right;
			}
		}
		return 0;
	}
	public int getBottom( int id ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get( i );
			if( tmp._id == id ){
				return tmp._bottom;
			}
		}
		return 0;
	}
	public int getWidth( int id ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get( i );
			if( tmp._id == id ){
				return tmp._right - tmp._left;
			}
		}
		return 0;
	}
	public int getHeight( int id ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get( i );
			if( tmp._id == id ){
				return tmp._bottom - tmp._top;
			}
		}
		return 0;
	}

	public boolean isWindow(){
		return _window;
	}

	public int check( int x, int y ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get( i );
			if( _window ) {
				if(
					(x >= tmp._left) && (x < tmp._right ) &&
					(y >= tmp._top ) && (y < tmp._bottom)
				){
					return tmp._id;
				}
			} else {
				if(
					(x >= scaledValue( tmp._left )) && (x < scaledValue( tmp._right  )) &&
					(y >= scaledValue( tmp._top  )) && (y < scaledValue( tmp._bottom ))
				){
					return tmp._id;
				}
			}
		}
		return -1;
	}

	public void draw( _Canvas canvas, _Graphics g ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get(i);
			int left, top, right, bottom;
			if( _window ){
				left   = canvas.screenX( tmp._left   );
				top    = canvas.screenY( tmp._top    );
				right  = canvas.screenX( tmp._right  );
				bottom = canvas.screenY( tmp._bottom );
			} else {
				left   = scaledValue( tmp._left   );
				top    = scaledValue( tmp._top    );
				right  = scaledValue( tmp._right  );
				bottom = scaledValue( tmp._bottom );
			}
			g.drawRect( left, top, right - left - 1, bottom - top - 1 );
			g.drawString( "" + tmp._id, left + 5, top + 5 + g.fontHeight() );
		}
	}
}
