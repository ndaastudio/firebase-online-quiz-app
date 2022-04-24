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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Timer;
import java.util.TimerTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.view.View;

public class TambahSoalActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private FloatingActionButton _fab;
	private String Jawaban = "";
	private HashMap<String, Object> map_soal = new HashMap<>();
	
	private ArrayList<String> list_pilihan_jawaban = new ArrayList<>();
	
	private LinearLayout linear1;
	private TextView textview1;
	private EditText edittext1;
	private TextView textview2;
	private EditText edittext2;
	private EditText edittext3;
	private EditText edittext4;
	private EditText edittext5;
	private TextView textview3;
	private LinearLayout linear2;
	private Spinner spinner1;
	
	private DatabaseReference DataSoal = _firebase.getReference("Data Soal");
	private ChildEventListener _DataSoal_child_listener;
	private TimerTask DelayPush;
	private AlertDialog.Builder D_Sukses;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.tambah_soal);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview1 = (TextView) findViewById(R.id.textview1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		textview2 = (TextView) findViewById(R.id.textview2);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		edittext4 = (EditText) findViewById(R.id.edittext4);
		edittext5 = (EditText) findViewById(R.id.edittext5);
		textview3 = (TextView) findViewById(R.id.textview3);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		D_Sukses = new AlertDialog.Builder(this);
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 0) {
					Jawaban = "A";
				}
				if (_position == 1) {
					Jawaban = "B";
				}
				if (_position == 2) {
					Jawaban = "C";
				}
				if (_position == 3) {
					Jawaban = "D";
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!edittext1.getText().toString().trim().equals("")) {
					if (!edittext2.getText().toString().trim().equals("")) {
						if (!edittext3.getText().toString().trim().equals("")) {
							if (!edittext4.getText().toString().trim().equals("")) {
								if (!edittext5.getText().toString().trim().equals("")) {
									map_soal = new HashMap<>();
									map_soal.put("Jawaban A", edittext2.getText().toString());
									map_soal.put("Jawaban B", edittext3.getText().toString());
									map_soal.put("Jawaban C", edittext4.getText().toString());
									map_soal.put("Jawaban D", edittext5.getText().toString());
									map_soal.put("Jawaban Benar", Jawaban);
									if (edittext1.getText().toString().contains(" by Nda")) {
										map_soal.put("Pertanyaan", edittext1.getText().toString().replace(" by Nda", ""));
										map_soal.put("Diterima", "true");
									}
									else {
										map_soal.put("Pertanyaan", edittext1.getText().toString());
										map_soal.put("Diterima", "false");
									}
									DataSoal.push().updateChildren(map_soal);
									progressDoalog = new ProgressDialog(TambahSoalActivity.this);
									progressDoalog.setMessage("Loading...");
									progressDoalog.show();
									progressDoalog.setCancelable(false);
									DelayPush = new TimerTask() {
										@Override
										public void run() {
											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													progressDoalog.dismiss();
													D_Sukses.setCancelable(false);
													D_Sukses.setTitle("Berhasil Ditambahkan");
													D_Sukses.setMessage("Soal atau pertanyaan dari anda telah dikirim ke server, admin akan meninjau apakah soal atau pertanyaan yang anda kirimkan layak diterima! Terimakasih.");
													D_Sukses.setPositiveButton("MENGERTI", new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface _dialog, int _which) {
															
														}
													});
													D_Sukses.create().show();
													edittext1.setText("");
													edittext2.setText("");
													edittext3.setText("");
													edittext4.setText("");
													edittext5.setText("");
													((ArrayAdapter)spinner1.getAdapter()).notifyDataSetChanged();
												}
											});
										}
									};
									_timer.schedule(DelayPush, (int)(3000));
								}
								else {
									edittext5.setError("Jawaban D jangan dikosongkan!");
								}
							}
							else {
								edittext4.setError("Jawaban C jangan dikosongkan!");
							}
						}
						else {
							edittext3.setError("Jawaban B jangan dikosongkan!");
						}
					}
					else {
						edittext2.setError("Jawaban A jangan dikosongkan!");
					}
				}
				else {
					edittext1.setError("Pertanyaan jangan dikosongkan!");
				}
			}
		});
		
		_DataSoal_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		edittext2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pilihan_a,0,0,0);
		//Kiri,Atas,Kanan,Bawah
		edittext3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pilihan_b,0,0,0);
		//Kiri,Atas,Kanan,Bawah
		edittext4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pilihan_c,0,0,0);
		//Kiri,Atas,Kanan,Bawah
		edittext5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pilihan_d,0,0,0);
		//Kiri,Atas,Kanan,Bawah
		_GontaGantiBG(SketchwareUtil.getRandom((int)(0), (int)(6)));
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 0, textview1);
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 0, textview2);
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 0, textview3);
		_shape(16, 16, 16, 16, "#FFFFFF", "#FFECEFF1", 0, linear2);
		list_pilihan_jawaban.add("Jawaban A");
		list_pilihan_jawaban.add("Jawaban B");
		list_pilihan_jawaban.add("Jawaban C");
		list_pilihan_jawaban.add("Jawaban D");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, list_pilihan_jawaban));
		((ArrayAdapter)spinner1.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
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
	
	
	private void _ProgresDialog () {
	}
	public ProgressDialog progressDoalog;
	
	private void temp(){
		{
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
