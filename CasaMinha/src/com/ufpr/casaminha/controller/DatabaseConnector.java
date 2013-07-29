package com.ufpr.casaminha.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ufpr.casaminha.model.DatabaseOpenHelper;
import com.ufpr.casaminha.model.Imovel;
import com.ufpr.casaminha.model.WebService;

public class DatabaseConnector {
	
	public static final String URI = "http://192.168.25.6:8080/CasaMinhaWS";
	
	public DatabaseConnector(Context context) {
//		databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
	}
	
	// Abre conexão
	public void open() throws SQLException {
		// Cria ou abre DB para leitura / escrita
//		database = databaseOpenHelper.getWritableDatabase();
	}
	
	// Fecha conexão
	public void close() {
//		if(database != null)
//			database.close();
	}
	
	// Inserir Casa
	public void insert(Imovel imovel) {
		WebService webService = new WebService(URI + "/Inserir");
		Map newHouse = new HashMap();
		newHouse.put(Imovel.TIPO, ""+imovel.getTipo());
		newHouse.put(Imovel.VALOR, ""+imovel.getValor());
		newHouse.put(Imovel.VALOR_CONDOMINIO, ""+imovel.getValorCondominio());
		newHouse.put(Imovel.ENDERECO, imovel.getEndereco());
		newHouse.put(Imovel.QTD_QUARTOS, ""+imovel.getQtdQuartos());
		newHouse.put(imovel.VENDIDO, "false");
		String response = webService.webGet("", newHouse);
		
		
		
	}
	
	// Editar Casa
	public void update(Imovel imovel) {
		WebService webService = new WebService(URI + "/Update");
		Map newHouse = new HashMap();
		newHouse.put(Imovel.TIPO, ""+imovel.getTipo());
		newHouse.put(Imovel.VALOR, ""+imovel.getValor());
		newHouse.put(Imovel.VALOR_CONDOMINIO, ""+imovel.getValorCondominio());
		newHouse.put(Imovel.ENDERECO, imovel.getEndereco());
		newHouse.put(Imovel.QTD_QUARTOS, ""+imovel.getQtdQuartos());
		newHouse.put(imovel.VENDIDO, "false");
		newHouse.put("id", ""+imovel.getId());
		String response = webService.webGet("", newHouse);
		
	}
	
