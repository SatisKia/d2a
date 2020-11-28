/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class _HttpRequest {
	private _Main _m;
	private int _timeout = 0;	// タイムアウトミリ秒（0で無限）
	private boolean _busy = false;

	// エラー
	public static final int IO_ERROR = -1;

	public _HttpRequest( _Main m ){
		_m = m;
	}

	public void setTimeout( int timeout ){
		_timeout = timeout;
	}

	public boolean get( final String url ){
		if( _busy ){
			return false;
		}
		_busy = true;

		(new Thread( new Runnable(){
			public void run(){
				HttpURLConnection conn = null;
				try {
					conn = (HttpURLConnection)(new URL( url )).openConnection();
					conn.setConnectTimeout( _timeout );	// 接続にかかる時間
					conn.setReadTimeout( _timeout );	// データの読み込みにかかる時間
					conn.setRequestMethod( "GET" );		// HTTPメソッド
					conn.setUseCaches( false );			// キャッシュ利用
					conn.setDoOutput( false );			// リクエストのボディの送信を許可（GET時はfalse）
					conn.setDoInput( true );			// レスポンスのボディの受信を許可

					conn.connect();

					int response = conn.getResponseCode();
					if( response == HttpURLConnection.HTTP_OK ){
						InputStream is = null;
						try {
							is = conn.getInputStream();
						} catch( Exception e ){}
						_m.onHttpResponse( is );
						if( is != null ){
							try {
								is.close();
							} catch( Exception e ){}
							is = null;
						}
					} else {
						_m.onHttpError( response );
					}
				} catch( IOException e ){
					_m.onHttpError( IO_ERROR );
				} finally {
					if( conn != null ){
						conn.disconnect();
					}
					_busy = false;
				}
			}
		} )).start();

		return true;
	}

	public boolean post( final String url, final String jsonString, final String encoding ){
		if( _busy ){
			return false;
		}
		_busy = true;

		(new Thread( new Runnable(){
			public void run(){
				HttpURLConnection conn = null;
				try {
					conn = (HttpURLConnection)(new URL( url )).openConnection();
					conn.setConnectTimeout( _timeout );	// 接続にかかる時間
					conn.setReadTimeout( _timeout );	// データの読み込みにかかる時間
					conn.setRequestMethod( "POST" );	// HTTPメソッド
					conn.setUseCaches( false );			// キャッシュ利用
					conn.setDoOutput( true );			// リクエストのボディの送信を許可（POST時はtrue）
					conn.setDoInput( true );			// レスポンスのボディの受信を許可

					OutputStream out = conn.getOutputStream();
					final boolean autoFlash = true;
					PrintStream ps = new PrintStream( out, autoFlash, encoding );
					ps.print( jsonString );
					ps.close();

					int response = conn.getResponseCode();
					if( response == HttpURLConnection.HTTP_OK ){
						InputStream is = null;
						try {
							is = conn.getInputStream();
						} catch( Exception e ){}
						_m.onHttpResponse( is );
						if( is != null ){
							try {
								is.close();
							} catch( Exception e ){}
							is = null;
						}
					} else {
						_m.onHttpError( response );
					}

				} catch( IOException e ){
					_m.onHttpError( IO_ERROR );
				} finally {
					if( conn != null ){
						conn.disconnect();
					}
					_busy = false;
				}
			}
		} )).start();

		return true;
	}

	public boolean busy(){
		return _busy;
	}
}
