package Api_Banco.DTOS;

import java.util.Optional;

import Api_Banco.Entidades.Cliente;

public class EnderecoCpfDTO {
	private String rua;
	private String bairro;
	private String estado;
	private String cidade;
	
	public EnderecoCpfDTO() {}

	public EnderecoCpfDTO(Optional<Cliente> cli) {
		super();
		this.rua = cli.get().getEndereco().getRua();
		this.bairro = cli.get().getEndereco().getBairro();
		this.estado = cli.get().getEndereco().getEstado();
		this.cidade = cli.get().getEndereco().getCidade();
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	

}
