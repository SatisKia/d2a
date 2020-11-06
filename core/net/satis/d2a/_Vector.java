/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import java.util.*;

public class _Vector {
	private ArrayList<Object> _list;

	public _Vector(){
		_list = new ArrayList<Object>();
	}

	public void addElement( Object object ){
		_list.add( object );
	}

	public void setElementAt( Object object, int index ){
		_list.set( index, object );
	}

	public void insertElementAt( Object object, int index ){
		_list.add( index, object );
	}

	public void removeElementAt( int index ){
		_list.remove( index );
	}

	public void removeAllElements(){
		_list.clear();
	}

	public Object elementAt( int index ){
		return _list.get( index );
	}

	public Object firstElement(){
		return _list.get( 0 );
	}

	public Object lastElement(){
		return _list.get( _list.size() - 1 );
	}

	public boolean isEmpty(){
		return _list.isEmpty();
	}

	public int size(){
		return _list.size();
	}
}
