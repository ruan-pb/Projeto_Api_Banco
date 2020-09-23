package Api_Banco.DTOS;

import Api_Banco.Entidades.Cliente;

public class ListaDTOCliente {
	private String nome;
	private String telefone;
	private String cpf ;
	private String cidade;
	
	
	
	
	public ListaDTOCliente(Cliente cliente) {
		super();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.cpf = cliente.getCpf();
		this.cidade = cliente.getEndereco().getCidade();
	}
	
	public ListaDTOCliente() {}
	
	
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	
	

}
