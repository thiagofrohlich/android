package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
				if(tipo.equalsIgnoreCase(Imovel.CASA_NA_RUA)){
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
		boolean erro = false;
		String mensagem = "";
		Double valor = null;
		String tipo = (String) ((Spinner) findViewById(R.id.tipo_spinner)).getSelectedItem();
		Double vCondominio = null;
		if(tipo.equals("Casa na rua")){
			vCondominio = 0.00;
		}else{
			try{
				vCondominio = Double.parseDouble(((EditText) findViewById(R.id.valorC)).getText().toString());
			}catch(Exception e){
				erro = true;
				mensagem = "Insira um valor de condomínio válido\n";
			}
		}
		String endereco = ((EditText) findViewById(R.id.endereco)).getText().toString();
		if(endereco.equals("")){
			erro = true;
			mensagem = mensagem + "Insira um endereço válido\n";
		}
		Integer quartos = Integer.parseInt((String) ((Spinner) findViewById(R.id.qtos_spinner)).getSelectedItem());
		try{
			valor = Double.parseDouble(((EditText) findViewById(R.id.valor_et)).getText().toString());
		}catch (Exception e){
			erro = true;
			mensagem = mensagem+"Insira um valor de venda válido";
		}
		if(!erro){
			imovel = new Imovel(tipo, valor, vCondominio, endereco, quartos);
			AsyncTask<Object, Object, Object> saveTask = new AsyncTask<Object, Object, Object>(){
				
				@Override
				protected Object doInBackground(Object... params) {
					save();
					return null;
				}
				@Override
				protected void onPostExecute(Object result){
					finish();
				}
				
			};
			saveTask.execute((Object[]) null);
			mensagem = "Imóvel salvo";
		}
		Toast toast = Toast.makeText(getApplicationContext(),mensagem, Toast.LENGTH_SHORT);
		toast.show();
		
		
		
	}
	
	public void save(){
		DatabaseConnector dbConnector = new DatabaseConnector(Inserir.this);
		dbConnector.insert(imovel);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inserir, menu);
		return true;
	}

}
