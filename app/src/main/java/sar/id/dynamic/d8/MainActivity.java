package sar.id.dynamic.d8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.content.Intent;
import android.content.ClipData;
import android.widget.CompoundButton;
import android.view.View;
import com.github.angads25.filepicker.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity {
	public final int REQ_CD_FC = 101;
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private String minApi = "";
	private boolean isJava8 = false;
	private boolean isIntermediate = false;
	private String path = "";
	private String out = "";
	private String outter = "";
	
	private LinearLayout linear4;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear5;
	private ScrollView vscroll1;
	private TextView textview3;
	private EditText edittext2;
	private TextView textview2;
	private EditText edittext1;
	private CheckBox checkbox2;
	private CheckBox checkbox1;
	private TextView textview4;
	private TextView textview5;
	private LinearLayout linear3;
	private TextView textview1;
	
	private Intent fc = new Intent(Intent.ACTION_GET_CONTENT);
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
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
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		textview3 = (TextView) findViewById(R.id.textview3);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		textview2 = (TextView) findViewById(R.id.textview2);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
		checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview5 = (TextView) findViewById(R.id.textview5);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		textview1 = (TextView) findViewById(R.id.textview1);
		fc.setType("*/*");
		fc.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					isJava8 = true;
				}
				else {
					isJava8 = false;
				}
			}
		});
		
		checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					isIntermediate = true;
				}
				else {
					isIntermediate = false;
				}
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				com.github.angads25.filepicker.model.DialogProperties properties = new com.github.angads25.filepicker.model.DialogProperties();
				properties.selection_mode = com.github.angads25.filepicker.model.DialogConfigs.SINGLE_MODE;
				properties.selection_type = com.github.angads25.filepicker.model.DialogConfigs.FILE_AND_DIR_SELECT;
				properties.root = new java.io.File("/sdcard");
				properties.error_dir = new java.io.File(com.github.angads25.filepicker.model.DialogConfigs.DEFAULT_DIR);
				properties.offset = new java.io.File(com.github.angads25.filepicker.model.DialogConfigs.STORAGE_DIR);
				
				
				String[] extensions = {"jar"};
				properties.extensions = extensions;
				com.github.angads25.filepicker.view.FilePickerDialog dialog = new com.github.angads25.filepicker.view.FilePickerDialog(MainActivity.this,properties);
				dialog.setTitle("Select a File");
				dialog.setDialogSelectionListener(new com.github.angads25.filepicker.controller.DialogSelectionListener() {
						@Override
						public void onSelectedFilePaths(String[] files) {
								
						path = Arrays.asList(files).get(0).toString();
						edittext2.setText(path);
						out = path.substring(path.lastIndexOf("/")+1);
						outter = path.replace(out, "");
						new d8Task().execute("run");
					}});
				dialog.show();
			}
		});
	}
	
	private void initializeLogic() {
		
		
		
		int[] colorsCRNKA = { Color.parseColor("#ffffff"), Color.parseColor("#ffffff") }; android.graphics.drawable.GradientDrawable CRNKA = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNKA);
		CRNKA.setCornerRadii(new float[]{(int)23,(int)23,(int)23,(int)23,(int)23,(int)23,(int)23,(int)23});
		CRNKA.setStroke((int) 0, Color.parseColor("#000000"));
		linear1.setElevation((float) 5);
		linear1.setBackground(CRNKA);
		int[] colorsCRNZH = { Color.parseColor("#ffffff"), Color.parseColor("#ffffff") }; android.graphics.drawable.GradientDrawable CRNZH = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNZH);
		CRNZH.setCornerRadii(new float[]{(int)23,(int)23,(int)23,(int)23,(int)23,(int)23,(int)23,(int)23});
		CRNZH.setStroke((int) 0, Color.parseColor("#000000"));
		linear4.setElevation((float) 5);
		linear4.setBackground(CRNZH);
		int[] colorsCRNMT = { Color.parseColor("#ffffff"), Color.parseColor("#ffffff") }; android.graphics.drawable.GradientDrawable CRNMT = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNMT);
		CRNMT.setCornerRadii(new float[]{(int)23,(int)23,(int)23,(int)23,(int)23,(int)23,(int)23,(int)23});
		CRNMT.setStroke((int) 0, Color.parseColor("#000000"));
		linear2.setElevation((float) 5);
		linear2.setBackground(CRNMT);
		int[] colorsCRNEX = { Color.parseColor("#ffffff"), Color.parseColor("#ffffff") }; android.graphics.drawable.GradientDrawable CRNEX = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNEX);
		CRNEX.setCornerRadii(new float[]{(int)23,(int)23,(int)23,(int)23,(int)23,(int)23,(int)23,(int)23});
		CRNEX.setStroke((int) 0, Color.parseColor("#000000"));
		linear5.setElevation((float) 5);
		linear5.setBackground(CRNEX);
		checkbox2.setChecked(true);
		checkbox1.setChecked(true);
		
		
		//uncaught exceptions
		
		String filePath = FileUtil.getExternalStorageDir().concat("/Jar2Dex/uncaught.txt");
		String[] cm = new String[] {"logcat", "-f", filePath, "-v","time","<MyTagNamr>:D", "*:S"};
		try{
			Runtime.getRuntime().exec(cm);
		} catch (IOException e){
			e.printStackTrace();
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
	
	private class d8Task extends AsyncTask<String, String, String>
		
		
	    {
		        ProgressDialog pd;
		        @Override
		        protected void onPreExecute()
		        {
			            pd = new ProgressDialog(MainActivity.this);
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
			
			
			 String minApi = edittext1.getText().toString();
					   
			            File common = new File(Environment.getExternalStorageDirectory(),"/Jar2Dex/");
			       File jar = new File(common, "/d8.jar");
			File libs = new File(common, "/android.jar");
			
			File classpaths = new File(common, "/rt.jar");
			Argument cmd = new Argument();
			
			    cmd.add("dalvikvm");
			      cmd.add("-Xcompiler-option");
			        cmd.add("--compiler-filter=" + "speed");
			        cmd.add("-Xmx512m");
			
			cmd.add("-cp", jar.toString());
			
			        cmd.add("com.android.tools.r8.D8");
			        
			        
			        cmd.add("--release");
			        cmd.add("--lib", libs.toString());
			        cmd.add("--min-api", minApi);
			           
			           cmd.add("--output", outter);
			           
			           cmd.add("--classpath", classpaths.toString());
			           
			           
			     if (isJava8 = true) {
				            cmd.add("--classpath", classpaths.toString());
				        } else {
				            cmd.add("--no-desugaring");
				        }
			            
			        
			        
			     if (isIntermediate = true) {
				            cmd.add("--intermediate");
				        } else {
				            
				            
				            
				            
				        }
			            
			        
			        
			        cmd.add(path);
			        
			        try{
				
				RuntimeExecution sh = new RuntimeExecution();
						sh.exec(cmd.toArray());
				   
			}catch(Exception e){
				
				e.printStackTrace();
				textview1.setText(e.toString());
				
			}
			return null;
			            
			            
			
			        
			        }
		        
		
			
				
				
		        @Override
		        protected void onPostExecute(String result)
		        {
			            super.onPostExecute(result);
						Toast.makeText(MainActivity.this, "Completed!",
									   Toast.LENGTH_LONG).show();
			            pd.dismiss();
						
			        }
		
		   
	}
	

//This Argument class is from the source of JavaNIDE app


	public class Argument {
		    private ArrayList<String> args = new ArrayList<>();
		
		    public Argument(String... args) {
			        add(args);
			    }
		
		    public Argument add(String... args) {
			        this.args.addAll(Arrays.asList(args));
			        return this;
			    }
		
		    @Override
		    public String toString() {
			        return "Argument{" +
			                "args=" + args +
			                '}';
			    }
		
		    public String[] toArray() {
			        ArrayList<String> clean = new ArrayList<>();
			        for (String arg : args) {
				            if (arg != null && !arg.isEmpty()) {
					                clean.add(arg);
					            }
				        }
			        String[] array = new String[clean.size()];
			        clean.toArray(array);
			        return array;
			    }
	}
	
}
