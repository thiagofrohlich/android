package com.ufpr.casaminha.controller;

import java.math.BigDecimal;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ufpr.casaminha.R;
import com.ufpr.casaminha.model.Imovel;

public class Relatorio extends Activity {

	private LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // remove title bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // remove notification bar
		setContentView(R.layout.activity_relatorio);
		
		inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		new GetHousesTask().execute((Object[]) null);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.relatorio, menu);
		return true;
	}
	
	
private class GetHousesTask extends AsyncTask<Object, Object, List<Imovel>>{
		
		DatabaseConnector conector = new DatabaseConnector(Relatorio.this);

		@Override
		protected List<Imovel> doInBackground(Object... params) {
			conector.open();
		
			return conector.findVendidos();
		}
		
		@Override
		protected void onPostExecute(List<Imovel> result) {
			ViewGroup conteudo = (ViewGroup) findViewById(R.id.listagemRelatorio);
			BigDecimal total = new BigDecimal(0);
			
			for (Imovel imovel : result) {
				View listagem = inflater.inflate(R.layout.lista_relatorio, (ViewGroup) findViewById(R.layout.lista_relatorio));
				
				TextView endereco = (TextView) listagem.findViewById(R.id.enderecoIm);
				TextView tipo = (TextView) listagem.findViewById(R.id.tipoIm);
				TextView valor = (TextView) listagem.findViewById(R.id.vlrIm);
				
				endereco.setText(imovel.getEndereco());
				tipo.setText(imovel.getTipo());
				valor.setText(""+imovel.getValor());
				
				total = total.add( new BigDecimal( imovel.getValor() ) ).setScale(2, BigDecimal.ROUND_HALF_UP);
				conteudo.addView(listagem);
			}
			
			TextView somatoria = (TextView) findViewById(R.id.somatoria);
			somatoria.setText(String.valueOf(total.doubleValue()));
			conector.close();
		}
	}

}
