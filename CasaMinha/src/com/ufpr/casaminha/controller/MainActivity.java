package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.ufpr.casaminha.R;
import com.ufpr.casaminha.model.Imovel;

public class MainActivity extends Activity {
	
	public static final String FILTRO_LOG = "CasaMinha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Usar o m�todo abaixo caso precise popular a tabela para testes
//        populate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void openRelatorio(View v) {
    	Intent i = new Intent(this, Relatorio.class);
    	startActivity(i);
    }
    
    public void openBuscar(View v) {
    	Intent i = new Intent(this, Buscar.class);
    	startActivity(i);
    }
    
    public void openInserir(View v) {
    	Intent i = new Intent(this, Inserir.class);
    	startActivity(i);
    }
    
    public void populate() {
    	new PopulateHousesTask().execute((Object[]) null);
    }
    
    private class PopulateHousesTask extends AsyncTask<Object, Object, Object>{
		
		DatabaseConnector conector = new DatabaseConnector(MainActivity.this);

		@Override
		protected Cursor doInBackground(Object... params) {
			Log.i(FILTRO_LOG, "populando db");
			
			conector.open();
			
			conector.truncate();
			conector.insert(new Imovel(Imovel.CASA_NA_RUA, 1d, 0d, "AQUELA RUA", 1));
			conector.insert(new Imovel(Imovel.CASA_CONDOMINIO, 2d, 0d, "AQUELA RUA 2", 2));
			conector.insert(new Imovel(Imovel.APARTAMENTO, 3d, 0d, "AQUELA RUA 3", 3));
			conector.insert(new Imovel(Imovel.CASA_NA_RUA, 4d, 0d, "AQUELA RUA 4", 4));
			
			Log.i(FILTRO_LOG, "db populado");
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
//			super.onPostExecute(result);
			conector.close();
		}
		
	}
    
}
