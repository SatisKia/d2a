/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import java.io.InputStream;
import java.util.ArrayList;

public class _State {
	private String _id;

	public _State( String id ){
		_id = new String( id );
	}

	public String id(){
		return _id;
	}

	public void enter(){
	}

	public void leave(){
	}

	public void draw( _ScalableGraphics g ){
	}

	public void processEvent( int type, int param ){
	}

	public _State go( ArrayList<_State> stateArray, String id ){
		_State tmp;
		for( int i = stateArray.size() - 1; i >= 0; i-- ) {
			tmp = stateArray.get( i );
			if( tmp.id().equals( id ) ){
				leave();
				tmp.enter();
				return tmp;
			}
		}
		return null;
	}

	// _HttpRequest クラス用
	public void onHttpResponse( InputStream is ){}
	public void onHttpError( int status, InputStream is ){}
}
