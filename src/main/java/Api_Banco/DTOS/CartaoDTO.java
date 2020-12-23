package Api_Banco.DTOS;

import java.sql.Date;

import Api_Banco.Entidades.CartaoDeCredito;

public class CartaoDTO {
	
	private Integer id;
	private String numeroDoCartao;
	private double valor;
	private Date DataDaCompra;
	private double limiteDisponivel;
	private double faturalAtual;
	
	
	public CartaoDTO(CartaoDeCredito cartao) {
		this.id = cartao.getId();
		this.numeroDoCartao = cartao.getNumeroDoCartao();
		this.valor = cartao.getValor();
		this.DataDaCompra = cartao.getDataDaCompra();
		this.limiteDisponivel = cartao.getLimiteDisponivel();
		this.faturalAtual = cartao.getFaturaAtual();
	}

	
	public CartaoDTO() {
		
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNumeroDoCartao() {
		return numeroDoCartao;
	}


	public void setNumeroDoCartao(String numeroDoCartao) {
		this.numeroDoCartao = numeroDoCartao;
	}


	public double getValor() {
		return valor;
	}


	public void setValor(double valor) {
		this.valor = valor;
	}


	public Date getDataDaCompra() {
		return DataDaCompra;
	}


	public void setDataDaCompra(Date dataDaCompra) {
		DataDaCompra = dataDaCompra;
	}


	public double getLimiteDisponivel() {
		return limiteDisponivel;
	}


	public void setLimiteDisponivel(double limiteDisponivel) {
		this.limiteDisponivel = limiteDisponivel;
	}


	public double getFaturalAtual() {
		return faturalAtual;
	}


	public void setFaturalAtual(double faturalAtual) {
		this.faturalAtual = faturalAtual;
	}
	
	
	


}
