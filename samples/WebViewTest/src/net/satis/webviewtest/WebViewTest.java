package net.satis.webviewtest;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.KeyEvent;
import android.webkit.*;
import android.webkit.GeolocationPermissions.Callback;
import android.widget.RelativeLayout;
import net.satis.d2a.*;
import net.satis.d2a.extras.*;

public class WebViewTest extends _Main {
	public static final int LOG_NUM = 5;
	public static final int FONT_SIZE = 20;
	public static final int LOG_HEIGHT = LOG_NUM * FONT_SIZE;
	private _Log log;

	public static final int WRAP_CONTENT = RelativeLayout.LayoutParams.WRAP_CONTENT;

	private _WebView webView = null;

	private String userAgent;
	private String url;
	private String backUrl;
	private String forwardUrl;
	private int elapse;

	private _Sound sound;

	void createSound(){
		sound.create( 0, R.raw.hit );
	}

	class MyJsInterface {
		@JavascriptInterface
		public void playSeHit(){
			sound.play( 0 );
		}
	}

	@Override
	@SuppressLint( "SetJavaScriptEnabled" )
	public void start(){
		log = new _Log( LOG_NUM );

		webView = new _WebView( this, new WebView( this ),
			new WebChromeClient(){
				@Override
				public void onGeolocationPermissionsShowPrompt( String origin, Callback callback ){
					callback.invoke( origin, true, false );
				}
			} );
		webView.addJavascriptInterface( new MyJsInterface(), "myJsInterface" );
		webView.setBackgroundColor( Color.TRANSPARENT );
		webView.setBuiltInZoomControls( true );
//		webView.setDefaultTextEncodingName( "UTF-8" );
//		webView.setDomStorageEnabled( true );
		webView.setGeolocationEnabled( true );
		webView.setJavaScriptEnabled( true );
//		webView.setUserAgentString( "..." );

		userAgent = new String( webView.getUserAgentString() );
		url = new String( "" );
		backUrl = new String( "" );
		forwardUrl = new String( "" );
		elapse = 0;

		sound = new _Sound( this, 1 );
		createSound();

		RelativeLayout relativeLayout = new RelativeLayout( this );
		setView( relativeLayout );

		addCanvas( new MyCanvas() );
	}

	public void addWebView( int bottom ){
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( WRAP_CONTENT, WRAP_CONTENT );
		params.setMargins( 0, 0, 0, bottom );	// left, top, right, bottom
		getView().addView( webView.getView(), params );
	}

	@Override
	public void destroy(){
		if( getView() != null ){
			getView().removeAllViews();
		}
		if( webView != null ){
			webView.destroy();
		}
	}

	@Override
	public void suspend(){
		if( webView != null ){
			webView.suspend();
		}

		sound.release();
	}

	@Override
	public void resume(){
		createSound();

		if( webView != null ){
			webView.resume();
			webView.setFocus();
		}
	}

	@Override
	public boolean onWebViewShouldStartLoad( String _url ){
		url = _url;
		elapse = 0;

		log.add( "H " + url );

		if( url.startsWith( "native://" ) ){
			String command = url.substring( 9 );
			if( command.equals( "se_hit" ) ){
				sound.play( 0 );
			}
			return true;
		}

		return false;
	}

	@Override
	public void onWebViewStartLoad( String _url ){
		url = _url;
		elapse = 0;

		log.add( "S " + url );
	}

	@Override
	public void onWebViewFinishLoad( String url ){
		log.add( "F " + url );

		backUrl = webView.getBackUrl();
		if( backUrl == null ){
			backUrl = new String( "-" );
		}

		forwardUrl = webView.getForwardUrl();
		if( forwardUrl == null ){
			forwardUrl = new String( "-" );
		}
	}

	@Override
	public void onWebViewLoadError( int errorCode, String description, String url ){
		log.add( "E " + url );
	}

	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event ){
		if( webView != null ){
			if( (keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack() ){
				webView.goBack();
				return true;
			}
		}
		return super.onKeyDown( keyCode, event );
	}

	public class MyCanvas extends _Canvas {
		_ScalableGraphics g;

		boolean first = true;

		@Override
		public int frameTime(){ return 100/*1000 / 10*/; }

		@Override
		public void init(){
			g = new _ScalableGraphics();
			g.setScale( (float)getWidth() / 480.0f );

			g.setGraphics( getGraphics() );
			g.setAntiAlias( true );
			g.setFontSize( FONT_SIZE );
			g.setStrokeWidth( 1.0f );

			if( first ){
				addWebView( g.scaledValue( LOG_HEIGHT + 3 + (FONT_SIZE + 3) * 4 ) );

				webView.clearCache();
//				webView.loadUrl( "http://www5d.biglobe.ne.jp/~satis/s/" );
				webView.loadFile( "index.html" );

				first = false;
			}
		}

		@Override
		public void paint( _Graphics _g ){
			g.setGraphics( _g );

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 128, 128, 255 ) );
			g.fillRect( 0, 0, g.getWidth(), g.getHeight() );
			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );

			g.drawLine( 0, g.getHeight() - (LOG_HEIGHT + 3 + (FONT_SIZE + 3) * 4), g.getWidth(), g.getHeight() - (LOG_HEIGHT + 3 + (FONT_SIZE + 3) * 4) );

			int w, x, y;

			w = g.stringWidth( userAgent );
			x = ((w - g.getWidth()) > 0) ? 0 - ((elapse * 3) % w) : 0;
			y = g.getHeight() - (LOG_HEIGHT + 3 + (FONT_SIZE + 3) * 3);
			g.drawString( userAgent, x, y );

			w = g.stringWidth( backUrl );
			x = ((w - g.getWidth()) > 0) ? 0 - ((elapse * 3) % w) : 0;
			y = g.getHeight() - (LOG_HEIGHT + 3 + (FONT_SIZE + 3) * 2);
			g.drawString( backUrl, x, y );

			w = g.stringWidth( url );
			x = ((w - g.getWidth()) > 0) ? 0 - ((elapse * 3) % w) : 0;
			y = g.getHeight() - (LOG_HEIGHT + 3 + FONT_SIZE + 3);
			g.drawString( url, x, y );

			w = g.stringWidth( forwardUrl );
			x = ((w - g.getWidth()) > 0) ? 0 - ((elapse * 3) % w) : 0;
			y = g.getHeight() - (LOG_HEIGHT + 3);
			g.drawString( forwardUrl, x, y );

			g.drawLine( 0, g.getHeight() - (LOG_HEIGHT + 3), g.getWidth(), g.getHeight() - (LOG_HEIGHT + 3) );

			String str;
			log.beginGet();
			while( (str = log.get()) != null ){
				g.drawString( str, 0, g.getHeight() - LOG_HEIGHT + (FONT_SIZE * log.lineNum()) );
			}

			g.unlock();

			elapse++;
		}
	}
}
