package com.ufpr.casaminha.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the houses database table.
 * 
 */
@Entity
@Table( name="houses")
public class House implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="_id")
	private Integer id;

	private String endereco;

	@Column(name="qtd_quartos")
	private Integer qtdQuartos;

	private String tipo;

	private double valor;

	@Column(name="valor_condominio")
	private double valorCondominio;

	private Boolean vendido;

	public House() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getQtdQuartos() {
		return this.qtdQuartos;
	}

	public void setQtdQuartos(Integer qtdQuartos) {
		this.qtdQuartos = qtdQuartos;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getValorCondominio() {
		return this.valorCondominio;
	}

	public void setValorCondominio(double valorCondominio) {
		this.valorCondominio = valorCondominio;
	}

	public Boolean getVendido() {
		return this.vendido;
	}

	public void setVendido(Boolean vendido) {
		this.vendido = vendido;
	}

}