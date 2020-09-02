package Api_Banco.DTOS;

import Api_Banco.Entidades.Conta;

public class ContaLoginDTO {
	private String numeroAgencia;
	private String numeroConta;
	private String senha;
	
	
	
	public ContaLoginDTO(Conta conta) {
		this.numeroAgencia = conta.getAgencia();
		this.numeroConta = conta.getConta();
		this.senha = conta.getSenha();
		
	}
	
	
	
	public String getNumeroAgencia() {
		return numeroAgencia;
	}
	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}
	public String getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
