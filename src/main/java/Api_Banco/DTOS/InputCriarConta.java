package Api_Banco.DTOS;

import Api_Banco.Entidades.Conta;

public class InputCriarConta { 
	private String agencia;
	private String senha;
	private String cpf;
	private String nome;
	private String telefone;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	
	public InputCriarConta(Conta conta) {
		this.agencia = conta.getAgencia();
		this.senha = conta.getSenha();
		this.cpf = conta.getCliente().getCpf();
		this.nome = conta.getCliente().getNome();
		this.telefone = conta.getCliente().getTelefone();
		this.rua = conta.getCliente().getEndereco().getRua();
		this.bairro = conta.getCliente().getEndereco().getBairro();
		this.cidade = conta.getCliente().getEndereco().getCidade();
		this.estado = conta.getCliente().getEndereco().getEstado();
		
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

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
	
	
	

}
