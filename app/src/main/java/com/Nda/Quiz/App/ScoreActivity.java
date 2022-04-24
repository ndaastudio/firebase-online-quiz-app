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
import android.support.design.widget.FloatingActionButton;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class ScoreActivity extends AppCompatActivity {
	
	
	private FloatingActionButton _fab;
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private TextView textview4;
	
	private Intent iMainSelesai = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.score);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		textview4 = (TextView) findViewById(R.id.textview4);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iMainSelesai.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				iMainSelesai.setClass(getApplicationContext(), MainActivity.class);
				startActivity(iMainSelesai);
				finish();
			}
		});
	}
	private void initializeLogic() {
		_GontaGantiBG(SketchwareUtil.getRandom((int)(0), (int)(6)));
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 2, textview1);
		_shape(150, 150, 150, 150, "#FFFFFF", "#FFECEFF1", 2, textview2);
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 2, textview3);
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 2, textview4);
		textview2.setText(getIntent().getStringExtra("total"));
		textview3.setText("Jawaban benar : ".concat(getIntent().getStringExtra("benar")));
		textview4.setText("Jawaban salah : ".concat(getIntent().getStringExtra("salah")));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		
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
	
	
	private void _shape (final double _tl, final double _tr, final double _bl, final double _br, final String _BGcolor, final String _Scolor, final double _Swidth, final View _view) {
		Double tlr = _tl;
		Double trr = _tr;
		Double blr = _bl;
		Double brr = _br;
		Double sw = _Swidth;
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
		s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		s.setCornerRadii(new float[] {tlr.floatValue(),tlr.floatValue(), trr.floatValue(),trr.floatValue(), blr.floatValue(),blr.floatValue(), brr.floatValue(),brr.floatValue()});
		s.setColor(Color.parseColor(_BGcolor));
		s.setStroke(sw.intValue(), Color.parseColor(_Scolor));
		_view.setBackground(s);
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
