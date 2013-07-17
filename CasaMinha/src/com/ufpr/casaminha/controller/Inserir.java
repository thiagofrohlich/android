package com.ufpr.casaminha.controller;

import com.ufpr.casaminha.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Inserir extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inserir);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inserir, menu);
		return true;
	}

}
