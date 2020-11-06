/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.List;

public class _Sensor implements SensorEventListener {
	private SensorManager _manager = null;
	private Sensor _accelerometer = null;
	private Sensor _magnetic = null;
//	private Sensor _orientation = null;

	private static final float ALPHA = 0.8f;
	private boolean _accel = false;
	private float _accel_x = 0.0f;
	private float _accel_y = 0.0f;
	private float _accel_z = 0.0f;
	private float _gravity_x = 0.0f;
	private float _gravity_y = 0.0f;
	private float _gravity_z = 0.0f;
	private float _linear_accel_x = 0.0f;
	private float _linear_accel_y = 0.0f;
	private float _linear_accel_z = 0.0f;
	private float _azimuth = 0.0f;
	private float _pitch = 0.0f;
	private float _roll = 0.0f;

	public void start( _Main m ){
		if( _manager == null ){
			_manager = (SensorManager)m.getSystemService( Context.SENSOR_SERVICE );

			List<Sensor> list;
			list = _manager.getSensorList( Sensor.TYPE_ACCELEROMETER );
			if( list.size() > 0 ){
				_accelerometer = list.get( 0 );
			}
			list = _manager.getSensorList( Sensor.TYPE_MAGNETIC_FIELD );
			if( list.size() > 0 ){
				_magnetic = list.get( 0 );
			}
//			list = _manager.getSensorList( Sensor.TYPE_ORIENTATION );
//			if( list.size() > 0 ){
//				_orientation = list.get( 0 );
//			}
		}

		restart();
	}

	public void stop(){
		if( _manager != null ){
			_manager.unregisterListener( this );
		}
	}

	public void restart(){
		_accel = false;
		_accel_x = 0.0f;
		_accel_y = 0.0f;
		_accel_z = 0.0f;
		_gravity_x = 0.0f;
		_gravity_y = 0.0f;
		_gravity_z = 0.0f;
		_linear_accel_x = 0.0f;
		_linear_accel_y = 0.0f;
		_linear_accel_z = 0.0f;
		_azimuth = 0.0f;
		_pitch   = 0.0f;
		_roll    = 0.0f;

		if( _manager != null ){
			if( _accelerometer != null ){
				_manager.registerListener( this, _accelerometer, SensorManager.SENSOR_DELAY_FASTEST );
			}
			if( _magnetic != null ){
				_manager.registerListener( this, _magnetic, SensorManager.SENSOR_DELAY_FASTEST );
			}
//			if( _orientation != null ){
//				_manager.registerListener( this, _orientation, SensorManager.SENSOR_DELAY_FASTEST );
//			}
		}
	}

	public void onSensorChanged( SensorEvent event ){
		if( event.sensor == _accelerometer ){
			_accel = true;
			_accel_x = event.values[0];
			_accel_y = event.values[1];
			_accel_z = event.values[2];
			_gravity_x = ALPHA * _gravity_x + (1.0f - ALPHA) * _accel_x;
			_gravity_y = ALPHA * _gravity_y + (1.0f - ALPHA) * _accel_y;
			_gravity_z = ALPHA * _gravity_z + (1.0f - ALPHA) * _accel_z;
			_linear_accel_x = _accel_x - _gravity_x;
			_linear_accel_y = _accel_y - _gravity_y;
			_linear_accel_z = _accel_z - _gravity_z;
		}
		if( event.sensor == _magnetic ){
			if( _accel ){
				float[] inR = new float[16];
				float[] outR = new float[16];
				float[] I = new float[16];
				float[] accel = new float[3];
				float[] orientation = new float[3];
				accel[0] = _accel_x;
				accel[1] = _accel_y;
				accel[2] = _accel_z;
				SensorManager.getRotationMatrix( inR, I, accel, event.values );
				SensorManager.remapCoordinateSystem( inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR );
				SensorManager.getOrientation( outR, orientation );
				_azimuth = orientation[0] * 180.0f / 3.14159265358979323846264f;
				_pitch   = orientation[1] * 180.0f / 3.14159265358979323846264f;
				_roll    = orientation[2] * 180.0f / 3.14159265358979323846264f;
				if( _azimuth < 0.0f ){
					_azimuth += 360.0f;
				}
				_roll = 0.0f - _roll;
			}
		}
//		if( event.sensor == _orientation ){
//			_azimuth = event.values[0];
//			_pitch   = event.values[1];
//			_roll    = event.values[2];
//		}
	}

	public void onAccuracyChanged( Sensor sensor, int accuracy ){
	}

	public float getAccelX(){
		return _accel_x;
	}
	public float getAccelY(){
		return _accel_y;
	}
	public float getAccelZ(){
		return _accel_z;
	}

	public float getGravityX(){
		return _gravity_x;
	}
	public float getGravityY(){
		return _gravity_y;
	}
	public float getGravityZ(){
		return _gravity_z;
	}

	public float getLinearAccelX(){
		return _linear_accel_x;
	}
	public float getLinearAccelY(){
		return _linear_accel_y;
	}
	public float getLinearAccelZ(){
		return _linear_accel_z;
	}

	public float getAzimuth(){
		return _azimuth;
	}
	public float getPitch(){
		return _pitch;
	}
	public float getRoll(){
		return _roll;
	}
}
