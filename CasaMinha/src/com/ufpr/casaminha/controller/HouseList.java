package com.ufpr.casaminha.controller;

import com.ufpr.casaminha.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class HouseList extends Activity {
	
	private CursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(MainActivity.CATEGORIA, "onCreate HouseList");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_imoveis);
		
		String from[] = new String[] {"valor"};
		int to[] = {R.id.listagemImoveis};
		adapter = new SimpleCursorAdapter(HouseList.this, R.id.listagemImoveis, null, from, to);
		
		new GetHousesTask().execute((Object[]) null);
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
		
		DatabaseConnector conector = new DatabaseConnector(HouseList.this);

		@Override
		protected Cursor doInBackground(Object... params) {
			Log.i(MainActivity.CATEGORIA, "buscando casas");
			conector.open();
			return conector.getAll();
		}
		
		@Override
		protected void onPostExecute(Cursor result) {
			Log.i(MainActivity.CATEGORIA, "populando tela");
			adapter.changeCursor(result);
			conector.close();
		}
	}
	
}
