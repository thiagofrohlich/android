package com.example.loginapp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread() {
			public void run() {
				String url = "http://192.168.25.6:8080/CasaMinhaWS/Teste";
				WebService webService = new WebService(url);
				
				Map params = new HashMap();
				params.put("login", "prof");
				params.put("senha", "123");
				
				String response = webService.webGet("", params);
				
				try {
					JSONObject o = new JSONObject(response);
					String out = o.get("message").toString();
					
					Bundle b = new Bundle();
					b.putString("login", out);
					
					Message msg = new Message();
					msg.setData(b);
					handler.sendMessage(msg);
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			TextView t = (TextView) findViewById(R.id.text);
			String out = (String) msg.getData().getString("login");
			t.setText("Login: "+ out);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
