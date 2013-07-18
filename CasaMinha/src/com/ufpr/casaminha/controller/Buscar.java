package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ufpr.casaminha.R;

public class Buscar extends Activity {
	
	private LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
		
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		new GetHousesTask().execute((Object[]) null);
		
//		for(int x=0; x<=3; x++) {
//			View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
//			conteudo.addView(listagem);
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar, menu);
		return true;
	}
	
	public void buscarImoveis(View v) {
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		ViewGroup conteudo = (ViewGroup) findViewById(R.id.listagemImoveis);
		conteudo.removeAllViews();
		for(int x=0; x<=3; x++) {
			View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
			conteudo.addView(listagem);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		new GetHousesTask().execute((Object[]) null);
	}
	
	@Override
	protected void onStop() {
//		Cursor cursor = adapter.getCursor();
//		if(cursor != null) 
//			cursor.deactivate();
//		
//		adapter.changeCursor(null);
		super.onStop();
	}
	
	private class GetHousesTask extends AsyncTask<Object, Object, Cursor>{
		
		DatabaseConnector conector = new DatabaseConnector(Buscar.this);

		@Override
		protected Cursor doInBackground(Object... params) {
			conector.open();
			return conector.getAll();
		}
		
		@Override
		protected void onPostExecute(Cursor result) {
			ViewGroup conteudo = (ViewGroup) findViewById(R.id.listagemImoveis);
			
			for(int i=0; i < result.getCount(); i++) {
				View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
				result.moveToPosition(i);
				
				TextView endereco = (TextView) listagem.findViewById(R.id.enderecoIm);
				endereco.setText(result.getString(result.getColumnIndexOrThrow("endereco")));
				
				TextView tipo = (TextView) listagem.findViewById(R.id.tipoIm);
				tipo.setText(result.getString(result.getColumnIndexOrThrow("tipo")));
				
				conteudo.addView(listagem);
			}
//			adapter.changeCursor(result);
			conector.close();
		}
	}

}
