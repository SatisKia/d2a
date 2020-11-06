package net.satis.speeder;

import android.content.res.*;
import java.util.*;
import net.satis.d2a.*;

/**
 * メイン
 */
public class Speeder extends _Main {
	public int orientation(){ return ORIENTATION_PORTRAIT; }

	// ゴールまでの距離
	public static final int DISTANCE				= 1000000;

	// アプリの状態
	public static final int STATE_LAUNCH			= -1;
	public static final int STATE_TITLE				= 0;
	public static final int STATE_TITLE_LOADING		= 1;
	public static final int STATE_SELECT			= 3;
	public static final int STATE_SELECT_LOADING	= 4;
	public static final int STATE_READY				= 5;
	public static final int STATE_PLAY				= 6;
	public static final int STATE_CLEAR				= 7;
	public static final int STATE_STOP				= 8;

	// 1フレームの時間(ミリ秒)
	public static final int FRAME_TIME				= 65;

	// 待ちフレーム数
	public static final int WAIT_BOOST				= 15;
	public static final int WAIT_1					= 30;
	public static final int WAIT_2					= 60;
	public static final int WAIT_3					= 90;

	// 画像の種類
	public static final int IMAGE_BACK				= 0;
	public static final int IMAGE_BAR				= 1;
	public static final int IMAGE_FORE1				= 2;
	public static final int IMAGE_FORE2				= 3;
	public static final int IMAGE_FORE3				= 4;
	public static final int IMAGE_FORE4				= 5;
	public static final int IMAGE_FORE5A			= 6;
	public static final int IMAGE_FORE5B			= 7;
	public static final int IMAGE_FORE6A			= 8;
	public static final int IMAGE_FORE6B			= 9;
	public static final int IMAGE_LOGO				= 10;
	public static final int IMAGE_SHIELD			= 11;
	public static final int IMAGE_SPEEDER1			= 12;
	public static final int IMAGE_SPEEDER2			= 13;
	public static final int IMAGE_SPEEDER3			= 14;
	public static final int IMAGE_STATUS			= 15;
	public static final int IMAGE_TITLE				= 16;
	public static final int IMAGE_RAY				= 17;
	public static final int IMAGE_COM				= 18;
	public static final int IMAGE_RAX				= 19;
	public static final int IMAGE_NUM				= 20;	// 画像の数

	// タイトル画面テキスト情報
	public static final int TEXT_NEUTRAL_X			= 0;
	public static final int TEXT_NEUTRAL_Y			= 12;
	public static final int TEXT_NEUTRAL_W			= 74;
	public static final int TEXT_NEUTRAL_H			= 10;
	public static final int TEXT_ON_X				= 0;
	public static final int TEXT_ON_Y				= 22;
	public static final int TEXT_ON_W				= 20;
	public static final int TEXT_ON_H				= 10;
	public static final int TEXT_OFF_X				= 22;
	public static final int TEXT_OFF_Y				= 22;
	public static final int TEXT_OFF_W				= 28;
	public static final int TEXT_OFF_H				= 10;
	public static final int TEXT_RACE_X				= 0;
	public static final int TEXT_RACE_Y				= 32;
	public static final int TEXT_RACE_W				= 38;
	public static final int TEXT_RACE_H				= 10;
	public static final int TEXT_FREE_X				= 40;
	public static final int TEXT_FREE_Y				= 32;
	public static final int TEXT_FREE_W				= 38;
	public static final int TEXT_FREE_H				= 10;
	public static final int TEXT_TRAINING_X			= 80;
	public static final int TEXT_TRAINING_Y			= 32;
	public static final int TEXT_TRAINING_W			= 80;
	public static final int TEXT_TRAINING_H			= 10;
	public static final int TEXT_EASY_X				= 0;
	public static final int TEXT_EASY_Y				= 42;
	public static final int TEXT_EASY_W				= 40;
	public static final int TEXT_EASY_H				= 10;
	public static final int TEXT_HARD_X				= 42;
	public static final int TEXT_HARD_Y				= 42;
	public static final int TEXT_HARD_W				= 40;
	public static final int TEXT_HARD_H				= 10;
	public static final int TEXT_1_X				= 84;
	public static final int TEXT_1_Y				= 42;
	public static final int TEXT_1_W				= 8;
	public static final int TEXT_1_H				= 10;
	public static final int TEXT_2_X				= 94;
	public static final int TEXT_2_Y				= 42;
	public static final int TEXT_2_W				= 8;
	public static final int TEXT_2_H				= 10;
	public static final int TEXT_OMAKE_X			= 104;
	public static final int TEXT_OMAKE_Y			= 42;
	public static final int TEXT_OMAKE_W			= 52;
	public static final int TEXT_OMAKE_H			= 10;
	public static final int TEXT_BESTTIME_X			= 0;
	public static final int TEXT_BESTTIME_Y			= 52;
	public static final int TEXT_BESTTIME_W			= 90;
	public static final int TEXT_BESTTIME_H			= 10;
	public static final int TEXT_DISTANCE_X			= 0;
	public static final int TEXT_DISTANCE_Y			= 62;
	public static final int TEXT_DISTANCE_W			= 80;
	public static final int TEXT_DISTANCE_H			= 10;
	public static final int TEXT_PRESS_X			= 0;
	public static final int TEXT_PRESS_Y			= 72;
	public static final int TEXT_PRESS_W			= 48;
	public static final int TEXT_PRESS_H			= 10;
	public static final int TEXT_KEY_X				= 50;
	public static final int TEXT_KEY_Y				= 72;
	public static final int TEXT_KEY_W				= 30;
	public static final int TEXT_KEY_H				= 10;
	public static final int TEXT_ENTER_X			= 0;
	public static final int TEXT_ENTER_Y			= 82;
	public static final int TEXT_ENTER_W			= 52;
	public static final int TEXT_ENTER_H			= 10;
	public static final int TEXT_SELECT_X			= 54;
	public static final int TEXT_SELECT_Y			= 82;
	public static final int TEXT_SELECT_W			= 60;
	public static final int TEXT_SELECT_H			= 10;
	public static final int TEXT_LOADING_X			= 0;
	public static final int TEXT_LOADING_Y			= 92;
	public static final int TEXT_LOADING_W			= 86;
	public static final int TEXT_LOADING_H			= 10;
	public static final int TEXT_COPYRIGHT_X		= 0;
	public static final int TEXT_COPYRIGHT_Y		= 102;
	public static final int TEXT_COPYRIGHT_W		= 92;
	public static final int TEXT_COPYRIGHT_H		= 10;
	public static final int TEXT_COPYRIGHT2_X		= 0;
	public static final int TEXT_COPYRIGHT2_Y		= 112;
	public static final int TEXT_COPYRIGHT2_W		= 94;
	public static final int TEXT_COPYRIGHT2_H		= 10;
	public static final int TEXT_SELECTCHAR_X		= 0;
	public static final int TEXT_SELECTCHAR_Y		= 122;
	public static final int TEXT_SELECTCHAR_W		= 160;
	public static final int TEXT_SELECTCHAR_H		= 10;
	public static final int TEXT_RAY_X				= 0;
	public static final int TEXT_RAY_Y				= 132;
	public static final int TEXT_RAY_W				= 30;
	public static final int TEXT_RAY_H				= 10;
	public static final int TEXT_RAX_X				= 32;
	public static final int TEXT_RAX_Y				= 132;
	public static final int TEXT_RAX_W				= 30;
	public static final int TEXT_RAX_H				= 10;
	public static final int TEXT_COM_X				= 64;
	public static final int TEXT_COM_Y				= 132;
	public static final int TEXT_COM_W				= 32;
	public static final int TEXT_COM_H				= 10;
	public static final int TEXT_ACCELERATION_X		= 0;
	public static final int TEXT_ACCELERATION_Y		= 142;
	public static final int TEXT_ACCELERATION_W		= 120;
	public static final int TEXT_ACCELERATION_H		= 10;
	public static final int TEXT_FAST_X				= 0;
	public static final int TEXT_FAST_Y				= 152;
	public static final int TEXT_FAST_W				= 40;
	public static final int TEXT_FAST_H				= 10;
	public static final int TEXT_MIDDLE_X			= 42;
	public static final int TEXT_MIDDLE_Y			= 152;
	public static final int TEXT_MIDDLE_W			= 60;
	public static final int TEXT_MIDDLE_H			= 10;
	public static final int TEXT_SLOW_X				= 104;
	public static final int TEXT_SLOW_Y				= 152;
	public static final int TEXT_SLOW_W				= 42;
	public static final int TEXT_SLOW_H				= 10;
	public static final int TEXT_SLOWDOWN_X			= 0;
	public static final int TEXT_SLOWDOWN_Y			= 162;
	public static final int TEXT_SLOWDOWN_W			= 88;
	public static final int TEXT_SLOWDOWN_H			= 10;
	public static final int TEXT_NORMAL_X			= 0;
	public static final int TEXT_NORMAL_Y			= 172;
	public static final int TEXT_NORMAL_W			= 64;
	public static final int TEXT_NORMAL_H			= 10;
	public static final int TEXT_SLIGHT_X			= 66;
	public static final int TEXT_SLIGHT_Y			= 172;
	public static final int TEXT_SLIGHT_W			= 60;
	public static final int TEXT_SLIGHT_H			= 10;
	public static final int TEXT_STEERING_X			= 0;
	public static final int TEXT_STEERING_Y			= 182;
	public static final int TEXT_STEERING_W			= 80;
	public static final int TEXT_STEERING_H			= 10;
	public static final int TEXT_QUICK_X			= 0;
	public static final int TEXT_QUICK_Y			= 192;
	public static final int TEXT_QUICK_W			= 50;
	public static final int TEXT_QUICK_H			= 10;
	public static final int TEXT_SENSOR_X			= 0;
	public static final int TEXT_SENSOR_Y			= 202;
	public static final int TEXT_SENSOR_W			= 60;
	public static final int TEXT_SENSOR_H			= 10;
	public static final int TEXT_BUTTON_X			= 82;
	public static final int TEXT_BUTTON_Y			= 72;
	public static final int TEXT_BUTTON_W			= 66;
	public static final int TEXT_BUTTON_H			= 10;

	// ステータステキスト情報
	public static final int TEXT_READY_X			= 0;
	public static final int TEXT_READY_Y			= 74;
	public static final int TEXT_READY_W			= 101;
	public static final int TEXT_READY_H			= 21;
	public static final int TEXT_START_X			= 0;
	public static final int TEXT_START_Y			= 95;
	public static final int TEXT_START_W			= 119;
	public static final int TEXT_START_H			= 21;
	public static final int TEXT_FINISH_X			= 0;
	public static final int TEXT_FINISH_Y			= 116;
	public static final int TEXT_FINISH_W			= 131;
	public static final int TEXT_FINISH_H			= 21;
	public static final int TEXT_STOP_X				= 0;
	public static final int TEXT_STOP_Y				= 137;
	public static final int TEXT_STOP_W				= 81;
	public static final int TEXT_STOP_H				= 21;
	public static final int TEXT_PED_X				= 0;
	public static final int TEXT_PED_Y				= 158;
	public static final int TEXT_PED_W				= 71;
	public static final int TEXT_PED_H				= 21;
	public static final int TEXT_1ST_X				= 71;
	public static final int TEXT_1ST_Y				= 158;
	public static final int TEXT_1ST_W				= 52;
	public static final int TEXT_1ST_H				= 31;
	public static final int TEXT_2ND_X				= 0;
	public static final int TEXT_2ND_Y				= 189;
	public static final int TEXT_2ND_W				= 58;
	public static final int TEXT_2ND_H				= 31;
	public static final int TEXT_3RD_X				= 58;
	public static final int TEXT_3RD_Y				= 189;
	public static final int TEXT_3RD_W				= 55;
	public static final int TEXT_3RD_H				= 31;
	public static final int TEXT_PAUSE_X			= 0;
	public static final int TEXT_PAUSE_Y			= 220;
	public static final int TEXT_PAUSE_W			= 51;
	public static final int TEXT_PAUSE_H			= 11;
	public static final int TEXT_NEWRECORD_X		= 0;
	public static final int TEXT_NEWRECORD_Y		= 231;
	public static final int TEXT_NEWRECORD_W		= 109;
	public static final int TEXT_NEWRECORD_H		= 11;
	public static final int TEXT_AUTOSTEERING_X		= 0;
	public static final int TEXT_AUTOSTEERING_Y		= 242;
	public static final int TEXT_AUTOSTEERING_W		= 131;
	public static final int TEXT_AUTOSTEERING_H		= 11;
	public static final int TEXT_AUTOSHIELD_X		= 0;
	public static final int TEXT_AUTOSHIELD_Y		= 253;
	public static final int TEXT_AUTOSHIELD_W		= 109;
	public static final int TEXT_AUTOSHIELD_H		= 11;

