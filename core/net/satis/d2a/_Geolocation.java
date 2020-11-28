/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.os.Handler;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class _Geolocation {
	public static final int PROVIDER_GPS     = 0x01;
	public static final int PROVIDER_NETWORK = 0x02;
	public static final int PROVIDER_BEST    = (PROVIDER_GPS | PROVIDER_NETWORK);

	public static final int ERROR                = 0;
	public static final int PERMISSION_DENIED    = 1;	// 位置情報の利用が許可されていない
	public static final int POSITION_UNAVAILABLE = 2;	// デバイスの位置が判定できない
	public static final int TIMEOUT              = 3;	// タイムアウト
	public static final int AVAILABLE            = 10;
	public static final int SUCCESS              = 11;
	public static final int LAST_KNOWN           = 12;

	private _Main _m;
	private LocationManager _manager = null;
	private LocationListener _listener = null;
	private Timer _timer = null;
	private long _time;

	private boolean _altitudeRequired = true;
	private boolean _headingRequired = true;
	private boolean _speedRequired = true;
	private long _maximumAge = 0L;
	private long _timeout = -1L;

	private double _latitude = 0.0;
	private double _longitude = 0.0;
	private float _accuracy = 0.0f;
	private double _altitude = 0.0;
	private boolean _hasAltitude = false;
	private float _heading = 0.0f;
	private boolean _hasHeading = false;
	private float _speed = 0.0f;
	private boolean _hasSpeed = false;
	private long _timestamp = 0L;

	public _Geolocation( _Main m ){
		_m = m;
	}

	public String start( int providerType ){
		stop();

		_manager = (LocationManager)_m.getSystemService( Context.LOCATION_SERVICE );
		if( _manager == null ){
			_m.onGeolocation( ERROR );
			return "";
		}

		String provider = new String( "" );

		switch( providerType ){
		case PROVIDER_GPS:
			provider = LocationManager.GPS_PROVIDER;
			break;
		case PROVIDER_NETWORK:
			provider = LocationManager.NETWORK_PROVIDER;
			break;
		case PROVIDER_BEST:
			{
				Criteria criteria = new Criteria();
				criteria.setAltitudeRequired( _altitudeRequired );
				criteria.setBearingRequired( _headingRequired );
				criteria.setSpeedRequired( _speedRequired );
				String bestProvider = _manager.getBestProvider( criteria, true );
				if( bestProvider == null ){
					stop();
					_m.onGeolocation( PERMISSION_DENIED );
					return "";
				}
				provider = bestProvider;
			}
			break;
		default:
			stop();
			_m.onGeolocation( ERROR );
			return "";
		}

		boolean enabled = false;
		try {
			enabled = _manager.isProviderEnabled( provider );
		} catch( Exception e ){}
		if( !enabled ){
			stop();
			_m.onGeolocation( PERMISSION_DENIED );
			return "";
		}

		// 前回の取得位置情報が時間内のものであれば有効
		Location lastKnownLocation = _manager.getLastKnownLocation( provider );
		if( (lastKnownLocation != null) && (((new Date()).getTime() - lastKnownLocation.getTime()) <= _maximumAge) ){
			success( LAST_KNOWN, lastKnownLocation );
			return provider;
		}

		_timer = new Timer( true );
		_time = 0L;
		final Handler handler = new Handler();
		_timer.scheduleAtFixedRate( new TimerTask(){
			@Override
			public void run(){
				handler.post( new Runnable(){
					@Override
					public void run(){
						if( (_timeout >= 0L) && (_time >= _timeout) ){
							stop();
							_m.onGeolocation( TIMEOUT );
						}
						_time = _time + 1000L;
					}
				} );
			}
		}, 0L, 1000L );

		// 位置情報の取得を開始
		_listener = new LocationListener(){
			@Override
			public void onLocationChanged( Location location ){
				success( SUCCESS, location );
			}
			@Override
			public void onProviderDisabled( String provider ){
			}
			@Override
			public void onProviderEnabled( String provider ){
			}
			@Override
			public void onStatusChanged( String provider, int status, Bundle extras ){
				if( status == LocationProvider.AVAILABLE ){
					_m.onGeolocation( AVAILABLE );
				} else if( status == LocationProvider.OUT_OF_SERVICE ){
					_m.onGeolocation( POSITION_UNAVAILABLE );
				} else if( status == LocationProvider.TEMPORARILY_UNAVAILABLE ){
					_m.onGeolocation( POSITION_UNAVAILABLE );
				}
			}
		};
		_manager.requestLocationUpdates( provider, 0, 0, _listener );
		return provider;
	}

	public void stop(){
		if( _timer != null ){
			_timer.cancel();
			_timer.purge();
			_timer = null;
		}
		if( _manager != null ){
			if( _listener != null ){
				_manager.removeUpdates( _listener );
				_listener = null;
			}
			_manager = null;
		}
	}

	private void success( int code, Location location ){
		_latitude    = location.getLatitude();
		_longitude   = location.getLongitude();
		_accuracy    = location.getAccuracy();
		_altitude    = location.getAltitude();
		_hasAltitude = location.hasAltitude();
		_heading     = location.getBearing();
		_hasHeading  = location.hasBearing();
		_speed       = location.getSpeed();
		_hasSpeed    = location.hasSpeed();
		_timestamp   = location.getTime();

		_m.onGeolocation( code );
	}

	public void setAltitudeRequired( boolean altitudeRequired ){
		_altitudeRequired = altitudeRequired;
	}
	public void setHeadingRequired( boolean headingRequired ){
		_headingRequired = headingRequired;
	}
	public void setSpeedRequired( boolean speedRequired ){
		_speedRequired = speedRequired;
	}
	public void setMaximumAgeSeconds( int maximumAge ){
		_maximumAge = (long)maximumAge * 1000L;
	}
	public void setTimeoutSeconds( int timeout ){
		_timeout = (long)timeout * 1000L;
	}

	public double latitude(){
		return _latitude;
	}
	public double longitude(){
		return _longitude;
	}
	public float accuracy(){
		return _accuracy;
	}
	public double altitude(){
		return _altitude;
	}
	public boolean hasAltitude(){
		return _hasAltitude;
	}
	public float heading(){
		return _heading;
	}
	public boolean hasHeading(){
		return _hasHeading;
	}
	public float speed(){
		return _speed;
	}
	public boolean hasSpeed(){
		return _hasSpeed;
	}
	public long timestamp(){
		return _timestamp;
	}
}
