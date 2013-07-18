package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ufpr.casaminha.R;

public class Inserir extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inserir);
		setContentView(R.layout.activity_inserir);
		Spinner spinnerTipo = (Spinner) findViewById(R.id.tipo_spinner);
		ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(this,  R.array.tipo_arrays, android.R.layout.simple_spinner_item);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inserir, menu);
		return true;
	}

}