	// スピーダーの種類
	public static final int SPEEDER1				= 0;
	public static final int SPEEDER2				= 1;
	public static final int SPEEDER3				= 2;
	public static final int SPEEDER4				= 3;
	public static final int SPEEDER5				= 4;

	// 自動移動の種類
	public static final int AUTO_INERTIA			= 0;
	public static final int AUTO_NEUTRAL			= 1;
	public static final int AUTO_MOVED_INERTIA		= 2;
	public static final int AUTO_MOVED_NEUTRAL		= 3;

	public static final int MYLAYOUT_LEFT			= 0;
	public static final int MYLAYOUT_RIGHT			= 1;
	public static final int MYLAYOUT_UP				= 2;
	public static final int MYLAYOUT_DOWN			= 3;
	public static final int MYLAYOUT_SELECT			= 4;
	public static final int MYLAYOUT_BACK			= 5;
	public static final int MYLAYOUT_PAUSE			= 6;
	public static final int MYLAYOUT_SHIELD_0		= 7;
	public static final int MYLAYOUT_SHIELD_1		= 8;
	public static final int MYLAYOUT_SHIELD_2		= 9;
	public static final int MYLAYOUT_LEFT1			= 10;
	public static final int MYLAYOUT_RIGHT1			= 11;
	public static final int MYLAYOUT_LEFT2			= 12;
	public static final int MYLAYOUT_RIGHT2			= 13;
	public static final int MYLAYOUT_LEFT3			= 14;
	public static final int MYLAYOUT_RIGHT3			= 15;

	Resources r;

	public static MainCanvas canvas;
	public static _Image window;
	public static _Graphics g;

	public static Random rand;
	public static Stage stage;
	public static Wave wave;
	public static MySpeeder[] speeder;

	// よく使う色
	public static final int COLOR_C = _Graphics.getColorOfRGB(   0, 255, 255 );
	public static final int COLOR_M = _Graphics.getColorOfRGB( 255,   0, 255 );
	public static final int COLOR_Y = _Graphics.getColorOfRGB( 255, 255,   0 );
	public static final int COLOR_K = _Graphics.getColorOfRGB(   0,   0,   0 );
	public static final int COLOR_W = _Graphics.getColorOfRGB( 255, 255, 255 );

	public static final int[] SPEEDER1_X = { 0, 17, 34, 51, 68, 85, 103, 121, 140, 160, 181, 202, 224, 247, 270, 294 };
	public static final int[] SPEEDER1_X_M = { 318, 301, 284, 267, 250, 232, 214, 195, 175, 154, 133, 111, 88, 65, 41, 17 };
	public static final int[] SPEEDER1_W = { 17, 17, 17, 17, 17, 18, 18, 19, 20, 21, 21, 22, 23, 23, 24, 24 };

	public static final int[] SPEEDER2_X = { 0, 17, 34, 51, 68, 86, 105, 124, 144, 164, 185, 206, 228, 250, 273, 296 };
	public static final int[] SPEEDER2_X_M = { 320, 303, 286, 269, 251, 232, 213, 193, 173, 152, 131, 109, 87, 64, 41, 17 };
	public static final int[] SPEEDER2_W = { 17, 17, 17, 17, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24 };

	public static final int[] SPEEDER3_X = { 0, 17, 34, 51, 68, 85, 103, 121, 140, 160, 181, 202, 224, 246, 269, 292 };
	public static final int[] SPEEDER3_X_M = { 315, 298, 281, 264, 247, 229, 211, 192, 172, 151, 130, 108, 86, 63, 40, 17 };
	public static final int[] SPEEDER3_W = { 17, 17, 17, 17, 17, 18, 18, 19, 20, 21, 21, 22, 22, 23, 23, 23 };

	int load_cnt;				// ロード済みデータ数
	int load_num;				// ロード数

	int state = STATE_LAUNCH;	// アプリの状態
	int help;					// ヘルプの種類
	int help_back = -1;			// ヘルプ背景の種類
	int _elapse;				// 経過時間
	int _elapse_p;				// ポーズ中の経過時間
	int _elapse_s;				// シールド切り替え速度
	boolean pause = false;		// ポーズ中かどうか
	int[] shield_lag;
	int shield_index;
	int[] shield_wait;
	int[] shield_col;

	int change_col;

	int height;

	boolean sensor_f;			// モーションセンサーを使用するかどうか
	boolean neutral;			// キー解放でニュートラルにするかどうか
	int level;					// レベル
	int player;					// 使用キャラクタ

	boolean first;				// 初挑戦かどうか

	int[] time;					// 今回のタイム
	int[][] best_time;			// ベストタイム
	int[][] win;				// １位になった回数
	int ranking;				// 順位
	boolean new_time;			// 記録更新かどうか

	int best_distance;			// ベスト走行距離
	int old_distance;			//
	boolean new_distance;		// 記録更新かどうか

	int _elapse_l;				// 計測ポイント通過時の時間
	int lap;					// ラップ
	int lap_time;				// ラップタイムの差
	boolean dsp_lap;			// ラップタイムの差を表示するかどうか
	boolean finish;				// フィニッシュラインかどうか

	boolean boost;				// ブースト可能かどうか
	int _elapse_b;				// ブースト使用時の時間

	int[] old_y;
	int[] new_y;

	_Image[] main_img;

	_Layout layout;
	int layoutState = 0;

	_Sensor sensor;

	// キー入力と描画との排他処理用
	boolean _lock_state = false;
	boolean _wait_state = false;
	public void lock_state(){
		_wait_state = true;
		while( _lock_state ){
			try {
				Thread.sleep( FRAME_TIME );
			} catch( Exception e ){
			}
		}
		_lock_state = true;
		_wait_state = false;
	}
	public void unlock_state(){
		_lock_state = false;
		while( _wait_state ){
			try {
				Thread.sleep( FRAME_TIME );
			} catch( Exception e ){
			}
		}
	}

	// キー入力時処理のコンフリクト抑制用
	boolean processingEvent = false;

	boolean terminate_f = false;

	/**
	 * 設定の読み込み
	 */
	public void load_config(){
		int i, j;

		// デフォルト値
		sensor_f      = true;
		neutral       = true;
		level         = 0;
		player        = 0;
		shield_lag[0] = 10;
		shield_lag[1] = 10;
		shield_index  = 0;
		for( i = 0; i < 7; i++ ){
			for( j = 0; j < 10; j++ ){
				best_time[i][j] = 99999;
			}
		}
		for( i = 0; i < 3; i++ ){
			for( j = 0; j < 3; j++ ){
				win[i][j] = 0;
			}
		}
		best_distance = 0;

		// 既にデータがあるかどうかチェックする
		_Preference pref = new _Preference( this, "config" );
		if( Integer.parseInt( pref.getParameter( "save", "0" ) ) == 1 ){
			// データがあるので読み出す
			pref.beginRead();
			String str = new String( "" );
			str = pref.read( "" ); if( str.length() > 0 ) neutral = (Integer.parseInt( str ) == 1) ? true : false;
			str = pref.read( "" ); if( str.length() > 0 ) level = Integer.parseInt( str );
			str = pref.read( "" ); if( str.length() > 0 ) player = Integer.parseInt( str );
			str = pref.read( "" ); if( str.length() > 0 ) shield_lag[0] = Integer.parseInt( str );
			str = pref.read( "" ); if( str.length() > 0 ) shield_lag[1] = Integer.parseInt( str );
			str = pref.read( "" ); if( str.length() > 0 ) shield_index = Integer.parseInt( str );
			for( i = 0; i < 6; i++ ){
				for( j = 0; j < 10; j++ ){
					str = pref.read( "" ); if( str.length() > 0 ) best_time[i][j] = Integer.parseInt( str );
				}
			}
			for( i = 0; i < 2; i++ ){
				for( j = 0; j < 3; j++ ){
					str = pref.read( "" ); if( str.length() > 0 ) win[i][j] = Integer.parseInt( str );
				}
			}
			str = pref.read( "" ); if( str.length() > 0 ) best_distance = Integer.parseInt( str );
			for( j = 0; j < 10; j++ ){
				str = pref.read( "" ); if( str.length() > 0 ) best_time[6][j] = Integer.parseInt( str );
			}
			for( j = 0; j < 3; j++ ){
				str = pref.read( "" ); if( str.length() > 0 ) win[2][j] = Integer.parseInt( str );
			}
			str = pref.read( "" ); if( str.length() > 0 ) sensor_f = (Integer.parseInt( str ) == 1) ? true : false;
			pref.endRead();
		}
	}

	/**
	 * 設定の書き出し
	 */
	public void save_config(){
		int i, j;
		_Preference pref = new _Preference( this, "config" );
		pref.setParameter( "save", "1" );
		pref.beginWrite();
		pref.write( neutral ? "1" : "0" );
		pref.write( "" + level );
		pref.write( "" + player );
		pref.write( "" + shield_lag[0] );
		pref.write( "" + shield_lag[1] );
		pref.write( "" + shield_index );
		for( i = 0; i < 6; i++ ){
			for( j = 0; j < 10; j++ ){
				pref.write( "" + best_time[i][j] );
			}
		}
		for( i = 0; i < 2; i++ ){
			for( j = 0; j < 3; j++ ){
				pref.write( "" + win[i][j] );
			}
		}
		pref.write( "" + best_distance );
		for( j = 0; j < 10; j++ ){
			pref.write( "" + best_time[6][j] );
		}
		for( j = 0; j < 3; j++ ){
			pref.write( "" + win[2][j] );
		}
		pref.write( sensor_f ? "1" : "0" );
		pref.endWrite();
	}

	/**
	 * イメージ読み込み
	 */
	public void create_image( int id ){
		if( main_img[id] == null ){
			int id2 = 0;
			switch( id ){
			case IMAGE_BACK    : id2 = R.drawable.back    ; break;
			case IMAGE_BAR     : id2 = R.drawable.bar     ; break;
			case IMAGE_FORE1   : id2 = R.drawable.fore1   ; break;
			case IMAGE_FORE2   : id2 = R.drawable.fore2   ; break;
			case IMAGE_FORE3   : id2 = R.drawable.fore3   ; break;
			case IMAGE_FORE4   : id2 = R.drawable.fore4   ; break;
			case IMAGE_FORE5A  : id2 = R.drawable.fore5a  ; break;
			case IMAGE_FORE5B  : id2 = R.drawable.fore5b  ; break;
			case IMAGE_FORE6A  : id2 = R.drawable.fore6a  ; break;
			case IMAGE_FORE6B  : id2 = R.drawable.fore6b  ; break;
			case IMAGE_LOGO    : id2 = R.drawable.logo    ; break;
			case IMAGE_SHIELD  : id2 = R.drawable.shield  ; break;
			case IMAGE_SPEEDER1: id2 = R.drawable.speeder1; break;
			case IMAGE_SPEEDER2: id2 = R.drawable.speeder2; break;
			case IMAGE_SPEEDER3: id2 = R.drawable.speeder3; break;
			case IMAGE_STATUS  : id2 = R.drawable.status  ; break;
			case IMAGE_TITLE   : id2 = R.drawable.title   ; break;
			case IMAGE_RAY     : id2 = R.drawable.ray     ; break;
			case IMAGE_COM     : id2 = R.drawable.com     ; break;
			case IMAGE_RAX     : id2 = R.drawable.rax     ; break;
			}
			main_img[id] = _Image.createImage( r, id2 );
		}
	}
	public void dispose_image( int id ){
		if( main_img[id] != null ){
			main_img[id].dispose();
			main_img[id] = null;
			System.gc();
		}
	}
	public void dispose_image(){
		for( int i = 0; i < IMAGE_NUM; i++ ){
			if( main_img[i] != null ){
				main_img[i].dispose();
				main_img[i] = null;
			}
		}
		System.gc();
	}
	public _Image use_image( int id ){
		create_image( id );
		return main_img[id];
	}

	// 経過時間を確認する
	public int elapse(){ return pause ? _elapse_p : _elapse; }

