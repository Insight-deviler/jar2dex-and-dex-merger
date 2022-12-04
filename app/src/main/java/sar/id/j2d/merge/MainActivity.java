package sar.id.j2d.merge;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	private String dex1 = "";
	private String dex2 = "";
	private String dex3 = "";
	private String mergedDex = "";
	private String output = "";

	private TextView textview1;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private EditText edittext4;

	private Intent d8 = new Intent();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		Permission.requestAllFilesAccessPermission(this);
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
					|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		LinearLayout linear7 = findViewById(R.id.linear7);
		LinearLayout linear8 = findViewById(R.id.linear8);
		textview1 = findViewById(R.id.textview1);
		edittext1 = findViewById(R.id.edittext1);
		edittext2 = findViewById(R.id.edittext2);
		edittext3 = findViewById(R.id.edittext3);
		edittext4 = findViewById(R.id.edittext4);
		Button button1 = findViewById(R.id.button1);
		TextView textview2 = findViewById(R.id.textview2);
		Button button2 = findViewById(R.id.button2);
		Button button3 = findViewById(R.id.button3);
		Permission.requestAllFilesAccessPermission(this);
		button1.setOnClickListener(_view -> {
			dex1 = edittext1.getText().toString();
			dex2 = edittext2.getText().toString();
			dex3 = edittext3.getText().toString();
			output = dex1.substring(dex1.lastIndexOf("/") + 1);
			mergedDex = dex1.replace(output, "merged.dex");
			edittext4.setText(mergedDex);
			new mergeTask().execute("run");
		});

		button2.setOnClickListener(_view -> {
			d8.setClass(getApplicationContext(), D8DexActivity.class);
			d8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(d8);
		});

		button3.setOnClickListener(_view -> {
			d8.setClass(getApplicationContext(), MulipleJarClassActivity.class);
			d8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(d8);
		});
	}

	private void initializeLogic() {

		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex"))) {

		} else {
			FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/Jar2Dex"));
			ApplicationUtil.showMessage(getApplicationContext(), "Directory created...");
		}

	}
	private class mergeTask extends AsyncTask<String, String, String> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(MainActivity.this);
			pd.setTitle("Please wait");
			pd.setMessage("merging...");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}


		@Override
		protected String doInBackground(String[] p1) {
			// add code which need to be done in background
			java.io.File jars = new java.io.File(Environment.getExternalStorageDirectory(), "/Jar2Dex/merge.jar");
			//merge.jar is nothing but dalvik dx library
			List<String> cmd = new ArrayList<String>();
			cmd.add(mergedDex);
			if (dex1.equals("")) {

			} else {
				cmd.add(dex1);
			}

			if (dex2.equals("")) {

			} else {
				cmd.add(dex2);
			}
			if (dex3.equals("")) {

			} else {
				cmd.add(dex3);
			}
			try {
				com.android.dx.merge.DexMerger.main(cmd.toArray(new String[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String string2) {
			super.onPostExecute(string2);

			Toast.makeText(MainActivity.this, "Completed!",
					Toast.LENGTH_LONG).show();
			pd.dismiss();

		}
	}
}