package com.ufpr.casaminha.controller;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ufpr.casaminha.R;
import com.ufpr.casaminha.model.Imovel;

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
	
	private class GetHousesTask extends AsyncTask<Object, Object, List<Imovel>>{
		
		DatabaseConnector conector = new DatabaseConnector(Buscar.this);

		@Override
		protected List<Imovel> doInBackground(Object... params) {
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
		protected void onPostExecute(List<Imovel> result) {
			ViewGroup conteudo = (ViewGroup) findViewById(R.id.listagemImoveis);
			conteudo.removeAllViews();
			
			if(result == null || result.isEmpty()) {
				Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.noneFound), Toast.LENGTH_SHORT);
				toast.show();
			} else {
			
				for (Imovel imovel : result) {
					View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
					
					TextView endereco = (TextView) listagem.findViewById(R.id.enderecoIm);
					TextView tipo = (TextView) listagem.findViewById(R.id.tipoIm);
					
					endereco.setText(imovel.getEndereco());
					tipo.setText(imovel.getTipo());
					
					Log.d(MainActivity.CATEGORIA, ""+ imovel.getId());
					listagem.setId(imovel.getId().intValue());
					Log.d(MainActivity.CATEGORIA, ""+ listagem.getId());
					
					listagem.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View view) {
							
							Log.i(MainActivity.CATEGORIA, "id view: "+view.getId());
							Intent it = new Intent(view.getContext(), Detalhe.class);
							it.putExtra("idCasa", view.getId());
							
							startActivity(it);
							
						}
					});
					
					conteudo.addView(listagem);
				}
			}
				
			conector.close();
		}
	}

}
