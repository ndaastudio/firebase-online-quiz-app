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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.Activity;
import android.content.SharedPreferences;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class QuizMainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private FloatingActionButton _fab;
	private String PilihanJawaban = "";
	private boolean DataLoad = false;
	private String JawabanBenar = "";
	private double SoalBenar = 0;
	private double SoalSalah = 0;
	private double NomorSoal = 0;
	
	private ArrayList<HashMap<String, Object>> list_soal = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear7;
	private LinearLayout linear1;
	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private LinearLayout linear2;
	private TextView textview5;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private TextView textview4;
	private ImageView imageview1;
	private TextView textview6;
	private ImageView imageview2;
	private TextView textview7;
	private ImageView imageview3;
	private TextView textview8;
	private ImageView imageview4;
	private TextView textview9;
	
	private SharedPreferences setting;
	private DatabaseReference DataSoal = _firebase.getReference("Data Soal");
	private ChildEventListener _DataSoal_child_listener;
	private TimerTask DelayGantiSoal;
	private Intent iSelesai = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.quiz_main);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		textview5 = (TextView) findViewById(R.id.textview5);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		textview4 = (TextView) findViewById(R.id.textview4);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview6 = (TextView) findViewById(R.id.textview6);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview7 = (TextView) findViewById(R.id.textview7);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		textview8 = (TextView) findViewById(R.id.textview8);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview9 = (TextView) findViewById(R.id.textview9);
		setting = getSharedPreferences("setting", Activity.MODE_PRIVATE);
		
		linear3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("A");
			}
		});
		
		linear4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("B");
			}
		});
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("C");
			}
		});
		
		linear6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("D");
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("A");
			}
		});
		
		textview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("A");
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("B");
			}
		});
		
		textview7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("B");
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("C");
			}
		});
		
		textview8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("C");
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("D");
			}
		});
		
		textview9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PilihJawaban("D");
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!PilihanJawaban.equals("")) {
					_CekSoalBenarOrSalah();
					_TidakBolehJawab();
					DelayGantiSoal = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									_PickSoal(SketchwareUtil.getRandom((int)(0), (int)(list_soal.size() - 1)));
									NomorSoal++;
									textview1.setText("Pertanyaan ke : ".concat(String.valueOf((long)(NomorSoal))));
									_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear3);
									_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear4);
									_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear5);
									_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear6);
									imageview1.setImageResource(R.drawable.ic_unchecked);
									imageview2.setImageResource(R.drawable.ic_unchecked);
									imageview3.setImageResource(R.drawable.ic_unchecked);
									imageview4.setImageResource(R.drawable.ic_unchecked);
									_BolehJawab();
									if (NomorSoal == 21) {
										iSelesai.putExtra("benar", String.valueOf((long)(SoalBenar)));
										iSelesai.putExtra("salah", String.valueOf((long)(SoalSalah)));
										iSelesai.putExtra("total", String.valueOf((long)(SoalBenar * 5)));
										iSelesai.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										iSelesai.setClass(getApplicationContext(), ScoreActivity.class);
										startActivity(iSelesai);
									}
								}
							});
						}
					};
					_timer.schedule(DelayGantiSoal, (int)(1000));
				}
			}
		});
		
		_DataSoal_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				DataSoal.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						list_soal = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								list_soal.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						DataLoad = true;
						if (DataLoad) {
							progressDoalog.dismiss();
							textview1.setText("Pertanyaan ke : ".concat(String.valueOf((long)(NomorSoal))));
							_PickSoal(SketchwareUtil.getRandom((int)(0), (int)(list_soal.size() - 1)));
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		DataSoal.addChildEventListener(_DataSoal_child_listener);
	}
	private void initializeLogic() {
		_GontaGantiBG(SketchwareUtil.getRandom((int)(0), (int)(6)));
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 0, textview1);
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 0, textview2);
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 0, textview3);
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear2);
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 0, textview5);
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear3);
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear4);
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear5);
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear6);
		if (setting.getString("hitung soal", "").equals("true")) {
			textview1.setVisibility(View.VISIBLE);
		}
		else {
			textview1.setVisibility(View.GONE);
		}
		if (setting.getString("hitung benar", "").equals("true")) {
			textview2.setVisibility(View.VISIBLE);
		}
		else {
			textview2.setVisibility(View.GONE);
		}
		if (setting.getString("hitung salah", "").equals("true")) {
			textview3.setVisibility(View.VISIBLE);
		}
		else {
			textview3.setVisibility(View.GONE);
		}
		progressDoalog = new ProgressDialog(QuizMainActivity.this);
		progressDoalog.setMessage("Sedang mengumpulkan data dari server...");
		progressDoalog.show();
		progressDoalog.setCancelable(false);
		SoalBenar = 0;
		SoalSalah = 0;
		NomorSoal = 1;
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
		progressDoalog.dismiss();
		finish();
	}
	private void _GontaGantiBG (final double _random) {
		if (_random == 1) {
			vscroll1.setBackgroundResource(R.drawable.background_app_3);
		}
		else {
			if (_random == 2) {
				vscroll1.setBackgroundResource(R.drawable.background_app_4);
			}
			else {
				if (_random == 3) {
					vscroll1.setBackgroundResource(R.drawable.background_app_2);
				}
				else {
					if (_random == 4) {
						vscroll1.setBackgroundResource(R.drawable.background_app_5);
					}
					else {
						if (_random == 5) {
							vscroll1.setBackgroundResource(R.drawable.background_app_7);
						}
						else {
							if (_random == 6) {
								vscroll1.setBackgroundResource(R.drawable.background_app_8);
							}
						}
					}
				}
			}
		}
	}
	
	
	private void _PilihJawaban (final String _options) {
		imageview1.setImageResource(R.drawable.ic_unchecked);
		imageview2.setImageResource(R.drawable.ic_unchecked);
		imageview3.setImageResource(R.drawable.ic_unchecked);
		imageview4.setImageResource(R.drawable.ic_unchecked);
		if (_options.equals("A")) {
			PilihanJawaban = "A";
			imageview1.setImageResource(R.drawable.ic_checked);
		}
		else {
			if (_options.equals("B")) {
				PilihanJawaban = "B";
				imageview2.setImageResource(R.drawable.ic_checked);
			}
			else {
				if (_options.equals("C")) {
					PilihanJawaban = "C";
					imageview3.setImageResource(R.drawable.ic_checked);
				}
				else {
					if (_options.equals("D")) {
						PilihanJawaban = "D";
						imageview4.setImageResource(R.drawable.ic_checked);
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
	
	
	private void _ProgresDialog () {
	}
	public ProgressDialog progressDoalog;
	
	private void temp(){
		{
		}
	}
	
	
	private void _PickSoal (final double _random) {
		DataSoal.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				list_soal = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						list_soal.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				if (list_soal.get((int)_random).get("Diterima").toString().equals("true")) {
					textview4.setText(list_soal.get((int)_random).get("Pertanyaan").toString());
					textview6.setText(list_soal.get((int)_random).get("Jawaban A").toString());
					textview7.setText(list_soal.get((int)_random).get("Jawaban B").toString());
					textview8.setText(list_soal.get((int)_random).get("Jawaban C").toString());
					textview9.setText(list_soal.get((int)_random).get("Jawaban D").toString());
					JawabanBenar = list_soal.get((int)_random).get("Jawaban Benar").toString();
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	private void _CekSoalBenarOrSalah () {
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear3);
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear4);
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear5);
		_shape(16, 16, 16, 16, "#FFEC407A", "#FF03A9F4", 2, linear6);
		if (PilihanJawaban.equals(JawabanBenar)) {
			if (PilihanJawaban.equals("A")) {
				_shape(16, 16, 16, 16, "#FF4CAF50", "#FF03A9F4", 2, linear3);
			}
			else {
				if (PilihanJawaban.equals("B")) {
					_shape(16, 16, 16, 16, "#FF4CAF50", "#FF03A9F4", 2, linear4);
				}
				else {
					if (PilihanJawaban.equals("C")) {
						_shape(16, 16, 16, 16, "#FF4CAF50", "#FF03A9F4", 2, linear5);
					}
					else {
						if (PilihanJawaban.equals("D")) {
							_shape(16, 16, 16, 16, "#FF4CAF50", "#FF03A9F4", 2, linear6);
						}
					}
				}
			}
			SoalBenar++;
			textview2.setText("Benar : ".concat(String.valueOf((long)(SoalBenar))));
			PilihanJawaban = "";
		}
		else {
			if (PilihanJawaban.equals("A")) {
				_shape(16, 16, 16, 16, "#FFF44336", "#FF03A9F4", 2, linear3);
			}
			else {
				if (PilihanJawaban.equals("B")) {
					_shape(16, 16, 16, 16, "#FFF44336", "#FF03A9F4", 2, linear4);
				}
				else {
					if (PilihanJawaban.equals("C")) {
						_shape(16, 16, 16, 16, "#FFF44336", "#FF03A9F4", 2, linear5);
					}
					else {
						if (PilihanJawaban.equals("D")) {
							_shape(16, 16, 16, 16, "#FFF44336", "#FF03A9F4", 2, linear6);
						}
					}
				}
			}
			SoalSalah++;
			textview3.setText("Salah : ".concat(String.valueOf((long)(SoalSalah))));
			PilihanJawaban = "";
		}
	}
	
	
	private void _TidakBolehJawab () {
		linear3.setEnabled(false);
		linear4.setEnabled(false);
		linear5.setEnabled(false);
		linear6.setEnabled(false);
		textview6.setEnabled(false);
		textview7.setEnabled(false);
		textview8.setEnabled(false);
		textview9.setEnabled(false);
		imageview1.setEnabled(false);
		imageview2.setEnabled(false);
		imageview3.setEnabled(false);
		imageview4.setEnabled(false);
	}
	
	
	private void _BolehJawab () {
		linear3.setEnabled(true);
		linear4.setEnabled(true);
		linear5.setEnabled(true);
		linear6.setEnabled(true);
		textview6.setEnabled(true);
		textview7.setEnabled(true);
		textview8.setEnabled(true);
		textview9.setEnabled(true);
		imageview1.setEnabled(true);
		imageview2.setEnabled(true);
		imageview3.setEnabled(true);
		imageview4.setEnabled(true);
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
