package Api_Banco.DTOS;

import Api_Banco.Entidades.Conta;

public class ContaSaqueDTO {
	private String agencia;
	private String conta;
	private double saldo;
	private double valor;
	
	
	public ContaSaqueDTO(Conta conta) {
		super();
		this.agencia = conta.getAgencia();
		this.conta = conta.getConta();
		this.saldo = conta.getSaldo();
	}
	public ContaSaqueDTO() {}
	
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	
	

}
