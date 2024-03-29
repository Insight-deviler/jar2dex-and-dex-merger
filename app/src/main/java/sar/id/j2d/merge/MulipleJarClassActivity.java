package sar.id.j2d.merge;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class MulipleJarClassActivity extends Activity {

	private String input = "";
	private String out = "";
	private String minApi = "";
	private String d8lib = "";
	private String libs = "";
	private String outter = "";
	private String classp = "";
	private String rtssd = "";

	private TextView log1;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private Button button1;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.muliple_jar_class);
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
		LinearLayout linear1 = findViewById(R.id.linear1);
		LinearLayout linear2 = findViewById(R.id.linear2);
		LinearLayout linear3 = findViewById(R.id.linear3);
		LinearLayout linear4 = findViewById(R.id.linear4);
		LinearLayout linear5 = findViewById(R.id.linear5);
		log1 = findViewById(R.id.log1);
		edittext1 = findViewById(R.id.edittext1);
		edittext2 = findViewById(R.id.edittext2);
		edittext3 = findViewById(R.id.edittext3);
		button1 = findViewById(R.id.button1);
		TextView textview2 = findViewById(R.id.textview2);

		button1.setOnClickListener(_view -> {
			input = edittext1.getText().toString();
			out = input.substring(input.lastIndexOf("/") + 1);
			outter = input.replace(out, "");
			minApi = edittext3.getText().toString();
			edittext2.setText(outter);
			new d8Task().execute("run");
			startService();
		});
	}
	private void initializeLogic() {
		setTitle("Folder to dex");
		//check for android.jar
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex/android.jar"))) {
			libs = FileUtil.getExternalStorageDir().concat("/Jar2Dex/android.jar");
		}
		else {
			ApplicationUtil.showMessage(getApplicationContext(), "android.jar is missing please download it from my GitHub page or from Android studio");
			button1.setEnabled(false);
		}
		//check for rt.jar
		rtssd = FileUtil.getExternalStorageDir().concat("/Jar2Dex/rt.jar");
		if (FileUtil.isExistFile(rtssd)) {
		}
		else {
			try{
				copyAssetFile("fonts/rtjar.jar", rtssd);
				ApplicationUtil.showMessage(getApplicationContext(), "rt.jar copied successfully!");
			}catch (java.io.IOException e){
				log1.setText(e.toString());
			}
		}
	}
	private class d8Task extends AsyncTask<String, String, String>{
		        ProgressDialog pd;
		        @Override
		        protected void onPreExecute()
		        {
			            pd = new ProgressDialog(MulipleJarClassActivity.this);
						pd.setTitle("Please wait");
			            pd.setMessage("D8 running...");
			            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			            pd.setCancelable(false);
						pd.setIndeterminate(true);
			            pd.show();
					}
					@Override
		        protected String doInBackground(String[] p1) {
					classp = FileUtil.getExternalStorageDir().concat("/Jar2Dex/rt.jar");
					List<String> cmd= new ArrayList<String>();
						cmd.add("--release");
						cmd.add("--min-api");
						cmd.add(minApi);
						cmd.add("--lib");
						cmd.add(libs);
						cmd.add("--output");
						cmd.add(outter);
						cmd.add("--classpath");
						cmd.add(classp);
						cmd.addAll(getSourceFile(new java.io.File(input), new ArrayList<String>()));
					try {
						// this is for doing the execution
						com.android.tools.r8.D8.main(cmd.toArray(new String[0]));
					}catch(Exception e){
						e.printStackTrace();
					}
					return null;
				}
				@Override
		        protected void onPostExecute(String result)
		        {
			            super.onPostExecute(result);
						Toast.makeText(MulipleJarClassActivity.this, "Completed!",
			Toast.LENGTH_LONG).show();
			            pd.dismiss();
						stopService();
						
			        }
	}
	//for listing all the files in a directory
	public ArrayList<String> getSourceFile(java.io.File file, ArrayList<String> arrayList) {
		        if (file != null) {
			            java.io.File[] files = file.listFiles();
			            if (files != null) {
				                for (java.io.File value : files) {
					                    file = value;
					                    if (file.isDirectory()) {
						                        getSourceFile(file, arrayList);
						                    } else if ((file.getName().endsWith(".jar")) || (file.getName().endsWith(".class"))) {
						                        arrayList.add(file.getAbsolutePath());
						                    }
					                }
				            }
			        }
		        return arrayList;
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
	public void startService() {
		Intent serviceIntent = new Intent(this, ForegroundService.class);
		serviceIntent.putExtra("inputExtra", "Files from the folder are being converted");
		ContextCompat.startForegroundService(this, serviceIntent);
	}
	public void stopService() {
		Intent serviceIntent = new Intent(this, ForegroundService.class);
		stopService(serviceIntent);
	}
}