	// Buscar todas as casas
	public List<Imovel> getAll() {
		WebService webService = new WebService(URI + "/AllHouses");
		Map params = new HashMap();
		String response = webService.webGet("", params);
		
		List<Imovel> list = null;
		try {
			JSONObject o = new JSONObject(response);
			JSONArray array = (JSONArray) o.get("message");
			if(array != null && array.length()> 0) list = new ArrayList<Imovel>();
			
			for(int i=0; i < array.length(); i++) {
				JSONObject imovelJson = (JSONObject) array.get(i);
				Imovel im = new Imovel();
				im.setEndereco(imovelJson.getString(Imovel.ENDERECO));
				im.setId(imovelJson.getLong(Imovel.ID));
				im.setQtdQuartos(imovelJson.getInt(Imovel.QTD_QUARTOS));
				im.setTipo(imovelJson.getString(Imovel.TIPO));
				im.setValor(imovelJson.getDouble(Imovel.VALOR));
				im.setValorCondominio(imovelJson.getDouble(Imovel.VALOR_CONDOMINIO));
				im.setVendido(imovelJson.getBoolean("vendido"));
				
				list.add(im);
			}
			
			
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	// Buscar por id
	public Imovel getOne(Long id) {
		WebService webService = new WebService(URI + "/GetOne");
		Map params = new HashMap();
		params.put(Imovel.ID, ""+id);
		String response = webService.webGet("", params);
		
		try {
			JSONObject o = new JSONObject(response);
			JSONObject imovelJson = o.getJSONObject("casa");
			
			Imovel imovel = new Imovel();
			imovel.setEndereco(imovelJson.getString(Imovel.ENDERECO));
			imovel.setId(imovelJson.getLong(Imovel.ID));
			imovel.setQtdQuartos(imovelJson.getInt(Imovel.QTD_QUARTOS));
			imovel.setTipo(imovelJson.getString(Imovel.TIPO));
			imovel.setValor(imovelJson.getDouble(Imovel.VALOR));
			imovel.setValorCondominio(imovelJson.getDouble(Imovel.VALOR_CONDOMINIO));
			imovel.setVendido(imovelJson.getBoolean("vendido"));
			
			return imovel;
		} catch(JSONException e) {
			return null;
		} catch(NullPointerException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
	}
	
	public boolean sell(Long id) {
		WebService webService = new WebService(URI + "/Vender");
		Map params = new HashMap();
		params.put(Imovel.ID, ""+id);
		String response = webService.webGet("", params);
		
		try {
			JSONObject o = new JSONObject(response);
			return o.getBoolean("sucess");
		} catch	(JSONException e) {
			return false;
		}
	}
	
	public boolean excluir(Long id) {
		WebService webService = new WebService(URI + "/Excluir");
		Map params = new HashMap();
		params.put(Imovel.ID, ""+id);
		String response = webService.webGet("", params);
		
		try {
			JSONObject o = new JSONObject(response);
			return o.getBoolean("sucess");
		} catch	(JSONException e) {
			return false;
		}
	}
	
	public void truncate() {
//		String sql = "DELETE FROM "+ TABLE_HOUSES + ";";
//		Log.d(MainActivity.FILTRO_LOG, sql);
//		database.execSQL(sql);
	}
	
	public List<Imovel> filtrar(String tipo, Integer qtdQuartos, Double valor) {
		if(tipo == null && qtdQuartos == null && valor == null) return getAll();
		
		WebService webService = new WebService(URI + "/Filtrar");
		Map params = new HashMap();
		params.put(Imovel.TIPO, tipo);
		params.put(Imovel.QTD_QUARTOS, ""+qtdQuartos);
		params.put(Imovel.VALOR, ""+valor);
		String response = webService.webGet("", params);
		
		List<Imovel> list = null;
		try {
			JSONObject o = new JSONObject(response);
			JSONArray array = (JSONArray) o.get("message");
			if(array != null && array.length()> 0) list = new ArrayList<Imovel>();
			
			for(int i=0; i < array.length(); i++) {
				JSONObject ImovelJson = (JSONObject) array.get(i);
				Imovel im = new Imovel();
				im.setEndereco(ImovelJson.getString(Imovel.ENDERECO));
				im.setId(ImovelJson.getLong(Imovel.ID));
				im.setQtdQuartos(ImovelJson.getInt(Imovel.QTD_QUARTOS));
				im.setTipo(ImovelJson.getString(Imovel.TIPO));
				im.setValor(ImovelJson.getDouble(Imovel.VALOR));
				im.setValorCondominio(ImovelJson.getDouble(Imovel.VALOR_CONDOMINIO));
				im.setVendido(ImovelJson.getBoolean("vendido"));
				
				list.add(im);
			}
			
			
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

	public List<Imovel> findVendidos(){
		WebService webService = new WebService(URI + "/Relatorio");
		Map params = new HashMap();
		String response = webService.webGet("", params);
		
		List<Imovel> list = new ArrayList<Imovel>();
		try {
			JSONObject o = new JSONObject(response);
			JSONArray array = (JSONArray) o.get("message");
			if(array != null && array.length()> 0) list = new ArrayList<Imovel>();
			
			for(int i=0; i < array.length(); i++) {
				JSONObject imovelJson = (JSONObject) array.get(i);
				Imovel im = new Imovel();
				im.setEndereco(imovelJson.getString(Imovel.ENDERECO));
				im.setId(imovelJson.getLong(Imovel.ID));
				im.setQtdQuartos(imovelJson.getInt(Imovel.QTD_QUARTOS));
				im.setTipo(imovelJson.getString(Imovel.TIPO));
				im.setValor(imovelJson.getDouble(Imovel.VALOR));
				im.setValorCondominio(imovelJson.getDouble(Imovel.VALOR_CONDOMINIO));
				im.setVendido(imovelJson.getBoolean("vendido"));
				
				list.add(im);
			}
			
			
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		
		return list;
		
		
	}

}
