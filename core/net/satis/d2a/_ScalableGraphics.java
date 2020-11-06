/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.graphics.*;

public class _ScalableGraphics extends _Scalable {
	private _Graphics _g;

	public void setGraphics( _Graphics g ){
		_g = g;
	}

	public int getWidth(){
		return (int)((float)_g.getWidth() / _scale);
	}
	public int getHeight(){
		return (int)((float)_g.getHeight() / _scale);
	}

	public void setAntiAlias( boolean aa ){
		_g.setAntiAlias( aa );
	}
	public void setStrokeWidth( float width ){
		_g.setStrokeWidth( width * _scale );
	}
	public void setColor( int color ){
		_g.setColor( color );
	}
	public void setAlpha( int a ){
		_g.setAlpha( a );
	}
	public void setROP( int mode ){
		_g.setROP( mode );
	}
	public void setFlipMode( int flipmode ){
		_g.setFlipMode( flipmode );
	}
	public void setOrigin( int x, int y ){
		_g.setOrigin( scaledValue( x ), scaledValue( y ) );
	}
	public void setFontSize( int size ){
		_g.setFontSize( scaledValue( size ) );
	}
	public int stringWidth( String str ){
		if( _scale == 0.0f ){
			return 0;
		}
		return (int)((float)_g.stringWidth( str ) / _scale);
	}
	public int fontHeight(){
		if( _scale == 0.0f ){
			return 0;
		}
		return (int)((float)_g.fontHeight() / _scale);
	}
	public void lock(){
		_g.lock();
	}
	public void unlock(){
		_g.unlock();
	}
	public Canvas getCanvas(){
		return _g.getCanvas();
	}
	public Paint getPaint(){
		return _g.getPaint();
	}
	public void setClip( int x, int y, int width, int height ){
		_g.setClip( scaledValue( x ), scaledValue( y ), scaledValue( width ), scaledValue( height ) );
	}
	public void clearClip(){
		_g.clearClip();
	}
	public void drawLine( int x1, int y1, int x2, int y2 ){
		_g.drawLine( scaledValue( x1 ), scaledValue( y1 ), scaledValue( x2 ), scaledValue( y2 ) );
	}
	public void drawRect( int x, int y, int width, int height ){
		_g.drawRect( scaledValue( x ), scaledValue( y ), scaledValue( width ), scaledValue( height ) );
	}
	public void fillRect( int x, int y, int width, int height ){
		_g.fillRect( scaledValue( x ), scaledValue( y ), scaledValue( width ), scaledValue( height ) );
	}
	public void drawRoundRect( int x, int y, int width, int height, int rx, int ry ){
		_g.drawRoundRect( scaledValue( x ), scaledValue( y ), scaledValue( width ), scaledValue( height ), scaledValue( rx ), scaledValue( ry ) );
	}
	public void fillRoundRect( int x, int y, int width, int height, int rx, int ry ){
		_g.fillRoundRect( scaledValue( x ), scaledValue( y ), scaledValue( width ), scaledValue( height ), scaledValue( rx ), scaledValue( ry ) );
	}
	public void drawOval( int x, int y, int width, int height ){
		_g.drawOval( scaledValue( x ), scaledValue( y ), scaledValue( width ), scaledValue( height ) );
	}
	public void fillOval( int x, int y, int width, int height ){
		_g.fillOval( scaledValue( x ), scaledValue( y ), scaledValue( width ), scaledValue( height ) );
	}
	public void drawCircle( int cx, int cy, int r ){
		_g.drawCircle( scaledValue( cx ), scaledValue( cy ), scaledValue( r ) );
	}
	public void fillCircle( int cx, int cy, int r ){
		_g.fillCircle( scaledValue( cx ), scaledValue( cy ), scaledValue( r ) );
	}
	public void drawString( String str, int x, int y ){
		_g.drawString( str, scaledValue( x ), scaledValue( y ) );
	}
	public void drawImage( _Image image, int x, int y ){
		int width  = image.getWidth();
		int height = image.getHeight();
		_g.drawScaledImage( image, scaledValue( x ), scaledValue( y ), scaledValue( width ), scaledValue( height ), 0, 0, width, height );
	}
	public void drawImage( _Image image, int dx, int dy, int sx, int sy, int width, int height ){
		_g.drawScaledImage( image, scaledValue( dx ), scaledValue( dy ), scaledValue( width ), scaledValue( height ), sx, sy, width, height );
	}
	public void drawScaledImage( _Image image, int dx, int dy, int width, int height, int sx, int sy, int swidth, int sheight ){
		_g.drawScaledImage( image, scaledValue( dx ), scaledValue( dy ), scaledValue( width ), scaledValue( height ), sx, sy, swidth, sheight );
	}
	public void drawTransImage( _Image image, float dx, float dy, int sx, int sy, int width, int height, float cx, float cy, float r360, float z128x, float z128y ){
		_g.drawTransImage( image, dx * _scale, dy * _scale, sx, sy, width, height, cx, cy, r360, z128x * _scale, z128y * _scale );
	}
}
