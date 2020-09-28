package Api_Banco.DTOS;

import Api_Banco.Entidades.Conta;

public class TranferenciaDTO {
	
	private String contaOrigem;
	private String cpfOrigem;
	private String contaDestino;
	private String cpfDestinatario;
	private double saldoDoDestinatario;
	
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
		this.saldoDoDestinatario = conta.getValor();
	}



	public String getContaOrigem() {
		return contaOrigem;
	}
	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	public String getCpfOrigem() {
		return cpfOrigem;
	}
	public void setCpfOrigem(String cpfOrigem) {
		this.cpfOrigem = cpfOrigem;
	}
	public String getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}
	public String getCpfDestinatario() {
		return cpfDestinatario;
	}
	public void setCpfDestinatario(String cpfDestinatario) {
		this.cpfDestinatario = cpfDestinatario;
	}
	public double getSaldoDoDestinatario() {
		return saldoDoDestinatario;
	}
	public void setSaldoDoDestinatario(double saldoDoDestinatario) {
		this.saldoDoDestinatario = saldoDoDestinatario;
	}
	
	

}
