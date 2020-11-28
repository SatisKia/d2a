/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.gl;

import java.nio.*;
import javax.microedition.khronos.opengles.GL10;

public class _GLModel extends _GLPrimitive {
	// マテリアル
	public int _material_num = 0;
	public int[] _material_texture = null;
	public float[] _material_diffuse = null;	// R、G、B、Aを各 0～1
	public float[] _material_ambient = null;	// R、G、B、Aを各 0～1
	public float[] _material_emission = null;	// R、G、B、Aを各 0～1
	public float[] _material_specular = null;	// R、G、B、Aを各 0～1
	public float[] _material_shininess = null;	// 0～128

	// オブジェクト
	public int _object_num = 0;
	public float[][] _coord = null;		// X、Y、Z
	public float[][] _normal = null;	// X、Y、Z
	public float[][] _color = null;		// R、G、B、Aを各 0～1
	public float[][] _map = null;		// U、V

	// 三角形ストリップ
	public int _strip_num = 0;
	public int[] _strip_material = null;
	public int[] _strip_coord = null;
	public int[] _strip_normal = null;
	public int[] _strip_color = null;
	public int[] _strip_map = null;
	public int[] _strip_len = null;
	public short[][] _strip = null;

	private int _texture_env_mode = GL10.GL_MODULATE;

	public _GLModel( boolean depth ){
		setType( _GLPrimitive.TYPE_MODEL );
		setDepth( depth );
	}

	public void setMaterial( int num, int[] texture, float[] diffuse, float[] ambient, float[] emission, float[] specular, float[] shininess ){
		_material_num = num;
		_material_texture = texture;
		_material_diffuse = diffuse;
		_material_ambient = ambient;
		_material_emission = emission;
		_material_specular = specular;
		_material_shininess = shininess;
	}

	public void setObject( int num, float[][] coord, float[][] normal, float[][] color, float[][] map ){
		_object_num = num;
		_coord = coord;
		_normal = normal;
		_color = color;
		_map = map;
	}

	public void setStrip( int num, int[] material, int[] coord, int[] normal, int[] color, int[] map, int[] len, short[][] strip ){
		_strip_num = num;
		_strip_material = material;
		_strip_coord = coord;
		_strip_normal = normal;
		_strip_color = color;
		_strip_map = map;
		_strip_len = len;
		_strip = strip;
	}

	public void setTextureEnvMode( int mode ){
		_texture_env_mode = mode;
	}

	public int stripNum(){
		return _strip_num;
	}

	public int textureIndex( int index ){
		if( _strip_material[index] < 0 ){
			return -1;
		}
		return _material_texture[_strip_material[index]];
	}

	public boolean textureAlpha( _GLTexture glt, int index, int tex_index ){
		boolean alpha = false;
		boolean depth = depth();
		if( tex_index < 0 ){
			tex_index = textureIndex( index );
		}
		if( tex_index >= 0 ){
			glt.use( tex_index );
			glt.setTransparency( tex_index, transparency() );
			alpha = glt.alpha( tex_index );
			if( depth ){
				// モデル全体でデプスバッファ描き込みモードになっている場合のみ、
				// テクスチャ個別のモードを見る。
				depth = glt.depth( tex_index );
			}
		}
		return (alpha && !depth);
	}

