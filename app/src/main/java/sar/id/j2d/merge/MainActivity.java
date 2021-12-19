package sar.id.j2d.merge;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.Manifest;
import android.content.pm.PackageManager;

public class MainActivity extends Activity {
	
	private String dex1 = "";
	private String dex2 = "";
	private String dex3 = "";
	private String mergedDex = "";
	private String output = "";
	private String jarPath = "";
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private TextView textview1;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private EditText edittext4;
	private Button button1;
	private TextView textview2;
	private Button button2;
	private Button button3;
	
	private Intent d8 = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
			||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
			} else {
				initializeLogic();
			}
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		textview1 = findViewById(R.id.textview1);
		edittext1 = findViewById(R.id.edittext1);
		edittext2 = findViewById(R.id.edittext2);
		edittext3 = findViewById(R.id.edittext3);
		edittext4 = findViewById(R.id.edittext4);
		button1 = findViewById(R.id.button1);
		textview2 = findViewById(R.id.textview2);
		button2 = findViewById(R.id.button2);
		button3 = findViewById(R.id.button3);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dex1 = edittext1.getText().toString();
				dex2 = edittext2.getText().toString();
				dex3 = edittext3.getText().toString();
				//your dex1 location is output location
				//below code remove the last segment of directory 
				output = dex1.substring(dex1.lastIndexOf("/")+1);
				mergedDex = dex1.replace(output, "merged.dex");
				edittext4.setText(mergedDex);
				//Async task initiater
				new mergeTask().execute("run");
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d8.setClass(getApplicationContext(), D8DexActivity.class);
				d8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(d8);
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d8.setClass(getApplicationContext(), MulipleJarClassActivity.class);
				d8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(d8);
			}
		});
	}
	
	private void initializeLogic() {
		
		
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex"))) {
			
		}
		else {
			FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/Jar2Dex"));
			ApplicationUtil.showMessage(getApplicationContext(), "Directory created...");
		}
		jarPath = FileUtil.getExternalStorageDir().concat("/Jar2Dex/merge.jar");
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex/merge.jar"))) {
			
		}
		else {
			try{
						copyAssetFile("fonts/merge.jar", jarPath);
				ApplicationUtil.showMessage(getApplicationContext(), "Merger.jar copied successfully!");
			}catch (java.io.IOException e){
						textview1.setText(e.toString());
				}
			
		}
	}
	
	public void _async() {
	}
	
	private class mergeTask extends AsyncTask<String, String, String>
		
		
	    {
		        ProgressDialog pd;
		        @Override
		        protected void onPreExecute()
		        {
			            pd = new ProgressDialog(MainActivity.this);
						pd.setTitle("Please wait");
			            pd.setMessage("merging...");
			            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			            pd.setCancelable(false);
						pd.setIndeterminate(true);
					    
						
			            pd.show();
						
			            
					}
		
		     
				
				
		        @Override
		        protected String doInBackground(String[] p1)
		        {
			            // add code which need to be done in background
			java.io.File jars = new java.io.File(Environment.getExternalStorageDirectory(),"/Jar2Dex/merge.jar");
			
			
			//merge.jar is nothing but dalvik dx library 
			
			List<String> cmd= new ArrayList<String>();
			
			
			    cmd.add("dalvikvm");
			      cmd.add("-Xcompiler-option");
			        cmd.add("--compiler-filter=" + "speed");
			        cmd.add("-Xmx512m");
			cmd.add("-cp");
			cmd.add(jars.toString());
			cmd.add("com.android.dx.merge.DexMerger");
			cmd.add(mergedDex);
			
			if (dex1.equals("")) {
					
			}
			else {
					cmd.add(dex1);
			}
			
			if (dex2.equals("")) {
					
			}
			else {
					cmd.add(dex2);
			}
			if (dex3.equals("")) {
					
			}
			else {
					cmd.add(dex3);
			}
			        
			        
			try{
				
				java.lang.ProcessBuilder pb = new java.lang.ProcessBuilder(cmd); 
						java.lang.Process process = pb.start();
						
						
						//this below code is for writing input process
						
				java.io.BufferedReader stdInput= new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
						String s = null;
				while ((s = stdInput.readLine()) != null) {
							    FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex/process.txt"), s);
							    
							    } 
			}catch(Exception e){
				
				e.printStackTrace();
				textview1.setText(e.toString());
				
			}
			
			return null;
			            
			            
			
			        
			        }
		        
		
			
				
				
		        @Override
		        protected void onPostExecute(String string2)
		        {
			            super.onPostExecute(string2);
			            
						Toast.makeText(MainActivity.this, "Completed!",
									   Toast.LENGTH_LONG).show();
			            pd.dismiss();
						
			        }
	}
	
	
	public void _copy_assets() {
	}
	
	public void copyAssetFile(String assetFilePath, String destinationFilePath) throws java.io.IOException {
		java.io.InputStream in = getApplicationContext().getAssets().open(assetFilePath);
		java.io.OutputStream out = new java.io.FileOutputStream(destinationFilePath);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
		in.close();
		out.close();
	}
	
	{
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
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}