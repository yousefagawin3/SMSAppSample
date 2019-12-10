package com.example.smsapp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


@SuppressLint("NewApi") public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btn_send_message =(Button) findViewById(R.id.send_message);
        Button btn_send_data =(Button) findViewById(R.id.send_data);
        
        btn_send_message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SmsManager sms_manager = SmsManager.getDefault();
				sms_manager.sendTextMessage("+639265032398", null, "sana gumana!!", null, null);
			}
		});
        
        btn_send_data.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONArray students = new JSONArray();
				JSONObject student = new JSONObject();
				JSONObject student1 = new JSONObject();
				
				try {
					student.put("age", 20);
					student.put("name", "Roi");
					
					student1.put("age", 30);
					student1.put("name", "Arkhan");
					
					students.put(student);
					students.put(student1);
					
					Log.i("JSON",students.toString());
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				register("backup", students.toString());
			}
		});
    }
    

    private void register(String action, String json) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            private static final String REGISTER_URL = "http://finalthesis.esy.es/index.php";
            
			ProgressDialog loading;
            Register ruc = new Register();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s ,Toast.LENGTH_LONG).show();
            }

            protected String doInBackground(String ... params) {
            
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("action", params[0]);
                data.put("data", params[1]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(action, json);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
