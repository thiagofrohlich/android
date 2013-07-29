package com.ufpr.casaminha.model;



/**
 * @author THIAGO
 *
 */
/**
 * @author THIAGO
 *
 */
public class Imovel {
	
	public static final String ID = "id";
	public static final String TIPO = "tipo";
	public static final String VALOR = "valor";
	public static final String VALOR_CONDOMINIO = "valorCondominio";
	public static final String ENDERECO = "endereco";
	public static final String QTD_QUARTOS = "qtdQuartos";
	public static final String VENDIDO = "vendido";	
	public static final String CASA_NA_RUA = "Casa na rua";
	public static final String CASA_CONDOMINIO = "Casa em condomínio";
	public static final String APARTAMENTO = "Apartamento";
	
	private Long id;
	private String tipo;
	private Double valor;
	private Double valorCondominio;
	private String endereco;
	private Integer qtdQuartos;
	private Boolean vendido;
	
	public Imovel(){}
	
	public Imovel(Long id) {
		super();
		this.id = id;
	}


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
	public Boolean isVendido() {
		return vendido;
	}

	public void setVendido(Boolean vendido) {
		this.vendido = vendido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
