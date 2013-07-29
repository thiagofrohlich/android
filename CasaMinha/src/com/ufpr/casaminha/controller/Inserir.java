package com.ufpr.casaminha.controller;

import org.xml.sax.DTDHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ufpr.casaminha.R;
import com.ufpr.casaminha.model.Imovel;

public class Inserir extends Activity {

	Imovel imovel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent it = getIntent();
		setContentView(R.layout.activity_inserir);
		if(it != null) {
			Bundle pac = it.getExtras();
			if(pac != null) {
				new GetHouseTask().execute(pac);
			}
		}else{
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
	
		
		
	}

	public void onClick(View view){
		boolean erro = false;
		String mensagem = "";
		Double valor = null;
		Integer quartos = null;
		String tipo = (String) ((Spinner) findViewById(R.id.tipo_spinner)).getSelectedItem();
		Double vCondominio = null;
		if(tipo.equals(Imovel.CASA_NA_RUA)){
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
		
			quartos = Integer.parseInt(((String) ((Spinner) findViewById(R.id.qtos_spinner)).getSelectedItem()).substring(0, 1));
		
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

	private class GetHouseTask extends AsyncTask<Bundle, Object, Imovel> {

		DatabaseConnector conector = new DatabaseConnector(Inserir.this);
		
		@Override
		protected Imovel doInBackground(Bundle... params) {
			
			if(params != null) {
				Bundle pac = params[0];
				return conector.getOne((long) pac.getInt("idCasa"));
			}

			finish();
			return null;
		}
		
		@Override
		protected void onPostExecute(Imovel result) {

			if(result == null) {
				finish();
			}
			
			imovel = result;
			Spinner spinnerTipo = (Spinner) findViewById(R.id.tipo_spinner);
			ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(Inserir.this,  R.array.tipo_arrays, android.R.layout.simple_spinner_item);
			Integer posicao =  tipoAdapter.getPosition(imovel.getTipo());
			spinnerTipo.setAdapter(tipoAdapter);
			spinnerTipo.setSelection(posicao);
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
						vCondominio.setText(""+imovel.getValorCondominio());
						vCondominio.setEnabled(true);
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
			});
			
			
			try {
				Spinner quartos = (Spinner) findViewById(R.id.qtos_spinner);
				ArrayAdapter<CharSequence> quartosAdapter = ArrayAdapter.createFromResource(Inserir.this,  R.array.quartos_arrays, android.R.layout.simple_spinner_item);
				quartos.setAdapter(quartosAdapter);
				Integer qtdade = quartosAdapter.getPosition(imovel.getQtdQuartos().equals(4)? "4 ou mais" : ""+imovel.getQtdQuartos());
				quartos.setSelection(qtdade);
				
				TextView endereco = (TextView) findViewById(R.id.endereco);
				endereco.setText(imovel.getEndereco());
				
				TextView valor = (TextView) findViewById(R.id.valor_et);
				valor.setText(""+imovel.getValor());
			} catch (NullPointerException e) {
				
			}
			
			
		}
		
	}
	
	
}
