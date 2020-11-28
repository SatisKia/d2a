package net.satis.httptest;

import java.io.InputStream;
import java.io.InputStreamReader;
import net.satis.d2a.*;

public class HttpTest extends _Main {
	_HttpRequest http;

	String server;

	String str1;
	String str2;

	int step = 0;
	int elapse = 0;

	@Override
	public void start(){
		setCurrent( new MyCanvas() );

		http = new _HttpRequest( this );

		server = getResString( R.string.server );

		str1 = new String( "" );
		str2 = new String( "" );
	}

	@Override
	public void onHttpResponse( InputStream is ){
		str2 = "";
		if( is != null ){
			try {
				InputStreamReader reader = new InputStreamReader( is );
				StringBuilder builder = new StringBuilder();
				char[] buf = new char[1024];
				int len;
				while( (len = reader.read( buf )) > 0 ){
					builder.append( buf, 0, len );
				}
				str2 = builder.toString();
			} catch( Exception e ){}
		}
	}

	@Override
	public void onHttpError( int status ){
		String tmp = new String( "" );
		if( status == _HttpRequest.CLIENTPROTOCOL_ERROR ){
			tmp = "CLIENTPROTOCOL_ERROR";
		} else if( status == _HttpRequest.IO_ERROR ){
			tmp = "IO_ERROR";
		} else {
			tmp = "" + status;
		}
		str2 = "通信エラー " + tmp;
	}

	public class MyCanvas extends _Canvas {
		@Override
		public int frameTime(){ return 100/*1000 / 10*/; }

		@Override
		public void init(){
			_Graphics g = getGraphics();
			g.setAntiAlias( true );
			g.setFontSize( 24 );
		}

		@Override
		public void paint( _Graphics g ){
			elapse++;

			g.lock();

			g.setColor( _Graphics.getColorOfRGB( 255, 255, 255 ) );
			g.fillRect( 0, 0, getWidth(), getHeight() );

			g.setColor( _Graphics.getColorOfRGB( 0, 0, 255 ) );

			if( http.busy() ){
				switch( elapse % 3 ){
				case 0: g.drawString( "通信中."  , 0, 30 ); break;
				case 1: g.drawString( "通信中.." , 0, 30 ); break;
				case 2: g.drawString( "通信中...", 0, 30 ); break;
				}
			} else {
				g.drawString( "タッチしてください", 0, 30 );
			}

			g.drawString( "通信URL", 0, 90 );
			g.drawString( server, 0, 120 );
			g.drawString( str1, 0, 150 );

			g.drawString( "レスポンス", 0, 210 );
			g.drawString( str2, 0, 240 );

			g.unlock();
		}

		@Override
		public void processEvent( int type, int param ){
			if( http.busy() ){
				return;
			}

			if( type == TOUCH_DOWN_EVENT ){
				switch( step ){
				case 0:
					str1 = "test1.php?user=guest";
					http.get( server + str1 );
					step = 1;
					break;
				case 1:
					str1 = "test2.php";
					{
						String[] vars = new String[2];
						vars[0] = new String( "user" );
						vars[1] = new String( "guest" );
						http.post( server + str1, vars, "UTF-8" );
					}
					step = 2;
					break;
				case 2:
					str1 = "test3.php?user=guest";
					http.get( server + str1 );
					step = 3;
					break;
				case 3:
					str1 = "test4.php";
					{
						String[] vars = new String[2];
						vars[0] = new String( "user" );
						vars[1] = new String( "guest" );
						http.post( server + str1, vars, "UTF-8" );
					}
					step = 0;
					break;
				}
			}
		}
	}
}
