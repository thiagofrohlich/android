package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ufpr.casaminha.R;

public class Buscar extends Activity {
	
	private LayoutInflater inflater;
	private Integer qtdQuartosCasa;
	private String tipoCasa;
	private Double valorCasa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
		
		// Recupera o LayoutInflater
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar, menu);
		return true;
	}
	
	public void buscarImoveis(View v) {
		
		Spinner tipoSpinner = (Spinner) findViewById(R.id.tipo_spinner);
		Spinner quartosSpinner = (Spinner) findViewById(R.id.qtos_spinner);
		EditText valorCasa = (EditText) findViewById(R.id.valor_et);
		
		tipoCasa = (String) tipoSpinner.getSelectedItem();
		try {
			qtdQuartosCasa = Integer.parseInt(""+quartosSpinner.getSelectedItem());
		} catch(NumberFormatException e) {
			qtdQuartosCasa = null;
		}
		try {
			this.valorCasa = Double.parseDouble(valorCasa.getText().toString());
		} catch(NumberFormatException e) {
			this.valorCasa = null;
		}
		
		new GetHousesTask().execute(new Object[] {tipoCasa, qtdQuartosCasa, this.valorCasa});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		new GetHousesTask().execute(new Object[] {tipoCasa, qtdQuartosCasa, this.valorCasa});
	}
	
	private class GetHousesTask extends AsyncTask<Object, Object, Cursor>{
		
		DatabaseConnector conector = new DatabaseConnector(Buscar.this);

		@Override
		protected Cursor doInBackground(Object... params) {
			conector.open();
			
			if(params != null) {
				String tipo = (String) params[0];
				Integer qtdQuartos = (Integer) params[1];
				Double valor = (Double) params[2];
				return conector.filtrar(tipo, qtdQuartos, valor);
			}
		
		
			return conector.getAll();
			
		}
		
		@Override
		protected void onPostExecute(Cursor result) {
			ViewGroup conteudo = (ViewGroup) findViewById(R.id.listagemImoveis);
			conteudo.removeAllViews();
			
			Log.d(MainActivity.CATEGORIA, "result count: "+ result.getCount());
			
			for(int i=0; i < result.getCount(); i++) {
				View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
				
				// Aponta para o registro
				result.moveToPosition(i);
				
				TextView endereco = (TextView) listagem.findViewById(R.id.enderecoIm);
				endereco.setText(result.getString(result.getColumnIndexOrThrow("endereco")));
				
				TextView tipo = (TextView) listagem.findViewById(R.id.tipoIm);
				tipo.setText(result.getString(result.getColumnIndexOrThrow("tipo")));
				
				conteudo.addView(listagem);
			}
			
			if(result.getCount() == 0) {
				View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
				
				TextView endereco = (TextView) listagem.findViewById(R.id.enderecoIm);
				endereco.setText(getResources().getString(R.string.noneFound));
				
			}
			
			conector.close();
		}
	}

}
