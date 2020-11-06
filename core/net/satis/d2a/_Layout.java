/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import java.util.*;

public class _Layout {
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
			if(
				(x >= tmp._left) && (x < tmp._right ) &&
				(y >= tmp._top ) && (y < tmp._bottom)
			){
				return tmp._id;
			}
		}
		return -1;
	}

	public void draw( _Canvas canvas, _Graphics g ){
		MyLayout tmp;
		for( int i = _layout.size() - 1; i >= 0; i-- ){
			tmp = _layout.get(i);
			if( _window ){
				int left   = canvas.screenX( tmp._left   );
				int top    = canvas.screenY( tmp._top    );
				int right  = canvas.screenX( tmp._right  );
				int bottom = canvas.screenY( tmp._bottom );
				g.drawRoundRect( left, top, right - left - 1, bottom - top - 1, 16, 16 );
				g.drawString( "" + tmp._id, left + 5, top + 5 + g.fontHeight() );
			} else {
				g.drawRoundRect( tmp._left, tmp._top, tmp._right - tmp._left - 1, tmp._bottom - tmp._top - 1, 16, 16 );
				g.drawString( "" + tmp._id, tmp._left + 5, tmp._top + 5 + g.fontHeight() );
			}
		}
	}
}
