/*
 * D2A
 * Copyright (C) SatisKia. All rights reserved.
 */

package net.satis.d2a.extras;

import java.nio.*;
import java.nio.charset.*;

public class _StringUtil {
	public static String decode( byte[] sjisBytes, int offset, int length ){
		byte[] data = new byte[length];
		for( int i = 0; i < length; i++ ){
			data[i] = sjisBytes[offset + i];
		}
		ByteBuffer bb = ByteBuffer.wrap( data );
		Charset charset = Charset.forName( "Shift_JIS" );
		CharsetDecoder d = charset.newDecoder();
		CharBuffer cb = CharBuffer.allocate( length );
		d.decode( bb, cb, true );
		bb.rewind();
		cb.rewind();
		return cb.toString();
	}
}
