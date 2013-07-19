package com.ufpr.casaminha.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ufpr.casaminha.model.DatabaseOpenHelper;
import com.ufpr.casaminha.model.Imovel;

public class DatabaseConnector {
	
	private static final String DATABASE_NAME = "HousesDatabase";
	private static final String TABLE_HOUSES = "houses";
	private SQLiteDatabase database;
	private DatabaseOpenHelper databaseOpenHelper;
	
	public DatabaseConnector(Context context) {
		databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
	}
	
	// Abre conexão
	public void open() throws SQLException {
		// Cria ou abre DB para leitura / escrita
		database = databaseOpenHelper.getWritableDatabase();
	}
	
	// Fecha conexão
	public void close() {
		if(database != null)
			database.close();
	}
	
	// Inserir Casa
	public void insert(Imovel imovel) {
		
		ContentValues newHouse = new ContentValues();
		newHouse.put("tipo", imovel.getTipo());
		newHouse.put("valor", imovel.getValor());
		newHouse.put("valor_condominio", imovel.getValorCondominio());
		newHouse.put("endereco", imovel.getEndereco());
		newHouse.put("qtd_quartos", imovel.getQtdQuartos());
		
		open();
		database.insert(TABLE_HOUSES, null, newHouse);
		close();
		
	}
	
	// Editar Casa
	public void update(Long id, String tipo, Double valor, Double valorCondominio, String endereco, Integer qtdQuartos) {
		
		ContentValues house = new ContentValues();
		house.put("tipo", tipo);
		house.put("valor", valor);
		house.put("valor_condominio", valorCondominio);
		house.put("endereco", endereco);
		house.put("qtd_quartos", qtdQuartos);
		
		open();
		database.update(TABLE_HOUSES, house, "_id="+ id, null);
		close();
		
	}
	
	// Buscar todas as casas
	public Cursor getAll() {
		return database.query(TABLE_HOUSES, new String[] {"_id", "tipo", "valor", "valor_condominio", "endereco", "qtd_quartos"}, null, null, null, null, "valor");
	}
	
	// Buscar por id
	public Cursor getOne(Long id) {
		return database.query(TABLE_HOUSES, null, "_id=" + id, null, null, null, null);
	}
	
	public void truncate() {
		String sql = "DELETE FROM "+ TABLE_HOUSES + ";";
		Log.d(MainActivity.CATEGORIA, sql);
		database.execSQL(sql);
	}
	
	public Cursor filtrar(String tipo, Integer qtdQuartos, Double valor) {
		if(tipo == null && qtdQuartos == null && valor == null) return getAll();
		
		StringBuffer where = new StringBuffer();
		
		boolean fTipo = false, fQuartos = false, fValor = false;
		if(tipo != null && !tipo.trim().equals("")) fTipo = true;
		if(qtdQuartos != null && qtdQuartos > 0) fQuartos = true;
		if(valor != null && valor > 0) fValor = true;
		
		if(fTipo) {
			where.append(" tipo='"+ tipo.toUpperCase() + "'");
			if(fQuartos || fValor) where.append(" and");
		}
		if(fQuartos) {
			where.append(" qtd_quartos="+ qtdQuartos);
			if(fValor) where.append(" and");
		}
		if(fValor) where.append(" valor="+ valor);
		
		Log.d(MainActivity.CATEGORIA, where.toString());
		return database.query(TABLE_HOUSES, null, where.toString(), null, null, null, "valor");
	}

	public Cursor findByEndereco(Imovel imovel){
		return database.query(TABLE_HOUSES, null, "endereco=" + imovel.getEndereco(), null, null, null, null);
	}

}
