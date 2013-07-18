package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ufpr.casaminha.R;
import com.ufpr.casaminha.model.Imovel;

public class Inserir extends Activity {

	Imovel imovel;
	
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
		imovel = new Imovel(tipo, valor, vCondominio, endereco, quartos);
		AsyncTask<Object, Object, Object> saveTask = new AsyncTask<Object, Object, Object>(){

			@Override
			protected Object doInBackground(Object... params) {
				save();
				return null;
			}
			
		};
		saveTask.execute((Object[]) null);
		
		
		
		
	}
	
	public void save(){
		DatabaseConnector dbConnector = new DatabaseConnector(Inserir.this);
		dbConnector.insert(imovel);
		Cursor result = dbConnector.findByEndereco(imovel);
		CharSequence text = "Salvo "+result.getString(result.getColumnIndexOrThrow("tipo"))+
				" no endere�o "+ result.getString(result.getColumnIndexOrThrow("endereco"))+
				"valor "+result.getString(result.getColumnIndexOrThrow("valor"));
		Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inserir, menu);
		return true;
	}

}
