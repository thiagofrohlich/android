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

import com.ufpr.casaminha.R;

public class Buscar extends Activity {
	
	private CursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
		
		String from[] = new String[] {"valor"};
		int to[] = {R.id.listagemImoveis};
		adapter = new SimpleCursorAdapter(Buscar.this, R.layout.lista_imoveis, null, from, null);
		
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		ViewGroup conteudo = (ViewGroup) findViewById(R.id.listagemImoveis);
		
		View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
		conteudo.addView(listagem);
		
		new GetHousesTask().execute((Object[]) null);
		
//		adapter.getCursor().
		
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
		Cursor cursor = adapter.getCursor();
		if(cursor != null) 
			cursor.deactivate();
		
		adapter.changeCursor(null);
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
			adapter.changeCursor(result);
			conector.close();
		}
	}

}
