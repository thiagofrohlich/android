package com.ufpr.casaminha.controller;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.ufpr.casaminha.R;

public class Buscar extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
		
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
//		LayoutInflater inflater = LayoutInflater.from();
		ViewGroup conteudo = (ViewGroup) findViewById(R.id.listagemImoveis);
		
//		View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
		View listagem = inflater.inflate(R.layout.lista_imoveis, conteudo);
		conteudo.addView(listagem);

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
	

}