	public void draw( GL10 gl, _GLTexture glt, int index, int tex_index, float[] mat, boolean alpha ){
		boolean alpha2 = textureAlpha( glt, index, tex_index );
		if( transparency() != 255 ){
			alpha2 = true;
		}
		if( alpha2 != alpha ){
			return;
		}

		if( _strip_coord[index] >= 0 ){
			gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );{
				FloatBuffer coord_buffer;
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _coord[_strip_coord[index]].length * 4 );
				byteBuffer.order( ByteOrder.nativeOrder() );
				coord_buffer = byteBuffer.asFloatBuffer();
				coord_buffer.put( _coord[_strip_coord[index]] );
				coord_buffer.position( 0 );
				gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, coord_buffer );
			}
		} else {
			gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		}

		if( (_normal != null) && (_strip_normal[index] >= 0) ){
			gl.glEnableClientState( GL10.GL_NORMAL_ARRAY );{
				FloatBuffer normal_buffer;
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _normal[_strip_normal[index]].length * 4 );
				byteBuffer.order( ByteOrder.nativeOrder() );
				normal_buffer = byteBuffer.asFloatBuffer();
				normal_buffer.put( _normal[_strip_normal[index]] );
				normal_buffer.position( 0 );
				gl.glNormalPointer( GL10.GL_FLOAT, 0, normal_buffer );
			}
		} else {
			gl.glDisableClientState( GL10.GL_NORMAL_ARRAY );
		}

		if( (_color != null) && (_strip_color[index] >= 0) ){
			gl.glEnableClientState( GL10.GL_COLOR_ARRAY );{
				FloatBuffer color_buffer;
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _color[_strip_color[index]].length * 4 );
				byteBuffer.order( ByteOrder.nativeOrder() );
				color_buffer = byteBuffer.asFloatBuffer();
				color_buffer.put( _color[_strip_color[index]] );
				color_buffer.position( 0 );
				gl.glColorPointer( 4, GL10.GL_FLOAT, 0, color_buffer );
			}
		} else {
			gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
		}

		if( tex_index < 0 ){
			tex_index = textureIndex( index );
		}
		if( !setTexture( gl, glt, index, tex_index ) ){
			if( (_map != null) && (_strip_map[index] >= 0) && (tex_index >= 0) ){
				gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
				gl.glEnable( GL10.GL_TEXTURE_2D );
				gl.glBindTexture( GL10.GL_TEXTURE_2D, glt.id( tex_index ) );{
					FloatBuffer map_buffer;
					ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _map[_strip_map[index]].length * 4 );
					byteBuffer.order( ByteOrder.nativeOrder() );
					map_buffer = byteBuffer.asFloatBuffer();
					map_buffer.put( _map[_strip_map[index]] );
					map_buffer.position( 0 );
					gl.glTexCoordPointer( 2, GL10.GL_FLOAT, 0, map_buffer );
				}
				gl.glTexEnvf( GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, _texture_env_mode );
			} else {
				gl.glDisableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
				gl.glDisable( GL10.GL_TEXTURE_2D );
			}
		}

		if( (_material_diffuse != null) && (_strip_material[index] >= 0) ){
			gl.glMaterialfv( GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, _material_diffuse, _strip_material[index] * 4 );
		}
		if( (_material_ambient != null) && (_strip_material[index] >= 0) ){
			gl.glMaterialfv( GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, _material_ambient, _strip_material[index] * 4 );
		}
		if( (_material_emission != null) && (_strip_material[index] >= 0) ){
			gl.glMaterialfv( GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, _material_emission, _strip_material[index] * 4 );
		}
		if( (_material_specular != null) && (_strip_material[index] >= 0) ){
			gl.glMaterialfv( GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, _material_specular, _strip_material[index] * 4 );
		}
		if( (_material_shininess != null) && (_strip_material[index] >= 0) ){
			gl.glMaterialf( GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, _material_shininess[_strip_material[index]] );
		}

		if( glt.alpha( tex_index ) ){
			gl.glEnable( GL10.GL_ALPHA_TEST );
		}
		if( alpha2 ){
			gl.glEnable( GL10.GL_BLEND );
			gl.glDepthMask( false );
		}

		gl.glPushMatrix();

		if( beginDraw( gl, glt, index, tex_index, mat ) ){{
				ShortBuffer strip_buffer;
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect( _strip[index].length * 2 );
				byteBuffer.order( ByteOrder.nativeOrder() );
				strip_buffer = byteBuffer.asShortBuffer();
				strip_buffer.put( _strip[index] );
				strip_buffer.position( 0 );
				gl.glDrawElements( GL10.GL_TRIANGLE_STRIP, _strip_len[index], GL10.GL_UNSIGNED_SHORT, strip_buffer );
			}

			endDraw( gl, glt, index, tex_index );
		}

		gl.glPopMatrix();

		if( glt.alpha( tex_index ) ){
			gl.glDisable( GL10.GL_ALPHA_TEST );
		}
		if( alpha2 ){
			gl.glDisable( GL10.GL_BLEND );
			gl.glDepthMask( true );
		}
	}

	public boolean setTexture( GL10 gl, _GLTexture glt, int index, int tex_index ){ return false; }
	public boolean beginDraw( GL10 gl, _GLTexture glt, int index, int tex_index, float[] mat ){
		multMatrix( gl, mat );
		return true;
	}
	public void endDraw( GL10 gl, _GLTexture glt, int index, int tex_index ){}
}
