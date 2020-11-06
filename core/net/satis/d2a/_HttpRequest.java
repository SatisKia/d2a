/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class _HttpRequest {
	private _Main _m;
	private HttpUriRequest _request;
	private boolean _busy = false;

	// ÉGÉâÅ[
	public static final int CLIENTPROTOCOL_ERROR = -1;
	public static final int IO_ERROR             = -2;

	public _HttpRequest( _Main m ){
		_m = m;
	}

	private void execute(){
		(new Thread( new Runnable(){
			public void run(){
				DefaultHttpClient client = new DefaultHttpClient();
				HttpResponse response = null;
				try {
					response = client.execute( _request );
				} catch( ClientProtocolException e ){
					_m.onHttpError( CLIENTPROTOCOL_ERROR );
				} catch( IOException e ){
					_m.onHttpError( IO_ERROR );
				}
				if( response != null ){
					int status = response.getStatusLine().getStatusCode();
					if( status == HttpStatus.SC_OK ){
						InputStream is = null;
						try {
							is = response.getEntity().getContent();
						} catch( Exception e ){}
						_m.onHttpResponse( is );
						if( is != null ){
							try {
								is.close();
							} catch( Exception e ){}
							is = null;
						}
					} else {
						_m.onHttpError( status );
					}
				}
				client.getConnectionManager().shutdown();
				_busy = false;
			}
		} )).start();
	}

	public boolean get( String url ){
		if( _busy ){
			return false;
		}
		_busy = true;
		_request = new HttpGet( url );
		execute();
		return true;
	}

	public boolean post( String url, String[] vars, String encoding ){
		if( _busy ){
			return false;
		}
		_busy = true;
		_request = new HttpPost( url );
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		for( int i = 0; i < vars.length / 2; i++ ){
			params.add( new BasicNameValuePair( vars[i * 2], vars[i * 2 + 1] ) );
		}
		try {
			((HttpPost)_request).setEntity( new UrlEncodedFormEntity( params, encoding ) );
		} catch( Exception e ){}
		execute();
		return true;
	}

	public boolean busy(){
		return _busy;
	}
}
