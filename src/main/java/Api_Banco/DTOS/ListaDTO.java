package Api_Banco.DTOS;

import Api_Banco.Entidades.Conta;

public class ListaDTO {
	private String conta;
	private String agencia;
	private String nome;
	private double saldo;
	
	public ListaDTO() {}
	
	

	public ListaDTO(Conta conta) {
		super();
		this.conta = conta.getConta();
		this.agencia = conta.getAgencia();
		this.nome = conta.getCliente().getNome();
		this.saldo = conta.getSaldo();
	}



	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	
	
	

}
