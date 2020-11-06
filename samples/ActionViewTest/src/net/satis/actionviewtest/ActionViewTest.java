package net.satis.actionviewtest;

import net.satis.d2a.*;

import android.content.Intent;
import android.net.Uri;

public class ActionViewTest extends _Main {
	@Override
	public void start(){
		Uri uri = Uri.parse( "http://www5d.biglobe.ne.jp/~satis/s/" );
		Intent target = new Intent( Intent.ACTION_VIEW, uri );
		startActivity( target );
		terminate();
	}
}
