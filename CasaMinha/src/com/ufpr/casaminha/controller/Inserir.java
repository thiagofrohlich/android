package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ufpr.casaminha.R;
import com.ufpr.casaminha.model.Imovel;

public class Inserir extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inserir);
		setContentView(R.layout.activity_inserir);
		Spinner spinnerTipo = (Spinner) findViewById(R.id.tipo_spinner);
		ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(this,  R.array.tipo_arrays, android.R.layout.simple_spinner_item);
		spinnerTipo.setAdapter(tipoAdapter);
		spinnerTipo.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				String tipo = parent.getItemAtPosition(pos).toString();
				if(tipo.equals("Casa na rua")){
					EditText vCondominio = (EditText) findViewById(R.id.valorC);
					vCondominio.setEnabled(false);
				}else{
					EditText vCondominio = (EditText) findViewById(R.id.valorC);
					vCondominio.setEnabled(true);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		
		
		
	}

	public void onClick(View view){
		String tipo = (String) ((Spinner) findViewById(R.id.tipo_spinner)).getSelectedItem();
		Double vCondominio;
		if(tipo.equals("Casa na rua")){
			vCondominio = 0.00;
		}else{
			vCondominio = Double.parseDouble(((EditText) findViewById(R.id.valorC)).getText().toString());
		}
		String endereco = ((EditText) findViewById(R.id.endereco)).getText().toString();
		Integer quartos = Integer.parseInt((String) ((Spinner) findViewById(R.id.qtos_spinner)).getSelectedItem());
		Double valor = Double.parseDouble(((EditText) findViewById(R.id.valor_et)).getText().toString());
		Imovel imovel = new Imovel(tipo, valor, vCondominio, endereco, quartos);
		DatabaseConnector dbConnector = new DatabaseConnector(Inserir.this);
		dbConnector.insert(imovel);
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inserir, menu);
		return true;
	}

}
