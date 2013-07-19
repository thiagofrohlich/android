package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.ufpr.casaminha.R;

public class Detalhe extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe);
		
		Intent it = getIntent();
		if(it != null) {
			Bundle pac = it.getExtras();
			if(pac != null) {
				new GetHouseTask().execute(pac);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe, menu);
		return true;
	}
	
	public void salvarStatus(View v) {
		
	}
	
	private class GetHouseTask extends AsyncTask<Bundle, Object, Cursor> {

		DatabaseConnector conector = new DatabaseConnector(Detalhe.this);
		
		@Override
		protected Cursor doInBackground(Bundle... params) {
			conector.open();
			if(params != null) {
				Bundle pac = params[0];
				return conector.getOne((long) pac.getInt("idCasa"));
			}

			finish();
			return null;
		}
		
		@Override
		protected void onPostExecute(Cursor result) {
			
			TextView tipo = (TextView) findViewById(R.id.tipo);
			TextView valorCond = (TextView) findViewById(R.id.valorCond);
			TextView qtdQuartos = (TextView) findViewById(R.id.qtos);
			TextView endereco = (TextView) findViewById(R.id.endereco);
			TextView valor = (TextView)findViewById(R.id.valor);
			
			result.moveToPosition(0);
			
			tipo.setText(result.getString(result.getColumnIndexOrThrow("tipo")));
			valorCond.setText(""+result.getDouble(result.getColumnIndexOrThrow("valor_condominio")));
			qtdQuartos.setText(""+ result.getInt(result.getColumnIndexOrThrow("qtd_quartos")));
			endereco.setText(result.getString(result.getColumnIndexOrThrow("endereco")));
			valor.setText(""+result.getDouble(result.getColumnIndexOrThrow("valor")));
			
			conector.close();
		}
		
	}

}
