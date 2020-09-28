package Api_Banco.DTOS;



public class TranferenciaDTO {
	
	private String contaOrigem;
	//private String cpfOrigem;
	private String contaDestino;
	//private String cpfDestinatario;
	private double valorDestinado;
	
	public TranferenciaDTO() {}
	
	/*
	
	public TranferenciaDTO(Conta conta) {
		super();
		this.contaOrigem = conta.getConta();
		this.cpfOrigem = conta.getCliente().getCpf();
		this.contaDestino = conta.getConta();
		this.cpfDestinatario = conta.getCliente().getCpf();
		this.saldoDoDestinatario = conta.getSaldo();
		
	}
	*/
	public TranferenciaDTO(InputTranferencia conta) {
		super();
		this.contaOrigem = conta.getContaOrigem();
		//this.cpfOrigem = conta.getCliente().getCpf();
		this.contaDestino = conta.getContaDestino();
		//this.cpfDestinatario = conta.getCliente().getCpf();
		this.valorDestinado = conta.getValor();
		
	}



	public String getContaOrigem() {
		return contaOrigem;
	}
	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	/*
	public String getCpfOrigem() {
		return cpfOrigem;
	}
	public void setCpfOrigem(String cpfOrigem) {
		this.cpfOrigem = cpfOrigem;
	}
	*/
	public String getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}
	/*
	public String getCpfDestinatario() {
		return cpfDestinatario;
	}
	public void setCpfDestinatario(String cpfDestinatario) {
		this.cpfDestinatario = cpfDestinatario;
	}
	*/

	public double getValorDestinado() {
		return valorDestinado;
	}

	public void setValorDestinado(double valorDestinado) {
		this.valorDestinado = valorDestinado;
	}
	
	
	

}
