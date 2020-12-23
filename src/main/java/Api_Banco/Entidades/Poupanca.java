package Api_Banco.Entidades;

import java.io.Serializable;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Poupanca implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id; 
	private double Saldo;
	private static double Juros = 1.1;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date DataDeAbertura;
	
	@OneToOne(mappedBy = "poupanca", cascade = CascadeType.ALL)
	private Conta conta;
	
	public Poupanca() {}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public double getSaldo() {
		return Saldo;
	}

	public void setSaldo(double saldo) {
		Saldo = saldo;
	}

	public double getJuros() {
		return Juros;
	}

	public void setJuros(double juros) {
		Juros = juros;
	}
	
	
	@JsonIgnore
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Date getDataDeAbertura() {
		return DataDeAbertura;
	}

	public void setDataDeAbertura(Date dataDeAbertura) {
		DataDeAbertura = dataDeAbertura;
	}
	
	

}