	// おまけモードがプレイできるか確認する
	public boolean omake1(){
		int i;
		for( i = 0; i < 6; i++ ){
			if( best_time[i][0] == 99999 ){
				return false;
			}
		}
		return true;
	}
	public boolean omake2(){
		int i, j;
		for( i = 0; i < 2; i++ ){
			for( j = 0; j < 3; j++ ){
				if( win[i][j] == 0 ){
					return false;
				}
			}
		}
		return true;
	}

	public int level_max(){
		if( omake1() ){
			return omake2() ? 7 : 6;
		}
		return 5;
	}

	public int index_b(){ return (level == 7) ? 6 : level; }
	public int index_w(){ return (level == 7) ? 2 : level; }

	/**
	 * アプリの状態を変更する
	 */
	public void set_state( int new_state ){
		int old_state = state;
		state = new_state;
		_elapse = 0;
		_elapse_l = 0;
		_elapse_b = 0 - WAIT_BOOST;

		switch( old_state ){
		case STATE_TITLE_LOADING:
		case STATE_SELECT_LOADING:
			dispose_image();

			if( state == STATE_READY ){
				change_col = 0;

				if( level != 6 ){
					shield_wait[0] = 0;
					shield_wait[1] = 0;

					first = (best_time[index_b()][0] == 99999) ? true : false;

					time[0] = 0;

					lap     = 0;
					dsp_lap = false;
					finish  = false;
				} else {
					old_distance = best_distance;
				}

				boost = false;

				if( (level < 2) || (level == 7) ){
					boolean tmp = (rand.nextInt() > 0) ? true : false;
					switch( player ){
					case 0:
						speeder[0].init( true, SPEEDER1, 0, 0 );
						speeder[1].init( false, tmp ? SPEEDER2 : SPEEDER3, 0, -50 );
						speeder[2].init( false, tmp ? SPEEDER3 : SPEEDER2, 0,  50 );
						break;
					case 1:
						speeder[0].init( true, SPEEDER2, 0, 0 );
						speeder[1].init( false, tmp ? SPEEDER1 : SPEEDER3, 0, -50 );
						speeder[2].init( false, tmp ? SPEEDER3 : SPEEDER1, 0,  50 );
						break;
					case 2:
						speeder[0].init( true, SPEEDER3, 0, 0 );
						speeder[1].init( false, tmp ? SPEEDER1 : SPEEDER2, 0, -50 );
						speeder[2].init( false, tmp ? SPEEDER2 : SPEEDER1, 0,  50 );
						break;
					}
				} else {
					switch( rand.nextInt() % 2 ){
					case -1: speeder[0].init( true, SPEEDER1, (level != 6) ? 0 : 495, 0 ); break;
					case  0: speeder[0].init( true, SPEEDER4, (level != 6) ? 0 : 495, 0 ); break;
					case  1: speeder[0].init( true, SPEEDER5, (level != 6) ? 0 : 495, 0 ); break;
					}
				}
			}

			break;
		case STATE_CLEAR:
		case STATE_STOP:
			save_config();
			break;
		}

		switch( state ){
		case STATE_LAUNCH:
		case STATE_TITLE_LOADING:
		case STATE_SELECT_LOADING:
			canvas.setLayout( null );
			break;
		case STATE_TITLE:
			{
				int t = canvas.windowY( 0 );
				int h = -t; if( h > 40 ) h = 40;
				layout.clear();
				layout.add(   0,   t,  80,  h, MYLAYOUT_BACK   );
				layout.add(  20,  89,  40, 27, MYLAYOUT_LEFT1  );
				layout.add( 180,  89,  40, 27, MYLAYOUT_RIGHT1 );
				layout.add(  20, 116,  40, 27, MYLAYOUT_LEFT2  );
				layout.add( 180, 116,  40, 27, MYLAYOUT_RIGHT2 );
				layout.add(  20, 143,  40, 27, MYLAYOUT_LEFT3  );
				layout.add( 180, 143,  40, 27, MYLAYOUT_RIGHT3 );
				layout.add(  40, 217, 160, 27, MYLAYOUT_SELECT );
				canvas.setLayout( layout );
			}
			break;
		case STATE_SELECT:
			{
				int t = canvas.windowY( 0 );
				int h = -t; if( h > 40 ) h = 40;
				layout.clear();
				layout.add(  37,  65,  40, 40, MYLAYOUT_LEFT   );
				layout.add( 165,  65,  40, 40, MYLAYOUT_RIGHT  );
				layout.add(  40, 217, 160, 27, MYLAYOUT_SELECT );
				layout.add(   0,   t,  80,  h, MYLAYOUT_BACK   );
				canvas.setLayout( layout );
			}
			break;
		default:
			{
				int t = canvas.windowY( 0 );
				int h = -t; if( h > 40 ) h = 40;
				int b = canvas.windowY( canvas.getHeight() ) - h;
				layout.clear();
				layout.add( 160, t,  80,   h, MYLAYOUT_PAUSE    );
				layout.add(   0, t,  80,   h, MYLAYOUT_BACK     );
				layout.add(   0, 0, 120, 270, MYLAYOUT_LEFT     );
				layout.add( 120, 0, 120, 270, MYLAYOUT_RIGHT    );
				layout.add(   0, b,  80,   h, MYLAYOUT_SHIELD_0 );
				layout.add(  80, b,  80,   h, MYLAYOUT_SHIELD_1 );
				layout.add( 160, b,  80,   h, MYLAYOUT_SHIELD_2 );
				canvas.setLayout( layout );
			}
			break;
		}
		
		switch( state ){
		case STATE_TITLE:
			if( old_state != STATE_SELECT ){
				pause = false;
				save_config();
			}
			dispose_image();
			break;
		case STATE_SELECT:
			create_image( IMAGE_SPEEDER1 );
			create_image( IMAGE_SPEEDER2 );
			create_image( IMAGE_SPEEDER3 );
			break;
		case STATE_READY:
			wave.create();
			stage.create();
			break;
		case STATE_CLEAR:
			new_time = false;
			if( time[0] < best_time[index_b()][0] ){
				new_time = first ? false : true;
				best_time[index_b()][0] = time[0];
				best_time[index_b()][1] = time[1];
				for( int i = 2; i < 10; i++ ){
					best_time[index_b()][i] = time[i] - time[i - 1];
				}
			}
			ranking = 1;
			if( (level < 2) || (level == 7) ){
				if( speeder[0].distance() < speeder[1].distance() ) ranking++;
				if( speeder[0].distance() < speeder[2].distance() ) ranking++;
				if( ranking == 1 ){
					win[index_w()][player]++;
				}
			}
			break;
		case STATE_STOP:
			new_distance = false;
			if( speeder[0].distance() > best_distance ){
				new_distance = (best_distance == 0) ? false : true;
				best_distance = speeder[0].distance();
			}
			break;
		}
	}

	/**
	 * 描画に使用する色を設定
	 */
	public void setCMYColor( int col ){
		switch( col ){
		case 0: g.setColor( COLOR_C ); break;
		case 1: g.setColor( COLOR_M ); break;
		case 2: g.setColor( COLOR_Y ); break;
		}
	}

	int stringWidth( String str ){
		return g.stringWidth( str );
	}
	int fontHeight(){
		return g.fontHeight();
	}
	int drawImage( _Graphics g, _Image img, int x0, int y0, int x, int y, int w, int h ){
		if( (x0 + w) <= 0 ){
			return -1;
		} else if( x0 >= 240 ){
			return 1;
		}
		g.drawImage( img, x0, y0, x, y, w, h );
		return 0;
	}
	void drawImage( _Graphics g, _Image img, int x0, int y0 ){
		g.drawImage( img, x0, y0 );
	}
	void drawScaledImage( _Graphics g, _Image img, int dx, int dy, int width, int height, int sx, int sy, int swidth, int sheight ){
		g.drawScaledImage( img, dx, dy, width, height, sx, sy, swidth, sheight );
	}

	void drawButton( _Graphics g, int id ){
		g.setAlpha( 64 );
		g.fillRoundRect( layout.getLeft( id ), layout.getTop( id ), layout.getWidth( id ), layout.getHeight( id ), 5, 5 );
		g.setAlpha( 255 );
		g.drawRoundRect( layout.getLeft( id ), layout.getTop( id ), layout.getWidth( id ) - 1, layout.getHeight( id ) - 1, 5, 5 );
	}
	void drawScreenButton( _Graphics g, int id, String str ){
		int left   = canvas.screenX( layout.getLeft  ( id ) );
		int top    = canvas.screenY( layout.getTop   ( id ) );
		int right  = canvas.screenX( layout.getRight ( id ) );
		int bottom = canvas.screenY( layout.getBottom( id ) );
		g.drawRoundRect( left, top, right - left - 1, bottom - top - 1, 16, 16 );
		g.drawString( str, left + ((right - left) - g.stringWidth( str )) / 2, top + ((bottom - top) - g.fontHeight()) / 2 + g.fontHeight() );
	}
	void drawScreenButton2( _Graphics g, int id ){
		int left   = canvas.screenX( layout.getLeft  ( id ) );
		int top    = canvas.screenY( layout.getTop   ( id ) );
		int right  = canvas.screenX( layout.getRight ( id ) );
		int bottom = canvas.screenY( layout.getBottom( id ) );
		g.fillRoundRect( left, top, right - left, bottom - top, 16, 16 );
	}

	/**
	 * start
	 */
	public void start(){
		r = getResources();

		canvas = new MainCanvas();
		setCurrent( canvas );

		shield_lag  = new int[2];
		shield_wait = new int[2];
		shield_col  = new int[2];

		time = new int[10];
		best_time = new int[7][10];
		win = new int[3][3];

		old_y = new int[2];
		new_y = new int[2];

		rand = new Random( System.currentTimeMillis() );
		stage = new Stage();
		wave = new Wave();

		speeder = new MySpeeder[3];
		speeder[0] = new MySpeeder();
		speeder[1] = new MySpeeder();
		speeder[2] = new MySpeeder();

		main_img = new _Image[IMAGE_NUM];
		for( int i = 0; i < IMAGE_NUM; i++ ){
			main_img[i] = null;
		}

		layout = new _Layout( true );

		sensor = new _Sensor();
		sensor.start( this );

		load_config();
	}

	public void suspend(){
		sensor.stop();
	}

	public void resume(){
		sensor.restart();
	}

	/**
	 * キャンバス
	 */
	class MainCanvas extends _Canvas {
		public int frameTime(){ return FRAME_TIME; }
		public int touchNum(){ return 2; }

		public void init(){
			_Graphics g2 = getGraphics();
			g2.setAntiAlias( true );

			height = 270;

			window = _Image.createImage( 240, height );
			g = window.getGraphics();
			g.setAntiAlias( true );

			setWindow( 240, height );

			lock_state();
			set_state( STATE_TITLE );
			unlock_state();
		}

		/**
		 * 文字列センタリング描画
		 */
		private void centerDrawString( String str, int y ){
			int x;
			x = (240 - stringWidth( str )) / 2;
			y = y + fontHeight() / 2;

			g.drawString( str, x + 1, y );
			g.drawString( str, x    , y );
		}

