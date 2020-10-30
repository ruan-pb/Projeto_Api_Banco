package Api_Banco.DTOS;



import org.hibernate.annotations.Cascade;

import Api_Banco.Entidades.Parcela;

public class InputCartaoDeCredito {
	private String conta;
	private String Agencia;
	private String numeroDoCartao;
	private int parcelas;
	private double valor;
	
	
	
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getAgencia() {
		return Agencia;
	}
	public void setAgencia(String agencia) {
		Agencia = agencia;
	}
	public String getNumeroDoCartao() {
		return numeroDoCartao;
	}
	public void setNumeroDoCartao(String numeroDoCartao) {
		this.numeroDoCartao = numeroDoCartao;
	}
	/*
	public Parcela getParcela() {
		return parcela;
	}
	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}
	*/
	public int getParcelas() {
		return parcelas;
	}
	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	

}
