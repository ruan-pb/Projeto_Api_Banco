package Api_Banco.DTOS;

import Api_Banco.Entidades.Conta;

public class ContaDepositoDTO {
	private String agencia;
	private String conta;
	private double deposito;
	
	public ContaDepositoDTO() {}

	public ContaDepositoDTO(Conta conta) {
		super();
		this.agencia = conta.getAgencia();
		this.conta = conta.getConta();
		this.deposito = conta.getSaldo();
	}

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

	public double getDeposito() {
		return deposito;
	}

	public void setDeposito(double deposito) {
		this.deposito = deposito;
	}
	
	
	
	
	
	

}