		/**
		 * ステータス描画
		 */
		private void drawStatus( boolean ready ){
			int i, x, y;
			int tmp;

			if( level != 6 ){
				drawImage( g, use_image( IMAGE_STATUS ), 10, 0, 0, 26, 92, 24 );
				x = 15 + 12 * 3;
				tmp = time[0];
				for( i = 0; i < 4; i++ ){
					drawImage( g, use_image( IMAGE_STATUS ), x, 6, (tmp % 10) * 12, 0, 12, 13 );
					tmp /= 10;
					x -= 12;
				}

				if( !first && dsp_lap ){
					if( (lap > 0) && ((_elapse - _elapse_l) < WAIT_2) ){
						drawImage( g, use_image( IMAGE_STATUS ), 10, 24, 0, 26, 59, 24 );
						x = 15 + 12 * 3;
						y = 0;
						tmp = lap_time;
						if( tmp < 0 ){
							tmp = 0 - tmp;
							drawImage( g, use_image( IMAGE_STATUS ), 15, 30, 120, 13, 12, 13 );
							y = 13;
						} else {
							drawImage( g, use_image( IMAGE_STATUS ), 15, 30, 120, (tmp == 0) ? 26 : 0, 12, 13 );
						}
						for( i = 0; i < 3; i++ ){
							drawImage( g, use_image( IMAGE_STATUS ), x, 30, (tmp % 10) * 12, y, 12, 13 );
							tmp /= 10;
							x -= 12;
						}
					} else {
						dsp_lap = false;
					}
				}
			}

			drawImage( g, use_image( IMAGE_STATUS ), 148, 0, 0, 50, 82, 24 );
			x = 190 + 12 * 2;
			tmp = ready ? 0 : speeder[0].speed();
			for( i = 0; i < 3; i++ ){
				drawImage( g, use_image( IMAGE_STATUS ), x, 6, (tmp % 10) * 12, 0, 12, 13 );
				tmp /= 10;
				x -= 12;
			}

			drawImage( g, use_image( IMAGE_STATUS ), 0, 0, 132, 0, 10, height );
			drawImage( g, use_image( IMAGE_STATUS ), 230, 0, 132, 0, 10, height );

			if( (level < 2) || (level == 7) ){
				for( i = 1; i < 3; i++ ){
					drawImage( g, use_image( IMAGE_STATUS ), 1, height - (height * speeder[i].distance() / DISTANCE) - 4, 92, 34, 8, 8 );
				}
				drawImage( g, use_image( IMAGE_STATUS ), 1, height - (height * speeder[0].distance() / DISTANCE) - 4, 92, 26, 8, 8 );

				for( i = 1; i < 3; i++ ){
					drawImage( g, use_image( IMAGE_STATUS ), 231, (height / 2 - 4) - ((speeder[i].distance() - speeder[0].distance()) / 100), 92, 34, 8, 8 );
				}
				drawImage( g, use_image( IMAGE_STATUS ), 231, height / 2 - 4, 92, 26, 8, 8 );
			} else if( (level != 6) || (old_distance == 0) ){
				y = height - (height * speeder[0].distance() / DISTANCE);
				drawImage( g, use_image( IMAGE_STATUS ), 1, y - 4, 92, 26, 8, 8 );
				drawImage( g, use_image( IMAGE_STATUS ), 231, y - 4, 92, 26, 8, 8 );
			} else {
				y = height - (height * old_distance / (old_distance * 2));
				drawImage( g, use_image( IMAGE_STATUS ), 1, y - 4, 92, 34, 8, 8 );
				drawImage( g, use_image( IMAGE_STATUS ), 231, y - 4, 92, 34, 8, 8 );
				y = height - (height * speeder[0].distance() / (old_distance * 2));
				drawImage( g, use_image( IMAGE_STATUS ), 1, y - 4, 92, 26, 8, 8 );
				drawImage( g, use_image( IMAGE_STATUS ), 231, y - 4, 92, 26, 8, 8 );
			}

			if( boost ){
				drawImage( g, use_image( IMAGE_STATUS ), 184, 24, ((elapse() % 4) < 2) ? 0 : 46, 264, 46, 23 );
			}
		}

