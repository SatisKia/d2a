/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.*;
import android.webkit.*;

public class _WebView extends WebViewClient {
	private _Main _m;
	private WebView _view;
	private boolean _pauseTimers = false;

	public _WebView( _Main m, WebView view, WebChromeClient client ){
		_m = m;
		_view = view;
		_view.setWebViewClient( this );
		_view.setWebChromeClient( client );
	}

	public void destroy(){
		_view.stopLoading();
		if( _pauseTimers ){
			_view.resumeTimers();
			_pauseTimers = false;
		}
		_view.setWebViewClient( null );
		_view.setWebChromeClient( null );
		_view.destroy();
		_view = null;
	}

	public void suspend(){
		try {
			WebView.class.getMethod( "onPause" ).invoke( _view );
		} catch( Exception e ){}

		if( !_pauseTimers ){
			_view.pauseTimers();
			_pauseTimers = true;
		}
	}

	public void resume(){
		if( _pauseTimers ){
			_view.resumeTimers();
			_pauseTimers = false;
		}

		try {
			WebView.class.getMethod( "onResume" ).invoke( _view );
		} catch( Exception e ){}
	}

	public WebView getView(){
		return _view;
	}

	public int getWidth(){
		return _view.getWidth();
	}
	public int getHeight(){
		return _view.getHeight();
	}

	public void hideScrollBar(){
		_view.setScrollBarStyle( View.SCROLLBARS_INSIDE_OVERLAY );
		_view.setHorizontalScrollBarEnabled( false );
		_view.setVerticalScrollBarEnabled( false );
	}

	public void setViewportWidth( int width ){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager)_m.getSystemService( Activity.WINDOW_SERVICE );
		Display display = windowManager.getDefaultDisplay();
		display.getMetrics( metrics );
		_view.setInitialScale( 100 * metrics.widthPixels / width );
		_view.getSettings().setUseWideViewPort( true );
		_view.getSettings().setLoadWithOverviewMode( true );
	}

	public void addJavascriptInterface( Object object, String name ){
		// Android 4.2（API level 17）以降
		if( Build.VERSION.SDK_INT >= 17 ){
			_view.addJavascriptInterface( object, name );
		}
	}
	public void setAppCacheEnabled( boolean enabled ){
		_view.getSettings().setAppCacheEnabled( enabled );
	}
	public void setBackgroundColor( int color ){
		_view.setBackgroundColor( color );
	}
	public void setBuiltInZoomControls( boolean enabled ){
		_view.getSettings().setBuiltInZoomControls( enabled );
	}
	public void setDefaultTextEncodingName( String encoding ){
		_view.getSettings().setDefaultTextEncodingName( encoding );
	}
	public void setGeolocationEnabled( boolean enabled ){
		_view.getSettings().setGeolocationEnabled( enabled );
	}
	public void setJavaScriptEnabled( boolean enabled ){
		_view.getSettings().setJavaScriptEnabled( enabled );
	}
	public void setStorageEnabled( boolean enabled ){
		_view.getSettings().setDomStorageEnabled( enabled );
		if( enabled ){
			_view.getSettings().setDatabaseEnabled( true );

			// Android 4.4（API level 19）以前
			if( Build.VERSION.SDK_INT < 19 ){
				File databaseDir = _m.getDir( "databases", Context.MODE_PRIVATE );
				if( !databaseDir.exists() ){
					databaseDir.mkdirs();
				}
				_view.getSettings().setDatabasePath( databaseDir.getPath() );
			}
		}
	}
	public String getUserAgentString(){
		return _view.getSettings().getUserAgentString();
	}
	public void setUserAgentString( String ua ){
		_view.getSettings().setUserAgentString( ua );
	}

	public void clearCache(){
		_view.clearCache( true );
	}

	public void loadUrl( String url ){
		_view.loadUrl( url );
	}

	public void loadFile( String file ){
		_view.loadUrl( "file:///android_asset/" + file );
	}

	public void callScript( String script ){
		_view.loadUrl( "javascript:" + script );
	}

	// 新しい URL が指定されたときの処理
	@Override
	public boolean shouldOverrideUrlLoading( WebView view, String url ){
		if( _m.onWebViewShouldStartLoad( url ) ){
			return true;
		}
		return super.shouldOverrideUrlLoading( view, url );
	}

	// ページ読み込み開始時の処理
	@Override
	public void onPageStarted( WebView view, String url, Bitmap favicon ){
		super.onPageStarted( view, url, favicon );
		_m.onWebViewStartLoad( url );
	}

	// ページ読み込み完了時の処理
	@Override
	public void onPageFinished( WebView view, String url ){
		super.onPageFinished( view, url );
		_m.onWebViewFinishLoad( url );
	}

	// ページ読み込みエラー時の処理
	@Override
	public void onReceivedError( WebView view, int errorCode, String description, String failingUrl ){
		super.onReceivedError( view, errorCode, description, failingUrl );
		_m.onWebViewLoadError( errorCode, description, failingUrl );
	}

	public void setFocus(){
		_view.requestFocus( View.FOCUS_DOWN );
	}

	public boolean canGoBack(){
		return _view.canGoBack();
	}
	public String getBackUrl(){
		if( canGoBack() ){
			WebBackForwardList list = _view.copyBackForwardList();
//			int index = list.getCurrentIndex() - 1;
//			if( index >= 0 ){
				WebHistoryItem item = list.getItemAtIndex( list.getCurrentIndex() - 1 );
				return item.getUrl();
//			}
		}
		return null;
	}
	public void goBack(){
		if( canGoBack() ){
			_view.goBack();
		}
	}

	public boolean canGoForward(){
		return _view.canGoForward();
	}
	public String getForwardUrl(){
		if( canGoForward() ){
			WebBackForwardList list = _view.copyBackForwardList();
//			int index = list.getCurrentIndex() + 1;
//			if( (index > 0) && (index < list.getSize()) ){
				WebHistoryItem item = list.getItemAtIndex( list.getCurrentIndex() + 1 );
				return item.getUrl();
//			}
		}
		return null;
	}
	public void goForward(){
		if( canGoForward() ){
			_view.goForward();
		}
	}

	public void zoomIn(){
		_view.zoomIn();
	}

	public void zoomOut(){
		_view.zoomOut();
	}
}
