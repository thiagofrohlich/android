package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ufpr.casaminha.R;
import com.ufpr.casaminha.model.Imovel;

public class Detalhe extends Activity {
	
	private Imovel imovel;

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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.vender:
			vender();
			return true;
		
		case R.id.excluir:
			excluir();
			return true;
		
		case R.id.editar:
			editar(getBaseContext());
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void vender() {
		
		AsyncTask<Long, Object, Boolean> sellTask = new AsyncTask<Long, Object, Boolean>() {

			DatabaseConnector conector = new DatabaseConnector(Detalhe.this);
			
			@Override
			protected Boolean doInBackground(Long... params) {
				conector.open();
				return conector.sell(params[0]);
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(result) {
					Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.vendido), Toast.LENGTH_SHORT);
					toast.show();
				}
				finish();
			}
			
		};
		
		sellTask.execute(imovel.getId());
	}
	
	public void excluir() {
		AsyncTask<Long, Object, Boolean> deleteTask = new AsyncTask<Long, Object, Boolean>() {

			DatabaseConnector conector = new DatabaseConnector(Detalhe.this);
			
			@Override
			protected Boolean doInBackground(Long... params) {
				conector.open();
				return conector.excluir(params[0]);
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(result) {
					Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.removido), Toast.LENGTH_SHORT);
					toast.show();
				}
				finish();
			}
			
		};
		
		deleteTask.execute(imovel.getId());
	}
	
	public void editar(Context context) {
		Intent it = new Intent(context, Inserir.class);
		it.putExtra("idCasa", imovel.getId());
		
		startActivity(it);
	}
	
	private class GetHouseTask extends AsyncTask<Bundle, Object, Imovel> {

		DatabaseConnector conector = new DatabaseConnector(Detalhe.this);
		
		@Override
		protected Imovel doInBackground(Bundle... params) {
			conector.open();
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
			TextView tipo = (TextView) findViewById(R.id.tipo);
			TextView valorCond = (TextView) findViewById(R.id.valorCond);
			TextView qtdQuartos = (TextView) findViewById(R.id.qtos);
			TextView endereco = (TextView) findViewById(R.id.endereco);
			TextView valor = (TextView)findViewById(R.id.valor);
			TextView disponibilidade = (TextView) findViewById(R.id.statusText);
//			Button saveButton = (Button) findViewById(R.id.salvar);
			
			try {
				tipo.setText(imovel.getTipo());
				valorCond.setText(""+imovel.getValorCondominio());
				qtdQuartos.setText(""+ imovel.getQtdQuartos());
				endereco.setText(imovel.getEndereco());
				valor.setText(""+imovel.getValor());
				
				disponibilidade.setText(imovel.isVendido() ? getResources().getString(R.string.vendido) : getResources().getString(R.string.disponivel));
				disponibilidade.setEnabled(!imovel.isVendido());
//				saveButton.setEnabled(!imovel.isVendido());
			} catch (NullPointerException e) {
				
			}
			
			conector.close();
		}
		
	}
	

}