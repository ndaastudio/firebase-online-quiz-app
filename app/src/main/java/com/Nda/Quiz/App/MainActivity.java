package com.Nda.Quiz.App;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;

public class MainActivity extends AppCompatActivity {
	
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	
	private Intent iMain = new Intent();
	private SharedPreferences setting;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		setting = getSharedPreferences("setting", Activity.MODE_PRIVATE);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				iMain.setClass(getApplicationContext(), QuizMainActivity.class);
				startActivity(iMain);
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				iMain.setClass(getApplicationContext(), SettingActivity.class);
				startActivity(iMain);
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				iMain.setClass(getApplicationContext(), TambahSoalActivity.class);
				startActivity(iMain);
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				iMain.setClass(getApplicationContext(), AboutActivity.class);
				startActivity(iMain);
			}
		});
	}
	private void initializeLogic() {
		_GontaGantiBG(SketchwareUtil.getRandom((int)(0), (int)(6)));
		_DesainButton(button1, "#FFE91E63", "#FF2196F3", 8);
		_DesainButton(button2, "#FFE91E63", "#FF2196F3", 8);
		_DesainButton(button3, "#FFE91E63", "#FF2196F3", 8);
		_DesainButton(button4, "#FFE91E63", "#FF2196F3", 8);
		imageview1.setElevation(10);
		if (setting.getString("hitung soal", "").equals("") && (setting.getString("hitung benar", "").equals("") && setting.getString("hitung salah", "").equals(""))) {
			setting.edit().putString("hitung soal", "true").commit();
			setting.edit().putString("hitung benar", "true").commit();
			setting.edit().putString("hitung salah", "true").commit();
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	private void _DesainButton (final View _view, final String _color, final String _stroke, final double _radius) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadius((int)_radius);
		gd.setStroke(2, Color.parseColor(_stroke));
		_view.setBackground(gd);
		_view.setElevation(10);
	}
	
	
	private void _GontaGantiBG (final double _random) {
		if (_random == 1) {
			linear1.setBackgroundResource(R.drawable.background_app_3);
		}
		else {
			if (_random == 2) {
				linear1.setBackgroundResource(R.drawable.background_app_4);
			}
			else {
				if (_random == 3) {
					linear1.setBackgroundResource(R.drawable.background_app_2);
				}
				else {
					if (_random == 4) {
						linear1.setBackgroundResource(R.drawable.background_app_5);
					}
					else {
						if (_random == 5) {
							linear1.setBackgroundResource(R.drawable.background_app_7);
						}
						else {
							if (_random == 6) {
								linear1.setBackgroundResource(R.drawable.background_app_8);
							}
						}
					}
				}
			}
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
