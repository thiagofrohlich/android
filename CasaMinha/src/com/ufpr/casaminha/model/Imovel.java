package com.ufpr.casaminha.model;



public class Imovel {
	private String tipo;
	private Double valor;
	private Double valorCondominio;
	private String endereco;
	private Integer qtdQuartos;
	
	public Imovel(){}
	
	public Imovel(String tipo, Double valor, Double valorCondominio,
			String endereco, Integer qtdQuartos) {
		super();
		this.tipo = tipo;
		this.valor = valor;
		this.valorCondominio = valorCondominio;
		this.endereco = endereco;
		this.qtdQuartos = qtdQuartos;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getValorCondominio() {
		return valorCondominio;
	}
	public void setValorCondominio(Double valorCondominio) {
		this.valorCondominio = valorCondominio;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Integer getQtdQuartos() {
		return qtdQuartos;
	}
	public void setQtdQuartos(Integer qtdQuartos) {
		this.qtdQuartos = qtdQuartos;
	}
	
}