		/**
		 * paint
		 */
		public void paint( _Graphics _g ){
			lock_state();

			layoutState = getLayoutState();

			switch( state ){
//			case STATE_LAUNCH:
//				g.lock();
//				g.setColor( COLOR_K );
//				g.fillRect( 0, 0, 240, height );
//				setCMYColor( _elapse % 3 );
//				centerDrawString( "起動中...", 120 );
//				g.unlock();
//				break;
			case STATE_TITLE:
			case STATE_TITLE_LOADING:
				// 描画
				g.lock();
				{
					int x;

					g.setColor( COLOR_K );
					g.fillRect( 0, 0, 240, height );

					g.setColor( COLOR_W );
					g.setStrokeWidth( 1 );
					drawButton( g, MYLAYOUT_LEFT1 );
					drawButton( g, MYLAYOUT_RIGHT1 );
					drawButton( g, MYLAYOUT_LEFT2 );
					drawButton( g, MYLAYOUT_RIGHT2 );
					drawButton( g, MYLAYOUT_LEFT3 );
					drawButton( g, MYLAYOUT_RIGHT3 );
					if( state == STATE_TITLE ){
						drawButton( g, MYLAYOUT_SELECT );
					}

					drawImage( g, use_image( IMAGE_LOGO ), 0, 5 );

					drawImage( g, use_image( IMAGE_TITLE ),  36, 98, 150, 0, 5, 9 );
					drawImage( g, use_image( IMAGE_TITLE ), 200, 98, 155, 0, 5, 9 );
					x = (240 - (TEXT_SENSOR_W + 8 + TEXT_OFF_W)) / 2;
					drawImage( g, use_image( IMAGE_TITLE ), x, 98, TEXT_SENSOR_X, TEXT_SENSOR_Y, TEXT_SENSOR_W, TEXT_SENSOR_H ); x += (TEXT_SENSOR_W + 8);
					if( sensor_f ){
						drawImage( g, use_image( IMAGE_TITLE ), x, 98, TEXT_ON_X, TEXT_ON_Y, TEXT_ON_W, TEXT_ON_H );
					} else {
						drawImage( g, use_image( IMAGE_TITLE ), x, 98, TEXT_OFF_X, TEXT_OFF_Y, TEXT_OFF_W, TEXT_OFF_H );
					}

					drawImage( g, use_image( IMAGE_TITLE ),  36, 125, 150, 0, 5, 9 );
					drawImage( g, use_image( IMAGE_TITLE ), 200, 125, 155, 0, 5, 9 );
					x = (240 - (TEXT_NEUTRAL_W + 8 + TEXT_OFF_W)) / 2;
					drawImage( g, use_image( IMAGE_TITLE ), x, 125, TEXT_NEUTRAL_X, TEXT_NEUTRAL_Y, TEXT_NEUTRAL_W, TEXT_NEUTRAL_H ); x += (TEXT_NEUTRAL_W + 8);
					if( neutral ){
						drawImage( g, use_image( IMAGE_TITLE ), x, 125, TEXT_ON_X, TEXT_ON_Y, TEXT_ON_W, TEXT_ON_H );
					} else {
						drawImage( g, use_image( IMAGE_TITLE ), x, 125, TEXT_OFF_X, TEXT_OFF_Y, TEXT_OFF_W, TEXT_OFF_H );
					}

					drawImage( g, use_image( IMAGE_TITLE ),  36, 152, 150, 0, 5, 9 );
					drawImage( g, use_image( IMAGE_TITLE ), 200, 152, 155, 0, 5, 9 );
					switch( level ){
					case 0:
						x = (240 - (TEXT_RACE_W + 8 + TEXT_EASY_W)) / 2;
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_RACE_X, TEXT_RACE_Y, TEXT_RACE_W, TEXT_RACE_H ); x += (TEXT_RACE_W + 8);
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_EASY_X, TEXT_EASY_Y, TEXT_EASY_W, TEXT_EASY_H );
						break;
					case 1:
						x = (240 - (TEXT_RACE_W + 8 + TEXT_HARD_W)) / 2;
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_RACE_X, TEXT_RACE_Y, TEXT_RACE_W, TEXT_RACE_H ); x += (TEXT_RACE_W + 8);
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_HARD_X, TEXT_HARD_Y, TEXT_HARD_W, TEXT_HARD_H );
						break;
					case 2:
						x = (240 - (TEXT_FREE_W + 8 + TEXT_EASY_W)) / 2;
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_FREE_X, TEXT_FREE_Y, TEXT_FREE_W, TEXT_FREE_H ); x += (TEXT_FREE_W + 8);
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_EASY_X, TEXT_EASY_Y, TEXT_EASY_W, TEXT_EASY_H );
						break;
					case 3:
						x = (240 - (TEXT_FREE_W + 8 + TEXT_HARD_W)) / 2;
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_FREE_X, TEXT_FREE_Y, TEXT_FREE_W, TEXT_FREE_H ); x += (TEXT_FREE_W + 8);
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_HARD_X, TEXT_HARD_Y, TEXT_HARD_W, TEXT_HARD_H );
						break;
					case 4:
						x = (240 - (TEXT_TRAINING_W + 8 + TEXT_1_W)) / 2;
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_TRAINING_X, TEXT_TRAINING_Y, TEXT_TRAINING_W, TEXT_TRAINING_H ); x += (TEXT_TRAINING_W + 8);
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_1_X, TEXT_1_Y, TEXT_1_W, TEXT_1_H );
						break;
					case 5:
						x = (240 - (TEXT_TRAINING_W + 8 + TEXT_2_W)) / 2;
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_TRAINING_X, TEXT_TRAINING_Y, TEXT_TRAINING_W, TEXT_TRAINING_H ); x += (TEXT_TRAINING_W + 8);
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_2_X, TEXT_2_Y, TEXT_2_W, TEXT_2_H );
						break;
					case 6:
						x = (240 - (TEXT_OMAKE_W + 8 + TEXT_1_W)) / 2;
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_OMAKE_X, TEXT_OMAKE_Y, TEXT_OMAKE_W, TEXT_OMAKE_H ); x += (TEXT_OMAKE_W + 8);
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_1_X, TEXT_1_Y, TEXT_1_W, TEXT_1_H );
						break;
					case 7:
						x = (240 - (TEXT_OMAKE_W + 8 + TEXT_2_W)) / 2;
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_OMAKE_X, TEXT_OMAKE_Y, TEXT_OMAKE_W, TEXT_OMAKE_H ); x += (TEXT_OMAKE_W + 8);
						drawImage( g, use_image( IMAGE_TITLE ), x, 152, TEXT_2_X, TEXT_2_Y, TEXT_2_W, TEXT_2_H );
						break;
					}

					if( level != 6 ){
						drawImage( g, use_image( IMAGE_TITLE ), (240 - TEXT_BESTTIME_W) / 2, 179, TEXT_BESTTIME_X, TEXT_BESTTIME_Y, TEXT_BESTTIME_W, TEXT_BESTTIME_H );
						if( best_time[index_b()][0] == 99999 ){
							drawImage( g, use_image( IMAGE_TITLE ), 96, 195, 120, 0, 12, 12 );
							drawImage( g, use_image( IMAGE_TITLE ), 108, 195, 120, 0, 12, 12 );
							drawImage( g, use_image( IMAGE_TITLE ), 120, 195, 120, 0, 12, 12 );
							drawImage( g, use_image( IMAGE_TITLE ), 132, 195, 120, 0, 12, 12 );
						} else {
							x = 96 + 12 * 3;
							int tmp = best_time[index_b()][0];
							for( int i = 0; i < 4; i++ ){
								drawImage( g, use_image( IMAGE_TITLE ), x, 195, (tmp % 10) * 12, 0, 12, 12 );
								tmp /= 10;
								x -= 12;
							}
						}
					} else {
						drawImage( g, use_image( IMAGE_TITLE ), (240 - TEXT_DISTANCE_W) / 2, 179, TEXT_DISTANCE_X, TEXT_DISTANCE_Y, TEXT_DISTANCE_W, TEXT_DISTANCE_H );
						int i = 0;
						int tmp = best_distance / 5;
						while( true ){
							i++;
							tmp /= 10;
							if( tmp == 0 ){
								break;
							}
						}
						x = 240 - ((240 - (12 * i)) / 2);
						tmp = best_distance / 5;
						while( true ){
							x -= 12;
							drawImage( g, use_image( IMAGE_TITLE ), x, 195, (tmp % 10) * 12, 0, 12, 12 );
							tmp /= 10;
							if( tmp == 0 ){
								break;
							}
						}
					}

					if( state == STATE_TITLE ){
						if( (_elapse % WAIT_1) <= (WAIT_1 / 2) ){
							x = (240 - (TEXT_PRESS_W + 8 + TEXT_BUTTON_W)) / 2;
							drawImage( g, use_image( IMAGE_TITLE ), x, 225, TEXT_PRESS_X, TEXT_PRESS_Y, TEXT_PRESS_W, TEXT_PRESS_H ); x += (TEXT_PRESS_W + 8);
							drawImage( g, use_image( IMAGE_TITLE ), x, 225, TEXT_BUTTON_X, TEXT_BUTTON_Y, TEXT_BUTTON_W, TEXT_BUTTON_H );
						}
					} else {
						drawImage( g, use_image( IMAGE_TITLE ), (240 - TEXT_LOADING_W) / 2, 225, TEXT_LOADING_X, TEXT_LOADING_Y, TEXT_LOADING_W, TEXT_LOADING_H );
					}

					x = (240 - (TEXT_COPYRIGHT_W + 8 + TEXT_COPYRIGHT2_W)) / 2;
					drawImage( g, use_image( IMAGE_TITLE ), x, 255, TEXT_COPYRIGHT_X, TEXT_COPYRIGHT_Y, TEXT_COPYRIGHT_W, TEXT_COPYRIGHT_H ); x += (TEXT_COPYRIGHT_W + 8);
					drawImage( g, use_image( IMAGE_TITLE ), x, 255, TEXT_COPYRIGHT2_X, TEXT_COPYRIGHT2_Y, TEXT_COPYRIGHT2_W, TEXT_COPYRIGHT2_H );
				}
				g.unlock();

				if( state == STATE_TITLE_LOADING ){
					if( (level < 2) || (level == 7) ){
						set_state( STATE_SELECT );
					} else {
						set_state( STATE_READY );
					}
				}

				break;
			case STATE_SELECT:
			case STATE_SELECT_LOADING:
				// 描画
				g.lock();
				{
					int i;
					int x = 0;
					int tmp = 0;

					g.setColor( COLOR_K );
					g.fillRect( 0, 0, 240, height );

					switch( player ){
					case 0: drawScaledImage( g, use_image( IMAGE_RAY ), 0, 0, 240, height, 0, 0, 240, 240 ); break;
					case 1: drawScaledImage( g, use_image( IMAGE_RAX ), 0, 0, 240, height, 0, 0, 240, 240 ); break;
					case 2: drawScaledImage( g, use_image( IMAGE_COM ), 0, 0, 240, height, 0, 0, 240, 240 ); break;
					}
					g.setColor( COLOR_K );
					g.setAlpha( 128 );
					g.fillRect( 0, 0, 240, height );
					g.setAlpha( 255 );

//					drawImage( g, use_image( IMAGE_TITLE ), (240 - TEXT_SELECTCHAR_W) / 2, 15, TEXT_SELECTCHAR_X, TEXT_SELECTCHAR_Y, TEXT_SELECTCHAR_W, TEXT_SELECTCHAR_H );
					g.setColor( COLOR_Y );
					g.setFontSize( 16 );
					centerDrawString( "キャラクタを選んでね", 20 );

					drawImage( g, use_image( IMAGE_TITLE ), 140 - TEXT_ACCELERATION_W, 140, TEXT_ACCELERATION_X, TEXT_ACCELERATION_Y, TEXT_ACCELERATION_W, TEXT_ACCELERATION_H );
					drawImage( g, use_image( IMAGE_TITLE ), 140 - TEXT_SLOWDOWN_W, 160, TEXT_SLOWDOWN_X, TEXT_SLOWDOWN_Y, TEXT_SLOWDOWN_W, TEXT_SLOWDOWN_H );
					drawImage( g, use_image( IMAGE_TITLE ), 140 - TEXT_STEERING_W, 180, TEXT_STEERING_X, TEXT_STEERING_Y, TEXT_STEERING_W, TEXT_STEERING_H );

					switch( player ){
					case 0:
						for( i = 0; i < 3; i++ ){
							switch( i ){
							case 0: x =  65; tmp = win[index_w()][2]; break;
							case 1: x = 145; tmp = win[index_w()][0]; break;
							case 2: x = 225; tmp = win[index_w()][1]; break;
							}
							while( tmp != 0 ){
								drawImage( g, use_image( IMAGE_TITLE ), x, 50, (tmp % 10) * 12, 0, 12, 12 );
								tmp /= 10;
								x -= 12;
							}
						}

						drawImage( g, use_image( IMAGE_SPEEDER3 ),  40 - (SPEEDER3_W[0] / 2), 55, SPEEDER3_X[0], 0, SPEEDER3_W[0], 43 );
						drawImage( g, use_image( IMAGE_SPEEDER1 ), 120 - (SPEEDER1_W[0] / 2), 55, SPEEDER1_X[0], 0, SPEEDER1_W[0], 43 );
						drawImage( g, use_image( IMAGE_SPEEDER2 ), 200 - (SPEEDER2_W[0] / 2), 57, SPEEDER2_X[0], 0, SPEEDER2_W[0], 41 );

						drawImage( g, use_image( IMAGE_TITLE ), (240 - TEXT_RAY_W) / 2, 105, TEXT_RAY_X, TEXT_RAY_Y, TEXT_RAY_W, TEXT_RAY_H );

						drawImage( g, use_image( IMAGE_TITLE ), 150, 140, TEXT_FAST_X, TEXT_FAST_Y, TEXT_FAST_W, TEXT_FAST_H );
						drawImage( g, use_image( IMAGE_TITLE ), 150, 160, TEXT_NORMAL_X, TEXT_NORMAL_Y, TEXT_NORMAL_W, TEXT_NORMAL_H );
						drawImage( g, use_image( IMAGE_TITLE ), 150, 180, TEXT_NORMAL_X, TEXT_NORMAL_Y, TEXT_NORMAL_W, TEXT_NORMAL_H );

						break;
					case 1:
						for( i = 0; i < 3; i++ ){
							switch( i ){
							case 0: x =  65; tmp = win[index_w()][0]; break;
							case 1: x = 145; tmp = win[index_w()][1]; break;
							case 2: x = 225; tmp = win[index_w()][2]; break;
							}
							while( tmp != 0 ){
								drawImage( g, use_image( IMAGE_TITLE ), x, 50, (tmp % 10) * 12, 0, 12, 12 );
								tmp /= 10;
								x -= 12;
							}
						}

						drawImage( g, use_image( IMAGE_SPEEDER1 ),  40 - (SPEEDER1_W[0] / 2), 55, SPEEDER1_X[0], 0, SPEEDER1_W[0], 43 );
						drawImage( g, use_image( IMAGE_SPEEDER2 ), 120 - (SPEEDER2_W[0] / 2), 57, SPEEDER2_X[0], 0, SPEEDER2_W[0], 41 );
						drawImage( g, use_image( IMAGE_SPEEDER3 ), 200 - (SPEEDER3_W[0] / 2), 55, SPEEDER3_X[0], 0, SPEEDER3_W[0], 43 );

						drawImage( g, use_image( IMAGE_TITLE ), (240 - TEXT_RAX_W) / 2, 105, TEXT_RAX_X, TEXT_RAX_Y, TEXT_RAX_W, TEXT_RAX_H );

						drawImage( g, use_image( IMAGE_TITLE ), 150, 140, TEXT_MIDDLE_X, TEXT_MIDDLE_Y, TEXT_MIDDLE_W, TEXT_MIDDLE_H );
						drawImage( g, use_image( IMAGE_TITLE ), 150, 160, TEXT_SLIGHT_X, TEXT_SLIGHT_Y, TEXT_SLIGHT_W, TEXT_SLIGHT_H );
						drawImage( g, use_image( IMAGE_TITLE ), 150, 180, TEXT_NORMAL_X, TEXT_NORMAL_Y, TEXT_NORMAL_W, TEXT_NORMAL_H );

						break;
					case 2:
						for( i = 0; i < 3; i++ ){
							switch( i ){
							case 0: x =  65; tmp = win[index_w()][1]; break;
							case 1: x = 145; tmp = win[index_w()][2]; break;
							case 2: x = 225; tmp = win[index_w()][0]; break;
							}
							while( tmp != 0 ){
								drawImage( g, use_image( IMAGE_TITLE ), x, 50, (tmp % 10) * 12, 0, 12, 12 );
								tmp /= 10;
								x -= 12;
							}
						}

						drawImage( g, use_image( IMAGE_SPEEDER2 ),  40 - (SPEEDER2_W[0] / 2), 57, SPEEDER2_X[0], 0, SPEEDER2_W[0], 41 );
						drawImage( g, use_image( IMAGE_SPEEDER3 ), 120 - (SPEEDER3_W[0] / 2), 55, SPEEDER3_X[0], 0, SPEEDER3_W[0], 43 );
						drawImage( g, use_image( IMAGE_SPEEDER1 ), 200 - (SPEEDER1_W[0] / 2), 55, SPEEDER1_X[0], 0, SPEEDER1_W[0], 43 );

						drawImage( g, use_image( IMAGE_TITLE ), (240 - TEXT_COM_W) / 2, 105, TEXT_COM_X, TEXT_COM_Y, TEXT_COM_W, TEXT_COM_H );

						drawImage( g, use_image( IMAGE_TITLE ), 150, 140, TEXT_SLOW_X, TEXT_SLOW_Y, TEXT_SLOW_W, TEXT_SLOW_H );
						drawImage( g, use_image( IMAGE_TITLE ), 150, 160, TEXT_NORMAL_X, TEXT_NORMAL_Y, TEXT_NORMAL_W, TEXT_NORMAL_H );
						drawImage( g, use_image( IMAGE_TITLE ), 150, 180, TEXT_QUICK_X, TEXT_QUICK_Y, TEXT_QUICK_W, TEXT_QUICK_H );

						break;
					}

					g.setColor( COLOR_W );
					g.setStrokeWidth( 1 );
					drawButton( g, MYLAYOUT_LEFT );
					drawButton( g, MYLAYOUT_RIGHT );
					if( state == STATE_SELECT ){
						drawButton( g, MYLAYOUT_SELECT );
					}

					setCMYColor( _elapse % 3 );
					g.drawRect( 80, 45, 80, 80 );
					drawImage( g, use_image( IMAGE_TITLE ),  56, 81, 150, 0, 5, 9 );
					drawImage( g, use_image( IMAGE_TITLE ), 180, 81, 155, 0, 5, 9 );

					if( state == STATE_SELECT ){
						if( (_elapse % WAIT_1) <= (WAIT_1 / 2) ){
							x = (240 - (TEXT_PRESS_W + 8 + TEXT_BUTTON_W)) / 2;
							drawImage( g, use_image( IMAGE_TITLE ), x, 225, TEXT_PRESS_X, TEXT_PRESS_Y, TEXT_PRESS_W, TEXT_PRESS_H ); x += (TEXT_PRESS_W + 8);
							drawImage( g, use_image( IMAGE_TITLE ), x, 225, TEXT_BUTTON_X, TEXT_BUTTON_Y, TEXT_BUTTON_W, TEXT_BUTTON_H );
						}
					} else {
						drawImage( g, use_image( IMAGE_TITLE ), (240 - TEXT_LOADING_W) / 2, 225, TEXT_LOADING_X, TEXT_LOADING_Y, TEXT_LOADING_W, TEXT_LOADING_H );
					}
				}
				g.unlock();

				if( state == STATE_SELECT_LOADING ){
					set_state( STATE_READY );
				}

				break;
			case STATE_READY:
				// 描画
				g.lock();
				stage.draw( true );
				wave.draw();
				speeder[0].draw( true );
				if( (level < 2) || (level == 7) ){
					speeder[1].draw( true );
					speeder[2].draw( true );
				}
				switch( level ){
				case 4:
					drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_AUTOSTEERING_W) / 2, 80, TEXT_AUTOSTEERING_X, TEXT_AUTOSTEERING_Y, TEXT_AUTOSTEERING_W, TEXT_AUTOSTEERING_H );
					break;
				case 5:
					drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_AUTOSHIELD_W) / 2, 80, TEXT_AUTOSHIELD_X, TEXT_AUTOSHIELD_Y, TEXT_AUTOSHIELD_W, TEXT_AUTOSHIELD_H );
					break;
				}
				drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_READY_W) / 2, (240 - TEXT_READY_H) / 2, TEXT_READY_X, TEXT_READY_Y, TEXT_READY_W, TEXT_READY_H );
				drawStatus( true );
				g.unlock();

				// 一定時間過ぎたらゲーム開始
				if( _elapse > WAIT_2 ){
					set_state( STATE_PLAY );
				}

				break;
			case STATE_PLAY:
				if( !pause ){
					int i;

					time[0]++;

					// 更新前の座標を保持
					if( (level < 2) || (level == 7) ){
						old_y[0] = speeder[1].dsp_y();
						old_y[1] = speeder[2].dsp_y();
					}

					// 更新
					stage.update();
					wave.update();

					if( (level < 2) || (level == 7) ){
						for( i = 0; i < 2; i++ ){
							if( shield_wait[i] > 0 ){
								shield_wait[i]--;
								if( shield_wait[i] == 0 ){
									speeder[i + 1].shield( shield_col[i] );
								}
							}

							new_y[i] = speeder[i + 1].dsp_y();
							if( (old_y[i] > -48) && (new_y[i] <= -48) ){
								speeder[i + 1].out( wave.top_x() );
							} else if( (old_y[i] <= -48) && (new_y[i] > -48) ){
								speeder[i + 1].in( wave.top_x() );
							} else if( (old_y[i] < 320) && (new_y[i] >= 320) ){
								speeder[i + 1].out( wave.bottom_x() );
							} else if( (old_y[i] >= 320) && (new_y[i] < 320) ){
								speeder[i + 1].in( wave.bottom_x() );
							}

							if( new_y[i] <= -48 ){
								if( speeder[0].speed() < 310 ){
									speeder[i + 1].speed_limit( 300 );
								} else {
									speeder[i + 1].speed_limit( speeder[0].speed() - 10 );
								}
							}
						}
					}

					// スピーダーの移動
					if( level == 4 ){
						switch( speeder[0].auto() ){
						case AUTO_MOVED_INERTIA: speeder[0].auto( AUTO_INERTIA ); break;
						case AUTO_MOVED_NEUTRAL: speeder[0].auto( AUTO_NEUTRAL ); break;
						case AUTO_INERTIA: speeder[0].inertia( false ); break;
						case AUTO_NEUTRAL: speeder[0].inertia( true  ); break;
						}
					} else {
						if     ( (layoutState & (1 << MYLAYOUT_LEFT )) != 0 ) speeder[0].left ();
						else if( (layoutState & (1 << MYLAYOUT_RIGHT)) != 0 ) speeder[0].right();
						else if( sensor_f && ((int)sensor.getRoll() / 3 > 0) ) speeder[0].left ();
						else if( sensor_f && ((int)sensor.getRoll() / 3 < 0) ) speeder[0].right();
						else speeder[0].inertia( neutral );
						if( (level < 2) || (level == 7) ){
							for( i = 1; i < 3; i++ ){
								switch( speeder[i].auto() ){
								case AUTO_MOVED_INERTIA: speeder[i].auto( AUTO_INERTIA ); break;
								case AUTO_MOVED_NEUTRAL: speeder[i].auto( AUTO_NEUTRAL ); break;
								case AUTO_INERTIA: speeder[i].inertia( false ); break;
								case AUTO_NEUTRAL: speeder[i].inertia( true  ); break;
								}
							}
						}
					}
				}

				// 描画
				g.lock();
				stage.draw( false );
				{
					int cnt = wave.draw();
					if( (cnt < 0) || (stage.offset_x() < 0) ){
						if( (elapse() % 2) == 0 ){
							drawImage( g, use_image( IMAGE_BAR ), 10, 102, 200, 0, 40, 36 );
						}
					} else if( (cnt > 0) || (stage.offset_x() > 0) ){
						if( (elapse() % 2) == 0 ){
							drawImage( g, use_image( IMAGE_BAR ), 190, 102, 200, 36, 40, 36 );
						}
					}
				}
				speeder[0].draw( false );
				if( (level < 2) || (level == 7) ){
					speeder[1].draw( false );
					speeder[2].draw( false );
				}
				if( level != 6 ){
					if( _elapse < WAIT_2 ){
						drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_START_W) / 2, (240 - TEXT_START_H) / 2, TEXT_START_X, TEXT_START_Y, TEXT_START_W, TEXT_START_H );
					}
				}
				if( pause ){
					if( (_elapse_p % WAIT_1) <= (WAIT_1 / 2) ){
						drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_PAUSE_W) / 2, 125, TEXT_PAUSE_X, TEXT_PAUSE_Y, TEXT_PAUSE_W, TEXT_PAUSE_H );
					}
				}
				drawStatus( false );
				g.unlock();

				if( level != 6 ){
					if( finish ){
						// ステージクリア
						set_state( STATE_CLEAR );
					}
				} else {
					if( speeder[0].speed() == 0 ){
						// ゲーム終了
						set_state( STATE_STOP );
					}
				}

				break;
			case STATE_CLEAR:
				{
					int i;

					// 更新前の座標を保持
					if( (level < 2) || (level == 7) ){
						old_y[0] = speeder[1].dsp_y();
						old_y[1] = speeder[2].dsp_y();
					}

					// 更新
					stage.update();
					wave.update();

					if( (level < 2) || (level == 7) ){
						for( i = 0; i < 2; i++ ){
							new_y[i] = speeder[i + 1].dsp_y();
							if( (old_y[i] <= -48) && (new_y[i] > -48) ){
								speeder[i + 1].in( speeder[0].x() - 88 );
							} else if( (old_y[i] >= 320) && (new_y[i] < 320) ){
								speeder[i + 1].in( speeder[0].x() - 88 );
							}
						}
					}

					// スピーダーの移動
					speeder[0].inertia( true );
					if( (level < 2) || (level == 7) ){
						speeder[1].inertia( true );
						speeder[2].inertia( true );
					}
				}

				// 描画
				g.lock();
				stage.draw( false );
				wave.draw();
				speeder[0].draw( false );
				if( (level < 2) || (level == 7) ){
					speeder[1].draw( false );
					speeder[2].draw( false );
				}
				if( (level < 2) || (level == 7) ){
					int y = (240 - (TEXT_FINISH_H + 10 + TEXT_1ST_H + 10 + TEXT_NEWRECORD_H)) / 2;
					drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_FINISH_W) / 2, y, TEXT_FINISH_X, TEXT_FINISH_Y, TEXT_FINISH_W, TEXT_FINISH_H );
					y += (TEXT_FINISH_H + 10);
					switch( ranking ){
					case 1:
						drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_1ST_W) / 2, y, TEXT_1ST_X, TEXT_1ST_Y, TEXT_1ST_W, TEXT_1ST_H );
						break;
					case 2:
						drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_2ND_W) / 2, y, TEXT_2ND_X, TEXT_2ND_Y, TEXT_2ND_W, TEXT_2ND_H );
						break;
					case 3:
						drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_3RD_W) / 2, y, TEXT_3RD_X, TEXT_3RD_Y, TEXT_3RD_W, TEXT_3RD_H );
						break;
					}
					y += (TEXT_1ST_H + 10);
					if( new_time ){
						drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_NEWRECORD_W) / 2, y, TEXT_NEWRECORD_X, TEXT_NEWRECORD_Y, TEXT_NEWRECORD_W, TEXT_NEWRECORD_H );
					}
				} else {
					drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_FINISH_W) / 2, (240 - TEXT_FINISH_H) / 2, TEXT_FINISH_X, TEXT_FINISH_Y, TEXT_FINISH_W, TEXT_FINISH_H );
					if( new_time ){
						drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_NEWRECORD_W) / 2, 140, TEXT_NEWRECORD_X, TEXT_NEWRECORD_Y, TEXT_NEWRECORD_W, TEXT_NEWRECORD_H );
					}
				}
				drawStatus( false );
				g.unlock();

				// 一定時間過ぎたらタイトル画面へ
				if( _elapse > WAIT_3 ){
					set_state( STATE_TITLE );
				}

				break;
			case STATE_STOP:
				// スピーダーの移動
				speeder[0].inertia( true );

				// 描画
				g.lock();
				stage.draw( false );
				wave.draw();
				speeder[0].draw( false );
				{
					int x = (240 - (TEXT_STOP_W + 3 + TEXT_PED_W)) / 2;
					int y = (240 - TEXT_STOP_H) / 2;
					drawImage( g, use_image( IMAGE_STATUS ), x, y, TEXT_STOP_X, TEXT_STOP_Y, TEXT_STOP_W, TEXT_STOP_H ); x += (TEXT_STOP_W + 3);
					drawImage( g, use_image( IMAGE_STATUS ), x, y, TEXT_PED_X, TEXT_PED_Y, TEXT_PED_W, TEXT_PED_H );
				}
				if( new_distance ){
					drawImage( g, use_image( IMAGE_STATUS ), (240 - TEXT_NEWRECORD_W) / 2, 140, TEXT_NEWRECORD_X, TEXT_NEWRECORD_Y, TEXT_NEWRECORD_W, TEXT_NEWRECORD_H );
				}
				drawStatus( false );
				g.unlock();

				// 一定時間過ぎたらタイトル画面へ
				if( _elapse > WAIT_3 ){
					set_state( STATE_TITLE );
				}

				break;
			}

			if( (state != STATE_PLAY) || !pause ){
				_elapse++;
			}
			if( pause ){
				_elapse_p++;
			} else if( _elapse_s > 0 ){
				_elapse_s++;
			}

			_g.lock();

			_g.setColor( _Graphics.getColorOfRGB( 64, 64, 64 ) );
			_g.fillRect( 0, 0, getWidth(), getHeight() );

			_g.drawScaledImage( window, getWindowLeft(), getWindowTop(), getWindowRight() - getWindowLeft(), getWindowBottom() - getWindowTop(), 0, 0, 240, height );

			_g.setColor( COLOR_W );
			_g.setStrokeWidth( 3 );
			_g.setFontSize( 32 );
			switch( state ){
			case STATE_LAUNCH:
			case STATE_TITLE_LOADING:
			case STATE_SELECT_LOADING:
				break;
			case STATE_TITLE:
				drawScreenButton( _g, MYLAYOUT_BACK, "終了" );
				break;
			case STATE_SELECT:
				drawScreenButton( _g, MYLAYOUT_BACK, "戻る" );
				break;
			default:
				drawScreenButton( _g, MYLAYOUT_PAUSE, "中断" );
				drawScreenButton( _g, MYLAYOUT_BACK, "戻る" );
				_g.setColor( COLOR_C );
				drawScreenButton2( _g, MYLAYOUT_SHIELD_0 );
				_g.setColor( COLOR_M );
				drawScreenButton2( _g, MYLAYOUT_SHIELD_1 );
				_g.setColor( COLOR_Y );
				drawScreenButton2( _g, MYLAYOUT_SHIELD_2 );
				break;
			}

