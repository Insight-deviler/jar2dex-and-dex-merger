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
import java.util.ArrayList;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.content.ClipData;
import android.view.View;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.Manifest;
import android.content.pm.PackageManager;

public class D8DexActivity extends Activity {
	
	public final int REQ_CD_D8PICK = 101;
	
	private String d8lib = "";
	private String path = "";
	private String outter = "";
	private String out = "";
	private String libs = "";
	private String rtss = "";
	
	private ArrayList<String> d8class = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private TextView log1;
	private TextView textview2;
	private EditText edittext1;
	private TextView textview3;
	private EditText edittext2;
	private TextView textview4;
	private EditText edittext3;
	private Button button1;
	private TextView textview5;
	
	private Intent d8pick = new Intent(Intent.ACTION_GET_CONTENT);
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.d8_dex);
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
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
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		log1 = findViewById(R.id.log1);
		textview2 = findViewById(R.id.textview2);
		edittext1 = findViewById(R.id.edittext1);
		textview3 = findViewById(R.id.textview3);
		edittext2 = findViewById(R.id.edittext2);
		textview4 = findViewById(R.id.textview4);
		edittext3 = findViewById(R.id.edittext3);
		button1 = findViewById(R.id.button1);
		textview5 = findViewById(R.id.textview5);
		d8pick.setType("*/*");
		d8pick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(d8pick, REQ_CD_D8PICK);
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("D8 dexer");
		d8lib = FileUtil.getExternalStorageDir().concat("/Jar2Dex/d8s.jar");
		if (FileUtil.isExistFile(d8lib)) {
			
		}
		else {
			try{
								copyAssetFile("fonts/d8s.jar", d8lib);
				ApplicationUtil.showMessage(getApplicationContext(), "d8.jar copied successfully!");
			}catch (java.io.IOException e){
								log1.setText(e.toString());
					}
				
			
		}
		//check for android.jar
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex/android.jar"))) {
			libs = FileUtil.getExternalStorageDir().concat("/Jar2Dex/android.jar");
		}
		else {
			ApplicationUtil.showMessage(getApplicationContext(), "android.jar file is missing. Please download it from my GitHub page  or get it from Android studio");
			button1.setEnabled(false);
		}
		//check for rt.jar
		rtss = FileUtil.getExternalStorageDir().concat("/Jar2Dex/rt.jar");
		if (FileUtil.isExistFile(rtss)) {
			
		}
		else {
			try{
								copyAssetFile("fonts/rtjar.jar", rtss);
				ApplicationUtil.showMessage(getApplicationContext(), "rt.jar copied successfully!");
				}catch (java.io.IOException e){
								log1.setText(e.toString());
					}
				
			
		}
		java.io.OutputStream _os = new java.io.OutputStream() {
			StringBuilder mCache;
			@Override
			public void write(int b) {
				if(mCache == null) mCache = new StringBuilder();
				if(((char) b) == '\n'){
					final String _print = mCache.toString();
											
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							log1.append("\n");
							log1.append(_print);
						}
					});
											
					mCache = new StringBuilder();
				}else{
					mCache.append((char)b);
				}
			}
		};
		
		System.setOut(new java.io.PrintStream(_os));
		System.setErr(new java.io.PrintStream(_os));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_D8PICK:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				path = _filePath.get((int)(0));
				//get file name from the path
				out = path.substring(path.lastIndexOf("/")+1);
				//to remove the file name
				outter = path.replace(out, "");
				edittext1.setText(path);
				edittext2.setText(outter);
				//execution of task
				if (JarCheck.checkJar(path, 44, 51)) {
					new d8Task().execute("run");
				}
				else {
					new d8Task().execute("run");
					ApplicationUtil.showMessage(getApplicationContext(), "Java 8 file");
				}
			}
			else {
				
			}
			break;
			default:
			break;
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
	
	
	public void _extra() {
	}
		private class d8Task extends AsyncTask<String, String, String>
		
		
	    {
		        ProgressDialog pd;
		        @Override
		        protected void onPreExecute()
		        {
			            pd = new ProgressDialog(D8DexActivity.this);
						pd.setTitle("Please wait");
			            pd.setMessage("D8 running...");
			            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			            pd.setCancelable(false);
						pd.setIndeterminate(true);
					    
						
			            pd.show();
						
			            
					}
		
		     
				
				
		        @Override
		        protected String doInBackground(String[] p1)
		        {
			            // add code which need to be done in background
			
			
			 String minApi = edittext3.getText().toString();
					   
			            java.io.File common = new java.io.File(Environment.getExternalStorageDirectory(),"/Jar2Dex/");
			    java.io.File jar = new java.io.File(common, "/d8s.jar");
			
			
			java.io.File classpaths = new java.io.File(common, "/rt.jar");
			List<String> cmd= new ArrayList<String>();
			
			        cmd.add("dalvikvm");
			      cmd.add("-Xcompiler-option");
			        cmd.add("--compiler-filter=" + "speed");
			        cmd.add("-Xmx512m");
			
			cmd.add("-cp");
			cmd.add(jar.toString());
			
			        cmd.add("com.android.tools.r8.D8");
			        cmd.add("--release");
			        cmd.add("--lib");
			        cmd.add(libs);
			        cmd.add("--min-api");
			        cmd.add(minApi);
			           
			           cmd.add("--output");
			           cmd.add(outter);
			           
			           // the below code is for enabling java8 support remove if you dont need it
			//            cmd.add("--classpath");
			     //       cmd.add(classpaths.toString());
			            
			           /* add this if you dont want to desugar java8 apis 
            cmd.add("--no-desugaring");
          */
			        
			       
					cmd.add("--classpath");
			            cmd.add(classpaths.toString());
			
			        
			        
			        
			            cmd.add("--intermediate");
			            cmd.add(path);
			            
			            /* add this if you want dex for each class in a jar file
            cmd.add("--file-per-class")
            
            */
			            
			try {
				    
				    // this is for doing the execution
				   java.lang.ProcessBuilder pbs = new java.lang.ProcessBuilder(cmd); 
						java.lang.Process proces = pbs.start();
						
						
						//this below code is for writing input process
						
				java.io.BufferedReader StdInput= new java.io.BufferedReader(new java.io.InputStreamReader(proces.getInputStream()));
						String st = null;
				while ((st = StdInput.readLine()) != null) {
							    FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex/d8process.txt"), st);
							    
							    } 
				
			}catch(Exception e){
				
				e.printStackTrace();
				log1.setText(e.toString());
				
				
			}
			return null;
		}
		      @Override
		        protected void onPostExecute(String result)
		        {
			            super.onPostExecute(result);
						Toast.makeText(D8DexActivity.this, "Completed!",
			Toast.LENGTH_LONG).show();
			
			log1.setText("Error log");
			            pd.dismiss();
						
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