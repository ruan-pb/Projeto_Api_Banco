package Api_Banco.DTOS;

import java.util.Date;

import Api_Banco.Entidades.Poupanca;

public class PoupancaDTO {
	
	
	private Integer id;
	private double juros;
	private double saldo;
	private Date dataDeAbertura;
	
	
	public PoupancaDTO(Poupanca poupanca) {
		this.id = poupanca.getId();
		this.juros = poupanca.getJuros();
		this.saldo = poupanca.getSaldo();
		this.dataDeAbertura = poupanca.getDataDeAbertura();
		
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getJuros() {
		return juros;
	}
	public void setJuros(double juros) {
		this.juros = juros;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public Date getDataDeAbertura() {
		return dataDeAbertura;
	}
	public void setDataDeAbertura(Date dataDeAbertura) {
		this.dataDeAbertura = dataDeAbertura;
	}
	
	

}