//			_g.setColor( COLOR_W );
//			_g.setStrokeWidth( 1 );
//			_g.setFontSize( 16 );
//			drawLayout( _g );

			_g.unlock();

			unlock_state();
		}

		/**
		 * キー入力処理
		 */
		public void processEvent( int type, int param ){
			if( processingEvent ) return;
			processingEvent = true;

			if( type == LAYOUT_DOWN_EVENT ){
				switch( state ){
				case STATE_LAUNCH:
					break;
				case STATE_TITLE:
					switch( param ){
					case MYLAYOUT_BACK:
						terminate_f = true;
						break;
					case MYLAYOUT_LEFT1:
					case MYLAYOUT_RIGHT1:
						lock_state();
						sensor_f = sensor_f ? false : true;
						unlock_state();
						break;
					case MYLAYOUT_LEFT2:
					case MYLAYOUT_RIGHT2:
						lock_state();
						neutral = neutral ? false : true;
						unlock_state();
						break;
					case MYLAYOUT_LEFT3:
						lock_state();
						level--; if( level < 0 ) level = level_max();
						unlock_state();
						break;
					case MYLAYOUT_RIGHT3:
						lock_state();
						level++; if( level > level_max() ) level = 0;
						unlock_state();
						break;
					case MYLAYOUT_SELECT:
						lock_state();
						set_state( STATE_TITLE_LOADING );
						unlock_state();
						break;
					}
					break;
				case STATE_TITLE_LOADING:
					break;
				case STATE_SELECT:
					switch( param ){
					case MYLAYOUT_LEFT:
						lock_state();
						player--; if( player < 0 ) player = 2;
						unlock_state();
						break;
					case MYLAYOUT_RIGHT:
						lock_state();
						player++; if( player > 2 ) player = 0;
						unlock_state();
						break;
					case MYLAYOUT_SELECT:
						lock_state();
						set_state( STATE_SELECT_LOADING );
						unlock_state();
						break;
					case MYLAYOUT_BACK:
						lock_state();
						set_state( STATE_TITLE );
						unlock_state();
						break;
					}
					break;
				case STATE_SELECT_LOADING:
					break;
				default:
					switch( param ){
					case MYLAYOUT_PAUSE:
						lock_state();
						pause = pause ? false : true; if( pause ) _elapse_p = 0;
						unlock_state();
						break;
					case MYLAYOUT_BACK:
						lock_state();
						set_state( STATE_TITLE );
						unlock_state();
						break;
					case MYLAYOUT_SHIELD_0:
						if( level != 5 ){
							if( !pause ){
								if( boost && (speeder[0]._shield == 0) ){
									_elapse_b = _elapse;
									boost = false;
									speeder[0].speed_up( 400 );
								} else {
									speeder[0].shield( 0 );
								}
							}
						}
						break;
					case MYLAYOUT_SHIELD_1:
						if( level != 5 ){
							if( !pause ){
								if( boost && (speeder[0]._shield == 1) ){
									_elapse_b = _elapse;
									boost = false;
									speeder[0].speed_up( 400 );
								} else {
									speeder[0].shield( 1 );
								}
							}
						}
						break;
					case MYLAYOUT_SHIELD_2:
						if( level != 5 ){
							if( !pause ){
								if( boost && (speeder[0]._shield == 2) ){
									_elapse_b = _elapse;
									boost = false;
									speeder[0].speed_up( 400 );
								} else {
									speeder[0].shield( 2 );
								}
							}
						}
						break;
					}
					break;
				}
			} else if( type == LAYOUT_UP_EVENT ){
				if( (state == STATE_TITLE) && (param == MYLAYOUT_BACK) ){
					if( terminate_f ){
						terminate();
					}
				}
			} else if( type == TOUCH_UP_EVENT ){
				terminate_f = false;
			}

			processingEvent = false;
		}
	}

	/**
	 * ステージ
	 */
	class Stage {
		int _back = -1;		// 背景の種類
		int _col;			// ウェーブの色
		int old_distance;
		int x, _move_x, _offset_x;
		boolean counter;
		int bar;
		Vector<Star> star;		// 星情報

		/**
		 * コンストラクタ
		 */
		Stage(){
			star = new Vector<Star>();
		}

		/**
		 * ステージデータ構築
		 */
		public void create(){
			int i;

			star.removeAllElements();
			System.gc();

			int tmp;
			while( true ){
				tmp = (rand.nextInt() % 4) + 3;
				if( tmp != _back ){
					_back = tmp;
					break;
				}
			}

			_col = (rand.nextInt() % 2) + 1;

			// シールド変更
			if( level == 5 ){
				speeder[0].shield( _col );
			}
			if( (level < 2) || (level == 7) ){
				speeder[1].shield( _col );
				speeder[2].shield( _col );
			}

			for( i = 0; i < height; i += 3 ){
				star.addElement( new Star( 120 + (rand.nextInt() % 360), i ) );
			}

			wave.add_bar( -100, 192, 3, (level != 6) ? 9 : 0, false );
			bar = 1;
			for( i = 172; i > 0; i -= 20 ){
				wave.add_bar( -100, i, _col );
				bar++;
			}

			old_distance = speeder[0].distance();
			x            = -100;
			_move_x      = 0;
			_offset_x    = 0;
			counter      = false;
		}

		/**
		 * ステージデータ更新
		 */
		public void update(){
			int i;

			for( i = star.size() - 1; i >= 0; i-- ){
				Star tmp = (Star)star.elementAt( i );
				if( !tmp.update() ){
					star.removeElementAt( i );
					System.gc();
				}
			}

			if( speeder[0].speed() > 0 ){
				star.addElement( new Star( 120 + (rand.nextInt() % 360), 0 ) );
			}

			if( level != 6 ){
				for( i = 0; i < 3; i++ ){
					if(
					((i == 0) && wave.clear()) ||
					((i != 0) && (speeder[i].distance() > (DISTANCE + 2400)))
					){
						speeder[i].speed_down( 10 );
					} else {
						speeder[i].speed_up( 1 );
					}
					speeder[i].add_distance( speeder[i].speed() );
					if( (level >= 2) && (level <= 6) ) break;
				}
			} else {
				if( speeder[0].speed() < 300 ){
					speeder[0].speed_down( 5 );
				}
				speeder[0].add_distance( speeder[0].speed() );
			}

			if( counter ){
				if( (speeder[0].distance() - old_distance) >= 240 ){
					wave.add_bar( x, 0, 3, 9 - speeder[0].distance() / (DISTANCE / 9), false );
					bar++;
					if( bar >= 12 ){
						bar = 0;
						counter = false;
					}
					old_distance = speeder[0].distance();
				}
			} else {
				if( bar > 20 ){
					bar = 0;

					change_col = (level == 7) ? 1 : 2;

					switch( level ){
					case 0:
					case 2:
					case 4:
						_move_x += (12 * (rand.nextInt() % 2));
						if( _move_x < -12 ) _move_x = -12;
						if( _move_x >  12 ) _move_x =  12;
						break;
					case 1:
					case 3:
					case 5:
					case 6:
						_move_x += (12 * (rand.nextInt() % 3));
						if( _move_x < -24 ) _move_x = -24;
						if( _move_x >  24 ) _move_x =  24;
						break;
					case 7:
						_offset_x = (100 * (rand.nextInt() % 2));
						break;
					}
				} else if( (level == 7) && (bar > 5) ){
					if( change_col == 1 ) change_col = 2;

					x += _offset_x;
					_offset_x = 0;
				}

				if( change_col == 2 ){
					change_col = 0;

					int old_col = _col;
					_col = (rand.nextInt() % 2) + 1;
					if( _col != old_col ){
						if( (level < 2) || (level == 7) ){
							// シールド切り替え速度計測開始
							_elapse_s = 1;

							// シールド変更
							for( i = 0; i < 2; i++ ){
								if( shield_wait[i] == 0 ){
									shield_wait[i] = shield_lag[i];
									shield_col [i] = _col;
								}
							}
						}
					}
				}

				if( (speeder[0].distance() - old_distance) >= 500 ){
					if( level != 6 ){
						if( (speeder[0].distance() / (DISTANCE / 9)) != (old_distance / (DISTANCE / 9)) ){
							wave.add_bar( x, 0, 3, 9 - (speeder[0].distance() / (DISTANCE / 9)), true );
							if( (9 - (speeder[0].distance() / (DISTANCE / 9))) > 0 ){
								bar = 1;
								counter = true;
								if( (level < 2) || (level == 7) ){
									boost = true;
								}
							}
						} else if( speeder[0].distance() < DISTANCE ){
							bar += (speeder[0].distance() - old_distance) / 500;
							x += _move_x;
							wave.add_bar( x, 0, _col );
						}
					} else {
						bar += (speeder[0].distance() - old_distance) / 500;
						x += _move_x;
						wave.add_bar( x, 0, _col );
					}
					old_distance = speeder[0].distance();
				}
			}
		}

		// ウェーブの色を確認
		public int col(){ return _col; }

		public int move_x(){ return _move_x; }

		public int offset_x(){ return _offset_x; }

		/**
		 * 描画
		 */
		public void draw( boolean ready ){
			if( _back < 5 ){
				drawImage( g, use_image( IMAGE_BACK ), 0,   0 );
				drawImage( g, use_image( IMAGE_BACK ), 0, 135 );
			}

			int distance;
			distance = speeder[0].distance(); if( distance > DISTANCE ) distance = DISTANCE;
			switch( _back ){
			case 0:
				drawImage( g, use_image( IMAGE_FORE1 ), 10, (120 * distance / DISTANCE) - 120 );
				break;
			case 1:
				drawImage( g, use_image( IMAGE_FORE2 ), (120 * distance / DISTANCE) - 120, 0 );
				break;
			case 2:
				drawImage( g, use_image( IMAGE_FORE3 ), 0 - (80 * distance / DISTANCE), height - 188 );
				break;
			case 3:
				drawImage( g, use_image( IMAGE_FORE4 ), 120 - (240 * distance / DISTANCE), 0 );
				break;
			case 4:
				drawImage( g, use_image( IMAGE_FORE4 ), 0, (240 * distance / DISTANCE) - 120 );
				break;
			case 5:
				drawImage( g, use_image( IMAGE_FORE5A ), 0, ((480 - height) * distance / DISTANCE) - (480 - height)       );
				drawImage( g, use_image( IMAGE_FORE5B ), 0, ((480 - height) * distance / DISTANCE) - (480 - height) + 240 );
				break;
			case 6:
				drawImage( g, use_image( IMAGE_FORE6A ), 0 - (140 * distance / DISTANCE)      , 0 );
				drawImage( g, use_image( IMAGE_FORE6B ), 0 - (140 * distance / DISTANCE) + 190, 0 );
				break;
			}

			g.setColor( COLOR_W );
			int h = ready ? 0 : (speeder[0].speed() / 50);
			for( int i = star.size() - 1; i >= 0; i-- ){
				Star tmp = (Star)star.elementAt( i );
				if( (tmp.x() >= 0) && (tmp.x() < 240) ){
					g.drawLine( tmp.x(), tmp.y(), tmp.x(), tmp.y() + h );
				}
			}
		}
	}

	/**
	 * ウェーブ
	 */
	class Wave {
		Vector<Bar> bar;	// バー情報

		/**
		 * コンストラクタ
		 */
		Wave(){
			bar = new Vector<Bar>();
		}

		/**
		 * 構築
		 */
		public void create(){
			bar.removeAllElements();
			System.gc();
		}

		/**
		 * バーを登録する
		 */
		public void add_bar( int x, int y, int col, int count, boolean border ){
			bar.addElement( new Bar( x, y, col, count, border ) );
		}
		public void add_bar( int x, int y, int col ){
			bar.addElement( new Bar( x, y, col, 0, false ) );
		}

		/**
		 * バーが無くなったかどうか
		 */
		public boolean clear(){
			if( bar.size() <= 0 ) return true;
			return false;
		}

		public int top_x(){
			int x = 0;
			int y = 240;
			for( int i = bar.size() - 1; i >= 0; i-- ){
				Bar tmp = (Bar)bar.elementAt( i );
				if( tmp.y() < y ){
					y = tmp.y();
					x = tmp.x();
				}
			}
			return x;
		}
		public int bottom_x(){
			int x = 0;
			int y = 0;
			for( int i = bar.size() - 1; i >= 0; i-- ){
				Bar tmp = (Bar)bar.elementAt( i );
				if( tmp.y() > y ){
					y = tmp.y();
					x = tmp.x();
				}
			}
			return x;
		}

		/**
		 * ウェーブデータ更新
		 */
		public void update(){
			for( int i = bar.size() - 1; i >= 0; i-- ){
				Bar tmp = (Bar)bar.elementAt( i );
				if( !tmp.update() ){
					bar.removeElementAt( i );
					System.gc();
				}
			}
		}

		/**
		 * 描画
		 */
		public int draw(){
			int cnt = 0;
			for( int i = bar.size() - 1; i >= 0; i-- ){
				Bar tmp = (Bar)bar.elementAt( i );
				if( tmp.col() == 3 ){
					cnt += drawImage( g, use_image( IMAGE_BAR ), 108 - (speeder[0].x() - tmp.x()), tmp.y(), 0, 36 * (elapse() % 2) + 12 * (elapse() % 3), 200, 12 );
					drawImage( g, use_image( IMAGE_BAR ), 108 - (speeder[0].x() - tmp.x()) - 24, tmp.y() - 6, 24 * tmp.count(), 72, 24, 24 );
					drawImage( g, use_image( IMAGE_BAR ), 108 - (speeder[0].x() - tmp.x()) + 200, tmp.y() - 6, 24 * tmp.count(), 72, 24, 24 );
				} else {
					cnt += drawImage( g, use_image( IMAGE_BAR ), 108 - (speeder[0].x() - tmp.x()), tmp.y(), 0, 36 * (elapse() % 2) + 12 * tmp.col(), 200, 12 );
				}
			}
			if( Math.abs( cnt ) == bar.size() ){
				return cnt;
			}
			return 0;
		}
	}

	/**
	 * 星
	 */
	class Star extends Object {
		int _x, _y;
		Star(int x, int y){ _x = x; _y = y; }
		public int x(){ return _x; }
		public int y(){ return _y; }
		public boolean update(){
			_x -= speeder[0].direction() / 7;
			if( speeder[0].speed() > 0 ){
				_y += ((speeder[0].speed() / 50) + 1);
			}
			if( _y >= height ){
				return false;
			}
			return true;
		}
	}

	/**
	 * バー
	 */
	class Bar extends Object {
		int _x, _y;
		int _col;
		int _count;
		boolean _border;
		boolean[] _hit;
		Bar( int x, int y, int col, int count, boolean border ){
			_x      = x;
			_y      = y;
			_col    = col;
			_count  = count;
			_border = border;

			_hit = new boolean[3];
			_hit[0] = false;
			_hit[1] = false;
			_hit[2] = false;
		}
		public int x(){ return _x; }
		public int y(){ return _y; }
		public int col(){ return _col; }
		public int count(){ return _count; }
		public boolean update(){
			_y += speeder[0].speed() / 10;

			int y;
			int dsp_x0, dsp_y0;
			int dsp_x1, dsp_y1;
			int dsp_x2, dsp_y2;
			for( int i = 0; i < 3; i++ ){
				y = speeder[i].dsp_y();
				if( (y > -48) && (y < 320) && (_y > y) && !_hit[i] ){
					_hit[i] = true;

					if( i != 0 ){
						// 移動
						if( speeder[i].x() <= (_x + 38) ){
							speeder[i].right();
							speeder[i].auto( AUTO_MOVED_INERTIA );
						} else if( speeder[i].x() >= (_x + 138) ){
							speeder[i].left();
							speeder[i].auto( AUTO_MOVED_INERTIA );
						} else if( speeder[i].x() != (_x + 88) ){
							speeder[i].set_direction( ((_x + 88) - speeder[i].x()) / 10 );
							speeder[i].auto( AUTO_MOVED_NEUTRAL );
						} else {
							speeder[i].auto( AUTO_NEUTRAL );
						}

						// スライド
						dsp_x0 = speeder[0].dsp_x();
						dsp_y0 = speeder[0].dsp_y();
						dsp_x1 = speeder[1].dsp_x();
						dsp_y1 = speeder[1].dsp_y();
						dsp_x2 = speeder[2].dsp_x();
						dsp_y2 = speeder[2].dsp_y();
						switch( i ){
						case 1:
							if(
							( dsp_x1       <= (dsp_x0 + 24)) &&
							( dsp_y1       <= (dsp_y0 + 48)) &&
							((dsp_x1 + 24) >=  dsp_x0      ) &&
							((dsp_y1 + 48) >=  dsp_y0      )
							){
								speeder[i].slide( (speeder[0].x() < (_x + 88)) ? 1 : -1 );
							} else if(
							( dsp_x1       <= (dsp_x2 + 24)) &&
							( dsp_y1       <= (dsp_y2 + 48)) &&
							((dsp_x1 + 24) >=  dsp_x2      ) &&
							((dsp_y1 + 48) >=  dsp_y2      )
							){
								speeder[i].slide( (speeder[2].x() < (_x + 88)) ? 1 : -1 );
							}
							break;
						case 2:
							if(
							( dsp_x2       <= (dsp_x0 + 24)) &&
							( dsp_y2       <= (dsp_y0 + 48)) &&
							((dsp_x2 + 24) >=  dsp_x0      ) &&
							((dsp_y2 + 48) >=  dsp_y0      )
							){
								speeder[i].slide( (speeder[0].x() < (_x + 88)) ? 1 : -1 );
							} else if(
							( dsp_x2       <= (dsp_x1 + 24)) &&
							( dsp_y2       <= (dsp_y1 + 48)) &&
							((dsp_x2 + 24) >=  dsp_x1      ) &&
							((dsp_y2 + 48) >=  dsp_y1      )
							){
								speeder[i].slide( (speeder[1].x() < (_x + 88)) ? 1 : -1 );
							}
							break;
						}
					}

					// 移動
					if( level == 4 ){
						if( speeder[0].x() <= (_x + 38) ){
							speeder[0].right();
							speeder[0].auto( AUTO_MOVED_INERTIA );
						} else if( speeder[0].x() >= (_x + 138) ){
							speeder[0].left();
							speeder[0].auto( AUTO_MOVED_INERTIA );
						} else if( speeder[0].x() != (_x + 88) ){
							speeder[0].set_direction( ((_x + 88) - speeder[0].x()) / 10 );
							speeder[0].auto( AUTO_MOVED_NEUTRAL );
						} else {
							speeder[0].auto( AUTO_NEUTRAL );
						}
					}

					// シールド変更
					if( level == 5 ){
						if( _col < 3 ) speeder[0].shield( _col );
					}

					// スピード変更
					boolean in = true;
					if( (level != 4) && ((_x > speeder[i].x()) || ((_x + 176) < speeder[i].x())) ){
						in = false;
					}
					if( in && ((_col == 3) || (_col == speeder[i].shield())) ){
						if( level != 6 ){
							speeder[i].speed_up();
							if( _col == 3 ){
								speeder[i].speed_up();
							}
						}
					} else {
						if( level != 6 ){
							speeder[i].speed_down();
						} else {
							speeder[i].speed_down( 5 );
						}
					}

					if( i == 0 ){
						// スピード変更
						if( (level < 2) || (level == 7) ){
							if( speeder[1].out() ){
								if( (speeder[1].dsp_y() <= -48) && (shield_wait[0] > 0) ){
									speeder[1].speed_down();
								} else {
									speeder[1].speed_up();
								}
							}
							if( speeder[2].out() ){
								if( (speeder[2].dsp_y() <= -48) && (shield_wait[1] > 0) ){
									speeder[2].speed_down();
								} else {
									speeder[2].speed_up();
								}
							}
						}

						// ラップタイム計測
						if( _border ){
							_elapse_l = _elapse;
							lap = 9 - _count;
							time[lap] = time[0];
							if( lap == 1 ){
								lap_time = time[lap] - best_time[index_b()][lap];
							} else {
								lap_time = (time[lap] - time[lap - 1]) - best_time[index_b()][lap];
							}
							dsp_lap = true;
							if( lap == 9 ){
								finish = true;
							}
						}
					}
				}
				if( (level >= 2) && (level <= 6) ) break;
			}

			if( _y >= 320 ){
				return false;
			}
			return true;
		}
	}

	/**
	 * スピーダー
	 */
	class MySpeeder {
		boolean _jiki;	// 自機かどうか
		int _type;		// 種類
		int _auto;		// 自動移動の種類
		int _distance;	// 走行距離
		int _speed;		// スピード
		int _x, off_x;	// 位置
		boolean _out;	// 画面外かどうか
		int _direction;	// 移動の状態
		int _shield;	// シールドの状態

		/**
		 * コンストラクタ
		 */
		MySpeeder(){
		}

		/**
		 * 初期化
		 */
		public void init( boolean jiki, int type, int speed, int x ){
			_jiki      = jiki;
			_type      = type;
			_auto      = AUTO_INERTIA;
			_distance  = 0;
			_speed     = speed;
			_x         = x - 12;
			_out       = false;
			_direction = 0;
			_shield    = 0;
		}

		/**
		 * 左移動
		 */
		public void left(){
			switch( _type ){
			case SPEEDER1: _direction -= 1; break;
			case SPEEDER2: _direction -= 1; break;
			case SPEEDER3: _direction -= 2; break;
			case SPEEDER4: _direction -= 1; break;
			case SPEEDER5: _direction -= 1; break;
			}
			if( _direction < -15 ) _direction = -15;
			_x += _direction;
		}

		/**
		 * 右移動
		 */
		public void right(){
			switch( _type ){
			case SPEEDER1: _direction += 1; break;
			case SPEEDER2: _direction += 1; break;
			case SPEEDER3: _direction += 2; break;
			case SPEEDER4: _direction += 1; break;
			case SPEEDER5: _direction += 1; break;
			}
			if( _direction > 15 ) _direction = 15;
			_x += _direction;
		}

		/**
		 * 慣性移動
		 */
		public void inertia( boolean neutral ){
			if( neutral ){
				if( _direction > 0 ){
					_direction--;
				} else if( _direction < 0 ){
					_direction++;
				}
			}
			_x += _direction;
		}

		/**
		 *
		 */
		public void set_direction( int target ){
			if( _direction < target ){
				right();
			} else if( _direction > target ){
				left();
			}
		}

		public void slide( int val ){ _x += val; }

		public void out( int bar_x ){
			off_x = _x - bar_x;
			_out = true;
		}
		public void in( int bar_x ){
			if      ( off_x <   0 ) off_x =   0;
			else if( off_x > 176 ) off_x = 176;
			_x = bar_x + off_x;
			_out = false;
			if( !_jiki ){
				_direction = (stage.move_x() * 15) / 24;
			}
		}
		public boolean out(){ return _out; }

		public int type(){ return _type; }

		// 自動移動の種類を変更
		public void auto( int type ){ _auto = type; }

		// 自動移動の種類を確認
		public int auto(){ return _auto; }

		// 走行距離を変更
		public void add_distance( int val ){ _distance += val; }

		// 走行距離を確認
		public int distance(){ return _distance; }

		/**
		 * スピードを上げる
		 */
		public void speed_up( int val ){
			_speed += val;
			if( _speed > 505 ) _speed = 495;
		}
		public void speed_up(){
			switch( _type ){
			case SPEEDER1:
			case SPEEDER4:
			case SPEEDER5:
				speed_up( 5 );
				break;
			case SPEEDER2:
				speed_up( 4 );
				break;
			case SPEEDER3:
				speed_up( 3 );
				break;
			}
		}

		/**
		 * スピードを落とす
		 */
		public void speed_down( int val ){
			_speed -= val;
			if( _speed < 0 ) _speed = 0;
		}
		public void speed_down(){
			switch( _type ){
			case SPEEDER1:
			case SPEEDER4:
			case SPEEDER5:
				speed_down( 25 );
				break;
			case SPEEDER2:
				speed_down( 20 );
				break;
			case SPEEDER3:
				speed_down( 25 );
				break;
			}
		}

		/**
		 * スピードを制限する
		 */
		public void speed_limit( int val ){
			if( _speed > val ) _speed = val;
		}

		// スピードを確認
		public int speed(){ return _speed; }

		// シールドの状態を変更
		public void shield( int col ){
			_shield = col;
			if( _jiki && (_elapse_s > 0) && (_shield == stage.col()) ){
				if( _elapse_s <= 20 ){
					shield_lag[shield_index] = (shield_lag[shield_index] + _elapse_s) / 2;
					shield_index++; if( shield_index > 1 ) shield_index = 0;
				}
				_elapse_s = 0;
			}
		}

		// シールドの状態を確認
		public int shield(){ return _shield; }

		// 位置を確認
		public int x(){ return _x; }
		public int dsp_x(){ return _jiki ? 108 : (108 + (_x - speeder[0].x())); }
		public int dsp_y(){ return _jiki ? 192 : (192 - (_distance - speeder[0].distance()) / 10); }

		// 移動の状態を確認
		public int direction(){ return _direction; }

		/**
		 * 描画
		 */
		public void draw( boolean ready ){
			int x = dsp_x();
			int y = dsp_y();
			if( y <= -48 ){
				return;
			}
			if( _jiki && ((_elapse - _elapse_b) < WAIT_BOOST) ){
				for( int i = 1; i < 10; i++ ){
					drawImage( g, use_image( IMAGE_SHIELD ), x - _direction * i / 10, y + _speed * i / 50, 24 * _shield, 0, 24, 24 );
				}
			} else if( !ready && ((elapse() % 2) == 0) ){
				drawImage( g, use_image( IMAGE_SHIELD ), x -  _direction     , y + (_speed / 10), 24 * _shield, 96, 24, 48 );
				drawImage( g, use_image( IMAGE_SHIELD ), x - (_direction / 2), y + (_speed / 20), 24 * _shield, 48, 24, 48 );
			}
			drawImage( g, use_image( IMAGE_SHIELD ), x, y, 24 * _shield, 0, 24, 48 );
			if( _direction < 0 ){
				int d = 0 - _direction;
				switch( _type ){
				case SPEEDER1:
					drawImage( g, use_image( IMAGE_SPEEDER1 ),
						x + 12 - (SPEEDER1_W[d] / 2), y + 3,
						SPEEDER1_X_M[d], 43,
						SPEEDER1_W[d], 43
						);
					break;
				case SPEEDER2:
				case SPEEDER4:
					drawImage( g, use_image( IMAGE_SPEEDER2 ),
						x + 12 - (SPEEDER2_W[d] / 2), y + 3,
						SPEEDER2_X_M[d], 41,
						SPEEDER2_W[d], 41
						);
					break;
				case SPEEDER3:
				case SPEEDER5:
					drawImage( g, use_image( IMAGE_SPEEDER3 ),
						x + 12 - (SPEEDER3_W[d] / 2), y + 3,
						SPEEDER3_X_M[d], 43,
						SPEEDER3_W[d], 43
						);
					break;
				}
			} else {
				int d = _direction;
				switch( _type ){
				case SPEEDER1:
					drawImage( g, use_image( IMAGE_SPEEDER1 ),
						x + 12 - (SPEEDER1_W[d] / 2), y + 3,
						SPEEDER1_X[d], 0,
						SPEEDER1_W[d], 43
						);
					break;
				case SPEEDER2:
				case SPEEDER4:
					drawImage( g, use_image( IMAGE_SPEEDER2 ),
						x + 12 - (SPEEDER2_W[d] / 2), y + 3,
						SPEEDER2_X[d], 0,
						SPEEDER2_W[d], 41
						);
					break;
				case SPEEDER3:
				case SPEEDER5:
					drawImage( g, use_image( IMAGE_SPEEDER3 ),
						x + 12 - (SPEEDER3_W[d] / 2), y + 3,
						SPEEDER3_X[d], 0,
						SPEEDER3_W[d], 43
						);
					break;
				}
			}
		}
	}
}
