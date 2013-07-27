package com.ufpr.casaminha.controller;

import java.util.ArrayList;
import java.util.List;

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
		newHouse.put("vendido", "false");
		
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
	public List<Imovel> getAll() {
		Cursor result = database.query(TABLE_HOUSES, null,	null, null, null, null, "valor");
		
		List<Imovel> list = null;
		if(result.getCount() > 0) {
			result.moveToPosition(0);
			list = new ArrayList<Imovel>(result.getCount());
			for(int i=0; i<result.getCount(); i++) {
				Imovel imovel = new Imovel(result.getLong(result.getColumnIndexOrThrow("_id")));
				imovel.setEndereco(result.getString(result.getColumnIndexOrThrow("endereco")));
				imovel.setQtdQuartos(result.getInt(result.getColumnIndexOrThrow("qtd_quartos")));
				imovel.setTipo(result.getString(result.getColumnIndexOrThrow("tipo")));
				imovel.setValor(result.getDouble(result.getColumnIndexOrThrow("valor")));
				imovel.setValorCondominio(result.getDouble(result.getColumnIndexOrThrow("valor_condominio")));
				
				list.add(imovel);
				
				result.moveToNext();
			}
		}
		return list;
	}
	
	// Buscar por id
	public Imovel getOne(Long id) {
		try {
			Cursor result = database.query(TABLE_HOUSES, null, "_id=" + id, null, null, null, null);
			result.moveToPosition(0);
			
			Imovel imovel = new Imovel();
			imovel.setId(result.getLong(result.getColumnIndexOrThrow("_id")));
			imovel.setEndereco(result.getString(result.getColumnIndexOrThrow("endereco")));
			imovel.setQtdQuartos(result.getInt(result.getColumnIndexOrThrow("qtd_quartos")));
			imovel.setTipo(result.getString(result.getColumnIndexOrThrow("tipo")));
			imovel.setValor(result.getDouble(result.getColumnIndexOrThrow("valor")));
			imovel.setValorCondominio(result.getDouble(result.getColumnIndexOrThrow("valor_condominio")));
			imovel.setVendido(result.getString(result.getColumnIndexOrThrow("vendido")).equalsIgnoreCase("true") ? true : false);
			
			return imovel;
		} catch(NullPointerException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public boolean sell(Long id) {
		ContentValues args = new ContentValues();
		args.put("vendido", "true");
		return database.update(TABLE_HOUSES, args, "_id=" + id, null) > 0;
	}
	
	public void truncate() {
		String sql = "DELETE FROM "+ TABLE_HOUSES + ";";
		Log.d(MainActivity.FILTRO_LOG, sql);
		database.execSQL(sql);
	}
	
	public List<Imovel> filtrar(String tipo, Integer qtdQuartos, Double valor) {
		if(tipo == null && qtdQuartos == null && valor == null) return getAll();
		
		StringBuffer where = new StringBuffer();
		
		boolean fTipo = false, fQuartos = false, fValor = false;
		if(tipo != null && !tipo.trim().equals("")) fTipo = true;
		if(qtdQuartos != null && qtdQuartos > 0) fQuartos = true;
		if(valor != null && valor > 0) fValor = true;
		
		if(fTipo) {
			where.append(" tipo='"+ tipo + "'");
			if(fQuartos || fValor) where.append(" and");
		}
		if(fQuartos) {
			where.append(" qtd_quartos="+ qtdQuartos);
			if(fValor) where.append(" and");
		}
		if(fValor) where.append(" valor<="+ valor);
		
		Log.d(MainActivity.FILTRO_LOG, where.toString());
		
		Cursor result = database.query(TABLE_HOUSES, null, where.toString(), null, null, null, "valor");
		
		List<Imovel> list = null;
		if(result.getCount() > 0) {
			result.moveToPosition(0);
			list = new ArrayList<Imovel>(result.getCount());
			for(int i=0; i<result.getCount(); i++) {
				Imovel imovel = new Imovel(result.getLong(result.getColumnIndexOrThrow("_id")));
				imovel.setEndereco(result.getString(result.getColumnIndexOrThrow("endereco")));
				imovel.setQtdQuartos(result.getInt(result.getColumnIndexOrThrow("qtd_quartos")));
				imovel.setTipo(result.getString(result.getColumnIndexOrThrow("tipo")));
				imovel.setValor(result.getDouble(result.getColumnIndexOrThrow("valor")));
				imovel.setValorCondominio(result.getDouble(result.getColumnIndexOrThrow("valor_condominio")));
				
				list.add(imovel);
				
				result.moveToNext();
			}
		}
		return list;
		
	}

	public List<Imovel> findVendidos(){
		Cursor result = database.query(TABLE_HOUSES, null, "vendido = 'true'", null, null, null, null);
		
		List<Imovel> list = null;
		if(result.getCount() > 0) {
			result.moveToPosition(0);
			list = new ArrayList<Imovel>(result.getCount());
			for(int i=0; i<result.getCount(); i++) {
				Imovel imovel = new Imovel();
				imovel.setEndereco(result.getString(result.getColumnIndexOrThrow("endereco")));
				imovel.setQtdQuartos(result.getInt(result.getColumnIndexOrThrow("qtd_quartos")));
				imovel.setTipo(result.getString(result.getColumnIndexOrThrow("tipo")));
				imovel.setValor(result.getDouble(result.getColumnIndexOrThrow("valor")));
				imovel.setValorCondominio(result.getDouble(result.getColumnIndexOrThrow("valor_condominio")));
				
				list.add(imovel);
				
				result.moveToNext();
			}
		}
		return list;
		
		
	}

}
