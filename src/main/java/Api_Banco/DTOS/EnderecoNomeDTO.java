package Api_Banco.DTOS;



import Api_Banco.Entidades.Cliente;

public class EnderecoNomeDTO {
	private String rua;
	private String bairro;
	private String estado;
	private String cidade;
	
	public EnderecoNomeDTO() {}

	public EnderecoNomeDTO(Cliente x) {
		super();
		this.rua =x.getEndereco().getRua();
		this.bairro = x.getEndereco().getBairro();
		this.estado = x.getEndereco().getEstado();
		this.cidade = x.getEndereco().getCidade();
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



