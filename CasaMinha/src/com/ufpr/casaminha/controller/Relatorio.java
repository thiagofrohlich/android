package com.ufpr.casaminha.controller;

import com.ufpr.casaminha.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class Relatorio extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // remove title bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // remove notification bar
		setContentView(R.layout.activity_relatorio);
		
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		ViewGroup conteudo = (ViewGroup) findViewById(R.id.listagemImoveis);
		for(int x=0; x<=3; x++) {
			View listagem = inflater.inflate(R.layout.lista_imoveis, (ViewGroup) findViewById(R.layout.lista_imoveis));
			conteudo.addView(listagem);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.relatorio, menu);
		return true;
	}

}
