package Api_Banco.Entidades;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Poupanca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id; 
	private double Saldo;
	private static double Juros = 1.8;
	
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
	
	public double ganhosComJuros() {
		return this.Saldo*this.Juros;
	}

}
