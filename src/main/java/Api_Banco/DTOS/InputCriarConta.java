package Api_Banco.DTOS;

import Api_Banco.Entidades.Conta;

public class InputCriarConta { 
	private String conta;
	private String agencia;
	private String senha;
	private double saldoInicial;
	private String cpf;
	private String nome;
	
	public InputCriarConta(Conta conta) {
		String camuflagem = conta.getSenha().replace(conta.getSenha(), "*****");
		this.conta = conta.getConta();
		this.agencia = conta.getAgencia();
		this.senha = camuflagem;
		this.saldoInicial = conta.getSaldo();
		this.cpf = conta.getCliente().getCpf();
		this.nome = conta.getCliente().getNome();
		
	}
	public InputCriarConta() {}

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String numeroDaAgencia) {
		agencia = numeroDaAgencia;
	}

	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
/*
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	*/

	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public double getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	/*
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	*/
	
	

}
