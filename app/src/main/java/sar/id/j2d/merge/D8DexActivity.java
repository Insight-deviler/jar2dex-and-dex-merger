package sar.id.j2d.merge;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class D8DexActivity extends Activity {
	
	public final int REQ_CD_D8PICK = 101;

	private String d8lib = "";
	private String path = "";
	private String outter = "";
	private String out = "";
	private String libs = "";
	private String rtss = "";
	
	private final ArrayList<String> d8class = new ArrayList<>();

	private TextView log1;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private Button button1;

	private final Intent d8pick = new Intent(Intent.ACTION_GET_CONTENT);
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.d8_dex);
		initialize(_savedInstanceState);
		Permission.requestAllFilesAccessPermission(this);
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
		ScrollView vscroll1 = findViewById(R.id.vscroll1);
		LinearLayout linear1 = findViewById(R.id.linear1);
		LinearLayout linear2 = findViewById(R.id.linear2);
		LinearLayout linear3 = findViewById(R.id.linear3);
		LinearLayout linear4 = findViewById(R.id.linear4);
		LinearLayout linear5 = findViewById(R.id.linear5);
		log1 = findViewById(R.id.log1);
		TextView textview2 = findViewById(R.id.textview2);
		edittext1 = findViewById(R.id.edittext1);
		TextView textview3 = findViewById(R.id.textview3);
		edittext2 = findViewById(R.id.edittext2);
		TextView textview4 = findViewById(R.id.textview4);
		edittext3 = findViewById(R.id.edittext3);
		button1 = findViewById(R.id.button1);
		d8pick.setType("*/*");
		d8pick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		button1.setOnClickListener(_view -> startActivityForResult(d8pick, REQ_CD_D8PICK));
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
	public void copyAssetFile(String assetFilePath, String destinationFilePath) throws java.io.IOException {
		java.io.InputStream in = getApplicationContext().getAssets().open(assetFilePath);
		java.io.OutputStream out = new java.io.FileOutputStream(destinationFilePath);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
		in.close();
		out.close();
	}
	private class d8Task extends AsyncTask<String, String, String>{
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
				cmd.add("--classpath");
				cmd.add(classpaths.toString());
				cmd.add("--intermediate");
				cmd.add(path);
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
}
